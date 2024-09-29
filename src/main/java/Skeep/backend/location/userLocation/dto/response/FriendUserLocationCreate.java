package Skeep.backend.location.userLocation.dto.response;

import lombok.Builder;

import java.net.URI;

@Builder
public record FriendUserLocationCreate(
        URI uri
) {
    public static FriendUserLocationCreate of(final URI uri) {
        return FriendUserLocationCreate.builder()
                                       .uri(uri)
                                       .build();
    }
}
