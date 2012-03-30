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

import java.util.Observable;
import org.streameps.aggregation.collection.ISortedAccumulator;
import org.streameps.filter.IFilterValueSet;
import org.streameps.filter.listener.FilterObservable;
import org.streameps.filter.listener.IFilterObservable;
import org.streameps.io.netty.server.IServiceCallback;

/**
 *
 * @author Frank Appiah
 */
public class FilterServiceListener extends FilterObservable implements IFilterServiceListener<ISortedAccumulator> {

    private IFilterObservable observablePrototype;
    private IFilterValueSet<ISortedAccumulator> filterValueSet;
    private IServiceCallback<IFilterValueSet<ISortedAccumulator>> serviceCallback;

    public FilterServiceListener() {
        super();
    }

    public FilterServiceListener(IFilterObservable observablePrototype) {
        super(observablePrototype);
        this.observablePrototype = observablePrototype;
    }

    public FilterServiceListener(IServiceCallback<IFilterValueSet<ISortedAccumulator>> serviceCallback) {
        this.serviceCallback = serviceCallback;
    }

    public FilterServiceListener(IFilterObservable observablePrototype, IServiceCallback<IFilterValueSet<ISortedAccumulator>> serviceCallback) {
        this.observablePrototype = observablePrototype;
        this.serviceCallback = serviceCallback;
    }

    public void handleFilteredEvent(IFilterValueSet filterValueSet) {
        setFilterValueSet(filterValueSet);
        callCallback();
    }

    public void update(Observable o, Object arg) {
        notifyAllObservers();
    }

    public void setFilterValueSet(IFilterValueSet<ISortedAccumulator> filterValueSet) {
        this.filterValueSet = filterValueSet;
    }

    public void setServiceCallback(IServiceCallback<IFilterValueSet<ISortedAccumulator>> callback) {
        this.serviceCallback = callback;
    }

    public IServiceCallback<IFilterValueSet<ISortedAccumulator>> getServiceCallback() {
        return this.serviceCallback;
    }

    public void callCallback() {
        this.serviceCallback.onServiceCall(filterValueSet, true);
    }
    
}
