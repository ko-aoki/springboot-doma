package com.example.dao;

import com.example.entity.MstRole;
import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 権限マスタのDaoインターフェース.
 */
@ConfigAutowireable
@Dao
public interface MstRoleDao {

    @Select
    List<MstRole> selectAll();

    @Select
    List<MstRole> selectByRoleName(String roleName);

    @Insert
    @Transactional
    int insert(MstRole ent);

}
