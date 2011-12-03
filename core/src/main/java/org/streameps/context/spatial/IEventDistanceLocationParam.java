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
package org.streameps.context.spatial;

import org.streameps.context.temporal.IInitiatorEventList;

/**
 * Interface for the event distance location parameter.
 * 
 * @author Frank Appiah
 */
public interface IEventDistanceLocationParam {

    /**
     * A new partition is created when the event processing agent receives any of
     * the events specified in the list. An event may be specified by its event
     * type, in which case any instance of that event type will create the partition,
     * or it may be specified by the combination of an event type and a predicate
     * expression. If the predicate is present, the partition will be created only if the
     * event instance also satisfies the predicate (that is to say, the predicate expression
     * must return TRUE when evaluated on the event instance).
     * @param eventList initiator event list to set.
     */
    public void setInitiatorEventList(IInitiatorEventList eventList);

    /**
     * It returns the initiator event list in which case any instance of that event
     * type will create the partition, or it may be specified by the combination of
     * an event type and a predicate expression.
     * @return initiator event list 
     */
    public IInitiatorEventList getInitiatorEventList();

    /**
     * It returns identifier of the attribute in the event that gives the event’s
     * location.
     * @return location attribute.
     */
    public String getLocationAttribute();

    /**
     * It sets the identifier of the attribute in the event that gives the
     * event’s location.
     * @param locationAttribute location attribute to set.
     */
    public void setLocationAttribute(String locationAttribute);

    /**
     * It returns the maximum distance for the entity distance location parameter.
     * @return maximum distance
     */
    public double getMaxDistance();

    /**
     * It returns the minimum distance for the entity distance location parameter.
     * @return minimum distance
     */
    public double getMinDistance();

    /**
     * An event is included in the partition if the shortest distance between
     * the event and the entity is less than the maximum distance and not less
     * than the minimum.
     * @param maxDistance maximum distance
     */
    public void setMaxDistance(double maxDistance);

    /**
     * An event is included in the partition if the shortest distance between
     * the event and the entity is less than the maximum distance and not less
     * than the minimum.
     * @param minDistance minimum distance.
     */
    public void setMinDistance(double minDistance);
}
