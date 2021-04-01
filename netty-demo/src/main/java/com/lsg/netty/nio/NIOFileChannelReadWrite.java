package com.lsg.netty.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 使用缓冲区作为中转站。将一个文件的内容（1.txt）读取到另外一个文件（2.txt）
 */
public class NIOFileChannelReadWrite {
    public static void main(String[] args) throws IOException {


        onceReadWrite();
//        whileReadWrite();

    }

    /**
     * 一次性读写
     *
     * @throws IOException
     */
    private static void onceReadWrite() throws IOException {
        // 创建文件出入流 去读取文件。
        File file = new File("D:\\temp\\1.txt");
        FileInputStream fileInputStream = new FileInputStream(file);
        // 通过文件输入流获取fileChannel
        FileChannel fileChannel = fileInputStream.getChannel();

        // 创建一个字节缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());

        // 通道读取内容到 缓冲区
        fileChannel.read(byteBuffer);

        // 关闭输入流
        fileInputStream.close();

        // 切换缓冲区状态
        byteBuffer.flip();

        // 创建文件输出流
        FileOutputStream fileOutputStream = new FileOutputStream("D:\\temp\\2.txt");
        // 通过输出流获取 通道
        fileChannel = fileOutputStream.getChannel();

        // 将缓冲区的内容写入到通道中
        fileChannel.write(byteBuffer);

        // 关闭输出流
        fileOutputStream.close();
    }

    /**
     * 循环读写
     *
     * @throws IOException
     */
    private static void whileReadWrite() throws IOException {
        // 创建文件出入流 去读取文件。
        FileInputStream fileInputStream = new FileInputStream("D:\\temp\\1.txt");
        // 通过文件输入流获取fileChannel
        FileChannel fileChannel1 = fileInputStream.getChannel();
        // 创建文件输出流
        FileOutputStream fileOutputStream = new FileOutputStream("D:\\temp\\2.txt");
        // 通过输出流获取 通道
        FileChannel fileChannel2 = fileOutputStream.getChannel();

        // 创建一个字节缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(512);

        // 循环读取内容
        int read;
        while (true) {
            // 清空缓冲区内容
            byteBuffer.clear();
            read = fileChannel1.read(byteBuffer);
            if (read == -1) {
                break;
            }

            // 切换缓冲区状态
            byteBuffer.flip();
            fileChannel2.write(byteBuffer);
        }
        // 关闭输出流
        fileOutputStream.close();
        fileInputStream.close();
    }
}
