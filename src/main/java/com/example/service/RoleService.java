package com.example.service;

import com.example.dto.NewsDto;
import com.example.dto.RoleDto;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/** 権限機能を操作するサービスインターフェース. */
public interface RoleService {

  /**
   * 権限情報を検索します.
   *
   * @param roleNm:権限名称
   * @return 権限情報
   */
  List<RoleDto> findRole(String roleNm);
}
