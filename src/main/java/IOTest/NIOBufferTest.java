package IOTest;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;


/*
缓冲区的核心方法：
put（）：存入到缓冲区中
get（）：获取缓冲区中的数据
缓冲区的核心属性
capacity：容量，表示缓冲区中最大的存储数据的容量
limit：表示可以操作的缓冲区的数据的大小（limit之后的数据不能进行操作）
position：位置，表示缓冲区中正在操作数据的位置
mark：标记，记当前的position的位置，reset可以回到position位置
mark<=position<=limit<=capacity
 */
public class NIOBufferTest {


    @Test
    public void test1(){
        //1.分配一个指定大小的缓冲区
        ByteBuffer buf = ByteBuffer.allocate(1024);
        buf.put("youhuan".getBytes());
        System.out.println(buf.capacity());
        System.out.println(buf.limit());
        System.out.println(buf.position());
        //切换到读模式
        buf.flip();
        System.out.println(buf.capacity());
        System.out.println(buf.limit());
        System.out.println(buf.position());
        byte[] bytes = new byte[1024];
        buf.get(bytes,buf.position(),buf.limit());
        System.out.println(new String(bytes));
        System.out.println(buf.capacity());
        System.out.println(buf.limit());
        System.out.println(buf.position());

        //rewind可重复读数据
        buf.rewind();
        //clear清空缓冲区，其实只是重置指针的位置，但是不清空缓冲区，等待下次覆盖
    }


    @Test
    public void test2(){
        //mark标记，记当前的position的位置，reset可以回到position位置
        //1.分配一个指定大小的缓冲区
        ByteBuffer buf = ByteBuffer.allocate(1024);

        buf.put("youhuan".getBytes());

        buf.flip();
        byte[] bytes = new byte[1024];
        buf.get(bytes,0,2);
        System.out.println(new String(bytes));
        System.out.println(buf.position());
        buf.mark();
        buf.get(bytes,buf.position(),2);
        System.out.println(new String(bytes));
        System.out.println(buf.position());
        buf.reset();
        System.out.println(buf.position());
    }

}
