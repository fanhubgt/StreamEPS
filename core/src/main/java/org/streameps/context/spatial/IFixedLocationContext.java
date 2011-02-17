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
 * A fixed location context has predefined context partitions based on specific
 * spatial entities. An event is included in a partition if its location attribute
 * indicates that it is correlated with the partition’s spatial entity.
 * 
 * @author Frank Appiah
 */
public interface IFixedLocationContext extends IContextDetail, IContextParam<IFixedLocationParam> {

    /**
     * It sets an identifier assigned to this partition. It can be used to
     * refer to this partition from elsewhere.
     * @param partitionIdentifier partition identifier
     */
    public void setPartitionIdentifier(String partitionIdentifier);

    /**
     * It returns an identifier assigned to this partition. It can be used to refer to this
     * partition from elsewhere.
     * @return partition identifier
     */
    public String getPartitionIdentfier();

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
}
