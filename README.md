# Guava基础案例
本项目为Guava基础的使用案例，如您觉得该项目对您有用，欢迎点击右上方的Star按钮，给予支持！！

## 1. 基本工具 [Basic utilities]
让使用Java语言变得更舒适

### [1.1 使用和避免null](https://github.com/yxxcoder/Guava-Learning/tree/master/src/main/java/ch01_basic/optional)
null是模棱两可的，会引起令人困惑的错误，有些时候它让人很不舒服。很多Guava工具类用快速失败拒绝null值，而不是盲目地接受

### [1.2 前置条件](https://github.com/yxxcoder/Guava-Learning/tree/master/src/main/java/ch01_basic/preconditions)
让方法中的条件检查更简单

### [1.3 常见Object方法](https://github.com/yxxcoder/Guava-Learning/tree/master/src/main/java/ch01_basic/object)
简化Object方法实现，如hashCode()和toString()

### [1.4 排序: Guava强大的”流畅风格比较器”](https://github.com/yxxcoder/Guava-Learning/tree/master/src/main/java/ch01_basic/ordering)
排序器[Ordering]是Guava流畅风格比较器[Comparator]的实现，可以用来为构建复杂的比较器，以完成集合排序的功能

### [1.5 Throwables：简化了异常和错误的传播与检查](https://github.com/yxxcoder/Guava-Learning/tree/master/src/main/java/ch01_basic/throwables)
简化了异常和错误的传播与检查


## 2. 集合 [Collections]
Guava对JDK集合的扩展，这是Guava最成熟和为人所知的部分

### [2.1 不可变集合](https://github.com/yxxcoder/Guava-Learning/tree/master/src/main/java/ch02_collections/immutable)
用不变的集合进行防御性编程和性能提升

### [2.2 新集合类型](https://github.com/yxxcoder/Guava-Learning/tree/master/src/main/java/ch02_collections/newcollection)
multisets, multimaps, tables, bidirectional maps等

### [2.3 强大的集合工具类](https://github.com/yxxcoder/Guava-Learning/tree/master/src/main/java/ch02_collections/utilities)
提供java.util.Collections中没有的集合工具

### [2.4 扩展工具类](https://github.com/yxxcoder/Guava-Learning/tree/master/src/main/java/ch02_collections/extension)
让实现和扩展集合类变得更容易，比如创建Collection的装饰器，或实现迭代器


## 3. [缓存 [Caches]](https://github.com/yxxcoder/Guava-Learning/tree/master/src/main/java/ch03_caches)
Guava Cache：本地缓存实现，支持多种缓存过期策略


## 4. [函数式风格 [Functional idioms]](https://github.com/yxxcoder/Guava-Learning/tree/master/src/main/java/ch04_functional)
Guava Cache：本地缓存实现，支持多种缓存过期策略


## 5. [并发 [Concurrency]](https://github.com/yxxcoder/Guava-Learning/tree/master/src/main/java/ch05_concurrency)
强大而简单的抽象，让编写正确的并发代码更简单


## 6. [字符串处理 [String]](https://github.com/yxxcoder/Guava-Learning/tree/master/src/main/java/ch06_strings)
非常有用的字符串工具，包括分割、连接、填充等操作


## 7. [原生类型[Primitives]](https://github.com/yxxcoder/Guava-Learning/tree/master/src/main/java/ch07_primitives)
扩展 JDK 未提供的原生类型（如int、char）操作， 包括某些类型的无符号形式


## 8. [区间[Ranges]](https://github.com/yxxcoder/Guava-Learning/tree/master/src/main/java/ch08_ranges)
可比较类型的区间API，包括连续和离散类型


## 9. [I/O](https://github.com/yxxcoder/Guava-Learning/tree/master/src/main/java/ch09_io)
简化I/O尤其是I/O流和文件的操作，针对Java5和6版本


## 10. [散列[Hash]](https://github.com/yxxcoder/Guava-Learning/tree/master/src/main/java/ch10_hashing)
提供比Object.hashCode()更复杂的散列实现，并提供布鲁姆过滤器的实现


## 11. [事件总线[EventBus]](https://github.com/yxxcoder/Guava-Learning/tree/master/src/main/java/ch11_event)
发布-订阅模式的组件通信，但组件不需要显式地注册到其他组件中


## 12. [数学运算[Math]](https://github.com/yxxcoder/Guava-Learning/tree/master/src/main/java/ch12_math)
优化的、充分测试的数学工具类


## 13. [反射[Reflection]](https://github.com/yxxcoder/Guava-Learning/tree/master/src/main/java/ch13_reflection)
优化的、充分测试的数学工具类


