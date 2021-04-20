package com.lsg.netty.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class GroupChatClient {
    // 定义相关属性
    private final String HOST = "127.0.0.1";// 服务端IP地址
    private final int PORT = 6667;// 服务端端口
    private Selector selector;
    private SocketChannel socketChannel;
    private String username;

    // 完成初始化工作
    public GroupChatClient() {
        try {
            // 获取selector
            selector = Selector.open();
            // 获取socketChannel
            socketChannel = SocketChannel.open(new InetSocketAddress(HOST, PORT));
            // 设置非阻塞模式
            socketChannel.configureBlocking(false);
            // 将socketChannel注册到selector（关注读取事件）
            socketChannel.register(selector, SelectionKey.OP_READ);
            // 得到username
            username = socketChannel.getLocalAddress().toString();
            System.out.println(username + "  is ready....");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // 向服务器发送消息
    public void sendInfo(String info) {
        info = username + "说：" + info;
        try {
            ByteBuffer byteBuffer = ByteBuffer.wrap(info.getBytes());
            socketChannel.write(byteBuffer);
        } catch (Exception e) {

        }

    }

    // 读取从服务器发送过来的消息
    public void readInfo() {

        try {
            int read = selector.select();
            if (read > 0) {// 有可以用的通道
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    if (selectionKey.isReadable()) {
                        // 得到相关通道
                        SocketChannel channel = (SocketChannel) selectionKey.channel();
                        // 得到一个bytebuffer
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        // 读取
                        channel.read(byteBuffer);
                        String msg = new String(byteBuffer.array());
                        System.out.println(msg.trim());
                    }
                    iterator.remove();
                }
            } else {
                //System.out.println("没有可以用的通道。。。");
            }
        } catch (IOException e) {

        }
    }


    public static void main(String[] args) {
        GroupChatClient groupChatClient = new GroupChatClient();
        // 启动一个线程
        new Thread(() -> {
            while (true) {
                groupChatClient.readInfo();
                try {
                    TimeUnit.MILLISECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        // 发送数据给服务器端
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String info = scanner.nextLine();
            groupChatClient.sendInfo(info);
        }
    }

}
