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

import java.util.ArrayList;
import java.util.List;
import org.streameps.context.IPredicateExpr;

/**
 * Implementation of the segmentation parameter specification.
 * 
 * @author  Frank Appiah
 * @version 0.2.2
 */
public class SegmentParam implements ISegmentParam {

    private List<String> attributes;
    private List<IPredicateExpr> partitionExprs;
    private boolean predicateEnabled = false;

    public SegmentParam() {
        attributes=new ArrayList<String>();
        partitionExprs = new ArrayList<IPredicateExpr>();
    }

    public SegmentParam(List<String> attributes) {
        this.attributes = attributes;
    }

    public void setAttributes(List<String> atts) {
        this.attributes = atts;
    }

    public List<String> getAttributes() {
        return this.attributes;
    }

    public void addParam(String param) {
        attributes.add(param);
    }

    public void purgeParam(String param) {
        attributes.remove(param);
    }

    public void setPartitionExprs(List<IPredicateExpr> exprs) {
        this.partitionExprs = exprs;
    }

    public List<IPredicateExpr> getPartitionExprs() {
        return this.partitionExprs;
    }

    public boolean isPredicateEnabled() {
        return predicateEnabled & (partitionExprs.size() > 0);
    }

    public void setPredicateEnabled(boolean predicateEnabled) {
        this.predicateEnabled = predicateEnabled & (partitionExprs.size() > 0);
    }
}
