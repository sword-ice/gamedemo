package com.xia.framework.net;

import com.xia.framework.common.SpringContext;
import com.xia.framework.message.Message;
import com.xia.framework.message.annotation.MessageMeta;
import com.xia.framework.serializer.annotation.Controller;
import com.xia.framework.serializer.annotation.RequestMapping;
import com.xia.framework.task.TaskMsg;
import com.xia.utils.ClassScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class MessageDispatcher {

    private final Logger logger = LoggerFactory.getLogger(MessageDispatcher.class);

    private volatile static MessageDispatcher instance;
    private MessageDispatcher (){
        init();
    }
    public static MessageDispatcher getSingleton() {
        if (instance == null) {
            synchronized (MessageDispatcher.class) {
                if (instance == null) {
                    instance = new MessageDispatcher();
                }
            }
        }
        return instance;
    }

    private static final Map<String, CmdExecutor> MSG_TASK_MAP = new HashMap<>();


    private void init(){
        logger.info("messageDispatcher is init ...... ");
        Set<Class<?>> controllers = ClassScanner.listClassesWithAnnotation("com.xia.game", Controller.class);
        for(Class<?> controller:controllers){
            try {
                Object handler = SpringContext.getBean(controller);
                Method[] methods = controller.getDeclaredMethods();
                for(Method method:methods){
                    if(method == null){
                        continue;
                    }
                    RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                    if(requestMapping == null){
                        continue;
                    }
                    //获取参数对象
                    Class<?>[] classes = method.getParameterTypes();
                    for(Class<?> clazz:classes){
                        //通过msg参数绑定方法
                        if(Message.class.isAssignableFrom(clazz)){
                            MessageMeta meta = clazz.getAnnotation(MessageMeta.class);
                            short module = meta.module();
                            short cmd = meta.cmd();
                            String key  = module +"_"+cmd;
                            CmdExecutor cmdExecutor = MSG_TASK_MAP.get(key);
                            if (cmdExecutor != null) {
                                throw new RuntimeException(String.format("module[%d] cmd[%d] duplicated", module, cmd));
                            }
                            cmdExecutor = CmdExecutor.valueOf(method, method.getParameterTypes(), handler);
                            MSG_TASK_MAP.put(key,cmdExecutor);
                        }
                    }

                }
            } catch (Exception e) {
                logger.error("",e);
                e.printStackTrace();
            }
        }
    }

    public void dispatch(IdSession session,Message message){
        short module = message.getModule();
        short cmd = message.getCmd();

        CmdExecutor cmdExecutor = MSG_TASK_MAP.get(module + "_" +cmd);
        if(cmdExecutor == null){
            logger.error("message executor mission,module = {},cmd={}",module,cmd);
            return;
        }
        Object[] params =convertToMethodParams(session,cmdExecutor.getParams(),message);

        int distributeKey = (int)session.getAttribute(SessionProperties.DISTRIBUTE_KEY);
        TaskMsg task = TaskMsg.valueOf(session,distributeKey,cmdExecutor.getHandler(),cmdExecutor.getMethod(),params);
        GameExecutor.getInstance().acceptTask(task);
    }

    /**
     * 将各种参数转为被RequestMapper注解的方法的实参
     */
    private Object[] convertToMethodParams(IdSession session, Class<?>[] methodParams, Message message) {
        Object[] result = new Object[methodParams == null ? 0 : methodParams.length];

        for (int i = 0; i < result.length; i++) {
            Class<?> param = methodParams[i];
            if (IdSession.class.isAssignableFrom(param)) {
                result[i] = session;
            } else if (Long.class.isAssignableFrom(param)) {
                result[i] = session;
            } else if (long.class.isAssignableFrom(param)) {
                result[i] = session.getOwnerId();
            } else if (Message.class.isAssignableFrom(param)) {
                result[i] = message;
            }
        }

        return result;
    }

}
