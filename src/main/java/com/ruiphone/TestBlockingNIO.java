package com.ruiphone;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Description:
 * 1.channel  通道
 * java.nio.channels.Channel
 *              |--SelectChannel
 *                   SocketChannel
 *                   ServerSocketChannel
 *                   DatagramChannel
 *
 *                   Pipe.SinkChannel
 *                   Pipe.SourceChannel
 *
 *
 * @author wang zifeng
 * @Date Create on 2019-08-19 18:47
 * @since version1.0 Copyright 2018 Burcent All Rights Reserved.
 */
public class TestBlockingNIO {
    //客户端
    @Test
    public  void test01() throws IOException {
        SocketChannel open = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9898));
        ByteBuffer allocate = ByteBuffer.allocate(1024);

        FileChannel open1 = FileChannel.open(Paths.get("C:\\Users\\admin\\Desktop\\png\\12.jpg"), StandardOpenOption.READ);
        while(open1.read(allocate)!=-1){
            allocate.flip();
            open.write(allocate);
            allocate.clear();
        }


        open.shutdownOutput();

        //接受服务端反馈
        int len=0;
        while((len=open.read(allocate))!=-1){
            allocate.flip();
            System.out.println(new String(allocate.array(),0,len));
            allocate.clear();
        }

        open.close();
        open1.close();
    }

    @Test
    public  void test02() throws IOException {
            ServerSocketChannel open = ServerSocketChannel.open();
        FileChannel out = FileChannel.open(Paths.get("C:\\Users\\admin\\Desktop\\png\\45.jpg"), StandardOpenOption.WRITE, StandardOpenOption.READ);
        open.bind(new InetSocketAddress(9898));
        ByteBuffer allocate = ByteBuffer.allocate(1024);
        SocketChannel accept = open.accept();
        while(accept.read(allocate)!=-1){
            allocate.flip();
            out.write(allocate);
            allocate.clear();
        }


        //发送反馈给客户端
        allocate.clear();
        allocate.put("服务端接受成功".getBytes());
        allocate.flip();
        accept.write(allocate);

        accept.close();
        out.close();
        open.close();

    }

}
