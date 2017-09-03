package com.example.dao;

import com.example.entity.MstRole;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seasar.doma.boot.autoconfigure.DomaAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/** Created by ko-aoki on 2017/08/29. */
@RunWith(SpringRunner.class)
@JdbcTest
@Import(DomaAutoConfiguration.class)
@ComponentScan
@Sql(scripts = "../../../schema-dev.sql")
@Sql(scripts = "data_role.sql")
public class MstRoleDaoTest {

  @Autowired MstRoleDao dao;

  @Test
  public void selectAll() {

    List<MstRole> mstRoleList = dao.selectAll();
    assertThat(mstRoleList.size(), is(3));
  }

  @Test
  public void selectByRoleName() {

    List<MstRole> mstRoleList = dao.selectByRoleName("一般");
    assertThat(mstRoleList.size(), is(1));
  }

  @Test
  public void insert() {

    MstRole mstRole = new MstRole();
    mstRole.setRoleId("ROLE_FOO");
    mstRole.setRoleName("テスト権限");
    dao.insert(mstRole);

    List<MstRole> mstRoleList = dao.selectByRoleName("テスト権限");
    assertThat(mstRoleList.get(0).getRoleId(), is("ROLE_FOO"));
    assertThat(mstRoleList.size(), is(1));
  }
}
