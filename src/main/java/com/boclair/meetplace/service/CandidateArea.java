package com.boclair.meetplace.service;

import com.boclair.meetplace.domain.MeetingPurpose;

import java.util.List;
import java.util.Map;

record CandidateArea(
        String name,
        String description,
        double latitude,
        double longitude,
        Map<MeetingPurpose, List<String>> placeTypes
) {

    List<String> getPlaceTypes(MeetingPurpose purpose) {
        return placeTypes.getOrDefault(purpose, purpose.getRecommendedTypes());
    }
}
