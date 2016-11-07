package com.example.web;

import com.example.security.LoginUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("news")
public class NewsController {

    /** ロガー */
    private static final Logger logger = LoggerFactory.getLogger(NewsController.class);

    /**
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public String sample(Model model, @AuthenticationPrincipal LoginUserDetails userDetails) {

        model.addAttribute("loginInfo", userDetails.getUserInfo());
        return "news/news";
    }

}
