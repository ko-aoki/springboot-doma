package com.example.service;

import com.example.dto.NewsDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * お知らせ情報を操作するサービスインターフェース.
 */
public interface NewsService {

    /**
     * 権限リストを取得します.
     * @return
     */
    Map<String, String> retrieveRoleIdMap();

    /**
     * お知らせ情報を追加します.
     * @param dto
     */
    @Transactional
    void addNews(NewsDto dto);

    /**
     * お知らせ情報を修正します.
     * @param dto
     */
    @Transactional
    void modifyNews(NewsDto dto);
}
