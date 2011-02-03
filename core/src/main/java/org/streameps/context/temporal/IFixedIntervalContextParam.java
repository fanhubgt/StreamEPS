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
 * Interface specification for the fixed interval context parameter.
 * 
 * @author  Development Team
 */
public interface IFixedIntervalContextParam {

    /**
     * It sets the start time for the first (or only) interval. It can either be a
     * DateTime or a Date. If a Date value is given by default, the interval starts
     * at midnight local time on the specified day, however, an application can set
     * its own start of day time to apply to all fixed interval contexts.
     * 
     * @param timestamp The interval start timestamp.
     */
    public void setIntervalStart(long timestamp);

    /**
     * It returns the start time for the first (or only) interval. It can either
     * be a DateTime or a Date. If a Date value is given by default, the interval.
     * 
     * @return The interval start timestamp.
     */
    public Long getIntervalStart();

    /**
     * This sets the end time for the first (or only) interval. It can be a Date
     * or DateTime or it can be a duration giving the length of the interval. If it is
     * specified as a Date, by default the interval ends at midnight that day; however,
     * the application can specify an alternative time for end of day.
     *
     * @param timestamp The interval end timestamp for the context.
     */
    public void setIntervalEnd(long timestamp);

    /**
     * This gives the end time for the first (or only) interval. It can be a Date
     * or DateTime or it can be a duration giving the length of the interval. If it is
     * specified as a Date, by default the interval ends at midnight that day; however,
     * the application can specify an alternative time for end of day.
     *
     * @return The interval end timestamp for the context.
     */
    public Long getIntervalEnd();

    /**
     * This specifies how frequently the interval is to repeat, if at all.
     */
    public void setRecurrence(FrequencyRepeatType repeatType);

    /**
     * It returns how frequent to repeat the interval for the context.
     *
     * @return frequent of recurrence
     */
    public FrequencyRepeatType getRecurrence();

    /**
     * This indicates whether the assignment of events to windows is based on
     * their detection time or on their occurrence time timestamp. The event
     * instance is only assigned to a window if it has the appropriate timestamp.
     *
     * It sets the temporal order of the context.
     * 
     * @param order temporal order
     */
    public void setOrdering(TemporalOrder order);

    /**
     * It returns the temporal order of the context.
     * This parameter indicates whether the assignment of events to windows is
     * based on their detection time or on their occurrence time timestamp. The
     * event instance is only assigned to a window if it has the appropriate
     * timestamp.
     * @return temporal The temporal order.
     */
    public TemporalOrder getOrdering();
}
