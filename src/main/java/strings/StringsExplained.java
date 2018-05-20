package strings;

import com.google.common.base.*;
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
        String string = "\u0009666 and  sb ";
        // 	666 and  sb
        System.out.println(string);

        // 移除control字符
        String noControl = CharMatcher.javaIsoControl().removeFrom(string);
        // 666 and  sb
        System.out.println(noControl);

        // 去除两端的空格，并把中间的连续空格替换成单个空格
        String spaced = CharMatcher.whitespace().collapseFrom(string, ' ');
        //  666 and sb
        System.out.println(spaced);

        // 只保留数字字符
        String theDigits = CharMatcher.inRange('0', '9').retainFrom(string);
        // 666
        System.out.println(theDigits);

        // 用*号替换所有数字
        String noDigits = CharMatcher.inRange('0', '9').replaceFrom(string, "*");
        // 	*** and  sb
        System.out.println(noDigits);


        // 只保留数字和小写字母
        String lowerAndDigit = CharMatcher.inRange('0', '9').or(CharMatcher.inRange('a', 'z')).retainFrom(string);
        // 666andsb
        System.out.println(lowerAndDigit);



        /**
         * 获取字符匹配器的常见方法
         * 方法	                    描述
         * anyOf(CharSequence)	枚举匹配字符。如CharMatcher.anyOf(“aeiou”)匹配小写英语元音
         * is(char)	            给定单一字符匹配
         * inRange(char, char)	给定字符范围匹配，如CharMatcher.inRange(‘a’, ‘z’)
         *
         * 此外，CharMatcher还有negate()、and(CharMatcher)和or(CharMatcher)方法
         */



        /**
         * 使用字符匹配器
         * 方法	                                            描述
         * collapseFrom(CharSequence, char)	        把每组连续的匹配字符替换为特定字符。如WHITESPACE.collapseFrom(string, ‘ ‘)把字符串中的连续空白字符替换为单个空格。
         * matchesAllOf(CharSequence)	            测试是否字符序列中的所有字符都匹配。
         * removeFrom(CharSequence)	                从字符序列中移除所有匹配字符。
         * retainFrom(CharSequence)	                在字符序列中保留匹配字符，移除其他字符。
         * trimFrom(CharSequence)	                移除字符序列的前导匹配字符和尾部匹配字符。
         * replaceFrom(CharSequence, CharSequence)	用特定字符序列替代匹配字符。
         *
         * 所有这些方法返回String，除了matchesAllOf返回的是boolean
         */


        /**
         * 字符集
         * Charsets针对所有Java平台都要保证支持的六种字符集提供了常量引用
         * 尝试使用这些常量，而不是通过名称获取字符集实例。
         */

        byte[] bytes = string.getBytes(Charsets.UTF_8);


        /**
         * 大小写格式
         * CaseFormat被用来方便地在各种ASCII大小写规范间转换字符串——比如，编程语言的命名规范。CaseFormat支持的格式如下：
         *
         * 格式	                范例
         * LOWER_CAMEL	      lowerCamel
         * LOWER_HYPHEN	      lower-hyphen
         * LOWER_UNDERSCORE	  lower_underscore
         * UPPER_CAMEL	      UpperCamel
         * UPPER_UNDERSCORE	  UPPER_UNDERSCORE
         */

        String lowerCamel = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, "CONSTANT_NAME");
        // constantName
        System.out.println(lowerCamel);

    }
}
