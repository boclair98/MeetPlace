# MeetPlace

MeetPlace는 여러 사람의 출발지를 기준으로 약속 후보 지역을 추천하는 Spring Boot 웹 서비스입니다.

현재 버전은 지도 API 없이 좌표 기반으로 동작합니다. 참가자들의 위치 중심점, 후보 지역까지의 평균 거리, 참가자 간 거리 편차를 함께 계산해서 약속 장소 후보를 정렬합니다.

## 기능

- 참가자 이름, 위도, 경도 입력
- 모임 목적 선택
  - 카페
  - 식사
  - 술자리
  - 스터디
  - 데이트
- 추천 기준 선택
  - 균형형
  - 짧은 이동 우선
  - 공정성 우선
- 서울 주요 후보 지역 추천
- 후보별 추천 점수 표시
- 후보별 추천 이유 표시
- 평균 거리, 최대 거리, 거리 편차, 중심점 거리 표시
- 예상 이동 시간 표시
- 목적별 추천 장소 타입 표시

## 추천 기준

### 균형형

평균 거리, 거리 편차, 중심점과의 거리를 모두 적당히 반영합니다.

### 짧은 이동 우선

전체 평균 이동거리를 더 강하게 반영합니다. 가까운 후보가 더 높은 점수를 받습니다.

### 공정성 우선

참가자 간 거리 편차를 더 강하게 반영합니다. 특정 사람만 지나치게 멀어지는 후보를 낮게 평가합니다.

## 추천 점수

```text
score = 100
      - 평균 거리 패널티
      - 참가자 간 거리 편차 패널티
      - 중심점과 후보 지역 간 거리 패널티
```

추천 기준에 따라 각 패널티의 가중치가 달라집니다.

## 프로젝트 구조

```text
src/main/java/com/boclair/meetplace
├── controller
│   └── HomeController.java
├── domain
│   ├── MeetingPurpose.java
│   └── RecommendationMode.java
├── dto
│   ├── ParticipantDistance.java
│   ├── ParticipantRequest.java
│   ├── PlaceRecommendation.java
│   ├── RecommendationRequest.java
│   └── RecommendationResult.java
├── service
│   ├── CandidateArea.java
│   ├── CandidateAreaCatalog.java
│   ├── DistanceCalculator.java
│   └── MeetingPlaceRecommendationService.java
└── MeetPlaceApplication.java
```

## 실행

```bash
gradle bootRun
```

```text
http://localhost:8081
```

## 테스트

```bash
gradle test
```

## 다음 개발

- 주소 검색 API 연결
- 실제 장소 검색 API 연결
- 대중교통 소요 시간 기반 추천
- 참가자 추가/삭제 UI
- 후보 지역 투표
- 약속 공유 카드 생성
