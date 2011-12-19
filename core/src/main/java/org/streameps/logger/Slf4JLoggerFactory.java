package org.streameps.logger;



/**
 * Logger factory which creates a <a href="http://www.slf4j.org/">SLF4J</a>
 * logger.
 *
 * @author Frank Appiah
 *
 */
public class Slf4JLoggerFactory extends InternalLoggerFactory {

    @Override
    public ILogger newInstance(String name) {
        final org.slf4j.Logger logger =
            org.slf4j.LoggerFactory.getLogger(name);
        return new Slf4JLogger(logger);
    }
}
