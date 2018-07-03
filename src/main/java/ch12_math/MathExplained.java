package ch12_math;

import com.google.common.math.BigIntegerMath;
import com.google.common.math.DoubleMath;
import com.google.common.math.IntMath;
import java.math.BigInteger;
import java.math.RoundingMode;

/**
 * 数学运算
 *
 * @author yuxuan
 * @create 2018-06-30 下午11:23
 **/
public class MathExplained {
    public static void main(String[] args) {
        // 整数运算
        integral();
        // 实数运算
        realValued();
        // 浮点数运算
        floatingPoint();
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
         * 运算       IntMath	                LongMath
         * 加法       IntMath.checkedAdd	    LongMath.checkedAdd
         * 减法       IntMath.checkedSubtract	LongMath.checkedSubtract
         * 乘法       IntMath.checkedMultiply	LongMath.checkedMultiply
         * 幂运算     IntMath.checkedPow	    LongMath.checkedPow
         */

        // 加法运算
        try {
            IntMath.checkedAdd(Integer.MAX_VALUE, Integer.MAX_VALUE);
        } catch (Exception e) {
            // java.lang.ArithmeticException: overflow
            e.printStackTrace();
        }

        // 幂运算
        int checkedPow = IntMath.checkedPow(2, 10);
        // 1024
        System.out.println(checkedPow);
    }


    /**
     * 实数运算
     * IntMath、LongMath和BigIntegerMath提供了很多实数运算的方法，并把最终运算结果舍入成整数
     * 这些方法接受一个java.math.RoundingMode枚举值作为舍入的模式:
     * DOWN：向零方向舍入（去尾法）
     * UP：远离零方向舍入
     * FLOOR：向负无限大方向舍入
     * CEILING：向正无限大方向舍入
     * UNNECESSARY：不需要舍入，如果用此模式进行舍入，应直接抛出ArithmeticException
     * HALF_UP：向最近的整数舍入，其中x.5远离零方向舍入
     * HALF_DOWN：向最近的整数舍入，其中x.5向零方向舍入
     * HALF_EVEN：向最近的整数舍入，其中x.5向相邻的偶数舍入
     *
     * 运算	          IntMath	                        LongMath	                        BigIntegerMath
     * 除法	          divide(int, int, RoundingMode)	divide(long, long, RoundingMode)	divide(BigInteger, BigInteger, RoundingMode)
     * 2为底的对数	  log2(int, RoundingMode)	        log2(long, RoundingMode)	        log2(BigInteger, RoundingMode)
     * 10为底的对数	  log10(int, RoundingMode)	        log10(long, RoundingMode)	        log10(BigInteger, RoundingMode)
     * 平方根	      sqrt(int, RoundingMode)	        sqrt(long, RoundingMode)	        sqrt(BigInteger, RoundingMode)
     */
    private static void realValued() {
        // 除法运算
        int divide = IntMath.divide(5, 2, RoundingMode.UP);
        // 3
        System.out.println(divide);

        // 2为底的对数
        int log2 = IntMath.log2(8, RoundingMode.UP);
        // 3
        System.out.println(log2);

        // 10为底的对数
        int log10 = IntMath.log10(100, RoundingMode.UP);
        // 2
        System.out.println(log10);

        // 平方根
        BigInteger sqrt = BigIntegerMath.sqrt(BigInteger.TEN.pow(99), RoundingMode.HALF_EVEN);
        // 31622776601683793319988935444327185337195551393252
        System.out.println(sqrt);


        /**
         * 附加功能
         * Guava还另外提供了一些有用的运算函数
         *
         * 运算	        IntMath	            LongMath	        BigIntegerMath*
         * 最大公约数	    gcd(int, int)	    gcd(long, long)	    BigInteger.gcd(BigInteger)
         * 取模	        mod(int, int)	    mod(long, long)	    BigInteger.mod(BigInteger)
         * 取幂	        pow(int, int)	    pow(long, int)	    BigInteger.pow(int)
         * 是否2的幂	    isPowerOfTwo(int)	isPowerOfTwo(long)	isPowerOfTwo(BigInteger)
         * 阶乘*	        factorial(int)	    factorial(int)	    factorial(int)
         * 二项式系数*	binomial(int, int)	binomial(int, int)	binomial(int, int)
         *
         * *BigInteger的最大公约数和取模运算由JDK提供
         * *阶乘和二项式系数的运算结果如果溢出，则返回MAX_VALUE
         */

        // 最大公约数
        int gcd = IntMath.gcd(10, 11);
        // 1
        System.out.println(gcd);

        // 取模
        int mod = IntMath.mod(18, 12);
        // 6
        System.out.println(mod);

        // 取幂
        int pow = IntMath.pow(2, 10);
        // 1024
        System.out.println(pow);

        // 是否2的幂
        boolean powerOfTwo = IntMath.isPowerOfTwo(258);
        // false
        System.out.println(powerOfTwo);

        // 阶乘
        int factorial = IntMath.factorial(5);
        // 120
        System.out.println(factorial);

        // 二项式系数
        //              1
        //          1       1
        //      1       2       1
        //  1       3       3       1
        int binomial = IntMath.binomial(3, 2);
        // 3
        System.out.println(binomial);
    }


    /**
     * 浮点数运算
     * JDK比较彻底地涵盖了浮点数运算，但Guava在DoubleMath类中也提供了一些有用的方法
     *
     * isMathematicalInteger(double)	        判断该浮点数是不是一个整数
     * roundToInt(double, RoundingMode)	        舍入为int；对无限小数、溢出抛出异常
     * roundToLong(double, RoundingMode)	    舍入为long；对无限小数、溢出抛出异常
     * roundToBigInteger(double, RoundingMode)	舍入为BigInteger；对无限小数抛出异常
     * log2(double, RoundingMode)	            2的浮点对数，并且舍入为int，比JDK的Math.log(double) 更快
     */
    private static void floatingPoint() {
        // 判断该浮点数是不是一个整数
        boolean isMathematicalInteger = DoubleMath.isMathematicalInteger(3.14);
        // false
        System.out.println(isMathematicalInteger);

        // 舍入为int；对无限小数、溢出抛出异常
        int roundToInt = DoubleMath.roundToInt(3.14, RoundingMode.UP);
        // 4
        System.out.println(roundToInt);

        // 舍入为long；对无限小数、溢出抛出异常
        long roundToLong = DoubleMath.roundToLong(123456789.987654321, RoundingMode.DOWN);
        // 123456789
        System.out.println(roundToLong);

        // 舍入为BigInteger；对无限小数抛出异常
        BigInteger roundToBigInteger = DoubleMath.roundToBigInteger(1234567897654321.999, RoundingMode.DOWN);
        // 1234567897654322
        System.out.println(roundToBigInteger);

        int log2 = DoubleMath.log2(100, RoundingMode.DOWN);
        // 6
        System.out.println(log2);
    }
}
