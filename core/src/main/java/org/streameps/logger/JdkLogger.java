package org.streameps.logger;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <a href="http://java.sun.com/javase/6/docs/technotes/guides/logging/index.html">java.util.logging</a>
 * logger.
 *
 * @author Frank Appiah
 *
 */
public class JdkLogger extends AbstractInternalLogger {

    private final Logger logger;
    private final String loggerName;

    JdkLogger(Logger logger, String loggerName) {
        this.logger = logger;
        this.loggerName = loggerName;
    }

    @Override
    public void debug(String msg) {
        logger.logp(Level.FINE, loggerName, null, msg);
    }

    @Override
    public void debug(String msg, Throwable cause) {
        logger.logp(Level.FINE, loggerName, null, msg, cause);
    }

    @Override
    public void error(String msg) {
        logger.logp(Level.SEVERE, loggerName, null, msg);
    }

    @Override
    public void error(String msg, Throwable cause) {
        logger.logp(Level.SEVERE, loggerName, null, msg, cause);
    }

    @Override
    public void info(String msg) {
        logger.logp(Level.INFO, loggerName, null, msg);
    }

    @Override
    public void info(String msg, Throwable cause) {
        logger.logp(Level.INFO, loggerName, null, msg, cause);
    }

    @Override
    public boolean isDebugEnabled() {
        return logger.isLoggable(Level.FINE);
    }

    @Override
    public boolean isErrorEnabled() {
        return logger.isLoggable(Level.SEVERE);
    }

    @Override
    public boolean isInfoEnabled() {
        return logger.isLoggable(Level.INFO);
    }

    @Override
    public boolean isWarnEnabled() {
        return logger.isLoggable(Level.WARNING);
    }

    @Override
    public void warn(String msg) {
        logger.logp(Level.WARNING, loggerName, null, msg);
    }

    @Override
    public void warn(String msg, Throwable cause) {
        logger.logp(Level.WARNING, loggerName, null, msg, cause);
    }

    @Override
    public String toString() {
        return loggerName;
    }
}
