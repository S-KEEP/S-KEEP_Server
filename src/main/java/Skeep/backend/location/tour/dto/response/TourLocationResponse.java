package Skeep.backend.location.tour.dto.response;

import java.util.List;

public record TourLocationResponse(
        Response response
) {
    public record Response(
            Header header,
            Body body
    ) {
        public record Header(
                String resultCode,
                String resultMsg
        ) {
        }

        public record Body(
                Items items,
                int numOfRows,
                int pageNo,
                int totalCount
        ) {
            public record Items(
                    List<TourLocation> item
            ) {
            }
        }
    }

    public record TourLocation(
            String addr1,
            String addr2,
            String areacode,
            String booktour,
            String cat1,
            String cat2,
            String cat3,
            String contentid,
            String contenttypeid,
            String createdtime,
            String dist,
            String firstimage,
            String firstimage2,
            String cpyrhtDivCd,
            String mapx,
            String mapy,
            String mlevel,
            String modifiedtime,
            String sigungucode,
            String tel,
            String title
    ) {
    }
}
