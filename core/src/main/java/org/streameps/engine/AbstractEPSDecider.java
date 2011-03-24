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

import java.util.ArrayList;
import java.util.List;
import org.streameps.context.IContextPartition;
import org.streameps.processor.pattern.BasePattern;

/**
 * Abstract implementation of the EPS decider interface. The specific functionality of
 * the <b>decide</b> method is left to be implemented by the developer.
 * 
 * @author Frank Appiah
 * @version 0.3.3
 */
public abstract class AbstractEPSDecider<C extends IContextPartition, B extends BasePattern> implements IEPSDecider<C, B> {

    private List<IHistoryStore> historyStores = new ArrayList<IHistoryStore>();
    private IEPSProducer producer;
    private IDeciderPair<C, B> deciderPair;
    private IPatternChain<B> patternChain;

    public AbstractEPSDecider() {
    }

    public List<IHistoryStore> getHistoryStores() {
        return this.historyStores;
    }

    public void setHistoryStores(List<IHistoryStore> historyStore) {
        this.historyStores = historyStore;
    }

    public void setProducer(IEPSProducer producer) {
        this.producer = producer;
    }

    public IEPSProducer getProducer() {
        return producer;
    }

    public void setContextPartition(C contextPartition) {
        this.deciderPair.setContextPartition(contextPartition);
    }

    public void setPatternChain(IPatternChain<B> pattern) {
        this.deciderPair.setPatternDetector(pattern);
        this.patternChain=pattern;
    }

    public IPatternChain<B> getPatternChain() {
        return patternChain;
    }

    public IDeciderPair<C, B> getDeciderPair() {
        return this.deciderPair;
    }

    /**
     * It provides an easy method to add a new history store to the list of
     * history stores.
     * @param historyStore An instance of history store to add to the list.
     */
    public void addHistoryStore(IHistoryStore historyStore) {
        historyStores.add(historyStore);
    }

    /**
     * It removes the history store from the list.
     * @param historyStore An instance of history store to remove from the list.
     */
    public void removeHistoryStore(IHistoryStore historyStore) {
        historyStores.remove(historyStore);
    }
}
