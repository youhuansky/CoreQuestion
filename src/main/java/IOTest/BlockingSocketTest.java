package IOTest;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * 一、使用NIO完成网络通信的三个核心：
 * 1.通道（Channel）负责链接
 * 2.缓冲区（Buffer）负责数据存取
 * 3.选择器（Selector）是SelectableChannel的多路复用器，用于监控SelectableChannel的IO状况
 */


public class BlockingSocketTest {


    @Test
    public void client() {
        try {
            //1.获取通道
            SocketChannel channel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 7777));
            FileChannel fc = FileChannel.open(Paths.get("C:\\Users\\HP\\Desktop\\file.txt"), StandardOpenOption.READ);
            //2.分配指定大小的缓冲区
            ByteBuffer bb = ByteBuffer.allocate(1024);
            //3.读取本地文件，并发送到服务端
            while (fc.read(bb) != -1) {
                bb.flip();
                channel.write(bb);
                bb.clear();
            }
            fc.close();
            channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Test
    public void Server() {
        try {
            //1.获取通道
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(7777));
            SocketChannel sc = serverSocketChannel.accept();

            FileChannel fc = FileChannel.open(Paths.get("C:\\Users\\HP\\Desktop\\file4.txt"), StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.CREATE_NEW);
            //2.分配指定大小的缓冲区
            ByteBuffer bb = ByteBuffer.allocate(1024);
            //3.读取本地文件，并发送到服务端
            while (sc.read(bb) != -1) {
                bb.flip();
                fc.write(bb);
                bb.clear();
            }
            fc.close();
            sc.close();
            serverSocketChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
