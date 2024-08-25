package Skeep.backend.location.tour.dto.response;

import java.util.List;

    public record TourLocationList (
            int totalCount,
            List<TourLocationDto> tourLocationDtolist
    ) {
        public record TourLocationDto (
                String title,
                String mapX,
                String mapY,
                String address,
                String dist,
                String imageUrl
        ) {
        }
    }
