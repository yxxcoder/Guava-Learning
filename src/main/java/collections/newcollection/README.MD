# 新集合类型
Guava引入了很多JDK没有的、但我们发现明显有用的新集合类型。这些新类型是为了和JDK集合框架共存，而没有往JDK集合抽象中硬塞其他概念。作为一般规则，Guava集合非常精准地遵循了JDK接口契约

### 1. Multiset
Guava提供了一个新集合类型 Multiset，它可以多次添加相等的元素。
````java
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
````

### 2. Multimap
Multimap可以把键映射到任意多个值
````java
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
````


