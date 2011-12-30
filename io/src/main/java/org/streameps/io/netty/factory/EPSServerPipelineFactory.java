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
package org.streameps.io.netty.factory;

import org.jboss.netty.handler.codec.compression.ZlibDecoder;
import org.jboss.netty.handler.codec.compression.ZlibEncoder;
import org.jboss.netty.handler.codec.compression.ZlibWrapper;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.streameps.io.netty.server.IEPSNettyServer;
import org.streameps.logger.ILogger;
import org.streameps.logger.LoggerUtil;
import static org.jboss.netty.channel.Channels.*;

/**
 *
 * @author Frank Appiah
 */
public class EPSServerPipelineFactory implements ChannelPipelineFactory {

    private IEPSNettyServer nettyServer;
    private static ILogger logger = LoggerUtil.getLogger(EPSServerPipelineFactory.class);

    public EPSServerPipelineFactory() {
    }

    public EPSServerPipelineFactory(IEPSNettyServer nettyServer) {
        this.nettyServer = nettyServer;
    }

    public ChannelPipeline getPipeline() throws Exception {
        ChannelPipeline channelPipeline = pipeline();

        channelPipeline.addLast("deflater", new ZlibEncoder(ZlibWrapper.GZIP));
        channelPipeline.addLast("inflater", new ZlibDecoder(ZlibWrapper.GZIP));

        channelPipeline.addLast("handler", nettyServer.getChannelHandler());
        if (logger.isDebugEnabled()) {
            logger.debug("Settinng the server channel pipe handlers.");
        }
        return channelPipeline;
    }
}
