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

import java.util.Map;

/**
 *
 * @author Development Team
 */
public class StreamEvent implements IStreamEvent {

    private Payload payload;
    private Header header;
    private Relationship relationshipType = null;
    private Map<String, Object> openContent = null;

    public StreamEvent() {
    }

    public StreamEvent(Payload payload, Header header, Relationship relationshipType) {
        this.payload = payload;
        this.header = header;
        this.relationshipType = relationshipType;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    public void setRelationshipType(Relationship relationshipType) {
        this.relationshipType = relationshipType;
    }

    public Header getHeader() {
        return header;
    }

    public Payload getPayload() {
        return payload;
    }

    public Relationship getRelationshipType() {
        return relationshipType;
    }

    public Map<String, Object> getOpenContent() {
        return openContent;
    }

    public void setOpenContent(Map<String, Object> openContent) {
        this.openContent = openContent;
    }
}