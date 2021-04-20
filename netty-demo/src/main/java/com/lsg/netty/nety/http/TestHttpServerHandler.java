package com.lsg.netty.nety.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

/**
 * 1.SimpleChannelInboundHandler 是 ChannelInboundHandlerAdapter 子类
 * 2. HttpObject 表示客户端与服务端相互通讯的数据封装成HttpObject
 */
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    /**
     * 读取客户端数据
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {

        // 判断msg是否httpRequest
        if (msg instanceof HttpRequest) {


            HttpRequest httpRequest = (HttpRequest) msg;
            URI uri = new URI(httpRequest.uri());
            if ("/favicon.ico".equalsIgnoreCase(uri.getPath())){
                System.out.println(uri.getPath());
                return;
            }

            System.out.println(ctx.channel().pipeline().hashCode());

            System.out.println("msg 类型=" + msg.getClass());
            System.out.println("客户端地址=" + ctx.channel().remoteAddress());

            // 回复浏览器数据【http协议】
            ByteBuf byteBuf = Unpooled.copiedBuffer("hello，我是服务器", CharsetUtil.UTF_8);

            // 构造一个http的响应，即httpResponse
            DefaultFullHttpResponse httpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, byteBuf);

            httpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            httpResponse.headers().set(HttpHeaderNames.CONTENT_LENGTH, byteBuf.readableBytes());

            // 将构建好的httpResponse 返回
            ctx.writeAndFlush(httpResponse);

        }

    }
}
