#排序: Guava强大的”流畅风格比较器”
排序器[Ordering]是Guava流畅风格比较器[Comparator]的实现，它可以用来为构建复杂的比较器，以完成集合排序的功能
从实现上说，Ordering实例就是一个特殊的Comparator实例。Ordering把很多基于Comparator的静态方法（如Collections.max）包装为自己的实例方法（非静态方法），并且提供了链式调用方法，来定制和增强现有的比较器

###1. 创建排序器
````java
    /**
     * 常见的排序器可以由下面的静态方法创建
     */
    public static void creationMethod(){

        List<String> list = Lists.newArrayList();
        list.add("peter");
        list.add("jerry");
        list.add("harry");
        list.add("eva");
        list.add("john");
        list.add("neron");
        System.out.println("list:"+ list);

        // 对可排序类型做自然排序，如数字按大小，日期按先后排序
        Ordering<String> naturalOrdering = natural();
        // 按对象的字符串形式做字典排序
        Ordering<Object> usingToStringOrdering = usingToString();
        // 把给定的Comparator转化为排序器
        Ordering<String> fromOrdering = Ordering.from(new Comparator<String>() {
            public int compare(String o1, String o2) {
                return o1.hashCode() - o2.hashCode() ;
            }
        });

        System.out.println("naturalOrdering:"+ naturalOrdering.sortedCopy(list));
        System.out.println("usingToStringOrdering:"+ usingToStringOrdering.sortedCopy(list));
        System.out.println("fromOrdering:"+ fromOrdering.sortedCopy(list));
        
        // 实现自定义的排序器时，除了用上面的from方法，也可以跳过实现Comparator，而直接继承Ordering：
        Ordering<String> byLengthOrdering = new Ordering<String>() {
            public int compare(String left, String right) {
                return Ints.compare(left.length(), right.length());
            }
        };
    }

````
