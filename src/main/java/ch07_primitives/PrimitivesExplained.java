package ch07_primitives;

import com.google.common.primitives.Chars;
import com.google.common.primitives.Ints;
import com.google.common.primitives.Shorts;

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
        //
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
}
