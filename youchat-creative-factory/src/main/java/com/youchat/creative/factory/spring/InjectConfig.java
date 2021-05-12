package com.youchat.creative.factory.spring;

import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncExecutionAspectSupport;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.support.TaskUtils;

import java.util.concurrent.*;

@Configuration
@ComponentScan("com.youchat.creative.factory.spring")
@EnableAsync
@EnableAspectJAutoProxy(proxyTargetClass = true)
@Slf4j
public class InjectConfig implements AsyncConfigurer {

    //    public static final RemoteClient remoteClient = RemoteClient.builder().ipAddr("127.0.0.1").type("1").build();
//
//    public static final RemoteClient remoteClientV2 = RemoteClient.builder().ipAddr("127.0.0.2").type("2").build();
//
//    @Bean
//    public SimpleAsyncTaskExecutor simpleAsyncTaskExecutor() {
//        return new SimpleAsyncTaskExecutor();
//    }

    /**
     * 事件广播器
     *
     * @return
     */
//    @Bean
//    public SimpleApplicationEventMulticaster applicationEventMulticaster() {
//        SimpleApplicationEventMulticaster simpleApplicationEventMulticaster = new SimpleApplicationEventMulticaster();
//        // 若设置 以下代码这 完全异步发送
//        simpleApplicationEventMulticaster.setTaskExecutor(Executors.newFixedThreadPool(3));
//        // 以error日志的方式完成事件扫描
//        simpleApplicationEventMulticaster.setErrorHandler(TaskUtils.LOG_AND_SUPPRESS_ERROR_HANDLER);
//
//        return simpleApplicationEventMulticaster;
//    }

    /**
     * 可以自定义 但是 bean name 必须是 applicationEventMulticaster
     *
     * @see ApplicationEventMulticaster
     */
    @Bean("applicationEventMulticaster")
    public TestApplicationEventMulticaster testApplicationEventMulticaster() {
        TestApplicationEventMulticaster multicaster = new TestApplicationEventMulticaster();
        // 若设置 以下代码这 完全异步发送
        multicaster.setTaskExecutor(Executors.newFixedThreadPool(3));
        // 以error日志的方式完成事件扫描
        multicaster.setErrorHandler(TaskUtils.LOG_AND_SUPPRESS_ERROR_HANDLER);

        return multicaster;
    }


    @Bean
    public RemoteClient remoteClientV2() {
        return RemoteClient.builder().ipAddr("127.0.0.2").type("2").build();
    }

    @Bean
    public InjectService injectServiceV2(RemoteClient remoteClientV2) {
        return new InjectServiceImpl(remoteClientV2);
    }

    @Bean
    public RemoteClient remoteClientV1() {
        return RemoteClient.builder().ipAddr("127.0.0.1").type("1").build();
    }

    @Bean
    public InjectService injectService(RemoteClient remoteClientV1) {
        return new InjectServiceImpl(remoteClientV1);
    }

    private static class CustomRejectedExecutionHandler implements RejectedExecutionHandler {
        @Override
        public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {

        }
    }

    @Bean(name = AsyncExecutionAspectSupport.DEFAULT_TASK_EXECUTOR_BEAN_NAME)
    public Executor taskExecutor1() {
        return MoreExecutors.directExecutor();
    }

    @Bean(name = "new_task2")
    public Executor taskExecutor2() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors() * 2
                , Runtime.getRuntime().availableProcessors() * 4
                , 10L, TimeUnit.SECONDS
                , new LinkedBlockingQueue<>(100)
                , new ThreadFactoryBuilder().setNameFormat("AsyncEventPublisher_%d").build()
                , new CustomRejectedExecutionHandler());
        return threadPoolExecutor;
    }

    @Bean("threadPoolTaskExecutor")
    public ThreadPoolTaskExecutor executor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        int corePoolSize = 10;
        executor.setCorePoolSize(corePoolSize);
        int maxPoolSize = 50;
        executor.setMaxPoolSize(maxPoolSize);
        int queueCapacity = 10;
        executor.setQueueCapacity(queueCapacity);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        String threadNamePrefix = "lienbo-threadPoolTaskExecutor-";
        executor.setThreadNamePrefix(threadNamePrefix);
        // 那么线程池从Running状态会进入Shutdown，这是会等待已有的任务执行完毕再关闭线程池。单元测试就会正常执行了。
        // 其中还有一个awaitTerminationSeconds参数是指等待线程池状态到Terminated的最长时间。
        executor.setWaitForTasksToCompleteOnShutdown(true);
        // 使用自定义的跨线程的请求级别线程工厂类19         int awaitTerminationSeconds = 5;
        executor.setAwaitTerminationSeconds(2);
        executor.initialize();
        return executor;
    }

    @Override
    public Executor getAsyncExecutor() {
        return executor();
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (ex, method, params) -> log.error(String.format("执行异步任务'%s'", method), ex);
    }
    // AutowiredAnnotationBeanPostProcessor implement MergedBeanDefinitionPostProcessor  findAutowiringMetadata

    // externallyManagedConfigMembers
}
