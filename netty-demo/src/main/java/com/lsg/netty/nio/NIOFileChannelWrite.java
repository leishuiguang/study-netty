package com.lsg.netty.nio;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 通过FileChannel 将字符串写入文件中去
 */
public class NIOFileChannelWrite {

    public static void main(String[] args) throws IOException {


        // 创建一个输出流 获取fileChannel
        FileOutputStream fileOutputStream = new FileOutputStream("D:\\temp\\aa.txt");
        FileChannel fileChannel = fileOutputStream.getChannel();

        // 将string 放入到缓冲区
        String string = "深圳市";
        int length = string.getBytes().length;

        // 创建一个缓冲区，存放字符串
        ByteBuffer byteBuffer = ByteBuffer.allocate(length);
        byteBuffer.put(string.getBytes());

        // 对byteBuffer 进行flip
        byteBuffer.flip();// 切换状态、读取byteBuffer

        // fileChannel 从byteBuffer中读取数据
        fileChannel.write(byteBuffer);

        // 关闭文件输出流
        fileOutputStream.close();
    }


}
