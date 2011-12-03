/*
 * ====================================================================
 *  StreamEPS Platform
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
 *  =============================================================================
 */
package org.streameps.core;


import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.streameps.core.schema.ISchemaProperty;

/**
 * A cache for properties of the events for further use to enhance performance
 * from serialising the event every time we need a property of an event
 * instance.
 * 
 * @author Frank Appiah
 */
public class EventPropertyCache implements IEventPropertyCache {

    private static final long serialVersionUID = 3991890316133375487L;
    private SoftReference<Map<Integer, ISchemaProperty>> cacheMap ;
    private SoftReference<Map<String, ISchemaProperty>> strCacheMap;
    private ReferenceQueue<Map<Integer, ISchemaProperty>> cacheReferenceQueue;
    private ReferenceQueue<Map<String, ISchemaProperty>> strCacheReferenceQueue;
    private Logger logger = Logger.getLogger(EventPropertyCache.class);

    public EventPropertyCache() {
        cacheReferenceQueue=new ReferenceQueue<Map<Integer, ISchemaProperty>>();
        strCacheReferenceQueue=new ReferenceQueue<Map<String, ISchemaProperty>>();
        cacheMap = new
            SoftReference<Map<Integer, ISchemaProperty>>
            (new HashMap<Integer, ISchemaProperty>(),cacheReferenceQueue);
        strCacheMap = new
                SoftReference<Map<String, ISchemaProperty>>
                (new HashMap<String, ISchemaProperty>(),strCacheReferenceQueue);
    }
    
    public void putPropertyToCache(Integer count, ISchemaProperty trend) {
        cacheMap.get().put(count, trend);
        logger.info("Property added to map: value count=" + count);
    }

    public void putPropertyToCacheByString(String propName, ISchemaProperty trend) {
        strCacheMap.get().put(propName, trend);
        logger.info("Property added to map:" + propName);
    }

    public ISchemaProperty getPropertyFromCache(int position) {
        ISchemaProperty property = cacheMap.get().get(position);
        if (property != null) {
            return property;
        }
        throw new IllegalArgumentException();
    }

    public ISchemaProperty getPropertyFromCacheByString(String propName) {
        ISchemaProperty property = strCacheMap.get().get(propName);
        if (property != null) {
            return property;
        }
        throw new IllegalArgumentException();
    }

    /**
     * @return the trendMap
     */
    public Map<Integer, ISchemaProperty> getCacheMap() {
        return cacheMap.get();
    }

    public ReferenceQueue<Map<Integer, ISchemaProperty>> getCacheReferenceQueue() {
        return cacheReferenceQueue;
    }

    public ReferenceQueue<Map<String, ISchemaProperty>> getStrCacheReferenceQueue() {
        return strCacheReferenceQueue;
    }

    
}
