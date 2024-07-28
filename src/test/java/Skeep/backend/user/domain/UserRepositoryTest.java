package Skeep.backend.user.domain;

import Skeep.backend.global.RepositoryTest;
import Skeep.backend.user.dto.UserSecurityForm;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("[Repository Layer] -> UserRepository 테스트")
class UserRepositoryTest extends RepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void findUserSecurityFromByAppleSerialId() {
        // given
        Long userId = userRepository.save(User.createAppleUser("1234567890", "Chaerin Yang")).getId();

        // when
        Optional<UserSecurityForm> find_user_security_form = userRepository.findUserSecurityFromByAppleSerialId("1234567890");

        // then
        assertThat(find_user_security_form.get().getId()).isEqualTo(userId);
    }

    @Test
    void findUserSecurityFromById() {
        // given
        Long userId = userRepository.save(User.createAppleUser("1234567890", "Chaerin Yang")).getId();

        // when
        Optional<UserSecurityForm> find_user_security_form = userRepository.findUserSecurityFromById(userId);

        // then
        assertThat(find_user_security_form.get().getId()).isEqualTo(userId);
    }

    @Test
    void findByAppleSerialId() {
        // given
        Long userId = userRepository.save(User.createAppleUser("1234567890", "Chaerin Yang")).getId();

        // when
        Optional<User> find_user = userRepository.findByAppleSerialId("1234567890");

        // then
        assertAll(
                () -> assertThat(find_user.get().getId()).isEqualTo(userId),
                () -> assertThat(find_user.get().getAppleSerialId()).isEqualTo("1234567890"),
                () -> assertThat(find_user.get().getName()).isEqualTo("Chaerin Yang"),
                () -> assertThat(find_user.get().getProvider()).isEqualTo(EProvider.APPLE),
                () -> assertThat(find_user.get().getStatus()).isEqualTo(EStatus.ACTIVATED)
        );
    }
}