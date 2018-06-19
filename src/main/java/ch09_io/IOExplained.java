package ch09_io;

import com.google.common.base.Charsets;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.graph.Traverser;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.common.io.*;
import java.io.*;
import java.net.URL;
import java.nio.file.StandardOpenOption;
import java.util.List;

import static com.google.common.base.Charsets.UTF_8;
import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.READ;

/**
 * I/O
 * @create 2018-06-14 下午11:52
 **/
public class IOExplained {

    private static File testFile = new File("src/main/java/ch09_io/test.txt");
    private static File newFile = new File("src/main/java/ch09_io/new.txt");

    public static void main(String args[]) throws Exception {
        // 字节流和字符流
        byteStreamsAndCharStreams();
        // 源与汇
        sourcesAndSinks();
        // 文件操作
        files();
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


    /**
     * 源与汇
     *
     * Guava提供的关于源与汇的抽象:
     *      字节	        字符
     * 读	ByteSource	CharSource
     * 写	ByteSink	CharSink
     */
    private static void sourcesAndSinks() throws Exception {
        /**
         * 创建源与汇
         * 
         * Guava提供了若干源与汇的实现：
         * 字节 Bytes                                    字符 Chars
         * Files.asByteSource(File)	                    Files.asCharSource(File, Charset)
         * Files.asByteSink(File, FileWriteMode...)	    Files.asCharSink(File, Charset, FileWriteMode...)
         * MoreFiles.asByteSource(Path, OpenOption...)	MoreFiles.asCharSource(Path, Charset, OpenOption...)
         * MoreFiles.asByteSink(Path, OpenOption...)	MoreFiles.asCharSink(Path, Charset, OpenOption...)
         * Resources.asByteSource(URL)	                Resources.asCharSource(URL, Charset)
         * ByteSource.wrap(byte[])	                    CharSource.wrap(CharSequence)
         * ByteSource.concat(ByteSource...)	            CharSource.concat(CharSource...)
         * ByteSource.slice(long, long)	                N/A
         * CharSource.asByteSource(Charset)	            ByteSource.asCharSource(Charset)
         * N/A	                                        ByteSink.asCharSink(Charset)
         */

        ByteSource fileAsByteSource = Files.asByteSource(testFile);

        ByteSink fileAsByteSink = Files.asByteSink(newFile, FileWriteMode.APPEND);

        ByteSource moreFilesByteSource = MoreFiles.asByteSource(testFile.toPath(), READ);

        ByteSink moreFilesByteSink = MoreFiles.asByteSink(newFile.toPath(), APPEND);

        ByteSource urlAsByteSource = Resources.asByteSource(new URL("https://www.baidu.com"));

        ByteSource wrap = ByteSource.wrap("Hello World".getBytes());

        ByteSource concat = ByteSource.concat(fileAsByteSource, wrap);

        ByteSource slice = fileAsByteSource.slice(5L, 10L);

        ByteSource charSourceAsByteSource = CharSource.wrap("Hello World").asByteSource(UTF_8);


        CharSource fileAsCharSource = Files.asCharSource(testFile, Charsets.UTF_8);

        CharSink fileAsCharSink = Files.asCharSink(newFile, Charsets.UTF_8, FileWriteMode.APPEND);

        CharSource moreFilesAsCharSource = MoreFiles.asCharSource(testFile.toPath(), Charsets.UTF_8, READ);

        CharSink moreFileAsCharSink = MoreFiles.asCharSink(newFile.toPath(), UTF_8, StandardOpenOption.APPEND);

        CharSource urlAsCharSource = Resources.asCharSource(new URL("https://www.baidu.com"), UTF_8);

        CharSource strWrap = CharSource.wrap("Hello World");

        CharSource concatCharSource = CharSource.concat(fileAsCharSource, strWrap);

        ByteSource asByteSource = concatCharSource.asByteSource(UTF_8);

        CharSink asCharSink = fileAsByteSink.asCharSink(UTF_8);


        /**
         * 通用操作
         * 所有源与汇都有一些方法用于打开新的流用于读或写
         * 默认情况下，其他源与汇操作都是先用这些方法打开流，然后做一些读或写，最后保证流被正确地关闭了
         * 这些方法列举如下：
         * openStream()：根据源与汇的类型，返回InputStream、OutputStream、Reader或者Writer
         * openBufferedStream()：根据源与汇的类型，返回InputStream、OutputStream、BufferedReader或者BufferedWriter
         *  返回的流保证在必要情况下做了缓冲。例如，从字节数组读数据的源就没有必要再在内存中作缓冲，这就是为什么该方法针对字节源不返回
         *  BufferedInputStream。字符源属于例外情况，它一定返回BufferedReader，因为BufferedReader中才有readLine()方法
         */

        InputStream inputStream = fileAsByteSource.openStream();
        OutputStream outputStream = fileAsByteSink.openStream();
        Reader reader = fileAsCharSource.openStream();
        Writer writer = fileAsCharSink.openStream();

        InputStream inputStream2 = fileAsByteSource.openBufferedStream();
        OutputStream outputStream2 = fileAsByteSink.openBufferedStream();
        BufferedReader bufferedReader = fileAsCharSource.openBufferedStream();
        Writer writer2 = moreFileAsCharSink.openBufferedStream();


        inputStream.close();
        outputStream.close();
        reader.close();
        writer.close();
        inputStream2.close();
        outputStream2.close();
        bufferedReader.close();
        writer2.close();


        /**
         * 源操作
         *
         * ByteSource	                        CharSource
         * byte[] read()	                    String read()
         * N/A	                                ImmutableList<String> readLines()
         * N/A	                                String readFirstLine()
         * long copyTo(ByteSink)	            long copyTo(CharSink)
         * long copyTo(OutputStream)	        long copyTo(Appendable)
         * Optional<Long> sizeIfKnown()	        Optional<Long> lengthIfKnown()
         * long size()	                        long length()
         * boolean isEmpty()	                boolean isEmpty()
         * boolean contentEquals(ByteSource)	N/A
         * HashCode hash(HashFunction)	        N/A
         */
        byte[] bytes = fileAsByteSource.read();
        // Hello World!
        println(new String(bytes));

        long total = fileAsByteSource.copyTo(fileAsByteSink);
        // 12
        println(total);

        long total2 = fileAsByteSource.copyTo(new FileOutputStream(newFile, true));
        // 12
        println(total2);

        Optional<Long> longOptional = fileAsByteSource.sizeIfKnown();
        // 12
        println(longOptional.get());

        long size = fileAsByteSource.size();
        // 12
        println(size);

        boolean contentEquals = fileAsByteSource.contentEquals(moreFilesByteSource);
        // true
        println(contentEquals);

        // SHA-1 a file
        HashCode hash = fileAsByteSource.hash(Hashing.sha256());
        // 7f83b1657ff1fc53b92dc18148a1d65dfc2d4b1fa3d677284addd200126d9069
        println(hash.toString());


        String read = fileAsCharSource.read();
        // Hello World!
        println(read);

        ImmutableList<String> strings = fileAsCharSource.readLines();
        // [Hello World!]
        println(strings);

        String firstLine = fileAsCharSource.readFirstLine();
        // Hello World!
        println(firstLine);

        long copyTotal = fileAsCharSource.copyTo(fileAsCharSink);
        // 12
        println(copyTotal);

        long copyTo = fileAsCharSource.copyTo(new StringBuilder());
        // 12
        println(copyTo);

        Optional<Long> lengthIfKnown = fileAsCharSource.lengthIfKnown();
        // false
        println(lengthIfKnown.isPresent());

        long length = fileAsCharSource.length();
        // 12
        println(length);

        boolean isEmpty = fileAsCharSource.isEmpty();
        // false
        println(isEmpty);



        /**
         * 汇操作
         *
         * ByteSink                     CharSink
         * void write(byte[])	        void write(CharSequence)
         * long writeFrom(InputStream)	long writeFrom(Readable)
         * N/A	                        void writeLines(Iterable<? extends CharSequence>)
         * N/A	                        void writeLines(Iterable<? extends CharSequence>, String)
         */
        fileAsByteSink.write("Hello World!".getBytes());

        fileAsByteSink.writeFrom(fileAsByteSource.openStream());


        fileAsCharSink.write("Hello World!");

        fileAsCharSink.writeFrom(fileAsCharSource.openStream());

        fileAsCharSink.writeLines(Lists.newArrayList("Hello World!", "Hello Tom!"));

        // 写入给定的文本行，每行（包括最后一行）以给定的行分隔符结尾
        fileAsCharSink.writeLines(Lists.newArrayList("Hello World", "Hello Tom"), "!");
    }


    /**
     * 文件操作
     * 除了创建文件源和文件的方法，Files类还包含了若干便利方法
     *
     * 方法                              描述
     * createParentDirs(File)           必要时为文件创建父目录
     * getFileExtension(String)	        返回给定路径所表示文件的扩展名
     * getNameWithoutExtension(String)	返回去除了扩展名的文件名
     * simplifyPath(String)	            规范文件路径，并不总是与文件系统一致，请仔细测试
     * fileTraverser()
     */
    private static void files() throws IOException {

        Files.createParentDirs(new File("aaa/bbb.txt"));

        String fileExtension = Files.getFileExtension("src/main/java/ch09_io/test.txt");
        // txt
        println(fileExtension);

        String nameWithoutExtension = Files.getNameWithoutExtension("src/main/java/ch09_io/test.txt");
        // test
        println(nameWithoutExtension);

        String simplifyPath = Files.simplifyPath("src/test/../main/java/ch09_io/test.txt");
        // src/main/java/ch09_io/test.txt
        println(simplifyPath);

        Traverser<File> fileTraverser = Files.fileTraverser();
        Iterable<File> src = fileTraverser.breadthFirst(new File("src"));
        println(src);
    }

    private static void println(Object object) {
        System.out.println(object.toString());
    }
}
