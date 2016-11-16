package com.example.dao;

/**
 * Created by ko-aoki on 2016/09/01.
 */

import com.example.entity.MstRole;
import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@ConfigAutowireable
@Dao
public interface MstRoleDao {

    @Select
    List<MstRole> selectAll();

    @Insert
    @Transactional
    int insert(MstRole ent);

}
