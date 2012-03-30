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

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import org.streameps.logger.ILogger;
import org.streameps.logger.LoggerUtil;

/**
 *
 * @author Frank Appiah
 */
public class EPSMessageListener implements MessageListener {

    private String customMessageListenerClass;
    private ILogger logger = LoggerUtil.getLogger(EPSMessageListener.class);

    public EPSMessageListener() {
    }

    public EPSMessageListener(String customMessageListenerClass) {
        this.customMessageListenerClass = customMessageListenerClass;
    }

    public void onMessage(Message msg) {
        ObjectMessage message = (ObjectMessage) msg;
        try {
            Object event = message.getObject();
            ObjectMessage objectMessage=(ObjectMessage) event;
            try {
                callCustomListener(msg);
            } catch (ClassNotFoundException ex) {
               logger.warn(ex.getMessage());
            }
        } catch (JMSException ex) {
            logger.debug(ex.getMessage());
        }
    }

    private void callCustomListener(Message msg) throws ClassNotFoundException {
        try {
            Class clazz = Class.forName(customMessageListenerClass, true, this.getClass().getClassLoader());
            Object instance = clazz.newInstance();
            MessageListener listener = (MessageListener) instance;
            if (listener != null) {
                listener.onMessage(msg);
            }
        } catch (InstantiationException ex) {
            logger.warn(ex.getMessage());
        } catch (IllegalAccessException ex) {
            logger.warn(ex.getMessage());
        }
    }

    public void setCustomMessageListenerClass(String customMessageListenerClass) {
        this.customMessageListenerClass = customMessageListenerClass;
    }

    public String getCustomMessageListenerClass() {
        return customMessageListenerClass;
    }
}
