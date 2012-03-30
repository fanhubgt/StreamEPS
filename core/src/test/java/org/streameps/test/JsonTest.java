/*
 * ====================================================================
 *  StreamEPS Platform
 * 
 *  (C) Copyright 2012.
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
 * 
 *  =============================================================================
 */
package org.streameps.test;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import junit.framework.TestCase;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.streameps.aggregation.SumAggregation;
import org.streameps.core.TestEvent;
import org.streameps.core.util.JSONUtil;
import org.streameps.engine.AggregateContext;
import org.streameps.engine.builder.AggregateContextBuilder;
import org.streameps.operator.assertion.AssertionType;

/**
 *
 */
public class JsonTest extends TestCase {

    public JsonTest(String testName) {
        super(testName);
    }

    public void testJson() {
        List<TestEvent> events = new ArrayList<TestEvent>();
        for (int i = 0; i < 10; i++) {
            TestEvent event = new TestEvent("name" + i, 222.0 - i);
            events.add(event);
        }
        JSONObject jsono = new JSONObject();
        AggregateContextBuilder aggregatebuilder = new AggregateContextBuilder();
        aggregatebuilder.buildDeciderAggregateContext("value", new SumAggregation(), 20, AssertionType.GREATER);
        
        JSONObject jsonoa = JSONUtil.createJSONObject(aggregatebuilder);
        
        try {
            jsono.put("list", jsonoa);
            jsono.putOpt("classname", AggregateContext.class.getName());
            //System.out.println(jsonoa.toString(0));
            JSONArray context= jsonoa.names();
            JSONObject jsono1=(JSONObject) jsonoa.get("aggregateContext");
            System.out.println(jsono1.get("assertionType"));
        } catch (JSONException ex) {
            Logger.getLogger(JsonTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
