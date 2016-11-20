package com.example.entity;

import com.example.entity.listener.AuditListener;
import org.seasar.doma.*;
import org.seasar.doma.jdbc.entity.NamingType;

/**
 * Created by ko-aoki on 2016/09/01.
 */

@Entity(naming = NamingType.SNAKE_LOWER_CASE, listener = AuditListener.class)
@Table(name = "trn_news")
public class TrnNews extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long trnNewsId;
    public String roleId;
    public String subject;
    public String url;

    public Long getTrnNewsId() {
        return trnNewsId;
    }

    public void setTrnNewsId(Long trnNewsId) {
        this.trnNewsId = trnNewsId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
