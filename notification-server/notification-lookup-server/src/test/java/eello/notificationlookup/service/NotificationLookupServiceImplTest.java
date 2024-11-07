package eello.notificationlookup.service;

import eello.notificationlookup.dto.response.NotificationResponseDTO;
import eello.notificationlookup.entitiy.Notification;
import eello.notificationlookup.repository.NotificationRepository;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NotificationLookupServiceImplTest {

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationLookupServiceImpl lookupService;

    @Test
    @DisplayName("조회 실패: pageable is null")
    public void fail_lookup() {
        // given
        Long userId = 1L;
        
        // when and then
        assertThrows(IllegalArgumentException.class, () -> lookupService.lookup(userId, null));
    }

    @Test
    @DisplayName("조회 성공: 존재하지 않는 userId")
    public void success_lookup_not_exist_user() {
        // given
        Long userId = 1L;
        int page = 1;
        int size = 20;

        PageRequest pageRequest = PageRequest.of(0, size, Sort.by("requestAt"));
        PageImpl<Notification> notificationPage = new PageImpl<>(Collections.EMPTY_LIST, pageRequest, 0);
        when(notificationRepository.findByUserId(any(Long.class), any(Pageable.class))).thenReturn(notificationPage);

        Page<NotificationResponseDTO> expect = notificationPage.map(NotificationResponseDTO::from);

        // when
        Page<NotificationResponseDTO> result = lookupService.lookup(userId, pageRequest);

        // then
        assertThat(result.getTotalElements()).isEqualTo(expect.getTotalElements());
        assertThat(result.getContent()).isEmpty();
        assertThat(result.getSize()).isEqualTo(size);
        assertThat(result.getPageable().getPageNumber()).isEqualTo(page - 1);
    }

    @Test
    @DisplayName("조회 성공")
    public void success_lookup() {
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
        Page<NotificationResponseDTO> result = lookupService.lookup(userId, pageRequest);

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