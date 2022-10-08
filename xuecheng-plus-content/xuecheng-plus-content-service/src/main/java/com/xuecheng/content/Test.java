package com.xuecheng.content;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {

    public static ExecutorService threadPool = Executors.newFixedThreadPool(10);
    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            get();
            set();
        }

    }

    public static void get(){
        threadPool.execute(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("快去吃饭");
        });
    }
    public static void set(){
        threadPool.execute(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("就不去");
        });
    }

}
