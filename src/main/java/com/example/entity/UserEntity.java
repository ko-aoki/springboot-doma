package com.example.entity;

import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.jdbc.entity.NamingType;

@Entity(naming = NamingType.SNAKE_LOWER_CASE)
public class UserEntity {

	private static final long serialVersionUID = 1L;

	@Id
	public String employeeId;
	public String employeeLastName;
	public String employeeFirstName;
	public String roleId;
	public String password;

}
