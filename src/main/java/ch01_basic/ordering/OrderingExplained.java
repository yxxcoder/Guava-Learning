package ch01_basic.ordering;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.google.common.primitives.Ints;
import java.util.Comparator;
import java.util.List;
import static com.google.common.collect.Ordering.natural;
import static com.google.common.collect.Ordering.usingToString;

/**
 * 排序器
 * 排序器[Ordering]是Guava流畅风格比较器[Comparator]的实现，它可以用来为构建复杂的比较器，以完成集合排序的功能
 * @author yuxuan
 * @create 2018-03-01 下午9:40
 **/
public class OrderingExplained {

    public static void main(String args[]) {
        // 创建排序器
        creationMethod();
        // 链式调用方法
        chainingMethod();
        // 运用排序器
        applicationMethod();
    }

    static List<People> peopleList = Lists.newArrayList(
            new People("peter", 8),
            new People("jerry", 9),
            new People("harry", 10),
            new People("eva", 11),
            new People("vermouth", 12),
            new People("jetty", 12));

    static List<String> list = Lists.newArrayList(
            "peter",
            "jerry",
            "jerry",
            "eva",
            "john"
    );


    /**
     * 创建排序器
     * 常见的排序器可以由下面的静态方法创建
     */
    public static void creationMethod(){

        System.out.println("list:"+ list);

        // 对可排序类型做自然排序，如数字按大小，日期按先后排序
        Ordering<String> naturalOrdering = natural();
        // 按对象的字符串形式做字典排序
        Ordering<Object> usingToStringOrdering = usingToString();
        // 把给定的Comparator转化为排序器
        Ordering<String> fromOrdering = Ordering.from(new Comparator<String>() {
            public int compare(String o1, String o2) {
                return o1.hashCode() - o2.hashCode() ;
            }
        });
        // 直接继承Ordering
        Ordering<String> byLengthOrdering = new Ordering<String>() {
            public int compare(String left, String right) {
                return Ints.compare(left.length(), right.length());
            }
        };

        System.out.println("naturalOrdering:"+ naturalOrdering.sortedCopy(list));
        System.out.println("usingToStringOrdering:"+ usingToStringOrdering.sortedCopy(list));
        System.out.println("fromOrdering:"+ fromOrdering.sortedCopy(list));
        System.out.println("byLengthOrdering:"+ byLengthOrdering.sortedCopy(list));

    }

    /**
     * 链式调用方法
     * 通过链式调用，可以由给定的排序器衍生出其它排序器
     * 当阅读链式调用产生的排序器时，应该从后往前读，因为每次链式调用都是用后面的方法包装了前面的排序器
     * 用compound方法包装排序器时，不遵循从后往前读的原则
     */
    public static void chainingMethod(){

        System.out.println("list:"+ list);

        List<String> withNullList = Lists.newArrayList(list);
        withNullList.add(null);
        System.out.println("withNullList:"+ withNullList);

        List<List> listList = Lists.newArrayList();
        listList.add(Lists.newArrayList("perter", "jerry"));
        listList.add(Lists.newArrayList("perter"));
        listList.add(Lists.newArrayList("perter", "jerry", null));
        System.out.println("listList:"+ withNullList);

        // 对可排序类型做自然排序，如数字按大小，日期按先后排序
        Ordering<String> naturalOrdering = natural();
        // 获取语义相反的排序器
        Ordering<String> reverseOrdering = natural().reverse();
        // 使用当前排序器，但额外把null值排到最前面
        Ordering<String> nullsFirst = natural().nullsFirst();
        // 使用当前排序器，但额外把null值排到最后面
        Ordering<String> nullsLast = natural().nullsLast();
        // 合成另一个比较器，以处理当前排序器中的相等情况
        Ordering<People> secondaryOrdering = new PeopleAgeOrder().compound(new PeopleNameLengthOrder());
        // 返回该类型的可迭代对象Iterable<T>的排序器
        Ordering lexicographicalOrdering = naturalOrdering.lexicographical();
        // 对集合中元素调用Function，再按返回值用当前排序器排序
        Ordering<String> resultOfOrdering = natural().nullsFirst().onResultOf(new Function<String, Integer>() {
            public Integer apply(String input) {
                return input == null ? null : input.length();
            }
        });

        System.out.println("naturalOrdering:"+ naturalOrdering.sortedCopy(list));
        System.out.println("reverseOrdering:"+ reverseOrdering.sortedCopy(list));
        System.out.println("nullsFirst:"+ nullsFirst.sortedCopy(withNullList));
        System.out.println("nullsLast:"+ nullsLast.sortedCopy(list));
        System.out.println("secondaryOrdering:"+ secondaryOrdering.sortedCopy(peopleList));
        System.out.println("lexicographicalOrdering:"+ lexicographicalOrdering.sortedCopy(listList));
        System.out.println("resultOfOrdering:"+ resultOfOrdering.sortedCopy(withNullList));
    }

    /**
     * 运用排序器
     * Guava的排序器实现有若干操纵集合或元素值的方法
     */
    public static void applicationMethod() {

        System.out.println("list:"+ list);

        // 获取可迭代对象中最大的k个元素
        System.out.println("greatestOfOrdering:"+ natural().greatestOf(list, 3));
        System.out.println("leastOfOrdering:"+ natural().leastOf(list, 3));

        // 判断可迭代对象是否已按排序器排序
        // 允许有排序值相等的元素
        System.out.println("isOrdered:"+ natural().isOrdered(natural().sortedCopy(list)));
        // 判断可迭代对象是否已严格按排序器排序
        // 不允许有排序值相等的元素
        System.out.println("isStrictlyOrdered:"+ natural().isStrictlyOrdered(natural().sortedCopy(list)));

        // 以列表形式返回指定元素的已排序副本
        System.out.println("isOrdered:"+ natural().sortedCopy(list));
        // 返回包含按此排序排序的元素的不可变列表
        natural().immutableSortedCopy(list);

        // 返回两个参数中最小的那个。如果相等，则返回第一个参数
        System.out.println("min:" + natural().min("abc", "ab"));
        // 返回两个参数中最大的那个。如果相等，则返回第一个参数
        System.out.println("max:" + natural().max("abc", "ab"));

        // 返回多个参数中最小的那个。如果有超过一个参数都最小，则返回第一个最小的参数
        System.out.println("min:" + natural().min("ab", "cd", "abc"));
        // 返回多个参数中最大的那个。如果有超过一个参数都最大，则返回第一个最大的参数
        System.out.println("max:" + natural().max("ab", "cde", "abc"));

        // 返回迭代器中最小的元素。如果可迭代对象中没有元素，则抛出NoSuchElementException
        System.out.println("min:" + natural().min(list));
        // 返回迭代器中最大的元素。如果可迭代对象中没有元素，则抛出NoSuchElementException
        System.out.println("max:" + natural().max(list));

    }
}
