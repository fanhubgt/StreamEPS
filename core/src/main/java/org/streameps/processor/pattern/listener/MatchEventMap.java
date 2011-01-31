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

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import org.streameps.core.StreamEvent;

/**
 *
 * @author Development Team
 */
public class MatchEventMap implements IMatchEventMap {

    private Map<String, Object> objectMap = Collections.synchronizedMap(new TreeMap<String, Object>());
    private Map<String, StreamEvent> streamEventMap = Collections.synchronizedMap(new TreeMap<String, StreamEvent>());
    private boolean streamed = false;

    public MatchEventMap(boolean isStreamed) {
        this.streamed = isStreamed;
    }

    public void put(String eventName, Object event) {
        if (streamed) {
            streamEventMap.put(eventName, (StreamEvent) event);
        }
        objectMap.put(eventName, event);
    }

    public Map getMatchingEvents() {
        if (streamed) {
            return streamEventMap;
        }
        return objectMap;
    }

    public StreamEvent getMatchingEvent(String eventName) {
        return streamEventMap.get(eventName);
    }

    public Object getMatchingEventAsObject(String eventName) {
        return objectMap.get(eventName);
    }

    @Override
    public IMatchEventMap clone() {
        return this;
    }

    public void merge(IMatchEventMap mergeMap) {
        Map map = mergeMap.getMatchingEvents();
        for (Object key : map.keySet()) {
            Object vaue = map.get(key);
            put((String) key, vaue);
        }
    }

    public Object purge(String eventName) {
        if (streamed) {
            return streamEventMap.remove(eventName);
        }
        return objectMap.remove(eventName);
    }

    public boolean isStreamed() {
        return streamed;
    }
}
