package Skeep.backend.location.tour.dto.response;

import Skeep.backend.location.tour.dto.TourLocationDto;

import java.util.List;

public record TourLocationList (
        int totalCount,
        List<TourLocationDto> tourLocationDtolist
) {
}
