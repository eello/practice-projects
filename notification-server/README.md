# Notification Server

[**if(kakao) 2022 알림 서비스로 시작하는 서버 개발**](https://www.youtube.com/watch?v=CmTO68I2HSc)를 보고 기존 아키텍처부터 시작해 영상과 같이 발전시켜 나가는 프로젝트

## 스택

- Java 21
- SpringBoot 3.3.5
- MySQL 8.0
- H2 Database
- Docker Images
  - RabbitMQ 3
  - Redis 7.4

### 1단계

- [x] `client - (server ↔ database)`의 단순 구조
  - [x] 알림 생성
  - [x] 알림 조회

### 2단계

- [x] 알림 생성 서버와 조회 서버 분리

### 3단계

- [ ] 알림 생성에 비동기 적용
  - [x] rabbitmq 적용
  - [x] 메시지 발행 전 redis에 요청 정보 저장
  - [ ] 테스트 코드
  - [ ] 예외 발생 상황

### 4단계

- [ ] 데이터베이스 분리 및 파티셔닝
- [ ] 기간이 지난 데이터 이관 배치
