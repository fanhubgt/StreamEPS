/*
 * ====================================================================
 *  StreamEPS Platform
 * 
 *  (C) Copyright 2011.
 * 
 *  Distributed under the Modified BSD License.
 *  Copyright notice: The copyright for this software and a full listing
 *  of individual contributors are as shown in the packaged copyright.txt
 *  file.
 *  All rights reserved.
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *  - Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 
 *  - Redistributions in binary form must reproduce the above copyright notice,
 *  this list of conditions and the following disclaimer in the documentation
 *  and/or other materials provided with the distribution.
 * 
 *  - Neither the name of the ORGANIZATION nor the names of its contributors may
 *  be used to endorse or promote products derived from this software without
 *  specific prior written permission.
 * 
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 *  AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 *  FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 *  DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 *  CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 *  OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE
 *  USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 *  =============================================================================
 */
package org.streameps.logger;

/**
 *
 * @author Frank Appiah
 */
public class LoggerUtil {

    private static ILogger logger = null;
    private static InternalLoggerFactory factory=new JdkLoggerFactory();
    private static LoggerUtil instance = null;

    public LoggerUtil() {
    }

    public static LoggerUtil getInstance(InternalLoggerFactory factory) {
        if (instance == null) {
            instance = new LoggerUtil(factory);
        }
        return instance;
    }

    public static LoggerUtil getInstance() {
        if (instance == null) {
            instance = new LoggerUtil(factory);
        }
        return instance;
    }

    public LoggerUtil(InternalLoggerFactory factory) {
        this.factory = factory;
    }

    public LoggerUtil(ILogger logger) {
        LoggerUtil.logger = logger;
    }

    public static void setLogger(ILogger logger) {
        LoggerUtil.logger = logger;
    }

    public static ILogger getLogger(String name, InternalLoggerFactory factory) {
        if (logger == null) {
            logger = InternalLoggerFactory.getInstance(name);
        } else {
            factory = factory;
            logger = factory.newInstance(name);
        }
        return logger;
    }

    public static ILogger getLogger(String name) {
        factory = new JdkLoggerFactory();
        logger = factory.newInstance(name);
        return logger;
    }

    public static ILogger getLogger(Class clazzname) {
        getLogger(clazzname.getName());
        return logger;
    }
}
