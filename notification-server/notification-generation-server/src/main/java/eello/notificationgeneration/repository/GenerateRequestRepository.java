package eello.notificationgeneration.repository;

import eello.notificationgeneration.dto.request.NotificationGenerationRequestDTO;
import eello.notificationgeneration.entity.RequestInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class GenerateRequestRepository {

    private static final String REQUEST_ID_KEY = "request:id"; // 자동 증가 ID 키
    private static final String REQUEST_PREFIX = "request:";   // 요청 정보 키 접두사

    private final RedisTemplate<String, Object> redisTemplate;

    // 요청 정보 저장 메서드
    public Long saveRequest(RequestInfo requestInfo) {
        return saveRequest(null, requestInfo);
    }

    private Long saveRequest(Long requestId, RequestInfo requestInfo) {
        if (requestId == null) {
            requestId = redisTemplate.opsForValue().increment(REQUEST_ID_KEY); // Redis에서 ID 자동 증가
        }

        redisTemplate.opsForValue().set(REQUEST_PREFIX + requestId, requestInfo); // requestId - requestInfo 형태로 저장
        return requestId;
    }

    // 요청 정보 조회 메서드
    public RequestInfo getRequestInfo(Long requestId) {
        return (RequestInfo) redisTemplate.opsForValue().get(REQUEST_PREFIX + requestId);
    }

    public void successRequest(Long requestId) {
        RequestInfo requestInfo = getRequestInfo(requestId);

        if (requestInfo != null) {
            requestInfo.success();
            saveRequest(requestId, requestInfo);
        } else {
            log.warn("Request with id {} not found", requestId);
        }
    }
}
