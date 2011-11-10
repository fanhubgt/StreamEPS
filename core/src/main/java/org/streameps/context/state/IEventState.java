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

package org.streameps.context.state;

/**
 * Interface of the state. If an event instance has finite number of state changes then
 * the decision whether to include the same event in a partition is based on the
 * notion that the state of the next event instance is the same as the state of previous
 * next state attribute of the first instance. The comparator attribute used for the
 * event instance is a unique attribute in the event. On the other hand, event instances
 * with no next states will be included in the partition once after the event's state is
 * verified with the registered states.
 *
 * @author Frank Appiah
 */
public interface IEventState {

    /**
     * It sets the event attribute used for the comparing current and previous
     * event instances.
     * 
     * @param attribute event attribute.
     */
    public void setEventAttribute(String attribute);

    /**
     * It returns the event attribute for the event instance.
     * @return event attribute.
     */
    public String getEventAttribute();
    
    /**
     * It sets the name of the state.
     * 
     * @param state name of the state to set.
     */
    public void setState(String state);

    /**
     * It sets the name of the state.
     * 
     * @return name of the state
     */
    public String getState();

    /**
     * It returns an optional next state attribute.
     * @return next state
     */
    public IEventState getNextState();

    /**
     * It sets an optional next state attribute.
     * @param state next state 
     */
    public void setNextState(IEventState state);
}
