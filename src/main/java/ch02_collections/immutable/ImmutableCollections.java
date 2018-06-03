package ch02_collections.immutable;

import com.google.common.collect.*;

import java.awt.*;
import java.util.Set;

/**
 * 不可变集合
 * 用不变的集合进行防御性编程和性能提升
 * @author
 * @create 2018-03-13 下午9:02
 **/
public class ImmutableCollections {
    /**
     * 不可变集合创建方式
     * 对有序不可变集合来说，排序是在构造集合的时候完成的
     */
    public static void creationMethod(){
        Set<String> colors = Sets.newHashSet("red", "orange", "yellow");
        Set<Color> colorSet = Sets.newHashSet(new Color(0, 0, 0));

        // copyOf方法
        ImmutableSet.copyOf(colors);

        // of方法
        ImmutableSet.of(colors);
        ImmutableSet.of("red", "orange", "yellow");
        ImmutableMap.of("a", 1, "b", 2);

        // Builder工具
        ImmutableSet<Color> GOOGLE_COLORS = ImmutableSet.<Color>builder()
                .addAll(colorSet)
                .add(new Color(0, 191, 255))
                .build();

    }

    /**
     *
     */
    public static void exampleMethod(){
        ImmutableSet<String> colors = ImmutableSet.of("red", "orange", "yellow");

        // ImmutableXXX.copyOf方法会尝试在安全的时候避免做拷贝
        // 在这段代码中，ImmutableList.copyOf(foobar)会智能地直接返回foobar.asList()
        // 它是一个ImmutableSet的常量时间复杂度的List视图
        ImmutableList.copyOf(colors);

        // 所有不可变集合都有一个asList()方法提供ImmutableList视图
        ImmutableList immutableList = colors.asList();
    }

}
