package com.lsg.netty.nio;

import java.nio.ByteBuffer;

public class ByteBufferTest {
    public static void main(String[] args) {

        ByteBuffer byteBuffer = ByteBuffer.allocate(64);
        for (int i = 0; i < 64; i++) {
            byteBuffer.put((byte) i);
        }
        byteBuffer.flip();

        ByteBuffer readOnlyBuffer = byteBuffer.asReadOnlyBuffer();
        for (int i = 0; i < 64; i++) {
            System.out.println(readOnlyBuffer.get());
        }


        //putGet();

    }

    private static void putGet() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(32);

        byteBuffer.putChar('号');
        byteBuffer.putInt(1);
        byteBuffer.putInt(5);
        byteBuffer.putFloat(1f);

        byteBuffer.flip();
        /**
         * 按顺序读取
         */
        System.out.println(byteBuffer.getChar());
        System.out.println(byteBuffer.getInt());
        System.out.println(byteBuffer.getInt());
        System.out.println(byteBuffer.getFloat());
    }
}
