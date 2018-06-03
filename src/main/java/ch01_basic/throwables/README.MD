# Throwables：简化异常和错误的传播与检查

### 1. 异常传播方法
Guava中的异常传播方法简要列举如下：
````java
// 类型为 X 时才抛出
Throwables.throwIfInstanceOf(t, SQLException.class);

// 类型为Error或RuntimeException时抛出
Throwables.throwIfUnchecked(t);
````

### 2. 异常原因链
Guava提供了如下三个有用的方法，让研究异常的原因链变得稍微简便了，这三个方法的签名是不言自明的:
````java
// 获取异常原因链
Throwable throwable = Throwables.getRootCause(t);
List<Throwable> throwables = Throwables.getCausalChain(t);
String str = Throwables.getStackTraceAsString(t);
````


