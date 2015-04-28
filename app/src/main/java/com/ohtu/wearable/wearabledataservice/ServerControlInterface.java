package com.ohtu.wearable.wearabledataservice;

/**
 * Interface used to call MainActivity's server control methods from UI classes
 */
public interface ServerControlInterface {
    public void startServer();
    public void stopServer();
    public boolean isRunning();
}
