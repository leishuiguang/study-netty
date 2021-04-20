package com.lsg.netty.nety;

import io.netty.util.NettyRuntime;

public class Tests {
    public static void main(String[] args) {
        System.out.println(NettyRuntime.availableProcessors() * 2);
    }
}
