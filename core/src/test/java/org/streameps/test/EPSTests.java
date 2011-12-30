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
package org.streameps.test;

import java.util.logging.Level;
import java.util.logging.Logger;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestResult;
import junit.framework.TestSuite;
import junit.textui.ResultPrinter;

/**
 *
 * @author Frank Appiah
 */
public class EPSTests extends TestCase {

    private static TestSuite suite;

    public EPSTests(String testName) {
        super(testName);
    }

    public static Test suite() {
        suite = new TestSuite("EPSTestSuite");
        return suite;
    }

    public void testEngine() {
        try {
            TestResult result = createResult();
            result.addListener(new ResultPrinter(System.out));
            result.startTest(suite);
            suite();
            suite.addTest(new AggregationTest("EPS Aggregation"));
            suite.run(result);
            suite.addTest(new AvgPatternTest("Avg Pattern"));
            suite.addTest(new EngineTest("Engine"));
            suite.addTest(new TemporalEngineTest("Fixed Interval Engine"));
            suite.addTest(new NumberUtilTest("Number Utility"));
            suite.addTest(new IDEventGeneratorTest("ID Generator"));
            suite.addTest(new TrendPatternTest("EPS Trend"));
            suite.addTest(new HighestPatternTest("Highest Pattern"));
            suite.addTest(new FileStoreTest("File store"));
            suite.run(result);
            run();
        } catch (Throwable ex) {
            Logger.getLogger(EPSTests.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
