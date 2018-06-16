package ch09_io;

import com.google.common.io.ByteStreams;
import java.io.*;

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
     * 字节流和字符流
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
         * 字节流 ByteStreams
         */
        InputStream is = new FileInputStream("src/main/java/ch09_io/test.txt");
        OutputStream os = new FileOutputStream("src/main/java/ch09_io/new.txt");

        // 读取输入流到数组
        byte[] bytes = ByteStreams.toByteArray(is);
        println(new String(bytes));

        is = new FileInputStream("src/main/java/ch09_io/test.txt");
        // 输入流的内容写入到输出流
        long copy = ByteStreams.copy(is, os);
        println(copy);

        is = new FileInputStream("src/main/java/ch09_io/test.txt");
        byte[] b = new byte[10];
        // 读取输入流到指定的byte数组, 如果此流在读取所有字节之前到达数组结尾会抛出 EOFException 异常
        ByteStreams.readFully(is, b);

        is = new FileInputStream("src/main/java/ch09_io/test.txt");
        // 丢弃输入流的n个bytes, 如果此流在读取n个字节之前到达输入流的结尾会抛出 EOFException 异常
        ByteStreams.skipFully(is, 5);
        println(new String(ByteStreams.toByteArray(is)));

        // 返回一个简单地丢弃写入的输出流
        os = ByteStreams.nullOutputStream();

        is.close();
        os.close();

    }

    private static void println(Object object) {
        System.out.println(object.toString());
    }
}
