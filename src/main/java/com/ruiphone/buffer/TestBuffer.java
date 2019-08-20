package com.ruiphone.buffer;

import org.junit.Test;

import java.nio.ByteBuffer;

/**
 * Description:
 * 除boolean  都有对应的buffer
 * ByteBuffer
 * CharBuffer
 * ShortBuffer
 * InterBuffer
 * LongBuffer
 * FloatBuffer
 * DoubleBuffer
 *   通过allocate()获取缓冲数据
 *
 *   缓冲区的核心方法
 *   put get
 *
 * mark  标记当前position  的位置，可通过reset（）回到当前位置
 * @author wang zifeng
 * @Date Create on 2019-08-19 11:49
 * @since version1.0 Copyright 2018 Burcent All Rights Reserved.
 */
public class TestBuffer {

    @Test
    public void test01(){
        String mby="我是谁？";
        //分布一个指定缓冲区 的大小
        ByteBuffer byteBuffer=ByteBuffer.allocate(1024);
        System.out.println("-----new --------");
        System.out.println(byteBuffer.capacity());
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.limit());
        //写模式  put
        byteBuffer.put(mby.getBytes());
        System.out.println("-------put------");
        System.out.println(byteBuffer.capacity());
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.limit());
        //读模式
        byteBuffer.flip();
        System.out.println("------get-------");
        System.out.println(byteBuffer.capacity());
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.limit());

        byte[] bytes=new byte[byteBuffer.limit()];
        byteBuffer.get(bytes);
        System.out.println(new String(bytes,0,bytes.length));

        System.out.println("-------------");
        System.out.println(byteBuffer.capacity());
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.limit());

        //重复读
        byteBuffer.rewind();
        System.out.println("------rewind（）-------");
        System.out.println(byteBuffer.capacity());
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.limit());

        //清空缓冲区  缓冲区的数据还是存在 ，只是处于"被遗忘状态"
        byteBuffer.clear();
        System.out.println("------clear（）-------");
        System.out.println(byteBuffer.capacity());
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.limit());

    }
}
