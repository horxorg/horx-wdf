package org.horx.wdf.launcher.dubbo.provider;

import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.util.concurrent.CountDownLatch;

public class Application {
    public static void main(String[] args) throws Exception {
        FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext("classpath:spring-main.xml");
        context.start();
        CountDownLatch latch = new CountDownLatch(1);
        latch.await();
    }
}
