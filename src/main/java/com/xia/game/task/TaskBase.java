package com.xia.game.task;

public interface TaskBase extends Distributable{
    //在任务池中执行
    void action();
}