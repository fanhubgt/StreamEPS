package org.streameps.logger;


/**
 * Logger factory which creates a
 * <a href="http://java.sun.com/javase/6/docs/technotes/guides/logging/index.html">java.util.logging</a>
 * logger.
 *
 * @author Frank Appiah
 */

public class JdkLoggerFactory extends InternalLoggerFactory {

    @Override
    public ILogger newInstance(String name) {
        final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(name);
        return new JdkLogger(logger, name);
    }
}
