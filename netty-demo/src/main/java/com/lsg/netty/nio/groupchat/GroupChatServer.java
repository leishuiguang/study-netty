package com.lsg.netty.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class GroupChatServer {
    public static void main(String[] args) {
        GroupChatServer groupChatServer = new GroupChatServer();
        groupChatServer.listen();
    }

    // 定义相关的属性
    private Selector selector;
    private ServerSocketChannel listenChannel;
    private static final int PORT = 6667;

    // 初始化
    public GroupChatServer() {
        try {
            // 得到选择器
            selector = Selector.open();
            // 得到listenChannel
            listenChannel = ServerSocketChannel.open();
            // 绑定端口
            listenChannel.bind(new InetSocketAddress(PORT));
            // 设置非阻塞模式
            listenChannel.configureBlocking(false);
            // 将listenChannel注册到Selector上
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listen() {
        try {

            // 循环处理
            while (true) {
                System.out.println("等待事件处理。。。。");
                int count = selector.select();
                System.out.println("等待事件处理2。。。。");
                // 有事件处理
                if (count > 0) {
                    Set<SelectionKey> selectionKeySet = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectionKeySet.iterator();
                    while (iterator.hasNext()) {
                        // 取出SelectionKey
                        SelectionKey selectionKey = iterator.next();
                        // 监听到accept
                        if (selectionKey.isAcceptable()) {
                            SocketChannel channel = listenChannel.accept();
                            // 设置非阻塞模式
                            channel.configureBlocking(false);
                            // 将channel 注册到Selector
                            channel.register(selector, SelectionKey.OP_READ);
                            // 提示上线
                            System.out.println(channel.getRemoteAddress() + "上线了");
                        }
                        // 监听read（通道发送read事件，即通道是可读状态）
                        if (selectionKey.isReadable()) {
                            System.out.println("有读取事件。。。。");
                            // 处理read业务
                            readData(selectionKey);
                        }

                        // 当前的selectionKey 需要删除。防止重复处理
                        iterator.remove();
                    }

                } else {
                    System.out.println("等待。。。。");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    private void readData(SelectionKey key) {
        // 定义一个socketChannel
        SocketChannel socketChannel = null;

        try {
            // 得到channel
            socketChannel = (SocketChannel) key.channel();
            // 创建buffer
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

            int count = socketChannel.read(byteBuffer);
            if (count > 0) {
                // 读取到数据了
                String msg = new String(byteBuffer.array());
                System.out.println("从客户端读取到的数据为=" + msg);

                // 向其他客户端转发消息 todo
                sendInfoToOtherClient(msg, socketChannel);
            }
        } catch (IOException e) {
            try {
                System.out.println(socketChannel.getRemoteAddress() + "离线了");
                // 取消注册
                key.cancel();
                // 关闭通道
                socketChannel.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    /**
     * 向其他客户端发送消息
     */
    private void sendInfoToOtherClient(String msg, SocketChannel self) {
        // 服务器转发消息了
        System.out.println("服务器转发消息中。。。。");

        // 遍历 所有注册到selector 上的socketChannel ，并排除self
        for (SelectionKey key : selector.keys()) {
            // 通过key 获取对应的socketChannel
            Channel targetChannel = key.channel();
            // 排除自己
            if (targetChannel instanceof SocketChannel && targetChannel != self) {
                // 转型
                SocketChannel dest = (SocketChannel) targetChannel;
                // 将信息存储到buffer
                ByteBuffer byteBuffer = ByteBuffer.wrap(msg.getBytes());
                // 将buffer 数据写入到通道中
                try {
                    dest.write(byteBuffer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }



}
