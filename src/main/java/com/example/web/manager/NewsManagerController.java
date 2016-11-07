package com.example.web.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("manager/news")
public class NewsManagerController {

    /** ロガー */
    private static final Logger logger = LoggerFactory.getLogger(NewsManagerController.class);

    /**
     * 「重要なお知らせ」入力画面を表示します.
     * @param model : モデル
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, path = "input")
    public String input(Model model) {

        NewsForm newsForm = new NewsForm();
        model.addAttribute("newsForm", newsForm);
        return "/manager/news/newsInput";
    }

    /**
     * 「重要なお知らせ」確認画面を表示します.
     * @param form : お知らせForm
     * @param result : バリデーション結果
     * @param model : モデル
     * @param redirectAttributes : リダイレクト属性
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, path = "confirm")
    public String confirm(@Validated NewsForm form,
                          BindingResult result,
                          Model model,
                          RedirectAttributes redirectAttributes) {

        // フラッシュスコープに登録
        redirectAttributes.addFlashAttribute(form);
        // エラーチェック
        if (result.hasErrors()) {
            model.addAttribute("errorList", result.getFieldErrors());
            return "/manager/news/newsInput";
        }
        return "redirect:/manager/news/confirm?complete";
    }

    /**
     * 「重要なお知らせ」確認画面を表示します.
     * @param form : お知らせForm
     * @param model : モデル
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, path = "confirm", params = "complete")
    public String confirmComplete(NewsForm form, Model model) {

        model.addAttribute("form", form);
        return "/manager/news/newsConfirm";
    }

    /**
     * 「重要なお知らせ」完了画面を表示します.
     * @param form : お知らせForm
     * @param result : バリデーション結果
     * @param model : モデル
     * @return
     */
    public String complete(@Validated NewsForm form,
                          BindingResult result,
                          Model model,
                          RedirectAttributes redirectAttributes) {

        // フラッシュスコープに登録
        redirectAttributes.addFlashAttribute(form);
        // 再度エラーチェック
        if (result.hasErrors()) {
            model.addAttribute("errorList", result.getFieldErrors());
            throw new RuntimeException("不正な入力変更");
        }
        return "redirect:/manager/news/complete?complete";
    }

    /**
     * 「重要なお知らせ」完了画面を表示します.
     * @param model : モデル
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, path = "complete", params = "complete")
    public String completeComplete(NewsForm form, Model model) {

        model.addAttribute("form", form);
        return "/manager/news/newsComplete";
    }

}
