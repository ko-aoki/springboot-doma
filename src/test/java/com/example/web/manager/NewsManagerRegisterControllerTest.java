package com.example.web.manager;

import com.example.SecurityConfig;
import com.example.helper.AuthenticationTestHelper;
import com.example.service.NewsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
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
import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(value = NewsManagerRegisterController.class)
@Import(SecurityConfig.class)
public class NewsManagerRegisterControllerTest {

  @Autowired private MockMvc mvc;

  @MockBean NewsService mockService;

  @Before
  public void setup() {

    AuthenticationTestHelper.管理者権限の設定();
    // modelAttribute
    Mockito.when(mockService.retrieveRoleIdMap()).thenReturn(new HashMap<String, String>());
  }

  @Test
  public void お知らせ登録画面_リクエストマッピング() throws Exception {

    MvcResult result =
        this.mvc
            .perform(
                MockMvcRequestBuilders.post("/manager/news/register")
                    .param("input", "")
                    .with(csrf()))
            .andExpect(status().isOk())
            .andExpect(view().name("/manager/news/register/newsRegisterInput"))
            .andReturn();
  }

  @Test
  public void お知らせ登録確認画面_リクエストマッピング() throws Exception {

    MvcResult result =
        this.mvc
            .perform(
                MockMvcRequestBuilders.post("/manager/news/register")
                    .param("confirm", "")
                    .param("url", "http://a.b")
                    .param("subject", "テスト表題")
                    .param("roleId", "ROLE_ADMIN")
                    .with(csrf()))
            .andExpect(status().isOk())
            .andExpect(view().name("/manager/news/register/newsRegisterConfirm"))
            .andReturn();
  }

  @Test
  public void お知らせ登録確認画面_バリデーションチェック() throws Exception {

    MvcResult result =
        this.mvc
            .perform(
                MockMvcRequestBuilders.post("/manager/news/register")
                    .param("confirm", "")
                    .param("url", "hoge://a.b")
                    .param("subject", "")
                    .with(csrf()))
            .andExpect(status().isOk())
            .andExpect(model().hasErrors())
            .andExpect(view().name("/manager/news/register/newsRegisterInput"))
            .andReturn();

    // エラーメッセージの確認
    String content = result.getResponse().getContentAsString();

    assertThat(content, is(containsString("お知らせURLの形式が正しくありません。")));
    assertThat(content, is(containsString("お知らせ表題が入力されていません。")));
    assertThat(content, is(containsString("権限IDが入力されていません。")));
  }

  @Test
  public void お知らせ登録画面戻る_リクエストマッピング() throws Exception {

    MvcResult result =
        this.mvc
            .perform(
                MockMvcRequestBuilders.post("/manager/news/register")
                    .param("back", "")
                    .param("url", "http://a.b")
                    .param("subject", "テスト表題")
                    .param("roleId", "ROLE_ADMIN")
                    .with(csrf()))
            .andExpect(status().isOk())
            .andExpect(view().name("/manager/news/register/newsRegisterInput"))
            .andReturn();
  }

  @Test
  public void お知らせ登録完了画面_リクエストマッピング() throws Exception {

    doNothing().when(mockService).validateNews(Matchers.any());
    doNothing().when(mockService).addNews(Matchers.any());

    MvcResult result =
        this.mvc
            .perform(
                MockMvcRequestBuilders.post("/manager/news/register")
                    .param("register", "")
                    .param("url", "http://a.b")
                    .param("subject", "テスト表題")
                    .param("roleId", "ROLE_ADMIN")
                    .with(csrf()))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/manager/news/register?complete"))
            .andReturn();
  }

  @Test
  public void お知らせ登録完了画面_バリデーションチェック() throws Exception {

    MvcResult result =
        this.mvc
            .perform(
                MockMvcRequestBuilders.post("/manager/news/register")
                    .param("register", "")
                    .param("url", "hoge://a.b")
                    .param("subject", "")
                    .with(csrf()))
            .andExpect(status().is5xxServerError())
            .andExpect(view().name("error/5xx"))
            .andReturn();
  }

  @Test
  public void お知らせ登録完了画面_リクエストマッピングGET() throws Exception {

    MvcResult result =
        this.mvc
            .perform(
                MockMvcRequestBuilders.get("/manager/news/register")
                    .param("complete", "")
                    .param("url", "http://a.b")
                    .param("subject", "テスト表題")
                    .param("roleId", "ROLE_ADMIN")
                    .with(csrf()))
            .andExpect(status().isOk())
            .andExpect(view().name("/manager/news/register/newsRegisterComplete"))
            .andReturn();
  }
}
