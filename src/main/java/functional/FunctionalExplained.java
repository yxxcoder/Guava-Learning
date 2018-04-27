package functional;


import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.collect.Maps;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 函数式编程
 *
 * @author
 * @create 2018-04-27 下午10:47
 **/
public class FunctionalExplained {
    public static void manipulating() {
        /**
         * Functions提供的构造和操作方法
         */
        Map<Integer, String> map1 = Maps.newHashMap();
        map1.put(123, "123");
        map1.put(456, "456");

        Map<Integer, String> map2 = Maps.newHashMap();
        map2.put(789, "789");

        // 构造方法
        Function<Integer, String> function =  Functions.forMap(map1);
        // 123
        System.out.println(function.apply(123));

        //
        // Functions.compose();





    }

    public static void main(String args[]) {
        manipulating();
    }
}
