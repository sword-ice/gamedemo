package com.xia.game.common;

import com.xia.game.net.MessageDispatcher;
import com.xia.game.net.SessionManager;
import com.xia.game.ServerConfig;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Map;

@Component
public class SpringContext implements ApplicationContextAware {


    private static ApplicationContext applicationContext = null;

    private static SpringContext instance;


    @PostConstruct
    private void init() {
        instance = this;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContext.applicationContext = applicationContext;
    }

    public static  <T> T getBean(Class<T> clazz){
        return applicationContext.getBean(clazz);
    }

    public static <T> Collection<T> getBeansOfType(Class<T> clazz) {
        return applicationContext.getBeansOfType(clazz).values();
    }

    public static <T> T getBean(String name, Class<T> requiredType) {
        return applicationContext.getBean(name, requiredType);
    }

    public static Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType) {
        return applicationContext.getBeansWithAnnotation(annotationType);
    }


    @Autowired
    private MessageDispatcher dispatch;
    public static MessageDispatcher getMessageDispatcher() {
        return instance.dispatch;
    }

    @Resource
    private SessionManager sessionManager;
    public static SessionManager getSessionManager() {
        return instance.sessionManager;
    }

    @Resource
    private ServerConfig serverConfig;

    public static ServerConfig getServerConfig() {
        return instance.serverConfig;
    }

}
