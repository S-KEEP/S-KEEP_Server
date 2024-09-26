package Skeep.backend.location.picks.dto.response;

import java.util.List;

public record PicksDtoList(
        int totalCount,
        List<PicksDto> picksDtoList
) {
}
