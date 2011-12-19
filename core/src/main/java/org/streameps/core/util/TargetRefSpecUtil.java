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
package org.streameps.core.util;

import org.streameps.logger.LoggerUtil;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import org.streameps.client.IClassSpec;
import org.streameps.client.IMethodSpec;
import org.streameps.client.IOutputTerminal;
import org.streameps.client.ITargetRefSpec;
import org.streameps.core.schema.ISchema;
import org.streameps.core.schema.ISchemaProperty;
import org.streameps.core.schema.Schema;
import org.streameps.engine.IAggregateContext;
import org.streameps.engine.IDeciderContext;
import org.streameps.engine.IFilterContext;
import org.streameps.logger.ILogger;

/**
 * A utility for the target reference specification implementation.
 * 
 * @author Frank Appiah
 */
public class TargetRefSpecUtil {

    private static ILogger logger = LoggerUtil.getLogger(TargetRefSpecUtil.class);

    public static void onTargetFilter(IFilterContext filterContext, IOutputTerminal terminal) {
        List<ITargetRefSpec> specs = terminal.getTargetReference();
        for (ITargetRefSpec spec : specs) {
            IClassSpec clazz = spec.getClazzSpec();
            IMethodSpec method = spec.getMethodSpec();
            try {
                invokeOnTerminal(method, clazz, filterContext, spec);
            } catch (IllegalArgumentException ex) {
                logger.error(ex.getMessage());
            } catch (IllegalAccessException ex) {
                logger.error(ex.getMessage());
            }
        }
    }

    public static void onTargetDecider(IDeciderContext deciderContext, IOutputTerminal terminal) {
        List<ITargetRefSpec> specs = terminal.getTargetReference();
        for (ITargetRefSpec spec : specs) {
            IClassSpec clazz = spec.getClazzSpec();
            IMethodSpec method = spec.getMethodSpec();
            try {
                invokeOnTerminal(method, clazz, deciderContext, spec);
            } catch (IllegalArgumentException ex) {
                logger.error(ex.getMessage());
            } catch (IllegalAccessException ex) {
                logger.error(ex.getMessage());
            }
        }
    }

     public static void onTargetAggregate(IAggregateContext aggregateContext, IOutputTerminal terminal) {
        List<ITargetRefSpec> specs = terminal.getTargetReference();
        for (ITargetRefSpec spec : specs) {
            IClassSpec clazz = spec.getClazzSpec();
            IMethodSpec method = spec.getMethodSpec();
            try {
                invokeOnTerminal(method, clazz, aggregateContext, spec);
            } catch (IllegalArgumentException ex) {
                logger.error(ex.getMessage());
            } catch (IllegalAccessException ex) {
                logger.error(ex.getMessage());
            }
        }
    }

    public static void invokeOnTerminal(IMethodSpec methodSpec, IClassSpec classSpec, Object filterContext, ITargetRefSpec spec)
            throws IllegalArgumentException, IllegalAccessException {
        ISchema schema = new Schema(classSpec.getClazz());
        Method method = methodSpec.getMethod();
        if (method == null) {
            schema.buildMetaSetterProperty();
            for (String key : schema.getProperties().keySet()) {
                if (key.equalsIgnoreCase(methodSpec.getMethodName())) {
                    ISchemaProperty schemaProperty = schema.getProperties().get(key);
                    method = schemaProperty.getMutatorMethod();
                }
            }
            if (method == null) {
                return;
            }
        }
        Class clazz = null;
        if (spec.getClientTerminal() == null) {
            clazz = classSpec.getClazz();
            try {
                spec.setClientTerminal(clazz.newInstance());
            } catch (InstantiationException ex) {
                logger.error(ex.getMessage());
            }
        }
        try {
            method.invoke(spec.getClientTerminal(), filterContext);
        } catch (InvocationTargetException ex) {
            logger.error(ex.getMessage());
        }
    }
    
}
