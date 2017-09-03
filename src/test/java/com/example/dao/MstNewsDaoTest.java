package com.example.dao;

import com.example.dto.NewsDto;
import com.example.entity.MstNews;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seasar.doma.boot.autoconfigure.DomaAutoConfiguration;
import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/** Created by ko-aoki on 2017/04/28. */
@RunWith(SpringRunner.class)
@JdbcTest
@Import(DomaAutoConfiguration.class)
@ComponentScan
@Sql(scripts = "../../../schema-dev.sql")
@Sql(scripts = "data_news.sql")
public class MstNewsDaoTest {

  @Autowired MstNewsDao dao;

  @Test
  public void selectAll() {

    List<MstNews> mstNewsList = dao.selectAll();
    assertThat(mstNewsList.size(), is(11));
  }

  @Test
  public void selectNewsDtoByCond() {

    List<NewsDto> newsDtoList =
        dao.selectNewsDtoByCond(null, null, null, getDefaultSelectOptions());

    assertThat(newsDtoList.size(), is(11));

    // 表題：テスト
    newsDtoList = dao.selectNewsDtoByCond("テスト", null, null, getDefaultSelectOptions());
    assertThat(newsDtoList.size(), is(9));
    // 権限：ROLE_ADMIN
    newsDtoList = dao.selectNewsDtoByCond("", "ROLE_ADMIN", null, getDefaultSelectOptions());
    assertThat(newsDtoList.size(), is(2));
    // URL:http://hoge/test2
    newsDtoList = dao.selectNewsDtoByCond("", null, "http://hoge/test", getDefaultSelectOptions());
    assertThat(newsDtoList.size(), is(2));
  }

  @Test
  public void insert() {
    MstNews news = new MstNews();

    news.setSubject("単体テスト");
    news.setUrl("http://test.url");
    news.setRoleId("ROLE_ADMIN");

    dao.insert(news);

    List<NewsDto> newsDtoList =
        dao.selectNewsDtoByCond("単体テスト", null, null, getDefaultSelectOptions());

    NewsDto dto = newsDtoList.get(0);
    assertThat(dto.getSubject(), is("単体テスト"));
    assertThat(dto.getRoleId(), is("ROLE_ADMIN"));
    assertThat(dto.getRoleNm(), is("管理者"));
    assertThat(dto.getUrl(), is("http://test.url"));
  }

  @Test
  public void update() {

    List<MstNews> mstNewsList = dao.selectAll();
    MstNews news = mstNewsList.get(0);

    news.setSubject("更新テスト");
    news.setUrl("http://test.update.url");
    news.setRoleId("ROLE_ADMIN");
    int ver = news.getVersion();

    dao.update(news);

    List<NewsDto> newsDtoList =
        dao.selectNewsDtoByCond("更新テスト", null, null, getDefaultSelectOptions());

    NewsDto dto = newsDtoList.get(0);
    assertThat(dto.getSubject(), is("更新テスト"));
    assertThat(dto.getRoleId(), is("ROLE_ADMIN"));
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
}
