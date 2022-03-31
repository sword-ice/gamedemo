package com.xia.game.net;

import com.xia.game.message.Message;

public interface IdSession {


    void sendPacket(Message packet);

    long getOwnerId();

    /**
     * 更新属性值
     *
     * @param key
     * @param value
     * @return
     */
    Object setAttribute(String key, Object value);

    /**
     * 获取属性值
     *
     * @param key
     * @return
     */
    Object getAttribute(String key);
}
