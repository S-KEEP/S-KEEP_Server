package Skeep.backend.user.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EStatus {

    ACTIVATED("ACTIVATED"),
    DEACTIVATED("DEACTIVATED");

    private final String status;
}
