package com.example.bilibili.util.crawler;
/*

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import java.io.IOException;

public class SeleniumTest {

    public static void main(String[] args) throws IOException, InterruptedException {
        String url= "http://localhost:8080";
//        url="https://www.baidu.com/";
        url="https://lanzoui.com/";
        // 创建HtmlUnitDriver实例
        WebClient webClient = new WebClient(BrowserVersion.CHROME);

        //支持js
        webClient.getOptions().setJavaScriptEnabled(true);
        //忽略js错误
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        //忽略css错误
        //不执行CSS渲染
        webClient.getOptions().setCssEnabled(false);
        //超时时间
        webClient.getOptions().setTimeout(3000);
        //允许重定向
        webClient.getOptions().setRedirectEnabled(true);
        //允许cookie
        webClient.getCookieManager().setCookiesEnabled(true);

        Thread.sleep(1000);
        //开始请求网站
        HtmlPage page = webClient.getPage(url);
        System.out.println("=================================================================================================");
        System.out.println(page.getPage().getDocumentElement());
    }
}*/
