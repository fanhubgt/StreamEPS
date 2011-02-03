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

import org.streameps.context.TemporalOrder;

/**
 * Interface for an interval event parameter.
 * 
 * @author  Development Team
 */
public interface IIntervalEventParam {

    /**
     * It returns the initiator event list for the event parameter.
     *
     * @return List of event initiator types and predicate expression.
     */
    public InitiatorEventList getInitiatorList();
    /**
     * This sets a list of event types and predicate expression that initiates an 
     * interval event window to open after it satisfies the predicate expression.
     * 
     * @param eventList An initiator event list.
     */
    public void setInitiatorList(InitiatorEventList eventList);

    /**
     * It sets the terminator event list with the predicate expression which is
     * satisfied for termination to occur.
     * 
     * @param terminatorEventList List of terminator event list.
     */
    public void setTerminatorList(TerminatorEventList terminatorEventList);

    /**
     * It returns the terminator event list for the event parameter.
     * 
     * @return List of terminators
     */
    public TerminatorEventList getTerminatorList();

    /**
     * It sets the expiration time offset before closing the interval event window.
     * The window is closed after this time period has elapsed, even if no
     * terminator event has been received. By default this is an offset from
     * the time the window was opened, but it can also be specified as an offset
     * from any attribute of the initiator event whose data type is DateTime.
     * 
     * @param offset expiration time offset.
     */
    public void setExpirationTimeOffset(long offset);

    /**
     * It returns the expiration time offset.
     * @return expiration time offset.
     */
    public long getExpirationTimeOffset();

    /**
     * It sets the number of event count to reach before closing the interval
     * event window. An event that causes the expiration event count limit to be
     * reached is included in the window (unless it happens also to be the terminator
     * event).
     * @param eventCount expiration event count.
     */
    public void setExpirationEventCount(long eventCount);

    /**
     * It returns the number of event count for the interval event window.
     * The window is closed after it has reached this size, even if no terminator
     * event has been received.
     * @return expiration event count.
     */
    public long getExpirationEventCount();
    
    /**
     * This parameter indicates whether inclusion of events in the window depends 
     * on the order in which they are detected, on their occurrence time
     * timestamp, or on another specified attribute in the event that has a value that
     * increases over time (a timestamp or an application-specified sequence number).
     * @param temporalOrder temporal order.
     */
    public void setTemporalOrder(TemporalOrder temporalOrder);

    /**
     * It returns the temporal order set for this interval event.
     * @return temporal order.
     */
    public TemporalOrder getTemporalOrder();
}
