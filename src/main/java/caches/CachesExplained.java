package caches;


import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.graph.Graph;

import java.security.Key;
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

        } catch (ExecutionException e) {
            e.printStackTrace();
        }

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
    // 某一个计算
    public static int fib(int x) {
        if (x < 2) {
            return x;
        }
        return fib(x-1) + fib(x-2);
    }
}
