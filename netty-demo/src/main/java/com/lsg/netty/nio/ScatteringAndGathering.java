package com.lsg.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * scattering: 将数据写入到buffer时，可以采用buffer数组，依次写入【分散写入】
 * gathering: 从buffer中读取数据时， 可以采用buffer数组，依次读取
 */
public class ScatteringAndGathering {
    public static void main(String[] args) throws IOException {

        // 使用ServerSocket 和socketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 设置一个地址端口
        InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);

        // 绑定地址,并启动服务器
        serverSocketChannel.socket().bind(inetSocketAddress);

        // 创建buffer数组
        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(3);

        // 等待客户端连接
        SocketChannel socketChannel = serverSocketChannel.accept();

        // 假设从客户端接收8个节字
        int msgSize = 8;
        // 循环读取
        while (true) {
            int byteRead = 0;
            while (byteRead < msgSize) {
                long len = socketChannel.read(byteBuffers);
                byteRead += len;// 累计读取字节数

                // 使用流打印
                Arrays.stream(byteBuffers).map(item -> "position" + item.position()
                        + "limit=" + item.limit()).forEach(System.out::println);
            }

            // 将所有的buffer flip
            Arrays.asList(byteBuffers).forEach(ByteBuffer::flip);

            // 将数据读出显示到客户端
            long byteWrite = 0;
            while (byteWrite < msgSize) {
                long len = socketChannel.write(byteBuffers);
                byteWrite += len;
            }

            // 将所有buffer 进行clear
            Arrays.asList(byteBuffers).forEach(ByteBuffer::clear);

            System.out.println("byteRead = " + byteRead);
            System.out.println("byteWrite = " + byteWrite);
            System.out.println("msgSize = " + msgSize);
        }


    }
}
