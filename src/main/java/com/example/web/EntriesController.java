package com.example.web;

import com.example.security.LoginUserDetails;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;

@Controller
@RequestMapping("entries")
public class EntriesController {

    /** ロガー */
    private static final Logger logger = LoggerFactory.getLogger(EntriesController.class);

    /**
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public String sample(Model model, @AuthenticationPrincipal LoginUserDetails userDetails) {

        model.addAttribute("loginInfo", userDetails.getUserInfo());
        return "entries/entries";
    }

}
