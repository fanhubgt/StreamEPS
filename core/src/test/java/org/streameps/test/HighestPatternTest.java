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
package org.streameps.test;

import org.streameps.core.TestEvent;
import junit.framework.TestCase;
import org.streameps.processor.pattern.HighestSubsetPE;
import org.streameps.processor.pattern.IPatternParameter;
import org.streameps.processor.pattern.PatternParameter;

/**
 *
 * @author Frank Appiah
 */
public class HighestPatternTest extends TestCase {

    public HighestPatternTest(String testName) {
        super(testName);
    }

    public void testHighestSubsetPE() {
        System.out.println("========================================");
        System.out.println("Starting----Highest Subset");
        HighestSubsetPE<TestEvent> hspe = new HighestSubsetPE<TestEvent>();
        hspe.getMatchListeners().add(new TestPatternMatchListener());
        hspe.getUnMatchListeners().add(new TestUnPatternMatchListener());
        IPatternParameter pp0=new PatternParameter("value", 6);
        hspe.setDispatcher(new TestDispatcher());
        hspe.getParameters().add(pp0);
        for (int i = 0; i < 10; i++) {
            TestEvent event = new TestEvent("e" + i, (double) i);
            hspe.processEvent(event);
        }
        hspe.output();
         System.out.println("Ending----Highest Subset");
         System.out.println("========================================");
    }

    
}
