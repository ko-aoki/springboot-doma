package com.example.dto;

import org.seasar.doma.Entity;
import org.seasar.doma.jdbc.entity.NamingType;

/** 権限情報Dtoクラス. */
@Entity(naming = NamingType.SNAKE_LOWER_CASE)
public class RoleDto {

  /** 権限 */
  private String roleId;
  /** 権限名 */
  private String roleNm;

  public String getRoleId() {
    return roleId;
  }

  public void setRoleId(String roleId) {
    this.roleId = roleId;
  }

  public String getRoleNm() {
    return roleNm;
  }

  public void setRoleNm(String roleNm) {
    this.roleNm = roleNm;
  }
}
