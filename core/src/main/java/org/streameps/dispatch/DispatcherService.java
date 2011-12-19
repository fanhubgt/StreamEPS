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

import java.lang.ref.WeakReference;
import java.util.Date;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import org.streameps.util.IDUtil;
import org.streameps.logger.LoggerUtil;
import org.streameps.engine.IEPSEngine;
import org.streameps.logger.ILogger;
import org.streameps.logger.JdkLoggerFactory;
import org.streameps.thread.EPSExecutorManager;
import org.streameps.thread.IEPSExecutorManager;
import org.streameps.thread.IWorkerCallable;

/**
 * Implementation of the dispatcher service specification.
 *
 * @author Frank Appiah
 */
public class DispatcherService implements IDispatcherService {

    private LinkedBlockingQueue<Dispatchable> dispatchables = new LinkedBlockingQueue<Dispatchable>();
    private WeakReference<IEPSEngine> engine;
    private WeakReference<IEPSExecutorManager> executorManager=new WeakReference<IEPSExecutorManager>(new EPSExecutorManager());
    private long intialDelay = 0, period = 0;
    private TimeUnit timeUnit = TimeUnit.MILLISECONDS;
    private int dispatchableSize = 1, tdispatchableSize = 1;
    private boolean dispatchAllowed = true;
    private ILogger logger = LoggerUtil.getLogger(DispatcherService.class.getName(), new JdkLoggerFactory());

    public DispatcherService() {
    }

    public void dispatch() {

        executorManager.get().execute(new IWorkerCallable<Object>() {

            public String getIdentifier() {
                return IDUtil.getUniqueID(new Date().toString());
            }

            public void setIdentifier(String name) {
            }

            public Object call() throws Exception {
                Dispatchable d = dispatchables.poll();
                if (d != null) {
                    d.dispatch();
                }
                return null;
            }
        }, intialDelay, timeUnit);
        logger.debug("Dispatcher service has dispatch a worker unit.");

    }

    public Queue<Dispatchable> registerDispatcher(Dispatchable dispatchable) {
        dispatchables.offer(dispatchable);
        doDispatch();
        if (tdispatchableSize < 1 && getExecutorManager().hasTaskComplete()) {
            tdispatchableSize += 1;
        }
        return dispatchables;
    }

    private void doDispatch() {
        if (tdispatchableSize >= 1) {
            dispatch();
            tdispatchableSize -= 1;
        }
    }

    public void setDispatchableSize(int dispatchableSize) {
        this.dispatchableSize = dispatchableSize;
        this.tdispatchableSize = dispatchableSize;
    }

    public void setEngine(IEPSEngine engine) {
        this.engine = new WeakReference<IEPSEngine>(engine);
        logger.debug("Dispatcher engine is set.");
    }

    public IEPSExecutorManager getExecutorManager() {
        return executorManager.get();
    }

    public void setExecutionManager(IEPSExecutorManager executorManager) {
        this.executorManager = new WeakReference<IEPSExecutorManager>(executorManager);
        logger.debug("The executor manager is set.");
    }

    public int getDispatchableSize() {
        return this.dispatchableSize;
    }

    public void setDispatchAllowed(boolean dispatchAllowed) {
        this.dispatchAllowed = dispatchAllowed;
    }

    public void setIntialDelay(long intialDelay) {
        this.intialDelay = intialDelay;
    }

    public void setPeriod(long period) {
        this.period = period;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

    public long getIntialDelay() {
        return intialDelay;
    }

    public long getPeriod() {
        return period;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }
}
