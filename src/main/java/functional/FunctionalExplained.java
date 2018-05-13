package functional;


import com.google.common.base.*;
import com.google.common.collect.Maps;
import com.google.common.primitives.Ints;

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

    public static void main(String args[]) {
        manipulating();
    }
}
