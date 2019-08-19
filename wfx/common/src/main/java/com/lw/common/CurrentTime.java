package com.lw.common;

import java.text.SimpleDateFormat;

import org.springframework.stereotype.Component;

/**
 * 	获取当前时间线程
 * @author liwen
 *
 */
@Component    //纳入SPRING IOC容器
public class CurrentTime {
	
	private ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal<SimpleDateFormat>(){
        @Override
        public SimpleDateFormat get(){
            return new SimpleDateFormat("yyyyMMddmmss");
        }

    };

    public ThreadLocal<SimpleDateFormat> getThreadLocal() {
        return threadLocal;
    }

}
