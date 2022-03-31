package com.xia.demo.game.net;

import com.xia.demo.game.task.MsgTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class MessageDispatcher {

    private Logger logger = LoggerFactory.getLogger(MessageDispatcher.class);

    private static final Map<String, MsgTask> MSG_TASK_MAP = new HashMap<String, MsgTask>();

    private void init(){
        //Set<Class<?>> controllers = ClassScanner.listClassesWithAnnotation("game", )
    }

}
