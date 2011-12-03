/*
 * ====================================================================
 *  StreamEPS Platform
 * 
 *  (C) Copyright 2011.
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
 * 
 *  =============================================================================
 */
package org.streameps.aggregation.collection;

import java.util.List;
import java.util.TreeMap;

/**
 * Interface for a sorted accumulator using tree map container.
 * 
 * @author  Frank Appiah
 */
public interface ISortedAccumulator<T> extends IEventAccumulator {

    /**
     * It returns a list of events by a key value.
     * @param key A key value.
     * @return A list of events in the accumulator.
     */
    public List<T> getAccumulatedByKey(Object key);

    /**
     * It returns the map used for the accumulator.
     * @return A tree map consisting of events grouped by key.
     */
    public TreeMap<Object, List<T>> getMap();

    /**
     * It returns the highest list of events.
     * @param n Number of events to return from the top.
     * @return A list of event.
     */
    public List<T> highest(int n);

    /**
     * It returns the highest list of events by key value.
     * @param key A key value.
     * @param n Number of events.
     * @return A list of event.
     */
    public List<T> highestByKey(Object key, int n);

    /**
     * It returns the lowest list of events by a count.
     * @param n Number of events.
     * @return A list of events sized by a number count.
     */
    public List<T> lowest(int n);

    /**
     * It retrieves a list of events by a key value.
     * @param key A key value.
     * @param n Number of events.
     * @return A list of events sized by a number count.
     */
    public List<T> lowestByKey(Object key, int n);

    /**
     * It retrieves and adds an event to be added to
     * @param key A key value.
     * @param value An object being added to the list of events.
     * @return  A list of events by key value.
     */
    public List<T> processAt(Object key, T value);

}
