package com.boclair.meetplace.dto;

import com.boclair.meetplace.domain.PlaceCategory;

import java.util.List;

public record CategoryRecommendation(
        PlaceCategory category,
        List<PlaceRecommendation> recommendations
) {
}
