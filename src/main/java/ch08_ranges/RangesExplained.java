package ch08_ranges;

import com.google.common.collect.*;
import com.google.common.primitives.Ints;

/**
 * 区间
 *
 * @author
 * @create 2018-06-07 下午11:41
 **/
public class RangesExplained {
    public static void main(String[] args) {
        // 构建区间
        buildingRanges();
        // 区间运算
        operations();
        // 离散域
        discreteDomains();
    }


    /**
     * 构建区间
     * Range type	    Method
     * (a..b)	        open(C, C)
     * [a..b]	        closed(C, C)
     * [a..b)	        closedOpen(C, C)
     * (a..b]	        openClosed(C, C)
     * (a..+∞)	        greaterThan(C)
     * [a..+∞)	        atLeast(C)
     * (-∞..b)	        lessThan(C)
     * (-∞..b]	        atMost(C)
     * (-∞..+∞)	        all()
     */
    private static void buildingRanges() {

        // 字典序在"left"和"right"之间的字符串，闭区间
        Range<String> closedLeftAndRight = Range.closed("left", "right");
        // [left..right]
        println(closedLeftAndRight.toString());

        // 严格小于4.0的double值
        Range<Double> lessThan4 = Range.lessThan(4.0);
        // (-∞..4.0)
        println(lessThan4);


        /**
         * 明确地指定边界类型来构造区间
         * Range type	                    Method
         * 有界区间	                        range(C, BoundType, C, BoundType)
         * 有界区间 ((a..+∞) or [a..+∞))	    downTo(C, BoundType)
         * 有界区间 ((-∞..b) or (-∞..b])	    upTo(C, BoundType)
         */
        // 等同于Range.closedOpen(2, 4)
        Range<Integer> range = Range.range(2, BoundType.CLOSED, 4, BoundType.OPEN);
        // [2..4)
        println(range);

        Range<Integer> downTo4AndOpen = Range.downTo(4, BoundType.OPEN);
        // (4..+∞)
        println(downTo4AndOpen);

        Range<Integer> upTo4AndClose = Range.upTo(4, BoundType.CLOSED);
        // (-∞..4]
        println(upTo4AndClose);
    }


    /**
     * 区间运算
     */
    private static void operations() {
        /**
         * 基本操作 contains
         */
        boolean contains = Range.closed(1, 3).contains(2);
        // true
        println(contains);

        boolean contains1 = Range.closed(1, 3).contains(4);
        // false
        println(contains1);

        boolean contains2 = Range.lessThan(5).contains(5);
        // false
        println(contains2);

        boolean containsAll = Range.closed(1, 4).containsAll(Ints.asList(1, 2, 3));
        // true
        println(containsAll);



        /**
         * 查询运算
         */
        /**
         * isEmpty()：判断是否为空区间。
         */
        boolean isEmpty = Range.closedOpen(4, 4).isEmpty();
        // true
        println(isEmpty);

        boolean isEmpty2 = Range.openClosed(4, 4).isEmpty();
        // true
        println(isEmpty2);

        boolean isEmpty3 = Range.closed(4, 4).isEmpty();
        // false
        println(isEmpty3);

        // Range.open(4, 4).isEmpty();
        // Range.open throws IllegalArgumentExceptio


        /**
         * 返回区间的端点值；如果区间没有对应的边界，抛出IllegalStateException；
         */
        Integer closedLowerEndpoint = Range.closed(3, 10).lowerEndpoint();
        // 3
        println(closedLowerEndpoint);

        Integer openLowerEndpoint = Range.open(3, 10).lowerEndpoint();
        // 3
        println(openLowerEndpoint);

        /**
         * 返回区间边界类型，CLOSED或OPEN；如果区间没有对应的边界，抛出IllegalStateException
         */
        BoundType lowerBoundType = Range.closed(3, 10).lowerBoundType();
        // CLOSED
        println(lowerBoundType);

        BoundType upperBoundType = Range.open(3, 10).upperBoundType();
        // OPEN
        println(upperBoundType);



        /**
         * 关系运算
         */
        /**
         * 包含[enclose]
         * 如果内区间的边界没有超出外区间的边界，则外区间包含内区间。包含判断的结果完全取决于区间端点的比较
         */
        // [1..4) 包含 (1..4)
        boolean encloses = Range.closedOpen(1, 4).encloses(Range.open(1, 4));
        // true
        println(encloses);

        /**
         * 相连[isConnected]
         * 判断区间是否是相连的, 等同于数学上的定义”两个区间的并集是连续集合的形式”
         */
        boolean closedOpen = Range.closed(3, 5).isConnected(Range.open(5, 10));
        // true
        println(closedOpen);

        boolean openClose = Range.open(3, 5).isConnected(Range.open(5, 10));
        // false
        println(openClose);

    }

    
    /**
     * 部分（但不是全部）可比较类型是离散的，即区间的上下边界都是可枚举的
     * DiscreteDomain提供的离散域实例包括：
     * 类型	        离散域
     * Integer	    integers()
     * Long	        longs()
     */
    private static void discreteDomains() {

        DiscreteDomain<Integer> integers = DiscreteDomain.integers();

        // ContiguousSet.create并没有真的构造了整个集合，而是返回了set形式的区间视图
        ImmutableSortedSet set = ContiguousSet.create(Range.open(1, 5), integers.integers());
        // [2..4]
        println(set);

        // 包含[1, 2, ..., Integer.MAX_VALUE]
        ImmutableSortedSet<Integer> numbers = ContiguousSet.create(Range.greaterThan(0), DiscreteDomain.integers());
        // [1..2147483647]
        println(numbers);

        // 把离散域转为区间的”规范形式”
        // 如果ContiguousSet.create(a, domain).equals(ContiguousSet.create(b, domain))并且!a.isEmpty()，则有a.canonical(domain).equals(b.canonical(domain))。（这并不意味着a.equals(b)）
        Range<Integer> canonical = Range.closed(1, 5).canonical(integers);
        // [1..6)
        println(canonical);

    }


    private static void println(Object object) {
        System.out.println(object.toString());
    }
}
