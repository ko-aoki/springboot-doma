package com.example.service;

import com.example.dao.MstRoleDao;
import com.example.dao.TrnNewsDao;
import com.example.dto.NewsDto;
import com.example.entity.MstRole;
import com.example.entity.TrnNews;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * お知らせ情報を操作するサービスクラス.
 * Created by ko-aoki on 2016/11/06.
 */
@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    TrnNewsDao dao;

    @Autowired
    MstRoleDao mstRoleDao;


    @Override
    public Map<String, String> retrieveRoleIdMap() {

        List<MstRole> mstRoles = mstRoleDao.selectAll();
        Map<String, String> roleIdMap = new HashMap<>();
        mstRoles.stream().forEach(mstRole -> roleIdMap.put(mstRole.roleId, mstRole.roleName));

        return roleIdMap;
    }

    @Override
    public void addNews(NewsDto dto) {

        TrnNews trn = new TrnNews();
        BeanUtils.copyProperties(dto, trn);

        dao.insert(trn);
    }

    @Override
    public void modifyNews(NewsDto dto) {

        TrnNews trnNews = new TrnNews();
        BeanUtils.copyProperties(dto, trnNews);
        trnNews.setTrnNewsId(dto.getId());

        dao.update(trnNews);
    }

    @Override
    public NewsDto findNews(Long id) {
        return dao.selectOneNewsDto(id);
    }

    @Override
    public List<NewsDto> findNewsList(String subject, String roleId, String url) {
        return dao.selectNewsDtoByCond(subject, roleId, url);
    }
}
