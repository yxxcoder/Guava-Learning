package basic;

import com.google.common.base.MoreObjects;
import com.google.common.base.Optional;

import static com.google.common.base.Strings.emptyToNull;
import static com.google.common.base.Strings.isNullOrEmpty;
import static com.google.common.base.Strings.nullToEmpty;


/**
 * 使用和避免null
 *
 * @author yuxuan
 * @create 2018-02-26 下午10:34
 **/
public class OptionalExplained {

    /**
     * 创建Optional实例
     */
    public void makingAnOptional() {

        // 创建指定引用的Optional实例，若引用为null则快速失败
        Optional<Integer> possible = Optional.of(5);

        // 抛出 NullPointerException
        // Optional possible2 = Optional.of(null);


        // 创建引用缺失的Optional实例
        possible = Optional.absent();


        // 创建引用缺失的Optional实例
        possible = Optional.fromNullable(5);
        possible = Optional.fromNullable(null);
    }

    /**
     * 用Optional实例查询引用
     */
    public void queryMethods() {
        Optional<Integer> possible = Optional.of(5);
        // 如果Optional包含非null的引用（引用存在），返回true
        possible.isPresent();

        // 返回Optional所包含的引用，若引用缺失，则抛出java.lang.IllegalStateException
        possible.get();

        // 返回Optional所包含的引用，若引用缺失，返回指定的值
        possible.or(3);

        // 返回Optional所包含的引用，若引用缺失，返回null
        possible.orNull();

        // 返回Optional所包含引用的单例不可变集
        // 如果引用存在，返回一个只有单一元素的集合，如果引用缺失，返回一个空集合
        possible.asSet();
    }

    /**
     * 其他处理null的便利方法
     */
    public void convenienceMethods() {

        // return 5
        MoreObjects.firstNonNull(5, 6);
        // return 6
        Optional.fromNullable(null).or(6);

        /**
         * 专门处理null或空字符串的方法
         */
        // return null
        emptyToNull("");

        // return true
        isNullOrEmpty("");
        isNullOrEmpty(null);

        // return ""
        nullToEmpty(null);
        // return "hello"
        nullToEmpty("hello");
    }
}
