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
package org.streameps.test;

import junit.framework.TestCase;
import org.streameps.aggregation.ConcatAggregation;
import org.streameps.aggregation.DistinctAggregation;
import org.streameps.aggregation.DistinctConcatAggregation;
import org.streameps.aggregation.TreeMapCounter;
import org.streameps.aggregation.collection.StringAggregateSetValue;

/**
 *
 * @author Frank Appiah
 */
public class AggregationTest extends TestCase {

    public AggregationTest(String testName) {
        super(testName);
    }

    public void testDAggregation() {
        String[] value = {"23", "10", "5", "45", "23", "15", "6", "5", "5"};
        DistinctConcatAggregation aggregation = new DistinctConcatAggregation();
        StringAggregateSetValue agg = new StringAggregateSetValue();
        for (String v : value) {
            aggregation.process(agg, v);
        }
        assertEquals("[23,10,5,45,15,6]", aggregation.getValue());
    }

    public void testConcatAgg() {
        String[] value = {"23", "10", "5", "45", "23", "15", "6", "5", "5"};
        ConcatAggregation ca = new ConcatAggregation();
        StringBuffer bb = new StringBuffer();
        for (String v : value) {
            ca.process(bb, v);
        }
        assertEquals("[23,10,5,45,23,15,6,5,5]", ca.getValue());
       // System.out.println(ca.getValue());
    }

    public void testDistinctAgg() {
        String[] value = {"23", "10", "5", "45", "23", "15", "6", "5", "5"};
        DistinctAggregation ca = new DistinctAggregation();
        TreeMapCounter bb = new TreeMapCounter();
        for (String v : value) {
            ca.process(bb, v);
        }
        assertEquals("{[10:1],[15:1],[23:2],[45:1],[5:3],[6:1]}", ca.getValue());
    }
}
