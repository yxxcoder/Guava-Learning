package concurrency;

import com.google.common.util.concurrent.*;
import java.util.concurrent.*;

/**
 * 完成后触发回调的Future
 *
 * @author
 * @create 2018-05-17 下午11:32
 **/
public class ListenableFutureExplained {
    public static void main(String args[]) throws ExecutionException, InterruptedException {

        /**
         * Java原生并发编程
         */
        ExecutorService executorService = Executors.newCachedThreadPool();

        // Callable+Future方式
        Future<String> future = executorService.submit(new Callable<String>() {
            @Override
            public String call() throws InterruptedException {
                TimeUnit.SECONDS.sleep(1);
                return "Callable+Future";
            }
        });

        System.out.println(future.get());


        // Callable+FutureTask方式
        // FutureTask类实现了RunnableFuture接口, 而RunnableFuture继承了Runnable接口和Future接口
        // 所以它既可以作为Runnable被线程执行，又可以作为Future得到Callable的返回值
        FutureTask<String> futureTask = new FutureTask<String>(new Callable<String>() {
            public String call() throws InterruptedException {
                TimeUnit.SECONDS.sleep(1);
                return "Callable+FutureTask";
            }
        });
         executorService.submit(futureTask);
        /**
         * 这种方式和executorService.submit效果是类似的
         * 只不过一个使用的是ExecutorService，一个使用的是Thread
         */
        // new Thread(futureTask).start();
        System.out.println(futureTask.get());



        /**
         * ListenableFuture的创建
         */
        ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));
        ListenableFuture<String> listenableFuture = service.submit(new Callable<String>() {
            public String call() throws InterruptedException {
                TimeUnit.SECONDS.sleep(1);
                return "ListenableFuture";
            }
        });

        Futures.addCallback(
                listenableFuture,
                new FutureCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println(result);
                        service.shutdown();
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        service.shutdown();
                    }
                },
                service
        );

        /**
         *  ListenableFutureTask
         */
        ListenableFutureTask task = ListenableFutureTask.create(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "ListenableFutureTask";
            }
        });
//        new Thread(task).start();
        executorService.submit(task);
        System.out.println(task.get());

        ListenableFutureTask task2 = ListenableFutureTask.create(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(2L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, 0);
        executorService.submit(task2);
        System.out.println(task2.get());


        // 运行完记得关掉
        executorService.shutdown();
    }

}
