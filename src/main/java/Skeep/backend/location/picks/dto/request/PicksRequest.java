package Skeep.backend.location.picks.dto.request;

public record PicksRequest(
        String placeName,
        String categoryName,
        String fileName,
        String editorName
) {
}
