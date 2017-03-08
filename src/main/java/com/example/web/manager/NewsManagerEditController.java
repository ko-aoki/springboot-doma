package com.example.web.manager;

import com.example.dto.NewsDto;
import com.example.service.NewsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.Map;

/**
 * お知らせ更新画面のコントローラクラス.
 */
@Controller
@RequestMapping("manager/news/edit")
@SessionAttributes({"roleIdMap"})
public class NewsManagerEditController {

    /** ロガー */
    private static final Logger logger = LoggerFactory.getLogger(NewsManagerEditController.class);

    /** お知らせ機能のサービスクラス */
    @Autowired
    private NewsService service;

    /**
     * 権限のコンボボックスを初期化します.
     * @return
     */
    @ModelAttribute("roleIdMap")
    public Map<String, String> setupRoleIdMap() {
        return service.retrieveRoleIdMap();
    }

    /**
     * 「重要なお知らせ」更新画面を表示します.
     * @param id : お知らせ情報ID
     * @param model : モデル
     * @return
     */
    @RequestMapping(params = "input")
    public String input(@RequestParam Long id,
                        Model model) {
        // お知らせ情報を選択する
        NewsDto news = service.findNews(id);

        NewsForm form = new NewsForm();
        BeanUtils.copyProperties(news, form);
        model.addAttribute("newsForm", form);
        return "/manager/news/edit/newsEditInput";
    }

    /**
     * 「重要なお知らせ」更新確認画面を表示します.
     * @param form : お知らせForm
     * @param result : バリデーション結果
     * @param roleIdMap : 権限のマップ
     * @param model : モデル
     * @return
     */
    @PostMapping(params = "confirm")
    public String confirm(@Validated NewsForm form,
                          BindingResult result,
                          @ModelAttribute("roleIdMap") Map<String, String> roleIdMap,
                          Model model) {

        // 権限名を設定
        form.setRoleNm(roleIdMap.get(form.getRoleId()));
        model.addAttribute("newsForm", form);

        // エラーチェック
        if (result.hasErrors()) {
            return "/manager/news/edit/newsEditInput";
        }
        return "/manager/news/edit/newsEditConfirm";
    }

    /**
     * 「重要なお知らせ」確認画面から入力画面にします.
     * @param form : お知らせForm
     * @param result : バリデーション結果
     * @param model : モデル
     * @return
     */
    @PostMapping(params = "back")
    public String backToInput(@Validated NewsForm form,
                          BindingResult result,
                          Model model) {

        return "/manager/news/edit/newsEditInput";
    }

    /**
     * 「重要なお知らせ」更新完了画面を表示します.
     * @param form : お知らせForm
     * @param result : バリデーション結果
     * @param model : モデル
     * @return
     */
    @PostMapping(params = "update")
    public String update(@Validated NewsForm form,
                          BindingResult result,
                          Model model,
                          RedirectAttributes redirectAttributes) {

        // フラッシュスコープに登録
        redirectAttributes.addFlashAttribute(form);
        // 再度エラーチェック
        if (result.hasErrors()) {
            throw new RuntimeException("不正な入力変更");
        }

        // 登録
        NewsDto dto = new NewsDto();
        BeanUtils.copyProperties(form, dto);
        try {
            service.modifyNews(dto);
        } catch (OptimisticLockingFailureException e) {
            model.addAttribute("messageList", Arrays.asList("他のユーザが再度同一データを更新しました。入力してください。"));
            return "forward:/manager/news/edit/newsEditInput";
        }

        return "redirect:/manager/news/edit?complete";
    }

    /**
     * 「重要なお知らせ」完了画面を表示します.
     * @param model : モデル
     * @return
     */
    @GetMapping(params = "complete")
    public String complete(NewsForm form, Model model) {

        return "/manager/news/edit/newsEditComplete";
    }

}
