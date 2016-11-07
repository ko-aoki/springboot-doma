package com.example.entity;

import com.example.entity.listener.AuditListener;
import org.seasar.doma.Entity;
import org.seasar.doma.Version;

import java.time.LocalDateTime;

/**
 * 監査項目(共通カラム)部分のエンティティ.
 */
@Entity
public class AuditEntity {

    @Version
    public int version;
    public LocalDateTime insertDate;
    public LocalDateTime updateDate;

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
}
