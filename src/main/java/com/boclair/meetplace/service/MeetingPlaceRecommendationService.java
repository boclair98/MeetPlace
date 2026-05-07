package com.boclair.meetplace.service;

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

    private final CandidateAreaCatalog candidateAreaCatalog;
    private final DistanceCalculator distanceCalculator;

    public MeetingPlaceRecommendationService(
            CandidateAreaCatalog candidateAreaCatalog,
            DistanceCalculator distanceCalculator
    ) {
        this.candidateAreaCatalog = candidateAreaCatalog;
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

        List<PlaceRecommendation> recommendations = candidateAreaCatalog.findAll()
                .stream()
                .map(area -> score(area, participants, centerLatitude, centerLongitude, request))
                .sorted(Comparator.comparing(PlaceRecommendation::score).reversed())
                .limit(5)
                .toList();

        return new RecommendationResult(centerLatitude, centerLongitude, recommendations);
    }

    private PlaceRecommendation score(
            CandidateArea area,
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
                                area.latitude(),
                                area.longitude()
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
                area.latitude(),
                area.longitude()
        );

        int score = calculateScore(averageDistance, fairnessGap, centerDistance, request);
        String fairnessLevel = getFairnessLevel(fairnessGap);
        int estimatedTravelMinutes = estimateTravelMinutes(averageDistance);
        String reason = buildReason(averageDistance, fairnessGap, centerDistance, request, fairnessLevel);

        return new PlaceRecommendation(
                area.name(),
                area.description(),
                area.latitude(),
                area.longitude(),
                round(averageDistance),
                round(stats.getMax()),
                round(fairnessGap),
                round(centerDistance),
                estimatedTravelMinutes,
                fairnessLevel,
                reason,
                score,
                area.getPlaceTypes(request.getPurpose()),
                distances
        );
    }

    private int calculateScore(
            double averageDistance,
            double fairnessGap,
            double centerDistance,
            RecommendationRequest request
    ) {
        double rawScore = 100
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
            double averageDistance,
            double fairnessGap,
            double centerDistance,
            RecommendationRequest request,
            String fairnessLevel
    ) {
        return "%s 기준에서 평균 이동거리 %.1fkm, 거리 편차 %.1fkm로 %s 수준입니다. 중심점과 %.1fkm 떨어져 있고, %s 모임에 맞는 장소 선택지가 있습니다."
                .formatted(
                        request.getMode().getLabel(),
                        round(averageDistance),
                        round(fairnessGap),
                        fairnessLevel,
                        round(centerDistance),
                        request.getPurpose().getLabel()
                );
    }

    private double round(double value) {
        return Math.round(value * 10.0) / 10.0;
    }
}
