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
package org.streameps.context.temporal;

import org.streameps.context.IContextEntry;
import java.util.List;

/**
 * A new window is opened when the event processing agent receives any of the events
 * specified in the list. An event may be specified by its event type, in which case
 * any instance of that event type will open the window, or it may be specified by
 * the combination of an event type and a predicate expression. If the predicate is
 * present, the window will be opened only if the event instance also satisfies the
 * predicate (that is to say if the predicate expression returns TRUE when evaluated
 * on the event instance).
 * 
 * @author  Development Team
 */
public interface InitiatorEventList {

    /**
     * It sets the list of event types and predicate entry for this initiator context.
     *
     * @param eventType List of event types.
     */
    public void setInitiatorEntry(List<IContextEntry> contextEntries);

    /**
     * It returns a list of event types and its predicate entry for the initiator context.
     * 
     * @return List of event types.
     */
    public List<IContextEntry> getInitiatorEntry();

    /**
     * It provides an easy way to add a context entry to the list of the initiator
     * list. The initiator event is included in the window that it initiates.
     *
     * @param contextEntry Context entry to set
     */
    public void addContextEntry(IContextEntry contextEntry);

    /**
     * It removes a context entry from the initiator  entry list.
     * @param contextEntry Context entry to remove
     */
    public void purgeContextEntry(IContextEntry contextEntry);
}
