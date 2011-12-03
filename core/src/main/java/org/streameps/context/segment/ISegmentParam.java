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
package org.streameps.context.segment;

import java.util.List;
import org.streameps.context.IPredicateExpr;

/**
 * Interface for the segmentation parameter.
 * 
 * @author  Frank Appiah
 */
public interface ISegmentParam {

    /**
     * It sets the list of one or more attributes used to determine the context partition.
     * 
     * @param atts list of attributes
     */
    public void setAttributes(List<String> atts);

    /**
     * It returns the list of one or more attributes used to determine the context partition.
     *
     * @return list of attributes.
     */
    public List<String> getAttributes();

    /**
     * It sets one or more predicate expressions referring to attributes in the
     * event instance. In order to be included in the partition, an event instance
     * must satisfy at least one of these expressions.
     *
     * @param exprs List of predicate expressions.
     */
    public void setPartitionExpr(List<IPredicateExpr> exprs);

    /**
     * It returns one or more predicate expressions referring to attributes
     * in the event instance. In order to be included in the partition, an event instance
     * must satisfy at least one of these expressions.
     *
     * @return List of predicate expressions.
     */
    public List<IPredicateExpr> getPartitionExpr();

    /**
     * An indicator to show if the predicate expression is set.
     * @return A boolean indicator.
     */
    public boolean isPredicateEnabled();

    /**
     * It sets the predicate enabled value.
     * @param predicateEnabled  A boolean indicator.
     */
    public void setPredicateEnabled(boolean predicateEnabled);
}
