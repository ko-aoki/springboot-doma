package com.example.entity;

import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.jdbc.entity.NamingType;

/**
 * Created by ko-aoki on 2016/09/01.
 */

@Entity(naming = NamingType.SNAKE_LOWER_CASE)
@Table(name = "mst_employee")
public class MstEmployee {

    @Id
    public String employeeId;
    public String employeeLastName;
    public String employeeFirstName;
    public String roleId;

}
