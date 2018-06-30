package ch12_math;

import com.google.common.math.IntMath;

/**
 * 数学运算
 *
 * @author
 * @create 2018-06-30 下午11:23
 **/
public class MathExplained {
    public static void main(String[] args) {
        // 整数运算
        integral();
    }

    /**
     * 整数运算
     * Guava Math主要处理三种整数类型：int、long和BigInteger
     * 这三种类型的运算工具类为IntMath、LongMath和BigIntegerMath
     */
    private static void integral() {
        /**
         * 有溢出检查的运算
         * Guava Math提供了若干有溢出检查的运算方法：结果溢出时，这些方法将快速失败而不是忽略溢出
         *
         * IntMath	                LongMath
         * IntMath.checkedAdd	    LongMath.checkedAdd
         * IntMath.checkedSubtract	LongMath.checkedSubtract
         * IntMath.checkedMultiply	LongMath.checkedMultiply
         * IntMath.checkedPow	    LongMath.checkedPow
         */

        IntMath.checkedAdd(Integer.MAX_VALUE, Integer.MAX_VALUE);
    }
}
