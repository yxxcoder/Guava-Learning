package ch01_basic.preconditions;

import static com.google.common.base.Preconditions.*;

/**
 * 前置条件：让方法调用的前置条件判断更简单
 *
 * @author yuxuan
 * @create 2018-02-27 下午9:59
 **/
public class PreconditionsExplained {

    public static void main(String args[]) {
        // 前置条件
        preconditions();
        // 使用示例
        // example();
    }

    /**
     * Guava在Preconditions类中提供了若干前置条件判断的实用方法，每个方法都有三个变种：
     * 没有额外参数：抛出的异常中没有错误消息；
     * 有一个Object对象作为额外参数：抛出的异常使用Object.toString() 作为错误消息；
     * 有一个String对象作为额外参数，并且有一组任意数量的附加Object对象：这个变种处理异常消息的方式有点类似printf，但考虑GWT的兼容性和效率，只支持%s指示符
     */
    public static void preconditions() {

        int i = 5;
        int j = 3;

        // 没有额外参数：抛出的异常中没有错误消息
        try {
            checkArgument(i <= 0);
        } catch(Exception e) {
            e.printStackTrace();
        }

        // 有一个Object对象作为额外参数
        try {
            checkArgument(i <= 0, "Argument Exception");
        } catch(Exception e) {
            e.printStackTrace();
        }

        // 有一个String对象作为额外参数，并且有一组任意数量的附加Object对象
        try {
            checkArgument(i < j, "Expected i < j, but %s > %s", i, j);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void example() {

        /**
         * 检查boolean是否为true，用来检查传递给方法的参数
         * 检查失败时抛出 IllegalArgumentException
         */
        checkArgument(Boolean.TRUE);

        /**
         * 检查value是否为null，该方法直接返回value，因此可以内嵌使用checkNotNull
         * 可以在构造函数中保持字段的单行赋值风格：this.field = checkNotNull(field)
         * 检查失败时抛出 NullPointerException
         */
        checkNotNull(null);

        /**
         * 用来检查对象的某些状态
         * 检查失败时抛出 IllegalStateException
         */
        checkState(Boolean.FALSE);

        /**
         * 检查index作为索引值对某个列表、字符串或数组是否有效。index>=0 && index<size
         * 检查失败时抛出 IndexOutOfBoundsException
         */
        checkElementIndex(3,3);

        /**
         * 检查index作为位置值对某个列表、字符串或数组是否有效。index>=0 && index<=size
         * 检查失败时抛出 IndexOutOfBoundsException
         */
        checkPositionIndex(3, 3);

        /**
         * 检查[start, end]表示的位置范围对某个列表、字符串或数组是否有效
         * 检查失败时抛出 IndexOutOfBoundsException
         */
        checkPositionIndexes(0, 3, 3);
    }

}
