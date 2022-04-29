package com.xia.framework.task;

import com.xia.framework.net.IDispatch;

public interface TaskBase extends IDispatch {
    //在任务池中执行
    void action();
}
