package com.example.web;

import com.example.security.LoginUserDetails;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;

/**
 * トップ画面のコントローラ.
 */
@Controller
@RequestMapping("/")
public class TopController {

    @Value("${app.uploadPath}")
    private String UPLOAD_PATH;

    /** ロガー */
    private static final Logger logger = LoggerFactory.getLogger(TopController.class);

    /**
     * トップ画面.
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public String top(Model model, @AuthenticationPrincipal LoginUserDetails userDetails) {

        return "top/top";
    }

    /**
     *
     * 動画を送信するstream.
     * http://stackoverflow.com/questions/29073085/streaming-large-video-using-spring-mvc
     * @return
     */
    @RequestMapping(value="video/{fileName}", method = RequestMethod.GET)
    public void stream(@PathVariable String fileName,
                       HttpServletResponse response, @AuthenticationPrincipal LoginUserDetails userDetails) {

        FileInputStream in = null;
        ServletOutputStream out = null;
        try {

            // TODO 拡張子
            String filePath = UPLOAD_PATH + fileName + ".mp4";

            int fileSize = (int) new File(filePath).length();
            response.setContentLength(fileSize);
            response.setContentType("video/mp4");
            in = new FileInputStream(filePath);
            out = response.getOutputStream();
            int value = IOUtils.copy(in, out);
            System.out.println("File Size :: " + fileSize);
            System.out.println("Copied Bytes :: " + value);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (java.io.FileNotFoundException e) {
            e.printStackTrace();
            response.setStatus(HttpStatus.NOT_FOUND.value());
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        } finally {
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(out);
        }
    }
}
