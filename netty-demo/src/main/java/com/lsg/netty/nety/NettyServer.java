package com.lsg.netty.nety;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {
    public static void main(String[] args) throws InterruptedException {
        // 创建bossGroup、 workGroup
        /**
         * boosGroup只处理连接请求、
         * workGroup 真正处理客户端业务请求
         * 默认创建的事件循环数=cpu核数*2
         */
        EventLoopGroup boosGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            // 创建服务端启动对象，配置参数
            ServerBootstrap bootstrap = new ServerBootstrap();
            // 使用链式编程设置
            bootstrap.group(boosGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)// 使用NioSctpServerChannel 作为服务端通道实现
                    .option(ChannelOption.SO_BACKLOG, 128)// 设置线程队列得到连接个数
                    .childOption(ChannelOption.SO_KEEPALIVE, true) // 设置保持活动连接状态
                    .childHandler(new ChannelInitializer<SocketChannel>() {// 创建一个通道测试对象（匿名对象）

                        // 给pipeline 设置处理器
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {

                            System.out.println("客户端的socketChannel hashCode="+socketChannel.hashCode());
                            //可以使用一个集合管理SocketChannel ,在推送消息时，可以将业务加入到各个channel对应的NIoEventLoop 的taskQueue 或者scheduleTaskQueue
                            socketChannel.pipeline().addLast(new NettyServerHandler());
                        }
                    }); // 给workerGroup 的EventLoop 对应的管道设置处理器
            System.out.println("服务器 is ready.....");

            // 绑定一个端口并且同步，生成一个ChannelFuture对象（启动服务器）
            ChannelFuture channelFuture = bootstrap.bind(6668).sync();

            // 对关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        } finally {
            boosGroup.shutdownGracefully();
        }


    }
}
