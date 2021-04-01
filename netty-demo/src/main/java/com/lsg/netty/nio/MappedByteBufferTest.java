package com.lsg.netty.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * MapperdByteBuffer 可让文件在内存钟直接修改（堆外内存），操作系统不需要拷贝一次
 */
public class MappedByteBufferTest {
    public static void main(String[] args) throws IOException {

        RandomAccessFile randomAccessFile = new RandomAccessFile("D:\\temp\\bb.txt", "rw");
        // 获取对应的通道
        FileChannel fileChannel = randomAccessFile.getChannel();

        /**
         * 参数1：FileChannel.MapMode.READ_WRITE 使用模式：读写模式
         * 参数2：可以直接修改起始位置
         * 参数3：映射到内存的大小，即将文件 的多少个字节映射到内存 （不包括该位置）
         *
         * MappedByteBuffer实际类型：DirectByteBuffer
         */
        MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 5);

        mappedByteBuffer.put(0, (byte) 'H');
        mappedByteBuffer.put(3, (byte) '9');
        System.out.println("修改成功");

    }
}
