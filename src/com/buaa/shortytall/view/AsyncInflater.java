
package com.buaa.shortytall.view;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Process;
import android.view.LayoutInflater;
import android.view.View;

import java.lang.ref.WeakReference;

public class AsyncInflater {

    private static AsyncInflater instance;
    private HandlerThread handlerThread;
    private Handler inflateHandler;

    static {
        if (instance == null) {
            instance = new AsyncInflater();
        }
    }

    private AsyncInflater() {
        handlerThread = new HandlerThread("inflate thread", Process.THREAD_PRIORITY_DEFAULT);
        handlerThread.start();
        inflateHandler = new Handler(handlerThread.getLooper());
    }

    public static AsyncInflater getInstance() {
        return instance;
    }
    
    public void asyncInflate(final LayoutInflater inflater, final int layoutId,
            final WeakReference<Handler> handlerRef,
            final WeakReference<InflateListener> listenerRef) {
        
            postInflate(inflater, layoutId, handlerRef, listenerRef);

    }


    private void postInflate(final LayoutInflater inflater, final int layoutId,
            final WeakReference<Handler> handlerRef,
            final WeakReference<InflateListener> listenerRef) {
        if (handlerRef != null) {
            Handler handler = handlerRef.get();
            if (handler != null) {
                handler.post(new Runnable() {
                    
                    @Override
                    public void run() {
                        View view = inflater.inflate(layoutId, null);
                        InflateListener listener = listenerRef.get();
                        if (listener != null) {
                            listener.onInflatedView(view);
                        }
                    }
                });
            }
        }
    }

}
