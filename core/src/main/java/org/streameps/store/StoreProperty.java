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
package org.streameps.store;

import org.streameps.core.IStoreIdentity;
import org.streameps.store.file.SupportedType;

/**
 *
 * @author Frank Appiah
 */
public class StoreProperty implements IStoreProperty {

    private IStoreIdentity storeIdentity;
    private String componentIdentifier;
    private String systemIdentifier;
    private String dirPath;
    private SupportedType supportedType;

    public StoreProperty() {
    }

    public StoreProperty(IStoreIdentity storeIdentity, String componentIdentifier, String systemIdentifier, String dirPath) {
        this.storeIdentity = storeIdentity;
        this.componentIdentifier = componentIdentifier;
        this.systemIdentifier = systemIdentifier;
        this.dirPath = dirPath;
    }

    public void setStoreIdentity(IStoreIdentity storeIdentity) {
        this.storeIdentity = storeIdentity;
    }

    public IStoreIdentity getStoreIdentity() {
        return this.storeIdentity;
    }

    public void setComponentIdentifier(String compIdentifier) {
        this.componentIdentifier = compIdentifier;
    }

    public String getComponentIdentifier() {
        return this.componentIdentifier;
    }

    public void setSystemIdentifier(String identifier) {
        this.systemIdentifier = identifier;
    }

    public String getSystemIdentifier() {
        return this.systemIdentifier;
    }

    public void setPersistLocation(String dirPath) {
        this.dirPath = dirPath;
    }

    public String getPersistLocation() {
        return this.dirPath;
    }

    public void setSupportType(SupportedType supportedType) {
        this.supportedType=supportedType;
    }

    public SupportedType getSupportType() {
        return this.supportedType;
    }
    
}
