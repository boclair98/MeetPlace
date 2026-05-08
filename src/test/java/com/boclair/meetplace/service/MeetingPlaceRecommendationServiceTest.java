package com.boclair.meetplace.service;

import com.boclair.meetplace.domain.PlaceCategory;
import com.boclair.meetplace.dto.ParticipantRequest;
import com.boclair.meetplace.dto.RecommendationRequest;
import com.boclair.meetplace.dto.RecommendationResult;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MeetingPlaceRecommendationServiceTest {

    private final MeetingPlaceRecommendationService service = new MeetingPlaceRecommendationService(
            new FakePlaceSearchClient(),
            new DistanceCalculator()
    );

    @Test
    void recommendsRealPlacesBySelectedCategories() {
        RecommendationRequest request = new RecommendationRequest();
        request.setCategories(List.of(PlaceCategory.CAFE, PlaceCategory.KARAOKE));
        request.setParticipants(List.of(
                new ParticipantRequest("홍대", 37.5563, 126.9236),
                new ParticipantRequest("잠실", 37.5133, 127.1002)
        ));

        RecommendationResult result = service.recommend(request);

        assertThat(result.categoryRecommendations()).hasSize(2);
        assertThat(result.categoryRecommendations().get(0).category()).isEqualTo(PlaceCategory.CAFE);
        assertThat(result.categoryRecommendations().get(0).recommendations()).hasSize(3);
        assertThat(result.categoryRecommendations().get(0).recommendations().get(0).score()).isGreaterThan(0);
        assertThat(result.categoryRecommendations().get(0).recommendations().get(0).reason()).contains("카페");
        assertThat(result.categoryRecommendations().get(0).recommendations().get(0).rating()).isGreaterThan(0);
        assertThat(result.categoryRecommendations().get(0).recommendations().get(0).reviewCount()).isGreaterThan(0);
        assertThat(result.categoryRecommendations().get(0).recommendations().get(0).participantDistances()).hasSize(2);
    }

    private static class FakePlaceSearchClient implements PlaceSearchClient {

        @Override
        public List<ExternalPlace> search(PlaceCategory category, double centerLatitude, double centerLongitude, int limit) {
            return List.of(
                    place(category, "중간점 카페", centerLatitude, centerLongitude, 4.7, 830),
                    place(category, "역세권 카페", centerLatitude + 0.01, centerLongitude + 0.01, 4.5, 410),
                    place(category, "조용한 카페", centerLatitude - 0.01, centerLongitude - 0.01, 4.4, 260)
            );
        }

        private ExternalPlace place(
                PlaceCategory category,
                String name,
                double latitude,
                double longitude,
                double rating,
                int reviewCount
        ) {
            return new ExternalPlace(
                    name,
                    "서울 테스트 주소",
                    "https://map.example.com/place",
                    "Fake API",
                    latitude,
                    longitude,
                    rating,
                    reviewCount,
                    "단체로 방문하기 좋다는 리뷰가 많습니다.",
                    category.getDefaultPlaceTypes()
            );
        }
    }
}
