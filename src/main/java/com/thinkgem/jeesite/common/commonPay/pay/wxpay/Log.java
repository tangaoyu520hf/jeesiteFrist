package com.thinkgem.jeesite.common.commonPay.pay.wxpay;

import org.slf4j.Logger;

/**
 * Created by zl on 15-3-18.
 */
public class Log {

    public static final String LOG_TYPE_TRACE = "logTypeTrace";
    public static final String LOG_TYPE_DEBUG = "logTypeDebug";
    public static final String LOG_TYPE_INFO = "logTypeInfo";
    public static final String LOG_TYPE_WARN = "logTypeWarn";
    public static final String LOG_TYPE_ERROR = "logTypeError";

    //打印日志
    private Logger logger;

    public Log(Logger log){
        logger = log;
    }

    public void t(String s){
        logger.trace(s);
    }

    public void d(String s){
        logger.debug(s);
    }

    public void i(String s){
        logger.info(s);
    }

    public void w(String s){
        logger.warn(s);
    }

    public void e(String s){
        logger.error(s);
    }

    public void log(String type,String s){
        if(type.equals(Log.LOG_TYPE_TRACE)){
            t(s);
        }else if(type.equals(Log.LOG_TYPE_DEBUG)){
            d(s);
        }else if(type.equals(Log.LOG_TYPE_INFO)){
            i(s);
        }else if(type.equals(Log.LOG_TYPE_WARN)){
            w(s);
        }else if(type.equals(Log.LOG_TYPE_ERROR)){
            e(s);
        }
    }

}
