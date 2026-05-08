package com.boclair.meetplace.service;

import java.util.List;

public record ExternalPlace(
        String name,
        String address,
        String url,
        String source,
        double latitude,
        double longitude,
        double rating,
        int reviewCount,
        String reviewSnippet,
        List<String> placeTypes
) {
}
