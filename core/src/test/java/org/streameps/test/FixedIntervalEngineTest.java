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
import java.util.concurrent.TimeUnit;
import junit.framework.TestCase;
import org.streameps.aggregation.SumAggregation;
import org.streameps.client.EPSRuntimeClient;
import org.streameps.client.IEPSRuntimeClient;
import org.streameps.context.ContextDimType;
import org.streameps.context.IContextPartition;
import org.streameps.context.PredicateOperator;
import org.streameps.context.TemporalOrder;
import org.streameps.context.temporal.FixedIntervalContextParam;
import org.streameps.context.temporal.FrequencyRepeatType;
import org.streameps.context.temporal.IFixedIntervalContext;
import org.streameps.context.temporal.TemporalType;
import org.streameps.util.IDUtil;
import org.streameps.dispatch.DispatcherService;
import org.streameps.engine.EPSProducer;
import org.streameps.engine.builder.EngineBuilder;
import org.streameps.engine.IEPSDecider;
import org.streameps.engine.IEPSEngine;
import org.streameps.engine.IEPSProducer;
import org.streameps.engine.IEPSReceiver;
import org.streameps.engine.builder.AggregateContextBuilder;
import org.streameps.engine.builder.FilterContextBuilder;
import org.streameps.engine.builder.PatternBuilder;
import org.streameps.engine.builder.ReceiverContextBuilder;
import org.streameps.engine.temporal.FixedIntervalDecider;
import org.streameps.engine.temporal.FixedIntervalEngine;
import org.streameps.engine.temporal.FixedIntervalReceiver;
import org.streameps.engine.temporal.TemporalDecider;
import org.streameps.engine.temporal.TemporalEngine;
import org.streameps.engine.temporal.TemporalReceiver;
import org.streameps.filter.FilterType;
import org.streameps.filter.IEPSFilter;
import org.streameps.filter.eval.ComparisonContentEval;
import org.streameps.operator.assertion.AssertionType;
import org.streameps.operator.assertion.trend.IncreasingAssertion;
import org.streameps.processor.pattern.HighestSubsetPE;
import org.streameps.processor.pattern.NullPatternPE;
import org.streameps.processor.pattern.TrendPatternPE;

/**
 */
public class FixedIntervalEngineTest extends TestCase {

    public FixedIntervalEngineTest(String testName) {
        super(testName);
    }

    public void testEngine() {

        //1: initialize the engine, decider, reciever and producer.
        IEPSDecider<IContextPartition<IFixedIntervalContext>> decider = new TemporalDecider(new FixedIntervalDecider());
        IEPSReceiver<IContextPartition<IFixedIntervalContext>,TestEvent> receiver = new TemporalReceiver(new FixedIntervalReceiver());
        IEPSEngine<IContextPartition<IFixedIntervalContext>, TestEvent> engine = new TemporalEngine(decider, receiver, new FixedIntervalEngine(), TemporalType.FIXED_INTERVAL);
        IEPSProducer producer = new EPSProducer();

        //2: set the engine, decider and receiver properties.
        EngineBuilder engineBuilder = new EngineBuilder(decider, engine, receiver);
        engineBuilder.setProducer(producer);

        //decider aggregate context
        AggregateContextBuilder aggregatebuilder = new AggregateContextBuilder();
        aggregatebuilder.buildDeciderAggregateContext("value", new SumAggregation(), 20, AssertionType.GREATER);
        // engineBuilder.setAggregatedDetectEnabled(aggregatebuilder.getAggregateContext(), new TestAggregateListener());

        //producer aggregate context
        aggregatebuilder.buildProducerAggregateContext("value", new SumAggregation());
        engineBuilder.setAggregatedEnabled(aggregatebuilder.getAggregateContext(), new TestAggregateListener(), true);

        //producer decider listener
        engineBuilder.setDeciderListener(new TestDeciderListener());

        //set the properties: sequence size, asychronous flag, queue flag.
        engineBuilder.buildProperties(5*2, false, true);
        engineBuilder.buildExecutorManagerProperties(2, "EPS");
        engineBuilder.buildDispatcher(3, 0, 1000, TimeUnit.MILLISECONDS, new DispatcherService());


        //3: set up a pattern detector for the decider.
        PatternBuilder patternBuilder = new PatternBuilder(new NullPatternPE()) //patternBuilder.buildParameter("value", 16);/*No comparison operator needed.*/
                .buildPatternMatchListener(new TestPatternMatchListener())
                .buildPatternUnMatchListener(new TestUnPatternMatchListener());

        //add the pattern 1 detector built to the engine/decider.
        engineBuilder.buildPattern(patternBuilder.getBasePattern());

        //pattern 2: repeated process.
        patternBuilder = new PatternBuilder(new TrendPatternPE<TestEvent>(new IncreasingAssertion()));
        patternBuilder.buildParameter("value").buildPatternMatchListener(new TestPatternMatchListener())
                .buildPatternUnMatchListener(new TestUnPatternMatchListener());

        engineBuilder.buildPattern(patternBuilder.getBasePattern());

        //pattern 3: repeated pattern detector process.
        patternBuilder = new PatternBuilder(new HighestSubsetPE<TestEvent>());
        patternBuilder.buildParameter("value", 12)
                .buildPatternMatchListener(new TestPatternMatchListener())
                .buildPatternUnMatchListener(new TestUnPatternMatchListener());

        engineBuilder.buildPattern(patternBuilder.getBasePattern(),
                new TestPatternMatchListener(),
                new TestUnPatternMatchListener());

        //4: create the receiver context to be used for the segment partition.
        ReceiverContextBuilder contextBuilder = new ReceiverContextBuilder(new FixedIntervalContextParam(10000, 45000, TemporalOrder.TEMPORAL_ATT, FrequencyRepeatType.SECONDLY));

        contextBuilder.buildIdentifier(IDUtil.getUniqueID(new Date().toString()))
                .buildContextDetail(IDUtil.getUniqueID(new Date().toString()), ContextDimType.TEMPORAL)
                .buildContextParameter("Primitive Event", contextBuilder.getFixedIntervalContextParam());

        receiver.setReceiverContext(contextBuilder.getContext());

        //create the filter context for a filtering process.
        FilterContextBuilder filterContextBuilder = new FilterContextBuilder();
        filterContextBuilder.buildPredicateTerm("value", PredicateOperator.GREATER_THAN_OR_EQUAL, 18)
                .buildContextEntry("TestEvent", new ComparisonContentEval())
                .buildEvaluatorContext(FilterType.COMPARISON).buildEPSFilter()
                .buildFilterContext();


        IEPSFilter filter = filterContextBuilder.getFilter();
        assertNotNull("Filter is not functional", filter);

        producer.setFilterContext(filterContextBuilder.getFilterContext());

        //5: build and retrieve the modified engine and shoot some events.
        IEPSRuntimeClient epsRuntimeClient = new EPSRuntimeClient(engineBuilder,
                aggregatebuilder,
                filterContextBuilder,
                patternBuilder, null,
                contextBuilder);

        engine = epsRuntimeClient.getEngine();

        assertNotNull(engine);

        Random rand = new Random(50);
        //Un-comment to send the events.
        for (int i = 0; i < 90; i++) {
            TestEvent event = new TestEvent("E" + i, ((double) rand.nextDouble()) + 29 - (2 * i));
            engine.sendEvent(event, true);
        }

        System.out.println("");

    }
}
