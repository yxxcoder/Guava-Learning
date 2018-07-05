package ch13_reflection;

import com.google.common.reflect.TypeToken;

import java.util.List;

/**
 * Guava 的 Java 反射机制工具类
 *
 * @author yuxuan
 * @create 2018-07-04 下午9:39
 **/
public class ReflectionExplained {
    public static void main(String[] args) {
        TypeToken<String> stringTok = TypeToken.of(String.class);
        TypeToken<Integer> intTok = TypeToken.of(Integer.class);

        TypeToken<List<String>> stringListTok = new TypeToken<List<String>>() {};
    }

}
