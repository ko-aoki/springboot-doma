package com.example.service;

import com.example.dao.TrnNewsDao;
import com.example.dto.NewsDto;
import com.example.entity.TrnNews;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by ko-aoki on 2016/11/06.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {"spring.datasource.url:jdbc:h2:~/dev/Java/ide/ws/springboot-doma/work/db/db;DB_CLOSE_ON_EXIT=FALSE"}
)
public class NewsServiceImplTest {

    @Autowired
    NewsService service;
    @Autowired
    TrnNewsDao dao;

    @Test
    public void addNews() throws Exception {

        NewsDto dto = new NewsDto();
        dto.setRoleId("01");
        dto.setSubject("表題");
        dto.setUrl("http://a.b");
        service.addNews(dto);

        List<TrnNews> trnNewses = dao.selectAll();
        assertEquals(3, trnNewses.size());

    }

    @Test
    public void findNewsList() {

        List<NewsDto> newsList = service.findNewsList("", "", "");
        assertEquals(2, newsList.size());

        newsList = service.findNewsList(null, null, null);
        assertEquals(2, newsList.size());

        newsList = service.findNewsList("テスト", null, null);
        assertEquals(1, newsList.size());

        newsList = service.findNewsList(null, "01", null);
        assertEquals(1, newsList.size());

        newsList = service.findNewsList(null, null, "http://hoge/test2");
        assertEquals(1, newsList.size());
    }

    @Test
    public void modifyNews() {
        NewsDto news = service.findNews(1L);
        assertEquals(0, news.getVersion());

        news.setSubject("更新後");
        service.modifyNews(news);

        news = service.findNews(1L);

        assertEquals("更新後", news.getSubject());
        assertEquals(1, news.getVersion());
    }
}