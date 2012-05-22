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

package org.streameps.io.netty.server.listener;

import java.util.Date;
import org.streameps.aggregation.IAggregation;
import org.streameps.core.util.IDUtil;
import org.streameps.engine.IAggregateContext;
import org.streameps.io.netty.AggregateResponse;
import org.streameps.io.netty.IAggregateResponse;
import org.streameps.io.netty.server.IServiceCallback;
import org.streameps.processor.AggregatePoint;
import org.streameps.processor.AggregatorListener;

/**
 *
 * @author Frank Appiah
 */
public class EPSAggregateListener implements AggregatorListener<IAggregation> {

    private IAggregateContext aggregateContext;
    private IAggregation aggregation;
    private IServiceCallback<IAggregateResponse> serviceCallback;

    public EPSAggregateListener() {
    }

    public EPSAggregateListener(IServiceCallback<IAggregateResponse> serviceCallback) {
        this.serviceCallback = serviceCallback;
    }

    public void onAggregate(IAggregation aggregate) {
        this.aggregation=aggregate;
        fireCallback();
    }

    public void setAggregateContext(IAggregateContext aggregateContext) {
      this.aggregateContext=aggregateContext;
      fireCallback();
    }

    public void setServiceCallback(IServiceCallback<IAggregateResponse> serviceCallback) {
        this.serviceCallback = serviceCallback;
    }

    private void fireCallback()
    {
        IAggregateResponse response=new AggregateResponse();
        response.setIdentifier(IDUtil.getUniqueID(new Date().toString()));
        response.setAggregateResult(aggregation.getValue());
        response.setAssertionType(aggregateContext.getAssertionType());
        
        this.serviceCallback.onServiceCall(response, true);
    }

    public IAggregateContext getAggregateContext() {
        return aggregateContext;
    }

    public IAggregation getAggregation() {
        return aggregation;
    }

    public void setIdentifier(String identifier) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getIdentifier() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public AggregatePoint getAggregatePoint() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
