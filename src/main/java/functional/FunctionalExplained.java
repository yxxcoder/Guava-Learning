package functional;


import com.google.common.base.*;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.google.common.primitives.Ints;

import java.util.Collection;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 函数式编程
 *
 * @author
 * @create 2018-04-27 下午10:47
 **/
public class FunctionalExplained {
    public static void manipulating() {

        Map<String, Integer> map1 = Maps.newHashMap();
        map1.put("one", 1);
        map1.put("two", 2);

        /**
         * Functions提供的构造和操作方法
         */

        // forMap构造方法
        Function<String, Integer> function =  Functions.forMap(map1);
        // 1
        System.out.println(function.apply("one"));

        // 返回两个Function的组合
        Function<String, Integer> compose = Functions.compose(input -> input * input, function);
        // 4
        System.out.println(compose.apply("two"));

        // 为任意输入的值构造一个Function
        Function<Object, String> constant = Functions.constant("How Are You !");
        // How Are You !
        System.out.println(constant.apply(1));
        // How Are You !
        System.out.println(constant.apply("Hello"));

        // 返回IdentityFunction的唯一实例
        Function identity = Functions.identity();
        // Hello
        System.out.println(identity.apply("Hello"));
        // How Are You !
        System.out.println(identity.apply("How Are You !"));

        // 返回ToStringFunction的唯一实例
        // ToStringFunction主要是对返回传入对象调用toString方法后的表示
        Function<Object, String> toStringFunction = Functions.toStringFunction();
        // [1, 2, 3]
        System.out.println(Ints.asList(1, 2, 3));


        /**
         * Predicates构造和处理方法
         */
        Predicate instanceOf = Predicates.instanceOf(Integer.class);
        // true
        System.out.println(instanceOf.apply(1));

        Predicate contains = Predicates.contains(Pattern.compile("[1-9]"));
        // true
        System.out.println(contains.apply("008"));

        Predicate in = Predicates.in(Ints.asList(1, 2, 3));
        // false
        System.out.println(in.apply(6));

        Predicate isNull = Predicates.isNull();
        // true
        System.out.println(isNull.apply(null));

        Predicate alwaysFalse = Predicates.alwaysFalse();
        // false
        System.out.println(alwaysFalse.apply("say false"));

        Predicate alwaysTrue = Predicates.alwaysTrue();
        // true
        System.out.println(alwaysTrue.apply("say true"));

        Predicate equalTo = Predicates.equalTo(new String("Hello"));
        // true
        System.out.println(equalTo.apply("Hello"));

        Predicate com = Predicates.compose(input -> input.equals(String.class), input -> input.getClass());
        // false
        System.out.println(com.apply(345));

        Predicate and = Predicates.and(Predicates.alwaysTrue(), Predicates.alwaysFalse());
        // false
        System.out.println(and.apply("and"));

        Predicate or = Predicates.or(Predicates.alwaysTrue(), Predicates.alwaysFalse());
        // true
        System.out.println(or.apply("or"));

        Predicate not = Predicates.not(Predicates.alwaysTrue());
        // false
        System.out.println(not.apply("not"));

    }

    public static void using() {

        /**
         * 断言
         * 断言的最基本应用就是过滤集合
         * 注意: 并非用一个新的集合表示过滤，而只是基于原集合的视图
         *
         * 集合类型	    过滤方法
         * Iterable	    Iterables.filter(Iterable, Predicate)FluentIterable.filter(Predicate)
         * Iterator	    Iterators.filter(Iterator, Predicate)
         * Collection	Collections2.filter(Collection, Predicate)
         * Set	        Sets.filter(Set, Predicate)
         * SortedSet	Sets.filter(SortedSet, Predicate)
         * Map	        Maps.filterKeys(Map, Predicate)Maps.filterValues(Map, Predicate)Maps.filterEntries(Map, Predicate)
         * SortedMap	Maps.filterKeys(SortedMap, Predicate)Maps.filterValues(SortedMap, Predicate)Maps.filterEntries(SortedMap, Predicate)
         * Multimap	    Multimaps.filterKeys(Multimap, Predicate)Multimaps.filterValues(Multimap, Predicate)Multimaps.filterEntries(Multimap, Predicate)
         */

        Iterable<Integer> list = Iterables.filter(Ints.asList(-1, 0, 1), Predicates.in(Ints.asList(1, 2, 3)));
        // [1]
        System.out.println(list);

        Collection<Integer> collection = Collections2.filter(Ints.asList(-1, 0, 1), Predicates.in(Ints.asList(1, 2, 3)));
        // [1]
        System.out.println(collection);


        /**
         * 除了简单过滤，Guava另外提供了若干用Predicate处理Iterable的工具
         * 通常在Iterables工具类中，或者是FluentIterable的”fluent”（链式调用）方法
         *
         * Iterables方法签名	                        说明	                                                    另请参见
         * boolean all(Iterable, Predicate)	        是否所有元素满足断言?                                      Iterators.all(Iterator, Predicate)
         *                                          懒实现：如果发现有元素不满足，不会继续迭代	                    FluentIterable.allMatch(Predicate)
         * boolean any(Iterable, Predicate)	        是否有任意元素满足元素满足断言?                              Iterators.any(Iterator, Predicate)
         *                                          懒实现：只会迭代到发现满足的元素	                            FluentIterable.anyMatch(Predicate)
         * T find(Iterable, Predicate)	            循环并返回一个满足元素满足断言的元素,                         Iterators.find(Iterator, Predicate)
         *                                          如果没有则抛出NoSuchElementException	                    Iterables.find(Iterable, Predicate, T default)
         *                                                                                                  Iterators.find(Iterator, Predicate, T default)
         * Optional<T> tryFind(Iterable, Predicate)	返回一个满足元素满足断言的元素，若没有则返回Optional.absent()	Iterators.find(Iterator, Predicate)
         *                                                                                                  Iterables.find(Iterable, Predicate, T default)
         *                                                                                                  Iterators.find(Iterator, Predicate, T default)
         * indexOf(Iterable, Predicate)	            返回第一个满足元素满足断言的元素索引值，若没有返回-1	            Iterators.indexOf(Iterator, Predicate)
         * removeIf(Iterable, Predicate)	        移除所有满足元素满足断言的元素，实际调用Iterator.remove()方法	Iterators.removeIf(Iterator, Predicate)
         */

    }

    public static void main(String args[]) {
        manipulating();
        using();
    }
}
