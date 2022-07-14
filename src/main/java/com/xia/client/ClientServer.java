package com.xia.client;

import com.xia.framework.message.MessageFactory;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.internal.logging.InternalLoggerFactory;
import io.netty.util.internal.logging.Slf4JLoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;


public class ClientServer {
    public static void main(String[] args) throws Exception{

        InternalLoggerFactory.setDefaultFactory(Slf4JLoggerFactory.INSTANCE);
        String host = "192.168.0.52";
        MessageFactory.getInstance().initMessagePool("com.xia.client");
        connect(host,8898);
    }


    public static void connect(String host, int port) throws InterruptedException {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try{
            Bootstrap bootstrap = new Bootstrap();
            bootstrap
                    .group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_RCVBUF, 64 * 1024)
                    .option(ChannelOption.SO_SNDBUF, 64 * 1024)
                    .option(ChannelOption.SO_REUSEADDR, true)
                    .option(ChannelOption.SO_KEEPALIVE,true)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .handler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new ClientProtocolDecoder());//解码
                            ch.pipeline().addLast(new ClientProtocolEncoder());
                            ch.pipeline().addLast(new ClientHandler());
                        }
                    });
            ChannelFuture future = bootstrap.connect(new InetSocketAddress(host,port)).sync();
            future.channel().closeFuture().sync();
        }finally {
            eventLoopGroup.shutdownGracefully();
            try {
                System.in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

