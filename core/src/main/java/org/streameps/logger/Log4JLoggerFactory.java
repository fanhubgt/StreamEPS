package org.streameps.logger;

/**
 * Logger factory which creates an
 * <a href="http://logging.apache.org/log4j/1.2/index.html">Apache Log4J</a>
 * logger.
 * 
 * @author Frank Appiah
 *
 */
public class Log4JLoggerFactory extends InternalLoggerFactory {

    @Override
    public ILogger newInstance(String name) {
        final org.apache.log4j.Logger logger =
                org.apache.log4j.Logger.getLogger(name);
        return new Log4JLogger(logger);
    }
    
}
