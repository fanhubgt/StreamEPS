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
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import org.streameps.core.IStreamEvent;
import org.streameps.core.StreamEvent;

/**
 *
 * @author Frank Appiah
 */
public class MatchEventMap<E> implements IMatchEventMap<E> {

    private Map<String, LinkedBlockingQueue<E>> objectMap;
    private Map<String,  LinkedBlockingQueue<IStreamEvent>> streamEventMap;
    private boolean streamed = false;

    public MatchEventMap(boolean isStreamed) {
        this.streamed = isStreamed;
        objectMap = Collections.synchronizedMap(new HashMap<String,  LinkedBlockingQueue<E>>());
        streamEventMap = Collections.synchronizedMap(new HashMap<String,  LinkedBlockingQueue<IStreamEvent>>());
    }

    public void put(String eventName, E event) {
        if (streamed) {
             LinkedBlockingQueue<IStreamEvent> events = streamEventMap.get(eventName);
            if (events == null) {
                events = new  LinkedBlockingQueue<IStreamEvent>();
            }
            events.add((StreamEvent) event);
            streamEventMap.put(eventName, events);
        } else {
             LinkedBlockingQueue<E> objects = objectMap.get(eventName);
            if (objects == null) {
                objects = new  LinkedBlockingQueue<E>();
            }
            objects.add(event);
            objectMap.put(eventName, objects);
        }
    }

    public Map getMatchingEvents() {
        if (streamed) {
            return streamEventMap;
        }
        return objectMap;
    }

    public  LinkedBlockingQueue<IStreamEvent> getMatchingEvents(String eventName) {
        return streamEventMap.get(eventName);
    }

    public  LinkedBlockingQueue<E> getMatchingEventAsObject(String eventName) {
        return objectMap.get(eventName);
    }

    @Override
    public IMatchEventMap<E> clone() {
        return this;
    }

    public void merge(IMatchEventMap<E> mergeMap) {
        Map map = mergeMap.getMatchingEvents();
        for (Object key : map.keySet()) {
            Object vaue = map.get(key);
            put((String) key, (E) vaue);
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

    @Override
    public String toString() {
//        if (streamed) {
//            return "streamMap[value:" + streamEventMap + "]";
//        } else {
//            return "objectMap[value:" + objectMap + "]";
//        }
        return "";
    }

    public Set<String> getKeySet() {
        if (streamed) {
            return streamEventMap.keySet();
        } else {
            return objectMap.keySet();
        }
    }
}
