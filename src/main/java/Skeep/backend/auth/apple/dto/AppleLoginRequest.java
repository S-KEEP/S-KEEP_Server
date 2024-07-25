package Skeep.backend.auth.apple.dto;

public record AppleLoginRequest(
        String state,
        String code,
        String id_token,
        AppleUser user
) {
    public record AppleUser(
            String email,
            AppleName name
    ) {
        public record AppleName(
                String firstName,
                String lastName
        ) {
        }
    }
}