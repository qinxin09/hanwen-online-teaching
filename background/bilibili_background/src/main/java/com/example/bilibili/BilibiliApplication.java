package com.example.bilibili;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableCaching
@MapperScan(basePackages = {"com.example.bilibili.dao","com.example.bilibili.dao.diy"})
@ComponentScan(basePackages = "com.example.bilibili.*")
@ServletComponentScan // 启用扫描Servlet相关的配置
public class BilibiliApplication {

    public static void main(String[] args) {
        SpringApplication.run(BilibiliApplication.class, args);
    }

}
