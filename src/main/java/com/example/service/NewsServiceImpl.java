package com.example.service;

import com.example.dao.TrnNewsDao;
import com.example.dto.NewsDto;
import com.example.entity.TrnNews;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * お知らせ情報を操作するサービスクラス.
 * Created by ko-aoki on 2016/11/06.
 */
@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    TrnNewsDao dao;

    @Override
    public void addNews(NewsDto dto) {

        TrnNews trn = new TrnNews();
        BeanUtils.copyProperties(dto, trn);

        dao.insert(trn);
    }

    @Override
    public void modifyNews(NewsDto dto) {

    }
}
