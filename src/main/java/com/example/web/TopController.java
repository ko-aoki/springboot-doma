package com.example.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/** トップ画面のコントローラ. */
@Controller
@RequestMapping("/")
public class TopController {

  /** ロガー */
  private static final Logger logger = LoggerFactory.getLogger(TopController.class);

  /**
   * トップ画面.
   *
   * @return ビュー名
   */
  @RequestMapping(method = RequestMethod.GET)
  public String top(Model model) {

    return "top/top";
  }
}
