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
package org.streameps.engine;

import java.util.List;
import org.streameps.context.IContextPartition;
import org.streameps.processor.pattern.IBasePattern;

/**
 * Interface for the event processing decider specification. It forms part of the
 * detection-production cycle. It's operation is to basically receive a pair of
 * context partition and pattern matcher to detect which of the events in the context 
 * matches the pattern parameters. The pattern parameters are evaluated with an
 * operator assertion to produce the match result of true or false values and it
 * also support complex patterns.
 *
 * It then sends the sequence of matched events to the event processing system
 * producer.
 * @see IEPSProducer
 * 
 * @author  Frank Appiah
 * @version 0.3.3
 */
public interface IEPSDecider<C extends IContextPartition, B extends IBasePattern> {

    /**
     * It decides on the pattern based on the context partition.
     * @param pair A decider pair that consists of a specific context partition
     * instance and pattern instance.
     * @see IDeciderPair
     */
    public void decideOnContext(IDeciderPair<C, B> pair);

    /**
     * It sets the memory accumulator for the decider.
     * @param historyStore An instance of the history store.
     * @see IHistoryStore
     */
    public void setHistoryStores(List<IHistoryStore> historyStore);

    /**
     * It returns the memory store used to accumulate the items received from the
     * receiver.
     * @return A list of the history store.
     */
    public List<IHistoryStore> getHistoryStores();

    /**
     * It sets the partition for a particular context.
     * 
     * @param contextPartition partition of the context.
     */
    public void setContextPartition(C contextPartition);

    /**
     * It sets the pattern chain implementation used for the detection process.
     *
     * @param basePattern An instance of the pattern detector.
     */
    public void setPatternChain(IPatternChain<B> pattern);

    /**
     * It returns the instance of the pattern chain.
     * @return An instance of the pattern chain.
     */
    public IPatternChain<B> getPatternChain();

    /**
     * It sets the producer that takes a context information and executes
     * each triggered rule (i.e., its action part).
     *
     * @param producer An instance of event processing producer.
     */
    public void setProducer(IEPSProducer producer);

    /**
     * It returns the producer that takes a context information and executes
     * each triggered rule.
     * 
     * @return An instance of the event system producer.
     */
    public IEPSProducer getProducer();

    /**
     * It returns the container for the context partition instance and the base
     * pattern.
     * @return An instance of the decider pair.
     */
    public IDeciderPair<C, B> getDeciderPair();
}
