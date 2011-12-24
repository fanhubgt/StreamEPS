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

import org.streameps.store.IStoreProperty;
import org.streameps.store.file.manager.IEPSFileManager;

/**
 *
 * @author Frank Appiah
 */
public class EPSFile<T> implements IEPSFile<T> {

    private String fileName;
    private T data;
    private String filePath;
    private long lastModified;
    private boolean hidden;
    private String identifier;
    private String extension;
    private transient volatile IEPSFileManager fileManager;
    private transient volatile IStoreProperty storeProperty;

    public EPSFile() {
    }

    public EPSFile(IEPSFileManager fileManager, IStoreProperty storeProperty) {
        this.fileManager = fileManager;
        this.storeProperty=storeProperty;
    }

    public EPSFile(String fileName, T data, String filePath, long lastModified, boolean hide, String identifier) {
        this.fileName = fileName;
        this.data = data;
        this.filePath = filePath;
        this.lastModified = lastModified;
        this.hidden = hide;
        this.identifier = identifier;
    }

    public EPSFile(String fileName, T data, String filePath, long lastModified, boolean hidden, String identifier, String extension, IEPSFileManager fileManager) {
        this.fileName = fileName;
        this.data = data;
        this.filePath = filePath;
        this.lastModified = lastModified;
        this.hidden = hidden;
        this.identifier = identifier;
        this.extension = extension;
        this.fileManager = fileManager;
    }

    public EPSFile(String fileName, T file, String filePath, long lastModified, boolean isHidden) {
        this.fileName = fileName;
        this.data = file;
        this.filePath = filePath;
        this.lastModified = lastModified;
        this.hidden = isHidden;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setData(T fileData) {
        this.data = fileData;
    }

    public T getData() {
        return this.data;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public void setHidden(boolean hide) {
        this.hidden = hide;
    }

    public boolean getHidden() {
        return this.hidden;
    }

    public void setLastModified(long lastModified) {
        this.lastModified = lastModified;
    }

    public long getLastModified() {
        return this.lastModified;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public void setExtension(String name) {
        this.extension = name;
    }

    public String getExtension() {
        return this.extension;
    }

    public void setFileManager(IEPSFileManager fileManager) {
        this.fileManager = fileManager;
    }

    public void save() {
        fileManager.addEPSFile(storeProperty.getComponentIdentifier(), storeProperty.getSystemIdentifier(), this);
    }

    public void setStoreProperty(IStoreProperty storeProperty) {
        this.storeProperty=storeProperty;
    }

    public IStoreProperty getStoreProperty() {
        return this.storeProperty;
    }
}
