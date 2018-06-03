package ch05_concurrency;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.*;
import java.util.List;
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
        ExecutorService executorService = Executors.newCachedThreadPool();

        // Callable+Future方式
        Future<String> future = executorService.submit(new Callable<String>() {
            public String call() throws InterruptedException {
                TimeUnit.SECONDS.sleep(1);
                return "Callable+Future";
            }
        });

        System.out.println(future.get());


        // Callable+FutureTask方式 异步任务
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
        final ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));
        ListenableFuture<String> listenableFuture = service.submit(new Callable<String>() {
            public String call() throws InterruptedException {
                return "ListenableFuture";
            }
        });

        listenableFuture.addListener(
                new Runnable() {
                    public void run() {
                        System.out.println("listenableFuture addListener");
                    }
                }, service);

        Futures.addCallback(
                listenableFuture,
                new FutureCallback<String>() {
                    public void onSuccess(String result) {
                        System.out.println(result);
                        service.shutdown();
                    }

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
            public String call() throws Exception {
                return "ListenableFutureTask";
            }
        });
//        new Thread(task).start();
        executorService.submit(task);
        System.out.println(task.get());

        ListenableFutureTask task2 = ListenableFutureTask.create(new Runnable() {
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(2L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, 0);
        executorService.submit(task2);
        // 0
        System.out.println(task2.get());




        /**
         * 复杂链式的异步操作
         *
         * transformAsync(ListenableFuture<A>, AsyncFunction<A, B>, Executor)
         * 返回一个新的ListenableFuture ，该ListenableFuture 返回的result是由传入的AsyncFunction 参数指派到传入的 ListenableFuture中
         *
         * transform(ListenableFuture<A>, Function<A, B>, Executor)
         * 返回一个新的ListenableFuture ，该ListenableFuture 返回的result是由传入的Function 参数指派到传入的 ListenableFuture中
         *
         * allAsList(Iterable<ListenableFuture<V>>)
         * 返回一个ListenableFuture ，该ListenableFuture 返回的result是一个List，List中的值是每个ListenableFuture的返回值
         * 假如传入的其中之一fails或者cancel，这个Future fails 或者canceled
         *
         * successfulAsList(Iterable<ListenableFuture<V>>)
         * 返回一个ListenableFuture ，该Future的结果包含所有成功的Future，按照原来的顺序，当其中之一Failed或者cancel，则用null替代
         */
        final ListeningExecutorService decorator = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());

        ListenableFuture randomFuture = decorator.submit(new Callable<Integer>() {
            public Integer call() throws Exception {
                return new Random().nextInt(100);
            }
        });
        AsyncFunction squareFunction = new AsyncFunction<Integer, Integer>() {
            public ListenableFuture<Integer> apply(Integer input) throws Exception {
                return decorator.submit(new Square(input));
            }
        };

        /**
         * transformAsync(ListenableFuture<A>, AsyncFunction<A, B>, Executor)
         * 返回一个100以内的随机数转换为一个随机数的平方
         */
        ListenableFuture<Integer> calculateFuture = Futures.transformAsync(
                randomFuture,
                squareFunction,
                decorator);

        Futures.addCallback(
                calculateFuture,
                new FutureCallback<Integer>() {
                    public void onSuccess(Integer result) {
                        // square: n   100以内随机数的平方
                        System.out.println("square: " + result);
                    }

                    public void onFailure(Throwable t) {
                    }
                },
                decorator
        );

        /**
         * transform(ListenableFuture<A>, Function<A, B>, Executor)
         * 返回一个100以内的随机数转换为"return:  random"字符串
         */
        ListenableFuture<String> strFuture = Futures.transform(
                randomFuture,
                new Function<Integer, String>() {
                    public String apply(Integer input) {
                        return "return: " + input;
                    }
                },
                decorator);

        Futures.addCallback(
                strFuture,
                new FutureCallback<String>() {
                    public void onSuccess(String result) {
                        // return:  n   n为100以内的随机数
                        System.out.println(result);
                    }

                    public void onFailure(Throwable t) {
                    }
                },
                decorator
        );



        ListenableFuture randomFuture1 = decorator.submit(new Callable<Integer>() {
            public Integer call() throws Exception {
                return new Random().nextInt(10);
            }
        });
        ListenableFuture randomFuture2 = decorator.submit(new Callable<Integer>() {
            public Integer call() throws Exception {
                throw  new Exception();
            }
        });


        List<ListenableFuture<Integer>> futureList = Lists.newArrayList(randomFuture1, randomFuture2);

        /**
         * allAsList(Iterable<ListenableFuture<V>>)
         * 10以内的随机数和fail ListenableFuture组成的ListenableFuture
         * 传入的其中之一fails或者cancel，这个Future fails 或者canceled
         */
        ListenableFuture<List<Integer>> allAsList = Futures.allAsList(futureList);

        Futures.addCallback(allAsList, new FutureCallback<List<Integer>>() {
            @Override
            public void onSuccess(List<Integer> result) {
                // 其中一个失败无返回
                System.out.println("list: " + result);
            }
            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        }, decorator);



        /**
         * successfulAsList(Iterable<ListenableFuture<V>>)
         * 返回一个ListenableFuture ，该Future的结果包含所有成功的Future，按照原来的顺序，当其中之一Failed或者cancel，则用null替代
         */

        ListenableFuture<List<Integer>> successfulAsList = Futures.successfulAsList(futureList);
        Futures.addCallback(successfulAsList, new FutureCallback<List<Integer>>() {
            @Override
            public void onSuccess(List<Integer> result) {
                // list: [n, null]  其中Failed或者cancel用null替代
                System.out.println("list: " + result);
            }
            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        }, decorator);


        // 运行完记得关掉
        executorService.shutdown();
        decorator.shutdown();
    }
    static class Square implements Callable {
        private int n;
        public Square(int n) {
            this.n = n;
        }
        public Integer call() throws Exception {
            return n*n;
        }
    }

}
