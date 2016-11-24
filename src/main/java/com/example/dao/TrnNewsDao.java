package com.example.dao;

/**
 * Created by ko-aoki on 2016/09/01.
 */

import com.example.dto.NewsDto;
import com.example.entity.TrnNews;
import org.seasar.doma.*;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@ConfigAutowireable
@Dao
public interface TrnNewsDao {

    @Select
    List<TrnNews> selectAll();

    @Select
    List<NewsDto> selectNewsDtoByCond(String subject, String roleId, String url, SelectOptions selectOptions);

    @Select
    NewsDto selectOneNewsDto(Long id);

    @Insert
    @Transactional
    int insert(TrnNews trn);

    @Update
    @Transactional
    int update(TrnNews trn);

    @Delete
    @Transactional
    int delete(TrnNews trn);
}
