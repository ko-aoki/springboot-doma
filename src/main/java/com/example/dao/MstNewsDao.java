package com.example.dao;

/**
 * Created by ko-aoki on 2016/09/01.
 */

import com.example.dto.NewsDto;
import com.example.entity.MstNews;
import org.seasar.doma.*;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * お知らせマスタのDaoインターフェース.
 */
@ConfigAutowireable
@Dao
public interface MstNewsDao {

    @Select
    List<MstNews> selectAll();

    @Select
    List<NewsDto> selectNewsDtoByCond(String subject, String roleId, String url, SelectOptions selectOptions);

    @Select
    NewsDto selectOneNewsDto(Long id);

    @Insert
    @Transactional
    int insert(MstNews trn);

    @Update
    @Transactional
    int update(MstNews trn);

    @Delete
    @Transactional
    int delete(MstNews trn);
}
