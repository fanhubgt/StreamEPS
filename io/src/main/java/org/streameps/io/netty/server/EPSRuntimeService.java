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
package org.streameps.io.netty.server;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import org.streameps.core.EPSRuntimeClient;
import org.streameps.core.IEPSRuntimeClient;
import org.streameps.dispatch.DispatcherService;
import org.streameps.dispatch.IDispatcherService;
import org.streameps.engine.AbstractEPSEngine;
import org.streameps.engine.AuditEventStore;
import org.streameps.engine.EPSForwarder;
import org.streameps.engine.EPSProducer;
import org.streameps.engine.HistoryStore;
import org.streameps.engine.IAggregateContext;
import org.streameps.engine.IEPSDecider;
import org.streameps.engine.IEPSEngine;
import org.streameps.engine.IEPSForwarder;
import org.streameps.engine.IEPSProducer;
import org.streameps.engine.IEPSReceiver;
import org.streameps.engine.IFilterContext;
import org.streameps.engine.IHistoryStore;
import org.streameps.engine.IPatternChain;
import org.streameps.engine.StoreType;
import org.streameps.engine.builder.AggregateContextBuilder;
import org.streameps.engine.builder.EngineBuilder;
import org.streameps.engine.builder.FilterContextBuilder;
import org.streameps.engine.builder.PatternBuilder;
import org.streameps.engine.builder.ReceiverContextBuilder;
import org.streameps.engine.builder.StoreContextBuilder;
import org.streameps.io.netty.EPSCommandLineProp;
import org.streameps.io.netty.server.listener.ChannelTerminalListener;
import org.streameps.io.netty.server.listener.PatternMatchListener;
import org.streameps.io.netty.server.listener.PatternUnMatchListener;
import org.streameps.io.netty.server.service.ServerService;
import org.streameps.logger.ILogger;
import org.streameps.logger.LoggerUtil;
import org.streameps.processor.AggregatorListener;
import org.streameps.processor.pattern.IBasePattern;
import org.streameps.processor.pattern.listener.NullPatternMatchListener;
import org.streameps.processor.pattern.NullPatternPE;
import org.streameps.store.IStoreProperty;
import org.streameps.store.StoreProperty;
import org.streameps.store.file.IEPSFileSystem;
import org.streameps.thread.EPSExecutorManager;

/**
 *
 * @author Frank Appiah
 */
public class EPSRuntimeService implements IEPSRuntimeService {

    private static IEPSRuntimeClient sRuntime;
    private static IEPSRuntimeService service;
    private static IEPSDecider decider;
    private static IEPSReceiver receiver;
    private static IEPSEngine engine;
    private static IEPSForwarder forwarder;
    private static IEPSProducer producer;
    private static ILogger logger = LoggerUtil.getLogger(EPSRuntimeService.class);
    private static Map<String, IEngineChannel> engineMap;
    private static boolean enginePerClient = false;

    public EPSRuntimeService() {
        engineMap = new TreeMap<String, IEngineChannel>();
    }

    public static void setEnginePerClient(boolean perClient) {
        enginePerClient = perClient;
    }

    public static boolean isEnginePerClient() {
        return enginePerClient;
    }

    public EPSRuntimeService(IEPSRuntimeClient rtime) {
        sRuntime = rtime;
        EPSRuntimeClient.setInstance(sRuntime);
    }

    public void setEPSRuntime(IEPSRuntimeClient client) {
        sRuntime = client;
        EPSRuntimeClient.setInstance(sRuntime);
    }

    public IEPSRuntimeClient getEPSRuntime() {
        return EPSRuntimeClient.getInstance();
    }

    public static IEPSRuntimeService getInstance() {
        if (service == null) {
            service = (IEPSRuntimeService) new EPSRuntimeService();
        }
        return service;
    }

    public EPSRuntimeService(IEPSDecider dec, IEPSReceiver rec, IEPSEngine eng) {
        decider = dec;
        receiver = rec;
        engine = eng;
    }

    public IEPSDecider getDecider() {
        return decider;
    }

    public IEPSEngine getEngine() {
        return engine;
    }

    public IEPSForwarder getForwarder() {
        return forwarder;
    }

    public void setForwarder(IEPSForwarder forwarder) {
        EPSRuntimeService.forwarder = forwarder;
    }

    public IEPSReceiver getReceiver() {
        return receiver;
    }

    public void setDecider(IEPSDecider dec) {
        decider = dec;
    }

    public void setReceiver(IEPSReceiver rec) {
        receiver = rec;
    }

    public void setEngine(IEPSEngine eng) {
        engine = eng;
    }

    public static IEPSRuntimeClient getClientInstance() {
        try {
            return createInstance();
        } catch (RuntimeServiceException ex) {
            logger.error(ex.getMessage());
        }
        return null;
    }

    public static void setClientInstance(IEPSRuntimeClient client) {
        sRuntime = client;
    }

    public static IEPSRuntimeClient createInstance(IEPSEngine engine, String ipAddress) throws RuntimeServiceException {
        //1: initialize the engine, decider, reciever and producer.

        try {
            if (engine != null) {
                logger.info("Reconfiguring the engine preference...");
                engine.setExecutorManager(new EPSExecutorManager());
                //core pool size, thread factory name
                int poolSize = Integer.parseInt(System.getProperty(EPSCommandLineProp.CORE_POOL_SIZE));
                String threadFactoryName = System.getProperty(EPSCommandLineProp.THREAD_FACTORY_NAME);
                ((AbstractEPSEngine) engine).getExecutorManager().setPoolSize(poolSize);
                ((AbstractEPSEngine) engine).getExecutorManager().setThreadFactoryName(threadFactoryName);
                ((AbstractEPSEngine) engine).getDomainManager().getExecutorManager().setPoolSize(poolSize);
                ((AbstractEPSEngine) engine).getDomainManager().getExecutorManager().setThreadFactoryName(threadFactoryName);

                String location = System.getProperty(EPSCommandLineProp.STORE_LOCATION);
                IStoreProperty isp = new StoreProperty("comp", IEPSFileSystem.DEFAULT_SYSTEM_ID, location);
                IHistoryStore historyStore = new HistoryStore(StoreType.FILE,
                        new AuditEventStore(isp, engine.getExecutorManager()));
                engine.getEPSReceiver().setHistoryStore(historyStore);
                engine.getDecider().setDeciderContextStore(historyStore);

                //asynchronous, dispatcher size, save on decide flag.
                engine.setAsynchronous(Boolean.parseBoolean(System.getProperty(EPSCommandLineProp.ASYNCHRONOUS)));
                int dispatcherSize = Integer.parseInt(System.getProperty(EPSCommandLineProp.DISPATCHER_SIZE));
                engine.setDispatcherSize(dispatcherSize);
                boolean saveonReceive = Boolean.parseBoolean(System.getProperty(EPSCommandLineProp.SAVE_ON_RECEIVE));
                engine.setSaveOnReceive(saveonReceive);

                //sequence size, queue flag, save on decide flag.
                int sequenceCount = Integer.parseInt(System.getProperty(EPSCommandLineProp.SEQUENCE_SIZE));
                ((AbstractEPSEngine) engine).setSequenceCount(sequenceCount);
                boolean queued = Boolean.parseBoolean(System.getProperty(EPSCommandLineProp.QUEUE));
                ((AbstractEPSEngine) engine).setEventQueued(queued);
                boolean saveOnDecide = Boolean.parseBoolean(System.getProperty(EPSCommandLineProp.SAVE_ON_DECIDE));
                ((AbstractEPSEngine) engine).setSaveOnDecide(saveOnDecide);

                //dispatcher service, initial delay, periodic delay
                IDispatcherService dispatcherService = new DispatcherService();
                dispatcherService.setExecutionManager(((AbstractEPSEngine) engine).getDomainManager().getExecutorManager());
                ((AbstractEPSEngine) engine).setDispatcherSize(dispatcherSize);
                long periodicDelay = Long.parseLong(System.getProperty(EPSCommandLineProp.PERIODIC_DELAY));
                ((AbstractEPSEngine) engine).setPeriodicDelay(periodicDelay);
                long initialDelay = Long.parseLong(System.getProperty(EPSCommandLineProp.INITIAL_DELAY, "0"));
                ((AbstractEPSEngine) engine).setInitialDelay(initialDelay);
                ((AbstractEPSEngine) engine).setDispatcherService(dispatcherService);

                engine.getDecider().getProducer().getForwarder().setChannelOutputTerminal(new ChannelTerminalListener());
                engine.getDecider().setAggregateListener(ServerService.getInstance().getAggregateService().getAggregatorListener());
                engine.getDecider().getProducer().setDeciderContextListener(ServerService.getInstance().getDeciderService().getDeciderContextListener());
                engine.getDecider().getProducer().setAggregatorListener(ServerService.getInstance().getAggregateService().getAggregatorListener());
                engine.getDecider().getPatternChain().addPatternMatchedListener(ServerService.getInstance().getPatternService().getPatternServiceListener());
                engine.getDecider().getPatternChain().addPatternUnMatchedListener(ServerService.getInstance().getPatternService().getPatternUnMatchListener());

                if (saveonReceive) {
                    Thread thread = new Thread((AuditEventStore) historyStore.getHistoryStore());
                    thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {

                        public void uncaughtException(Thread t, Throwable e) {
                            logger.error(e.getMessage());
                            e.printStackTrace();
                        }
                    });
                    thread.start();
                    //thread.setDaemon(true);
                    thread.setName("Store");
                }
//
//                EPSRuntimeClient.getInstance().getEngineBuilder().setProducer(producer);
//                EPSRuntimeClient.getInstance().getEngineBuilder().setReceiver(receiver);
//                EPSRuntimeClient.getInstance().getEngineBuilder().setEngine(engine);
//                EPSRuntimeClient.getInstance().getEngineBuilder().setProducer(producer);
                EPSRuntimeClient.getInstance().setEngine(engine);
                sRuntime = EPSRuntimeClient.getInstance();
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return sRuntime;
    }

    public static IEPSRuntimeClient createInstance() throws RuntimeServiceException {
        //1: initialize the engine, decider, reciever and producer.
        if (sRuntime == null) {
            producer = new EPSProducer();

            forwarder = new EPSForwarder();
            forwarder.setChannelOutputTerminal(new ChannelTerminalListener());
            producer.setForwarder(forwarder);

            EngineBuilder engineBuilder;
            {
                //2: set the engine, decider and receiver properties.
                if (decider == null || engine == null || receiver == null) {
                    throw new RuntimeServiceException("Either the decider or receiver or engine is not properly set.");
                }
                engineBuilder = new EngineBuilder(decider, engine, receiver);
                engineBuilder.setProducer(producer);
            }
            producer.setDeciderContextListener(ServerService.getInstance().getDeciderService().getDeciderContextListener());
            producer.setAggregatorListener(ServerService.getInstance().getAggregateService().getAggregatorListener());

            AggregateContextBuilder aggregatebuilder = new AggregateContextBuilder();
            aggregatebuilder.setAggregatorListener(
                    ServerService.getInstance().getAggregateService().getAggregatorListener());

            String location = "/store";

            StoreContextBuilder storeContextBuilder = new StoreContextBuilder();

            IStoreProperty isp = new StoreProperty("comp", IEPSFileSystem.DEFAULT_SYSTEM_ID, location);
            IHistoryStore historyStore = new HistoryStore(StoreType.FILE,
                    new AuditEventStore(isp, engine.getExecutorManager()));

            storeContextBuilder.addStoreProperty(isp).addHistoryStore(historyStore);

            engineBuilder.buildAuditStore(storeContextBuilder.getHistoryStore());

            //set the properties: sequence size, asychronous flag, queue flag, saveonReceive flag, saveonDecide flag.
            engineBuilder.buildProperties(20, false, true, false, true);
            engineBuilder.buildExecutorManagerProperties(2, "EPS");
            engineBuilder.buildDispatcher(3, 0, 1, TimeUnit.MILLISECONDS, new DispatcherService());

            PatternBuilder patternBuilder = new PatternBuilder(new NullPatternPE()).buildPatternMatchListener(new NullPatternMatchListener(), new NullPatternMatchListener());

            //add the pattern 1 detector built to the engine/decider.
            engineBuilder.buildPattern(patternBuilder.getBasePattern());

            ReceiverContextBuilder receiverContextBuilder = new ReceiverContextBuilder();

            receiver.setReceiverContext(receiverContextBuilder.getContext());

            FilterContextBuilder filterContextBuilder = new FilterContextBuilder();
            producer.setFilterContext(filterContextBuilder.getFilterContext());

            EPSRuntimeClient.getInstance().setAggregateContextBuilder(aggregatebuilder);
            EPSRuntimeClient.getInstance().setEngineBuilder(engineBuilder);
            EPSRuntimeClient.getInstance().setFilterContextBuilder(filterContextBuilder);
            EPSRuntimeClient.getInstance().setReceiverContextBuilder(receiverContextBuilder);
            EPSRuntimeClient.getInstance().setPatternBuilder(patternBuilder);
            EPSRuntimeClient.getInstance().setStoreContextBuilder(storeContextBuilder);
            sRuntime = EPSRuntimeClient.getInstance();
        } else {
            EPSRuntimeClient.getInstance().getEngineBuilder().setProducer(producer);
            EPSRuntimeClient.getInstance().getEngineBuilder().setReceiver(receiver);
            EPSRuntimeClient.getInstance().getEngineBuilder().setProducer(producer);
        }

        return EPSRuntimeClient.getInstance();
    }

    public static IEPSRuntimeClient createInstance(IEPSEngine eng, IEPSReceiver rec, IEPSDecider dec)
            throws RuntimeServiceException {
        engine = eng;
        receiver = rec;
        decider = dec;
        return createInstance();
    }

    public static void setFilterContext(IFilterContext context) {
        producer.setFilterContext(context);
        synchronized (producer) {
            EPSRuntimeClient.getInstance().getEngineBuilder().setProducer(producer);
            EPSRuntimeClient.getInstance().restartEngine();
            sRuntime = EPSRuntimeClient.getInstance();
        }
    }

    public static void setAggregateContext(IAggregateContext aggregateContext, AggregatorListener aggregatorListener) {
        producer.setAggregateContext(aggregateContext);
        producer.setAggregatorListener(aggregatorListener);
        synchronized (producer) {
            EPSRuntimeClient.getInstance().getEngineBuilder().setProducer(producer);
            EPSRuntimeClient.getInstance().getAggregateContextBuilder().setAggregateContext(aggregateContext);
            EPSRuntimeClient.getInstance().getEngineBuilder().setAggregatedEnabled(aggregateContext, aggregatorListener, true);
            EPSRuntimeClient.getInstance().restartEngine();
            sRuntime = EPSRuntimeClient.getInstance();
        }
    }

    public static void setPatternContext(IBasePattern basePattern) {
        EPSRuntimeClient.getInstance().getEngineBuilder().buildPattern(basePattern);
        EPSRuntimeClient.getInstance().restartEngine();
        sRuntime = EPSRuntimeClient.getInstance();
    }

    public static void setPatternContext(IBasePattern basePattern, PatternMatchListener listener, PatternUnMatchListener unMatchListener) {
        EPSRuntimeClient.getInstance().getEngineBuilder().buildPattern(basePattern, listener, unMatchListener);
        EPSRuntimeClient.getInstance().restartEngine();
        sRuntime = EPSRuntimeClient.getInstance();
    }

    public static void setPatternContext(IPatternChain patternChaiin) {
        EPSRuntimeClient.getInstance().getEngineBuilder().setPatternChain(patternChaiin);
        EPSRuntimeClient.getInstance().restartEngine();
        sRuntime = EPSRuntimeClient.getInstance();
    }

    public static void setStoreHistory(IHistoryStore historyStore) {
        EPSRuntimeClient.getInstance().getStoreContextBuilder().addHistoryStore(historyStore);
        EPSRuntimeClient.getInstance().getEngine().getDecider().getHistoryStores().add(historyStore);
    }

    public static IEPSRuntimeClient getRuntimeParent() {
        return sRuntime;
    }

    public static void setEngineMap(Map<String, IEngineChannel> engines) {
        engineMap = engines;
    }

    public static Map<String, IEngineChannel> getEngineChannelMap() {
        return engineMap;
    }
}
