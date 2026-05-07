package com.boclair.meetplace.service;

import com.boclair.meetplace.domain.MeetingPurpose;
import com.boclair.meetplace.dto.ParticipantRequest;
import com.boclair.meetplace.dto.RecommendationRequest;
import com.boclair.meetplace.dto.RecommendationResult;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MeetingPlaceRecommendationServiceTest {

    private final MeetingPlaceRecommendationService service = new MeetingPlaceRecommendationService(
            new CandidateAreaCatalog(),
            new DistanceCalculator()
    );

    @Test
    void recommendsCandidateAreasByDistanceAndFairness() {
        RecommendationRequest request = new RecommendationRequest();
        request.setPurpose(MeetingPurpose.CAFE);
        request.setParticipants(List.of(
                new ParticipantRequest("홍대", 37.5563, 126.9236),
                new ParticipantRequest("잠실", 37.5133, 127.1002)
        ));

        RecommendationResult result = service.recommend(request);

        assertThat(result.recommendations()).hasSize(5);
        assertThat(result.recommendations().get(0).score()).isGreaterThan(0);
        assertThat(result.recommendations().get(0).participantDistances()).hasSize(2);
    }
}
