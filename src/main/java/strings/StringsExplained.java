package strings;

import com.google.common.base.CharMatcher;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.primitives.Ints;

import java.util.regex.Pattern;


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


        /**
         * 拆分器[Splitter]
         */
        // 按单个字符拆分
        Iterable<String> strings = Splitter
                .on(',')
                .trimResults()
                .omitEmptyStrings()
                .split("foo,bar,,   qux");
        // [foo, bar, qux]
        System.out.println(strings);

        // 按字符匹配器拆分
        strings = Splitter
                .on(CharMatcher.whitespace())
                .trimResults()
                .omitEmptyStrings()
                .split("foo bar   qux,qq");
        // [foo, bar, qux,qq]
        System.out.println(strings);

        // 按字符串拆分
        strings = Splitter
                .on("ab")
                .trimResults()
                .omitEmptyStrings()
                .split("fooabbarabaux");
        // [foo, bar, aux]
        System.out.println(strings);

        // 按正则表达式拆分
        strings = Splitter
                .onPattern("\\r?\\n")
                .trimResults()
                .omitEmptyStrings()
                .split("fooabbarabaux");
        // [foo, bar, aux]
        System.out.println(strings);

        strings = Splitter
                .on(Pattern.compile("\\."))
                .trimResults()
                .omitEmptyStrings()
                .split("foo.bar.qux");
        // [foo, bar, qux]
        System.out.println(strings);
    }
}
