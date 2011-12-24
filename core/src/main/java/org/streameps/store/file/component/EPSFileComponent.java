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

import java.util.Map;
import java.util.TreeMap;
import org.streameps.store.file.IEPSFile;
import org.streameps.store.file.SupportedType;

/**
 *
 * @author Frank Appiah
 */
public class EPSFileComponent implements IEPSFileComponent {

    private String identifier;
    private Map<String, IEPSFile> files = new TreeMap<String, IEPSFile>();
    private String fileExt = SupportedType.OMP.getType();
    private String componentName;
    private String saveLocation;

    public EPSFileComponent() {
        fileExt = SupportedType.OMP.getType();
    }

    public EPSFileComponent(String identifier) {
        this.identifier = identifier;
        fileExt = SupportedType.OMP.getType();
    }

    public EPSFileComponent(String identifier, String filExt) {
        this.identifier = identifier;
        this.fileExt = filExt;
        fileExt = SupportedType.OMP.getType();
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public void setEPSFiles(Map<String, IEPSFile> epsFiles) {
        this.files = epsFiles;
    }

    public Map<String, IEPSFile> getEPSFiles() {
        return this.files;
    }

    public void setFileExtension(String fileExt) {
        this.fileExt = fileExt;
    }

    public String getFileExtension() {
        return this.fileExt;
    }

    public void addEPSFile(IEPSFile epsFile) {
        getEPSFiles().put(epsFile.getIdentifier(), epsFile);
    }

    public void removeEPSFile(IEPSFile epsFile) {
        getEPSFiles().remove(epsFile.getIdentifier());
    }

//    public void writeExternal(ObjectOutput out) throws IOException {
//        File file = new File(getSaveLocation() + File.pathSeparator + getComponentName() + "." + SupportedType.OMP.getType());
//        file.setExecutable(true);
//        FileOutputStream fos = new FileOutputStream(file);
//        ObjectOutputStream objectStream = new ObjectOutputStream(fos);
//        objectStream.writeChars(identifier);
//        objectStream.writeChars(saveLocation);
//        objectStream.writeChars(componentName);
//        objectStream.writeObject(files);
//        objectStream.close();
//        fos.close();
//    }

//    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
//        identifier = in.readUTF();
//        saveLocation = in.readUTF();
//        componentName = in.readUTF();
//        files = (Map<String, IEPSFile>) in.readObject();
//        setEPSFiles(files);
//        in.close();
//    }

    public void setSaveLocation(String locationPath) {
        this.saveLocation = locationPath;
    }

    public String getSaveLocation() {
        return this.saveLocation;
    }

    public void setComponentName(String name) {
        this.componentName = name;
    }

    public String getComponentName() {
        return this.componentName;
    }

    @Override
    public int hashCode() {
        return (componentName.hashCode() + identifier.hashCode() + saveLocation.hashCode()) * 31;
    }

    public int compareTo(IEPSFileComponent o) {
        return (getIdentifier().equalsIgnoreCase(o.getIdentifier())) ? 1 : 0;
    }

    @Override
    public boolean equals(Object obj) {
        return (getIdentifier().equalsIgnoreCase(((IEPSFileComponent) obj).getIdentifier()));
    }

    public void addEPSFileIfAbsent(IEPSFile epsFile) {
        if (getEPSFiles().containsKey(epsFile.getIdentifier())) {
            removeEPSFile(epsFile);
        }
        addEPSFile(epsFile);
    }
    
}
