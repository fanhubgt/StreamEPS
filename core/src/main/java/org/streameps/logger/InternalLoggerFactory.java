package org.streameps.logger;


/**
 * @author Frank Appiah
 */
public abstract class InternalLoggerFactory {

    private static volatile InternalLoggerFactory defaultFactory = new JdkLoggerFactory();

    /**
     * Returns the default factory.  The initial default factory is
     * {@link JdkLoggerFactory}.
     */
    public static InternalLoggerFactory getDefaultFactory() {
        return defaultFactory;
    }

    /**
     * Changes the default factory.
     */
    public static void setDefaultFactory(InternalLoggerFactory defaultFactory) {
        if (defaultFactory == null) {
            throw new NullPointerException("defaultFactory");
        }
        InternalLoggerFactory.defaultFactory = defaultFactory;
    }

    /**
     * Creates a new logger instance with the name of the specified class.
     */
    public static ILogger getInstance(Class<?> clazz) {
        return getInstance(clazz.getName());
    }

    /**
     * Creates a new logger instance with the specified name.
     */
    public static ILogger getInstance(String name) {
        final ILogger logger = getDefaultFactory().newInstance(name);
        return new ILogger() {

            @Override
            public void debug(String msg) {
                logger.debug(msg);
            }

            @Override
            public void debug(String msg, Throwable cause) {
              //  StackTraceSimplifier.simplify(cause);
                logger.debug(msg, cause);
            }

            @Override
            public void error(String msg) {
                logger.error(msg);
            }

            @Override
            public void error(String msg, Throwable cause) {
              //  StackTraceSimplifier.simplify(cause);
                logger.error(msg, cause);
            }

            @Override
            public void info(String msg) {
                logger.info(msg);
            }

            @Override
            public void info(String msg, Throwable cause) {
             //   StackTraceSimplifier.simplify(cause);
                logger.info(msg, cause);
            }

            @Override
            public boolean isDebugEnabled() {
                return logger.isDebugEnabled();
            }

            @Override
            public boolean isErrorEnabled() {
                return logger.isErrorEnabled();
            }

            @Override
            public boolean isInfoEnabled() {
                return logger.isInfoEnabled();
            }

            @Override
            public boolean isWarnEnabled() {
                return logger.isWarnEnabled();
            }

            @Override
            public void warn(String msg) {
                logger.warn(msg);
            }

            @Override
            public void warn(String msg, Throwable cause) {
//                StackTraceSimplifier.simplify(cause);
                logger.warn(msg, cause);
            }

            @Override
            public boolean isEnabled(InternalLogLevel level) {
                return logger.isEnabled(level);
            }

            @Override
            public void log(InternalLogLevel level, String msg) {
                logger.log(level, msg);
            }

            @Override
            public void log(InternalLogLevel level, String msg, Throwable cause) {
                //StackTraceSimplifier.simplify(cause);
                logger.log(level, msg, cause);
            }
        };
    }

    /**
     * Creates a new logger instance with the specified name.
     */
    public abstract ILogger newInstance(String name);
}
