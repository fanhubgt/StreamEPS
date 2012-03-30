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

import junit.framework.TestCase;
import org.streameps.aggregation.SumAggregation;
import org.streameps.context.PredicateOperator;
import org.streameps.core.util.XMLUtil;
import org.streameps.engine.AggregateContext;
import org.streameps.engine.builder.AggregateContextBuilder;
import org.streameps.engine.builder.FilterContextBuilder;
import org.streameps.filter.FilterType;
import org.streameps.filter.IEPSFilter;
import org.streameps.filter.eval.ComparisonContentEval;
import org.streameps.operator.assertion.AssertionType;

/**
 *
 * @author Development Team
 */
public class XMLTest extends TestCase {

    public XMLTest(String testName) {
        super(testName);
    }

    public void testHello() {
        AggregateContextBuilder aggregatebuilder = new AggregateContextBuilder();
        aggregatebuilder.buildDeciderAggregateContext("value", new SumAggregation(), 20, AssertionType.GREATER);
        byte[] bs = XMLUtil.encode(aggregatebuilder.getAggregateContext());
        System.out.println(bs);
        AggregateContext aggregate = (AggregateContext) XMLUtil.decode(bs);
        System.out.println(aggregate.getAssertionType());
        String encoder = XMLUtil.encode(aggregatebuilder.getAggregateContext(), false);
        System.out.println(encoder);
        aggregate = (AggregateContext) XMLUtil.decode(encoder.getBytes());
        System.out.println(aggregate.getAggregator());

        FilterContextBuilder filterContextBuilder = new FilterContextBuilder();
        filterContextBuilder.buildPredicateTerm("value", PredicateOperator.GREATER_THAN_OR_EQUAL, 18)
                .buildContextEntry("TestEvent", new ComparisonContentEval())
                .buildEvaluatorContext(FilterType.COMPARISON).buildEPSFilter()
                .buildFilterContext();

        IEPSFilter filter = filterContextBuilder.getFilter();

        System.out.println(XMLUtil.encode(filterContextBuilder.getFilterContext(), false));

    }
    
}
