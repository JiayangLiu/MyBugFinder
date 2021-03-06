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
package org.joda.time;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Locale;
import java.util.TimeZone;

import org.joda.time.chrono.ISOChronology;

import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * This class is a Junit unit test for MutableDuration.
 *
 * @author Stephen Colebourne
 */
public class TestMutablePeriod_Basics extends TestCase {
    // Test in 2002/03 as time zones are more well known
    // (before the late 90's they were all over the place)

    private static final DateTimeZone PARIS = DateTimeZone.getInstance("Europe/Paris");
    private static final DateTimeZone LONDON = DateTimeZone.getInstance("Europe/London");
    
    long y2002days = 365 + 365 + 366 + 365 + 365 + 365 + 366 + 365 + 365 + 365 + 
                     366 + 365 + 365 + 365 + 366 + 365 + 365 + 365 + 366 + 365 + 
                     365 + 365 + 366 + 365 + 365 + 365 + 366 + 365 + 365 + 365 +
                     366 + 365;
    long y2003days = 365 + 365 + 366 + 365 + 365 + 365 + 366 + 365 + 365 + 365 + 
                     366 + 365 + 365 + 365 + 366 + 365 + 365 + 365 + 366 + 365 + 
                     365 + 365 + 366 + 365 + 365 + 365 + 366 + 365 + 365 + 365 +
                     366 + 365 + 365;
    
    // 2002-06-09
    private long TEST_TIME_NOW =
            (y2002days + 31L + 28L + 31L + 30L + 31L + 9L -1L) * DateTimeConstants.MILLIS_PER_DAY;
            
    // 2002-04-05
    private long TEST_TIME1 =
            (y2002days + 31L + 28L + 31L + 5L -1L) * DateTimeConstants.MILLIS_PER_DAY
            + 12L * DateTimeConstants.MILLIS_PER_HOUR
            + 24L * DateTimeConstants.MILLIS_PER_MINUTE;
        
    // 2003-05-06
    private long TEST_TIME2 =
            (y2003days + 31L + 28L + 31L + 30L + 6L -1L) * DateTimeConstants.MILLIS_PER_DAY
            + 14L * DateTimeConstants.MILLIS_PER_HOUR
            + 28L * DateTimeConstants.MILLIS_PER_MINUTE;
    
    private DateTimeZone originalDateTimeZone = null;
    private TimeZone originalTimeZone = null;
    private Locale originalLocale = null;

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }

    public static TestSuite suite() {
        return new TestSuite(TestMutablePeriod_Basics.class);
    }

    public TestMutablePeriod_Basics(String name) {
        super(name);
    }

    protected void setUp() throws Exception {
        DateTimeUtils.setCurrentMillisFixed(TEST_TIME_NOW);
        originalDateTimeZone = DateTimeZone.getDefault();
        originalTimeZone = TimeZone.getDefault();
        originalLocale = Locale.getDefault();
        DateTimeZone.setDefault(LONDON);
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/London"));
        Locale.setDefault(Locale.UK);
    }

    protected void tearDown() throws Exception {
        DateTimeUtils.setCurrentMillisSystem();
        DateTimeZone.setDefault(originalDateTimeZone);
        TimeZone.setDefault(originalTimeZone);
        Locale.setDefault(originalLocale);
        originalDateTimeZone = null;
        originalTimeZone = null;
        originalLocale = null;
    }

    //-----------------------------------------------------------------------
    public void testTest() {
        assertEquals("2002-06-09T00:00:00.000Z", new Instant(TEST_TIME_NOW).toString());
        assertEquals("2002-04-05T12:24:00.000Z", new Instant(TEST_TIME1).toString());
        assertEquals("2003-05-06T14:28:00.000Z", new Instant(TEST_TIME2).toString());
    }

    //-----------------------------------------------------------------------
    public void testGetPeriodType() {
        MutablePeriod test = new MutablePeriod();
        assertEquals(PeriodType.getAllType(), test.getPeriodType());
    }

    public void testGetIsPrecise() {
        MutablePeriod test = new MutablePeriod(123L);
        assertEquals(true, test.isPrecise());
        test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        assertEquals(false, test.isPrecise());
    }

    public void testGetMethods() {
        MutablePeriod test = new MutablePeriod();
        assertEquals(0, test.getYears());
        assertEquals(0, test.getMonths());
        assertEquals(0, test.getDays());
        assertEquals(0, test.getWeeks());
        assertEquals(0, test.getDays());
        assertEquals(0, test.getHours());
        assertEquals(0, test.getMinutes());
        assertEquals(0, test.getSeconds());
        assertEquals(0, test.getMillis());
    }

    public void testEqualsHashCode() {
        MutablePeriod test1 = new MutablePeriod(123L);
        MutablePeriod test2 = new MutablePeriod(123L);
        assertEquals(true, test1.equals(test2));
        assertEquals(true, test2.equals(test1));
        assertEquals(true, test1.equals(test1));
        assertEquals(true, test2.equals(test2));
        assertEquals(true, test1.hashCode() == test2.hashCode());
        assertEquals(true, test1.hashCode() == test1.hashCode());
        assertEquals(true, test2.hashCode() == test2.hashCode());
        
        MutablePeriod test3 = new MutablePeriod(321L);
        assertEquals(false, test1.equals(test3));
        assertEquals(false, test2.equals(test3));
        assertEquals(false, test3.equals(test1));
        assertEquals(false, test3.equals(test2));
        assertEquals(false, test1.hashCode() == test3.hashCode());
        assertEquals(false, test2.hashCode() == test3.hashCode());
        
        assertEquals(false, test1.equals("Hello"));
        assertEquals(true, test1.equals(new MockMutablePeriod(123L)));
        assertEquals(false, test1.equals(new Period(123L, PeriodType.getDayHourType())));
    }
    
    class MockMutablePeriod extends AbstractPeriod {
        public MockMutablePeriod(long value) {
            super(value, null);
        }
        protected PeriodType checkPeriodType(PeriodType type) {
            return PeriodType.getAllType();
        }
    }

    //-----------------------------------------------------------------------
    public void testSerialization() throws Exception {
        MutablePeriod test = new MutablePeriod(123L);
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(test);
        byte[] bytes = baos.toByteArray();
        oos.close();
        
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(bais);
        MutablePeriod result = (MutablePeriod) ois.readObject();
        ois.close();
        
        assertEquals(test, result);
    }

    //-----------------------------------------------------------------------
    public void testAddTo1() {
        long expected = TEST_TIME_NOW;
        expected = ISOChronology.getInstance().years().add(expected, 1);
        expected = ISOChronology.getInstance().months().add(expected, 2);
        expected = ISOChronology.getInstance().weeks().add(expected, 3);
        expected = ISOChronology.getInstance().days().add(expected, 4);
        expected = ISOChronology.getInstance().hours().add(expected, 5);
        expected = ISOChronology.getInstance().minutes().add(expected, 6);
        expected = ISOChronology.getInstance().seconds().add(expected, 7);
        expected = ISOChronology.getInstance().millis().add(expected, 8);
        
        MutablePeriod test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        long added = test.addTo(TEST_TIME_NOW, 1);
        assertEquals(expected, added);
    }
    
    public void testAddTo2() {
        long expected = TEST_TIME_NOW;
        expected = ISOChronology.getInstance().years().add(expected, -2);
        expected = ISOChronology.getInstance().months().add(expected, -4);
        expected = ISOChronology.getInstance().weeks().add(expected, -6);
        expected = ISOChronology.getInstance().days().add(expected, -8);
        expected = ISOChronology.getInstance().hours().add(expected, -10);
        expected = ISOChronology.getInstance().minutes().add(expected, -12);
        expected = ISOChronology.getInstance().seconds().add(expected, -14);
        expected = ISOChronology.getInstance().millis().add(expected, -16);
        
        MutablePeriod test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        long added = test.addTo(TEST_TIME_NOW, -2);
        assertEquals(expected, added);
    }
    
    public void testAddTo3() {
        long expected = TEST_TIME_NOW;
        expected = ISOChronology.getInstanceUTC().years().add(expected, -2);
        expected = ISOChronology.getInstanceUTC().months().add(expected, -4);
        expected = ISOChronology.getInstanceUTC().weeks().add(expected, -6);
        expected = ISOChronology.getInstanceUTC().days().add(expected, -8);
        expected = ISOChronology.getInstanceUTC().hours().add(expected, -10);
        expected = ISOChronology.getInstanceUTC().minutes().add(expected, -12);
        expected = ISOChronology.getInstanceUTC().seconds().add(expected, -14);
        expected = ISOChronology.getInstanceUTC().millis().add(expected, -16);
        
        MutablePeriod test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8, PeriodType.getAllType(ISOChronology.getInstanceUTC()));
        long added = test.addTo(TEST_TIME_NOW, -2);
        assertEquals(expected, added);
    }
    
    public void testAddTo4() {
        long expected = TEST_TIME_NOW;
        MutablePeriod test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        long added = test.addTo(TEST_TIME_NOW, 0);
        assertEquals(expected, added);
    }
    
    public void testAddTo5() {
        long expected = TEST_TIME_NOW + 100L;
        MutablePeriod test = new MutablePeriod(100L);
        long added = test.addTo(TEST_TIME_NOW, 1);
        assertEquals(expected, added);
    }
    
    //-----------------------------------------------------------------------
    public void testAddToWithChronology1() {
        long expected = TEST_TIME_NOW;
        expected = ISOChronology.getInstance().years().add(expected, 1);
        expected = ISOChronology.getInstance().months().add(expected, 2);
        expected = ISOChronology.getInstance().weeks().add(expected, 3);
        expected = ISOChronology.getInstance().days().add(expected, 4);
        expected = ISOChronology.getInstance().hours().add(expected, 5);
        expected = ISOChronology.getInstance().minutes().add(expected, 6);
        expected = ISOChronology.getInstance().seconds().add(expected, 7);
        expected = ISOChronology.getInstance().millis().add(expected, 8);
        
        MutablePeriod test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        long added = test.addTo(TEST_TIME_NOW, 1, ISOChronology.getInstance());
        assertEquals(expected, added);
    }
    
    public void testAddToWithChronology2() {
        long expected = TEST_TIME_NOW;
        expected = ISOChronology.getInstance().years().add(expected, -2);
        expected = ISOChronology.getInstance().months().add(expected, -4);
        expected = ISOChronology.getInstance().weeks().add(expected, -6);
        expected = ISOChronology.getInstance().days().add(expected, -8);
        expected = ISOChronology.getInstance().hours().add(expected, -10);
        expected = ISOChronology.getInstance().minutes().add(expected, -12);
        expected = ISOChronology.getInstance().seconds().add(expected, -14);
        expected = ISOChronology.getInstance().millis().add(expected, -16);
        
        MutablePeriod test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8, PeriodType.getAllType(ISOChronology.getInstanceUTC()));
        long added = test.addTo(TEST_TIME_NOW, -2, ISOChronology.getInstance());  // local specified so use it
        assertEquals(expected, added);
    }
    
    public void testAddToWithChronology3() {
        long expected = TEST_TIME_NOW;
        expected = ISOChronology.getInstanceUTC().years().add(expected, -2);
        expected = ISOChronology.getInstanceUTC().months().add(expected, -4);
        expected = ISOChronology.getInstanceUTC().weeks().add(expected, -6);
        expected = ISOChronology.getInstanceUTC().days().add(expected, -8);
        expected = ISOChronology.getInstanceUTC().hours().add(expected, -10);
        expected = ISOChronology.getInstanceUTC().minutes().add(expected, -12);
        expected = ISOChronology.getInstanceUTC().seconds().add(expected, -14);
        expected = ISOChronology.getInstanceUTC().millis().add(expected, -16);
        
        MutablePeriod test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8, PeriodType.getAllType(ISOChronology.getInstanceUTC()));
        long added = test.addTo(TEST_TIME_NOW, -2, null);  // no chrono specified so drop back to duration (UTC)
        assertEquals(expected, added);
    }
    
    //-----------------------------------------------------------------------
    public void testAddToRI1() {
        long expected = TEST_TIME_NOW;
        expected = ISOChronology.getInstance().years().add(expected, 1);
        expected = ISOChronology.getInstance().months().add(expected, 2);
        expected = ISOChronology.getInstance().weeks().add(expected, 3);
        expected = ISOChronology.getInstance().days().add(expected, 4);
        expected = ISOChronology.getInstance().hours().add(expected, 5);
        expected = ISOChronology.getInstance().minutes().add(expected, 6);
        expected = ISOChronology.getInstance().seconds().add(expected, 7);
        expected = ISOChronology.getInstance().millis().add(expected, 8);
        
        MutablePeriod test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        Instant added = test.addTo(new Instant(), 1);  // Instant has no time zone, uses duration's zone (local)
        assertEquals(expected, added.getMillis());
    }
    
    public void testAddToRI2() {
        long expected = TEST_TIME_NOW;
        expected = ISOChronology.getInstanceUTC().years().add(expected, -2);
        expected = ISOChronology.getInstanceUTC().months().add(expected, -4);
        expected = ISOChronology.getInstanceUTC().weeks().add(expected, -6);
        expected = ISOChronology.getInstanceUTC().days().add(expected, -8);
        expected = ISOChronology.getInstanceUTC().hours().add(expected, -10);
        expected = ISOChronology.getInstanceUTC().minutes().add(expected, -12);
        expected = ISOChronology.getInstanceUTC().seconds().add(expected, -14);
        expected = ISOChronology.getInstanceUTC().millis().add(expected, -16);
        
        MutablePeriod test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8, PeriodType.getAllType(ISOChronology.getInstanceUTC()));
        Instant added = test.addTo(new Instant(), -2);  // Instant has no time zone, uses duration's zone (UTC)
        assertEquals(expected, added.getMillis());
    }
    
    public void testAddToRI3() {
        long expected = TEST_TIME_NOW;
        expected = ISOChronology.getInstance().years().add(expected, -2);
        expected = ISOChronology.getInstance().months().add(expected, -4);
        expected = ISOChronology.getInstance().weeks().add(expected, -6);
        expected = ISOChronology.getInstance().days().add(expected, -8);
        expected = ISOChronology.getInstance().hours().add(expected, -10);
        expected = ISOChronology.getInstance().minutes().add(expected, -12);
        expected = ISOChronology.getInstance().seconds().add(expected, -14);
        expected = ISOChronology.getInstance().millis().add(expected, -16);
        
        MutablePeriod test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8, PeriodType.getAllType(ISOChronology.getInstanceUTC()));
        Instant added = test.addTo(new DateTime(), -2);  // DateTime has local time zone
        assertEquals(expected, added.getMillis());
    }
    
    public void testAddToRI4() {
        long expected = TEST_TIME_NOW;
        expected = ISOChronology.getInstanceUTC().years().add(expected, -2);
        expected = ISOChronology.getInstanceUTC().months().add(expected, -4);
        expected = ISOChronology.getInstanceUTC().weeks().add(expected, -6);
        expected = ISOChronology.getInstanceUTC().days().add(expected, -8);
        expected = ISOChronology.getInstanceUTC().hours().add(expected, -10);
        expected = ISOChronology.getInstanceUTC().minutes().add(expected, -12);
        expected = ISOChronology.getInstanceUTC().seconds().add(expected, -14);
        expected = ISOChronology.getInstanceUTC().millis().add(expected, -16);
        
        MutablePeriod test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8, PeriodType.getAllType(ISOChronology.getInstanceUTC()));
        Instant added = test.addTo(null, -2);  // null has no time zone, uses duration's zone (UTC)
        assertEquals(expected, added.getMillis());
    }
    
    //-----------------------------------------------------------------------
    public void testAddIntoRWI1() {
        long expected = TEST_TIME_NOW;
        expected = ISOChronology.getInstance().years().add(expected, 1);
        expected = ISOChronology.getInstance().months().add(expected, 2);
        expected = ISOChronology.getInstance().weeks().add(expected, 3);
        expected = ISOChronology.getInstance().days().add(expected, 4);
        expected = ISOChronology.getInstance().hours().add(expected, 5);
        expected = ISOChronology.getInstance().minutes().add(expected, 6);
        expected = ISOChronology.getInstance().seconds().add(expected, 7);
        expected = ISOChronology.getInstance().millis().add(expected, 8);
        
        MutablePeriod test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        MutableDateTime mdt = new MutableDateTime();
        test.addInto(mdt, 1);
        assertEquals(expected, mdt.getMillis());
    }
    
    public void testAddIntoRWI2() {
        long expected = TEST_TIME_NOW;
        expected = ISOChronology.getInstance().years().add(expected, -2);
        expected = ISOChronology.getInstance().months().add(expected, -4);
        expected = ISOChronology.getInstance().weeks().add(expected, -6);
        expected = ISOChronology.getInstance().days().add(expected, -8);
        expected = ISOChronology.getInstance().hours().add(expected, -10);
        expected = ISOChronology.getInstance().minutes().add(expected, -12);
        expected = ISOChronology.getInstance().seconds().add(expected, -14);
        expected = ISOChronology.getInstance().millis().add(expected, -16);
        
        MutablePeriod test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8, PeriodType.getAllType(ISOChronology.getInstanceUTC()));
        MutableDateTime mdt = new MutableDateTime();
        test.addInto(mdt, -2);  // MutableDateTime has a chronology, use it
        assertEquals(expected, mdt.getMillis());
    }
    
    public void testAddIntoRWI3() {
        MutablePeriod test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        try {
            test.addInto(null, 1);
            fail();
        } catch (IllegalArgumentException ex) {}
    }
    
    //-----------------------------------------------------------------------
    public void testToString() {
        MutablePeriod test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        assertEquals("P1Y2M3W4DT5H6M7.008S", test.toString());
        
        test = new MutablePeriod(0, 0, 0, 0, 0, 0, 0, 0);
        assertEquals("PT0S", test.toString());
        
        test = new MutablePeriod(12345L);
        assertEquals("PT12.345S", test.toString());
    }

    //-----------------------------------------------------------------------
    public void testToPeriod() {
        MutablePeriod test = new MutablePeriod(123L);
        Period result = test.toPeriod();
        assertEquals(test, result);
    }

    public void testToMutablePeriod() {
        MutablePeriod test = new MutablePeriod(123L);
        MutablePeriod result = test.toMutablePeriod();
        assertEquals(test, result);
    }

    //-----------------------------------------------------------------------
    public void testToDurationMillis() {
        MutablePeriod test = new MutablePeriod(123L);
        assertEquals(123L, test.toDurationMillis());
        
        test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        try {
            test.toDurationMillis();
            fail();
        } catch (IllegalStateException ex) {}
    }

    public void testToDuration() {
        MutablePeriod test = new MutablePeriod(123L);
        assertEquals(new Duration(123L), test.toDuration());
        
        test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        try {
            test.toDuration();
            fail();
        } catch (IllegalStateException ex) {}
    }

    //-----------------------------------------------------------------------
    public void testCopy() {
        MutablePeriod test = new MutablePeriod(123L);
        MutablePeriod copy = test.copy();
        assertEquals(test.getPeriodType(), copy.getPeriodType());
        assertEquals(test.isPrecise(), copy.isPrecise());
        assertEquals(test.toDurationMillis(), copy.toDurationMillis());
    }

    //-----------------------------------------------------------------------
    public void testClone() {
        MutablePeriod test = new MutablePeriod(123L);
        MutablePeriod copy = (MutablePeriod) test.clone();
        assertEquals(test.getPeriodType(), copy.getPeriodType());
        assertEquals(test.isPrecise(), copy.isPrecise());
        assertEquals(test.toDurationMillis(), copy.toDurationMillis());
    }
    
}
