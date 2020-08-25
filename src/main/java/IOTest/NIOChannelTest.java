package IOTest;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * java.nio.channels.Channel
 *      \--fileChannel
 *      \--SocketChannel
 *      \--ServerSocketChannel
 *      \--DataGramChannel
 *
 * 获取通道
 *
 * 本地IO：
 * FileInputStream/FileOutputStream
 * RandomAccessFile
 *
 * 网络IO：
 * Socket
 * ServerSocket
 * DatagramSocket
 *
 * JDK1.7中各个通道提供了静态方法open（）
 * JDK1.7中Files工具类的newByteChannel（）
 *
 * Charset字符集，解决乱码
 * 编码：字符串转换成字符数组
 * 解码：字节数组转换成字符串
 */

public class NIOChannelTest {
    //Charset
    @Test
    public void test5() {
        Charset gbk = Charset.forName("GBK");
        //获取编码器和解码器
        CharsetEncoder charsetEncoder = gbk.newEncoder();
        CharsetDecoder charsetDecoder = gbk.newDecoder();

        CharBuffer cb = CharBuffer.allocate(1024);
        cb.put("游欢，啦啦啦");
        cb.flip();
        try {
            ByteBuffer encode = charsetEncoder.encode(cb);
            String s = new String(encode.array(), "GBK");
            System.out.println(s);
        } catch (Exception e) {
            e.printStackTrace();
        }




    }
    //分散和聚集buffer
    @Test
    public void test4() {

        try {
            RandomAccessFile rw = new RandomAccessFile("C:\\Users\\HP\\Desktop\\file.txt", "rw");
            //1.获取通道
            FileChannel channel = rw.getChannel();
            //2.分配指定大小的缓冲区
            ByteBuffer buf1 = ByteBuffer.allocate(10);
            ByteBuffer buf2 = ByteBuffer.allocate(1024);
            ByteBuffer[] bufs={buf1,buf2};

            channel.read(bufs);
            for (ByteBuffer  buf:bufs) {
                buf.flip();
            }

            System.out.println(new String(buf1.array(),0,buf1.limit()));
            System.out.println("================================");
            System.out.println(new String(buf2.array(),0,buf2.limit()));
            RandomAccessFile rw2 = new RandomAccessFile("C:\\Users\\HP\\Desktop\\file2.txt", "rw");
            FileChannel channel1 = rw2.getChannel();
            channel1.write(bufs);
            channel.close();
            channel1.close();
            rw2.close();
            rw.close();
        } catch (Exception e) {

        }

    }
    //使用transform（）完成数据传输（直接缓冲区）
    @Test
    public void test3() {
        try {
            FileChannel inChannel = FileChannel.open(Paths.get("C:\\Users\\HP\\Desktop\\file.txt"), StandardOpenOption.READ);
            FileChannel outChannel = FileChannel.open(Paths.get("C:\\Users\\HP\\Desktop\\file2.txt"), StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE_NEW);

            inChannel.transferTo(0,inChannel.size(),outChannel);
        }catch (Exception e){

        }
    }
    //用直接缓冲区完成文件的复制（内存映射文件）
    @Test
    public void test2(){
        try {
            FileChannel inChannel = FileChannel.open(Paths.get("C:\\Users\\HP\\Desktop\\file.txt"), StandardOpenOption.READ);
            FileChannel outChannel = FileChannel.open(Paths.get("C:\\Users\\HP\\Desktop\\file3.txt"), StandardOpenOption.WRITE,StandardOpenOption.READ,StandardOpenOption.CREATE_NEW);
            MappedByteBuffer inByteBuffer = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());//内存映射文件，直接内存
            MappedByteBuffer outByteBuffer = outChannel.map(FileChannel.MapMode.READ_WRITE, 0, inChannel.size());//内存映射文件，直接内存
            byte[] bytes = new byte[inByteBuffer.limit()];
            inByteBuffer.get(bytes);
            outByteBuffer.put(bytes);
            outChannel.close();
            inChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    //利用通道完成通道的复制
    @Test
    public void test(){

        try {
            FileInputStream fis = new FileInputStream("C:\\Users\\HP\\Desktop\\file.txt");
            FileOutputStream fos = new FileOutputStream("C:\\Users\\HP\\Desktop\\filenew.txt");
            FileChannel inChannel = fis.getChannel();
            FileChannel outChannel = fos.getChannel();

            ByteBuffer buf = ByteBuffer.allocate(1024);
            while (inChannel.read(buf)!=-1){
                buf.flip();
                outChannel.write(buf);
                buf.clear();
            }
            outChannel.close();
            inChannel.close();
            fos.close();
            fis.close();


        } catch (Exception e) {
            e.printStackTrace();
        }


    }



}
