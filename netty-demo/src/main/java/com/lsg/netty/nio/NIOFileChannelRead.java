package com.lsg.netty.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 通过FileChannel 从文件中读取字符串并在控制台中打印
 */
public class NIOFileChannelRead {

    public static void main(String[] args) throws IOException {


        // 创建一个输出流 获取fileChannel
        File file = new File("D:\\temp\\aa.txt");
        FileInputStream fileInputStream = new FileInputStream(file);
        FileChannel fileChannel = fileInputStream.getChannel();


        // 创建一个缓冲区，存放字符串
        ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());

        // 读取 fileChannel内容 到 byteBuffer
        fileChannel.read(byteBuffer);

        // 对byteBuffer 进行flip
        byteBuffer.flip();// 切换状态、读取byteBuffer

        // 打印结果
        byte[] bytes = byteBuffer.array();
        System.out.println(new String(bytes, "UTF-8"));

        // 关闭文件输出流
        fileInputStream.close();
    }


}
