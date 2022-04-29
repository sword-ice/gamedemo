package com.xia.framework.task;

import com.xia.framework.message.Message;
import com.xia.framework.net.IdSession;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Data
public class TaskMsg extends AbstractTask{

    private static Logger logger = LoggerFactory.getLogger(TaskMsg.class);

    private long uid;

    private Object handler;

    private Method method;

    private Object[] params;

    private IdSession session;

    public static TaskMsg valueOf(IdSession session,int distributeKey, Object handler,
                                      Method method, Object[] params) {
        TaskMsg msgTask = new TaskMsg();
        msgTask.distributeKey = distributeKey;
        msgTask.handler = handler;
        msgTask.method  = method;
        msgTask.params  = params;
        msgTask.session = session;
        return msgTask;
    }

    @Override
    public void action() {
        try {
            Object response = method.invoke(handler,params);
            if (response != null) {
                session.sendPacket((Message) response);
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
