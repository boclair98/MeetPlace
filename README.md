# MeetPlace

여러 명이 만날 때 출발지와 원하는 장소 카테고리를 기준으로 중간 약속 지역을 추천하는 Spring Boot 웹 서비스입니다.

## 주요 기능

- 참가자 2명부터 8명까지 출발 좌표 입력
- 카페, 맛집, PC방, 노래방, 술집, 스터디룸, 영화관, 볼링장 등 여러 카테고리 선택
- 선택한 카테고리별 추천 지역 Top 3 제공
- 평균 이동거리, 최대 이동거리, 거리 편차, 예상 이동시간, 공정성 표시
- 추천 기준 선택: 균형형, 짧은 이동 우선, 공정성 우선
- 결과 후보 선택 후 약속 공유 문구 생성
- 현재는 서울 주요 권역 후보 데이터를 기반으로 외부 API 없이 실행 가능

## 화면 흐름

1. 추천 기준을 선택합니다.
2. 원하는 장소 카테고리를 고릅니다.
3. 참가자별 출발 지역을 프리셋으로 선택하거나 위도와 경도를 직접 입력합니다.
4. 추천 버튼을 누르면 카테고리별 후보 지역이 계산됩니다.
5. 결과 탭에서 카테고리를 바꿔 보며 후보를 비교합니다.
6. 마음에 드는 후보를 선택하면 공유 문구가 만들어집니다.

## 추천 방식

MeetPlace는 참가자들의 출발 좌표를 기준으로 중심 좌표를 계산하고, 후보 지역별 이동 부담을 점수화합니다.

- 평균 이동거리: 전체 참가자가 평균적으로 얼마나 이동해야 하는지
- 최대 이동거리: 가장 멀리 이동하는 참가자의 부담
- 거리 편차: 참가자 간 이동거리 차이
- 중심 좌표 거리: 계산된 중간 좌표와 후보 지역의 차이
- 카테고리 적합도: 선택한 업종에 적합한 후보 지역인지

선택한 추천 기준에 따라 각 항목의 가중치를 다르게 적용해 최종 점수를 계산합니다.

## 실행

```bash
./gradlew bootRun
```

Windows에서는 다음 명령을 사용할 수 있습니다.

```bash
gradlew.bat bootRun
```

브라우저에서 `http://localhost:8080`으로 접속합니다.

## 주요 구조

```text
src/main/java/com/boclair/meetplace
├─ controller
│  └─ HomeController.java
├─ domain
│  ├─ PlaceCategory.java
│  └─ RecommendationMode.java
├─ dto
│  ├─ CategoryRecommendation.java
│  ├─ ParticipantRequest.java
│  ├─ PlaceRecommendation.java
│  ├─ RecommendationRequest.java
│  └─ RecommendationResult.java
└─ service
   ├─ CandidateAreaCatalog.java
   ├─ DistanceCalculator.java
   └─ MeetingPlaceRecommendationService.java
```

## 이후 확장 방향

- Kakao Local API 또는 Naver Local API로 실제 매장 목록 조회
- 지도 SDK를 붙여 후보 지역과 참가자 출발지를 지도 위에 표시
- 대중교통, 도보, 차량 기준 예상 이동시간 계산
- 사용자 로그인 후 즐겨찾기 장소와 최근 약속 기록 저장
- 실제 매장 영업 여부, 평점, 혼잡도, 가격대 반영

외부 API 키는 코드에 직접 넣지 않고 환경 변수나 별도 설정 파일로 관리하는 방식이 좋습니다.