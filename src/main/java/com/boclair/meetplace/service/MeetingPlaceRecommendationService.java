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

        int score = calculateScore(averageDistance, fairnessGap, centerDistance);

        return new PlaceRecommendation(
                area.name(),
                area.description(),
                area.latitude(),
                area.longitude(),
                round(averageDistance),
                round(stats.getMax()),
                round(fairnessGap),
                score,
                area.getPlaceTypes(request.getPurpose()),
                distances
        );
    }

    private int calculateScore(double averageDistance, double fairnessGap, double centerDistance) {
        double rawScore = 100
                - averageDistance * 2.1
                - fairnessGap * 3.2
                - centerDistance * 4.0;
        return Math.max(0, Math.min(100, (int) Math.round(rawScore)));
    }

    private double round(double value) {
        return Math.round(value * 10.0) / 10.0;
    }
}
