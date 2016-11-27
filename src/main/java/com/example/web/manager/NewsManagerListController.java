package com.example.web.manager;

import com.example.dto.NewsDto;
import com.example.service.NewsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

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
    @ModelAttribute("roleIdMap")
    public Map<String, String> setupRoleIdMap() {
        return service.retrieveRoleIdMap();
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
    public String display(NewsForm form,
                        Model model) {

        int page = 0;
        if (form.getPage() == null) {
            page = 0;
            form.setPage(1);
        } else {
            page = form.getPage() - 1;
        }
        Page<NewsDto> newsList = service.findNewsPage(form.getSubject(), form.getRoleId(), form.getUrl(), page);
        if (newsList.getTotalElements() > 0) {
            model.addAttribute("newsList", newsList);
        }

        return "/manager/news/list/newsList";
    }
}
