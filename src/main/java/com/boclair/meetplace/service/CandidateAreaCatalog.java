package com.boclair.meetplace.service;

import com.boclair.meetplace.domain.PlaceCategory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static com.boclair.meetplace.domain.PlaceCategory.BOWLING;
import static com.boclair.meetplace.domain.PlaceCategory.CAFE;
import static com.boclair.meetplace.domain.PlaceCategory.KARAOKE;
import static com.boclair.meetplace.domain.PlaceCategory.MOVIE;
import static com.boclair.meetplace.domain.PlaceCategory.PC_ROOM;
import static com.boclair.meetplace.domain.PlaceCategory.PUB;
import static com.boclair.meetplace.domain.PlaceCategory.RESTAURANT;
import static com.boclair.meetplace.domain.PlaceCategory.STUDY_ROOM;

@Component
public class CandidateAreaCatalog {

    public List<CandidateArea> findAll() {
        return List.of(
                area("홍대입구", "카페, 맛집, 노래방, PC방 선택지가 촘촘한 서북권 대표 약속지", 37.5572, 126.9245,
                        Map.of(
                                CAFE, List.of("디저트 카페", "대형 카페", "루프탑 카페"),
                                RESTAURANT, List.of("연남동 맛집", "브런치", "일식"),
                                PC_ROOM, List.of("게이밍 PC방", "프리미엄 PC방"),
                                KARAOKE, List.of("코인 노래방", "일반 노래방"),
                                PUB, List.of("이자카야", "맥주집", "와인바"),
                                MOVIE, List.of("상영관", "복합 문화공간")
                        )),
                area("신촌", "서대문, 마포, 여의도 쪽에서 모이기 좋은 대학가 상권", 37.5559, 126.9368,
                        Map.of(
                                CAFE, List.of("스터디 카페", "디저트 카페", "대형 카페"),
                                RESTAURANT, List.of("한식", "분식", "일식"),
                                PC_ROOM, List.of("대학가 PC방", "게이밍 PC방"),
                                KARAOKE, List.of("코인 노래방", "일반 노래방"),
                                STUDY_ROOM, List.of("스터디룸", "조용한 카페", "회의 가능 카페")
                        )),
                area("서울역", "서울 전역과 수도권 이동이 편한 교통 중심지", 37.5547, 126.9706,
                        Map.of(
                                CAFE, List.of("대형 카페", "프랜차이즈 카페", "호텔 라운지"),
                                RESTAURANT, List.of("한식", "양식", "호텔 뷔페"),
                                STUDY_ROOM, List.of("회의실", "라운지", "조용한 카페"),
                                MOVIE, List.of("멀티플렉스", "쇼핑몰")
                        )),
                area("종각", "강북과 강남 사이 균형이 좋고 직장인 약속이 강한 지역", 37.5702, 126.9830,
                        Map.of(
                                CAFE, List.of("대형 카페", "디저트 카페"),
                                RESTAURANT, List.of("한식", "고깃집", "일식"),
                                PUB, List.of("이자카야", "맥주집", "와인바"),
                                KARAOKE, List.of("일반 노래방", "코인 노래방"),
                                STUDY_ROOM, List.of("회의실", "스터디룸")
                        )),
                area("을지로입구", "직장인 모임과 감성 맛집을 함께 잡기 좋은 도심 상권", 37.5660, 126.9826,
                        Map.of(
                                CAFE, List.of("감성 카페", "프랜차이즈 카페"),
                                RESTAURANT, List.of("한식", "파스타", "고깃집"),
                                PUB, List.of("힙한 포차", "와인바", "이자카야"),
                                KARAOKE, List.of("코인 노래방", "일반 노래방"),
                                MOVIE, List.of("멀티플렉스", "쇼핑몰")
                        )),
                area("강남역", "강남권 접근성이 좋고 식사, 술자리, 놀이 선택지가 많은 지역", 37.4979, 127.0276,
                        Map.of(
                                CAFE, List.of("대형 카페", "디저트 카페", "브런치 카페"),
                                RESTAURANT, List.of("양식", "일식", "고깃집"),
                                PC_ROOM, List.of("프리미엄 PC방", "게이밍 라운지"),
                                KARAOKE, List.of("코인 노래방", "일반 노래방"),
                                PUB, List.of("바", "이자카야", "칵테일바"),
                                BOWLING, List.of("볼링장", "오락실")
                        )),
                area("잠실", "동남권과 강동, 성남 쪽 접근성이 좋은 복합 상권", 37.5133, 127.1002,
                        Map.of(
                                CAFE, List.of("대형 카페", "호수뷰 카페", "디저트 카페"),
                                RESTAURANT, List.of("패밀리 레스토랑", "일식", "한식"),
                                MOVIE, List.of("멀티플렉스", "쇼핑몰", "문화공간"),
                                BOWLING, List.of("볼링장", "오락실")
                        )),
                area("건대입구", "동북권과 강남권 사이에서 캐주얼한 모임에 좋은 지역", 37.5404, 127.0692,
                        Map.of(
                                CAFE, List.of("디저트 카페", "대형 카페", "스터디 카페"),
                                RESTAURANT, List.of("양꼬치", "중식", "고깃집"),
                                PC_ROOM, List.of("게이밍 PC방", "프리미엄 PC방"),
                                KARAOKE, List.of("코인 노래방", "일반 노래방"),
                                PUB, List.of("포차", "바", "이자카야"),
                                BOWLING, List.of("볼링장", "오락실")
                        )),
                area("사당", "강남, 관악, 경기 남부가 만날 때 균형이 좋은 환승 지역", 37.4765, 126.9816,
                        Map.of(
                                CAFE, List.of("프랜차이즈 카페", "조용한 카페"),
                                RESTAURANT, List.of("고깃집", "한식", "이탈리안"),
                                KARAOKE, List.of("일반 노래방", "코인 노래방"),
                                PUB, List.of("맥주집", "이자카야", "포차"),
                                STUDY_ROOM, List.of("스터디룸", "회의실")
                        )),
                area("왕십리", "동북권, 성동, 강남 이동을 함께 고려하기 좋은 환승 지역", 37.5612, 127.0371,
                        Map.of(
                                CAFE, List.of("대형 카페", "디저트 카페", "조용한 카페"),
                                RESTAURANT, List.of("한식", "일식", "고깃집"),
                                PC_ROOM, List.of("게이밍 PC방", "프리미엄 PC방"),
                                KARAOKE, List.of("코인 노래방", "일반 노래방"),
                                MOVIE, List.of("멀티플렉스", "쇼핑몰")
                        ))
        );
    }

    private CandidateArea area(
            String name,
            String description,
            double latitude,
            double longitude,
            Map<PlaceCategory, List<String>> placeTypes
    ) {
        return new CandidateArea(name, description, latitude, longitude, placeTypes);
    }
}
