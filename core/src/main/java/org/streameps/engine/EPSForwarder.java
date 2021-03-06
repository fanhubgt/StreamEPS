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
package org.streameps.engine;

import java.util.ArrayList;
import java.util.List;
import org.streameps.core.IOutputTerminal;
import org.streameps.core.util.TargetRefSpecUtil;
import org.streameps.epn.channel.ChannelOutputTerminal;

/**
 *
 * @author Frank Appiah
 */
public class EPSForwarder<T> implements IEPSForwarder<T> {

    private List<IOutputTerminal> outputTerminals;
    private IFilterContext<T> forwarderContext;
    private ChannelOutputTerminal channelOutputTerminal;
    private IAggregateContext aggregateContext;
    private IDeciderContext deciderContext;

    public EPSForwarder() {
        outputTerminals = new ArrayList<IOutputTerminal>();
    }

    public void setOutputTerminals(List<IOutputTerminal> outputTerminals) {
        this.outputTerminals = outputTerminals;
    }

    public List<IOutputTerminal> getOutputTerminals() {
        return this.outputTerminals;
    }

    public void addOutputTerminal(IOutputTerminal outputTerminal) {
        getOutputTerminals().add(outputTerminal);
    }

    public void removeOutputTerminal(IOutputTerminal outputTerminal) {
        getOutputTerminals().remove(outputTerminal);
    }

    public void onContextReceive(IFilterContext<T> forwardContext) {
        this.forwarderContext = forwardContext;
        forwardToOutputTerminals();
    }

    public void forwardToOutputTerminals() {
        if (outputTerminals.size() > 0) {
            for (IOutputTerminal terminal : getOutputTerminals()) {
                TargetRefSpecUtil.onTargetFilter(forwarderContext, terminal);
            }
        }
        if (channelOutputTerminal != null) {
            channelOutputTerminal.sendEvent(forwarderContext, true);
        }
    }

    public void forwardAggregate() {
        if (outputTerminals.size() > 0) {
            for (IOutputTerminal terminal : getOutputTerminals()) {
                TargetRefSpecUtil.onTargetAggregate(aggregateContext, terminal);
            }
        }
        if (channelOutputTerminal != null) {
            channelOutputTerminal.sendEvent(forwarderContext, true);
        }
    }

    public void forwardPatternDetection() {
        if (outputTerminals.size() > 0) {
            for (IOutputTerminal terminal : getOutputTerminals()) {
                TargetRefSpecUtil.onTargetDecider(deciderContext, terminal);
            }
        }
        if (channelOutputTerminal != null) {
            channelOutputTerminal.sendEvent(forwarderContext, true);
        }
    }

    public ChannelOutputTerminal getChannelOutputTerminal() {
        return channelOutputTerminal;
    }

    public void setChannelOutputTerminal(ChannelOutputTerminal channelOutputTerminal) {
        this.channelOutputTerminal = channelOutputTerminal;
    }

    public void onPatternContextReceive(IDeciderContext deciderContext) {
        this.deciderContext = deciderContext;
        forwardPatternDetection();
    }

    public void onAggregateContextReceive(IAggregateContext aggregateContext) {
        this.aggregateContext = aggregateContext;
        forwardAggregate();
    }
}
