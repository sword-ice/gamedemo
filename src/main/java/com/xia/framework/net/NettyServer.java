package com.xia.framework.net;

import com.xia.framework.ServerConfig;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.InetSocketAddress;



@Component
public class NettyServer {

    private int port;

    private EventLoopGroup bossGroup = new NioEventLoopGroup(4);
    private EventLoopGroup workerGroup = new NioEventLoopGroup();


    private Logger logger = LoggerFactory.getLogger(GameNetServer.class);


    @Autowired
    private ServerConfig serverConfig;

    @PostConstruct
    public void init() {
        this.port = serverConfig.getServerPort();
    }

    public void start(){
        logger.info("netty socket服务已启动，正在监听用户的请求@port:" + port + "......");

        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup,workerGroup)
                .channel(NioServerSocketChannel.class)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_BACKLOG,1024)
                .childHandler(new ChildChannelHandler());
        try {
            ChannelFuture f = b.bind(new InetSocketAddress(port)).sync();
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            logger.error("",e);
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }


    private static class ChildChannelHandler extends ChannelInitializer<SocketChannel> {
        @Override
        protected void initChannel(SocketChannel arg0) throws Exception {
            ChannelPipeline pipeline = arg0.pipeline();
            pipeline.addLast(new NettyProtocolDecoder());
            pipeline.addLast(new NettyProtocolEncoder());
            // 客户端300秒没收发包，便会触发UserEventTriggered事件到IdleEventHandler
            pipeline.addLast(new IdleStateHandler(0, 0, 300));
            pipeline.addLast(new IoEventHandler());
        }
    }

}
