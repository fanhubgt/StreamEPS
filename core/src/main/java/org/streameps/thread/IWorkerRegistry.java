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

import java.util.List;
import java.util.Map;

/**
 * Interface for the worker unit registry.
 * 
 * @author  Frank Appiah
 */
public interface IWorkerRegistry {

    /**
     * It adds a worker callable to the worker registry.
     * @param workerCallable The worker callable.
     */
    public void addWorkerCallable(IWorkerCallable workerCallable);

    /**
     * It removes the worker callable from the worker registry.
     * @param workerCallable The worker callable.
     */
    public void removeWorkerCallable(String workerCallableID);

    /**
     * It returns a list of worker callable.
     * @return A list of worker callable.
     */
    public List<IWorkerCallable> getWorkerList();

    /**
     * It sets a list of worker callable.
     * @param callables A list of worker callable.
     */
    public void setWorkerList(List<IWorkerCallable> callables);

    /**
     * It returns a map(identifier, worker callable).
     * @return A map of worker callable.
     */
    public Map<String, IWorkerCallable> getMapCallables();

    /**
     * It returns the number of worker callable.
     * @return The number of worker callable.
     */
    public int getCount();
}
