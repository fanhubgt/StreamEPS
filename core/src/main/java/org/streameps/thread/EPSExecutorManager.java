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
package org.streameps.thread;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import org.streameps.core.util.RuntimeUtil;

/**
 *
 * @author Frank Appiah
 */
public class EPSExecutorManager implements IEPSExecutorManager {

    private ScheduledExecutorService executorService;
    private IEPSThreadFactory epsThreadFactory;
    private int poolSize = 1;
    private String threadFactoryName = "EPS";
    private IFutureResultQueue futureResultQueue;
    private IWorkerRegistry workerRegistry;
    private boolean aTaskComplete = false;

    public EPSExecutorManager() {
        epsThreadFactory = new EPSThreadFactory(threadFactoryName);
        executorService = Executors.newScheduledThreadPool(poolSize, epsThreadFactory);
        futureResultQueue = new FutureResultQueue();
        workerRegistry = new WorkerRegistry();
    }

    public EPSExecutorManager(int poolSize) {
        this.poolSize = poolSize;
        epsThreadFactory = new EPSThreadFactory(threadFactoryName);
        executorService = Executors.newScheduledThreadPool(poolSize, epsThreadFactory);
        futureResultQueue = new FutureResultQueue();
        workerRegistry = new WorkerRegistry();
    }

    public ScheduledExecutorService getExecutorService() {
        return executorService;
    }

    public void setExecutorService(ScheduledExecutorService executorService) {
        this.executorService = executorService;
    }

    public void setPoolSize(int poolSize) {
        if (poolSize > RuntimeUtil.getNumberProcessors()) {
            poolSize = RuntimeUtil.getNumberProcessors();
        } else {
            this.poolSize = poolSize;
        }
        executorService = Executors.newScheduledThreadPool(poolSize, epsThreadFactory);
    }

    public int getPoolSize() {
        return poolSize;
    }

    public void setThreadFactoryName(String threadFactoryName) {
        this.threadFactoryName = threadFactoryName;
    }

    public String getThreadFactoryName() {
        return threadFactoryName;
    }

    public <T> void submit(IWorkerCallable<T> callable) {
        Future<T> future = getExecutorService().submit(callable);
        getFutureResultQueue().addResultUnit(new ResultUnit<T>(new Date().getTime(), (ScheduledFuture<T>) future));
    }

    public <T> void execute(IWorkerCallable<T> workerCallable, TimeUnit timeUnit) {
        ScheduledFuture<T> scheduledFuture = getExecutorService().schedule(workerCallable, 0, timeUnit);
        getFutureResultQueue().addResultUnit(new ResultUnit<T>(new Date().getTime(), scheduledFuture));
        workerRegistry.addWorkerCallable(workerCallable);
    }

    public <T> void execute(IWorkerCallable<T> workerCallable, long delay, TimeUnit timeUnit) {
        ScheduledFuture<T> scheduledFuture = getExecutorService().schedule(workerCallable, delay, timeUnit);
        getFutureResultQueue().addResultUnit(new ResultUnit<T>(new Date().getTime(), scheduledFuture));
        workerRegistry.addWorkerCallable(workerCallable);
    }

    public <T> void execute(IWorkerCallable<T> workerCallable) {
        Future<T> scheduledFuture = getExecutorService().submit(workerCallable);
        getFutureResultQueue().addResultUnit(new ResultUnit<T>(new Date().getTime(), (ScheduledFuture<T>) scheduledFuture));
        workerRegistry.addWorkerCallable(workerCallable);
    }

    public <T> void executeAtFixedRate(IWorkerCallable<T> workerCallable, long initialDelay, long period, TimeUnit timeUnit) {
        ScheduledFuture<?> scheduledFuture = getExecutorService().scheduleAtFixedRate
                (new CallableAdapter(workerCallable, getFutureResultQueue()),
                initialDelay, period, timeUnit);
        getFutureResultQueue().addResultUnit(new ResultUnit(new Date().getTime(), scheduledFuture));
        workerRegistry.addWorkerCallable(workerCallable);
    }

     public <T> void executeWithFixedDelay(IWorkerCallable<T> workerCallable, long initialDelay, long period, TimeUnit timeUnit) {
        ScheduledFuture<?> scheduledFuture = getExecutorService().scheduleWithFixedDelay
                (new CallableAdapter(workerCallable, getFutureResultQueue()),
                initialDelay, period, timeUnit);
        getFutureResultQueue().addResultUnit(new ResultUnit(new Date().getTime(), scheduledFuture));
        workerRegistry.addWorkerCallable(workerCallable);
    }

    public void shutdown() {
        getExecutorService().shutdown();
    }

    public IFutureResultQueue getFutureResultQueue() {
        return futureResultQueue;
    }

    public void setFutureResultQueue(IFutureResultQueue futureResultQueue) {
        this.futureResultQueue = futureResultQueue;
    }

    public IWorkerRegistry getWorkerRegistry() {
        return workerRegistry;
    }

    public void setWorkerRegistry(IWorkerRegistry workerRegistry) {
        this.workerRegistry = workerRegistry;
    }

    public boolean hasTaskComplete() {
        boolean complete = false;
        if (getFutureResultQueue().getSize() > 0) {
            for (Object unit : getFutureResultQueue().getResultQueue().toArray()) {
                complete |= ((ResultUnit) unit).getScheduledFuture().isDone();
            }
        }
        aTaskComplete |= complete;
        return this.aTaskComplete;
    }
}
