package com.flybirds.web.thread.config;

/**
 * ThreadPoolConstant 线程的基本参数
 *
 *  默认情况下，在创建了线程池后，线程池中的线程数为0，当有任务来之后，就会创建一个线程去执行任务，
 *  当线程池中的线程数目达到corePoolSize后，就会把到达的任务放到缓存队列当中；
 *  当队列满了，就继续创建线程，当线程数量大于等于maxPoolSize后，开始使用拒绝策略拒绝
 * @author :flybirds
 */
public interface ThreadPoolConstant {

    /** 核心线程数（默认线程数） */
    int corePoolSize = 20;
    /** 最大线程数 */
    int maxPoolSize = 100;
    /** 允许线程空闲时间（单位：默认为秒） */
    int keepAliveTime = 10;
    /** 缓冲队列大小 */
    int queueCapacity = 200;
    /** 线程池名前缀 */
    String threadNamePrefix = "Async-Service-";
}
