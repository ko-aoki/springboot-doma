Doma2 基本
===================


.. code-block:: console

    └── src
        └── main
            ├── java
            │   └── com
            │       └── example
            │           ├── dao  ... Doma2のDAOクラス格納パッケージ
            │           │   └── MstRoleDao.java
            │           ├── dto
            │           │   └── NewsDto.java
            │           └── entity  ... Doma2のEntityクラス格納パッケージ
            │               └── MstRole.java
            └── resources
                └── META-INF
                    └── com
                        └── example
                            └── dao ... Doma2のSQLファイル格納パッケージ
                                └── MstRoleDao
                                    └── selectAll.sql

エンティティクラス
--------------------------

テーブルを表現する（だけではないですが）クラスです。

以下のテーブル「権限マスタ」を例にとります。

.. code-block:: sql

    CREATE TABLE mst_role
    (
        role_id varchar(20) NOT NULL COMMENT '権限ID',
        role_name varchar(100) COMMENT '権限名',
        version int COMMENT 'バージョン',
        insert_user varchar(20) COMMENT '登録ユーザ',
        insert_date datetime COMMENT 'insert_date',
        update_user varchar(20) COMMENT '更新ユーザ',
        update_date datetime COMMENT 'update_date',
        PRIMARY KEY (role_id)
    ) COMMENT = '権限マスタ';



「権限マスタ」に対応するエンティティクラスは以下のようになります。

MstRole.java

.. code-block:: java

    /**
     * 権限マスタエンティティ.
     */
    @Entity(\ = NamingType.SNAKE_LOWER_CASE)
    @Table(name = "mst_role")
    public class MstRole extends AuditEntity {

        @Id
        private String roleId;

        private String roleName;

        // getter, setter
    }

\ ``@Entity`` \でエンティティを定義します。

\ ``naming`` \要素でテーブル-クラスのネーミング規約を指定します。

\ ``Id`` \で主キーを指定します。

クラスMstRoleにない項目がテーブル「mst_role」には存在します。

これはMstRoleが継承しているAuditEntityが共通の監査項目として定義しています。

AuditEntity.java

.. code-block:: java

    /**
     * 監査項目(共通カラム)部分のエンティティ.
     */
    @Entity
    public class AuditEntity {

        @Version
        private int version;
        private String insertUser;
        private LocalDateTime insertDate;
        private String updateUser;
        private LocalDateTime updateDate;

        // getter, setter
    }

Daoインターフェース
--------------------------

Daoはデータベースアクセスのためのインタフェースです。

MstRoleDao.java

.. code-block:: java

    /**
     * 権限マスタのDaoインターフェース.
     */
    @ConfigAutowireable
    @Dao
    public interface MstRoleDao {

        @Select
        List<MstRole> selectAll();

        @Insert
        @Transactional
        int insert(MstRole ent);

    }

\ ``@Dao`` \でDaoを定義します。

DomaのDaoはインターフェースであり、
\ ``@Dao`` \を定義したインターフェースの実装クラスはaptにより、コンパイル時に自動生成されます。

\ ``@ConfigAutowireable`` \はDomaの\ ``@AnnotateWith`` \を使って、
生成されるDao実装クラスに\ ``@Repository`` \と\ ``@Autowired`` \を付与するためのアノテーションです。

参考：https://blog.ik.am/entries/371

自動生成されるDao実装クラス

.. code-block:: console

    └── target
         └── generated-sources
             └── annotations
               └── com
                   └── example
                        └── dao
                            └── MstRoleDaoImpl.java

MstRoleDaoImpl.java

.. code-block:: java

    @org.springframework.stereotype.Repository()
    @javax.annotation.Generated(value = { "Doma", "2.12.0" }, date = "2017-01-06T22:56:19.786+0900")
    public class MstRoleDaoImpl extends org.seasar.doma.internal.jdbc.dao.AbstractDao implements com.example.dao.MstRoleDao {

        static {
            org.seasar.doma.internal.Artifact.validateVersion("2.12.0");
        }

        private static final java.lang.reflect.Method __method0 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(com.example.dao.MstRoleDao.class, "selectAll");

        private static final java.lang.reflect.Method __method1 = org.seasar.doma.internal.jdbc.dao.AbstractDao.getDeclaredMethod(com.example.dao.MstRoleDao.class, "insert", com.example.entity.MstRole.class);

        /**
         * @param config the config
         */
        @org.springframework.beans.factory.annotation.Autowired()
        public MstRoleDaoImpl(org.seasar.doma.jdbc.Config config) {
            super(config);
        }

        @Override
        public java.util.List<com.example.enti.MstRole> selectAll() {
        // 後略

再掲

MstRoleDao.java

.. code-block:: java

    /**
     * 権限マスタのDaoインターフェース.
     */
    @ConfigAutowireable
    @Dao
    public interface MstRoleDao {

        @Select
        List<MstRole> selectAll();

        @Insert
        @Transactional
        int insert(MstRole ent);

    }

\ ``@Insert`` \をDaoのメソッドにアノテーションして、挿入処理となります。


同様に、\ ``@Select`` \をDaoのメソッドにアノテーションして、検索処理となります。

検索内容はメソッドと同名のSQLファイルに定義します。

.. code-block:: console

    └── src
            └── resources
                └── META-INF
                    └── com
                        └── example
                            └── dao ... Doma2のSQLファイル格納パッケージ
                                └── MstRoleDao
                                    └── selectAll.sql

selectAll.sql

.. code-block:: sql

    select
      role_id,
      role_name,
      version,
      insert_date,
      update_date
    FROM
      mst_role

SQL文がEntityと合致している必要があります。

Entityはテーブルと同一である必要はなく、結合処理の結果にも使用します。


NewsDto.java

.. code-block:: java

    /**
     * お知らせ情報Dtoクラス.
     */
    @Entity(naming = NamingType.SNAKE_LOWER_CASE)
    public class NewsDto {

        /** id */
        private Long id;
        /** 表題 */
        private String subject;
        /** 権限 */
        private String roleId;
        /** 権限名 */
        private String roleNm;
        /** URL */
        private String url;
        /** バージョン */
        private int version;

        // getter,setter

MstNewsDao.java

.. code-block:: java

    /**
     * お知らせマスタのDaoインターフェース.
     */
    @ConfigAutowireable
    @Dao
    public interface MstNewsDao {

        @Select
        NewsDto selectOneNewsDto(Long id);

        @Select
        List<NewsDto> selectNewsDtoByCond(String subject, String roleId, String url, SelectOptions selectOptions);

        // 後略

selectOneNewsDto.sql

.. code-block:: sql

    select
      n.mst_news_id id,
      n.role_id role_id,
      r.role_name role_nm,
      n.subject subject,
      n.url url,
      n.version version
    FROM
      mst_news n
    INNER JOIN
      mst_role r
    ON
      n.role_id = r.role_id
    WHERE
      n.mst_news_id = /* id */1

SQLファイルのパラメータとDaoメソッドのパラメータをマッピングさせて
動的条件を表現できます。

selectNewsDtoByCond.sql

.. code-block:: sql

    select
      n.mst_news_id id,
      n.role_id role_id,
      r.role_name role_nm,
      n.subject subject,
      n.url url,
      n.version version
    FROM
      mst_news n
    INNER JOIN
      mst_role r
    ON
      n.role_id = r.role_id
    WHERE
    /*%if @isNotEmpty(url) */
        n.url LIKE /* @prefix(url) */'http'
    /*%end*/
    /*%if @isNotEmpty(subject) */
    AND
        n.subject LIKE /* @prefix(subject) */'今日'
    /*%end*/
    /*%if @isNotEmpty(roleId) */
    AND
        n.role_id = /* roleId */'01'
    /*%end*/
    ORDER BY
      n.mst_news_id

検索条件そのものを動的に設定できます。
