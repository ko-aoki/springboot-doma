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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = NewsManagerRegisterController.class)
@Import(SecurityConfig.class)
public class NewsManagerRegisterControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    NewsService mockService;

    @Before
    public void setup() {

        AuthenticationTestHelper.管理者権限の設定();
        // modelAttribute
        Mockito.when(mockService.retrieveRoleIdMap()).thenReturn(new HashMap<String, String>());
    }

    @Test
    public void バリデーションチェック() throws Exception {

        MvcResult result = this.mvc.perform(
                MockMvcRequestBuilders.post("/manager/news/register")
                        .param("confirm", "")
                        .param("url", "hoge://a.b")
                        .param("subject", "")
                        .with(csrf())
        )
                .andExpect(status().isOk())
                .andExpect(model().hasErrors())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        assertThat(content, is(containsString("お知らせURLの形式が正しくありません。")));
        assertThat(content, is(containsString("お知らせ表題が入力されていません。")));
        assertThat(content, is(containsString("権限IDが入力されていません。")));

    }

}