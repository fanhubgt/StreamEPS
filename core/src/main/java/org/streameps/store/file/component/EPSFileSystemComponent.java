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
package org.streameps.store.file.component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import org.streameps.store.file.IEPSFileSystem;
import org.streameps.store.file.SupportedType;

/**
 *
 * @author Frank Appiah
 */
public class EPSFileSystemComponent implements IEPSFileSystemComponent {

    private String identifier;
    private List<IEPSFileSystem> fileSystems = new ArrayList<IEPSFileSystem>();
    private transient List<String> identifiers = new ArrayList<String>();
    private String persistLocation;
    private String componentName;

    public EPSFileSystemComponent(String identifier) {
        this.identifier = identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public void addEPSFileSystem(IEPSFileSystem systemComponent) {
        this.fileSystems.add(systemComponent);
        this.identifiers.add(systemComponent.getIdentifier());
    }

    public void addEPSFileSystemIfAbsent(IEPSFileSystem systemComponent) {
        boolean id = identifiers.contains(systemComponent.getIdentifier());
        if (id) {
            removeEPSFileSystem(systemComponent);
        }
        addEPSFileSystem(systemComponent);
    }

    public void removeEPSFileSystem(IEPSFileSystem systemComponent) {
        this.fileSystems.remove(systemComponent);
        this.identifiers.remove(systemComponent.getIdentifier());
    }

    public List<IEPSFileSystem> getFileSystems() {
        return this.fileSystems;
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        File file = new File(getSaveLocation() + getFileSystemComponentName() + "." + SupportedType.FSC.getType());
        file.setExecutable(true);
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream objectStream = new ObjectOutputStream(fos);
        objectStream.writeChars(identifier);
        objectStream.writeObject(getFileSystems());
        objectStream.close();
        fos.close();
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        File file = new File(getSaveLocation() + getFileSystemComponentName() + "." + SupportedType.FSC.getType());
        file.setExecutable(true);
        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream inputStream = new ObjectInputStream(fis);
        identifier = inputStream.readUTF();
        fileSystems = (List<IEPSFileSystem>) inputStream.readObject();
        for (IEPSFileSystem fileSystem : fileSystems) {
            identifiers.add(fileSystem.getIdentifier());
        }
        setFileSystems(fileSystems);
        inputStream.close();
        fis.close();
    }

    public void setSaveLocation(String locationPath) {
        this.persistLocation = locationPath;
    }

    public String getSaveLocation() {
        return this.persistLocation;
    }

    public void setFileSystems(List<IEPSFileSystem> systems) {
        this.fileSystems = systems;
    }

    public void setFileSystemComponentName(String name) {
        this.componentName = name;
    }

    public String getFileSystemComponentName() {
        return this.componentName;
    }

    public int compareTo(IEPSFileSystemComponent o) {
        return (getIdentifier().equalsIgnoreCase(o.getIdentifier())) ? 1 : 0;
    }

    @Override
    public int hashCode() {
        return identifier.length() + 
                identifiers.size() +
                fileSystems.size() +
                persistLocation.length() +
                componentName.length();
    }
}
