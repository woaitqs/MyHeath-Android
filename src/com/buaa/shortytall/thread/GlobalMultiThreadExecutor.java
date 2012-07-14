package com.buaa.shortytall.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class GlobalMultiThreadExecutor {
	
	private static final int MAX_THREAD_NUM = 5;
	private static GlobalMultiThreadExecutor instance;
	private ExecutorService minPriorExecutorService;
    private ExecutorService normPriorExecutorService;
    
    private GlobalMultiThreadExecutor(){
    	minPriorExecutorService = Executors.newFixedThreadPool(MAX_THREAD_NUM, new MyHealthThreadFactory(Thread.MIN_PRIORITY));
        normPriorExecutorService = Executors.newFixedThreadPool(MAX_THREAD_NUM, new MyHealthThreadFactory(Thread.NORM_PRIORITY));
    }
    
    public static GlobalMultiThreadExecutor getInstance(){
    	return instance;
    }
    
    public void execute(Runnable runnable, int threadPriority) {
        if (threadPriority == Thread.MIN_PRIORITY) {
            minPriorExecutorService.execute(runnable);
        } else {
            normPriorExecutorService.execute(runnable);
        }
    }
    
    static {
    	if (instance == null){
    		instance = new GlobalMultiThreadExecutor();
    	}
    }
    
    static class MyHealthThreadFactory implements ThreadFactory {
        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;
        private final int priority;

        MyHealthThreadFactory(int priority) {
            this.priority = priority;
            group = Thread.currentThread().getThreadGroup();
            namePrefix = "pool-" + priority + "-" +
                          poolNumber.getAndIncrement() +
                         "-thread-";
        }

        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r,
                                  namePrefix + threadNumber.getAndIncrement(),
                                  0);
            if (t.isDaemon())
                t.setDaemon(false);
            if (t.getPriority() != priority)
                t.setPriority(priority);
            return t;
        }
    }
	
}
