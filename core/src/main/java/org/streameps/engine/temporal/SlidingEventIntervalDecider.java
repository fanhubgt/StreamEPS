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
import java.util.Date;
import java.util.List;
import org.streameps.context.IContextPartition;
import org.streameps.context.IPartitionWindow;
import org.streameps.context.temporal.ISlidingEventIntervalContext;
import org.streameps.core.IMatchedEventSet;
import org.streameps.core.IUnMatchedEventSet;
import org.streameps.core.MatchedEventSet;
import org.streameps.core.UnMatchedEventSet;
import org.streameps.core.util.IDUtil;
import org.streameps.dispatch.Dispatchable;
import org.streameps.engine.AbstractEPSDecider;
import org.streameps.engine.DeciderContext;
import org.streameps.engine.DeciderPair;
import org.streameps.engine.IDeciderContext;
import org.streameps.engine.IDeciderPair;
import org.streameps.engine.IPatternChain;
import org.streameps.processor.pattern.IBasePattern;
import org.streameps.processor.pattern.listener.IMatchEventMap;
import org.streameps.processor.pattern.listener.IPatternMatchListener;
import org.streameps.processor.pattern.listener.IPatternUnMatchListener;
import org.streameps.processor.pattern.listener.IUnMatchEventMap;
import org.streameps.store.file.IFileEPStore;

/**
 *
 * @author Frank Appiah
 */
public class SlidingEventIntervalDecider<T extends IContextPartition<ISlidingEventIntervalContext>>
        extends AbstractEPSDecider<IContextPartition<ISlidingEventIntervalContext>>
        implements IPatternMatchListener, IPatternUnMatchListener {

    private List<IContextPartition<ISlidingEventIntervalContext>> partitions;
    private IDeciderPair<IContextPartition<ISlidingEventIntervalContext>> deciderPair;
    private IDeciderContext<IMatchedEventSet> matchDeciderContext;
    private IDeciderContext<IUnMatchEventMap> unMatchDeciderContext;

    public SlidingEventIntervalDecider() {
        super();
        deciderPair = new DeciderPair<IContextPartition<ISlidingEventIntervalContext>>();
        super.setDeciderPair(deciderPair);
        partitions = new ArrayList<IContextPartition<ISlidingEventIntervalContext>>();
        super.setContextPartition(partitions);
    }

    public void decideOnContext(IDeciderPair<IContextPartition<ISlidingEventIntervalContext>> pair) {
        super.setDeciderPair(pair);
        IPatternChain<IBasePattern> patternChain = pair.getPatternDetector();
        patternChain.addPatternMatchedListener(this);
        patternChain.addPatternUnMatchedListener(this);
        List<IContextPartition<ISlidingEventIntervalContext>> contextPars = pair.getContextPartitions();
        this.partitions.addAll(contextPars);
        for (IContextPartition<ISlidingEventIntervalContext> partition : contextPars) {
            List<IPartitionWindow<?>> windowList = partition.getPartitionWindow();
            for (IPartitionWindow window : windowList) {
                patternChain.executePatternChain(window);
            }
        }
    }

    public void onContextPartitionReceive(List<IContextPartition<ISlidingEventIntervalContext>> partitions) {
        this.partitions = partitions;
        this.deciderPair.setContextPartitions(partitions);
        decideOnContext(this.deciderPair);
    }

    public void onMatch(IMatchEventMap eventMap, Dispatchable dispatcher, Object... optional) {
        matchDeciderContext = new DeciderContext<IMatchedEventSet>();
        matchDeciderContext.setIdentifier(IDUtil.getUniqueID(new Date().toString()));
        IMatchedEventSet matchedEventSet = new MatchedEventSet();
        for (Object key : eventMap.getKeySet()) {
            for (Object event : eventMap.getMatchingEventAsObject((String) key)) {
                matchedEventSet.add(event);
            }
        }
        matchDeciderContext.setDeciderValue(matchedEventSet);
        sendDeciderContext(matchDeciderContext);
        if (isSaveOnDecide()) {
            getDeciderContextStore().saveToStore(IFileEPStore.PATTERN_MATCH_GROUP, matchedEventSet);
        }
    }

    public void onUnMatch(IUnMatchEventMap eventMap, Dispatchable dispatcher, Object... optional) {
        unMatchDeciderContext = new DeciderContext<IUnMatchEventMap>();
        unMatchDeciderContext.setIdentifier(IDUtil.getUniqueID(new Date().toString()));
        unMatchDeciderContext.setDeciderValue(eventMap);
        if (isSaveOnDecide()) {
            IUnMatchedEventSet un_matchedEventSet = new UnMatchedEventSet();
            for (Object key : eventMap.getKeySet()) {
                for (Object value : eventMap.getUnMatchingEvents((String) key)) {
                    un_matchedEventSet.add(value);
                }
            }
            getDeciderContextStore().saveToStore(IFileEPStore.PATTERN_UNMATCH_GROUP, un_matchedEventSet);
        }
    }
}
