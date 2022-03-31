package com.xia.game.net;

import com.xia.game.task.AbstractTask;
import com.xia.game.thread.NameThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;

public class GameExecutor {

    private Logger logger = LoggerFactory.getLogger(GameExecutor.class);

    private static volatile GameExecutor  instance;

    private final int CORE_SIZE = Runtime.getRuntime().availableProcessors();

    private final ExecutorService[] workerPool = new ExecutorService[CORE_SIZE];

    private final AtomicBoolean run = new AtomicBoolean(true);

    @PostConstruct
    private void init(){
        for(int i = 0;i<CORE_SIZE;i++){
            ThreadFactory threadFactory = new NameThreadFactory("message_task");
            workerPool[i]  = Executors.newSingleThreadExecutor(threadFactory);
        }
        instance = this;
    }

    public static GameExecutor getInstance(){
        return instance;
    }

    public void acceptTask(AbstractTask task){
        if (task == null) {
            logger.error("task is null");
            return;
        }
        int distributeKet = task.getDistributeKey() % CORE_SIZE;
        workerPool[distributeKet].submit(task::action);
    }

    public void shumDown() {
        run.set(false);
    }



}
