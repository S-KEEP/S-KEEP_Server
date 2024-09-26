package Skeep.backend.location.picks.dto.response;

public record PicksDto(
        String title,
        String address,
        String mapX,
        String mapY,
        String imageUrl,
        String category,
        String editorName
) {
}
