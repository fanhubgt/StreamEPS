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
package org.streameps.context.temporal;

import org.streameps.context.TemporalOrder;

/**
 *
 * @author Frank Appiah
 */
public class EventIntervalParam implements IEventIntervalParam {

    private IInitiatorEventList initiatorEventList;
    private ITerminatorEventList terminatorEventList;
    private long expirationTimeOffSet;
    private long expirationEventCount;
    private TemporalOrder temporalOrder;

    public EventIntervalParam() {
    }

    public EventIntervalParam(IInitiatorEventList initiatorEventList, ITerminatorEventList terminatorEventList, long expirationTimeOffSet, long expirationEventCount, TemporalOrder temporalOrder) {
        this.initiatorEventList = initiatorEventList;
        this.terminatorEventList = terminatorEventList;
        this.expirationTimeOffSet = expirationTimeOffSet;
        this.expirationEventCount = expirationEventCount;
        this.temporalOrder = temporalOrder;
    }

     public EventIntervalParam( long expirationTimeOffSet, long expirationEventCount, TemporalOrder temporalOrder) {
        this.expirationTimeOffSet = expirationTimeOffSet;
        this.expirationEventCount = expirationEventCount;
        this.temporalOrder = temporalOrder;
    }

    public IInitiatorEventList getInitiatorList() {
       return this.initiatorEventList;
    }

    public void setInitiatorList(IInitiatorEventList eventList) {
        this.initiatorEventList=eventList;
    }

    public void setTerminatorList(ITerminatorEventList terminatorEventList) {
        this.terminatorEventList=terminatorEventList;
    }

    public ITerminatorEventList getTerminatorList() {
       return this.terminatorEventList;
    }

    public void setExpirationTimeOffset(long offset) {
        this.expirationTimeOffSet=offset;
    }

    public long getExpirationTimeOffset() {
       return this.expirationTimeOffSet;
    }

    public void setExpirationEventCount(long eventCount) {
        this.expirationEventCount=eventCount;
    }

    public long getExpirationEventCount() {
       return this.expirationEventCount;
    }

    public void setTemporalOrder(TemporalOrder temporalOrder) {
        this.temporalOrder=temporalOrder;
    }

    public TemporalOrder getTemporalOrder() {
        return this.temporalOrder;
    }
}
