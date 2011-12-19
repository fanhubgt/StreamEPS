/*
 * ====================================================================
 *  StreamEPS Platform
 * 
 *  Copyright 2011.
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

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Interface for the executor manager.
 * 
 * @author  Frank Appiah
 */
public interface IEPSExecutorManager {

    /**
     * It returns the scheduledExecutor service.
     * @return The scheduled executor service.
     */
    public ScheduledExecutorService getExecutorService();

    /**
     * It returns the pool size for the scheduledExecutor service.
     * @return The pool size of the executor service.
     */
    public int getPoolSize();

    /**
     * It sets the scheduled executor service.
     * @param executorService The scheduled executor service.
     */
    public void setExecutorService(ScheduledExecutorService executorService);

    /**
     * It sets the pool size for the scheduledExecutor service.
     * @param poolSize The pool size of the executor service.
     */
    public void setPoolSize(int poolSize);

    /**
     * It returns the thread factory name.
     * @return A thread factory name.
     */
    public String getThreadFactoryName();

    /**
     * It sets the thread factory name.
     * @param threadFactoryName A thread factory name.
     */
    public void setThreadFactoryName(String threadFactoryName);

    /**
     * It executes a worker callable which is threaded in a scheduled executor
     * service.
     * @param <T> A parameterised class
     * @param workerCallable An instance of a worker callable.
     * @param timeUnit A time unit.
     * @return
     */
    public <T> void execute(IWorkerCallable<T> workerCallable, TimeUnit timeUnit);

    /**
     * It executes a worker callable which is threaded in a scheduled executor
     * service.
     *
     * @param <T> A parameterised class
     * @param workerCallable An instance of a worker callable.
     * @param delay Dalay time before execution.
     * @param timeUnit A time unit.
     * @return
     */
    public <T> void execute(IWorkerCallable<T> workerCallable, long delay, TimeUnit timeUnit);

    /**
     * It executes a worker callable which is threaded in a scheduled executor
     * service.
     * @param <T>
     * @param workerCallable
     */
    public <T> void execute(IWorkerCallable<T> workerCallable);

    /**
     * It submits a worker callable for execution.
     * @param <T> A parameterised class.
     * @param callable A worker callable.
     */
    public <T> void submit(IWorkerCallable<T> callable);
    
    /**
     *
     * @param <T>
     * @param workerCallable
     * @param initialDelay
     * @param period
     * @param timeUnit
     */
     public <T> void executeAtFixedRate(IWorkerCallable<T> workerCallable, long initialDelay, long period, TimeUnit timeUnit) ;
    /**
     * It returns the future result queue.
     * @return A future result queue.
     */
    public IFutureResultQueue getFutureResultQueue();

    /**
     * It sets the future result queue.
     * @param futureResultQueue A future result queue.
     */
    public void setFutureResultQueue(IFutureResultQueue futureResultQueue);

    /**
     * It returns the worker registry.
     * @return An instance of worker registry.
     */
    public IWorkerRegistry getWorkerRegistry();

    /**
     * It sets the worker registry.
     * 
     * @param workerRegistry An instance of worker registry.
     */
    public void setWorkerRegistry(IWorkerRegistry workerRegistry);

    /**
     * It returns the boolean indicator to show if some task has complete.
     * @return The indicator.
     */
    public boolean hasTaskComplete();
}
