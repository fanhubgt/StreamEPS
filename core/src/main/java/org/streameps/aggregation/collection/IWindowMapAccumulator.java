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
package org.streameps.aggregation.collection;

import java.util.ArrayDeque;
import java.util.Map;

/**
 * It accumulate events within a temporal window.
 * 
 * @author  Frank Appiah
 */
public interface IWindowMapAccumulator<T> extends IEventAccumulator{

    /**
     * It returns the accumulated events for this temporal window.
     * @param windowTime Time of window
     * @return The accumulated events.
     */
    public ArrayDeque<T> getAccumulate(long windowTime);

    /**
     * It returns a map of events collected within a temporal window.
     *
     * @return Map of aggregate events.
     */
    public Map<Long, ArrayDeque<T>> getWindowMap();

    /**
     * It updates the temporal window of this accumulator.
     * @param delta
     */
    public void updateWindowTime(long delta);

    /**
     * It puts a new event into an accumulator if the time
     * @param win Time of window
     * @param event Event being accumulated
     */
    public void accumulate(long win, ArrayDeque<T> event);

    /**
     * It removes a queue of events from the windows accumulator.
     * 
     * @param timestamp Temporal window
     */
    public void remove(Long timestamp);

    /**
     * It returns the time stamp for this time window accumulator.
     * @return Time window
     */
    public Long getTimestamp();
}
