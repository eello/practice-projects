package eello.notificationgeneration.service;

import eello.notificationgeneration.dto.request.NotificationGenerationRequestDTO;
import eello.notificationgeneration.dto.response.NotificationGenerationResponseDTO;
import eello.notificationgeneration.dto.response.NotificationResponseDTO;
import eello.notificationgeneration.entity.Notification;
import eello.notificationgeneration.repository.NotificationRepository;
import jakarta.persistence.PersistenceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationGenerationServiceImplTest {

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationGenerationServiceImpl notificationService;

    private NotificationGenerationRequestDTO requestDTO;

    @BeforeEach
    public void setUp() {
        requestDTO = NotificationGenerationRequestDTO.builder()
                .userId(1L)
                .title("this is title")
                .body("this is body")
                .requestAt(LocalDateTime.now())
                .build();
    }

    @Test
    @DisplayName("생성 실패: dto == null")
    public void fail_dto_is_null() {
        assertThatThrownBy(() -> notificationService.create(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("생성 실패: 데이터베이스에 저장 실패")
    public void fail_save() {
        // given
        when(notificationRepository.save(any(Notification.class))).thenThrow(PersistenceException.class);

        // when
        NotificationGenerationResponseDTO result = notificationService.create(requestDTO);

        // then
        assertThat(result.getUserId()).isEqualTo(requestDTO.getUserId());
        assertThat(result.getTitle()).isEqualTo(requestDTO.getTitle());
        assertThat(result.getBody()).isEqualTo(requestDTO.getBody());
        assertThat(result.getRequestAt()).isEqualTo(NotificationResponseDTO.formatRequestAt(requestDTO.getRequestAt()));
        assertThat(result.getCreated()).isFalse();
    }

    @Test
    @DisplayName("생성 성공")
    public void success_create() {
        // given
        Long notificationId = 1L;
        Notification saved = Notification.builder()
                .id(notificationId)
                .userId(requestDTO.getUserId())
                .title(requestDTO.getTitle())
                .body(requestDTO.getBody())
                .requestAt(requestDTO.getRequestAt())
                .build();

        when(notificationRepository.save(any(Notification.class))).thenReturn(saved);

        // when
        NotificationGenerationResponseDTO result = notificationService.create(requestDTO);

        // then
        assertThat(result.getId()).isEqualTo(notificationId);
        assertThat(result.getUserId()).isEqualTo(requestDTO.getUserId());
        assertThat(result.getTitle()).isEqualTo(requestDTO.getTitle());
        assertThat(result.getBody()).isEqualTo(requestDTO.getBody());
        assertThat(result.getRequestAt()).isEqualTo(NotificationResponseDTO.formatRequestAt(requestDTO.getRequestAt()));
        assertThat(result.getCreated()).isTrue();
        verify(notificationRepository, times(1)).save(any(Notification.class));

    }
}