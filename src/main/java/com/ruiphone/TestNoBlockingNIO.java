package com.ruiphone;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

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
public class TestNoBlockingNIO {
    //客户端
    @Test
    public  void test01() throws IOException {
        //获取通道
        SocketChannel sChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9898));
        //切换非阻塞模式
        sChannel.configureBlocking(false);
        //指定缓冲区大小
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);






        Scanner  scanner=new Scanner(System.in);
        while(scanner.hasNext()){
            String sendStr = scanner.next();
            byteBuffer.put((new Date().toString()+"/"+sendStr).getBytes());
            //切换到 读模式
            byteBuffer.flip();
            sChannel.write(byteBuffer);
            //清空
            byteBuffer.clear();
        }






        sChannel.close();
    }


    //服务端
    @Test
    public  void test02() throws IOException {
        ServerSocketChannel socketChannel = ServerSocketChannel.open();
        //绑定链接
        socketChannel.bind(new InetSocketAddress(9898));
        socketChannel.configureBlocking(false);
        //获取选择器
        Selector selector = Selector.open();
        //讲通道注册到选择器上  ，并监听“监听接受事件”
        socketChannel.register(selector, SelectionKey.OP_ACCEPT);

        //轮询获取选择器上的 准备就绪事件
        while(selector.select()>0){
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

            while (iterator.hasNext()){
                SelectionKey next = iterator.next();

                if(next.isAcceptable()){
                    SocketChannel accept = socketChannel.accept();
                    //切换非阻塞模式
                    accept.configureBlocking(false);

                    accept.register(selector,SelectionKey.OP_READ);

                }else if(next.isReadable()){
                    //获取当前选择器上的读通道
                    SocketChannel socketChannel1= (SocketChannel) next.channel();

                    ByteBuffer allocate = ByteBuffer.allocate(1024);

                    int len=0;
                     while((len=socketChannel1.read(allocate))!=-1){
                         allocate.flip();
                         System.out.println(new String(allocate.array(),0,len));
                         allocate.clear();
                     }
                }


                iterator.remove();
            }
        }

    }

}
