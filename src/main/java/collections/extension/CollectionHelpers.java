package collections.extension;

import com.google.common.collect.ForwardingList;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.PeekingIterator;
import com.google.common.primitives.Ints;

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
    }

}
