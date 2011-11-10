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
import java.util.Date;

/**
 * The header consists of meta-information about the event, for example, its occurrence
 * time. This information is carried using well-known attributes, and so can be
 * recognised by a processor that might not understand the remainder of the event
 * instance.
 * @author  Frank Appiah
 */
public interface IHeader extends Serializable {

    /**
     * It returns the type of chronon.
     * @return chronon type.
     */
   public ChrononType getChronon();

    /**
     * It returns the detection time.
     * @return detection time.
     */
   public Date getDetectionTime();

    /**
     * It returns the event annotation for the header.
     * @return event annotation.
     */
   public String getEventAnnotation();

    /**
     * It returns the event certainty
     * @return The event certainty.
     */
   public Float getEventCertainty();

    /**
     * It returns the event identity of the header.
     * @return event identity.
     */
   public String getEventIdentity();

    /**
     * It returns the source of the event.
     * @return The source of event.
     */
   public String getEventSource();

    /**
     * It returns the identifier for the header.
     * @return The identifier for the header.
     */
   public String getIdentifier();

    /**
     * It returns the composite indicator.
     * @return True/false value.
     */
   public boolean isIsComposable();

    /**
     * It sets the chronon for the header.
     * @param chronon type of chronon.
     */
   public void setChronon(ChrononType chronon);

    /**
     * It sets the detection time for the header.
     * @param detectionTime detection time.
     */
   public void setDetectionTime(Date detectionTime);

    /**
     * It sets the annotation of the event.
     * @param eventAnnotation event annotation.
     */
   public void setEventAnnotation(String eventAnnotation);

    /**
     * It sets the certainty of the event.
     * @param eventCertainty certainty of the event.
     */
   public void setEventCertainty(Float eventCertainty);

    /**
     * It sets the event identity.
     * @param eventIdentity identity of event.
     */
   public void setEventIdentity(String eventIdentity);

    /**
     * The source of the event.
     * @param eventSource source of event.
     */
   public void setEventSource(String eventSource);

    /**
     * It sets the identifier for the header.
     * @param identifier identifier.
     */
   public void setIdentifier(String identifier);

    /**
     * It sets the composite indicator.
     * 
     * @param isComposable Whether it is composite or not.
     */
   public void setIsComposable(boolean isComposable);

    /**
     * It sets the occurrence time for the header.
     * @param occurenceTime The occurrence time.
     */
   public void setOccurenceTime(Date occurenceTime);
}
