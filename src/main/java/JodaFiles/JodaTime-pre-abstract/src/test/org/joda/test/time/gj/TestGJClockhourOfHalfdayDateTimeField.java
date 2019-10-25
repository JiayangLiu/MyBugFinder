/*
 * Joda Software License, Version 1.0
 *
 *
 * Copyright (c) 2001-2004 Stephen Colebourne.  
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer. 
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 *    if any, must include the following acknowledgment:  
 *       "This product includes software developed by the
 *        Joda project (http://www.joda.org/)."
 *    Alternately, this acknowledgment may appear in the software itself,
 *    if and wherever such third-party acknowledgments normally appear.
 *
 * 4. The name "Joda" must not be used to endorse or promote products
 *    derived from this software without prior written permission. For
 *    written permission, please contact licence@joda.org.
 *
 * 5. Products derived from this software may not be called "Joda",
 *    nor may "Joda" appear in their name, without prior written
 *    permission of the Joda project.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE JODA AUTHORS OR THE PROJECT
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Joda project and was originally 
 * created by Stephen Colebourne <scolebourne@joda.org>. For more
 * information on the Joda project, please see <http://www.joda.org/>.
 */
package org.joda.test.time.gj;

import junit.framework.TestSuite;

import org.joda.test.time.AbstractTestDateTimeField;
import org.joda.time.DateTimeField;
import org.joda.time.chrono.GJChronology;
/**
 * This class is a Junit unit test for the date time field.
 *
 * @author Stephen Colebourne
 */
public class TestGJClockhourOfHalfdayDateTimeField extends AbstractTestDateTimeField {


    public TestGJClockhourOfHalfdayDateTimeField(String name) {
        super(name);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }
    public static TestSuite suite() {
        return new TestSuite(TestGJClockhourOfHalfdayDateTimeField.class);
    }
    protected void setUp() throws Exception {
        super.setUp();
    }
    protected void tearDown() throws Exception {
        super.tearDown();
    }
    
    protected long getAddWrappedResult(long millis, int addWrapped) {
        if (addWrapped == 0) {
            return millis;
        }
        int val = getCalendarValue(millis);
        if (val == 12) {
            val = 0;
        }
        int max = 11;
        int min = 0;
        int add = val + addWrapped;
        while (add > max || add < min) {
            if (addWrapped >= 0) {
                add = add - (max - min) - 1;
            } else {
                add = add + (max - min) + 1;
            }
        }
        return millis + ((add - val) * getUnitSize());
    }
    
    protected String getFieldName() {
        return "clockhourOfHalfday";
    }
    protected DateTimeField getField() {
        return GJChronology.getInstance(getZone()).clockhourOfHalfday();
    }
    protected int getMinimumValue() {
        return 1;
    }
    protected int getMaximumValue() {
        return 12;
    }
    protected int getCalendarValue(long millis) {
        millis = millis + getZone().getOffset(millis);
        int val = 0;
        if (millis >= 0) {
            val = ((int) ((millis / (60 * 60 * 1000)) % 24)) % 12;
        } else {
            val = ((int) 23 + (int) (((millis + 1) / (60 * 60 * 1000)) % 24)) % 12;
        }
        return (val == 0 ? 12 : val);
    }
    protected long getUnitSize() {
        return 60 * 60 * 1000; // 1 hour
    }

}
