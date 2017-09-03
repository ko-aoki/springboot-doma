package com.example.web.common;

import com.example.dto.RoleDto;
import com.example.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/** モーダル画面のコントローラ. */
@Controller
@RequestMapping("modal")
public class ModalDialogController {

  /** ロガー */
  private static final Logger logger = LoggerFactory.getLogger(ModalDialogController.class);

  /** モーダルサービス */
  @Autowired private RoleService roleService;

  /**
   * 「権限」モーダル画面を表示します.
   *
   * @param roleNm : 権限名称
   * @param model : モデル
   * @return 権限モーダル画面テンプレートパス
   */
  @GetMapping(path = "role")
  public String role(@Param(value = "roleNm") String roleNm, Model model) {

    List<RoleDto> dtos = roleService.findRole(roleNm);
    model.addAttribute("roleList", dtos);

    return "common/roleModal";
  }
}
