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
     *
     * @return
     */
   public ChrononType getChronon();

    /**
     *
     * @return
     */
   public Date getDetectionTime();

    /**
     *
     * @return
     */
   public String getEventAnnotation();

    /**
     *
     * @return
     */
   public Float getEventCertainty();

    /**
     *
     * @return
     */
   public String getEventIdentity();

    /**
     *
     * @return
     */
   public String getEventSource();

    /**
     *
     * @return
     */
   public String getIdentifier();

    /**
     *
     * @return
     */
   public boolean isIsComposable();

    /**
     *
     * @param chronon
     */
   public void setChronon(ChrononType chronon);

    /**
     *
     * @param detectionTime
     */
   public void setDetectionTime(Date detectionTime);

    /**
     *
     * @param eventAnnotation
     */
   public void setEventAnnotation(String eventAnnotation);

    /**
     *
     * @param eventCertainty
     */
   public void setEventCertainty(Float eventCertainty);

    /**
     *
     * @param eventIdentity
     */
   public void setEventIdentity(String eventIdentity);

    /**
     *
     * @param eventSource
     */
   public void setEventSource(String eventSource);

    /**
     *
     * @param identifier
     */
   public void setIdentifier(String identifier);

    /**
     *
     * @param isComposable
     */
   public void setIsComposable(boolean isComposable);

    /**
     *
     * @param occurenceTime
     */
   public void setOccurenceTime(Date occurenceTime);
}
