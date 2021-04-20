package com.lsg.netty.nety.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

public class TestServerInitializer extends ChannelInitializer<SocketChannel> {


    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        // 向管道加入处理器
        ChannelPipeline pipeline = ch.pipeline();
        // 加入一个netty 提供的httpServerCoder codec => [coder - decoder]
        pipeline.addLast("MyHttpServerCodec", new HttpServerCodec());
        // 加入一个自定义的处理器
        pipeline.addLast("MyTestHttpServerHandler", new TestHttpServerHandler());
    }
}
