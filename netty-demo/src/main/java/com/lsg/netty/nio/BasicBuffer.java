package com.lsg.netty.nio;

import java.nio.IntBuffer;

/**
 * 缓冲区 的简单使用案例
 */
public class BasicBuffer {
    public static void main(String[] args) {
        // 举例说明buffer 的使用
        // 创建一个Buffer ，大小为5， 即可存放5个int
        IntBuffer intBuffer = IntBuffer.allocate(5);

        // 向buffer存放数据
        for (int i = 0; i < intBuffer.capacity(); i++) {
            intBuffer.put(i * 2);
        }

        // 从buffer中取出数据
        intBuffer.flip();// 切换状态，读取数据
        while (intBuffer.hasRemaining()) {
            System.out.println(intBuffer.get());
        }

    }
}
