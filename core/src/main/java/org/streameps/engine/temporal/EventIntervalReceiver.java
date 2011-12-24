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
package org.streameps.engine.temporal;

import java.util.ArrayList;
import java.util.List;
import org.streameps.aggregation.collection.ISortedAccumulator;
import org.streameps.context.IContextDetail;
import org.streameps.context.IContextPartition;
import org.streameps.context.temporal.IEventIntervalContext;
import org.streameps.context.temporal.IEventIntervalParam;
import org.streameps.engine.AbstractEPSReceiver;
import org.streameps.engine.IReceiverContext;
import org.streameps.engine.IReceiverPair;
import org.streameps.engine.IRouterContext;
import org.streameps.engine.IWorkerEventQueue;

/**
 *
 * @author Frank Appiah
 */
public class EventIntervalReceiver<E> extends AbstractEPSReceiver<IContextPartition<IEventIntervalContext>, E> {

    public EventIntervalReceiver() {
        super();
    }

    public void routeEvent(E event, IReceiverPair<? extends IRouterContext, ? extends IReceiverContext> receiverPair) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void buildContextPartition(IReceiverContext receiverContext, List<E> events) {
        IWorkerEventQueue<E> eventQueue = getEventQueue();
        IContextDetail contextDetail = receiverContext.getContextDetail();
        IEventIntervalParam intervalParam = (IEventIntervalParam) receiverContext.getContextParam();
        
    }

    @Override
    public void onReceive(E event) {
       
    }

    public void orderEvents(List<E> events)
    {

    }
    
    public void buildContextPartition(IReceiverContext receiverContext) {
        ISortedAccumulator<E> accumulator = getEventQueue().getAccumulator();
        List<E> events = new ArrayList<E>();
        for (Object key : accumulator.getMap().keySet()) {
            for (E event : accumulator.getAccumulatedByKey(key)) {
                events.add(event);
            }
        }
        buildContextPartition(receiverContext, events);
    }
    
}
