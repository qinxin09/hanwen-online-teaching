package com.example.bilibili.util;

import javax.sql.DataSource;

import com.example.bilibili.BilibiliApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@SpringBootApplication
public class CheckMysql {

    @Autowired
    private DataSource dataSource;

    public static void main(String[] args) {
        SpringApplication.run(CheckMysql.class, args);
    }

    @Bean
    public void checkMysqlStatus() throws SQLException {
        Connection connection = null;
        boolean isConnected = false; // 是否连接到 MySQL
        try {
            // 获取一个连接
            connection = dataSource.getConnection();
            // 执行一个查询语句
            PreparedStatement statement = connection.prepareStatement("SELECT 1");
            ResultSet resultSet = statement.executeQuery();
            // 输出连接状态
            System.out.println("已连接到 MySQL.");
            isConnected = true;
        } catch (Exception e) {
            // 输出连接状态
            System.out.println("无法连接到 MySQL：" + e.getMessage());
        } finally {
            // 关闭连接
            if (connection != null) {
                connection.close();
            }
        }
        // 如果没有连接到 MySQL
        if (!isConnected) {
            System.out.println("无法连接数据库");
        }
    }

}