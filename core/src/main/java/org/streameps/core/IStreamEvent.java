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
package org.streameps.core;

import java.io.Serializable;
import java.util.Map;

/**
 * An event type is a specification for a set of event objects that have
 * the same semantic intent and same structure; every event object is considered
 * to be an instance of an event type called IStreamEvent.
 * 
 * @author Frank Appiah
 */
public interface IStreamEvent extends Serializable {

    /**
     * It returns the header of the event.
     * @return Header header of event.
     */
    public Header getHeader();

    /**
     * It returns the payload of the stream event.
     * @return Payload payload of this event.
     */
    public Payload getPayload();

    /**
     * It returns the relationship of the payload.
     * It can either be an retraction, membership, generalisation or specialisation.
     *
     * @return Relationship
     */
    public Relationship getRelationshipType();

    /**
     * It returns the open map container for this stream event.
     * 
     * @return The open map content container
     */
    public Map<String,Object> getOpenContent();
    /**
     * It provides a mutator to set the header for the stream event.
     *  Header:
     *     
     * @param header Header to set
     */
    public void setHeader(Header header);

    /**
     * It sets the payload of the stream event.
     * Payload:
     *   id: It has an id attribute
     *   object: The actual content of the event.
     * @param payload payload for the event.
     */
    public void setPayload(Payload payload);

    /**
     * It sets the relationship type of the stream event.
     * 
     * @param relationshipType Type of relationship to be set.
     */
    public void setRelationshipType(Relationship relationshipType);

    /**
     * It is a container for application specific open content
     * attributes to be set.
     * @param openContent It is a map container for optional control commands.
     */
    public void setOpenContent(Map<String, Object> openContent);

}
