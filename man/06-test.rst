ユニットテスト
===================

ユニットテストの手順について記述します。
テスト系のリソースは以下のファイル構成になります。

.. code-block:: console

    ├── main
    │      └── resources
    │       ├── schema-dev.sql // 開発時使用のDML文。ユニットテストでも使用する。
    │
    └── test
        ├── java
        │   ├── com
        │   │   └── example
        │   │       ├── dao
        │   │       │   ├── MstEmployeeDaoTest.java // Daoのテストクラス
        │   │       │   └── MstNewsDaoTest.java
        │   │       ├── dto
        │   │       ├── helper
        │   │       │   └── AuthenticationTestHelper.java // 認証系テスト用のヘルパークラス
        │   │       ├── service
        │   │       │   └── NewsServiceImplTest.java // サービスのテストクラス
        │   │       └── web
        │   │           └── manager
        │   │               ├── NewsManagerListControllerTest.java // コントローラのテストクラス
        │   │               └── NewsManagerRegisterControllerTest.java
        │   └── password
        │       └── PasswordGenerator.java // パスワードをハッシュ化するクラス
        └── resources
            └── com
                └── example
                    └── dao
                        ├── data_employee.sql // Daoテストに使用するDDL文
                        └── data_news.sql


Daoクラスのテスト
--------------------------

Daoテストに使用するDDLを記述します。
実行対象のクラス/メソッドに\ ``@SQL`` \アノテーションで指定します。

data_employee.sql

.. code-block:: sql

    delete from mst_employee;
    delete from mst_password;
    delete from mst_role;
    insert into mst_employee (employee_id, employee_last_name, employee_first_name, role_id) values('01', '管理', '太郎', 'ROLE_ADMIN');
    insert into mst_employee (employee_id, employee_last_name, employee_first_name, role_id) values('02', '一般', '二郎', 'ROLE_USER');
    insert into mst_employee (employee_id, employee_last_name, employee_first_name, role_id) values('03', '一般', '三郎', 'ROLE_USER');
    insert into mst_password (mst_password_id, employee_id, password, generation) values(1, '01', '$2a$10$i7ZAPWh9xNT98pkJ4B6TyuPkPdehn6oZiwOOjm9/GXc3ZNlbUdQLq', '01');
    insert into mst_password (mst_password_id, employee_id, password, generation) values(2, '02', '$2a$10$1gJJgBlL75OIjkSgkYPXI.mV7ihEPjxIiCkXKBEc7/r9xUIjZyc9i', '01');
    insert into mst_password (mst_password_id, employee_id, password, generation) values(3, '01', '$2a$10$1gJJgBlL75OIjkSgkYPXI.mV7ihEPjxIiCkXKBEc7/r9xUIjZyc9i', '02');
    insert into mst_role (role_id, role_name) values('ROLE_ADMIN', '管理者');
    insert into mst_role (role_id, role_name) values('ROLE_USER', '一般');
    insert into mst_role (role_id, role_name) values('ROLE_ACTUATOR', '運用管理');


Doma2のDAOをテストするサンプルです。
\ ``@SQL`` \アノテーションで指定されたSQLを実行してテスト対象のデータを作成し、単体テストを行います。

MstEmployeeDaoTest.java

.. code-block:: java

    @RunWith(SpringRunner.class) // テストランナーでSpringRunner.classを指定してSpring Test Contextを使用
    @JdbcTest // DB部分のコンポーネントだけロードする ※1
    @Import(DomaAutoConfiguration.class) // Doma2のconfigurationをimport
    @ComponentScan // テスト対象の実装Beanをscan
    @Sql(scripts = "../../../schema-dev.sql") // DMLを実行する。ここではテーブルをDrop&Createしている
    @Sql(scripts = "data_employee.sql") // テスト対象のデータをInsertしている
    public class MstEmployeeDaoTest {

        @Autowired
        MstEmployeeDao dao;

        @Test
        public void selectAll() {

            List<MstEmployee> mstEmployees = dao.selectAll();
            assertThat(mstEmployees.size(), is(3));
        }

        @Test
        public void selectOne() {

            MstEmployee actual = dao.selectOne("01");
            assertThat(actual.getEmployeeLastName(), is("管理"));
            assertThat(actual.getEmployeeFirstName(), is("太郎"));
            assertThat(actual.getRoleId(), is("ROLE_ADMIN"));

            actual = dao.selectOne("100");
            assertNull(actual);
        }

        @Test
        public void selectUser(){

            UserEntity actual = dao.selectUser("01");
            assertThat(actual.getEmployeeLastName(), is("管理"));
            assertThat(actual.getEmployeeFirstName(), is("太郎"));
            assertThat(actual.getRoleId(), is("ROLE_ADMIN"));
            assertThat(actual.getPassword(), is("$2a$10$1gJJgBlL75OIjkSgkYPXI.mV7ihEPjxIiCkXKBEc7/r9xUIjZyc9i"));

            actual = dao.selectUser("100");
            assertNull(actual);
        }
    }

※1
https://docs.spring.io/spring-boot/docs/current/reference/html/test-auto-configuration.html


Doma2自動生成のメソッドですが、insert,update,deleteもテストしておきたいです。

MstNewsDaoTest.java

.. code-block:: java

    @RunWith(SpringRunner.class)
    @JdbcTest
    @Import(DomaAutoConfiguration.class)
    @ComponentScan
    @Sql(scripts = "../../../schema-dev.sql")
    @Sql(scripts = "data_news.sql")
    public class MstNewsDaoTest {

        @Autowired
        MstNewsDao dao;

    // 中略

        @Test
        public void insert() {
            MstNews news = new MstNews();

            news.setSubject("単体テスト");
            news.setUrl("http://test.url");
            news.setRoleId("ROLE_ADMIN");

            dao.insert(news);

            List<NewsDto> newsDtoList = dao.selectNewsDtoByCond("単体テスト", null, null, getDefaultSelectOptions());

            NewsDto dto = newsDtoList.get(0);
            assertThat(dto.getSubject(), is("単体テスト"));
            assertThat(dto.getRoleId(),is("ROLE_ADMIN"));
            assertThat(dto.getRoleNm(), is("管理者"));
            assertThat(dto.getUrl(), is("http://test.url"));
        }

        @Test
        public  void update() {

            List<MstNews> mstNewsList = dao.selectAll();
            MstNews news = mstNewsList.get(0);

            news.setSubject("更新テスト");
            news.setUrl("http://test.update.url");
            news.setRoleId("ROLE_ADMIN");
            int ver = news.getVersion();

            dao.update(news);

            List<NewsDto> newsDtoList = dao.selectNewsDtoByCond("更新テスト", null, null, getDefaultSelectOptions());

            NewsDto dto = newsDtoList.get(0);
            assertThat(dto.getSubject(), is("更新テスト"));
            assertThat(dto.getRoleId(),is("ROLE_ADMIN"));
            assertThat(dto.getRoleNm(), is("管理者"));
            assertThat(dto.getUrl(), is("http://test.update.url"));
            assertThat(dto.getVersion(), is(ver + 1));
        }

        @Test
        public void delete() {

            List<MstNews> mstNewsList = dao.selectAll();
            int size = mstNewsList.size();
            MstNews mstNews = mstNewsList.get(0);

            dao.delete(mstNews);

            mstNewsList = dao.selectAll();

            assertThat(mstNewsList.size(), is(size - 1));

        }

       private SelectOptions getDefaultSelectOptions() {
            // 最初のページ
            int pageNo = 0;
            // ページあたり件数
            int sizePerPage = 5;
            // offset指定、最大100件、カウントあり
            int offset = pageNo * sizePerPage;
            return SelectOptions.get().offset(offset).limit(100).count();
        }

    // 後略

    }

コントローラクラスのテスト
--------------------------

コントローラのテストを実行します。
ここでは、以下をテスト対象とします。

#. リクエストマッピング
#. 認証
#. バリデーションのメッセージ

NewsManagerListControllerTest.java

.. code-block:: java

    @RunWith(SpringRunner.class)
    @WebMvcTest(value = NewsManagerListController.class) // テスト対象のコントローラを指定
    @Import(SecurityConfig.class) // 認証の設定を追加でロード
    public class NewsManagerListControllerTest {

        // 擬似的なリクエストをDispatcher Servletにリクエストするモック
        @Autowired
        private MockMvc mvc;

        @MockBean
        NewsService mockService;

        @Before
        public void setup() {

            // セキュリティ設定
            AuthenticationTestHelper.管理者権限の設定();
            // modelAttribute
            Mockito.when(mockService.retrieveRoleIdMap()).thenReturn(new HashMap<String, String>());
        }

        @Test
        public void 重要なお知らせリスト画面_リクエストマッピング() throws Exception {

            this.mvc.perform(
                    MockMvcRequestBuilders.get("/manager/news/list")
                            .with(csrf())
            ).andExpect(status().isOk())
            .andExpect(view().name("/manager/news/list/newsList"));
        }

        @Test
        public void 重要なお知らせリスト画面_一般ユーザでエラーになる() throws Exception {

            // セキュリティ設定
            AuthenticationTestHelper.一般権限の設定();
            this.mvc.perform(
                    MockMvcRequestBuilders.get("/manager/news/list")
                            .with(csrf())
            ).andExpect(status().is4xxClientError());
        }
    }

\ ``重要なお知らせリスト画面_リクエストマッピング`` \メソッドでは「/manager/news/list」に対するリクエストに対し、
HTTPステータスの200が返却され、ビュー「/manager/news/list/newsList」が返却されることをテストしています。
Spring Securityが設定されているため、CSRFトークンが設定される必要があるので\ ``with(csrf())`` \でCSRFトークンが設定されています。
\ ``setup`` \メソッドでは\ ``AuthenticationTestHelper`` \クラスで認証設定をしています。

AuthenticationTestHelper.java

.. code-block:: java

    public class AuthenticationTestHelper {

        public static void 管理者権限の設定() {

            // 認証状態にする
            UserInfo userInfo = new UserInfo();

            userInfo.setId("01");
            userInfo.setEmployeeFirstName("テスト");
            userInfo.setEmployeeLastName("太郎");
            userInfo.setRoleId("ROLE_ADMIN");
            userInfo.setPassword("pwd");

            LoginUserDetails details = new LoginUserDetails(userInfo);

            Authentication authentication =
                    new TestingAuthenticationToken(details, null, "ROLE_ADMIN");

            setAuthentication(authentication);

        }

        public static void 一般権限の設定() {

            // 認証状態にする
            UserInfo userInfo = new UserInfo();

            userInfo.setId("01");
            userInfo.setEmployeeFirstName("テスト");
            userInfo.setEmployeeLastName("太郎");
            userInfo.setRoleId("ROLE_USER");
            userInfo.setPassword("pwd");

            LoginUserDetails details = new LoginUserDetails(userInfo);

            Authentication authentication =
                    new TestingAuthenticationToken(details, null, "ROLE_USER");

            setAuthentication(authentication);

        }

        private static void setAuthentication(Authentication authentication) {
            SecurityContext securityContext;
            securityContext = SecurityContextHolder.createEmptyContext();
            securityContext.setAuthentication(authentication);
            SecurityContextHolder.setContext(securityContext);
        }
    }

AuthenticationTestHelperクラスではSecurityContextHolderにSecurityContextを設定し、Spring Securityの認証状態を設定します。


NewsManagerRegisterControllerTest.java

.. code-block:: java

    @RunWith(SpringRunner.class)
    @WebMvcTest(value = NewsManagerRegisterController.class)
    @Import(SecurityConfig.class)
    public class NewsManagerRegisterControllerTest {

        @Autowired
        private MockMvc mvc;

        @MockBean
        NewsService mockService;

        @Before
        public void setup() {

            AuthenticationTestHelper.管理者権限の設定();
            // modelAttribute
            Mockito.when(mockService.retrieveRoleIdMap()).thenReturn(new HashMap<String, String>());
        }

        @Test
        public void お知らせ登録確認画面_バリデーションチェック() throws Exception {

            MvcResult result = this.mvc.perform(
                    MockMvcRequestBuilders.post("/manager/news/register")
                            .param("confirm", "")
                            .param("url", "hoge://a.b")
                            .param("subject", "")
                            .with(csrf())
            )
                    .andExpect(status().isOk())
                    .andExpect(model().hasErrors())
                    .andExpect(view().name("/manager/news/register/newsRegisterInput"))
                    .andReturn();

            // エラーメッセージの確認
            String content = result.getResponse().getContentAsString();

            assertThat(content, is(containsString("お知らせURLの形式が正しくありません。")));
            assertThat(content, is(containsString("お知らせ表題が入力されていません。")));
            assertThat(content, is(containsString("権限IDが入力されていません。")));

        }

    }

NewsForm.java

.. code-block:: java

    /**
     * お知らせ画面フォームクラス.
     */
    public class NewsForm {

        /** id */
        private Long id;
        /** ページ */
        private Integer page;
        /** 表題 */
        @NotBlank
        private String subject;
        /** 権限 */
        @NotBlank
        private String roleId;
        /** 権限名 */
        private String roleNm;
        /** URL */
        @NotBlank
        @URL(message = "お知らせURLの形式が正しくありません。")
        private String url;
        /** バージョン */
        private int version;

        // getter.setter
    }

ValidationMessages.properties

.. code-block:: text

    org.hibernate.validator.constraints.NotBlank.message = {0}が入力されていません。

message.properties

.. code-block:: text

    newsForm.subject = お知らせ表題
    newsForm.url = お知らせURL
    newsForm.roleId = 権限ID

バリデーションチェックの結果、出力されるメッセージをレスポンスの文字列で確認しています。
エラーメッセージは\ ``ValidationMessages.properties`` \,\ ``messages.properties`` \,Formクラスが関連して出力されるので、ユニットテストで確認しておいた方が無難です。
