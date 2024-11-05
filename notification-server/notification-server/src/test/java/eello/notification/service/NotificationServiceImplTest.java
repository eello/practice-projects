package eello.notification.service;

import eello.notification.dto.request.NotificationCreateRequestDTO;
import eello.notification.dto.response.NotificationCreateResponseDTO;
import eello.notification.dto.response.NotificationResponseDTO;
import eello.notification.entity.Notification;
import eello.notification.repository.NotificationRepository;
import jakarta.persistence.PersistenceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceImplTest {

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    private NotificationCreateRequestDTO requestDTO;

    @BeforeEach
    public void setUp() {
        requestDTO = NotificationCreateRequestDTO.builder()
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
        NotificationCreateResponseDTO result = notificationService.create(requestDTO);

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
        NotificationCreateResponseDTO result = notificationService.create(requestDTO);

        // then
        assertThat(result.getId()).isEqualTo(notificationId);
        assertThat(result.getUserId()).isEqualTo(requestDTO.getUserId());
        assertThat(result.getTitle()).isEqualTo(requestDTO.getTitle());
        assertThat(result.getBody()).isEqualTo(requestDTO.getBody());
        assertThat(result.getRequestAt()).isEqualTo(NotificationResponseDTO.formatRequestAt(requestDTO.getRequestAt()));
        assertThat(result.getCreated()).isTrue();
        verify(notificationRepository, times(1)).save(any(Notification.class));

    }

    @Test
    @DisplayName("조회 실패: page < 0")
    public void fail_page_less_than_0() {
        // given
        Long userId = 1L;
        int page = -1;
        int size = 20;

        // then
        assertThatThrownBy(() -> notificationService.find(userId, page, size))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("조회 실패: size <= 0")
    public void fail_size_less_then_1() {
        // given
        Long userId = 1L;
        int page = 0;
        int size = 0;

        // then
        assertThatThrownBy(() -> notificationService.find(userId, page, size))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("조회 성공: 존재하지 않는 userId")
    public void success_find_not_exist_user() {
        // given
        Long userId = 1L;
        int page = 1;
        int size = 20;

        PageRequest pageRequest = PageRequest.of(0, size, Sort.by("requestAt"));
        PageImpl<Notification> notificationPage = new PageImpl<>(Collections.EMPTY_LIST, pageRequest, 0);
        when(notificationRepository.findByUserId(any(Long.class), any(Pageable.class))).thenReturn(notificationPage);

        Page<NotificationResponseDTO> expect = notificationPage.map(NotificationResponseDTO::from);

        // when
        Page<NotificationResponseDTO> result = notificationService.find(userId, page, size);

        // then
        assertThat(result.getTotalElements()).isEqualTo(expect.getTotalElements());
        assertThat(result.getContent()).isEmpty();
        assertThat(result.getSize()).isEqualTo(size);
        assertThat(result.getPageable().getPageNumber()).isEqualTo(page - 1);
    }

    @Test
    @DisplayName("조회 성공")
    public void success_find() {
        // given
        Long userId = 1L;
        int page = 1;
        int size = 20;

        List<Notification> content = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        for (long i = 1; i <= 50; i++) {
            Notification noti = Notification.builder()
                    .id(i)
                    .userId(userId)
                    .title("title " + i)
                    .body("body " + i)
                    .requestAt(now.minusDays(i))
                    .build();

            content.add(noti);
        }

        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by("requestAt"));
        PageImpl<Notification> notificationPage = new PageImpl<>(content, pageRequest, content.size());
        when(notificationRepository.findByUserId(any(Long.class), any(Pageable.class))).thenReturn(notificationPage);

        Page<NotificationResponseDTO> expect = notificationPage.map(NotificationResponseDTO::from);

        // when
        Page<NotificationResponseDTO> result = notificationService.find(userId, page, size);

        // then
        assertThat(result.getTotalElements()).isEqualTo(expect.getTotalElements());
        assertThat(result.getContent()).isNotEmpty();
        assertThat(result.getSize()).isEqualTo(size);
        assertThat(result.getPageable().getPageNumber()).isEqualTo(page - 1);
        for (int i = 0; i < result.getTotalElements(); i++) {
            assertThat(result.getContent().get(i)).isEqualTo(expect.getContent().get(i));
        }
    }
}