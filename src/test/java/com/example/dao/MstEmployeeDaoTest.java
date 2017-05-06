package com.example.dao;

import com.example.entity.MstEmployee;
import com.example.entity.UserEntity;
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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

/**
 * Created by ko-aoki on 2017/03/10.
 */
@RunWith(SpringRunner.class)
@JdbcTest
@Import(DomaAutoConfiguration.class)
@ComponentScan
@Sql(scripts = "../../../schema-dev.sql")
@Sql(scripts = "data_employee.sql")
public class MstEmployeeDaoTest {

    @Autowired
    MstEmployeeDao dao;

    @Test
    public void selectAll() {

        List<MstEmployee> mstEmployees = dao.selectAll();
        assertThat(mstEmployees.size(), is(3));
    }

    @Test
    public void selectOne() {

        MstEmployee actual = dao.selectOne("01");
        assertThat(actual.getEmployeeLastName(), is("管理"));
        assertThat(actual.getEmployeeFirstName(), is("太郎"));
        assertThat(actual.getRoleId(), is("ROLE_ADMIN"));

        actual = dao.selectOne("100");
        assertNull(actual);
    }

    @Test
    public void selectUser(){

        UserEntity actual = dao.selectUser("01");
        assertThat(actual.getEmployeeLastName(), is("管理"));
        assertThat(actual.getEmployeeFirstName(), is("太郎"));
        assertThat(actual.getRoleId(), is("ROLE_ADMIN"));
        assertThat(actual.getPassword(), is("$2a$10$1gJJgBlL75OIjkSgkYPXI.mV7ihEPjxIiCkXKBEc7/r9xUIjZyc9i"));

        actual = dao.selectUser("100");
        assertNull(actual);

    }
}