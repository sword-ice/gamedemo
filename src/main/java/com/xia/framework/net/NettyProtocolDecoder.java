package com.xia.framework.net;


import com.xia.framework.message.Message;
import com.xia.framework.serializer.SerializerHelper;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class NettyProtocolDecoder extends ByteToMessageDecoder {

	private boolean remained = false;

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		if (!remained) {
			if (in.readableBytes() < 2) {
				return;
			}
			remained = true;
		}
		// IMessageDecoder msgDecoder = SerializerHelper.getInstance().getDecoder();
		// ----------------消息协议格式-------------------------
		// packetLength | moduleId | cmd | 四字节保留 |body
		// short short short int byte[]

		int length = in.readUnsignedShortLE();

		if (in.readableBytes() >= length - 2) {
			// 消息元信息常量3表示消息body前面的两个字段，一个short表示module，一个byte表示cmd,
			final int metaSize = 2 + 2 + 2 + 4;
			short moduleId = in.readShortLE();
			short cmd = in.readShortLE();
			in.readInt(); // 读取4个字节的保留

			byte[] body = new byte[length - metaSize];
			in.readBytes(body);

			Message msg = SerializerHelper.protobufDecode(moduleId, cmd, body);
			if (msg != null && moduleId > 0) {
				out.add(msg);
			} else { // 属于组合包
				/*CombineMessage combineMessage = (CombineMessage) msg;
				List<Packet> packets = combineMessage.getPackets();
				for (Packet packet : packets) {
					// 依次拆包反序列化为具体的Message
					out.add(Packet.asMessage(packet));
				}*/
			}
		}
	}

}
