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
package org.streameps.client;

import org.streameps.client.EventProducerDetail.ElementType;

/**
 * Interface for the detail of an event producer.
 * 
 * @author  Development Team
 */
public interface IEventProducerDetail {

    /**
     * It returns an optional attribute that provides more information about the
     * event producer instance, class, or abstract type.
     * @return the annotation value
     */
    public String getAnnotation();

    /**
     * It indicates the kind of event producer that is being described by the
     * definition element (for example, it could be a software trace point,
     * or an RFID reader).
     *
     * @return category of the event
     */
    public String getCategory();

    /**
     * It returns an attribute which indicates whether the definition element
     * represents an abstract type, a class of producer instances, or a single
     * instance.
     * @return The element type.
     */
    public ElementType getElementType();

    /**
     * It returns the unique identifier for the event producer.
     * 
     * @return Unique Identifier
     */
    public String getIdentifier();

    /**
     * It returns a boolean attribute which indicates whether the producer can
     * be queried or not.
     * @return Whether it has a query capability.
     */
    public boolean isQueryable();

    /**
     * It sets an optional attribute that provides more information about the
     * event producer instance, class, or abstract type.
     * @param annotation The annotation of the event.
     */
    public void setAnnotation(String annotation);

    /**
     * It sets the name of the producer abstract type, producer class, or
     * producer instance described by the definition element. It can be used
     * when referring to this definition element from elsewhere.
     * For example, it could be a software trace point, or an RFID reader.
     * @param cat The category to be set.
     */
    public void setCategory(String cat);

    /**
     * It sets an attribute which indicates whether the definition element
     * represents an abstract type, a class of producer instances, or a single
     * instance. Its possible values are abstract type, producer class, or producer
     * instance.
     * Provided Element Types:
     * - Abstract
     * - Class
     * - Instance
     * @param elementType
     */
    public void setElementType(ElementType elementType);

    /**
     * It sets the name of the producer abstract type, producer class, or
     * producer instance described by the definition element. It can be used when
     * referring to this definition element from elsewhere.
     * @param id Identifier of the event.
     */
    public void setIdentifier(String id);

    /**
     * It sets a boolean attribute which indicates whether the producer can
     * be queried or not.
     * @param queryable A boolean value 
     */
    public void setQueryable(boolean queryable);
}
