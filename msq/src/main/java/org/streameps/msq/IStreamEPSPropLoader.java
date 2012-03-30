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
package org.streameps.msq;

import java.util.Properties;
import org.streameps.msq.hornetq.PerformanceParams;

/**
 * An interface for loading the properties to define a context specific to
 * the queue server.
 * 
 * @author  Frank Appiah
 */
public interface IStreamEPSPropLoader {

    /**
     * It configures the performance parameters with the specified location to the
     * configuration property file.
     * @param location The path to the configuration file.
     */
    public void configurePerformanceParams(String location);

    /**
     * It configures the performance parameters with the specified location to the
     * configuration property file.
     */
    public void configurePerformanceParams();

    /**
     * It returns the instance of performance parameter created.
     * @return An instance of the performance parameter.
     */
    public PerformanceParams getPerfParams();

    /**
     * It logs the performance parameter to the screen.
     * @param params The performance parameter to the screen.
     */
    public void logParams(PerformanceParams params);

    /**
     * It returns the path to the stream property configuration file.
     * @return The path to the stream property file.
     */
    public String getStreamLocation();

    /**
     * It populates the performance parameter instance.
     * @param props The property container for the loaded configuration file.
     */
    public void populateParams(Properties props);

    /**
     * It sets an instance of the performance parameter for the queue server.
     * @param perfParams An instance of the performance parameter.
     */
    public void setPerfParams(PerformanceParams perfParams);

    /**
     * It sets the stream property location.
     * @param streamLocation The path to the stream property file.
     */
    public void setStreamLocation(String streamLocation);
}
