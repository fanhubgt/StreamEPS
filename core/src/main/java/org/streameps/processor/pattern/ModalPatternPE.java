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
package org.streameps.processor.pattern;

import org.streameps.dispatch.Dispatchable;
import org.streameps.operator.assertion.modal.ModalAssertion;
import org.streameps.processor.pattern.listener.IMatchEventMap;
import org.streameps.processor.pattern.listener.IUnMatchEventMap;
import org.streameps.processor.pattern.listener.MatchEventMap;
import org.streameps.processor.pattern.listener.UnMatchEventMap;

/**
 * Modal patterns are patterns that take an assertion and check to see if it's
 * satisfied by the entire participant event set or just by some members of the set.
 * 
 * @author  Frank Appiah
 * @version 0.2.2
 */
public class ModalPatternPE extends BasePattern {

    private Dispatchable dispatcher;
    private ModalAssertion modalAssertion;

    public ModalPatternPE() {
        setPatternType(PatternType.MODAL.getName());
    }

    public ModalPatternPE(Dispatchable dispatcher, ModalAssertion modalAssertion) {
        this.dispatcher = dispatcher;
        this.modalAssertion = modalAssertion;
         setPatternType(PatternType.MODAL.getName());
    }

    public void processEvent(Object event) {
        this.participantEvents.add(preProcessOnRecieve(event));
    }

    @Override
    public void output() {
        boolean result = modalAssertion.assertModal(parameters, participantEvents);
        if (result) {
            IMatchEventMap matchEventMap = new MatchEventMap(false);
            for (Object mEvent : this.participantEvents) {
                matchEventMap.put(mEvent.getClass().getName(), postProcessBeforeSend(mEvent));
                this.matchingSet.add(mEvent);
            }
            publishMatchEvents(matchEventMap, dispatcher, getOutputStreamName());
           // matchingSet.clear();
        } else {
            IUnMatchEventMap unmatchEventMap = new UnMatchEventMap(false);
            for (Object mEvent : this.participantEvents) {
                unmatchEventMap.put(mEvent.getClass().getName(), postProcessBeforeSend(mEvent));
            }
            publishUnMatchEvents(unmatchEventMap, dispatcher, getOutputStreamName());
        }
        this.participantEvents.clear();
    }

    public void setModalAssertion(ModalAssertion modalAssertion) {
        this.modalAssertion = modalAssertion;
    }

    public void setDispatcher(Dispatchable dispatcher) {
        this.dispatcher = dispatcher;
    }
    
}
