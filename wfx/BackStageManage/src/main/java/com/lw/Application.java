package com.lw;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**	
 * 	启动类
 * @author liwen
 *
 */
@EnableJms    //启动消息队列
@SpringBootApplication
@MapperScan(basePackages = {"com.lw.mapper"})
@EnableTransactionManagement(proxyTargetClass = true)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}