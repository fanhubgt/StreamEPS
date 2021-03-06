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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.streameps.aggregation.collection.SortedAccumulator;
import org.streameps.context.IContextPartition;
import org.streameps.context.IPartitionWindow;
import org.streameps.context.segment.ISegmentContext;
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
 * Implementation of the segment pattern detection logic.
 * 
 * @author Frank Appiah
 * @version 0.4.0
 */
public class SegmentDecider<T extends IContextPartition<ISegmentContext>>
        extends AbstractEPSDecider<IContextPartition<ISegmentContext>>
        implements IPatternMatchListener, IPatternUnMatchListener {

    private List<IContextPartition<ISegmentContext>> contextPartitions;
    private IDeciderPair<IContextPartition<ISegmentContext>> deciderPair;
    private IDeciderContext<IMatchedEventSet> matchDeciderContext;
    private IDeciderContext<IUnMatchEventMap> unMatchDeciderContext;

    public SegmentDecider() {
        super();
        contextPartitions = new ArrayList<IContextPartition<ISegmentContext>>();
        deciderPair = new DeciderPair<IContextPartition<ISegmentContext>>();
        super.setDeciderPair(deciderPair);
    }

    public void decideOnContext(IDeciderPair<IContextPartition<ISegmentContext>> pair) {
        super.setDeciderPair(pair);
        IPatternChain<IBasePattern> patternChain = pair.getPatternDetector();
        patternChain.addPatternMatchedListener(this);
        patternChain.addPatternUnMatchedListener(this);
        List<IContextPartition<ISegmentContext>> partitions = pair.getContextPartitions();
        contextPartitions.addAll(partitions);
        for (IContextPartition<ISegmentContext> partition : partitions) {
            List<IPartitionWindow<?>> windowList = partition.getPartitionWindow();
            for (IPartitionWindow window : windowList) {
                patternChain.executePatternChain(window);
            }
        }
        getLogger().info("Deciding on the context of the decider pair partition....");
    }

    public void onContextPartitionReceive(List<IContextPartition<ISegmentContext>> partitions) {
        this.contextPartitions = partitions;
        this.deciderPair.setContextPartitions(partitions);
        if (isPatternDetectionEnabled()) {
            decideOnContext(this.deciderPair);
        } else {
            matchDeciderContext = new DeciderContext<IMatchedEventSet>();
            matchDeciderContext.setIdentifier(IDUtil.getUniqueID(new Date().toString()));
            IMatchedEventSet matchedEventSet = new MatchedEventSet();
            for (IContextPartition<ISegmentContext> partition : partitions) {
                List<IPartitionWindow<?>> partitionWindows = (List<IPartitionWindow<?>>) partition.getPartitionWindow();
                for (IPartitionWindow<?> window : partitionWindows) {
                    SortedAccumulator accumulator = (SortedAccumulator) window.getWindow();
                    for (Object key : accumulator.getMap().keySet()) {
                        for (Object event : accumulator.getAccumulatedByKey(key)) {
                            matchedEventSet.add(event);
                        }
                    }
                }
            }
            matchDeciderContext.setDeciderValue(matchedEventSet);
            matchDeciderContext.setAnnotation(RECEIVER_EVENTS);
            sendDeciderContext(matchDeciderContext);
            if (isSaveOnDecide()) {
                performMatchSave(matchedEventSet);
            }
        }
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
        matchDeciderContext.setAnnotation(PATTERN_MATCH_EVENTS);
        sendDeciderContext(matchDeciderContext);
        if (isSaveOnDecide()) {
            performMatchSave(matchedEventSet);
        }
        getLogger().info("Performing the event matching process.....");
    }

    public void performMatchSave(IMatchedEventSet matchedEventSet) {
        getDeciderContextStore().saveToStore(IFileEPStore.PATTERN_MATCH_GROUP, matchedEventSet);
        if (getExternalMatchStore() != null) {
            getExternalMatchStore().saveToStore(IFileEPStore.PATTERN_MATCH_GROUP, matchedEventSet);
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
            performUnMatchSave(un_matchedEventSet);
        }
        getLogger().info("Performing the event un-match process.....");
    }

    public void performUnMatchSave(IUnMatchedEventSet un_matchedEventSet) {
        getDeciderContextStore().saveToStore(IFileEPStore.PATTERN_UNMATCH_GROUP, un_matchedEventSet);
        if (getExternalUnMatchStore() != null) {
            getExternalUnMatchStore().saveToStore(IFileEPStore.PATTERN_UNMATCH_GROUP, un_matchedEventSet);
        }
    }

    public IDeciderContext<IUnMatchEventMap> getUnMatchDeciderContext() {
        return unMatchDeciderContext;
    }

    public IDeciderContext<IMatchedEventSet> getMatchDeciderContext() {
        return matchDeciderContext;
    }

    
}
