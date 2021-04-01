package com.lsg.netty.nio.channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * 客户端
 */
public class NIOClient {
    public static void main(String[] args) throws IOException {

        // 得到一个网络通道
        SocketChannel socketChannel = SocketChannel.open();

        // 设置为非阻塞模式
        socketChannel.configureBlocking(false);

        // 设置服务器端的IP 和port
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 6666);

        // 连接服务器
        if (!socketChannel.connect(inetSocketAddress)) {// 非阻塞
            while (!socketChannel.finishConnect()) {
                System.out.println("连接需要时间，这里不会阻塞，客户端可以做其他事件");
            }
        }

        // 如果连接成功，就发送字符串
        String string = "abc";
        // 包装成一个Buffer
        ByteBuffer byteBuffer = ByteBuffer.wrap(string.getBytes());

        // 发送数据
        socketChannel.write(byteBuffer);
        System.in.read();

    }
}
