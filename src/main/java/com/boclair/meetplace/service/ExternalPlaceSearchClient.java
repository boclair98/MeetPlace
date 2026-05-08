package com.boclair.meetplace.service;

import com.boclair.meetplace.domain.PlaceCategory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class ExternalPlaceSearchClient implements PlaceSearchClient {

    private final RestClient restClient = RestClient.create();
    private final String kakaoRestApiKey;
    private final String googlePlacesApiKey;
    private final int searchRadiusMeters;

    public ExternalPlaceSearchClient(
            @Value("${meetplace.api.kakao.rest-key:}") String kakaoRestApiKey,
            @Value("${meetplace.api.google.places-key:}") String googlePlacesApiKey,
            @Value("${meetplace.api.search-radius-meters:3500}") int searchRadiusMeters
    ) {
        this.kakaoRestApiKey = kakaoRestApiKey;
        this.googlePlacesApiKey = googlePlacesApiKey;
        this.searchRadiusMeters = searchRadiusMeters;
    }

    @Override
    public List<ExternalPlace> search(
            PlaceCategory category,
            double centerLatitude,
            double centerLongitude,
            int limit
    ) {
        if (isBlank(kakaoRestApiKey)) {
            return List.of();
        }

        List<Map<String, Object>> documents = requestKakaoPlaces(category, centerLatitude, centerLongitude, limit);
        List<ExternalPlace> places = new ArrayList<>();

        for (Map<String, Object> document : documents) {
            double latitude = parseDouble(document.get("y"));
            double longitude = parseDouble(document.get("x"));
            String name = text(document.get("place_name"));
            String address = firstNonBlank(text(document.get("road_address_name")), text(document.get("address_name")));
            String url = text(document.get("place_url"));
            PlaceReviewSummary review = requestGoogleReview(name, address, latitude, longitude);

            places.add(new ExternalPlace(
                    name,
                    address,
                    url,
                    "Kakao Local" + (review.reviewCount() > 0 ? " + Google Reviews" : ""),
                    latitude,
                    longitude,
                    review.rating(),
                    review.reviewCount(),
                    review.reviewSnippet(),
                    category.getDefaultPlaceTypes()
            ));
        }

        return places;
    }

    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> requestKakaoPlaces(
            PlaceCategory category,
            double centerLatitude,
            double centerLongitude,
            int limit
    ) {
        try {
            Map<String, Object> response = restClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .scheme("https")
                            .host("dapi.kakao.com")
                            .path("/v2/local/search/keyword.json")
                            .queryParam("query", searchKeyword(category))
                            .queryParam("x", centerLongitude)
                            .queryParam("y", centerLatitude)
                            .queryParam("radius", searchRadiusMeters)
                            .queryParam("size", Math.min(limit, 15))
                            .queryParam("sort", "accuracy")
                            .build())
                    .header("Authorization", "KakaoAK " + kakaoRestApiKey)
                    .retrieve()
                    .body(new ParameterizedTypeReference<Map<String, Object>>() {
                    });

            if (response == null || !(response.get("documents") instanceof List<?> documents)) {
                return List.of();
            }
            return (List<Map<String, Object>>) documents;
        } catch (RestClientException exception) {
            return List.of();
        }
    }

    private PlaceReviewSummary requestGoogleReview(
            String name,
            String address,
            double latitude,
            double longitude
    ) {
        if (isBlank(googlePlacesApiKey) || isBlank(name)) {
            return PlaceReviewSummary.empty();
        }

        String placeId = findGooglePlaceId(name, address, latitude, longitude);
        if (isBlank(placeId)) {
            return PlaceReviewSummary.empty();
        }

        return requestGoogleDetails(placeId);
    }

    @SuppressWarnings("unchecked")
    private String findGooglePlaceId(String name, String address, double latitude, double longitude) {
        try {
            Map<String, Object> response = restClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .scheme("https")
                            .host("maps.googleapis.com")
                            .path("/maps/api/place/textsearch/json")
                            .queryParam("query", name + " " + address)
                            .queryParam("location", latitude + "," + longitude)
                            .queryParam("radius", 120)
                            .queryParam("language", "ko")
                            .queryParam("key", googlePlacesApiKey)
                            .build())
                    .retrieve()
                    .body(new ParameterizedTypeReference<Map<String, Object>>() {
                    });

            if (response == null || !(response.get("results") instanceof List<?> results) || results.isEmpty()) {
                return "";
            }

            Map<String, Object> first = (Map<String, Object>) results.get(0);
            return text(first.get("place_id"));
        } catch (RestClientException exception) {
            return "";
        }
    }

    @SuppressWarnings("unchecked")
    private PlaceReviewSummary requestGoogleDetails(String placeId) {
        try {
            Map<String, Object> response = restClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .scheme("https")
                            .host("maps.googleapis.com")
                            .path("/maps/api/place/details/json")
                            .queryParam("place_id", placeId)
                            .queryParam("fields", "rating,user_ratings_total,reviews")
                            .queryParam("language", "ko")
                            .queryParam("key", googlePlacesApiKey)
                            .build())
                    .retrieve()
                    .body(new ParameterizedTypeReference<Map<String, Object>>() {
                    });

            if (response == null || !(response.get("result") instanceof Map<?, ?> rawResult)) {
                return PlaceReviewSummary.empty();
            }

            Map<String, Object> result = (Map<String, Object>) rawResult;
            double rating = parseDouble(result.get("rating"));
            int reviewCount = parseInt(result.get("user_ratings_total"));
            String snippet = "";

            if (result.get("reviews") instanceof List<?> reviews && !reviews.isEmpty()) {
                Map<String, Object> firstReview = (Map<String, Object>) reviews.get(0);
                snippet = text(firstReview.get("text"));
            }

            return new PlaceReviewSummary(rating, reviewCount, snippet);
        } catch (RestClientException exception) {
            return PlaceReviewSummary.empty();
        }
    }

    private String searchKeyword(PlaceCategory category) {
        return switch (category) {
            case CAFE -> "카페";
            case RESTAURANT -> "맛집";
            case PC_ROOM -> "PC방";
            case KARAOKE -> "노래방";
            case PUB -> "술집";
            case STUDY_ROOM -> "스터디룸";
            case MOVIE -> "영화관";
            case BOWLING -> "볼링장";
        };
    }

    private String firstNonBlank(String first, String second) {
        return isBlank(first) ? second : first;
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }

    private String text(Object value) {
        return value == null ? "" : String.valueOf(value);
    }

    private double parseDouble(Object value) {
        if (value == null) {
            return 0.0;
        }
        if (value instanceof Number number) {
            return number.doubleValue();
        }
        try {
            return Double.parseDouble(String.valueOf(value));
        } catch (NumberFormatException exception) {
            return 0.0;
        }
    }

    private int parseInt(Object value) {
        if (value == null) {
            return 0;
        }
        if (value instanceof Number number) {
            return number.intValue();
        }
        try {
            return Integer.parseInt(String.valueOf(value));
        } catch (NumberFormatException exception) {
            return 0;
        }
    }
}
