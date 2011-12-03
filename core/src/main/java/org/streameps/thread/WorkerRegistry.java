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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Frank Appiah
 */
public class WorkerRegistry implements IWorkerRegistry {

    private IWorkerCallable workerCallable;
    private List<IWorkerCallable> callables;
    private Map<String, IWorkerCallable> mapCallables;

    public WorkerRegistry() {
        callables = new ArrayList<IWorkerCallable>();
        mapCallables = new HashMap<String, IWorkerCallable>();
    }

    public void addWorkerCallable(IWorkerCallable workerCallable) {
        this.callables.add(workerCallable);
        this.mapCallables.put(workerCallable.getIdentifier(), workerCallable);
        this.workerCallable = workerCallable;
    }

    public void removeWorkerCallable(String workerCallableID) {
        this.mapCallables.remove(workerCallableID);
    }

    public List<IWorkerCallable> getWorkerList() {
        return (List<IWorkerCallable>) this.mapCallables.values();
    }

    public void setWorkerList(List<IWorkerCallable> callables) {
        this.callables = callables;
        for (IWorkerCallable callable : callables) {
            mapCallables.put(callable.getIdentifier(), callable);
        }
    }

    public Map<String, IWorkerCallable> getMapCallables() {
        return this.mapCallables;
    }

    public int getCount() {
        return mapCallables.size();
    }
}
