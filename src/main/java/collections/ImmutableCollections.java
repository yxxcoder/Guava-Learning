package collections;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

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
}
