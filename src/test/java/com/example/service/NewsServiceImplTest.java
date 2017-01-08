package com.example.service;

import com.example.dao.MstNewsDao;
import com.example.dto.NewsDto;
import com.example.entity.MstNews;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
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
    MstNewsDao dao;

    @Test
    public void addNews() throws Exception {

        NewsDto dto = new NewsDto();
        dto.setRoleId("01");
        dto.setSubject("表題");
        dto.setUrl("http://a.b");
        service.addNews(dto);

        List<MstNews> mstNewses = dao.selectAll();
        assertEquals(12, mstNewses.size());

        MstNews mstNews = new MstNews();
        // ゴミ掃除
        mstNews.setMstNewsId(12L);
        dao.delete(mstNews);
    }

    @Test
    public void findNewsPage() {

        // 条件なし 1ページ
        Page<NewsDto> newsList = service.findNewsPage("", "", "", 0);
        System.out.println(newsList.getContent().size());
        System.out.println(newsList.getSize());
        System.out.println(newsList.getNumber());
        System.out.println(newsList.getNumberOfElements());
        System.out.println(newsList.getTotalElements());
        System.out.println(newsList.getTotalPages());

        assertEquals(11, newsList.getTotalElements());
        assertEquals(5, newsList.getNumberOfElements());
        assertEquals(3, newsList.getTotalPages());

        // 条件なし 2ページ
        newsList = service.findNewsPage("", "", "", 1);
        assertEquals(11, newsList.getTotalElements());
        assertEquals(5, newsList.getNumberOfElements());
        assertEquals(3, newsList.getTotalPages());

        // 条件なし 3ページ
        newsList = service.findNewsPage("", "", "", 2);
        assertEquals(11, newsList.getTotalElements());
        assertEquals(1, newsList.getNumberOfElements());
        assertEquals(3, newsList.getTotalPages());


        // 条件なし 1ページ
        newsList = service.findNewsPage(null, null, null, 0);
        assertEquals(11, newsList.getTotalElements());
        assertEquals(5, newsList.getNumberOfElements());
        assertEquals(3, newsList.getTotalPages());

        // 表題：テスト 1ページ
        newsList = service.findNewsPage("テスト", null, null, 0);

        assertEquals(9, newsList.getTotalElements());
        assertEquals(5, newsList.getNumberOfElements());
        assertEquals(2, newsList.getTotalPages());

        // 権限：ROLE_ADMIN
        newsList = service.findNewsPage(null, "ROLE_ADMIN", null, 0);
        assertEquals(2, newsList.getTotalElements());
        assertEquals(2, newsList.getNumberOfElements());
        assertEquals(1, newsList.getTotalPages());

        // URL:http://hoge/test2
        newsList = service.findNewsPage(null, null, "http://hoge/test2", 0);
        assertEquals(1, newsList.getTotalElements());
        assertEquals(1, newsList.getNumberOfElements());
        assertEquals(1, newsList.getTotalPages());
    }

    @Test
    public void modifyNews() {
        NewsDto news = service.findNews(1L);
        assertEquals(0, news.getVersion());
        // 正常
        news.setSubject("更新後");
        service.modifyNews(news);

        news = service.findNews(1L);

        assertEquals("更新後", news.getSubject());
        assertEquals(1, news.getVersion());

        // 楽観排他
        news.setSubject("更新後");
        try {
            service.modifyNews(news);
            service.modifyNews(news);
        } catch (OptimisticLockingFailureException e) {
            assertEquals(true, true);
        }
    }
}