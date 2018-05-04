package strings;

import com.google.common.base.Joiner;
import com.google.common.primitives.Ints;

/**
 * 字符串处理：分割，连接，填充
 *
 * @author
 * @create 2018-05-04 下午6:04
 **/
public class StringsExplained {
    public static void main(String[] args) {
        /**
         * 连接器[Joiner]
         */
        Joiner joiner = Joiner.on("; ").skipNulls();
        String str = joiner.join("Harry", null, "Ron", "Hermione");
        // Harry; Ron; Hermione
        System.out.println(str);

        joiner = Joiner.on("; ").useForNull("null");
        str = joiner.join("Harry", null, "Ron", "Hermione");
        // Harry; null; Ron; Hermione
        System.out.println(str);

        str = Joiner.on(",").join(Ints.asList(1, 2, 3));
        // 1,2,3
        System.out.println(str);

        str = Joiner.on(",").join(4, 5, 6);
        // 4,5,6
        System.out.println(str);
    }
}
