package com.example.dao;

import com.example.entity.MstEmployee;
import com.example.entity.UserEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by ko-aoki on 2017/03/10.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {"spring.datasource.url:jdbc:h2:file:./work/db/db;MODE=MySQL;DB_CLOSE_ON_EXIT=FALSE"}
)
@Transactional
public class MstEmployeeDaoTest {

    @Autowired
    MstEmployeeDao dao;

    @Test
    @Sql(scripts = "data_employee.sql")
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
    }

    @Test
    @Sql(scripts = "data_employee.sql")
    public void selectUser(){

        UserEntity actual = dao.selectUser("01");
        assertThat(actual.getEmployeeLastName(), is("管理"));
        assertThat(actual.getEmployeeFirstName(), is("太郎"));
        assertThat(actual.getRoleId(), is("ROLE_ADMIN"));
        assertThat(actual.getPassword(), is("$2a$10$1gJJgBlL75OIjkSgkYPXI.mV7ihEPjxIiCkXKBEc7/r9xUIjZyc9i"));
    }
}