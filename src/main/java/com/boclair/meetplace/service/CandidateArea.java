package com.boclair.meetplace.service;

import com.boclair.meetplace.domain.PlaceCategory;

import java.util.List;
import java.util.Map;

record CandidateArea(
        String name,
        String description,
        double latitude,
        double longitude,
        Map<PlaceCategory, List<String>> placeTypes
) {

    boolean supports(PlaceCategory category) {
        return placeTypes.containsKey(category);
    }

    List<String> getPlaceTypes(PlaceCategory category) {
        return placeTypes.getOrDefault(category, category.getDefaultPlaceTypes());
    }
}
