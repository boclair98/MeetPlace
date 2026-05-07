package com.boclair.meetplace.domain;

import java.util.List;

public enum MeetingPurpose {
    CAFE("카페", List.of("대형 카페", "디저트 카페", "조용한 카페")),
    MEAL("식사", List.of("한식", "양식", "일식", "브런치")),
    DRINK("술자리", List.of("이자카야", "와인바", "펍", "포차")),
    STUDY("스터디", List.of("스터디룸", "조용한 카페", "공유 오피스")),
    DATE("데이트", List.of("분위기 좋은 식당", "전시 공간", "카페", "와인바"));

    private final String label;
    private final List<String> recommendedTypes;

    MeetingPurpose(String label, List<String> recommendedTypes) {
        this.label = label;
        this.recommendedTypes = recommendedTypes;
    }

    public String getLabel() {
        return label;
    }

    public List<String> getRecommendedTypes() {
        return recommendedTypes;
    }
}
