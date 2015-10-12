package org.aimos.abstractg.handlers;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Angyay0 on 12/10/2015.
 */
public class ConcurrentManager {

    private static ThreadPoolExecutor tpool;

    public static ConcurrentManager manager = new ConcurrentManager();

    private ConcurrentManager(){;}

    public static void init(){
        tpool = new ThreadPoolExecutor(10, Constants.CCAPACITY, 1000, null, null,
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        return new Thread(r);
                    }
                }
        );
    }

    public static ConcurrentManager getInstance(){
        return manager;
    }

    public void exec(Runnable r){
        tpool.execute(r);
    }

    public int getThreadsRunning(){
        return tpool.getActiveCount();
    }

    public void stop(Runnable r){
        tpool.remove(r);
    }
}
