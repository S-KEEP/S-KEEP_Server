package Skeep.backend.user.service;

import Skeep.backend.global.ServiceTest;
import Skeep.backend.user.domain.EProvider;
import Skeep.backend.user.domain.EStatus;
import Skeep.backend.user.domain.User;
import Skeep.backend.user.domain.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("[Service Test] -> UserService")
class UserServiceTest extends ServiceTest {
    @Autowired
    UserRepository userRepository;

    @Nested
    @DisplayName("Apple 유저 생성")
    class saveAppleUser {
        @Test
        void Apple_유저를_성공적으로_저장하다() {
            // when
            Long userId = userService.saveAppleUser("1234567890", "Chaerin Yang");

            // then
            Optional<User> find_user = userRepository.findById(userId);
            assertAll(
                    () -> assertThat(find_user.get().getId()).isEqualTo(userId),
                    () -> assertThat(find_user.get().getAppleSerialId()).isEqualTo("1234567890"),
                    () -> assertThat(find_user.get().getName()).isEqualTo("Chaerin Yang"),
                    () -> assertThat(find_user.get().getProvider()).isEqualTo(EProvider.APPLE),
                    () -> assertThat(find_user.get().getStatus()).isEqualTo(EStatus.ACTIVATED)
            );
        }
    }
}
