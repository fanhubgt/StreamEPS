/*
 * ====================================================================
 *  StreamEPS Platform
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
 *  =============================================================================
 */

package org.streameps.context.temporal;

import org.streameps.context.TemporalOrder;

/**
 *
 * @author Frank Appiah
 */
public class SlidingEventIntervalParam implements ISlidingEventIntervalParam{

    private IInitiatorEventList eventList;
    private Long intervalSize;
    private Long eventPeriod;
    private TemporalOrder temporalOrder;

    public SlidingEventIntervalParam() {
    }

    public SlidingEventIntervalParam(long intervalSize, long eventPeriod, TemporalOrder temporalOrder) {
        this.intervalSize = intervalSize;
        this.eventPeriod = eventPeriod;
        this.temporalOrder = temporalOrder;
    }

    public void setEventList(IInitiatorEventList eventList) {
        this.eventList=eventList;
    }

    public IInitiatorEventList getEventList() {
        return this.eventList;
    }

    public void setIntervalSize(long size) {
        this.intervalSize=size;
    }

    public Long getIntervalSize() {
       return this.intervalSize;
    }

    public void setEventPeriod(Long eventPeriod) {
        this.eventPeriod=eventPeriod;
    }

    public Long getEventPeriod() {
        return this.eventPeriod;
    }

    public void setOrdering(TemporalOrder order) {
        this.temporalOrder=order;
    }

    public TemporalOrder getOrdering() {
        return this.temporalOrder;
    }

}
