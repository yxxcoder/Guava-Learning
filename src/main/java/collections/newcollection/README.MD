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

### 3. BiMap
```java
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
    
```

### 4. Table
```java
 /**
 * 带有两个支持所有类型的键：”行”和”列”
 * Table有如下几种实现：
 * HashBasedTable：本质上用HashMap<R, HashMap<C, V>>实现；
 * TreeBasedTable：本质上用TreeMap<R, TreeMap<C,V>>实现；
 * ImmutableTable：本质上用ImmutableMap<R, ImmutableMap<C, V>>实现；注：ImmutableTable对稀疏或密集的数据集都有优化。
 * ArrayTable：要求在构造时就指定行和列的大小，本质上由一个二维数组实现，以提升访问速度和密集Table的内存利用率
 */
public static void table() {
    Table<Integer, Integer, Double> weightedGraph = HashBasedTable.create();
    weightedGraph.put(1, 2, 4d);
    weightedGraph.put(1, 3, 20d);
    weightedGraph.put(2, 3, 5d);
    System.out.println(weightedGraph);

    // 用Map<R, Map<C, V>>表现Table<R, C, V>
    Map<Integer, Map<Integer, Double>> map = weightedGraph.rowMap();
    System.out.println(map);

    // 返回”行”的集合Set<R>
    Set<Integer> set = weightedGraph.rowKeySet();
    System.out.println(set);

    // 用Map<C, V>返回给定”行”的所有列
    // 类似的列访问方法：columnMap()、columnKeySet()、column(c)
    // （基于列的访问会比基于的行访问稍微低效点）
    Map<Integer, Double> row = weightedGraph.row(2);
    System.out.println(set);
    // 对这个map进行的写操作也将写入Table中
    row.put(4, 6d);
    row.put(5, 8d);
    System.out.println(weightedGraph);

    // 用元素类型为Table.Cell<R, C, V>的Set表现Table<R, C, V>
    // Cell类似于Map.Entry，但它是用行和列两个键区分的
    Set<Table.Cell<Integer, Integer, Double>> cells = weightedGraph.cellSet();
    System.out.println(cells);

}
```

### 5. ClassToInstanceMap
ClassToInstanceMap是一种特殊的Map：它的键是类型，而值是符合键所指类型的对象
对于ClassToInstanceMap，Guava提供了两种有用的实现：MutableClassToInstanceMap和 ImmutableClassToInstanceMap
```java
public static void classToInstanceMap() {

    ClassToInstanceMap<Number> numberDefaults=MutableClassToInstanceMap.create();
    numberDefaults.putInstance(Integer.class, Integer.valueOf(0));

    Integer instance =  numberDefaults.getInstance(Integer.class);
    System.out.println(instance);

}
```

### 6. RangeSet
RangeSet描述了一组不相连的、非空的区间
当把一个区间添加到可变的RangeSet时，所有相连的区间会被合并，空区间会被忽略

```java
public static void rangeSet() {
    RangeSet<Integer> rangeSet = TreeRangeSet.create();

    rangeSet.add(Range.closed(1, 10)); // {[1,10]}
    System.out.println(rangeSet.toString());

    rangeSet.add(Range.closedOpen(11, 15));//不相连区间:{[1,10], [11,15)}
    System.out.println(rangeSet.toString());

    rangeSet.add(Range.closedOpen(15, 20)); //相连区间; {[1,10], [11,20)}
    System.out.println(rangeSet.toString());

    rangeSet.add(Range.openClosed(0, 0)); //空区间; {[1,10], [11,20)}
    System.out.println(rangeSet.toString());

    rangeSet.remove(Range.open(5, 10)); //分割[1, 10]; {[1,5], [10,10], [11,20)}
    System.out.println(rangeSet.toString());

}
```

