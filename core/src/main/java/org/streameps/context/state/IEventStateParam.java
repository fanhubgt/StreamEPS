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

package org.streameps.context.state;

import java.util.List;
import org.streameps.context.TemporalOrder;

/**
 * Interface specification for the event state parameter.
 * 
 * @author Frank Appiah
 */
public interface IEventStateParam {

    /**
     * It returns the identifier of the external entity whose state controls
     * this context.
     * @return entity identifier
     */
   public  String getEntity();

   /**
    * It returns a list of the external entity states which cause an event to be
    * included in the partition.
    * @return list of event state.
    */
   public List<IEventState> getStates();

   /**
    * It indicates whether the decision to include or exclude is made
    * using the value of the state at the event instance’s occurrence time or at its
    * detection time.
    * @return temporal order.
    */
   public TemporalOrder getTemporalOrder();

   /**
    * It sets the identifier of the external entity whose state controls
     * this context.
    * @param entity entity identifier.
    */
   public void setEntity(String entity);

   /**
    * It sets a list of the external entity states which cause an event to be
    * included in the partition.
    * @param states List of states to set
    */
   public void setStates(List<IEventState> states);

   /**
    * It sets the temporal indicator whether the decision to include or exclude is made
    * using the value of the state at the event instance’s occurrence time or at its
    * detection time.
    * @param temporalOrder temporal order.
    */
   public void setTemporalOrder(TemporalOrder temporalOrder);

}
