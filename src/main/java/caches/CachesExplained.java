package caches;


import com.google.common.cache.*;
import com.google.common.collect.ImmutableMap;
import com.google.common.primitives.Ints;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;

import java.util.Map;
import java.util.concurrent.*;

/**
 * 缓存
 *
 * @author yuxuan
 * @create 2018-04-24 下午11:27
 **/
public class CachesExplained {
    public static void main(String args[]) {
        /**
         * CacheLoader
         * LoadingCache是附带CacheLoader构建而成的缓存实现
         * 创建自己的CacheLoader通常只需要简单地实现V load(K key) throws Exception方法
         */
        LoadingCache<Integer, Integer> cachedFib = CacheBuilder.newBuilder()
                .maximumSize(1000)
                .expireAfterAccess(10, TimeUnit.MINUTES)
                .build(
                        new CacheLoader<Integer, Integer>() {
                            public Integer load(Integer key) {
                                return fib(key);
                            }
        });

        try {
            // 这个方法要么返回已经缓存的值，要么使用CacheLoader向缓存原子地加载新值
            int value = cachedFib.get(20);
            // 6765
            System.out.println(value);

            // 如果定义的CacheLoader没有声明任何检查型异常，则可以通过getUnchecked(K)查找缓存
            // 一旦CacheLoader声明了检查型异常，就不可以调用getUnchecked(K)
            value = cachedFib.getUnchecked(20);
            // 6765
            System.out.println(value);

            // 执行批量查询
            ImmutableMap<Integer, Integer> immutableMap = cachedFib.getAll(Ints.asList(20, 21, 22));
            // {20=6765, 21=10946, 22=17711}
            System.out.println(immutableMap);

            // 如果批量的加载比多个单独加载更高效，可以重载CacheLoader.loadAll来利用这一点, getAll(Iterable)的性能也会相应提升
            LoadingCache<Integer, Integer> cachedFib2 = CacheBuilder.newBuilder()
                    .maximumSize(1000)
                    .build(
                            new CacheLoader<Integer, Integer>() {
                                @Override
                                public Integer load(Integer key) {
                                    return fib(key);
                                }

                                @Override
                                public Map<Integer, Integer> loadAll(Iterable<? extends Integer> keys) throws Exception {
                                    return super.loadAll(keys);
                                }
                            });

        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        /**
         * Callable
         * 所有类型的Guava Cache，不管有没有自动加载功能，都支持get(K, Callable<V>)方法
         * 这个方法返回缓存中相应的值，或者用给定的Callable运算并把结果加入到缓存中
         * 这个方法简便地实现了模式"如果有缓存则返回；否则运算、缓存、然后返回"
         */
        Cache<Integer, Integer> cache = CacheBuilder.newBuilder()
                .maximumSize(1000)
                .build();

        try {
            // 如果有缓存则返回；否则运算、缓存、然后返回
            cache.get(20, new Callable<Integer>() {
                @Override
                public Integer call() {
                    return fib(20);
                }
            });
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        /**
         * 显式插入
         */
        cache.put(21, 10946);

        cache.asMap().putIfAbsent(23, 6766);

        System.out.println(cache.asMap());


        /**
         * 缓存回收
         */

        /**
         * 基于容量的回收
         */
        // 缓存将尝试回收最近没有使用或总体上很少使用的缓存项，在缓存项的数目达到限定值之前，缓存就可能进行回收操作
        // 不同的缓存项可以有不同的“权重”
        LoadingCache<Integer, Integer> loadingCache = CacheBuilder.newBuilder()
                .maximumWeight(100000)
                .weigher(new Weigher<Integer, Integer>() {
                    public int weigh(Integer k, Integer g) {
                        return k;
                    }
                })
                .build(new CacheLoader<Integer, Integer>() {
                            public Integer load(Integer key) {
                                return fib(key);
                            }
                        });

        /**
         * 定时回收
          */
        // 缓存项在给定时间内没有被读/写访问，则回收。请注意这种缓存的回收顺序和基于大小回收一样
        Cache<Integer, Integer> timed = CacheBuilder.newBuilder()
                .expireAfterAccess(100, TimeUnit.SECONDS)
                .build();
        // 缓存项在给定时间内没有被写访问（创建或覆盖），则回收
        // 如果认为缓存数据总是在固定时候后变得陈旧不可用，这种回收方式是可取的
        Cache<Integer, Integer> timed2 = CacheBuilder.newBuilder()
                .expireAfterWrite(100, TimeUnit.SECONDS)
                .build();


        /**
         * 基于引用的回收
         */
        // 使用弱引用存储键
        // 当键没有其它（强或软）引用时，缓存项可以被垃圾回收。因为垃圾回收仅依赖恒等式（==），使用弱引用键的缓存用==而不是equals比较键
        CacheBuilder.newBuilder().weakKeys().build();

        // 使用弱引用存储值
        // 当值没有其它（强或软）引用时，缓存项可以被垃圾回收。因为垃圾回收仅依赖恒等式（==），使用弱引用值的缓存用==而不是equals比较值。
        CacheBuilder.newBuilder().weakValues().build();

        // 使用软引用存储值
        // 软引用只有在响应内存需要时，才按照全局最近最少使用的顺序回收。使用软引用值的缓存同样用==而不是equals比较值
        CacheBuilder.newBuilder().softValues().build();



        /**
         * 显式清除
         * 任何时候，都可以显式地清除缓存项，而不是等到它被回收
         */
        Cache cacheClean = CacheBuilder.newBuilder().build();

        // 个别清除
        cacheClean.invalidate(1);
        // 批量清除
        cacheClean.invalidateAll(Ints.asList(1, 2, 3));
        // 清除所有缓存项
        cacheClean.invalidateAll();




        /**
         * 移除监听器
         */
        CacheLoader<Integer, Integer> loader = new CacheLoader<Integer, Integer> () {
            public Integer load(Integer key) throws Exception {
                return fib(key);
            }
        };
        RemovalListener<Integer, Integer> removalListener = new RemovalListener<Integer, Integer>() {
            public void onRemoval(RemovalNotification<Integer, Integer> removal) {
                System.out.println(String.format("key: %d, value: %d be removed . Cause: %s ", removal.getKey(), removal.getValue(), removal.getCause()));
            }
        };

        Cache listenCache = CacheBuilder.newBuilder()
                .expireAfterAccess(2, TimeUnit.SECONDS)
                .removalListener(removalListener)
                .build(loader);

        listenCache.put(1, fib(1));
        listenCache.put(2, fib(2));
        listenCache.invalidateAll();


        // 把监听器装饰为异步操作
        // 避免代价高昂的监听器方法在同步模式下拖慢正常的缓存请求
        RemovalListener<Integer, Integer> async = RemovalListeners.asynchronous(removalListener, Executors.newSingleThreadExecutor());

        listenCache = CacheBuilder.newBuilder()
                .expireAfterAccess(2, TimeUnit.SECONDS)
                .removalListener(async)
                .build(loader);

        listenCache.put(1, fib(1));
        listenCache.put(2, fib(2));
        listenCache.invalidateAll();


        // 使用CacheBuilder构建的缓存不会"自动"执行清理和回收工作，只会在写操作时顺带做少量的维护工作
        // 如果你的缓存是高吞吐的，那就无需担心缓存的维护和清理等工作
        // 如果你的缓存只会偶尔有写操作，而你又不想清理工作阻碍了读操作，那么可以创建自己的维护线程，以固定的时间间隔调用Cache.cleanUp()
        listenCache.cleanUp();



        /**
         * 刷新
         * 刷新和回收不太一样。正如LoadingCache.refresh(K)所声明，刷新表示为键加载新值，这个过程可以是异步的
         * 在刷新操作进行时，缓存仍然可以向其他线程返回旧值，而不像回收操作，读缓存的线程必须等待新值加载完成
         */

        //有些键不需要刷新，并且我们希望刷新是异步完成的
        LoadingCache<Integer, Integer> reload = CacheBuilder.newBuilder()
                .maximumSize(1000)
                .refreshAfterWrite(1, TimeUnit.MINUTES)
                .build(
                        new CacheLoader<Integer, Integer>() {
                            public Integer load(Integer key) { // no checked exception
                                return fib(key);
                            }

                            public ListenableFuture<Integer> reload(final Integer key, Integer prev) {
                                if (neverNeedsRefresh(key)) {
                                    return Futures.immediateFuture(prev);
                                } else {
                                    // asynchronous!
                                    ListenableFutureTask<Integer> task = ListenableFutureTask.create(new Callable<Integer>() {
                                        public Integer call() {
                                            return fib(key);
                                        }
                                    });
                                    Executors.newSingleThreadExecutor().execute(task);
                                    return task;
                                }
                            }
                        });


    }





    // 模拟计算或检索一个值的代价很高的场景
    private static int fib(int x) {
        if (x < 2) {
            return x;
        }
        return fib(x-1) + fib(x-2);
    }

    // 大于100的值不需要刷新
    private static boolean neverNeedsRefresh(Integer key) {
        return key > 100 ? false : true;
    }
}
