package Skeep.backend.user.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("[Domain Layer] User")
class UserTest {
    @Test
    void APPLE_유저_생성에_성공하다() {
        User user = User.createAppleUser("1234567890", "Chaerin Yang", Email.createEmail("abcdegf@gmail.com"));

        assertAll(
                () -> assertThat(user.getAppleSerialId()).isEqualTo("1234567890"),
                () -> assertThat(user.getName()).isEqualTo("Chaerin Yang"),
                () -> assertThat(user.getEmail().getEmail()).isEqualTo("abcdegf@gmail.com"),
                () -> assertThat(user.getProvider()).isEqualTo(EProvider.APPLE),
                () -> assertThat(user.getStatus()).isEqualTo(EStatus.ACTIVATED)
                );
    }
}