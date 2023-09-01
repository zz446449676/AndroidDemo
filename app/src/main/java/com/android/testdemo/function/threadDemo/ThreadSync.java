package com.android.testdemo.function.threadDemo;

import android.util.Log;

import com.android.testdemo.function.strategy.Strategy;

// 多线程使用案例，线程同步
// 我有三个线程分别是thread1, thread2，thread3,我想让他们按顺序执行，也就是thread1,执行完才能执行thread2，然后再执行thread3
public class ThreadSync implements Strategy {
    Thread thread1, thread2, thread3;

    // 创建一个共享的唤醒对象
    private static final Object lock = new Object();
    @Override
    public void run() {
        try {
            fun2();
        } catch (Exception ignored) {
            Log.e("zhang", "Exception : " + ignored);
        }
    }

    // 方法1，使用join
    // join()方法是一个线程的方法，它表示当前线程需要等待调用join()方法的线程执行完毕后才能继续执行。
    // 通常，join()方法用在主线程中等待从属线程执行完毕，以确保程序能够按照预期的顺序执行。如果主线程没有调用任何线程的join()方法，那么主线程可能会在其他线程之前结束。
    // 在实际应用中，wait()和join()方法通常与synchronized关键字一起使用，用于实现线程间的通信和同步控制
    private void fun1() throws InterruptedException {
        // 初始化线程
        thread1 = new Thread(() -> {
            try {
                // 模拟耗时操作
                Thread.sleep(1000);

                System.out.println("线程1执行");
            } catch (Exception ignored) {}
        });

        thread2 = new Thread(() -> {
            try {
                // 模拟耗时操作
                Thread.sleep(500);
                System.out.println("线程2执行");
            } catch (Exception ignore) {}
        });

        thread3 = new Thread(() -> {
            try {
                System.out.println("线程3执行");
            } catch (Exception ignore) {}
        });

        // 如果没有同步机制，控制台输出的信息应该为：
        // 线程3执行
        // 线程2执行
        // 线程1执行

        System.out.println("同步后的执行顺序如下 ：");
        thread1.start();
        thread1.join();
        thread2.start();
        thread2.join();
        thread3.start();
    }

    // 方法2，使用锁
    private void fun2() throws InterruptedException {
        System.out.println("fun 2 开始执行");
        thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    try {
                        // 模拟耗时操作
                        Thread.sleep(1500);
                        System.out.println("线程1执行");
                    } catch (Exception ignored) {} finally {
                        lock.notify();
                    }
                }
            }
        });

        thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    try {
                        // 模拟耗时操作
                        if (thread1.isAlive()) {
                            lock.wait();
                        }
                        System.out.println("线程2执行");
                    } catch (Exception ignored) {} finally {
                        lock.notify();
                    }
                }
            }
        });

        thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    try {
                        if (thread2.isAlive() || thread1.isAlive()) {
                            lock.wait();
                        }
                        System.out.println("线程3执行");
                    } catch (Exception ignored) {} finally {
                        lock.notify();
                    }
                }
            }
        });

        thread1.start();
        thread2.start();
        thread3.start();
        // 等待最后的thread3执行完毕后，再继续后续，否则主进程就执行完退出了
        thread3.join();
    }
}
