/*
 * ====================================================================
 *  StreamEPS Platform
 * 
 *  (C) Copyright 2011.
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

import java.util.Date;
import java.util.Random;
import junit.framework.TestCase;
import org.streameps.aggregation.collection.ISortedAccumulator;
import org.streameps.context.ContextDimType;
import org.streameps.context.IContextPartition;
import org.streameps.context.PredicateOperator;
import org.streameps.context.segment.ISegmentContext;
import org.streameps.context.segment.SegmentParam;
import org.streameps.core.IMatchedEventSet;
import org.streameps.core.util.IDUtil;
import org.streameps.engine.EPSProducer;
import org.streameps.engine.FilterContext;
import org.streameps.engine.IDeciderContext;
import org.streameps.engine.builder.EngineBuilder;
import org.streameps.engine.IEPSDecider;
import org.streameps.engine.IEPSEngine;
import org.streameps.engine.IEPSProducer;
import org.streameps.engine.IEPSReceiver;
import org.streameps.engine.IFilterContext;
import org.streameps.engine.ReceiverContext;
import org.streameps.engine.builder.FilterContextBuilder;
import org.streameps.engine.builder.PatternBuilder;
import org.streameps.engine.builder.ReceiverContextBuilder;
import org.streameps.engine.segment.SegmentDecider;
import org.streameps.engine.segment.SegmentEngine;
import org.streameps.engine.segment.SegmentReceiver;
import org.streameps.filter.FilterType;
import org.streameps.filter.IEPSFilter;
import org.streameps.filter.IFilterValueSet;
import org.streameps.filter.eval.ComparisonContentEval;
import org.streameps.operator.assertion.trend.DecreasingAssertion;
import org.streameps.processor.pattern.HighestSubsetPE;
import org.streameps.processor.pattern.TrendPatternPE;

/**
 */
public class EngineTest extends TestCase {

    public EngineTest(String testName) {
        super(testName);
    }

    public void testEngine() {

        //1: initialize the engine, decider, reciever and producer.
        IEPSDecider<IContextPartition<ISegmentContext>> decider = new SegmentDecider();
        IEPSReceiver<IContextPartition<ISegmentContext>, TestEvent> receiver = new SegmentReceiver();
        IEPSEngine<IContextPartition<ISegmentContext>, TestEvent> engine = new SegmentEngine();
        IEPSProducer producer = new EPSProducer();

        //2: set the engine, decider and receiver properties.
        EngineBuilder engineBuilder = new EngineBuilder(decider, engine, receiver);
        engineBuilder.setProducer(producer);

        //set the properties: sequence size, asychronous flag, queue flag.
        engineBuilder.buildProperties(20, false, true);

        //3: set up a pattern detector for the decider.
        PatternBuilder patternBuilder = new PatternBuilder(new HighestSubsetPE<TestEvent>());
        patternBuilder.buildParameter("value", 18);/*No comparison operator needed.*/
         //.buildPatternMatchListener(new TestPatternMatchListener())
         //.buildPatternUnMatchListener(new TestUnPatternMatchListener());

        //add the pattern 1 detector built to the engine/decider.
        engineBuilder.buildPattern(patternBuilder.getBasePattern());

        //pattern 2: repeated process.
        patternBuilder=new PatternBuilder(new TrendPatternPE(new DecreasingAssertion()));
        patternBuilder.buildParameter("value");
       // .buildPatternMatchListener(new TestPatternMatchListener())
       // .buildPatternUnMatchListener(new TestUnPatternMatchListener());

        engineBuilder.buildPattern(patternBuilder.getBasePattern());

        //pattern 3: repeated pattern detector process.
        patternBuilder=new PatternBuilder( new HighestSubsetPE<TestEvent>());
        patternBuilder.buildParameter("value", 10);
        //.buildPatternMatchListener(new TestPatternMatchListener())
        //.buildPatternUnMatchListener(new TestUnPatternMatchListener());

        engineBuilder.buildPattern(patternBuilder.getBasePattern());
           //new TestPatternMatchListener(),
           // new TestUnPatternMatchListener());
        
        //4: create the receiver context for the segment partitioning test.
        ReceiverContextBuilder contextBuilder = new ReceiverContextBuilder(new ReceiverContext(), new SegmentParam());

        contextBuilder.buildIdentifier(IDUtil.getUniqueID(new Date().toString()))
                .buildContextDetail(IDUtil.getUniqueID(new Date().toString()), ContextDimType.SEGMENT_ORIENTED)
                .buildPredicateTerm("value", PredicateOperator.EQUAL, 2.677)
                .buildSegmentParamAttribute("value")
                .buildSegmentParamAttribute("name")
                .buildContextParameter("Test Event", contextBuilder.getSegmentParam());

        receiver.setReceiverContext(contextBuilder.getContext());

        //create the filter context for a filtering test process.
        FilterContextBuilder filterContextBuilder = new FilterContextBuilder(new FilterContext());
        filterContextBuilder.buildPredicateTerm("value", PredicateOperator.GREATER_THAN_OR_EQUAL, 19)
                .buildContextEntry("TestEvent", new ComparisonContentEval())
                .buildEvaluatorContext(FilterType.COMPARISON)
                .buildEPSFilter().buildFilterContext();


        IEPSFilter filter = filterContextBuilder.getFilter();
        assertNotNull("Filter is not functional", filter);

        producer.setFilterContext(filterContextBuilder.getFilterContext());

        //5: build and retrieve the modified engine and shoot some events.
        engine = engineBuilder.getEngine();

        assertNotNull(engine);

        Random rand = new Random(50);
        for (int i = 0; i < 40; i++) {
            TestEvent event = new TestEvent("E" + i, ((double) rand.nextDouble())+ 29-(2*i) );
            //TestEvent event = new TestEvent("E" + i,(double)i);
            engine.sendEvent(event, false);
        }

         System.out.println();
        System.out.println("==============Decider Context============");

        //6: check for the decider context
        IDeciderContext<IMatchedEventSet<TestEvent>> context = ((SegmentDecider) engine.getDecider()).getMatchDeciderContext();

        assertNotNull("Decider context is not properly set.",context);

        for (TestEvent event : context.getDeciderValue()) {
            System.out.println("Name:" + event.getName() + "=====Value:" + event.getValue());
        }

        //7: check for the filter context
        IFilterContext<IFilterValueSet<ISortedAccumulator<TestEvent>>> fcontext = producer.getFilterContext();

        assertNotNull("Filter contxt is not set properly", fcontext);

        ISortedAccumulator<TestEvent> accumulator = fcontext.getFilteredValue().getValueSet().getWindow();

        System.out.println();
        System.out.println("============Filter Context=============");

        for (Object key : accumulator.getMap().keySet()) {
            for (TestEvent event : accumulator.getAccumulatedByKey(key)) {
                System.out.println("Name:" + event.getName() + "=====Value:" + event.getValue());
            }
        }
    }
}
