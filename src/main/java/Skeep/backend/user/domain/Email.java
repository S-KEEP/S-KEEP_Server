package Skeep.backend.user.domain;

import Skeep.backend.global.exception.BaseException;
import Skeep.backend.user.exception.UserErrorCode;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Email {
    private static final String EMAIL_REGEX =
            "^[a-zA-Z0-9_+&*-]+(?:\\." +
            "[a-zA-Z0-9_+&*-]+)*@" +
            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
            "A-Z]{2,7}$";

    @Column(name = "email")
    private String email;

    @Builder
    private Email(String email) {
        this.email = email;
    }

    public static Email createEmail(String email) {
        if (isValidEmail(email)) {
            return Email.builder().email(email).build();
        }
        throw new BaseException(UserErrorCode.INVALID_EMAIL_FORMAT);
    }

    public static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
