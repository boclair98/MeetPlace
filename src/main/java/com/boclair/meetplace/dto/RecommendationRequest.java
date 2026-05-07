package com.boclair.meetplace.dto;

import com.boclair.meetplace.domain.MeetingPurpose;
import com.boclair.meetplace.domain.RecommendationMode;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

public class RecommendationRequest {

    @NotNull(message = "모임 목적을 선택해주세요.")
    private MeetingPurpose purpose = MeetingPurpose.CAFE;

    @NotNull(message = "추천 기준을 선택해주세요.")
    private RecommendationMode mode = RecommendationMode.BALANCED;

    @Valid
    @Size(min = 2, max = 8, message = "참가자는 2명 이상 8명 이하로 입력해주세요.")
    private List<ParticipantRequest> participants = new ArrayList<>();

    public RecommendationRequest() {
        participants.add(new ParticipantRequest("나", 37.5563, 126.9236));
        participants.add(new ParticipantRequest("친구", 37.5133, 127.1002));
    }

    public MeetingPurpose getPurpose() {
        return purpose;
    }

    public void setPurpose(MeetingPurpose purpose) {
        this.purpose = purpose;
    }

    public RecommendationMode getMode() {
        return mode;
    }

    public void setMode(RecommendationMode mode) {
        this.mode = mode;
    }

    public List<ParticipantRequest> getParticipants() {
        return participants;
    }

    public void setParticipants(List<ParticipantRequest> participants) {
        this.participants = participants;
    }
}
