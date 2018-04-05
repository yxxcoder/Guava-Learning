package collections.utilities;


import com.google.common.collect.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 强大的集合工具类：java.util.Collections中未包含的集合工具
 * @author
 * @create 2018-04-06 上午12:10
 **/
public class CollectionUtilities {

    /**
     * 静态工厂方法
     */
    public static void staticConstructors() {

        // Guava提供了能够推断范型的静态工厂方法
        List<Integer> list = Lists.newArrayList();
        Map<String, String> map = Maps.newLinkedHashMap();

        // 用工厂方法模式，我们可以方便地在初始化时就指定起始元素
        Set<Integer> copySet = Sets.newHashSet(1, 2);
        List<String> theseElements = Lists.newArrayList("alpha", "beta", "gamma");

        // 通过为工厂方法命名（Effective Java第一条），我们可以提高集合初始化大小的可读性
        List<Integer> exactly100 = Lists.newArrayListWithCapacity(100);
        List<Integer> approx100 = Lists.newArrayListWithExpectedSize(100);
        Set<Integer> approx100Set = Sets.newHashSetWithExpectedSize(100);

        // Guava引入的新集合类型没有暴露原始构造器，也没有在工具类中提供初始化方法。而是直接在集合类中提供了静态工厂方法
        Multiset<String> multiset = HashMultiset.create();

    }
}
