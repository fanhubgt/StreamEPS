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

import java.util.List;
import org.streameps.aggregation.collection.SortedAccumulator;
import org.streameps.context.IContextPartition;
import org.streameps.context.IPartitionWindow;
import org.streameps.context.segment.ISegmentContext;
import org.streameps.engine.AbstractEPSDecider;
import org.streameps.engine.IDeciderPair;
import org.streameps.engine.IPatternChain;
import org.streameps.processor.pattern.BasePattern;

/**
 * Implementation of the segment pattern detection logic.
 * 
 * @author Frank Appiah
 * @version 0.4.0
 */
public class SegmentDecider<T extends IContextPartition<ISegmentContext>, S extends BasePattern>
        extends AbstractEPSDecider<IContextPartition<ISegmentContext>, BasePattern> {

    public void decideOnContext(IDeciderPair<IContextPartition<ISegmentContext>, BasePattern> pair) {
        IPatternChain<BasePattern> patternChain = pair.getPatternDetector();
        IContextPartition<ISegmentContext> partition = pair.getContextPartition();
        ISegmentContext context = partition.getContext();
        //partition=(ContextPartition<SegmentContext>) partition;
        List<IPartitionWindow<?>> windowList = partition.getPartitionWindow();
        //SortedAccumulator accumulator=(SortedAccumulator) windowList.get(0).getWindow();
        for (Object pattern : patternChain.getPatterns()) {
            BasePattern basePattern = (BasePattern) pattern;
            //partition.
        }

    }

    private void decideOnWindow(IPartitionWindow<?> window, BasePattern basePattern) {
        SortedAccumulator accumulator = (SortedAccumulator) window.getWindow();

    }
    //private
}
