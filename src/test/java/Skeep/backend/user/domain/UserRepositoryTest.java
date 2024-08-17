package Skeep.backend.user.domain;

import Skeep.backend.global.RepositoryTest;
import Skeep.backend.user.dto.UserSecurityForm;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("[Repository Layer] -> UserRepository 테스트")
class UserRepositoryTest extends RepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void findUserSecurityFromByAppleSerialId() {
        // given
        Long userId = userRepository.save(User.createAppleUser("1234567890", "Chaerin Yang", Email.createEmail("abcdegf@gmail.com"), ERole.USER)).getId();

        // when
        Optional<UserSecurityForm> find_user_security_form = userRepository.findUserSecurityFromBySerialId("1234567890");

        // then
        assertThat(find_user_security_form.get().getId()).isEqualTo(userId);
    }

    @Test
    void findUserSecurityFromById() {
        // given
        Long userId = userRepository.save(User.createAppleUser("1234567890", "Chaerin Yang", Email.createEmail("abcdegf@gmail.com"), ERole.USER)).getId();

        // when
        Optional<UserSecurityForm> find_user_security_form = userRepository.findUserSecurityFromById(userId);

        // then
        assertThat(find_user_security_form.get().getId()).isEqualTo(userId);
    }

    @Test
    void findByAppleSerialId() {
        // given
        Long userId = userRepository.save(User.createAppleUser("1234567890", "Chaerin Yang", Email.createEmail("abcdegf@gmail.com"), ERole.USER)).getId();

        // when
        Optional<User> find_user = userRepository.findBySerialId("1234567890");

        // then
        assertAll(
                () -> assertThat(find_user.get().getId()).isEqualTo(userId),
                () -> assertThat(find_user.get().getSerialId()).isEqualTo("1234567890"),
                () -> assertThat(find_user.get().getName()).isEqualTo("Chaerin Yang"),
                () -> assertThat(find_user.get().getEmail().getEmail()).isEqualTo("abcdegf@gmail.com"),
                () -> assertThat(find_user.get().getProvider()).isEqualTo(EProvider.APPLE),
                () -> assertThat(find_user.get().getStatus()).isEqualTo(EStatus.ACTIVATED)
        );
    }

    @Test
    void deleteUsersNotModifiedSince() {
        // given
        User userToDelete1 = User.createAppleUser("1234567891", "User To Delete 1", Email.createEmail("user1@gmail.com"), ERole.USER);
        User userToDelete2 = User.createAppleUser("1234567892", "User To Delete 2", Email.createEmail("user2@gmail.com"), ERole.USER);
        User userToKeep = User.createAppleUser("1234567893", "User To Keep", Email.createEmail("user3@gmail.com"), ERole.USER);
        User userNotAffected = User.createAppleUser("1234567894", "User Not Affected", Email.createEmail("user4@gmail.com"), ERole.USER);

        userRepository.saveAll(Arrays.asList(userToDelete1, userToDelete2, userToKeep, userNotAffected));

        userToDelete1.setModifiedDate();
        userToDelete2.setModifiedDate();
        userToDelete1.updateStatus(EStatus.DEACTIVATED);
        userToDelete2.updateStatus(EStatus.DEACTIVATED);

        userRepository.saveAll(Arrays.asList(userToDelete1, userToDelete2, userToKeep, userNotAffected));

        // when
        userRepository.deleteUsersNotModifiedSince(LocalDateTime.now().minus(7, ChronoUnit.DAYS), EStatus.DEACTIVATED);

        // then
        List<User> remainingUsers = userRepository.findAll();
        assertAll(
                () -> assertFalse(remainingUsers.contains(userToDelete1)),
                () -> assertFalse(remainingUsers.contains(userToDelete2)),
                () -> assertTrue(remainingUsers.contains(userToKeep)),
                () -> assertTrue(remainingUsers.contains(userNotAffected))
        );
    }
}