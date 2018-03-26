package collections.newcollection;

import com.google.common.collect.*;

import java.util.Set;

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
     * Guava提供了多种Multiset的实现，如
     * Map	                对应的Multiset	        是否支持null元素
     * HashMap	            HashMultiset	            是
     * TreeMap	            TreeMultiset	     是（如果comparator支持的话）
     * LinkedHashMap	    LinkedHashMultiset	        是
     * ConcurrentHashMap  ConcurrentHashMultiset	    否
     * ImmutableMap	        ImmutableMultiset	        否

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
        // 和Map的entrySet类似，返回Set<Multiset.Entry<E>>，其中包含的Entry支持getElement()和getCount()方法
        System.out.println(hashMultiset.entrySet());
        // 返回所有不重复元素的Set<E>，和Map的keySet()类似
        System.out.println(hashMultiset.elementSet());
    }

    /**
     * 把键映射到任意多个值的一般方式
     *
     * 实现	                    键行为类似	    值行为类似
     * ArrayListMultimap	    HashMap	        ArrayList
     * HashMultimap	            HashMap	        HashSet
     * LinkedListMultimap*	    LinkedHashMap*	LinkedList*
     * LinkedHashMultimap**	    LinkedHashMap	LinkedHashMap
     * TreeMultimap	            TreeMap	        TreeSet
     * ImmutableListMultimap	ImmutableMap	ImmutableList
     * ImmutableSetMultimap	    ImmutableMap	ImmutableSet
     */
    public static void multimap() {
        HashMultimap multimap = HashMultimap.create();
        multimap.put("a", "aa");
        multimap.put("a", "ss");
        multimap.put("b", "ss");
        multimap.put("d", "ss");

        // 总是返回非null、但是可能空的集合
        System.out.println(multimap.get("c"));
        // 像Map一样，没有的键返回null
        System.out.println(multimap.asMap());
        // 且仅当有值映射到键时，Multimap.containsKey(key)才会返回true
        multimap.remove("b", "ss");
        System.out.println(multimap.containsKey("b"));
        // 返回Multimap中所有”键-单个值映射”——包括重复键
        System.out.println(multimap.entries());
        // 得到所有”键-值集合映射”
        System.out.println(multimap.asMap().entrySet());
        // 返回所有”键-单个值映射”的个数
        System.out.println(multimap.size());
        // 不同键的个数
        System.out.println(multimap.asMap().size());

    }


    /**
     * 实现键值对的双向映射的map
     * 可以用 inverse()反转BiMap<K, V>的键值映射
     * 保证值是唯一的，因此 values()返回Set而不是普通的Collection
     *
     * 键–值实现	        值–键实现	        对应的BiMap实现
     * HashMap	        HashMap	        HashBiMap
     * ImmutableMap	    ImmutableMap	ImmutableBiMap
     * EnumMap	        EnumMap	        EnumBiMap
     * EnumMap	        HashMap	        EnumHashBiMap
     */
    public static void biMap() {

        BiMap<String, Integer> userId = HashBiMap.create();
        userId.put("a", 123);

        // 把键映射到已经存在的值，会抛出IllegalArgumentException异常
        // userId.put("b", 123);

        // 强制替换它的键
        userId.forcePut("b", 123);
        System.out.println(userId);

        // 反转BiMap<K, V>的键值映射
        System.out.println(userId.inverse().get(123));

        // 保证值是唯一的，因此 values()返回Set而不是普通的Collection
        Set<Integer> IdSet = userId.values();
        System.out.println(IdSet);

    }


    public static void main(String args[]) {
        multiset();
        multimap();
        biMap();
    }
}
