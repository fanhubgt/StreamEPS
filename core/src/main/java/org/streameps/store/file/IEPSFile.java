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
package org.streameps.store.file;

import java.io.Serializable;

/**
 * Interface for an EPS file.
 * @param T The container for a collection of events or event data from a stream.
 * 
 * @author  Frank Appiah
 */
public interface IEPSFile<T> extends Serializable {

    /**
     * It sets the identifier for the EPS file.
     * @param identifier The identifier for the EPS file.
     */
    public void setIdentifier(String identifier);

    /**
     * It returns the identifier for the EPS file.
     * @return The identifier for the EPS file.
     */
    public String getIdentifier();

    /**
     * It sets the file name of the EPS file.
     * @param fileName The file name for the file.
     */
    public void setFileName(String fileName);

    /**
     * It returns the file name of the EPS file.
     * @return The file name for the file.
     */
    public String getFileName();

    /**
     * It sets the file for the EPS file.
     * @param file
     */
    public void setData(T file);

    /**
     * It returns the file for the EPS file.
     * @return The file for the EPS file.
     */
    public T getData();

    /**
     * It sets the file path for the EPS file.
     * @param filePath The file path of the EPS file.
     */
    public void setFilePath(String filePath);

    /**
     * It returns the file path for the EPS file.
     * @return The file path of the EPS file.
     */
    public String getFilePath();

    /**
     * It sets the hidden property of the file.
     * @param hide A hidden attribute indicator.
     */
    public void setHidden(boolean hide);

    /**
     * It returns the hidden property of the file.
     * @return A hidden attribute indicator.
     */
    public boolean getHidden();

    /**
     * It sets when the file was last modified.
     * @param lastModified The modified date.
     */
    public void setLastModified(long lastModified);

    /**
     * It returns when the file was last modified.
     * @return The modified date.
     */
    public long getLastModified();

    /**
     * It sets the extension of the file.
     * @param name The extension of the file.
     */
    public void setExtension(String name);

    /**
     * It returns the extension of the file.
     * @return The extension of the file.
     */
    public String getExtension();
}
