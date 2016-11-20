package com.example.web.manager;

import com.example.dto.NewsDto;
import com.example.service.NewsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * お知らせ管理リスト画面のコントローラクラス.
 */
@Controller
@RequestMapping("manager/news/list")
public class NewsManagerListController {

    /** ロガー */
    private static final Logger logger = LoggerFactory.getLogger(NewsManagerListController.class);

    /** お知らせ機能のサービスクラス */
    @Autowired
    NewsService service;

    /**
     * 権限のコンボボックスを初期化します.
     * @return
     */
    private Map<String, String> setupRoleIdList(Model model) {

        Map<String, String> roleIdMap = service.retrieveRoleIdMap();
        model.addAttribute("roleIdMap", roleIdMap);
        return roleIdMap;
    }

    @ModelAttribute
    public NewsForm setupForm() {
        return new NewsForm();
    }

    /**
     * 「重要なお知らせ」リスト画面を表示します.
     * @param model : モデル
     * @return
     */
    @GetMapping
    public String find(NewsForm form,
                        Model model) {

        this.setupRoleIdList(model);

        List<NewsDto> newsList = service.findNewsList(form.getSubject(), form.getRoleId(), form.getUrl());
        if (newsList.size() > 0) {
            model.addAttribute("newsList", newsList);
        }

        return "/manager/news/list/newsList";
    }

//    /**
//     * 「重要なお知らせ」確認画面を表示します.
//     * @param form : お知らせForm
//     * @param result : バリデーション結果
//     * @param model : モデル
//     * @param redirectAttributes : リダイレクト属性
//     * @return
//     */
//    @RequestMapping(method = RequestMethod.POST, path = "confirm")
//    public String confirm(@Validated NewsForm form,
//                          BindingResult result,
//                          Model model,
//                          RedirectAttributes redirectAttributes) {
//
//        // 権限名を設定
//        Map<String, String> roleIdMap = service.retrieveRoleIdMap();
//        form.setRoleNm(roleIdMap.get(form.getRoleId()));
//        // フラッシュスコープに登録
//        redirectAttributes.addFlashAttribute(form);
//        // エラーチェック
//        if (result.hasErrors()) {
//            model.addAttribute("errorList", result.getFieldErrors());
//            return "/manager/news/newsInput";
//        }
//        return "redirect:/manager/news/confirm?complete";
//    }

}
