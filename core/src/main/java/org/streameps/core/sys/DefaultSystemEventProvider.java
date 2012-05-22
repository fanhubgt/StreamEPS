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
import org.streameps.core.EventObject;
import org.streameps.core.Header;
import org.streameps.core.IEventObject;
import org.streameps.core.IRelationship;
import org.streameps.core.IStreamEvent;
import org.streameps.core.Payload;
import org.streameps.core.Relationship;
import org.streameps.core.RelationshipType;
import org.streameps.core.StreamEvent;
import org.streameps.core.util.IDUtil;
import org.streameps.core.util.JavaIDEventGenerator;
import org.streameps.core.util.UUIDEventGenerator;

/**
 * Default implementation of the stream event provider.
 * 
 * @author Frank Appiah
 * @version 0.2.3
 */
public final class DefaultSystemEventProvider implements StreamEventProvider {

    private StreamEvent streamEvent = null;
    private EventObject<?> eventObject = null;
    private UUIDEventGenerator idEventGenerator;

    public DefaultSystemEventProvider() {
        streamEvent = new StreamEvent();
        eventObject = new EventObject<Object>();
        idEventGenerator = new JavaIDEventGenerator();
    }

    public DefaultSystemEventProvider(UUIDEventGenerator idEventGenerator) {
        this.idEventGenerator = idEventGenerator;
    }

    public DefaultSystemEventProvider(StreamEvent streamEvent) {
        this.streamEvent = streamEvent;
    }

    public IStreamEvent createStreamEvent(Object event, String eventSource, String eventIdentity, String annotation) {
        String id = IDUtil.getUniqueID(new Date().toString());
        Payload payload = new Payload(id, event);
        String annot = (annotation == null) ? "Default:System Provider" : annotation;
        String eId = (eventIdentity == null) ? IDUtil.getUniqueID(new Date().toString()) : eventIdentity;
        Header header = new Header(false, id, ChrononType.MILLISECOND, new Date(),
                new Float(1.0), annot, null, eventSource, eId);
        IRelationship relationship = new Relationship();
        relationship.setType(RelationshipType.RETRACTION);
        this.streamEvent = new StreamEvent(payload, header, relationship);
        return this.streamEvent;
    }

    public IStreamEvent setDetectionTime(IStreamEvent event, long detectionTime) {
        event.getHeader().setDetectionTime(new Date(detectionTime));
        return event;
    }

    public IEventObject createEventObject(Object event, String eventSource, String eventIdentity, String eventAnnotation) {
        String id = idEventGenerator.UUID();
        Payload payload = new Payload(id, event);
        String annot = (eventAnnotation == null) ? "Default:System Provider" : eventAnnotation;
        String eId = (eventIdentity == null) ? idEventGenerator.UUID() : eventIdentity;

        eventObject = new EventObject<Object>(payload);
        eventObject.setChronon(ChrononType.MILLISECOND);
        eventObject.setDetectionTime(new Date());
        eventObject.setEventSource(eventSource);
        eventObject.setEventCertainty(Float.NaN);
        eventObject.setOccurenceTime(new Date());
        eventObject.setIsComposable(false);
        eventObject.setIdentifier(id);
        eventObject.setEventAnnotation(annot);
        eventObject.setEventIdentity(eId);

        return this.eventObject;
    }

    public IEventObject setDetectionTime(IEventObject event, long detectionTime) {
        event.setDetectionTime(new Date(detectionTime));
        return event;
    }
}
