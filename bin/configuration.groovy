import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.streameps.aggregation.SumAggregation;
import org.streameps.core.EPSRuntimeClient;
import org.streameps.core.IEPSRuntimeClient;
import org.streameps.context.ContextDimType;
import org.streameps.context.IContextPartition;
import org.streameps.context.PredicateOperator;
import org.streameps.context.segment.ISegmentContext;
import org.streameps.context.segment.SegmentParam;
import org.streameps.core.util.IDUtil;
import org.streameps.dispatch.DispatcherService;
import org.streameps.engine.AuditEventStore;
import org.streameps.engine.EPSProducer;
import org.streameps.engine.HistoryStore;
import org.streameps.engine.builder.EngineBuilder;
import org.streameps.engine.IEPSDecider;
import org.streameps.engine.IEPSEngine;
import org.streameps.engine.IEPSProducer;
import org.streameps.engine.IEPSReceiver;
import org.streameps.engine.IHistoryStore;
import org.streameps.engine.StoreType;
import org.streameps.engine.builder.AggregateContextBuilder;
import org.streameps.engine.builder.FilterContextBuilder;
import org.streameps.engine.builder.PatternBuilder;
import org.streameps.engine.builder.ReceiverContextBuilder;
import org.streameps.engine.builder.StoreContextBuilder;
import org.streameps.engine.segment.SegmentDecider;
import org.streameps.engine.segment.SegmentEngine;
import org.streameps.engine.segment.SegmentReceiver;
import org.streameps.filter.FilterType;
import org.streameps.filter.IEPSFilter;
import org.streameps.filter.eval.ComparisonContentEval;
import org.streameps.operator.assertion.AssertionType;
import org.streameps.operator.assertion.trend.IncreasingAssertion;
import org.streameps.processor.pattern.HighestSubsetPE;
import org.streameps.processor.pattern.NullPatternPE;
import org.streameps.processor.pattern.TrendPatternPE;
import org.streameps.store.IStoreProperty;
import org.streameps.store.StoreProperty;
import org.streameps.store.file.IEPSFileSystem;

def epsRuntimeClient =new EPSRuntimeClient()
def storeContextBuilder =new StoreContextBuilder()
def aggregatebuilder = new AggregateContextBuilder()
def isp = new StoreProperty()
def patternBuilder = new PatternBuilder()
def contextBuilder = new ReceiverContextBuilder()
def filterContextBuilder = new FilterContextBuilder()
def historyStore = new HistoryStore()

def decider = new SegmentDecider()
def receiver = new SegmentReceiver()
def engine = new SegmentEngine()
def producer = new EPSProducer()

def engineBuilder  = new EngineBuilder()

Thread.start()
    {
        engineBuilder = new EngineBuilder(decider, engine, receiver)
        engineBuilder.setProducer(producer)
        //aggregatebuilder.buildDeciderAggregateContext("value", new SumAggregation(), 20, AssertionType.GREATER);
        // engineBuilder.setAggregatedDetectEnabled(aggregatebuilder.getAggregateContext(), new TestAggregateListener());

        //producer aggregate context
        //aggregatebuilder.buildProducerAggregateContext("value", new SumAggregation());
        //engineBuilder.setAggregatedEnabled(aggregatebuilder.getAggregateContext(), new TestAggregateListener(), true);

        def location = "c:/store"
 
        isp = new StoreProperty("comp", IEPSFileSystem.DEFAULT_SYSTEM_ID, location)

        historyStore = new HistoryStore(StoreType.FILE, new AuditEventStore(isp, engine.getExecutorManager()))

        storeContextBuilder.addStoreProperty(isp)
        storeContextBuilder.addHistoryStore(historyStore)

        engineBuilder.buildAuditStore(storeContextBuilder.getHistoryStore())
        //producer decider listener
        //engineBuilder.setDeciderListener(new TestDeciderListener())

        //set the properties: sequence size, asychronous flag, queue flag, saveonReceive flag, saveonDecide flag.
        engineBuilder.buildProperties(20, false, true, false, true)
        //core pool size, thread factory name
        engineBuilder.buildExecutorManagerProperties(2, 'EPS')
        engineBuilder.buildDispatcher(3, 0, 1, TimeUnit.MILLISECONDS, new DispatcherService())

        //3: set up a pattern detector for the decider.
        patternBuilder = new PatternBuilder(new NullPatternPE())
        //patternBuilder.buildParameter("value", 16);/*No comparison operator needed.*/
        //patternBuilder.buildPatternMatchListener(new TestPatternMatchListener())
        //patternBuilder.buildPatternUnMatchListener(new TestUnPatternMatchListener())

        //add the pattern 1 detector built to the engine/decider.
        engineBuilder.buildPattern(patternBuilder.getBasePattern())

        //pattern 2: repeated process.
        //patternBuilder = new PatternBuilder(new TrendPatternPE(new IncreasingAssertion()))
        //patternBuilder.buildParameter("value")
        //.buildPatternMatchListener(new TestPatternMatchListener())
        //.buildPatternUnMatchListener(new TestUnPatternMatchListener());
        //engineBuilder.buildPattern(patternBuilder.getBasePattern())

        //pattern 3: repeated pattern detector process.
        //patternBuilder = new PatternBuilder(new HighestSubsetPE<TestEvent>())
        //patternBuilder.buildParameter("value", 12)
        //.buildPatternMatchListener(new TestPatternMatchListener())
        //.buildPatternUnMatchListener(new TestUnPatternMatchListener());
        //engineBuilder.buildPattern(patternBuilder.getBasePattern()),
        // new TestPatternMatchListener(),
        // new TestUnPatternMatchListener());

        //4: create the receiver context to be used for the segment partition.

        //Change the parameter to the constructor to suit a specific need
        //Check the API under package org.streameps.context for the different parameter.
        contextBuilder = new ReceiverContextBuilder(new SegmentParam())
        contextBuilder.buildIdentifier(IDUtil.getUniqueID(new Date().toString()))
        //.buildContextDetail(IDUtil.getUniqueID(new Date().toString()), ContextDimType.SEGMENT_ORIENTED)
        //.buildSegmentParameter(new ComparisonContentEval(),
        // new PredicateTerm("value", PredicateOperator.GREATER_THAN_OR_EQUAL, 18))
        //.buildSegmentParamAttribute("name") //.buildSegmentParamAttribute("name")
        //.buildContextParameter("Test Event", contextBuilder.getSegmentParam());

        receiver.setReceiverContext(contextBuilder.getContext())

        //create the filter context for a filtering process.
        filterContextBuilder = new FilterContextBuilder()
        //filterContextBuilder.buildPredicateTerm("value", PredicateOperator.GREATER_THAN_OR_EQUAL, 18)
        //.buildContextEntry("TestEvent", new ComparisonContentEval())
        .buildEvaluatorContext(FilterType.COMPARISON).buildEPSFilter()
        //.buildFilterListener(new TestFilterObserver())
        .buildFilterContext()


        IEPSFilter filter = filterContextBuilder.getFilter()

        producer.setFilterContext(filterContextBuilder.getFilterContext())

        engineBuilder.setProducer(producer)

        //5: build and retrieve the modified engine and shoot some events.
        epsRuntimeClient.setPatternBuilder(patternBuilder)
		epsRuntimeClient.setEngineBuilder(engineBuilder)
        epsRuntimeClient.setAggregateContextBuilder(aggregateBuilder)
        epsRuntimeClient.setFilterContextBuilder(filterContextBuilder)
        epsRuntimeClient.setStoreContextBuilder(storeContextBuilder)
        epsRuntimeClient.setReceiverContextBuilder(contextBuilder)
        
		getBinding().putAt("epsRuntimeClient", epsRuntimeClient)

        engine = epsRuntimeClient.getEngine()
		
		getBinding().putAt("engine", engine)
    }