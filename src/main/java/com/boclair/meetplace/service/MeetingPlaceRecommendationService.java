package com.boclair.meetplace.service;

import com.boclair.meetplace.domain.PlaceCategory;
import com.boclair.meetplace.dto.CategoryRecommendation;
import com.boclair.meetplace.dto.ParticipantDistance;
import com.boclair.meetplace.dto.ParticipantRequest;
import com.boclair.meetplace.dto.PlaceRecommendation;
import com.boclair.meetplace.dto.RecommendationRequest;
import com.boclair.meetplace.dto.RecommendationResult;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;

@Service
public class MeetingPlaceRecommendationService {

    private static final int SEARCH_LIMIT_PER_CATEGORY = 12;
    private static final int RECOMMENDATION_LIMIT_PER_CATEGORY = 3;

    private final PlaceSearchClient placeSearchClient;
    private final DistanceCalculator distanceCalculator;

    public MeetingPlaceRecommendationService(
            PlaceSearchClient placeSearchClient,
            DistanceCalculator distanceCalculator
    ) {
        this.placeSearchClient = placeSearchClient;
        this.distanceCalculator = distanceCalculator;
    }

    public RecommendationResult recommend(RecommendationRequest request) {
        List<ParticipantRequest> participants = request.getParticipants();
        double centerLatitude = participants.stream()
                .mapToDouble(ParticipantRequest::getLatitude)
                .average()
                .orElseThrow();
        double centerLongitude = participants.stream()
                .mapToDouble(ParticipantRequest::getLongitude)
                .average()
                .orElseThrow();

        List<CategoryRecommendation> categoryRecommendations = request.getCategories()
                .stream()
                .distinct()
                .map(category -> recommendByCategory(category, participants, centerLatitude, centerLongitude, request))
                .toList();

        return new RecommendationResult(centerLatitude, centerLongitude, categoryRecommendations);
    }

    private CategoryRecommendation recommendByCategory(
            PlaceCategory category,
            List<ParticipantRequest> participants,
            double centerLatitude,
            double centerLongitude,
            RecommendationRequest request
    ) {
        List<PlaceRecommendation> recommendations = placeSearchClient
                .search(category, centerLatitude, centerLongitude, SEARCH_LIMIT_PER_CATEGORY)
                .stream()
                .map(place -> score(place, category, participants, centerLatitude, centerLongitude, request))
                .sorted(Comparator.comparing(PlaceRecommendation::score).reversed())
                .limit(RECOMMENDATION_LIMIT_PER_CATEGORY)
                .toList();

        return new CategoryRecommendation(category, recommendations);
    }

    private PlaceRecommendation score(
            ExternalPlace place,
            PlaceCategory category,
            List<ParticipantRequest> participants,
            double centerLatitude,
            double centerLongitude,
            RecommendationRequest request
    ) {
        List<ParticipantDistance> distances = participants.stream()
                .map(participant -> new ParticipantDistance(
                        participant.getName(),
                        round(distanceCalculator.distanceKm(
                                participant.getLatitude(),
                                participant.getLongitude(),
                                place.latitude(),
                                place.longitude()
                        ))
                ))
                .toList();

        DoubleSummaryStatistics stats = distances.stream()
                .mapToDouble(ParticipantDistance::distanceKm)
                .summaryStatistics();
        double averageDistance = stats.getAverage();
        double fairnessGap = stats.getMax() - stats.getMin();
        double centerDistance = distanceCalculator.distanceKm(
                centerLatitude,
                centerLongitude,
                place.latitude(),
                place.longitude()
        );

        int score = calculateScore(averageDistance, fairnessGap, centerDistance, place, request);
        String fairnessLevel = getFairnessLevel(fairnessGap);
        int estimatedTravelMinutes = estimateTravelMinutes(averageDistance);
        String reason = buildReason(place, category, averageDistance, fairnessGap, centerDistance, request, fairnessLevel);

        return new PlaceRecommendation(
                place.name(),
                place.address(),
                place.latitude(),
                place.longitude(),
                round(averageDistance),
                round(stats.getMax()),
                round(fairnessGap),
                round(centerDistance),
                estimatedTravelMinutes,
                fairnessLevel,
                reason,
                score,
                round(place.rating()),
                place.reviewCount(),
                place.reviewSnippet(),
                place.url(),
                place.source(),
                place.placeTypes().isEmpty() ? category.getDefaultPlaceTypes() : place.placeTypes(),
                distances
        );
    }

    private int calculateScore(
            double averageDistance,
            double fairnessGap,
            double centerDistance,
            ExternalPlace place,
            RecommendationRequest request
    ) {
        double ratingBonus = place.rating() <= 0 ? 0 : (place.rating() - 3.5) * 7.0;
        double reviewVolumeBonus = Math.min(8, Math.log10(Math.max(1, place.reviewCount())) * 3.0);
        double rawScore = 100
                + ratingBonus
                + reviewVolumeBonus
                - averageDistance * request.getMode().getAverageDistanceWeight()
                - fairnessGap * request.getMode().getFairnessGapWeight()
                - centerDistance * request.getMode().getCenterDistanceWeight();
        return Math.max(0, Math.min(100, (int) Math.round(rawScore)));
    }

    private String getFairnessLevel(double fairnessGap) {
        if (fairnessGap <= 3.0) {
            return "매우 공정";
        }
        if (fairnessGap <= 6.0) {
            return "공정";
        }
        if (fairnessGap <= 10.0) {
            return "보통";
        }
        return "편차 큼";
    }

    private int estimateTravelMinutes(double averageDistance) {
        return Math.max(10, (int) Math.round(averageDistance * 4.2 + 8));
    }

    private String buildReason(
            ExternalPlace place,
            PlaceCategory category,
            double averageDistance,
            double fairnessGap,
            double centerDistance,
            RecommendationRequest request,
            String fairnessLevel
    ) {
        String reviewText = place.reviewCount() > 0
                ? " 평점 %.1f점, 리뷰 %,d개도 함께 반영했습니다.".formatted(place.rating(), place.reviewCount())
                : " 리뷰 API 결과가 없으면 거리와 위치 점수 중심으로 평가합니다.";

        return "%s 기준으로 평균 이동거리 %.1fkm, 거리 편차 %.1fkm라 %s한 편입니다. 중간점과 %.1fkm 떨어진 실제 %s 장소입니다.%s"
                .formatted(
                        request.getMode().getLabel(),
                        round(averageDistance),
                        round(fairnessGap),
                        fairnessLevel,
                        round(centerDistance),
                        category.getLabel(),
                        reviewText
                );
    }

    private double round(double value) {
        return Math.round(value * 10.0) / 10.0;
    }
}
