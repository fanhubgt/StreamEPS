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
    private Map<Integer, ISchemaProperty> cacheMap = new HashMap<Integer, ISchemaProperty>();
    private Map<String, ISchemaProperty> strCacheMap = new HashMap<String, ISchemaProperty>();
    private Logger logger = Logger.getLogger(EventPropertyCache.class);

    public void putPropertyToCache(Integer count, ISchemaProperty trend) {
        cacheMap.put(count, trend);
        logger.info("Property added to map: value count=" + count);
    }

    public void putPropertyToCacheByString(String propName, ISchemaProperty trend) {
        strCacheMap.put(propName, trend);
        logger.info("Property added to map:" + propName);
    }

    public ISchemaProperty getPropertyFromCache(int position) {
        ISchemaProperty property = cacheMap.get(position);
        if (property != null) {
            return property;
        }
        throw new IllegalArgumentException();
    }

    public ISchemaProperty getPropertyFromCacheByString(String propName) {
        ISchemaProperty property = strCacheMap.get(propName);
        if (property != null) {
            return property;
        }
        throw new IllegalArgumentException();
    }

    /**
     * @return the trendMap
     */
    public Map<Integer, ISchemaProperty> getCacheMap() {
        return cacheMap;
    }
}
