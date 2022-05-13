package com.xia.client;


import com.xia.framework.message.Message;

import com.xia.framework.message.MessageFactory;
import com.xia.framework.net.ChannelUtils;
import com.xia.framework.net.CmdExecutor;
import com.xia.framework.net.IdSession;
import com.xia.framework.net.NettySession;
import com.xia.framework.serializer.SerializerHelper;
import com.xia.game.login.message.ResLogin;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Scanner;


public class ClientHandler extends ChannelInboundHandlerAdapter {
    private final static Logger logger = LoggerFactory.getLogger(ClientHandler.class);


    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        if (!ChannelUtils.addChannelSession(ctx.channel(), new NettySession(ctx.channel()))) {
            ctx.channel().close();
            logger.error("Duplicate session,IP=[{}]", ChannelUtils.getIp(ctx.channel()));
        }
        ClientMessage.login(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext context, Object msg) throws Exception {
        Message packet = (Message) msg;
        logger.info("日志"+((Message) msg).getKey());
        if(packet instanceof ResLogin) {
            ResLogin message = (ResLogin) msg;
            System.out.println(System.currentTimeMillis()-message.getPlayerInfo().getTs());
        }
        System.out.println(msg.toString());
        logger.info("receive pact, content is {}", packet.getClass().getSimpleName());

        final Channel channel = context.channel();
        IdSession session = ChannelUtils.getSessionBy(channel);
        ClientMessage.login(context);


    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Channel channel = ctx.channel();
        if (channel.isActive() || channel.isOpen()) {
            ctx.close();
        }
        if (!(cause instanceof IOException)) {
            logger.error("remote:" + channel.remoteAddress(), cause);

        }
    }
}

