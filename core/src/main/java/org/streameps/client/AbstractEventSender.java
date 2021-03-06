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
package org.streameps.client;

import java.util.ArrayList;
import java.util.List;
import org.streameps.adaptor.IOutputAdapter;


/**
 * It is an abstract implementation of an event producer.
 * 
 * @author Frank Appiah
 */
public abstract class AbstractEventSender implements IEventProducer {

    private IEventProducerDetail eventProducerDetail=new EventProducerDetail();
    private List<IOutputTerminal> terminals = new ArrayList<IOutputTerminal>();
    private IOutputAdapter outputAdapter;

    public IEventProducerDetail getDetail() {
        return this.eventProducerDetail;
    }

    public void setProducerDetail(IEventProducerDetail eventProducerDetail) {
        this.eventProducerDetail = eventProducerDetail;
    }

    public void setOutputTerminals(List outputTerminals) {
        this.terminals=outputTerminals;
    }

    public List<IOutputTerminal> getOutputTerminals() {
        return this.terminals;
    }

    public void addOutputTerminal(IOutputTerminal outputTerminal) {
        this.terminals.add(outputTerminal);
    }

    public void removeOutputTerminal(IOutputTerminal outputTerminal) {
        this.terminals.remove(outputTerminal);
    }

    public IOutputAdapter getOutputAdapter() {
        return outputAdapter;
    }

    public void setOutputAdapter(IOutputAdapter outputAdapter) {
        this.outputAdapter = outputAdapter;
    }
    
}
