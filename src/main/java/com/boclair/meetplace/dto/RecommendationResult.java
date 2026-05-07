package com.boclair.meetplace.dto;

import java.util.List;

public record RecommendationResult(
        double centerLatitude,
        double centerLongitude,
        List<PlaceRecommendation> recommendations
) {
}
