package basic.object;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;

/**
 * 常见Object方法
 * @author yuxuan
 * @create 2018-02-28 下午9:08
 **/
public class ObjectExplained {
    /**
     * 使用Objects.equal帮助执行null敏感的equals判断，从而避免抛出NullPointerException
     * 注意：JDK7引入的Objects类提供了一样的方法Objects.equals
     */
    public static void equalsMethod() {
        // returns true
        Objects.equal("a", "a");
        // returns false
        Objects.equal(null, "a");
        // returns false
        Objects.equal("a", null);
        // returns true
        Objects.equal(null, null);

    }

    /**
     * 对传入的字段序列计算出合理的、顺序敏感的散列值
     * 可以使用Objects.hashCode(field1, field2, …, fieldn)来代替手动计算散列值
     * 注意：JDK7引入的Objects类提供了一样的方法Objects.hash(Object...)
     */
    public static void hashCodeMethod() {
        int hash = Objects.hashCode(2, "hello", null);
        System.out.println(hash);
    }

    /**
     * 轻松编写有用的toString方法
     */
    public void toStringHelperMethod() {

        // Returns "ClassName{x=1}"
        String s1 = MoreObjects.toStringHelper(this)
                .add("x", 1)
                .toString();
        System.out.println(s1);

        // Returns "MyObject{x=1}"
        String s2 = MoreObjects.toStringHelper("MyObject")
                .add("x", 1)
                .toString();
        System.out.println(s2);

    }

    class Person implements Comparable<Person> {
        String name;
        int age;
        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        /**
         * 执行比较操作直至发现非零的结果，在那之后的比较输入将被忽略
         * @param that
         * @return
         */
        public int compareTo(Person that) {
            return ComparisonChain.start()
                    .compare(this.age, that.age)
                    .compare(this.name, that.name)
                    .result();
        }
    }
    public void compare() {
        Person a = new Person("a", 10);
        Person b = new Person("a", 10);
        // Returns 1
        System.out.println(b.compareTo(a));
    }

    public static void main(String args[]) {
        equalsMethod();
        hashCodeMethod();
        new ObjectExplained().toStringHelperMethod();
        new ObjectExplained().compare();
    }

}
