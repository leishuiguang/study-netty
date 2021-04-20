package com.lsg.netty.nety;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * 说明：
 * 1. 我们定义一个handler 需要继承netty规定好的某个handlerAdapter(规范)
 * 2. 这时我们自定义一个handler ，才能成为一个handler
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 读取数据事件（这里我们可以读取客户端发送的消息）
     *
     * @param ctx 上下文对象，含有管道pipeline（业务处理）,通道（数据传输）
     * @param msg 客户端发送的数据 默认是object
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("线程1="+Thread.currentThread().getName());
        ctx.channel().eventLoop().execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("线程2="+Thread.currentThread().getName());
            }
        });

        ctx.channel().eventLoop().schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("线程2="+Thread.currentThread().getName());
            }
        }, 1, TimeUnit.SECONDS);


//        System.out.println("服务器当前线程=" + Thread.currentThread().getName());
//        System.out.println("ctx = " + ctx);
//        // 将msg 转成byteBuf（netty 提供的）
//        ByteBuf byteBuf = (ByteBuf) msg;
//        System.out.println("客户端发送的消息byteBuf = " + byteBuf.toString(CharsetUtil.UTF_8));
//        System.out.println("客户端的地址：" + ctx.channel().remoteAddress());
//        super.channelRead(ctx, msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        /**
         * 将数据写入到缓冲区
         */
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello，客户端~", CharsetUtil.UTF_8));
//        super.channelReadComplete(ctx);
    }

    /**
     * 处理异常
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
//        super.exceptionCaught(ctx, cause);
    }
}
