package com.example.entity.listener;

import com.example.entity.AuditEntity;
import com.example.security.LoginUserDetails;
import com.example.security.UserInfo;
import org.seasar.doma.jdbc.entity.EntityListener;
import org.seasar.doma.jdbc.entity.PreInsertContext;
import org.seasar.doma.jdbc.entity.PreUpdateContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;

/**
 * Created by ko-aoki on 2016/11/06.
 */
public class AuditListener<T extends AuditEntity> implements EntityListener<T> {

    @Override
    public void preInsert(T auditEntity, PreInsertContext<T> context) {
        UserInfo userInfo = this.getUserInfo();
        if (userInfo != null) {
            auditEntity.setInsertUser(userInfo.getId());
        }
        auditEntity.setInsertDate(LocalDateTime.now());
    }

    @Override
    public void preUpdate(T auditEntity, PreUpdateContext<T> context) {

        UserInfo userInfo = this.getUserInfo();
        if (userInfo != null) {
            auditEntity.setUpdateUser(userInfo.getId());
        }
        auditEntity.setUpdateDate(LocalDateTime.now());
    }

    private UserInfo getUserInfo() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return null;
        }
        LoginUserDetails userDetails = (LoginUserDetails) auth.getPrincipal();
        if (userDetails == null) {
            return null;
        }
        return userDetails.getUserInfo();
    }


}
