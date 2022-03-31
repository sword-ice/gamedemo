package com.xia.game.net;


import com.xia.game.message.Message;
import com.xia.game.serializer.SerializerHelper;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NettyProtocolEncoder extends MessageToByteEncoder<Message> {

	private Logger logger = LoggerFactory.getLogger(NettyProtocolEncoder.class);

	@Override
	protected void encode(ChannelHandlerContext ctx, Message message, ByteBuf out) throws Exception {
		// ----------------消息协议格式-------------------------
		// packetLength | moduleId | cmd | 4字节保留 | body
		// short           short     short |  4字节保留 |  byte[]
		// 其中 packetLength长度占2位，由编码链 LengthFieldPrepender(2) 提供

		short module = message.getModule();
		short cmd = message.getCmd();

		try {
			// 消息元信息常量3表示消息body前面的两个字段，一个short表示module，一个byte表示cmd,
			final int metaSize = 2 + 2 + 2 + 4;

			byte[] body = SerializerHelper.protobufEncode(message);
			//消息内容长度
			out.writeShortLE(body.length + metaSize);
			// 写入module类型
			out.writeShortLE(module);
			// 写入cmd类型
			out.writeShortLE(cmd);
			out.writeIntLE(0); // 4字节保留
			out.writeBytes(body);
		} catch (Exception e) {
			logger.error("读取消息出错,模块号{}，类型{},异常{}", module, cmd, e);
		}

	}

}
