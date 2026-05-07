package com.boclair.meetplace.dto;

import java.util.List;

public record PlaceRecommendation(
        String areaName,
        String description,
        double latitude,
        double longitude,
        double averageDistanceKm,
        double maxDistanceKm,
        double fairnessGapKm,
        int score,
        List<String> recommendedPlaceTypes,
        List<ParticipantDistance> participantDistances
) {
}
