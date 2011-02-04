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

/**
 * Interface for the fixed location parameter.
 * 
 * @author  Development Team
 */
public interface IFixedLocationParam {

    /**
     * It sets the type of comparison used to determine if the event is correlated
     * with the entity.
     * 
     * @param spatialRelation spatial relation.
     */
    public void setSpatialRelation(SpatialRelationType spatialRelation);

    /**
     * It returns the type of comparison used to determine if the event is correlated
     * with the entity.
     * 
     * @return spatial relation.
     */
    public SpatialRelationType getSpatialRelation();

    /**
     * It sets the location attribute for the fixed location parameter.
     * 
     * @param locAttribute location attribute.
     */
    public void setLocationAttribute(String locAttribute);

    /**
     * It returns the identifier of the event’s location attribute.
     * @return location attribute.
     */
    public String getLocationAttribute();

    /**
     * It sets an optional reference to a global state element that provides the location service
     * for this context
     * @param serviceIdentifier service identifier in the global state.
     */
    public void setLocationService(String serviceIdentifier);

    /**
     * It returns an optional reference to a global state element that provides the location service
     * for this context.
     * @return service identifier in the global state.
     */
    public String getLocationService();

    /**
     * It sets an identifier assigned to this partition. It can be used to
     * refer to this partition from elsewhere.
     * @param partitionIdentifier
     */
    public void setPartitionIdentifier(String partitionIdentifier);

    /**
     * It returns an identifier assigned to this partition. It can be used to refer to this
     * partition from elsewhere.
     * @return
     */
    public String getPartitionIdentfier();

    /**
     * It sets an identifier of the spatial entity associated with this partition.
     * This identifier can be used to retrieve the coordinate representation of the entity
     * from the location service global state element.
     * @param entity reference to a global state entity.
     */
    public void setEntityIdentifier(String entity);

    /**
     * It returns an identifier of the spatial entity associated with this partition.
     * This identifier can be used to retrieve the coordinate representation of the
     * entity from the location service global state element.
     * 
     * @return entity identifier.
     */
    public String getEntityIdentifier();
}
