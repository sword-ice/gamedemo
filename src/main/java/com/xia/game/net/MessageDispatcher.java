package com.xia.game.net;

import com.xia.game.message.Message;
import com.xia.game.message.annotation.MessageMeta;
import com.xia.game.serializer.annotation.Controller;
import com.xia.game.serializer.annotation.RequestMapping;
import com.xia.game.task.TaskMsg;
import com.xia.utils.ClassScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class MessageDispatcher {

    private final Logger logger = LoggerFactory.getLogger(MessageDispatcher.class);

    private static final Map<String, CmdExecutor> MSG_TASK_MAP = new HashMap<>();

    public MessageDispatcher(){
        init();
    }

    private void init(){
        logger.info("messageDispatcher is init ...... ");
        Set<Class<?>> controllers = ClassScanner.listClassesWithAnnotation("com.xia.game", Controller.class);
        for(Class<?> controller:controllers){
            try {
                Object handler = controller.newInstance();
                Method[] methods = controller.getDeclaredMethods();
                for(Method method:methods){
                    if(method == null){
                        continue;
                    }
                    RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                    if(requestMapping == null){
                        continue;
                    }
                    Class<?>[] classes = method.getParameterTypes();
                    for(Class<?> clazz:classes){
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
            } catch (InstantiationException | IllegalAccessException e) {
                logger.error("",e);
                e.printStackTrace();
            }
        }
    }

    public void dispatch(IdSession session,Message message){
        short module = message.getModule();
        short cmd = message.getCmd();

        CmdExecutor cmdExecutor = MSG_TASK_MAP.get(module*1000 + "_" +cmd);
        if(cmdExecutor == null){
            logger.error("message executor mission,module = {},cmd={}",module,cmd);
            return;
        }
        Object[] params =convertToMethodParams(session,cmdExecutor.getParams(),message);

        int distributeKey = (int)session.getAttribute(SessionProperties.DISTRIBUTE_KEY);
        GameExecutor.getInstance().acceptTask(TaskMsg.valueOf(distributeKey,cmdExecutor.getHandler()
                ,cmdExecutor.getMethod(),params));
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
