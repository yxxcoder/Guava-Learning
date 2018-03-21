package collections.newcollection;

import com.google.common.collect.HashMultiset;

/**
 * 新集合类型
 *
 * @author yuxuan
 * @create 2018-03-18 下午11:10
 **/
public class NewCollectionTypes {

    /**
     * 可以多次添加相等的元素的集合
     * 可以理解为没有元素顺序限制的ArrayList<E>，Map<E, Integer>，键为元素，值为计数
     */
    public static void multiset() {
        HashMultiset hashMultiset = HashMultiset.create();
        hashMultiset.add("a");
        hashMultiset.add("b");

        /**
         * 当把Multiset看成普通的Collection时，它表现得就像无序的ArrayList
         */

        // add(E)添加单个给定元素
        System.out.println(hashMultiset.add("a"));
        // 减少给定元素在Multiset中的计数
        System.out.println(hashMultiset.remove("a"));
        // iterator()返回一个迭代器，包含Multiset的所有元素（包括重复的元素）
        System.out.println(hashMultiset.iterator().next());
        // size()返回所有元素的总个数（包括重复的元素）
        System.out.println(hashMultiset.size());


        /**
         * 当把Multiset看作Map<E, Integer>时，它也提供了符合性能期望的查询操作
         */

        // count(Object)返回给定元素的计数
        // HashMultiset.count的复杂度为O(1)，TreeMultiset.count的复杂度为O(log n)
        System.out.println(hashMultiset.count("a"));
        // 设置给定元素在Multiset中的计数，不可以为负数
        System.out.println(hashMultiset.setCount("c", 2));
    }

    public static void main(String args[]) {
        multiset();
    }
}
