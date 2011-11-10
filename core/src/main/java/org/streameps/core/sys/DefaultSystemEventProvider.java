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
package org.streameps.core.sys;

import java.util.Date;
import org.streameps.core.ChrononType;
import org.streameps.core.Header;
import org.streameps.core.Payload;
import org.streameps.core.StreamEvent;
import org.streameps.core.util.JavaIDEventGenerator;
import org.streameps.core.util.UUIDEventGenerator;

/**
 * Default implementation of the stream event provider.
 * 
 * @author Frank Appiah
 * @version 0.2.3
 */
public class DefaultSystemEventProvider implements StreamEventProvider {

    private StreamEvent streamEvent = null;
    private UUIDEventGenerator idEventGenerator;

    public DefaultSystemEventProvider() {
        streamEvent = new StreamEvent();
        idEventGenerator = new JavaIDEventGenerator();
    }

    public DefaultSystemEventProvider(UUIDEventGenerator idEventGenerator) {
        this.idEventGenerator = idEventGenerator;
    }

    public DefaultSystemEventProvider(StreamEvent streamEvent) {
        this.streamEvent = streamEvent;
    }

    public StreamEvent createStreamEvent(Object event, String eventSource, String eventIdentity, String annotation) {
        String id = idEventGenerator.UUID();
        Payload payload = new Payload(id, event);
        String annot = (annotation == null) ? "Default:System Provider" : annotation;
        String eId = (eventIdentity == null) ? idEventGenerator.UUID() : eventIdentity;
        Header header = new Header(false, id, ChrononType.MILLISECOND, new Date(),
                new Float(1.0), annot, null, eventSource, eId);
        this.streamEvent = new StreamEvent(payload, header, null);
        return this.streamEvent;
    }

    public StreamEvent setDetectionTime(StreamEvent event, long detectionTime) {
        event.getHeader().setDetectionTime(new Date(detectionTime));
        return event;
    }
    
}