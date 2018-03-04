package basic;

import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import java.util.List;
import static com.google.common.collect.Ordering.natural;
import static com.google.common.collect.Ordering.usingToString;

/**
 * 排序器
 *
 * @author yuxuan
 * @create 2018-03-01 下午9:40
 **/
public class OrderingExplained {

    /**
     * 常见的排序器可以由下面的静态方法创建
     */
    public static void creationMethod(){

        List<String> list = Lists.newArrayList();
        list.add("peida");
        list.add("jerry");
        list.add("harry");
        list.add("eva");
        list.add("jhon");
        list.add("neron");
        System.out.println("list:"+ list);

        // 对可排序类型做自然排序，如数字按大小，日期按先后排序
        Ordering<String> naturalOrdering = natural();
        // 按对象的字符串形式做字典排序
        Ordering<Object> usingToStringOrdering = usingToString();

        System.out.println("naturalOrdering:"+ naturalOrdering.sortedCopy(list));
        System.out.println("usingToStringOrdering:"+ usingToStringOrdering.sortedCopy(list));
    }

    /**
     * 链式调用方法
     * 通过链式调用，可以由给定的排序器衍生出其它排序器
     */
    public static void chainingMethod(){
        List<String> list = Lists.newArrayList();
        list.add("peida");
        list.add("jerry");
        list.add("harry");
        list.add("eva");
        list.add("jhon");
        list.add(null);
        System.out.println("list:"+ list);

        // 对可排序类型做自然排序，如数字按大小，日期按先后排序
        Ordering<String> naturalOrdering = natural();
        // 获取语义相反的排序器
        Ordering<String> reverseOrdering = natural().reverse();
        // 使用当前排序器，但额外把null值排到最前面
        natural().nullsFirst();
        // 使用当前排序器，但额外把null值排到最后面
        natural().nullsFirst();

        System.out.println("naturalOrdering:"+ naturalOrdering.sortedCopy(list));
        System.out.println("reverseOrdering:"+ reverseOrdering.sortedCopy(list));
    }

    /**
     * 运用排序器
     * Guava的排序器实现有若干操纵集合或元素值的方法
     */
    public static void applicationMethod() {
        List<String> list = Lists.newArrayList();
        list.add("peida");
        list.add("jerry");
        list.add("harry");
        list.add("eva");
        list.add("jhon");
        list.add(null);
        System.out.println("list:"+ list);

        // 对可排序类型做自然排序，如数字按大小，日期按先后排序
        Ordering<String> naturalOrdering = natural();

        naturalOrdering.greatestOf();
    }

    public static void main(String args[]) {
        creationMethod();
        chainingMethod();

        Ordering<List<String>> ordering = new Ordering<List<String>>() {
            @Override
            public int compare(List<String> left, List<String> right) {
                return 0;
            }
        };
//        ordering.reverse().isOrdered(list);
    }
}
