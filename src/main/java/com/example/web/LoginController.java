package com.example.web;

/**
 * Created by ko-aoki on 2016/09/03.
 */
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
    @RequestMapping("loginForm")
    String loginForm() {
        return "loginForm";
    }
}