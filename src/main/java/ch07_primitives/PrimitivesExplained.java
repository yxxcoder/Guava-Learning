package ch07_primitives;

import com.google.common.primitives.*;

import java.math.BigInteger;
import java.util.Comparator;
import java.util.List;


/**
 * 原生类型
 * 原生类型不能当作对象或泛型的类型参数使用，这意味着许多通用方法都不能应用于它们。
 * Guava提供了若干通用工具，包括原生类型数组与集合API的交互，原生类型和字节数组的相互转换，以及对某些原生类型的无符号形式的支持
 *
 * 原生类型	    Guava工具类（都在com.google.common.primitives包）
 * byte	        Bytes, SignedBytes, UnsignedBytes
 * short	    Shorts
 * int	        Ints, UnsignedInteger, UnsignedInts
 * long	        Longs, UnsignedLong, UnsignedLongs
 * float	    Floats
 * double	    Doubles
 * char	        Chars
 * boolean	    Booleans
 * @create 2018-06-04 下午11:03
 **/
public class PrimitivesExplained {
    public static void main(String[] args) {
        // 原生类型数组工具
        arrayUtilities();
        // 通用工具方法
        generalUtilities();
        // 字节转换方法
        byteConversion();
        // 无符号支持
        unsignedSupport();
    }


    /**
     * 原生类型数组工具
     *
     * 方法签名	                                        描述	                                            类似方法	                                可用性
     * List<Wrapper> asList(prim… backingArray)	        把数组转为相应包装类的List	                        Arrays.asList	                        符号无关
     * prim[] toArray(Collection<Wrapper> collection)	把集合拷贝为数组，和collection.toArray()一样线程安全	Collection.toArray()	                符号无关
     * prim[] concat(prim[]… arrays)	                串联多个原生类型数组	                            Iterables.concat	                    符号无关
     * boolean contains(prim[] array, prim target)	    判断原生类型数组是否包含给定值	                    Collection.contains	                    符号无关
     * int indexOf(prim[] array, prim target)	        给定值在数组中首次出现处的索引，若不包含此值返回-1	    List.indexOf	                        符号无关
     * int lastIndexOf(prim[] array, prim target)	    给定值在数组最后出现的索引，若不包含此值返回-1	        List.lastIndexOf	                    符号无关
     * prim min(prim… array)	                        数组中最小的值	Collections.min	符号相关*
     * prim max(prim… array)	                        数组中最大的值	Collections.max	符号相关
     * String join(String separator, prim… array)	    把数组用给定分隔符连接为字符串	                    Joiner.on(separator).join	            符号相关
     * Comparator<prim[]> lexicographicalComparator()	按字典序比较原生类型数组的Comparator	                Ordering.natural().lexicographical()	符号相关
     *
     * 符号无关方法存在于Bytes, Shorts, Ints, Longs, Floats, Doubles, Chars, Booleans。而UnsignedInts, UnsignedLongs, SignedBytes, 或UnsignedBytes不存在
     *
     * 符号相关方法存在于SignedBytes, UnsignedBytes, Shorts, Ints, Longs, Floats, Doubles, Chars, Booleans, UnsignedInts, UnsignedLongs。而Bytes不存在
     */
    private static void arrayUtilities() {

        // 下面以int为例

        // asList 把数组转为相应包装类的List
        List<Integer> list = Ints.asList(1, 2, 3);

        // toArray 把集合拷贝为数组
        int[] arr = Ints.toArray(list);

        // concat 串联多个原生类型数组
        int[] arrArr = Ints.concat(arr, new int[]{4, 5});

        // contains 判断原生类型数组是否包含给定值
        boolean contains = Ints.contains(arr, 5);

        // indexOf 给定值在数组中首次出现处的索引，若不包含此值返回-1
        int index = Ints.indexOf(arr, 5);
        // -1
        System.out.println(index);

        int indexOf = Ints.indexOf(arr, new int[]{1, 2});
        // 0
        System.out.println(indexOf);

        // lastIndexOf 给定值在数组最后出现的索引，若不包含此值返回-1
        int lastIndexOf = Ints.lastIndexOf(new int[]{1, 2, 2}, 2);
        // 2
        System.out.println(lastIndexOf);

        // min 数组中最小的值
        int min = Ints.min(arr);

        // max 数组中最大的值
        int max = Ints.max(arr);

        // join 把数组用给定分隔符连接为字符串
        String join = Ints.join(",", 1, 2, 3);

        // lexicographicalComparator 按字典序比较原生类型数组的Comparator
        Comparator<int[]> comparator = Ints.lexicographicalComparator();
        // 1
        System.out.println(comparator.compare(new int[]{1, 2 ,3}, new int[]{1, 1, 5, 8}));
    }

    /**
     * 通用工具方法
     *
     * 方法签名	                        描述	                                                                            可用性
     * int compare(prim a, prim b)	    传统的Comparator.compare方法，但针对原生类型。JDK7的原生类型包装类也提供这样的方法	    符号相关
     * prim checkedCast(long value)	    把给定long值转为某一原生类型，若给定值不符合该原生类型，则抛出IllegalArgumentException	仅适用于符号相关的整型*
     * prim saturatedCast(long value)	把给定long值转为某一原生类型，若给定值不符合则使用最接近的原生类型值	                    仅适用于符号相关的整型
     *
     * 这里的整型包括byte, short, int, long。不包括char, boolean, float, 或double
     */
    private static void generalUtilities() {
        // 比较方法
        int compare = Ints.compare(2, 3);
        // -1
        System.out.println(compare);

        // 把给定long值转为某一原生类型，若给定值不符合该原生类型，则抛出IllegalArgumentException
        char c = Chars.checkedCast(234L);
        // ê
        System.out.println(c);

        // short sh = Shorts.checkedCast(23456789L);
        // java.lang.IllegalArgumentException: Out of range: 23456789
        // System.out.println(c);

        // 把给定long值转为某一原生类型，若给定值不符合则使用最接近的原生类型值
        short sh = Shorts.saturatedCast(23456789L);
        // 32767
        System.out.println(sh);
    }

    /**
     * 字节转换方法
     * Guava提供了若干方法，用来把原生类型按大字节序与字节数组相互转换。所有这些方法都是符号无关的，此外Booleans没有提供任何下面的方法
     *
     * 方法或字段签名	                        描述
     * int BYTES	                        常量：表示该原生类型需要的字节数
     * prim fromByteArray(byte[] bytes)	    使用字节数组的前Prims.BYTES个字节，按大字节序返回原生类型值；如果bytes.length <= Prims.BYTES，抛出IAE
     * prim fromBytes(byte b1, …, byte bk)	接受Prims.BYTES个字节参数，按大字节序返回原生类型值
     * byte[] toByteArray(prim value)	    按大字节序返回value的字节数组
     */
    private static void byteConversion() {

        // 该原生类型需要的字节数
        int intBytes = Ints.BYTES;
        int charBytes = Chars.BYTES;


        // 使用字节数组的前Prims.BYTES个字节，按大字节序返回原生类型值
        int fromByteArray = Ints.fromByteArray(new byte[]{0x12, 0x13, 0x14, 0x15, 0x33});
        // 12131415
        System.out.println(Integer.toHexString(fromByteArray));


        // 接受Prims.BYTES个字节参数，按大字节序返回原生类型值
        int fromBytes = Ints.fromBytes((byte)0x12, (byte)0x13, (byte)0x14, (byte)0x15);
        // 12131415
        System.out.println(Integer.toHexString(fromBytes));


        // 按大字节序返回value的字节数组
        byte[] bytes = Ints.toByteArray(18);

    }


    /**
     * 无符号支持
     */
    private static void unsignedSupport() {

        /**
         * 无符号通用工具方法
         * (JDK的原生类型包装类提供了有符号形式的类似方法)
         */
        // 按无符号十进制解析字符串
        int i = UnsignedInts.parseUnsignedInt("123");
        long l = UnsignedLongs.parseUnsignedLong("123");


        // 按无符号的特定进制解析字符串
        int radixTwo = UnsignedInts.parseUnsignedInt("110", 2);
        // 6
        System.out.println(radixTwo);

        long radix16 = UnsignedLongs.parseUnsignedLong("fff", 16);
        // 4095
        System.out.println(radix16);


        // 数字按无符号十进制转为字符串
        String intToString = UnsignedInts.toString(1024);
        String longToString = UnsignedLongs.toString(2014L);


        // 数字按无符号特定进制转为字符串
        String radix2 = UnsignedInts.toString(1024, 2);
        // 10000000000
        System.out.println(radix2);

        String radix8 = UnsignedLongs.toString(1234L, 8);
        // 2322
        System.out.println(radix8);



        /**
         * 无符号包装类
         * 无符号包装类包含了若干方法，让使用和转换更容易
         * 包括: UnsignedInteger, UnsignedLong
         */

        UnsignedInteger sixteen = UnsignedInteger.valueOf(16);

        // 简单算术运算
        // 加法运算
        // 26
        System.out.println(sixteen.plus(UnsignedInteger.valueOf(10)));

        // 减法运算
        // 14
        System.out.println(sixteen.minus(UnsignedInteger.valueOf(2)));

        // 乘法运算
        // 32
        System.out.println(sixteen.times(UnsignedInteger.valueOf(2)));

        // 除法运算
        // 8
        System.out.println(sixteen.dividedBy(UnsignedInteger.valueOf(2)));

        //模运算
        // 4
        System.out.println(sixteen.mod(UnsignedInteger.valueOf(12)));



        // 按给定BigInteger返回无符号对象，若BigInteger为负或不匹配，抛出IAE
        BigInteger bigIntNum = new BigInteger("2345678900");
        UnsignedInteger unsignedIntNum = UnsignedInteger.valueOf(bigIntNum);
        // 2345678900
        System.out.println(unsignedIntNum);

        // 按给定long返回无符号对象，若long为负或不匹配，抛出IAE
        UnsignedInteger unsignedIntNum2 = UnsignedInteger.valueOf(2345678900L);
        // 12345678901234567890
        System.out.println(unsignedIntNum2);


        // 把给定的值当作无符号类型
        // UnsignedInteger.asUnsigned(1<<31)的值为231,尽管1<<31当作int时是负的
        UnsignedInteger fromIntBits = UnsignedInteger.fromIntBits(1 << 31);
        // 2147483648
        System.out.println(fromIntBits);

        UnsignedInteger fromIntBits2 = UnsignedInteger.fromIntBits(-1);
        // 4294967295
        System.out.println(fromIntBits2);


        // 用BigInteger返回该无符号对象的值
        BigInteger bigInteger = sixteen.bigIntegerValue();
        // 16
        System.out.println(bigInteger);


        // 返回无符号值的字符串表示
        String str16 = sixteen.toString();
        // 16
        System.out.println(str16);

        String sixteenToRadix2 = sixteen.toString(2);
        // 10000
        System.out.println(sixteenToRadix2);

    }
}
