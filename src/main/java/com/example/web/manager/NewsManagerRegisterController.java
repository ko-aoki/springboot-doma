package com.example.web.manager;

import com.example.dto.NewsDto;
import com.example.service.NewsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

/**
 * お知らせ登録画面のコントローラクラス.
 */
@Controller
@RequestMapping("manager/news/register/")
public class NewsManagerRegisterController {

    /** ロガー */
    private static final Logger logger = LoggerFactory.getLogger(NewsManagerRegisterController.class);

    /** お知らせ機能のサービスクラス */
    @Autowired
    NewsService service;

    @ModelAttribute
    public NewsForm setupForm() {
        return new NewsForm();
    }

    /**
     * 権限のコンボボックスを初期化します.
     * @return
     */
    private Map<String, String> setupRoleIdList(Model model) {

        Map<String, String> roleIdMap = service.retrieveRoleIdMap();
        model.addAttribute("roleIdMap", roleIdMap);
        return roleIdMap;
    }

    /**
     * 「重要なお知らせ」入力画面を表示します.
     * @param model : モデル
     * @return
     */
    @GetMapping(path = "input")
    public String input(NewsForm form,
                        Model model) {

        if (form == null) {
            form = new NewsForm();
        }
        model.addAttribute("newsForm", form);
        this.setupRoleIdList(model);
        return "/manager/news/register/newsRegisterInput";
    }

    /**
     * 「重要なお知らせ」確認画面を表示します.
     * @param form : お知らせForm
     * @param result : バリデーション結果
     * @param model : モデル
     * @param redirectAttributes : リダイレクト属性
     * @return
     */
    @PostMapping(path = "confirm")
    public String confirm(@Validated NewsForm form,
                          BindingResult result,
                          Model model,
                          RedirectAttributes redirectAttributes) {

        // 権限名を設定
        Map<String, String> roleIdMap = service.retrieveRoleIdMap();
        form.setRoleNm(roleIdMap.get(form.getRoleId()));
        // フラッシュスコープに登録
        redirectAttributes.addFlashAttribute(form);
        // エラーチェック
        if (result.hasErrors()) {
            model.addAttribute("errorList", result.getFieldErrors());
            return "/manager/news/register/newsInput";
        }
        return "redirect:/manager/news/register/confirm?complete";
    }

    /**
     * 「重要なお知らせ」確認画面を表示します.
     * @param form : お知らせForm
     * @param model : モデル
     * @return
     */
    @GetMapping(path = "confirm", params = "complete")
    public String confirmComplete(NewsForm form, Model model) {

        model.addAttribute("form", form);
        return "/manager/news/register/newsRegisterConfirm";
    }

    /**
     * 「重要なお知らせ」確認画面から入力画面にします.
     * @param form : お知らせForm
     * @param result : バリデーション結果
     * @param model : モデル
     * @param redirectAttributes : リダイレクト属性
     * @return
     */
    @PostMapping(path = "complete", params = "back")
    public String backToInput(@Validated NewsForm form,
                          BindingResult result,
                          Model model,
                          RedirectAttributes redirectAttributes) {

        // フラッシュスコープに登録
        redirectAttributes.addFlashAttribute(form);
        return "redirect:/manager/news/register/input";
    }

    /**
     * 「重要なお知らせ」完了画面を表示します.
     * @param form : お知らせForm
     * @param result : バリデーション結果
     * @param model : モデル
     * @return
     */
    @PostMapping(path = "complete")
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

        // 登録
        NewsDto dto = new NewsDto();
        BeanUtils.copyProperties(form, dto);
        service.addNews(dto);

        return "redirect:/manager/news/register/complete?complete";
    }

    /**
     * 「重要なお知らせ」完了画面を表示します.
     * @param model : モデル
     * @return
     */
    @GetMapping(path = "complete", params = "complete")
    public String completeComplete(NewsForm form, Model model) {

        model.addAttribute("form", form);
        return "/manager/news/register/newsRegisterComplete";
    }

}
