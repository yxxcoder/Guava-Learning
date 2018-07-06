package ch13_reflection;

import com.google.common.reflect.TypeParameter;
import com.google.common.reflect.TypeToken;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * Guava 的 Java 反射机制工具类
 *
 * @author yuxuan
 * @create 2018-07-04 下午9:39
 **/
public class ReflectionExplained {
    public static void main(String[] args) {
        // 获取与使用TypeToken
        typeToken();


        TypeToken<List<String>> stringListTok = new TypeToken<List<String>>() {};
    }

    /**
     * 获取与使用TypeToken
     * 可以在运行时都能够操作和查询泛型类型
     */
    private static void typeToken() {
        /**
         * 获取一个基本的、原始类的TypeToken
         */
        TypeToken<String> stringTok = TypeToken.of(String.class);
        TypeToken<Integer> intTok = TypeToken.of(Integer.class);

        /**
         * 获得一个含有泛型的类型的TypeToken
         */
        TypeToken<List<String>> stringListTok = new TypeToken<List<String>>() {};

        /**
         * 指向一个通配符类型
         */
        TypeToken<Map<?, ?>> wildMapTok = new TypeToken<Map<?, ?>>() {};


        /**
         * TypeToken提供了一种方法来动态的解决泛型类型参数
         */
        TypeToken<Map<String, BigInteger>> mapToken = mapToken(
                TypeToken.of(String.class),
                TypeToken.of(BigInteger.class)
        );
        TypeToken<Map<Integer, Queue<String>>> complexToken = mapToken(
                TypeToken.of(Integer.class),
                new TypeToken<Queue<String>>() {}
        );
    }

    private static <K, V> TypeToken<Map<K, V>> mapToken(TypeToken<K> keyToken, TypeToken<V> valueToken) {
        return new TypeToken<Map<K, V>>() {}
                .where(new TypeParameter<K>() {}, keyToken)
                .where(new TypeParameter<V>() {}, valueToken);
    }

}
