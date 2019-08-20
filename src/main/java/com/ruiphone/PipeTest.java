package com.ruiphone;

import org.junit.Test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

/**
 * Description:
 *
 * @author wang zifeng
 * @Date Create on 2019-08-20 10:48
 * @since version1.0 Copyright 2018 Burcent All Rights Reserved.
 */
public class PipeTest {


    @Test
    public void send() throws IOException {
        //获取管道
        Pipe pipe = Pipe.open();
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        Pipe.SinkChannel sinkChannel = pipe.sink();

        buffer.put("通过管道单向传输".getBytes());
        buffer.flip();
        sinkChannel.write(buffer);


        //去读缓冲区数据
        Pipe.SourceChannel sourceChannel = pipe.source();
        buffer.flip();
        int read = sourceChannel.read(buffer);
        System.out.println(new String(buffer.array(),0,read));

    }
}
