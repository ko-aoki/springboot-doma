package com.example.web.manager;

import com.example.security.LoginUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("manager/entry")
public class EntryManagerController {

    /** ロガー */
    private static final Logger logger = LoggerFactory.getLogger(EntryManagerController.class);

    /**
     * 「重要なお知らせ」入力画面を表示します.
     * @param model
     * @param userDetails
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, path = "input")
    public String input(Model model, @AuthenticationPrincipal LoginUserDetails userDetails) {

        EntryForm entryForm = new EntryForm();
        model.addAttribute("entryForm", entryForm);
        model.addAttribute("loginInfo", userDetails.getUserInfo());
        return "manager/entry/entryInput";
    }

    /**
     * 「重要なお知らせ」確認画面を表示します.
     * @param model
     * @param userDetails
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, path = "confirm")
    public String confirm(@Validated EntryForm form,
                          BindingResult result,
                          @AuthenticationPrincipal LoginUserDetails userDetails, Model model) {

        model.addAttribute("form", form);
        model.addAttribute("loginInfo", userDetails.getUserInfo());
        if (result.hasErrors()) {
            model.addAttribute("errorList", result.getFieldErrors());
            return "manager/entry/entryInput";
        }
        return "manager/entry/entryConfirm";
    }

}
