package collections.extension;

import com.google.common.collect.*;
import com.google.common.primitives.Ints;

import java.util.Iterator;
import java.util.List;

/**
 * 集合扩展工具类
 * @author yuxuan
 * @create 2018-04-21 下午11:30
 **/
public class CollectionHelpers {
    public static void main(String args[]) {

        /**
         * Forwarding装饰器
         */
        // 在元素被添加到列表时增加特定的行为
        AddLoggingList<Integer> loggingList = new AddLoggingList<>();
        loggingList.add(1);



        /**
         * PeekingIterator
         * Iterators提供一个Iterators.peekingIterator(Iterator)方法，来把Iterator包装为PeekingIterator
         * 这是Iterator的子类，能事先窥视[peek()]到下一次调用next()返回的元素
         */

        // 举个例子：复制一个List，并去除连续的重复元素
        List<Integer> numbers = Ints.asList(1, 1, 2, 3, 3);
        List<Integer> results = Lists.newArrayList();

        PeekingIterator<Integer> iterator = Iterators.peekingIterator(numbers.iterator());
        while (iterator.hasNext()) {
            Integer current = iterator.next();
            while (iterator.hasNext() && iterator.peek().equals(current)) {
                // skip this duplicate element
                iterator.next();
            }
            results.add(current);
        }
        // [1, 2, 3]
        System.out.println(results);



        /**
         * AbstractIterator
         * 实现自己的Iterator
         */
        // 包装一个iterator跳过长度为1的字符串
        Iterator<String> skipNulls2 = skipSingle(Lists.newArrayList("aa", "b", "cc").iterator());
        // aa cc
        while (skipNulls2.hasNext()) {
            System.out.print(skipNulls2.next() + " ");
        }
        System.out.println();



        /**
         * AbstractSequentialIterator
         * 实现了computeNext(T)方法，接受前一个值作为参数
         * 注意，你必须额外传入一个初始值，或者传入null让迭代立即结束。
         * 因为computeNext(T)假定null值意味着迭代的末尾——AbstractSequentialIterator不能用来实现可能返回null的迭代器
         */
        // 注意初始值!
        Iterator<Integer> powersOfTwo = new AbstractSequentialIterator<Integer>(1) {
            protected Integer computeNext(Integer previous) {
                return (previous == 1 << 30) ? null : previous * 2;
            }
        };

    }

    // 包装一个iterator跳过长度为1的字符串
    public static Iterator<String> skipSingle(final Iterator<String> in) {
        return new AbstractIterator<String>() {
            protected String computeNext() {
                while (in.hasNext()) {
                    String s = in.next();
                    if (s.length() > 1) {
                        return s;
                    }
                }
                return endOfData();
            }
        };
    }

}
