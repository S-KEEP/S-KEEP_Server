package Skeep.backend.category.controller;

import Skeep.backend.category.domain.UserCategory;
import Skeep.backend.category.dto.UserCategoryList;
import Skeep.backend.fixture.UserCategoryFixture;
import Skeep.backend.fixture.UserFixture;
import Skeep.backend.global.ControllerTest;
import Skeep.backend.user.domain.EProvider;
import Skeep.backend.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.List;

import static Skeep.backend.fixture.TokenFixture.ACCESS_TOKEN;
import static Skeep.backend.global.constant.Constants.PREFIX_AUTH;
import static Skeep.backend.global.constant.Constants.PREFIX_BEARER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("[Controller Layer] -> UserCategoryController")
class UserCategoryControllerTest extends ControllerTest {
    @Nested
    @DisplayName("유저 카테고리 리스트 조회 API [GET /api/userCategory/list]")
    class getUserCategoryList {
        private static final String BASE_URL = "/api/userCategory/list";

        @Test
        @WithMockUser(username = "1")
        void 유저_카테고리_리스트_조회에_성공한다() throws Exception {
            // given
            doReturn(createUserCategoryList())
                    .when(userCategoryService)
                    .getUserCategoryList(any());

            // when
            MockHttpServletRequestBuilder requestBuilder = get(BASE_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(PREFIX_AUTH, PREFIX_BEARER + ACCESS_TOKEN);

            // then
            mockMvc.perform(requestBuilder)
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result.userCategoryDtoList").exists())
                    .andExpect(jsonPath("$.result.userCategoryDtoList.length()").value(8))
                    .andExpect(jsonPath("$.result.userCategoryDtoList[0].name").value("휴식"))
                    .andExpect(jsonPath("$.result.userCategoryDtoList[0].description").value("방해받지 않고 싶을 때 가려고 모아둔 곳"))
                    .andExpect(jsonPath("$.result.userCategoryDtoList[1].name").value("공원/자연"))
                    .andExpect(jsonPath("$.result.userCategoryDtoList[1].description").value("힐링하고 싶을 때"))
                    .andExpect(jsonPath("$.result.userCategoryDtoList[2].name").value("문화/축제"))
                    .andExpect(jsonPath("$.result.userCategoryDtoList[2].description").value("즐기고 싶은 곳"))
                    .andExpect(jsonPath("$.result.userCategoryDtoList[3].name").value("쇼핑/도심"))
                    .andExpect(jsonPath("$.result.userCategoryDtoList[3].description").value("가까운 곳을 가고 싶을 때"))
                    .andExpect(jsonPath("$.result.userCategoryDtoList[4].name").value("익사이팅"))
                    .andExpect(jsonPath("$.result.userCategoryDtoList[4].description").value("뛰어 놀고 싶을 때"))
                    .andExpect(jsonPath("$.result.userCategoryDtoList[5].name").value("맛집"))
                    .andExpect(jsonPath("$.result.userCategoryDtoList[5].description").value("여기는 꼭 먹어봐야 해"))
                    .andExpect(jsonPath("$.result.userCategoryDtoList[6].name").value("역사 및 유적지"))
                    .andExpect(jsonPath("$.result.userCategoryDtoList[6].description").value("언젠간 가볼 곳"))
                    .andExpect(jsonPath("$.result.userCategoryDtoList[7].name").value("기타"))
                    .andExpect(jsonPath("$.result.userCategoryDtoList[7].description").value(""));
        }
    }

    private UserCategoryList createUserCategoryList() {
        User user = UserFixture.ALICE_JOHNSON.toUser(EProvider.APPLE);

        List<UserCategory> userCategoryList = List.of(
                UserCategoryFixture.REST.toUserCategory(user),
                UserCategoryFixture.PARK_NATURE.toUserCategory(user),
                UserCategoryFixture.CULTURE_FESTIVAL.toUserCategory(user),
                UserCategoryFixture.SHOPPING_DOWNTOWN.toUserCategory(user),
                UserCategoryFixture.EXCITING.toUserCategory(user),
                UserCategoryFixture.RESTAURANT.toUserCategory(user),
                UserCategoryFixture.HISTORY.toUserCategory(user),
                UserCategoryFixture.EXTRA.toUserCategory(user)
        );
        return UserCategoryList.createDto(userCategoryList);
    }
}