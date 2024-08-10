package Skeep.backend.fixture;

import Skeep.backend.user.domain.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserFixture {
    JOHN_DOE("A1B2C3D4E5", "John Doe", Email.createEmail("john.doe@example.com"), EProvider.APPLE, EStatus.ACTIVATED),
    JANE_SMITH("F6G7H8I9J0", "Jane Smith", Email.createEmail("jane.smith@example.com"), EProvider.APPLE, EStatus.ACTIVATED),
    ALICE_JOHNSON("K1L2M3N4O5", "Alice Johnson", Email.createEmail("alice.johnson@example.com"), EProvider.APPLE, EStatus.ACTIVATED),
    BOB_BROWN("P6Q7R8S9T0", "Bob Brown", Email.createEmail("bob.brown@example.com"), EProvider.APPLE, EStatus.ACTIVATED),
    ;

    private final String serialId;
    private final String name;
    private final Email email;
    private final EProvider provider;
    private final EStatus status;

    public User toUser(EProvider provider) {
        if (provider == EProvider.APPLE) {
            return User.createAppleUser(
                    serialId,
                    name,
                    email,
                    ERole.USER
            );
        }
        return null;
    }
}