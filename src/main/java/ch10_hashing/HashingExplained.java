package ch10_hashing;

import com.google.common.base.Charsets;
import com.google.common.hash.*;

/**
 * 散列
 *
 * @author yuxuan
 * @create 2018-06-20 上午12:10
 **/
public class HashingExplained {

    public static void main(String[] args) {
        // 散列包的组成
        organization();
    }

    /**
     * 散列包的组成
     */
    private static void organization() {

        // HashFunction是一个纯粹的无状态函数，它将任意数据块映射到固定数量的位，
        // 其性能是相等的输入始终产生相同的输出，不相等的输入会尽可能地产生不相等的输出
        HashFunction hf = Hashing.sha256();

        // Funnel描述了如何把一个具体的对象类型分解为原生字段值，从而写入PrimitiveSink
        Funnel<Person> personFunnel = new Funnel<Person>() {
            @Override
            public void funnel(Person person, PrimitiveSink into) {
                into
                        .putInt(person.id)
                        .putString(person.firstName, Charsets.UTF_8)
                        .putString(person.lastName, Charsets.UTF_8)
                        .putInt(person.birthYear);
            }
        };

        // HashFunction的实例可以提供有状态的Hasher，Hasher提供了流畅的语法把数据添加到散列运算，然后获取散列值
        // Hasher可以接受所有原生类型、字节数组、字节数组的片段、字符序列、特定字符集的字符序列等等，或者任何给定了Funnel实现的对象
        // Hasher实现了PrimitiveSink接口，这个接口为接受原生类型流的对象定义了fluent风格的API
        HashCode hc = hf.newHasher()
                .putLong(1)
                .putString("name", Charsets.UTF_8)
                .putObject(new Person(1, "xx", "yy", 1995), personFunnel)
                // 一旦Hasher被赋予了所有输入，就可以通过hash()方法获取HashCode实例（多次调用hash()方法的结果是不确定的）
                .hash();


        // HashCode可以通过asInt()、asLong()、asBytes()方法来做相等性检测
        // 此外，writeBytesTo(array, offset, maxLength)把散列值的前maxLength字节写入字节数组

        int i = hc.asInt();
        long l = hc.asLong();
        byte[] bytes = hc.asBytes();
        // 108 42 -25 65 -62 31 109 49 15 75 40 -89 15 -111 -23 -108 90 28 -61 59 28 -56 -48 107 61 -128 -8 -67 65 -2 -96 81
        printArray(bytes);

        byte[] array = new byte[10];
        int maxLength = hc.writeBytesTo(array, 2, 5);
        // 0 0 108 42 -25 65 -62 0 0 0
        printArray(array);
        // 5
        System.out.println(maxLength);

    }

    static class Person {
        final int id;
        final String firstName;
        final String lastName;
        final int birthYear;

        Person(int id, String firstName, String lastName, int birthYear) {
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
            this.birthYear = birthYear;
        }
    }

    private static void printArray(byte[] bytes) {
        for (byte aByte : bytes) {
            System.out.print(aByte + " ");
        }
        System.out.println();
    }
}
