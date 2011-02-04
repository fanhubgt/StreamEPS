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
 * Interface for the sliding event interval parameter.
 * 
 * @author  Development Team
 */
public interface ISlidingEventIntervalParam {

    /**
     * This specifies which events count towards the interval size and event
     * period. The event processing agent may receive events not specified in 
     * this list; such events are included in the window but do not count towards 
     * the window size. Each entry in the list contains an event type. This type 
     * can be accompanied by a predicate expression, in which case an instance of 
     * the event type is only counted if it satisfies the predicate, that is to 
     * say, if the predicate returns TRUE when evaluated against the event instance.
     * 
     * @param eventList List of event types with predicate expression to set.
     */
    public void setEventList(InitiatorEventList eventList);

    /**
     * It returns a list of of event types accompanied by a predicate expression.
     * 
     * @return List of event types with predicate expression.
     */
    public InitiatorEventList getEventList();

    /**
     * This determines the size of each window. It is specified as the number of
     * events (of a kind specified by the event list parameter) that are to be
     * included in the window.
     * @param size Size of the window.
     */
    public void setIntervalSize(long size);

    /**
     * It returns the window of events that are to be included in the window.
     * @return Size of the window.
     */
    public Long getIntervalSize();

    /**
     * The number of events (as specified by the event list parameter) received
     * by the event processing agent before a new window is to be opened. If this
     * parameter is not specified, it defaults to the interval size, which means that
     * a new window is opened each time the previous window closes.
     * 
     * @param eventPeriod
     */
    public void setEventPeriod(Long eventPeriod);

    /**
     * It returns the number of events (as specified by the event list parameter)
     * received by the event processing agent before a new window is to be opened.
     * @return event period.
     */
    public Long getEventPeriod();

    /**
     * This parameter indicates whether inclusion of events in the window depends
     * on the order in which they are detected, on their occurrence time timestamp,
     * or on some other attribute in the event that has a value that increases over
     * time (a timestamp or an application-specified sequence number).
     * 
     * @param order temporal order to set.
     */
    public void setOrdering(TemporalOrder order);

    /**
     * It returns the order in which the events in the window are to be
     * processed.
     * @return temporal order.
     */
    public TemporalOrder getOrdering();
}
