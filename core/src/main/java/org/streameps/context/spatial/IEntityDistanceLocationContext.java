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

import org.streameps.context.IContextDetail;
import org.streameps.context.IContextParam;

/**
 * An entity distance location context assigns events to context partitions based
 * on their distance from a given entity. This entity is either specified by an
 * event attribute or is given as part of the context specification.
 * 
 * @author  Frank Appiah
 */
public interface IEntityDistanceLocationContext extends IContextDetail, IContextParam<IEntityDistanceLocationParam> {

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
     * It returns an identifier assigned to this partition. It can be used to
     * refer to this partition from elsewhere.
     *
     * @return partition identifier.
     */
    public String getPartitionIdentifier();

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

    /**
     * It sets an identifier assigned to this partition. It can be used to
     * refer to this partition from elsewhere.
     * @param partitionIdentifier partition identifier to set.
     */
    public void setPartitionIdentifier(String partitionIdentifier);

    /**
     * It sets an optional reference to a global state element that provides the location service
     * for this context
     * @param serviceIdentifier service identifier in the global state.
     */
    public void setLocationService(Object serviceIdentifier);

    /**
     * It returns an optional reference to a global state element that provides the location service
     * for this context.
     * @return service identifier in the global state.
     */
    public Object getLocationService();

    /**
     * It sets the minimum distance and maximum distance for the entity distance
     * location context.
     * 
     * @param minD minimum distance
     * @param maxD maximum distance
     */
    public void setMinMaxDistance(double minD, double maxD);
}
