package com.lsg.netty.nety.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class NettyByteBuf1 {
    public static void main(String [] args){
        //
        //
        /**
         * 创建一个ByteBuf
         * 在netty Buf中不需要使用fip进行反转
         * 底层维护了readerindex 和writerindex 和capacity，将buffer分成三个区域
         * 0----> 已读取的区域
         * reader ----> writerIndex 可读取的区域
         * writerIndex ----> capacity 可写的区域
         *
         */
        ByteBuf buffer = Unpooled.buffer(10);
        for (int i = 0; i < 10; i++) {
            buffer.writeByte(i);
        }
        int capacity = buffer.capacity();
        System.out.println("capacity="+capacity);
        for (int i = 0; i < buffer.capacity(); i++) {
            System.out.println(buffer.readByte());
        }
    }
}
