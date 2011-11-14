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

package org.streameps.processor.pattern.listener;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import org.streameps.core.StreamEvent;

/**
 *
 * @author  Frank Appiah
 */
public interface IUnMatchEventMap<E>{

        /**
     * It provides a mutator to add an event to the map of matched events.
     * The event can be an instance of the MatchingEventSet or any user defined
     * logical event structure.
     *
     * @param eventName The tag name of the event in the map
     * @param event The event being added to the matched events map
     */
    public void put(final String eventName, final E event);

    /**
     * It returns the map of matched events.
     *
     * @return Map of the matched events.
     */
    public Map getUnMatchingEvents();

    /**
     * It returns a StreamEvent for a event name in the map.
     *
     * @param eventName Tagged event name to search for.
     * @return Matched event as a StreamEvent object.
     */
    public  LinkedBlockingQueue<StreamEvent> getUnMatchingEvents(final String eventName);

    /**
     * It returns an Object for a event name in the map.
     * @param eventName
     * @return Object event matched
     */
    public  LinkedBlockingQueue<E> getUnMatchingEventAsObject(final String eventName);

    /**
     * It returns the shallow copy/clone of this IMatchEventMap.
     * @return Shallow copy/clone of this map.
     */
    public IUnMatchEventMap<E> clone();

    /**
     * It merges the passed to be merged map to this map.
     *
     * @param mergeMap Event map to be merged.
     */
    public void merge(final IUnMatchEventMap<E> mergeMap);

    /**
     * It removes a matched event by the tagged event name of that particular event.
     *
     * @param eventName The tag name of the event in the map
     * @return It returns the object purged from the map.
     */
    public Object purge(String eventName);

    /**
     * It returns the key set.
     * @return
     */
    public Set<String> getKeySet();
}
