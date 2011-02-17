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
 * Interface for the sliding fixed interval parameter.
 * 
 * @author  Frank Appiah
 */
public interface ISlidingFixedIntervalParam {

    /**
     * The time period that elapses between the start of each window.
     * @param period interval period
     */
    public void setIntervalPeriod(Long period);

    /**
     * The time period that elapses between the start of each window.
     * @return interval period
     */
    public Long getIntervalPeriod();

    /**
     * It sets the time period for which each window stays open.
     * @param duration interval duration to set.
     */
    public void setIntervalDuration(Long duration);

    /**
     * It returns the time period for which each window stays open.
     * @return interval duration
     */
    public Long getIntervalDuration();

    /**
     * It sets the maximum number of event instances to be included in each window.
     * @param intervalSize interval size
     */
    public void setIntervalSize(Long intervalSize);

    /**
     * It returns the maximum number of event instances to be included in each window.
     * @return maximum number of event instances
     */
    public Long getIntervalSize();

    /**
     * It returns the order in which event instances are added to windows.
     * @return temporal order.
     */
    public TemporalOrder getOrdering();

    /**
     * This parameter indicates whether the assignment of events to windows
     * is based on their detection time or on their occurrence time timestamp.
     * @param order temporal order
     */
    public void setOrdering(TemporalOrder order);
}
