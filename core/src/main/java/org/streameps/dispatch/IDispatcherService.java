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
package org.streameps.dispatch;

import java.util.Queue;
import java.util.concurrent.TimeUnit;
import org.streameps.engine.IEPSEngine;
import org.streameps.thread.IEPSExecutorManager;

/**
 * Interface for a dispatcher service specification.
 * 
 * @author  Frank Appiah
 */
public interface IDispatcherService {

    /**
     * This will in turn call all the registered dispatchers in this service.
     */
    public void dispatch();

    /**
     * It registers a new external dispatcher.
     * 
     * @param dispatchable external dispatcher.
     * @return list of external dispatchers.
     */
    public Queue<Dispatchable> registerDispatcher(Dispatchable dispatchable);

    /**
     * It sets the event processing engine for the dispatcher.
     * @param engine An instance of the engine.
     */
    public void setEngine(IEPSEngine engine);

    /**
     * It returns the EPS execution manager.
     * @return An instance of EPSExecutorManager.
     */
    public IEPSExecutorManager getExecutorManager();

    /**
     * It sets the EPS execution manager.
     * @param executorManager  An instance of EPSExecutorManager.
     */
    public void setExecutorManager(IEPSExecutorManager executorManager);

    /**
     * It sets the dispatcher size count.
     * @param dispatchableSize The number of dispatchables.
     */
    public void setDispatchableSize(int dispatchableSize);

    /**
     * It returns the dispatcher size count.
     * @return The number of dispatchables.
     */
    public int getDispatchableSize();

    /**
     * The initial delay for the schedule executor manager.
     * @return The initial delay.
     */
    public long getIntialDelay();

    /**
     * The period of the schedule executor manager.
     * @return The period.
     */
    public long getPeriod();

    /**
     * It returns the time unit.
     * @return The time unit.
     */
    public TimeUnit getTimeUnit();

    /**
     * The initial delay for the schedule executor manager.
     * @param intialDelay The initial delay
     */
    public void setIntialDelay(long intialDelay);

    /**
     * It sets the period for the schedule executor manager.
     * @param period The period.
     */
    public void setPeriod(long period);

    /**
     * It sets the time unit for the schedule executor manager.
     * @param timeUnit The time unit.
     */
    public void setTimeUnit(TimeUnit timeUnit);
}
