/*
 * ====================================================================
 *  StreamEPS Platform
 * 
 *  (C) Copyright 2012.
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
package org.streameps.msq;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.streameps.logger.ILogger;
import org.streameps.logger.LoggerUtil;

/**
 *
 * @author Frank Appiah
 */
public class EPSJndiPropLoader implements IEPSJndiPropLoader {

    private ILogger logger = LoggerUtil.getLogger(EPSJndiPropLoader.class);
    private Properties properties;

    public Properties loadProperties(IMSQClient client) {
         properties = new Properties();
        try {
            FileInputStream stream = null;
            try {
                stream = new FileInputStream(client.getMSQConfig().getJNDILocationPath());
            } catch (FileNotFoundException ex) {
                logger.warn(ex.getMessage());
            }
            try {
                properties.load(stream);
            } catch (IOException ex) {
                logger.warn(ex.getMessage());
            }
            Context context = new InitialContext(properties);
            client.setInitiatorContext(context);
        } catch (NamingException ex) {
            logger.warn(ex.getExplanation());
        }
        return properties;
    }

    public void logJndiProperties() {
       logger.info("java.naming.factory.initial:= "+properties.getProperty("java.naming.factory.initial"));
       logger.info("java.naming.provider.url:="+properties.getProperty("java.naming.provider.url"));
       logger.info("java.naming.factory.url.pkgs:= "+properties.getProperty("java.naming.factory.url.pkgs"));
    }
}
