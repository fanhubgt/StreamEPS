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

import java.util.ArrayList;
import java.util.List;
import org.streameps.store.file.component.IEPSFileComponent;

/**
 *
 * @author Frank Appiah
 */
public class EPSFileSystem implements IEPSFileSystem {

    private String dirPath;
    private String defaultName = "system";
    private List<String> identifiers = new ArrayList<String>();
    private String fileExtension;
    private SupportedType supportedType = SupportedType.FSC;
    private int fileSize = 0;
    private String identifier;
    private List<IEPSFileComponent> components = new ArrayList<IEPSFileComponent>();
    private transient IEPSFileSystemOp fileSystemOptor;
    private transient String serialVersionUID = "8944979181776933162";

    public EPSFileSystem() {
        fileSystemOptor = new EPSFileSystemOp(this);
    }

    public EPSFileSystem(String identifier) {
        this.identifier = identifier;
        fileSystemOptor = new EPSFileSystemOp(this);
    }

    public EPSFileSystem(String dirPath, String defaultName) {
        this.dirPath = dirPath;
        this.defaultName = defaultName;
        fileSystemOptor = new EPSFileSystemOp(this);
    }

    public EPSFileSystem(String dirPath, String defaultName, String fileExtension) {
        this.dirPath = dirPath;
        this.defaultName = defaultName;
        this.fileExtension = fileExtension;
        fileSystemOptor = new EPSFileSystemOp(this);
    }

    public void setDirPath(String filePath) {
        this.dirPath = filePath;
    }

    public String getDirPath() {
        return this.dirPath;
    }

    public List getIdentifiers() {
        return this.identifiers;
    }

    public int getFileSize() {
        return this.fileSize;
    }

    public void setFileExtension(String fileExt) {
        this.fileExtension = fileExt;
    }

    public String getFileExtension() {
        return this.fileExtension;
    }

    public void setDefaultName(String defaultName) {
        this.defaultName = defaultName;
    }

    public String getDefaultName() {
        return this.defaultName;
    }

    public void setSupportedType(SupportedType supportedType) {
        this.supportedType = supportedType;
    }

    public SupportedType getSupportedType() {
        return this.supportedType;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return this.identifier;
    }

//    public void writeExternal(ObjectOutput out) throws IOException {
//        File fscFile = null;
//        FileOutputStream fos = null;
//        ObjectOutputStream objectStream = null;
//        if (out == null) {
//            fscFile = new File(getDirPath() + File.separator + getDefaultName() + "-" + ai.incrementAndGet()
//                    + "." + SupportedType.FSC.getType());
//            fscFile.setExecutable(true);
//            fos = new FileOutputStream(fscFile);
//            objectStream = new ObjectOutputStream(fos);
//        } else {
//            objectStream = (ObjectOutputStream) out;
//        }
//        IEPSFileSystem fileSystem = new EPSFileSystem(identifier);
//        fileSystem.setDirPath(dirPath);
//        fileSystem.setDefaultName(defaultName);
//        fileSystem.setFileComponents(components);
//        fileSystem.setFileSize(fileSize);
//        objectStream.writeObject(fileSystem);
//        fos.close();
//        objectStream.close();
//    }
//
//    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
//        Object fsc = in.readObject();
//        IEPSFileSystem fileSystem = (IEPSFileSystem) fsc;
//        setSystemProperties(fileSystem);
//    }

    public void setSystemProperties(IEPSFileSystem fileSystem) {
        identifier = fileSystem.getIdentifier();
        supportedType = fileSystem.getSupportedType();
        defaultName = fileSystem.getDefaultName();
        dirPath = fileSystem.getDirPath();
        fileSize = fileSystem.getFileSize();

        for (Object input : fileSystem.getFileComponents()) {
            components.add((IEPSFileComponent) input);
        }
    }

    public List<IEPSFileComponent> getFileComponents() {
        if (components == null) {
            return new ArrayList<IEPSFileComponent>();
        }
        return components;
    }

    public void setFileComponents(List components) {
        this.components = components;
    }

    public void setFileSize(int size) {
        this.fileSize = size;
    }

    public void setFileSystemOp(IEPSFileSystemOp fileSystemOptor) {
        this.fileSystemOptor = fileSystemOptor;
    }

    public IEPSFileSystemOp getFileSystemOptor() {
        return this.fileSystemOptor;
    }

    @Override
    public int hashCode() {
        return super.hashCode()
                + serialVersionUID.hashCode() + 31
                + identifier.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return (identifier.equalsIgnoreCase(((IEPSFileSystem)obj).getIdentifier()));
    }
    
}
