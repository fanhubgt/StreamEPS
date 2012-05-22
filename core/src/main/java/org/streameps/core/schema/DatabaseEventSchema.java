/*
 * ====================================================================
 *  SoftGene Technologies
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
package org.streameps.core.schema;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import org.streameps.core.IDataColumn;
import org.streameps.core.IDatabaseEvent;
import org.streameps.logger.ILogger;
import org.streameps.logger.LoggerUtil;

/**
 *
 * @author Frank Appiah
 */
public class DatabaseEventSchema implements ISchema {

    private Class clazz, eventClazz;
    private Map<String, ISchemaProperty> properties;
    private IDatabaseEvent databaseEvent;
    private String name;
    private ILogger logger = LoggerUtil.getLogger(Schema.class);

    public DatabaseEventSchema(Class clazz, IDatabaseEvent databaseEvent, String name) {
        this.eventClazz = clazz;
        properties = new HashMap<String, ISchemaProperty>();
        this.databaseEvent = databaseEvent;
        this.name = name;
    }

    public Class getClazz() {
        return eventClazz;
    }

    public Map<String, ISchemaProperty> getProperties() {
        for (Object key : databaseEvent.getDataColumns().keySet()) {
            IDataColumn dataColumn = (IDataColumn) databaseEvent.getDataColumns().get(key);
            buildDataColumn(dataColumn.getClass());
        }
        return properties;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public void setProperties(Map<String, ISchemaProperty> properties) {
        this.properties = properties;
    }

    private void buildMetaGetterProperty() {
        for (Method c : clazz.getMethods()) {
            if (!c.getName().substring(0, 3).equals("set")
                    || !c.getReturnType().equals(Void.TYPE)
                    || c.getParameterTypes().length != 1) {
                continue;
            }
            String getterMethodName = c.getName().replaceFirst("set", "get");

            String propertyName = "";
            if (c.getName().length() > 4) {
                propertyName = c.getName().substring(4);
            }
            propertyName = c.getName().substring(3, 4).toLowerCase() + propertyName;

            Type parameterType = c.getGenericParameterTypes()[0];

            Method getterMethod = null;
            try {
                getterMethod = clazz.getMethod(getterMethodName, new Class[]{});
            } catch (NoSuchMethodException nsme) {
            }
            ISchemaProperty iProperty = new SchemaProperty();
            iProperty.setName(propertyName);
            iProperty.setEventClazz(clazz);
            //iProperty.setMutatorMethod(c); todo: add a mutator method check if needed
            iProperty.setAccessorMethod(getterMethod);
            iProperty.setParameterType(parameterType);
            if (iProperty != null) {
                properties.put(propertyName, iProperty);
            }
        }
    }

    private void buildDataColumn(Class columnClazz) {
        clazz = columnClazz;
        buildMetaGetterProperty();
    }

    public void buildMetaSetterProperty() {
        for (Method c : clazz.getMethods()) {
            if (!c.getName().substring(0, 3).equals("get")
                    || c.getReturnType().equals(Void.TYPE)
                    || c.getParameterTypes().length != 1) {
                continue;
            }
            String getterMethodName = c.getName().replaceFirst("get", "set");

            String propertyName = "";
            if (c.getName().length() > 4) {
                propertyName = c.getName().substring(4);
            }
            propertyName = c.getName().substring(3, 4).toLowerCase() + propertyName;

            Type parameterType = c.getGenericParameterTypes()[0];

            Method getterMethod = null;
            try {
                getterMethod = clazz.getMethod(getterMethodName, new Class[]{});
            } catch (NoSuchMethodException nsme) {
            }
            ISchemaProperty iProperty = new SchemaProperty();
            iProperty.setName(propertyName);
            iProperty.setEventClazz(clazz);
            iProperty.setMutatorMethod(c);
            iProperty.setParameterType(parameterType);
            if (iProperty != null) {
                properties.put(propertyName, iProperty);
            }
        }
    }
}
