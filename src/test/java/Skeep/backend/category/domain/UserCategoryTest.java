package Skeep.backend.category.domain;

import Skeep.backend.user.domain.Email;
import Skeep.backend.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("[Domain Test] UserCategory")
public class UserCategoryTest {
    @Test
    void UserCategory_객체_생성에_성공하다() {
        User user = User.createAppleUser("1234567890", "Chaerin Yang", Email.createEmail("abcdegf@gmail.com"));
        UserCategory userCategory = UserCategory.builder()
                .name("대충 맛집")
                .description("진짜 대충대충 맛있는 음식이 있다.")
                .user(user)
                .build();

        assertAll(
                () -> assertThat(userCategory.getName()).isEqualTo("대충 맛집"),
                () -> assertThat(userCategory.getDescription()).isEqualTo("진짜 대충대충 맛있는 음식이 있다."),
                () -> assertThat(userCategory.getUser()).isEqualTo(user)
        );
    }

}
