package com.example.entity;

import com.example.entity.listener.AuditListener;
import org.seasar.doma.*;
import org.seasar.doma.jdbc.entity.NamingType;

/**
 * お知らせマスタエンティティ.
 */

@Entity(naming = NamingType.SNAKE_LOWER_CASE, listener = AuditListener.class)
@Table(name = "mst_news")
public class MstNews extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mstNewsId;
    private String roleId;
    private String subject;
    private String url;

    public Long getMstNewsId() {
        return mstNewsId;
    }

    public void setMstNewsId(Long mstNewsId) {
        this.mstNewsId = mstNewsId;
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
