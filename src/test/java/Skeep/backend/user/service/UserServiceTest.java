package Skeep.backend.user.service;

import Skeep.backend.global.ServiceTest;
import Skeep.backend.user.domain.EProvider;
import Skeep.backend.user.domain.EStatus;
import Skeep.backend.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("[Service Layer] -> UserService")
class UserServiceTest extends ServiceTest {
    @Autowired
    private UserService userService;

    @Nested
    @DisplayName("Apple 유저 생성")
    class saveAppleUser {
        @Test
        void Apple_유저를_성공적으로_저장하다() {
            // when
            User user = userService.saveAppleUser("1234567890", "Chaerin Yang", "abcdegf@gmail.com");

            // then
            Optional<User> find_user = userRepository.findById(user.getId());
            assertAll(
                    () -> assertThat(find_user.get().getId()).isEqualTo(user.getId()),
                    () -> assertThat(find_user.get().getSerialId()).isEqualTo("1234567890"),
                    () -> assertThat(find_user.get().getName()).isEqualTo("Chaerin Yang"),
                    () -> assertThat(find_user.get().getEmail().getEmail()).isEqualTo("abcdegf@gmail.com"),
                    () -> assertThat(find_user.get().getProvider()).isEqualTo(EProvider.APPLE),
                    () -> assertThat(find_user.get().getStatus()).isEqualTo(EStatus.ACTIVATED)
            );
        }
    }
}
