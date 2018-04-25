package caches;


import com.google.common.cache.*;
import com.google.common.collect.ImmutableMap;
import com.google.common.graph.Graph;
import com.google.common.primitives.Ints;

import java.security.Key;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

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
        // 基于容量的回收
        // 缓存将尝试回收最近没有使用或总体上很少使用的缓存项，在缓存项的数目达到限定值之前，缓存就可能进行回收操作
        // 不同的缓存项可以有不同的“权重”
        LoadingCache<Integer, Integer> graphs = CacheBuilder.newBuilder()
                .maximumWeight(100000)
                .weigher((Weigher<Integer, Integer>) (k, g) -> k)
                .build(
                        new CacheLoader<Integer, Integer>() {
                            public Integer load(Integer key) { // no checked exception
                                return fib(key);
                            }
                        });
    }





    //    public static void example() {
//        LoadingCache<Key, Graph> graphs = CacheBuilder.newBuilder()
//                .maximumSize(1000)
//                .expireAfterWrite(10, TimeUnit.MINUTES)
//                .removalListener(MY_LISTENER)
//                .build(
//                        new CacheLoader<Key, Graph>() {
//                            public Graph load(Key key) {
//                                return createExpensiveGraph(key);
//                            }
//                        });
//    }
    // 模拟计算或检索一个值的代价很高的场景
    public static int fib(int x) {
        if (x < 2) {
            return x;
        }
        return fib(x-1) + fib(x-2);
    }
}
