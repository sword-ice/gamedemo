package com.xia.framework.message;

import java.lang.reflect.Method;

public class CmdExecutor {
    /** logic handler method */
    private Method method;
    /** arguments passed to method */
    private Class<?>[] params;
    /** logic controller */
    private Object handler;

    public static CmdExecutor valueOf(Method method, Class<?>[] params, Object handler) {
        CmdExecutor executor = new CmdExecutor();
        executor.method = method;
        executor.params = params;
        executor.handler = handler;

        return executor;
    }

    public Method getMethod() {
        return method;
    }

    public Class<?>[] getParams() {
        return params;
    }

    public Object getHandler() {
        return handler;
    }
}
