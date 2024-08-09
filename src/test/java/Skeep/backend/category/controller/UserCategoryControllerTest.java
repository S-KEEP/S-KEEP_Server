package Skeep.backend.category.controller;

import Skeep.backend.category.domain.UserCategory;
import Skeep.backend.category.dto.UserCategoryList;
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
import static Skeep.backend.fixture.UserCategoryFixture.*;
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
                    .andExpect(jsonPath("$.result.userCategoryDtoList.length()").value(6))
                    .andExpect(jsonPath("$.result.userCategoryDtoList[0].name").value("익사이팅"))
                    .andExpect(jsonPath("$.result.userCategoryDtoList[0].description").value("Exciting activities"))
                    .andExpect(jsonPath("$.result.userCategoryDtoList[1].name").value("공원/자연"))
                    .andExpect(jsonPath("$.result.userCategoryDtoList[1].description").value("Parks and nature-related activities"))
                    .andExpect(jsonPath("$.result.userCategoryDtoList[2].name").value("휴식"))
                    .andExpect(jsonPath("$.result.userCategoryDtoList[2].description").value("Relaxation and rest"))
                    .andExpect(jsonPath("$.result.userCategoryDtoList[3].name").value("역사 및 유적지"))
                    .andExpect(jsonPath("$.result.userCategoryDtoList[3].description").value("Historical sites and landmarks"))
                    .andExpect(jsonPath("$.result.userCategoryDtoList[4].name").value("문화/축제"))
                    .andExpect(jsonPath("$.result.userCategoryDtoList[4].description").value("Cultural events and festivals"))
                    .andExpect(jsonPath("$.result.userCategoryDtoList[5].name").value("쇼핑/도심"))
                    .andExpect(jsonPath("$.result.userCategoryDtoList[5].description").value("Shopping and downtown areas"));
        }
    }

    private UserCategoryList createUserCategoryList() {
        User user = UserFixture.ALICE_JOHNSON.toUser(EProvider.APPLE);

        List<UserCategory> userCategoryList = List.of(
                EXCITING.toUserCategory(user),
                PARK_NATURE.toUserCategory(user),
                REST.toUserCategory(user),
                HISTORY.toUserCategory(user),
                CULTURE_FESTIVAL.toUserCategory(user),
                SHOPPING_DOWNTOWN.toUserCategory(user)
        );
        return UserCategoryList.createDto(userCategoryList);
    }
}