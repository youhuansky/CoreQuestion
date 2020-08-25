package IOTest;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

public class NonBlockingSocketTest {

    @Test
    public void Client(){

        try {
            //1.获取通道
            SocketChannel sc = SocketChannel.open(new InetSocketAddress("127.0.0.1", 7777));
            //2.切换非阻塞模式
            sc.configureBlocking(false);
            //3.指定缓冲区
            ByteBuffer buf = ByteBuffer.allocate(1024);
            //4.发送数据给服务端
            buf.put(new Date().toString().getBytes());
            buf.flip();
            sc.write(buf);
            buf.clear();
            sc.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void Server(){
        try {
            //1.获取通道
            ServerSocketChannel open = ServerSocketChannel.open();
            //2.切换非阻塞模式
            open.configureBlocking(false);
            //3.绑定链接
            open.bind(new InetSocketAddress(7777));
            //4.获取选择器
            Selector selector = Selector.open();
            //5.将通道注册到选择器上,并且监听接受事件
            open.register(selector, SelectionKey.OP_ACCEPT);
            //6.通过选择器轮询式获取选择器上已经准备就绪的时间

            while(selector.select()>0){
                //7.获取当前选择器中所有注册的“选择键（已就绪的监听事件）”
                Iterator<SelectionKey> it = selector.selectedKeys().iterator();
                //8.迭代获取准备就绪的事件
                while(it.hasNext()){
                    SelectionKey next = it.next();
                    //9.判断具体是什么时间准备就绪
                    if(next.isAcceptable()){
                        //10.获取客户端连接
                        SocketChannel accept = open.accept();
                        //11.切换非阻塞模式
                        accept.configureBlocking(false);
                        //12.将通道注册到选择器上
                        accept.register(selector, SelectionKey.OP_READ);

                    }else if(next.isReadable()){
                        //13.获取当前选择器上的“读就绪”状态的通道
                        SocketChannel channel = (SocketChannel)next.channel();
                        //14.读取数据
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        while(channel.read(byteBuffer)>0){
                            byteBuffer.flip();
                            System.out.println(new String(byteBuffer.array()));
                            byteBuffer.clear();
                        }

                    }
                    //取消选择键
                    it.remove();
                }

            }



        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
