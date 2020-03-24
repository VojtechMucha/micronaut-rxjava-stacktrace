# Micronaut RXJava Stacktrace
This simple Micronaut application demonstrates that following exception appears in stderr:
```
    io.reactivex.exceptions.UndeliverableException: The exception could not be delivered to the consumer because it has already canceled/disposed the flow or the exception has nowhere to go to begin with. Further reading: https://github.com/ReactiveX/RxJava/wiki/What's-different-in-2.0#error-handling | io.micronaut.http.client.exceptions.HttpClientException: Connect Error: connection timed out: www.google.com/172.217.23.196:81
        at io.reactivex.plugins.RxJavaPlugins.onError(RxJavaPlugins.java:367)
        at io.reactivex.internal.operators.flowable.FlowableCreate$BaseEmitter.onError(FlowableCreate.java:275)
        at io.micronaut.http.client.DefaultHttpClient.lambda$null$27(DefaultHttpClient.java:1056)
        at io.netty.util.concurrent.DefaultPromise.notifyListener0(DefaultPromise.java:577)
        at io.netty.util.concurrent.DefaultPromise.notifyListeners0(DefaultPromise.java:570)
        at io.netty.util.concurrent.DefaultPromise.notifyListenersNow(DefaultPromise.java:549)
        at io.netty.util.concurrent.DefaultPromise.notifyListeners(DefaultPromise.java:490)
        at io.netty.util.concurrent.DefaultPromise.setValue0(DefaultPromise.java:615)
        at io.netty.util.concurrent.DefaultPromise.setFailure0(DefaultPromise.java:608)
        at io.netty.util.concurrent.DefaultPromise.tryFailure(DefaultPromise.java:117)
        at io.netty.channel.nio.AbstractNioChannel$AbstractNioUnsafe$1.run(AbstractNioChannel.java:263)
        at io.netty.util.concurrent.PromiseTask.runTask(PromiseTask.java:98)
        at io.netty.util.concurrent.ScheduledFutureTask.run(ScheduledFutureTask.java:170)
        at io.netty.util.concurrent.AbstractEventExecutor.safeExecute(AbstractEventExecutor.java:164)
        at io.netty.util.concurrent.SingleThreadEventExecutor.runAllTasks(SingleThreadEventExecutor.java:472)
        at io.netty.channel.nio.NioEventLoop.run(NioEventLoop.java:500)
        at io.netty.util.concurrent.SingleThreadEventExecutor$4.run(SingleThreadEventExecutor.java:989)
        at io.netty.util.internal.ThreadExecutorMap$2.run(ThreadExecutorMap.java:74)
        at io.micronaut.scheduling.instrument.InvocationInstrumenterWrappedRunnable.run(InvocationInstrumenterWrappedRunnable.java:48)
        at io.netty.util.concurrent.FastThreadLocalRunnable.run(FastThreadLocalRunnable.java:30)
        at java.base/java.lang.Thread.run(Thread.java:834)
    Caused by: io.micronaut.http.client.exceptions.HttpClientException: Connect Error: connection timed out: www.google.com/172.217.23.196:81
        at io.micronaut.http.client.DefaultHttpClient.lambda$null$27(DefaultHttpClient.java:1057)
        ... 18 more
    Caused by: io.netty.channel.ConnectTimeoutException: connection timed out: www.google.com/172.217.23.196:81
        at io.netty.channel.nio.AbstractNioChannel$AbstractNioUnsafe$1.run(AbstractNioChannel.java:261)
        ... 10 more
    Exception in thread "nioEventLoopGroup-1-4" io.reactivex.exceptions.UndeliverableException: The exception could not be delivered to the consumer because it has already canceled/disposed the flow or the exception has nowhere to go to begin with. Further reading: https://github.com/ReactiveX/RxJava/wiki/What's-different-in-2.0#error-handling | io.micronaut.http.client.exceptions.HttpClientException: Connect Error: connection timed out: www.google.com/172.217.23.196:81
        at io.reactivex.plugins.RxJavaPlugins.onError(RxJavaPlugins.java:367)
        at io.reactivex.internal.operators.flowable.FlowableCreate$BaseEmitter.onError(FlowableCreate.java:275)
        at io.micronaut.http.client.DefaultHttpClient.lambda$null$27(DefaultHttpClient.java:1056)
        at io.netty.util.concurrent.DefaultPromise.notifyListener0(DefaultPromise.java:577)
        at io.netty.util.concurrent.DefaultPromise.notifyListeners0(DefaultPromise.java:570)
        at io.netty.util.concurrent.DefaultPromise.notifyListenersNow(DefaultPromise.java:549)
        at io.netty.util.concurrent.DefaultPromise.notifyListeners(DefaultPromise.java:490)
        at io.netty.util.concurrent.DefaultPromise.setValue0(DefaultPromise.java:615)
        at io.netty.util.concurrent.DefaultPromise.setFailure0(DefaultPromise.java:608)
        at io.netty.util.concurrent.DefaultPromise.tryFailure(DefaultPromise.java:117)
        at io.netty.channel.nio.AbstractNioChannel$AbstractNioUnsafe$1.run(AbstractNioChannel.java:263)
        at io.netty.util.concurrent.PromiseTask.runTask(PromiseTask.java:98)
        at io.netty.util.concurrent.ScheduledFutureTask.run(ScheduledFutureTask.java:170)
        at io.netty.util.concurrent.AbstractEventExecutor.safeExecute(AbstractEventExecutor.java:164)
        at io.netty.util.concurrent.SingleThreadEventExecutor.runAllTasks(SingleThreadEventExecutor.java:472)
        at io.netty.channel.nio.NioEventLoop.run(NioEventLoop.java:500)
        at io.netty.util.concurrent.SingleThreadEventExecutor$4.run(SingleThreadEventExecutor.java:989)
        at io.netty.util.internal.ThreadExecutorMap$2.run(ThreadExecutorMap.java:74)
        at io.micronaut.scheduling.instrument.InvocationInstrumenterWrappedRunnable.run(InvocationInstrumenterWrappedRunnable.java:48)
        at io.netty.util.concurrent.FastThreadLocalRunnable.run(FastThreadLocalRunnable.java:30)
        at java.base/java.lang.Thread.run(Thread.java:834)
    Caused by: io.micronaut.http.client.exceptions.HttpClientException: Connect Error: connection timed out: www.google.com/172.217.23.196:81
        at io.micronaut.http.client.DefaultHttpClient.lambda$null$27(DefaultHttpClient.java:1057)
        ... 18 more
    Caused by: io.netty.channel.ConnectTimeoutException: connection timed out: www.google.com/172.217.23.196:81
        at io.netty.channel.nio.AbstractNioChannel$AbstractNioUnsafe$1.run(AbstractNioChannel.java:261)
        ... 10 more
```

You can simply see it by executing `gw test` or `UndeliverableExceptionTest`. This row is responsible for printing the exception: `RxJavaPlugins(382)`.

Notes:
* Using reactor.
* `GoogleCallingController` returns Mono with 1s timeout. It calls `GoogleClient`
* `GoogleClient` tries to connect to port 81 which causes connection timeout. Timeout is set to 2s in `application.yml`.
* It means that the controller result Mono timeouts before `GoogleClient`.
* When `GoogleClient` timeouts then the exception appears.
* The same or similar issue is already described in https://github.com/micronaut-projects/micronaut-core/issues/1799