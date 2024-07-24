package Skeep.backend.user.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EProvider {
    KAKAO("KAKAO"),
    APPLE("APPLE");

    private final String name;
}
