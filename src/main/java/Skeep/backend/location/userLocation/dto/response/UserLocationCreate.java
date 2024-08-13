package Skeep.backend.location.userLocation.dto.response;

import lombok.Builder;

import java.net.URI;

@Builder
public record UserLocationCreate(
        UserLocationCreateDto userLocationCreateDto,
        URI uri
) {
    public static UserLocationCreate of(
            final UserLocationCreateDto userLocationCreateDto,
            final URI uri
    ) {
        return UserLocationCreate .builder()
                                  .userLocationCreateDto(userLocationCreateDto)
                                  .uri(uri)
                                  .build();
    }
}
