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
import java.util.concurrent.Delayed;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Frank Appiah
 */
public class CallableAdapter<T> implements Runnable {

    private IWorkerCallable<T> callable;
    private IFutureResultQueue futureResultQueue;

    private T result;

    public CallableAdapter(IWorkerCallable<T> callable) {
        this.callable = callable;
    }

    public CallableAdapter(IWorkerCallable<T> callable, IFutureResultQueue futureResultQueue) {
        this.callable = callable;
        this.futureResultQueue = futureResultQueue;
    }

    public void run() {
        try {
            result = callable.call();
            ScheduledFuture resultFuture= new ScheduledFuture<T>() {

                public long getDelay(TimeUnit unit) {
                    throw new UnsupportedOperationException("Not supported yet.");
                }

                public int compareTo(Delayed o) {
                    throw new UnsupportedOperationException("Not supported yet.");
                }

                public boolean cancel(boolean mayInterruptIfRunning) {
                    throw new UnsupportedOperationException("Not supported yet.");
                }

                public boolean isCancelled() {
                    return false;
                }

                public boolean isDone() {
                    return true;
                }

                public T get() throws InterruptedException, ExecutionException {
                    return result;
                }

                public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
                   return result;
                }
            };
            getFutureResultQueue().addResultUnit(new ResultUnit((Long)new Date().getTime(), resultFuture));
           
        } catch (Exception ex) {
            Logger.getLogger(CallableAdapter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public T getResult() {
        return result;
    }

    public IFutureResultQueue getFutureResultQueue() {
        return futureResultQueue;
    }

}
