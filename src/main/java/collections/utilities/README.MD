# 集合工具类
任何对JDK集合框架有经验的程序员都熟悉和喜欢java.util.Collections包含的工具方法。Guava沿着这些路线提供了更多的工具方法：适用于所有集合的静态方法。这是Guava最流行和成熟的部分之一。

### 1. 静态工厂方法
```java
public static void staticConstructors() {

    // Guava提供了能够推断范型的静态工厂方法
    List<Integer> list = Lists.newArrayList();
    Map<String, String> map = Maps.newLinkedHashMap();

    // 用工厂方法模式，我们可以方便地在初始化时就指定起始元素
    Set<Integer> copySet = Sets.newHashSet(1, 2);
    List<String> theseElements = Lists.newArrayList("alpha", "beta", "gamma");

    // 通过为工厂方法命名（Effective Java第一条），我们可以提高集合初始化大小的可读性
    List<Integer> exactly100 = Lists.newArrayListWithCapacity(100);
    List<Integer> approx100 = Lists.newArrayListWithExpectedSize(100);
    Set<Integer> approx100Set = Sets.newHashSetWithExpectedSize(100);

    // Guava引入的新集合类型没有暴露原始构造器，也没有在工具类中提供初始化方法。而是直接在集合类中提供了静态工厂方法
    Multiset<String> multiset = HashMultiset.create();

}
```

### 2. Iterables工具类
```java
public static void iterable() {
    /**
     * 常规方法
     */
    // 串联多个iterables的懒视图
    // 懒视图意味着如果还没访问到某个iterable中的元素，则不会对它进行串联操作
    Iterable<Integer> concatenated = Iterables.concat(
            Ints.asList(1, 2, 3),
            Ints.asList(4, 5, 6));
    // [1, 2, 3, 4, 5, 6]
    System.out.println(concatenated);

    // 返回对象在iterable中出现的次数
    int num = Iterables.frequency(concatenated, 1);
    // 1
    System.out.println(num);

    // 把iterable按指定大小分割，得到的子集都不能进行修改操作
    Iterable<List<Integer>> partition = Iterables.partition(concatenated, 2);
    // [[1, 2], [3, 4], [5, 6]]
    System.out.println(partition);

    // 返回iterable的第一个元素，若iterable为空则返回默认值
    int firstValue = Iterables.getFirst(concatenated, 0);
    // 1
    System.out.println(firstValue);

    // 返回iterable的最后一个元素，若iterable为空则抛出NoSuchElementException
    int lastValue = Iterables.getLast(concatenated, 0);
    // 6
    System.out.println(lastValue);

    // 如果两个iterable中的所有元素相等且顺序一致，返回true
    Iterable<Integer> other = Iterables.concat(
            Ints.asList(4, 5, 6),
            Ints.asList(1, 2, 3));
    // [4, 5, 6, 1, 2, 3]
    System.out.println(other);
    boolean same = Iterables.elementsEqual(concatenated, other);
    // false
    System.out.println(same);

    // 返回iterable的不可变视图
    Iterable<Integer> unmodifiableIterable = Iterables.unmodifiableIterable(concatenated);
    // [1, 2, 3, 4, 5, 6]
    System.out.println(unmodifiableIterable);

    // 限制iterable的元素个数限制给定值
    Iterable<Integer> limitIterable = Iterables.limit(concatenated, 1);
    // [1]
    System.out.println(limitIterable);

    // 获取iterable中唯一的元素，如果iterable为空或有多个元素，则快速失败
    int value = Iterables.getOnlyElement(limitIterable);
    // 1
    System.out.println(value);


    /**
     * 与Collection方法相似的工具方法
     */
    List numbers = Lists.newArrayList(-1, 0);

    Iterables.addAll(numbers, concatenated);
    // [-1, 0, 1, 2, 3, 4, 5, 6]
    System.out.println(numbers);

    boolean contains = Iterables.contains(concatenated, 1);
    // true
    System.out.println(contains);

    boolean removeAll = Iterables.removeAll(numbers, Lists.newArrayList(6, 9));
    // true
    System.out.println(removeAll);
    // [-1, 0, 1, 2, 3, 4, 5]
    System.out.println(numbers);

    numbers = Lists.newArrayList(-1, 0);
    boolean retainAll = Iterables.retainAll(numbers, Lists.newArrayList(0));
    // true
    System.out.println(retainAll);
    // [0]
    System.out.println(numbers);

    int size = Iterables.size(concatenated);
    // 6
    System.out.println(size);

    Integer[] array = Iterables.toArray(concatenated, Integer.class);
    // 1 2 3 4 5 6
    for (Integer integer : array) {
        System.out.print(integer + " ");
    }
    System.out.println();

    boolean isEmpty = Iterables.isEmpty(Lists.newArrayList());
    // true
    System.out.println(isEmpty);

    int one = Iterables.get(concatenated, 1);
    // 2
    System.out.println(one);

    // [1, 2, 3, 4, 5, 6]
    String str = Iterables.toString(concatenated);
    System.out.println(str);
}
```

### 3. Lists工具类
```java
/**
 * Lists工具类
 */
public static void lists() {

    List countUp = Ints.asList(1, 2, 3, 4, 5);

    List countDown = Lists.reverse(countUp);
    // {5, 4, 3, 2, 1}
    System.out.println(countDown);


    List<List> parts = Lists.partition(countUp, 2);
    // {{1,2}, {3,4}, {5}}
    System.out.println(countDown);

    /**
     * Lists提供如下静态工厂方法
     */
    List list1 = Lists.newArrayList();
    List list2 = Lists.newArrayList(1, 2);
    List list3 = Lists.newArrayList(Iterables.concat());
    List list4 = Lists.newArrayList(Ints.asList(1).iterator());
    // 分配一个容量为10的数组
    List list5 = Lists.newArrayListWithCapacity(10);
    // 5L + arraySize + (arraySize / 10) = 16 分配一个容量为16的数组
    List list6 = Lists.newArrayListWithExpectedSize(10);

    LinkedList<Integer> linkedList1 = Lists.newLinkedList();
    LinkedList linkedList2 = Lists.newLinkedList(Iterables.concat());

}

```