package com.example.entity;

import org.seasar.doma.Entity;
import org.seasar.doma.Version;

import java.time.LocalDateTime;

/** 監査項目(共通カラム)部分のエンティティ. */
@Entity
public class AuditEntity {

  @Version private int version;
  private String insertUser;
  private LocalDateTime insertDate;
  private String updateUser;
  private LocalDateTime updateDate;

  public int getVersion() {
    return version;
  }

  public void setVersion(int version) {
    this.version = version;
  }

  public LocalDateTime getInsertDate() {
    return insertDate;
  }

  public void setInsertDate(LocalDateTime insertDate) {
    this.insertDate = insertDate;
  }

  public LocalDateTime getUpdateDate() {
    return updateDate;
  }

  public void setUpdateDate(LocalDateTime updateDate) {
    this.updateDate = updateDate;
  }

  public String getInsertUser() {
    return insertUser;
  }

  public void setInsertUser(String insertUser) {
    this.insertUser = insertUser;
  }

  public String getUpdateUser() {
    return updateUser;
  }

  public void setUpdateUser(String updateUser) {
    this.updateUser = updateUser;
  }
}
