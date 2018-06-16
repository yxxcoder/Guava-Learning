package ch09_io;

import com.google.common.io.ByteStreams;
import com.google.common.io.CharStreams;

import java.io.*;
import java.util.List;

/**
 * I/O
 * @create 2018-06-14 下午11:52
 **/
public class IOExplained {
    public static void main(String args[]) throws Exception {
        // 字节流和字符流
        byteStreamsAndCharStreams();
    }

    /**
     * 字节流和字符流工具类
     *
     * Guava提供的流工具:
     *  ByteStreams	                            CharStreams
     *  byte[] toByteArray(InputStream)	        String toString(Readable)
     *  N/A	                                    List<String> readLines(Readable)
     *  long copy(InputStream, OutputStream)	long copy(Readable, Appendable)
     *  void readFully(InputStream, byte[])	    N/A
     *  void skipFully(InputStream, long)	    void skipFully(Reader, long)
     *  OutputStream nullOutputStream()	        Writer nullWriter()
     */
    private static void byteStreamsAndCharStreams() throws Exception {

        /**
         * 字节流工具类 ByteStreams
         */
        InputStream is = new FileInputStream("src/main/java/ch09_io/test.txt");
        OutputStream os = new FileOutputStream("src/main/java/ch09_io/new.txt");

        // 读取输入流到数组
        byte[] bytes = ByteStreams.toByteArray(is);
        // Hello World!
        println(new String(bytes));

        is = new FileInputStream("src/main/java/ch09_io/test.txt");
        // 输入流的内容写入到输出流
        long copy = ByteStreams.copy(is, os);
        // 12
        println(copy);

        is = new FileInputStream("src/main/java/ch09_io/test.txt");
        byte[] b = new byte[10];
        // 读取输入流到指定的byte数组, 如果此流在读取所有字节之前到达数组结尾会抛出 EOFException 异常
        ByteStreams.readFully(is, b);

        is = new FileInputStream("src/main/java/ch09_io/test.txt");
        // 丢弃输入流的n个bytes, 如果此流在读取n个字节之前到达输入流的结尾会抛出 EOFException 异常
        ByteStreams.skipFully(is, 5);
        //  World!
        println(new String(ByteStreams.toByteArray(is)));

        // 返回一个简单地丢弃写入的输出流
        os = ByteStreams.nullOutputStream();

        is.close();
        os.close();


        /**
         * 字符流工具类 ByteStreams
         */
        Reader reader = new StringReader("Hello World!");

        // 读取所有的字符到一个字符串中
        String string = CharStreams.toString(reader);
        // Hello World!
        println(string);

        // 从字符流读取所有的行到list中
        List<String> strings = CharStreams.readLines(new StringReader("Hello \n World! "));
        // --- Hello  ---
        //---  World!  ---
        strings.forEach(str -> println("--- " + str + " ---"));

        // 复制所有字符, 返回字符长度
        StringBuffer sb = new StringBuffer();
        long number = CharStreams.copy(new StringReader("Hello World! "), sb);
        // number: 13, String: Hello World!
        println(String.format("number: %d, String: %s", number, sb.toString()));

        reader = new StringReader("Hello World!");
        // 丢弃输入流的n个字符, 如果此流在读取n个字符之前到达输入流的结尾会抛出 EOFException 异常
        CharStreams.skipFully(reader, 3);
        // lo World!
        println(CharStreams.toString(reader));

        // 返回一个简单地丢弃写入的输出流
        Writer writer = CharStreams.nullWriter();

        reader.close();
        writer.close();
    }

    private static void println(Object object) {
        System.out.println(object.toString());
    }
}
