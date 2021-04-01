package com.lsg.netty.nio.channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

import static java.nio.channels.SelectionKey.OP_ACCEPT;
import static java.nio.channels.SelectionKey.OP_READ;

public class NIOServer {
    public static void main(String[] args) throws IOException {


        // 创建ServerSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        // 得到一个Selector
        Selector selector = Selector.open();

        // 绑定一个端口6666，在服务器端监听
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));
        // 设置为非阻塞
        serverSocketChannel.configureBlocking(false);

        // 把serverSocketChannel 注册到selector， 关注事件为OP_ACCEPT
        serverSocketChannel.register(selector, OP_ACCEPT);

        // 循环等待客户端连接
        while (true) {

            // 这里我们等待1秒钟，如果没有事件发生则返回
            if (selector.select(1000) == 0) {
                System.out.println("继续等待客户端连接...");
                continue;
            }

            // 如果返回结果>0， 就获取相关的selectionKeys 集合
            /**
             * 1. 如果返回的结果>0, 表示已经获取关注事件的集合
             * 2. selector.selectedKeys() 返回关注事件集合
             * 通过SelectionKey 反向获取通道
             */
            Set<SelectionKey> selectionKeySet = selector.selectedKeys();

            // 遍历selectionKeySet
            Iterator<SelectionKey> iterator = selectionKeySet.iterator();
            SelectionKey selectionKey;
            while (iterator.hasNext()) {

                // 获取SelectionKey
                selectionKey = iterator.next();
                // 根据key 获取对应事件做出相应的业务处理
                if (selectionKey.isAcceptable()) {// 如果有OP_ACCEPT事件


                    // 该客户端生成一个SocketChannel
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    // 设置为非阻塞模式
                    socketChannel.configureBlocking(false);
                    System.out.println("连接成功了一个客户端，连接端口=" + socketChannel.getRemoteAddress());
                    // 将当前的SocketChannel 注册到Selector上，关注事件为OP_READ，同时给SocketChannel关联一个Buffer
                    socketChannel.register(selector, OP_READ, ByteBuffer.allocate(1024));//

                }

                // 根据selectionKey 获取对应事件为 OP_READ
                if (selectionKey.isReadable()) {
                    // 通过selectionKey 得到对应的Channel
                    SocketChannel channel = (SocketChannel) selectionKey.channel();
                    // 通过channel 获取对应的Buffer
                    ByteBuffer byteBuffer = (ByteBuffer) selectionKey.attachment();
                    // 将channel 的数据读入到byteBuffer
                    channel.read(byteBuffer);
                    System.out.println("从客户端读取到的数据=" + new String(byteBuffer.array()));


                }

                // 手动删除当前事件的selectionKey， 防止重复操作
                iterator.remove();
            }

        }
    }
}
