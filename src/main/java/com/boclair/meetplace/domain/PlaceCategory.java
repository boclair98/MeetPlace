package com.boclair.meetplace.domain;

import java.util.List;

public enum PlaceCategory {
    CAFE("카페", "대화하기 좋은 카페", List.of("대형 카페", "디저트 카페", "루프탑 카페")),
    RESTAURANT("맛집", "식사 중심의 만남", List.of("한식", "양식", "일식", "고깃집")),
    PC_ROOM("피시방", "게임과 가벼운 간식", List.of("프리미엄 PC방", "게이밍 라운지")),
    KARAOKE("노래방", "가볍게 놀기 좋은 코스", List.of("코인 노래방", "일반 노래방")),
    PUB("술집", "저녁 약속과 2차", List.of("이자카야", "맥주집", "와인바")),
    STUDY_ROOM("스터디룸", "조용한 모임과 회의", List.of("스터디룸", "회의실", "조용한 카페")),
    MOVIE("영화관", "영화 전후 동선", List.of("멀티플렉스", "영화관", "쇼핑몰")),
    BOWLING("볼링장", "액티비티 약속", List.of("볼링장", "오락실", "복합 놀이공간"));

    private final String label;
    private final String description;
    private final List<String> defaultPlaceTypes;

    PlaceCategory(String label, String description, List<String> defaultPlaceTypes) {
        this.label = label;
        this.description = description;
        this.defaultPlaceTypes = defaultPlaceTypes;
    }

    public String getLabel() {
        return label;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getDefaultPlaceTypes() {
        return defaultPlaceTypes;
    }
}
