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
package org.streameps.test;

import junit.framework.TestCase;
import org.streameps.io.netty.client.ClientConfigurator;
import org.streameps.io.netty.client.ClientConnectParam;
import org.streameps.io.netty.client.IClientConfigurator;
import org.streameps.io.netty.client.IClientConnectParam;
import org.streameps.io.netty.client.IEPSNettyClient;

/**
 *
 * @author Frank Appiah
 */
public class ClientProxyTest extends TestCase {

    public void testClient() {
        IClientConnectParam connectParam = new ClientConnectParam();
        connectParam.setServerAddress("localhost");
        connectParam.setServerPort(5455);
        connectParam.setTcpNoDelay(true);
        connectParam.setKeepAliveFlag(true);
        connectParam.setServerName("testServer");

        final IClientConfigurator configurator = new ClientConfigurator();
        configurator.setClientConnectionParameter(connectParam);
        configurator.setCorePoolSize(2);
       // configurator.configure();

        IEPSNettyClient client = configurator.createClient(null);
        //Channel cha = configurator.getChannel();

        //ChannelFuture future = null;
//        for (int i = 0; i < 2; i++) {
//            TestEvent test = new TestEvent("E1", 22.0-i);
//            StreamEventProvider provider = new DefaultSystemEventProvider();
//            IStreamEvent event = provider.createStreamEvent(test, "local client", null, null);
//
//            future = cha.write(event);
//        }
//        System.out.println();
//        future.addListener(new ChannelFutureListener() {
//
//            public void operationComplete(ChannelFuture cf) throws Exception {
//                configurator.close();
//            }
//        });
    }
}
