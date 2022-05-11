package com.xia.framework.net;

import com.xia.framework.message.Message;
import io.netty.channel.Channel;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@Data
public class NettySession implements IdSession{

    private static final Logger logger = LoggerFactory.getLogger(NettySession.class);

    /** 网络连接channel */
    private Channel channel;

    private long uid;

    /** ip地址 */
    private String ipAddr;

    private IDispatch dispatcher;

    /** 拓展用，保存一些个人数据 */
    private Map<String, Object> attrs = new HashMap<>();

    public NettySession(Channel channel){
        this.channel = channel;

    }


    @Override
    public void sendPacket(Message packet) {
        channel.writeAndFlush(packet);
    }

    @Override
    public long getOwnerId() {
        return getUid();
    }

    @Override
    public Object setAttribute(String key, Object value) {
        return this.attrs.put(key,value);
    }

    @Override
    public Object getAttribute(String key) {
        return this.attrs.containsKey(key)?this.attrs.get(key):0;
    }

    public boolean isClose() {
        if (channel == null) {
            return true;
        }
        return !channel.isActive() || !channel.isOpen();
    }

    /**
     * 关闭session
     *
     * @param reason {@link SessionCloseReason}
     */
    public void close(SessionCloseReason reason) {
        try {
            if (this.channel == null) {
                return;
            }
            if (channel.isOpen()) {
                channel.close();
                logger.info("close session[{}], reason is {}", getOwnerId(), reason);
            } else {
                logger.info("session[{}] already close, reason is {}", getOwnerId(), reason);
            }
        } catch (Exception e) {
            logger.error("session close fail");
        }
    }
}
