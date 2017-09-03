package com.example.error;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * エラーをハンドリングするクラス.
 *
 * <p>以下を参考にしています. https://github.com/openenquete/enquete
 */
@ControllerAdvice(annotations = Controller.class)
public class GlobalErrorControllerAdvice {

  /** 環境オブジェクト */
  private final Environment env;

  public GlobalErrorControllerAdvice(Environment env) {
    this.env = env;
  }

  /**
   * 意図しないエラーを操作します.
   *
   * @param e
   * @param model
   * @return
   */
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(Exception.class)
  public String handleException(Exception e, Model model) {

    this.addErrors(e, HttpStatus.INTERNAL_SERVER_ERROR, model);
    return "error/5xx";
  }

  /**
   * エラー画面に詳細情報を追加します.
   *
   * @param e
   * @param status
   * @param model
   */
  void addErrors(Exception e, HttpStatus status, Model model) {
    if (env.acceptsProfiles("default")) {
      StringWriter stackTrace = new StringWriter();
      e.printStackTrace(new PrintWriter(stackTrace));
      stackTrace.flush();
      model.addAttribute("status", status.value());
      model.addAttribute("error", status.getReasonPhrase());
      model.addAttribute("exception", e.getClass());
      model.addAttribute("message", e.getMessage());
      model.addAttribute("trace", stackTrace.toString());
    }
  }
}
