package com.xia.framework.message;

import com.xia.framework.message.annotation.MessageMeta;
import com.xia.utils.ClassScanner;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MessageFactory {

    private static MessageFactory instance = new MessageFactory();

    private Map<Integer, Class<? extends Message>> id2Clazz = new HashMap<>();

    private Map<Class<?>, Integer> clazz2Id = new HashMap<>();

    public static MessageFactory getInstance() {
        return instance;
    }

    public void initMessagePool(String scanPath) {
        Set<Class<?>> messages = ClassScanner.listAllSubclasses(scanPath, Message.class);
        for (Class<?> clazz: messages) {
            MessageMeta meta = clazz.getAnnotation(MessageMeta.class);
            if (meta == null) {
                throw new RuntimeException("messages["+clazz.getSimpleName()+"] missed MessageMeta annotation");
            }
            int key = buildKey(meta.module() , meta.cmd());
            if (id2Clazz.containsKey(key)) {
                throw new RuntimeException("message meta ["+key+"] duplicate！！");
            }
            id2Clazz.put(key, (Class<? extends Message>) clazz);
            clazz2Id.put(clazz, key);
        }
    }

    public Class<?> getMessage(short module, short cmd) {
        return id2Clazz.get(buildKey(module, cmd));
    }

    public Class<?> getMessage(int id) {
        short module = (short)(id / 1000);
        short cmd = (short)(id % 1000);
        return id2Clazz.get(buildKey(module, cmd));
    }

    private int buildKey(short module,short cmd) {

        return module * 1000 + +cmd;
    }

}
