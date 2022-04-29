package com.xia.framework.task;

public abstract class AbstractTask implements TaskBase{

    protected int distributeKey;

    private long startTs;

    private long endTs;

    @Override
    public int getDispatchKey(){
        return distributeKey;
    }

    public void setStartTs(){
        this.startTs = System.currentTimeMillis();
    }

    public long getStartTs(){
        return startTs;
    }

    public void setEndTs() {
        this.endTs = System.currentTimeMillis();
    }

    public long getEndTs(){
        return endTs;
    }

    public long getExecutionTime(){
        return endTs - startTs;
    }

}
