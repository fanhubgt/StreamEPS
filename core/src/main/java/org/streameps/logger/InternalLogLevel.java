package org.streameps.logger;

/**
 * The log level that {@link InternalLogger} can log at and this helps
 * to identify the kind of log level.
 *
 * @author Frank Appiah
 */
public enum InternalLogLevel {
    /**
     * 'DEBUG' log level.
     */
    DEBUG,
    /**
     * 'INFO' log level.
     */
    INFO,
    /**
     * 'WARN' log level.
     */
    WARN,
    /**
     * 'ERROR' log level.
     */
    ERROR;
}
