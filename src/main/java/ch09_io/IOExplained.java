package ch09_io;

import java.io.*;

/**
 * I/O
 * @create 2018-06-14 下午11:52
 **/
public class IOExplained {
    public static void main(String args[]) throws FileNotFoundException {
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
    private static void byteStreamsAndCharStreams() throws FileNotFoundException {
        /**
         * 字节流 ByteStreams
         */

        InputStream is = new FileInputStream("test.txt");

    }
}
