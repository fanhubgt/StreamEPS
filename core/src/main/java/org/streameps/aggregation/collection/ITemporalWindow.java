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

/**
 * Interface for temporal window.
 * 
 * @author  Development Team
 */
public interface ITemporalWindow {

    /**
     * It adjusts the timestamp for the events in the window.
     * 
     * @param delta timestamp used to adjust event timestamps.
     */
    public void adjustWindow(long delta);

    /**
     * It returns the current time stamp for the window.
     * @return current timestamp.
     */
    public Long getCurrentTimestamp();

    /**
     * Retrieves a list of events in a specific time window.
     *
     * @param expireTimestamp Timestamp expire
     * @return Queue of events
     */
    public ArrayDeque<Object> getWindowEvents(long expireTimestamp);

    /**
     * It checks if the window is empty.
     * @return true/false 
     */
    public boolean isEmpty();

    /**
     * It includes an event to the window
     * @param timestamp timestamp for the event
     * @param event event instance to include to window
     */
    public void putOrUpdate(long timestamp, Object event);

    /**
     * It removes an event instance from the window.
     * 
     * @param event event instance to remove.
     */
    public void remove(Object event);

}
