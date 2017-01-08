package com.example.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ログイン画面のコントローラ.
 */
@Controller
public class LoginController {

    @RequestMapping("loginForm")
    String loginForm() {
        return "loginForm";
    }
}