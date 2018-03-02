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


    public static void main(String args[]) {
        creationMethod();

        Ordering<List<String>> ordering = new Ordering<List<String>>() {
            @Override
            public int compare(List<String> left, List<String> right) {
                return 0;
            }
        };
//        ordering.reverse().isOrdered(list);
    }
}
