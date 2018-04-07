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