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
package org.streameps.core.util;

import io.s4.schema.Schema;
import io.s4.schema.Schema.Property;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import org.apache.log4j.Logger;
import org.streameps.core.EventPropertyCache;

/**
 *
 * @author Development Team
 */
public class SchemaUtil {

    private static EventPropertyCache cache = new EventPropertyCache();
    private static Logger logger = Logger.getLogger(SchemaUtil.class);

    public static Property getProperty(Object event, String name) {
        Schema schema = new Schema(event.getClass());
        Map<String, Property> schMap = schema.getProperties();
        cache.putPropertyToCacheByString(name, schMap.get(name));
        return schMap.get(name);
    }

    public static Object getPropertyValue(Object event, String name) {
        try {
            Property p = getProperty(event, name);
            Object value = p.getGetterMethod().invoke(event);
            //System.out.println("Property name:"+name+"---Value:"+value);
            return value;
        } catch (IllegalAccessException ex) {
            logger.error(ex);
        } catch (IllegalArgumentException ex) {
            logger.error(ex);
        } catch (InvocationTargetException ex) {
            logger.error(ex);
        }
        return null;
    }

    public static Object setPropertyValue(Object event, String name, Object value) {
        try {
            Property p = getProperty(event, name);
            Object result = p.getSetterMethod().invoke(event, value);
            return result;
        } catch (IllegalAccessException ex) {
            logger.error(ex);
        } catch (IllegalArgumentException ex) {
            logger.error(ex);
        } catch (InvocationTargetException ex) {
            logger.error(ex);
        }
        return null;
    }

    public static EventPropertyCache getCache() {
        return cache;
    }
}
