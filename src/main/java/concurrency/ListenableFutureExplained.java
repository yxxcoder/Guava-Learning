package concurrency;

import com.google.common.util.concurrent.*;

import java.util.Random;
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
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        // Callable+Future方式
        Future<Integer> future = executorService.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws InterruptedException {
                TimeUnit.SECONDS.sleep(1);
                return new Random().nextInt(100);
            }
        });

        System.out.println(future.get());


        // Callable+FutureTask方式
        // FutureTask类实现了RunnableFuture接口, 而RunnableFuture继承了Runnable接口和Future接口
        // 所以它既可以作为Runnable被线程执行，又可以作为Future得到Callable的返回值
        FutureTask<Integer> futureTask = new FutureTask<Integer>(new Callable<Integer>() {
            @Override
            public Integer call() throws InterruptedException {
                TimeUnit.SECONDS.sleep(1);
                return new Random().nextInt(100);
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
        ListenableFuture<Integer> listenableFuture = service.submit(new Callable<Integer>() {
            public Integer call() throws InterruptedException {
                TimeUnit.SECONDS.sleep(1);
                return new Random().nextInt(100);
            }
        });

        Futures.addCallback(
                listenableFuture,
                new FutureCallback<Integer>() {
                    @Override
                    public void onSuccess(Integer result) {
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
        ListenableFutureTask task = ListenableFutureTask.create(new Callable<Integer>() {

            @Override
            public Integer call() throws Exception {
                return null;
            }
        });

        task = ListenableFutureTask.create(new Runnable() {
            @Override
            public void run() {

            }
        }, 0);

        Futures.addCallback(
                task,
                new FutureCallback<Integer>() {
            @Override
            public void onSuccess(Integer result) {
                System.out.println(result);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        },
        Executors.newSingleThreadExecutor());
    }

}
