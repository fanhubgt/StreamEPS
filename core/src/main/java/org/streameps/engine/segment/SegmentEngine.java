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
package org.streameps.engine.segment;

import org.streameps.context.segment.SegmentContext;
import org.streameps.context.segment.SegmentPartition;
import org.streameps.engine.DefaultEnginePrePostAware;
import org.streameps.engine.EPSEngine;
import org.streameps.engine.IEPSDecider;
import org.streameps.processor.pattern.BasePattern;

/**
 * Implementation of the segment-oriented engine.
 *
 * @author Frank Appiah
 * @version 0.4.0
 */
public class SegmentEngine<B extends BasePattern> extends EPSEngine<SegmentPartition, B> {

    private SegmentDecider<B> decider;
    private DefaultEnginePrePostAware enginePrePostAware;
    private SegmentContext segmentContext;

    public SegmentEngine() {
        super();
        enginePrePostAware = new DefaultEnginePrePostAware();
        setDecider(decider);
    }

    public void sendOnReceive(Object event) {
     
    }


    public void routeEvent(Object event) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object preProcessOnRecieve(Object event) {
        return this.enginePrePostAware.preProcessOnRecieve(event);
    }

    public Object postProcessBeforeSend(Object event) {
        return this.enginePrePostAware.postProcessBeforeSend(event);
    }

    public void setSegmentContext(SegmentContext segmentContext) {
        this.segmentContext = segmentContext;
        getContextPartition().setContext(segmentContext);
    }

    public SegmentContext getSegmentContext() {
        return segmentContext;
    }

    @Override
    public IEPSDecider<SegmentPartition, B> getDecider() {
        return this.decider;
    }

}
