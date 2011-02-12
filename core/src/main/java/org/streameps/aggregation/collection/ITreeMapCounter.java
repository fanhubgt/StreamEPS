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

import java.util.Map;
import org.streameps.aggregation.EventAccumulator;

/**
 * Interface for the map counter accumulator.
 * 
 * @author  Development Team
 */
public interface ITreeMapCounter extends EventAccumulator {

    /**
     * It updates the counter value of key by a factor of delta value.
     * @param key Key of the map
     * @param delta Factor to increase count value.
     * @return count value.
     */
    public long addAt(Object key, long delta);

    /**
     * It clears the map counter.
     */
    public void clear();

    /**
     * The map for the counter.
     * @return the map
     */
    public Map<Object, Long> getMap();

    /**
     * It increments counter value of the object key.
     * @param key Key used to increment count value.
     * @return count value.
     */
    public long incrementAt(Object key);

    /**
     * It returns the total number count of events in the map counter.
     * @return overall total count of events in the map.
     */
    public long totalCount();

    /**
     * The total count for a specific key value in the map counter.
     * @param key The key object
     * @return total count value.
     */
    public long totalCountByKey(Object key);

}
