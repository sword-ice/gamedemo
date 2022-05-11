package com.xia.framework.net;

import com.xia.framework.common.SpringContext;
import com.xia.framework.message.Message;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class IoEventHandler extends ChannelInboundHandlerAdapter {

	private final static Logger logger = LoggerFactory.getLogger(IoEventHandler.class);


	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		IdSession session = new NettySession(ctx.channel());
		if (!ChannelUtils.addChannelSession(ctx.channel(), session)) {
			ctx.channel().close();
			logger.error("Duplicate session,IP=[{}]", ChannelUtils.getIp(ctx.channel()));
		}

	}

	@Override
	public void channelRead(ChannelHandlerContext context, Object msg) throws Exception {
		Message packet = (Message) msg;
		logger.info("receive pact, content is {}", packet.getClass().getSimpleName());

		final Channel channel = context.channel();
		IdSession session = ChannelUtils.getSessionBy(channel);

		MessageDispatcher.getSingleton().dispatch(session, packet);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		SpringContext.getSessionManager().unRegisterChannel(ctx.channel());
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
