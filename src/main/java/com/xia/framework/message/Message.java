package com.xia.framework.message;

import com.xia.framework.message.annotation.MessageMeta;

public abstract class Message {

    public short getModule() {
        MessageMeta annotation = getClass().getAnnotation(MessageMeta.class);
        if (annotation != null) {
            return annotation.module();
        }
        return 0;
    }

    public short getCmd() {
        MessageMeta annotation = getClass().getAnnotation(MessageMeta.class);
        if (annotation != null) {
            return annotation.cmd();
        }
        return 0;
    }

    public String getKey() {
        return this.getModule() + "_" + this.getCmd();
    }

}
