package com.neuqer.fitornot.common;

/**
 * @author DuLong
 * @since 2019/7/30
 * email 798382030@qq.com
 */
public class LogicErrorException extends Throwable {
    public LogicErrorException() {
        super("逻辑上不可达的代码，请检查");
    }
}
