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
                .onPattern("\\/")
                .trimResults()
                .omitEmptyStrings()
                .split("foo/abb /ara b ");
        // [foo, abb, ara b]
        System.out.println(strings);

        strings = Splitter
                .on(Pattern.compile("\\."))
                .trimResults()
                .omitEmptyStrings()
                .split("foo.bar.qux");
        // [foo, bar, qux]
        System.out.println(strings);

        // 按固定长度拆分；最后一段可能比给定长度短，但不会为空
        strings = Splitter
                .fixedLength(2)
                .split("foo bar");
        // [fo, o , ba, r]
        System.out.println(strings);


        /**
         * 拆分器修饰符
         *
         * 方法	                        描述
         * omitEmptyStrings()	    从结果中自动忽略空字符串
         * trimResults()	        移除结果字符串的前导空白和尾部空白
         * trimResults(CharMatcher)	给定匹配器，移除结果字符串的前导匹配字符和尾部匹配字符
         * limit(int)	            限制拆分出的字符串数量
         */


        /**
         * 字符匹配器[CharMatcher]
         */
        String string = "^666 and  sb ";

        // 移除control字符
        String noControl = CharMatcher.javaIsoControl().removeFrom(string);
        System.out.println(noControl);

        // 去除两端的空格，并把中间的连续空格替换成单个空格
        String spaced = CharMatcher.whitespace().trimAndCollapseFrom(string, ' ');
        System.out.println();

//        CharMatcher.inRange();

        // 只保留数字和小写字母
        String lowerAndDigit = CharMatcher.JAVA_DIGIT.or(CharMatcher.JAVA_LOWER_CASE).retainFrom(string);


    }
}
