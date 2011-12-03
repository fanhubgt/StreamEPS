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
package org.streameps.engine.temporal;

import java.util.Map;
import org.streameps.context.IContextDetail;
import org.streameps.context.IContextPartition;
import org.streameps.context.temporal.TemporalType;
import org.streameps.core.IDomainManager;
import org.streameps.core.PrePostProcessAware;
import org.streameps.dispatch.IDispatcherService;
import org.streameps.engine.AbstractEPSEngine;
import org.streameps.engine.DefaultEnginePrePostAware;
import org.streameps.engine.IEPSDecider;
import org.streameps.engine.IEPSReceiver;
import org.streameps.engine.IPatternChain;
import org.streameps.engine.IWorkerEventQueue;
import org.streameps.processor.pattern.IBasePattern;

/**
 *
 * @author Frank Appiah
 */
public final class TemporalEngine<T extends IContextDetail, E>
        extends AbstractEPSEngine<IContextPartition<T>, E> {

    private DefaultEnginePrePostAware prePostAware;
    private TemporalDecider<T> temporalDecider;
    private TemporalReceiver temporalReceiver;
    private AbstractEPSEngine<IContextPartition<T>, E> epsEngine;
    private TemporalType temporalType;

    public TemporalEngine() {
        super();
    }

    public TemporalEngine(AbstractEPSEngine<IContextPartition<T>, E> psEngine, TemporalType temporalType) {
        this.epsEngine = psEngine;
        this.temporalType = temporalType;
    }

    public TemporalEngine(TemporalDecider<T> temporalDecider,
            AbstractEPSEngine<IContextPartition<T>, E> psEngine,
            TemporalType temporalType) {
        this.temporalDecider = temporalDecider;
        this.epsEngine = psEngine;
        this.temporalType = temporalType;
        setDecider(temporalDecider);
    }

    public TemporalEngine(TemporalDecider<T> temporalDecider,
            TemporalReceiver temporalReceiver,
            AbstractEPSEngine<IContextPartition<T>, E> epsEngine,
            TemporalType temporalType) {
        this.temporalDecider = temporalDecider;
        this.temporalReceiver = temporalReceiver;
        this.epsEngine = epsEngine;
        this.temporalType = temporalType;
        setDecider(temporalDecider);
        setEPSReceiver(temporalReceiver);
    }

    @Override
    public void sendEvent(E event, boolean asynch) {
        epsEngine.sendEvent(event, asynch);
    }

    public void setEPSEngine(AbstractEPSEngine<IContextPartition<T>, E> epsEngine) {
        this.epsEngine = epsEngine;
    }

    public AbstractEPSEngine<IContextPartition<T>, E> getEpsEngine() {
        return epsEngine;
    }

    public Object preProcessOnRecieve(Object event) {
        return null;
    }

    public Object postProcessBeforeSend(Object event) {
        return null;
    }

    @Override
    public void setBasePattern(IPatternChain<IBasePattern> basePattern) {
        this.epsEngine.setBasePattern(basePattern);
    }

    @Override
    public void setDecider(IEPSDecider decider) {
        this.epsEngine.setDecider(decider);
    }

    @Override
    public void setSequenceCount(int sequenceCount) {
        this.epsEngine.setSequenceCount(sequenceCount);
    }

    @Override
    public void setContextPartition(IContextPartition<T> contextPartition) {
        this.epsEngine.setContextPartition(contextPartition);
    }

    @Override
    public void setEventQueued(boolean eventQueued) {
        this.epsEngine.setEventQueued(eventQueued);
    }

    @Override
    public void setEPSReceiver(IEPSReceiver<IContextPartition<T>, E> sReceiver) {
        this.epsEngine.setEPSReceiver(sReceiver);
    }

    @Override
    public void setDomainManager(IDomainManager domainManager) {
        this.epsEngine.setDomainManager(domainManager);
    }

    @Override
    public void setMapIDClass(Map<String, String> mapIDClass) {
        this.epsEngine.setMapIDClass(mapIDClass);
    }

    @Override
    public void setAsynchronous(boolean asynchronous) {
        this.epsEngine.setAsynchronous(asynchronous);
    }

    @Override
    public void setDispatcherService(IDispatcherService dispatcherService) {
        this.epsEngine.setDispatcherService(dispatcherService);
    }

    @Override
    public void setEnginePrePostAware(PrePostProcessAware enginePrePostAware) {
        this.epsEngine.setEnginePrePostAware(enginePrePostAware);
    }

    @Override
    public IPatternChain<IBasePattern> getBasePattern() {
        return this.epsEngine.getBasePattern();
    }

    @Override
    public IContextPartition<T> getContextPartition() {
        return this.epsEngine.getContextPartition();
    }

    @Override
    public IDispatcherService getDispatcherService() {
        return this.epsEngine.getDispatcherService();
    }

    @Override
    public IDomainManager getDomainManager() {
        return this.epsEngine.getDomainManager();
    }

    @Override
    public IEPSReceiver<IContextPartition<T>, E> getEPSReceiver() {
        return this.epsEngine.getEPSReceiver();
    }

    @Override
    public int getSequenceCount() {
        return this.epsEngine.getSequenceCount();
    }

    @Override
    public IWorkerEventQueue getEventQueue() {
        return this.epsEngine.getEventQueue();
    }

    @Override
    public PrePostProcessAware getEnginePrePostAware() {
        return this.epsEngine.getEnginePrePostAware();
    }

    @Override
    public Map<String, String> getMapIDClass() {
        return this.epsEngine.getMapIDClass();
    }

    public IEPSDecider<IContextPartition<T>> getDecider() {
        return this.temporalDecider;
    }

    public void orderContext(IContextPartition<T> contextPartition) {
        this.epsEngine.orderContext(contextPartition);
    }

    public void setTemporalType(TemporalType temporalType) {
        this.temporalType = temporalType;
    }

    public TemporalType getTemporalType() {
        return temporalType;
    }
}
