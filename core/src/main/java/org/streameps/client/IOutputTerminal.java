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

import java.util.List;

/**
 * Interface for the event output terminal for the event producer.
 * 
 * @author  Development Team
 */
public interface IOutputTerminal {

    /**
     * It sets an identifier used to distinguish this terminal in cases when the
     * event producer has more than one output terminal.
     * 
     * @param identifier Unique Identifier to be set.
     */
    public void setIdentifier(String identifier);

    /**
     * It returns the unique identifier for the output.
     * 
     * @return unique identifier value
     */
    public String getIdentifier();

    /**
     * It sets a collection of event type identifiers showing the types of events
     * that can be emitted through this output terminal. An output terminal can have
     * one or more event types associated with it.
     * @param eventTypes List of event types supported by the terminal
     */
    public void setEventTypes(List<String> eventTypes);

    /**
     * It returns a collection of event type identifiers showing the types of events
     * that can be emitted through this output terminal.
     * @return List of event types
     */
    public List<String> getEventTypes();

    /**
     * It sets a list of the identifiers of the input terminals of entities which
     * are to receive events from this output terminal. Each output terminal can have
     * zero or more targets. 
     * @param targetRef List of target reference
     */
    public void setTargetReference(List<TargetRefSpec> targetRef);

    /**
     * It returns a list of the identifiers of the input terminals of entities
     * which are to receive events from this output terminal. Each output terminal
     * can have zero or more targets. If the definition element represents an abstract
     * producer type, none of its output terminals have targets assigned to them.
     * 
     * @return List of target reference.
     */
    public List<TargetRefSpec> getTargetReference();
}
