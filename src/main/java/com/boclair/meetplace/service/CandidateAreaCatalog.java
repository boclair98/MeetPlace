package com.boclair.meetplace.service;

import com.boclair.meetplace.domain.MeetingPurpose;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class CandidateAreaCatalog {

    public List<CandidateArea> findAll() {
        return List.of(
                area("홍대입구", "카페, 술집, 공연장 선택지가 넓은 서북권 대표 약속지", 37.5572, 126.9245,
                        Map.of(
                                MeetingPurpose.CAFE, List.of("감성 카페", "디저트 카페", "루프탑 카페"),
                                MeetingPurpose.MEAL, List.of("퓨전 한식", "브런치", "라멘"),
                                MeetingPurpose.DRINK, List.of("이자카야", "펍", "와인바"),
                                MeetingPurpose.DATE, List.of("전시 공간", "소품샵", "분위기 좋은 식당")
                        )),
                area("신촌", "서대문, 마포, 여의도 쪽에서 만나기 좋은 대학가 상권", 37.5559, 126.9368,
                        Map.of(
                                MeetingPurpose.CAFE, List.of("스터디 카페", "디저트 카페", "대형 카페"),
                                MeetingPurpose.STUDY, List.of("스터디룸", "조용한 카페", "회의 가능한 카페"),
                                MeetingPurpose.MEAL, List.of("한식", "분식", "일식")
                        )),
                area("서울역", "서울 전역과 수도권 이동이 편한 교통 중심지", 37.5547, 126.9706,
                        Map.of(
                                MeetingPurpose.MEAL, List.of("한식", "양식", "호텔 뷔페"),
                                MeetingPurpose.CAFE, List.of("대형 카페", "프랜차이즈 카페", "호텔 라운지"),
                                MeetingPurpose.STUDY, List.of("회의실", "라운지", "조용한 카페")
                        )),
                area("종각", "강북과 강남 사이 균형이 좋고 직장인 약속에 강한 지역", 37.5702, 126.9830,
                        Map.of(
                                MeetingPurpose.MEAL, List.of("한식", "고깃집", "일식"),
                                MeetingPurpose.DRINK, List.of("이자카야", "맥주집", "와인바"),
                                MeetingPurpose.DATE, List.of("청계천 산책", "분위기 좋은 식당", "카페")
                        )),
                area("을지로입구", "직장인 모임과 감성 맛집을 함께 잡기 좋은 도심 상권", 37.5660, 126.9826,
                        Map.of(
                                MeetingPurpose.DRINK, List.of("노포", "와인바", "이자카야"),
                                MeetingPurpose.MEAL, List.of("한식", "파스타", "고깃집"),
                                MeetingPurpose.DATE, List.of("감성 맛집", "청계천 산책", "카페")
                        )),
                area("강남역", "강남권 접근성이 좋고 식사, 술자리 선택지가 많은 지역", 37.4979, 127.0276,
                        Map.of(
                                MeetingPurpose.CAFE, List.of("대형 카페", "디저트 카페", "브런치 카페"),
                                MeetingPurpose.MEAL, List.of("양식", "일식", "고깃집"),
                                MeetingPurpose.DRINK, List.of("펍", "이자카야", "칵테일바"),
                                MeetingPurpose.DATE, List.of("레스토랑", "와인바", "디저트 카페")
                        )),
                area("잠실", "동남권과 강동, 성남 쪽 접근성이 좋은 복합 상권", 37.5133, 127.1002,
                        Map.of(
                                MeetingPurpose.DATE, List.of("쇼핑몰", "석촌호수 산책", "레스토랑"),
                                MeetingPurpose.MEAL, List.of("패밀리 레스토랑", "일식", "한식"),
                                MeetingPurpose.CAFE, List.of("대형 카페", "호수뷰 카페", "디저트 카페")
                        )),
                area("건대입구", "동북권과 강남권 사이에서 캐주얼한 모임에 좋은 지역", 37.5404, 127.0692,
                        Map.of(
                                MeetingPurpose.MEAL, List.of("양꼬치", "중식", "고깃집"),
                                MeetingPurpose.DRINK, List.of("포차", "펍", "이자카야"),
                                MeetingPurpose.CAFE, List.of("디저트 카페", "대형 카페", "스터디 카페")
                        )),
                area("사당", "강남, 관악, 경기 남부가 만날 때 균형이 좋은 환승 지역", 37.4765, 126.9816,
                        Map.of(
                                MeetingPurpose.MEAL, List.of("고깃집", "한식", "이탈리안"),
                                MeetingPurpose.DRINK, List.of("맥주집", "이자카야", "포차"),
                                MeetingPurpose.STUDY, List.of("스터디룸", "조용한 카페", "회의실")
                        )),
                area("왕십리", "동북권, 성동, 강남 이동을 함께 고려하기 좋은 환승 지역", 37.5612, 127.0371,
                        Map.of(
                                MeetingPurpose.MEAL, List.of("한식", "일식", "고깃집"),
                                MeetingPurpose.CAFE, List.of("대형 카페", "디저트 카페", "조용한 카페"),
                                MeetingPurpose.DRINK, List.of("이자카야", "펍", "포차")
                        ))
        );
    }

    private CandidateArea area(
            String name,
            String description,
            double latitude,
            double longitude,
            Map<MeetingPurpose, List<String>> placeTypes
    ) {
        return new CandidateArea(name, description, latitude, longitude, placeTypes);
    }
}
