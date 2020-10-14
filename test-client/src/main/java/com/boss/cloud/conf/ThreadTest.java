package com.boss.cloud.conf;

import java.util.concurrent.TimeUnit;

/**
 * @author: lpb
 * @create: 2020-09-28 16:34
 */
public class ThreadTest {

    public static void main(String[] args) throws InterruptedException {
        ThreadTest threadTest = new ThreadTest();
        threadTest.test();
    }

    public void test() throws InterruptedException {
        Thread a = new Thread(() -> {
            testMethod();
        });
        Thread b = new Thread(() -> {
            testMethod();
        });
        a.start();
        Thread.sleep(2000L);
        b.start();
        System.out.println("Main" + Thread.currentThread().getName() + ":" + Thread.currentThread().getState());
        System.out.println("线程a" + a.getName() + ":" + a.getState());
        System.out.println(b.getName() + ":" + b.getState());
    }

    public synchronized void testMethod() {
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
