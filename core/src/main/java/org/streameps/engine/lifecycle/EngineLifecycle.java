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
package org.streameps.engine.lifecycle;

import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Frank Appiah
 */
public class EngineLifecycle<T> implements IEngineLifecycle<T> {

    private EngineStartedListener engineStartedListener;
    private EngineStoppedListener engineStoppedListener;
    private EngineRestartedListener engineRestartedListener;
    private IStartedEvent startedEvent;
    private IStoppedEvent stoppedEvent;
    private IRestartedEvent restartedEvent;
    private Map<String, T> properties;

    public EngineLifecycle() {
        properties = new TreeMap<String, T>();
    }

    public EngineLifecycle(EngineStartedListener engineStartedListener, EngineStoppedListener engineStoppedListener, EngineRestartedListener engineRestartedListener, IStartedEvent startedEvent, IStoppedEvent stoppedEvent, Map<String, T> properties) {
        this.engineStartedListener = engineStartedListener;
        this.engineStoppedListener = engineStoppedListener;
        this.engineRestartedListener = engineRestartedListener;
        this.startedEvent = startedEvent;
        this.stoppedEvent = stoppedEvent;
        this.properties = properties;
        properties = new TreeMap<String, T>();
    }

    public void onStart() {
        engineStartedListener.onStart(startedEvent);
    }

    public void onStop() {
        engineStoppedListener.onStop(stoppedEvent);
    }

    public void restart() {
        engineRestartedListener.onRestart(restartedEvent);
    }

    public void setLifecycleProperties(Map<String, T> properties) {
        this.properties = properties;
    }

    public Map<String, T> getLifecycleProperties() {
        return this.properties;
    }

    public void setEngineStartedListener(EngineStartedListener engineStartedListener) {
        this.engineStartedListener = engineStartedListener;
    }

    public void setEngineStoppedListener(EngineStoppedListener engineStoppedListener) {
        this.engineStoppedListener = engineStoppedListener;
    }

    public void setEngineRestartedListener(EngineRestartedListener engineRestartedListener) {
        this.engineRestartedListener = engineRestartedListener;
    }

    public EngineRestartedListener getEngineRestartedListener() {
        return engineRestartedListener;
    }

    public EngineStartedListener getEngineStartedListener() {
        return engineStartedListener;
    }

    public EngineStoppedListener getEngineStoppedListener() {
        return engineStoppedListener;
    }

}
