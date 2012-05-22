/*
 * ====================================================================
 *  SoftGene Technologies
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

package org.streameps.client;

/**
 *
 * @author Frank Appiah
 */
public class StreamEPS implements IStreamEPS {

    private StreamEPSHttp streamEPSHttp;
    private StreamEPSJMS streamEPSJMS;
    private StreamEPSMSQ streamEPSMSQ;
    private StreamEPSNetty streamEPSNetty;
    private StreamEPSRMI streamEPSRMI;

    public StreamEPS() {
    }

    public StreamEPS(StreamEPSHttp streamEPSHttp, StreamEPSJMS streamEPSJMS, StreamEPSMSQ streamEPSMSQ, StreamEPSNetty streamEPSNetty, StreamEPSRMI streamEPSRMI) {
        this.streamEPSHttp = streamEPSHttp;
        this.streamEPSJMS = streamEPSJMS;
        this.streamEPSMSQ = streamEPSMSQ;
        this.streamEPSNetty = streamEPSNetty;
        this.streamEPSRMI = streamEPSRMI;
    }

    public StreamEPSHttp getStreamEPSHttp() {
        return streamEPSHttp;
    }

    public void setStreamEPSHttp(StreamEPSHttp streamEPSHttp) {
        this.streamEPSHttp = streamEPSHttp;
    }

    public StreamEPSJMS getStreamEPSJMS() {
        return streamEPSJMS;
    }

    public void setStreamEPSJMS(StreamEPSJMS streamEPSJMS) {
        this.streamEPSJMS = streamEPSJMS;
    }

    public StreamEPSMSQ getStreamEPSMSQ() {
        return streamEPSMSQ;
    }

    public void setStreamEPSMSQ(StreamEPSMSQ streamEPSMSQ) {
        this.streamEPSMSQ = streamEPSMSQ;
    }

    public StreamEPSNetty getStreamEPSNetty() {
        return streamEPSNetty;
    }

    public void setStreamEPSNetty(StreamEPSNetty streamEPSNetty) {
        this.streamEPSNetty = streamEPSNetty;
    }

    public StreamEPSRMI getStreamEPSRMI() {
        return streamEPSRMI;
    }

    public void setStreamEPSRMI(StreamEPSRMI streamEPSRMI) {
        this.streamEPSRMI = streamEPSRMI;
    }

    
}
