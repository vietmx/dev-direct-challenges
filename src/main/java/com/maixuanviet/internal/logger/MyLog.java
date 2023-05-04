package com.maixuanviet.internal.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author VietMX
 */

@Component
public class MyLog {

    private Logger logger;

    private MyLog() {
        logger = LoggerFactory.getLogger(MyLog.class);
    }

    public static MyLog getInstance() {
        return new MyLog();
    }

    private String appendAuthInfo(String msg) {
        return String.format("%s|%s", RequestHolder.getAuthInfo().getPrefixLog(), msg);
    }

    public void trace(String var1) {
        logger.trace(appendAuthInfo(var1));
    }

    public void trace(String var1, Throwable var2) {
        logger.trace(appendAuthInfo(var1), var2);
    }

    public void debug(String var1) {
        logger.debug(appendAuthInfo(var1));
    }

    public void debug(String var1, Throwable var2) {
        logger.debug(appendAuthInfo(var1), var2);
    }

    public void info(String var1) {
        logger.info(appendAuthInfo(var1));
    }

    public void info(String var1, Throwable var2) {
        logger.info(appendAuthInfo(var1), var2);
    }

    public void warn(String var1) {
        logger.warn(appendAuthInfo(var1));
    }

    public void warn(String var1, Throwable var2) {
        logger.warn(appendAuthInfo(var1), var2);
    }

    public void error(String var1) {
        logger.error(appendAuthInfo(var1));
    }

    public void error(String var1, Throwable var2) {
        logger.error(appendAuthInfo(var1), var2);
    }
}
