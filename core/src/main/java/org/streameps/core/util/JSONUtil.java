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
package org.streameps.core.util;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.EventObject;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.streameps.core.CurrentTimeEvent;
import org.streameps.core.DatabaseEvent;
import org.streameps.core.EncryptedEvent;
import org.streameps.core.MemoryEvent;
import org.streameps.core.PrimitiveEvent;
import org.streameps.core.StreamEvent;
import org.streameps.exception.ConvertorException;
import org.streameps.logger.ILogger;
import org.streameps.logger.LoggerUtil;

/**
 *
 * @author Frank Appiah
 */
public class JSONUtil {

    private static Set<Class<?>> convertTypes = new HashSet<Class<?>>();
    private static Set<Class<?>> deConvertTypes = new HashSet<Class<?>>();
    private static ILogger logger = LoggerUtil.getLogger(JSONUtil.class);

    {
        convertTypes.add(String.class);
        convertTypes.add(Double.class);
        convertTypes.add(Set.class);
        convertTypes.add(List.class);
        convertTypes.add(StreamEvent.class);
        convertTypes.add(EventObject.class);
        convertTypes.add(PrimitiveEvent.class);
        convertTypes.add(CurrentTimeEvent.class);
        convertTypes.add(DatabaseEvent.class);
        convertTypes.add(EncryptedEvent.class);
        convertTypes.add(MemoryEvent.class);

        /**
         * The supported de-convert types;
         */
        deConvertTypes.add(StreamEvent.class);
        deConvertTypes.add(EventObject.class);
        deConvertTypes.add(PrimitiveEvent.class);
        deConvertTypes.add(CurrentTimeEvent.class);
        deConvertTypes.add(DatabaseEvent.class);
        deConvertTypes.add(EncryptedEvent.class);
        deConvertTypes.add(MemoryEvent.class);

    }

    public static JSONObject createJsonObject(String strValue, String className, Object... options) throws ConvertorException {
        JSONObject jsono = null;
        try {
            jsono = new JSONObject(strValue);
            jsono.putOpt("classname", className);
            jsono.putOpt("options", options);
            return jsono;
        } catch (JSONException ex) {
            logger.error(ex.getMessage());
        }
        throw new ConvertorException("The object was not successful converting...");
    }

    public static Object createObject(JSONObject jsono) {
        return null;
    }

    public static Map<String, Object> createMapStructure(Object event) throws ConvertorException {
        Map<String, Object> map = new TreeMap<String, Object>();

        Class<?> clazz = event.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field f : fields) {
            if (!f.isAccessible()) {
                f.setAccessible(true);
            }
            try {
                String name = f.getName();
                Object result = f.get(event);
                if (f.getType().isPrimitive() || convertTypes.contains(f.getType())) {
                    map.put(name, result);
                } else if (f.getType().isAssignableFrom(Set.class)) {
                    Set set = (Set) result;
                    JSONArray array = createJSONSet(set);
                    map.put(name, array);
                } else if (f.getType().isArray()) {
                    List list = Arrays.asList(result);
                    JSONArray array = createJSONList(list);
                    map.put(name, array);
                } else if (f.getType().isAssignableFrom(List.class)) {
                    List list = (List) result;
                    map.put(name, createJSONList(list));
                }
            } catch (IllegalArgumentException ex) {
                logger.error(ex.getMessage());
            } catch (IllegalAccessException ex) {
                logger.error(ex.getMessage());
            }
        }
        return map;
    }

    public static JSONArray createJSONList(List list) {
        JSONArray array = new JSONArray(list);
        return array;
    }

    public static JSONArray createJSONObject(Map<String, Object> map) {
        JSONArray array = null;
        try {
            array = new JSONArray(map);
        } catch (JSONException ex) {
            logger.error(ex.getMessage());
        }
        return array;
    }

    public static JSONObject createJSONObject(Object object) {
        JSONObject array = null;
        array = new JSONObject(object);
        return array;
    }

    public static JSONArray createJSONSet(Set set) {
        JSONArray array = new JSONArray(set);
        return array;
    }

    public Set<Class<?>> getConvertTypes() {
        return convertTypes;
    }

    public Set<Class<?>> getDeConvertTypes() {
        return deConvertTypes;
    }
}
