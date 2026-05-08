package com.boclair.meetplace.service;

public record PlaceReviewSummary(
        double rating,
        int reviewCount,
        String reviewSnippet
) {
    public static PlaceReviewSummary empty() {
        return new PlaceReviewSummary(0.0, 0, "");
    }
}
