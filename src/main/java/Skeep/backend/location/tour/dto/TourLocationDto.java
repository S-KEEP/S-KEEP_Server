package Skeep.backend.location.tour.dto;

public record TourLocationDto (
        String title,
        String mapX,
        String mapY,
        String address,
        String dist,
        String imageUrl
) {
}
