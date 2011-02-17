/*
 * ====================================================================
 * StreamEPS Platform
 *
 * Distributed under the Modified BSD License.
 * Copyright notice: The copyright for this software and a full listing
 * of individual contributors are as shown in the packaged copyright.txt
 * file.
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  - Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  - Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  - Neither the name of the ORGANIZATION nor the names of its contributors may
 *    be used to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE
 * USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * =============================================================================
 */
package org.streameps.core;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Frank Appiah
 */
public final class MemoryProbe implements IMemoryProbe {

    private long maxMemoryUsed = 0;
    private long totalMemory = 0;
    private Runtime runtime = null;
    private long timeUsedInChecking = 0;
    private Map<IMemoryProbeListener, Long> listeners;
    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

    public MemoryProbe() {
        super();
        reset();
        runtime = Runtime.getRuntime();
        listeners = new HashMap<IMemoryProbeListener, Long>();
    }

    public void reset() {
        totalMemory = 0;
        maxMemoryUsed = 0;
        timeUsedInChecking = 0;
    }

    public void startMonitor() {
        reset();
        for (final IMemoryProbeListener l : listeners.keySet()) {
            executorService.scheduleWithFixedDelay(new Runnable() {

                public void run() {
                    checkMemoryUsed();
                    MemoryEvent event = new MemoryEvent(maxMemoryUsed, totalMemory, timeUsedInChecking, listeners.get(l));
                    l.onMemoryChange(event);
                    reset();
                }
            }, 0L, listeners.get(l), TimeUnit.SECONDS);
        }
    }

    public void checkMemoryUsed() {
        long ts = System.currentTimeMillis();
        if (totalMemory <= 0) {
            totalMemory = runtime.totalMemory();
        }
        long memoryUsed = totalMemory - runtime.freeMemory();
        if (memoryUsed > maxMemoryUsed) {
            maxMemoryUsed = memoryUsed;
        }
        timeUsedInChecking += (System.currentTimeMillis() - ts);
    }

    public void addMemoryProbeListner(IMemoryProbeListener listener, long delay) {
        listeners.put(listener, delay);
    }

    public void removeMemoryProbeListener(IMemoryProbeListener listener) {
        listeners.remove(listener);
    }
}
