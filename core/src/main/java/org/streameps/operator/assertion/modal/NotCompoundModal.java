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
package org.streameps.operator.assertion.modal;

import java.util.List;
import org.streameps.core.IParticipantEventSet;
import org.streameps.processor.pattern.IPatternParameter;

/**
 *
 * @author Frank Appiah
 */
public class NotCompoundModal<M> implements ModalAssertion<M> {

    private ModalAssertion<M> modalAssertion_1, modalAssertion_2;

    public NotCompoundModal(ModalAssertion<M> modalAssertion_1, ModalAssertion<M> modalAssertion_2) {
        this.modalAssertion_1 = modalAssertion_1;
        this.modalAssertion_2 = modalAssertion_2;
    }

    public boolean assertModal(List<IPatternParameter<M>> params, IParticipantEventSet<M> partSetEvent) {
        return !(modalAssertion_1.assertModal(params, partSetEvent) && modalAssertion_2.assertModal(params, partSetEvent));
    }

    public ModalAssertion<M> getModalAssertion_1() {
        return modalAssertion_1;
    }

    public ModalAssertion<M> getModalAssertion_2() {
        return modalAssertion_2;
    }

    public ModalType getModalType() {
        ModalType modalType = ModalType.COMPOUND;
        modalType.setName(modalAssertion_1 + "!" + modalAssertion_2);
        return modalType;
    }
}
