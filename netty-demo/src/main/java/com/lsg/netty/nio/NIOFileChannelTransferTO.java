package com.lsg.netty.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * 测试文件通道的transferTo 方法去copy文件
 */
public class NIOFileChannelTransferTO {

    public static void main(String[] args) throws IOException {

        FileInputStream fileInputStream = new FileInputStream("D:\\temp\\123.jpg");
        FileChannel inputStreamChannel = fileInputStream.getChannel();
        FileOutputStream fileOutputStream = new FileOutputStream("D:\\temp\\456.jpg");
        FileChannel outputStreamChannel = fileOutputStream.getChannel();

        /*
        下面两种方式都可以
         */
        inputStreamChannel.transferTo(0, inputStreamChannel.size(), outputStreamChannel);
        //outputStreamChannel.transferFrom(inputStreamChannel, 0, inputStreamChannel.size());

        // 关闭通道、关闭流
        inputStreamChannel.close();
        outputStreamChannel.close();
        fileInputStream.close();
        fileOutputStream.close();


    }
}
