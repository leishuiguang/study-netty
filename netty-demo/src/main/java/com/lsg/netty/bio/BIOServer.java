package com.lsg.netty.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BIOServer {
    public static void main(String[] args) throws IOException {


        // 线程池机制

        // 思路：
        // 1. 创建一个线程池
        // 2. 如果有客户端连接，就创建一个线程与之通讯（单独写一个方法）
        ExecutorService threadPool = Executors.newCachedThreadPool();

        // 创建ServerSocket
        ServerSocket serverSocket = new ServerSocket(6666);
        System.out.println("服务器启动了");
        while (true) {
            System.out.println("等待连接。。。。。" + "当前线程=" + Thread.currentThread().getName());

            // 监听，等待客户端连接
            final Socket socket = serverSocket.accept();// 有客户端连接就处理，没有客户端连接就阻塞
            System.out.println("连接到一个客户端=" + socket.getPort() + "   当前线程=" + Thread.currentThread().getName());

            threadPool.execute(new Runnable() {
                public void run() {
                    // 可以与客户端通讯
                    handler(socket);
                }
            });
        }
    }

    /**
     * 编写一个handler方法，和客户端通讯
     *
     * @param socket
     */
    public static void handler(Socket socket) {


        try {
            byte[] bytes = new byte[1024];
            InputStream inputStream = socket.getInputStream();
            while (true) {
                System.out.println("等待读取。。。。" + Thread.currentThread().getName());
                int read = inputStream.read(bytes);// 有数据就进行读取处理、没有就把当前线程阻塞
                System.out.println("读取完成。。。。" + Thread.currentThread().getName());
                if (read != -1) {
                    System.out.println(new String(bytes, 0, read, "GBK"));
                } else {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                System.out.println("线程" + Thread.currentThread().getName() + "关闭连接");
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
