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
package org.streameps.loader;

import org.streameps.core.IEPSRuntimeClient;

/**
 * This interface is used to load a groovy file from the physical location.
 * This normally is used to load configuration files.
 * 
 * @author  Frank Appiah
 */
public interface GroovyLoader<T extends Object> {

    /**
     *  It sets the configure path to the groovy file.
     * @param path The configure path to the groovy file.
     */
    public void setConfigurePath(String path);

    /**
     * It sets the file name of the groovy file.
     * @param filename The file name of the groovy file.
     */
    public void setFileName(String filename);

    /**
     * It returns the file name of the groovy file.
     * @return The file name of the groovy file.
     */
    public String getFileName();

    /**
     * It returns the configure path to the groovy file.
     * @return The configuration path for the groovy file.
     */
    public String getConfigurePath();

    /**
     * It configures/loads the groovy file for the configuration.
     * @return The instance of the class loaded.s
     */
    public T loadClassBindings(String name);

    /**
     * 
     * @return
     */
    public IEPSRuntimeClient getRuntimeClient();
}
