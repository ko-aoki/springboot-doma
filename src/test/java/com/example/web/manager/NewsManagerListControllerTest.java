package com.example.web.manager;

import com.example.SecurityConfig;
import com.example.helper.AuthenticationTestHelper;
import com.example.service.NewsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = NewsManagerListController.class)
@Import(SecurityConfig.class)
public class NewsManagerListControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    NewsService mockService;

    @Autowired
    WebApplicationContext context;

    @Before
    public void setup() {

        AuthenticationTestHelper.管理者権限の設定();
        // modelAttribute
        Mockito.when(mockService.retrieveRoleIdMap()).thenReturn(new HashMap<String, String>());
    }

    @Test
    public void リクエストマッピング() throws Exception {

        this.mvc.perform(
                MockMvcRequestBuilders.get("/manager/news/list")
                .with(csrf().asHeader())
        ).andExpect(status().isOk());
    }

    @Test
    public void 一般ユーザでエラーになる() throws Exception {

        AuthenticationTestHelper.一般権限の設定();
        this.mvc.perform(
                MockMvcRequestBuilders.get("/manager/news/list")
                        .with(csrf().asHeader())
        ).andExpect(status().is4xxClientError());
    }
}