package com.xia.demo.game.serializer;


import com.baidu.bjf.remoting.protobuf.Codec;
import com.baidu.bjf.remoting.protobuf.ProtobufProxy;
import com.xia.demo.game.message.Message;
import com.xia.demo.game.message.MessageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


public class SerializerHelper {
	private static Logger logger = LoggerFactory.getLogger(SerializerHelper.class);

	public static volatile SerializerHelper instance = null;

	public static SerializerHelper getInstance() {
		if (instance != null) {
			return instance;
		}
		synchronized (SerializerHelper.class) {
			if (instance == null) {
				SerializerHelper self =  new SerializerHelper();
				instance = self;
			}
		}
		return instance;
	}

	static public byte[] protobufEncode(Message message) {

		byte[] body = null;
		Class<Message> msgClazz = (Class<Message>) message.getClass();

		try {
			Codec<Message> codec = ProtobufProxy.create(msgClazz);
			body = codec.encode(message);
		} catch (IOException e) {
			logger.error("", e);
		}
		return body;
	}

	static public Message protobufDecode(short module, short cmd, byte[] body) {
		Class<?> msgClazz = MessageFactory.getInstance().getMessage(module, cmd);
		if (msgClazz == null) {
			return null;
		}

		try {
			Codec<?> codec = ProtobufProxy.create(msgClazz);
			Message message = (Message) codec.decode(body);
			return message;
		} catch (IOException e) {
			logger.error("读取消息出错,模块号{}，类型{},异常{}", new Object[] { module, cmd, e });
		}
		return null;
	}
}

