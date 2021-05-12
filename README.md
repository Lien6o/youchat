# My idea Incubation factory

## Spring 事件
* 异步发送
```java
@Bean
public SimpleApplicationEventMulticaster applicationEventMulticaster() {
    SimpleApplicationEventMulticaster simpleApplicationEventMulticaster = new SimpleApplicationEventMulticaster();
    simpleApplicationEventMulticaster.setTaskExecutor(EXECUTOR);
    return simpleApplicationEventMulticaster;
}
```
* 异步监听
```java
@Component
@Slf4j
@Async("new_task")
public class EventConsumer {

@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, classes = Event.class)
public void consumer(Event namedEvent) {
    log.info("consumer TransactionalEventListener=" + namedEvent);
}

@Async("new_task_")
@EventListener(classes = Event.class, condition = "#namedEvent.name.equals('lienbo')")
public void consumer2(Event namedEvent) {
    log.info("consumer2 namedEvent=" + namedEvent);
}
}
```

* 事务事件解耦
```java
@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, classes = Event.class)
public void consumer(Event namedEvent) {
    // do some thing after Transaction
}
```

##  Pipeline

```java
public static void main(String[] args) {
    // 头部管节
    Function<Integer, String> start = String::valueOf;
    // 继续增加管节
    Function<Integer, String> pipelineV2 = start
            .andThen(input -> Integer.parseInt(input) + 1)
            .andThen(input -> Collections.singletonList(String.valueOf(input + 1)))
            .andThen(input -> input.get(0));
    // 真实执行
    String execute = pipelineV2.apply(1);
    System.out.println("execute = " + execute);
}
```

## invoke 
```java
@Slf4j
public class BatchCallSupport {

    public static <T, R> List<R> concurrentCall(List<T> requestList, Integer ableSize, Function<List<T>, R> function, ExecutorService executorService, Long eachTimeOutMills) {
        return Lists.partition(requestList, ableSize).stream()
                .map(list -> CompletableFuture.supplyAsync(FunctionSupporter.toSupplier(list, function), executorService))
                .map(call(eachTimeOutMills))
                .collect(Collectors.toList());
    }

    /**
     * 串行化执行
     */
    @SneakyThrows
    public static <T, R> List<R> serializationCall(List<T> target, Integer ableSize, Function<List<T>, R> function) {
        return concurrentCall(target, ableSize, function, MoreExecutors.newDirectExecutorService(), 0L);
    }
    
    public static <T, R> List<R> concurrentCall2(List<T> requestList, Integer ableSize, Function<List<T>, R> function, ExecutorService executorService) {
        return Lists.partition(requestList, ableSize).stream()
                .map(list -> CompletableFuture.supplyAsync(FunctionSupporter.toSupplier(list, function), executorService))
                .map(CompletableFuture::join).collect(Collectors.toList());
    }

    private static <R> Function<CompletableFuture<R>, R> call(Long eachTimeOutMills) {
        return completableFuture -> {
            try {
                return completableFuture.get(eachTimeOutMills, TimeUnit.MILLISECONDS);
            } catch (Exception e) {
                log.error("concurrentCall ", e);
                return null;
            }
        };
    }
}

```

