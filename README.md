# MeetPlace

여러 사람의 출발지를 기준으로 모두에게 덜 불공평한 약속 후보 지역을 추천하는 Spring Boot B2C 프로젝트입니다.

현재 MVP는 지도 API 없이 좌표 기반으로 동작합니다. 참가자들의 위치 중심점과 서울 주요 후보 지역을 비교하고, 평균 거리와 참가자 간 거리 편차를 함께 반영해 추천 순위를 계산합니다.

## 주요 기능

- 참가자 이름, 위도, 경도 입력
- 모임 목적 선택: 카페, 식사, 술자리, 스터디, 데이트
- 출발지들의 지리적 중심점 계산
- 서울 주요 후보 지역 추천
- 후보별 평균 거리, 최대 거리, 거리 편차 표시
- 목적별 추천 장소 타입 제공
- Thymeleaf 기반 웹 화면 제공

## 기술 스택

- Java 17
- Spring Boot 3.2.5
- Spring Web
- Spring Validation
- Thymeleaf
- Gradle

## 추천 방식

단순히 중간 좌표와 가까운 지역만 고르지 않고, 각 참가자에게 얼마나 공정한지도 함께 봅니다.

```text
score = 100
      - 평균 거리 패널티
      - 참가자 간 거리 편차 패널티
      - 중심점과 후보 지역 간 거리 패널티
```

예를 들어 한 후보지가 중심점과 가깝더라도 특정 참가자만 지나치게 멀다면 점수가 낮아집니다. 반대로 평균 거리와 편차가 모두 낮은 후보지가 더 높은 순위를 얻습니다.

## 프로젝트 구조

```text
src/main/java/com/boclair/meetplace
├── controller
│   └── HomeController.java
├── domain
│   └── MeetingPurpose.java
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

## 핵심 흐름

```text
사용자 입력
→ 참가자 좌표 검증
→ 출발지 중심 좌표 계산
→ 후보 지역별 참가자 거리 계산
→ 평균 거리와 편차 기반 점수 계산
→ 상위 후보 5개 반환
```

## 실행 방법

```bash
./gradlew bootRun
```

Windows PowerShell:

```powershell
.\gradlew.bat bootRun
```

기본 포트는 `8081`입니다.

```text
http://localhost:8081
```

## 테스트

```bash
./gradlew test
```

## 현재 한계

- 실제 주소 검색은 아직 지원하지 않습니다.
- 실제 대중교통 소요 시간이 아니라 좌표 기반 직선 거리를 사용합니다.
- 후보 지역은 서울 주요 상권 카탈로그에 기반합니다.
- 실제 식당/카페 검색 API는 아직 연결하지 않았습니다.

## 다음 고도화 방향

- Kakao Local API 또는 Naver Local API로 주소 검색 추가
- 지도 API로 실제 장소 후보 조회
- 대중교통 소요 시간 기반 공정성 계산
- 후보 장소 투표 기능
- 카카오톡 공유용 약속 카드 생성
- 사용자별 약속 히스토리 저장
- AI 기반 후보 비교 설명 생성

## 포트폴리오 포인트

이 프로젝트는 단순 CRUD보다 추천 로직을 설명하기 좋습니다.

- 거리 계산 알고리즘
- 추천 점수 설계
- 사용자 입력 검증
- 목적별 장소 타입 분류
- 지도/로컬 API 확장 가능성
- B2C 서비스 관점의 UX 구성
