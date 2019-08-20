package com.ruiphone.channel;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.Set;
import java.util.SortedMap;

/**
 * Description:
 * --FileChannel (本地)
 *
 *   socketChannel
 *   serverSockerChannel
 *   DatagramChannel （网络）
 *
 * 获取通道
 * 1.getChannel();
 *      FileInputStream/FileOutStream
 *
 *
 *      socket
 *      serverSocker
 *      Datagram
 *
 * 2.jdk1.7 NIO2 针对 open()  newByteChannel();
 *
 *
 *
 * 通道之前的数据传输
 * transferfrom()
 * transferto()
 *
 *
 *
 * 分散（scatter ）和聚集（gather）
 * 分散 :将通道中的数据分散到 缓冲区
 * 聚集：讲多个缓冲区的数据 聚集到 通道中
 *
 *
 *
 * 字符集
 * 编码：charset.newEncoder
 * 解码 charset.newDecoder
 *
 * @author wang zifeng
 * @Date Create on 2019-08-19 13:36
 * @since version1.0 Copyright 2018 Burcent All Rights Reserved.
 */
public class ChannelTest {
    @Test
    public  void test05() throws CharacterCodingException {
        Charset charset = Charset.forName("UTF-8");
        //编码器
        CharsetEncoder charsetEncoder = charset.newEncoder();
        //解码器
        CharsetDecoder charsetDecoder = charset.newDecoder();


        CharBuffer charBuffer=CharBuffer.allocate(1024);
        charBuffer.put("1234567");
        charBuffer.flip();
        ByteBuffer decode = charsetEncoder.encode(charBuffer);
        System.out.println(decode.array());
        CharBuffer decode1 = charsetDecoder.decode(decode);
        System.out.println(decode1.array());
    }

    @Test
    public  void test04(){
        SortedMap<String, Charset> stringCharsetSortedMap = Charset.availableCharsets();
        Set<String> strings = stringCharsetSortedMap.keySet();
        for (String string : strings) {
            System.out.println(string);
        }
    }


    @Test
    public void  test03() throws IOException {
        RandomAccessFile randomAccessFile=new RandomAccessFile("C:\\Users\\admin\\Desktop\\png\\12.jpg","rw");
        ByteBuffer byteBufferOne=ByteBuffer.allocate(1024);
        ByteBuffer byteBufferTwo=ByteBuffer.allocate(1024);

        FileChannel channel = randomAccessFile.getChannel();

        ByteBuffer[] bytes=new ByteBuffer[]{byteBufferOne,byteBufferTwo};
        channel.read(bytes);
        for (ByteBuffer aByte : bytes) {
            aByte.flip();
        }

//        System.out.println(new String(byteBufferOne.array(),0,byteBufferOne.limit()));
//        System.out.println("--------------------------------");
//        System.out.println(new String(byteBufferTwo.array(),0,byteBufferTwo.limit()));

//        for (ByteBuffer aByte : bytes) {
//            aByte.flip();
//        }

        RandomAccessFile rw=new RandomAccessFile("C:\\Users\\admin\\Desktop\\png\\45.jpg","rw");
        FileChannel channel1 = rw.getChannel();
        channel1.write(bytes);
    }




    @Test
    public  void  test02(){
        FileChannel channelIn= null;
        FileChannel channelOut = null;
        FileInputStream fis= null;
        FileOutputStream fos= null;
        try {
            fis = new FileInputStream("C:\\Users\\admin\\Desktop\\png\\newstart.png");
            fos = new FileOutputStream("C:\\Users\\admin\\Desktop\\png\\11.png");
            //获取 channerl
            channelIn = fis.getChannel();
            channelOut = fos.getChannel();
            channelIn.transferTo(0,channelIn.size(),channelOut);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(channelOut!=null) {
                    channelOut.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if(channelIn!=null) {
                    channelIn.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if(fos!=null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if(fis!=null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    @Test
    public void test01()  {
        //利用通通道 完成  file  复制
        FileChannel channelIn= null;
        FileChannel channelOut = null;
        FileInputStream fis= null;
        FileOutputStream fos= null;
        try {
            fis= new FileInputStream("C:\\Users\\admin\\Desktop\\png\\newstart.png");
            fos= new FileOutputStream("C:\\Users\\admin\\Desktop\\png\\11.png");
            //获取 channerl
            channelIn= fis.getChannel();
            channelOut = fos.getChannel();
            //读取
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while (channelIn.read(buffer) != -1) {
                buffer.flip();
                channelOut.write(buffer);
                buffer.clear();
            }
        }catch (Exception e){

        }finally {
            try {
                if(channelOut!=null) {
                    channelOut.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if(channelIn!=null) {
                    channelIn.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if(fos!=null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if(fis!=null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }



        }

    }
}
