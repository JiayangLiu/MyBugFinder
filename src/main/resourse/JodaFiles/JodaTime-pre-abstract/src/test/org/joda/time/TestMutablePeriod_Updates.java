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

import java.util.Locale;
import java.util.TimeZone;

import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * This class is a JUnit test for MutableDuration.
 *
 * @author Stephen Colebourne
 */
public class TestMutablePeriod_Updates extends TestCase {
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
        return new TestSuite(TestMutablePeriod_Updates.class);
    }

    public TestMutablePeriod_Updates(String name) {
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
    public void testAddYears() {
        MutablePeriod test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.addYears(10);
        assertEquals(11, test.getYears());
        
        test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.addYears(-10);
        assertEquals(-9, test.getYears());
        
        test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.addYears(0);
        assertEquals(1, test.getYears());
    }

    //-----------------------------------------------------------------------
    public void testAddMonths() {
        MutablePeriod test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.addMonths(10);
        assertEquals(12, test.getMonths());
        
        test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.addMonths(-10);
        assertEquals(-8, test.getMonths());
        
        test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.addMonths(0);
        assertEquals(2, test.getMonths());
    }

    //-----------------------------------------------------------------------
    public void testAddWeeks() {
        MutablePeriod test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.addWeeks(10);
        assertEquals(13, test.getWeeks());
        
        test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.addWeeks(-10);
        assertEquals(-7, test.getWeeks());
        
        test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.addWeeks(0);
        assertEquals(3, test.getWeeks());
    }

    //-----------------------------------------------------------------------
    public void testAddDays() {
        MutablePeriod test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.addDays(10);
        assertEquals(14, test.getDays());
        
        test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.addDays(-10);
        assertEquals(-6, test.getDays());
        
        test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.addDays(0);
        assertEquals(4, test.getDays());
    }

    //-----------------------------------------------------------------------
    public void testAddHours() {
        MutablePeriod test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.addHours(10);
        assertEquals(15, test.getHours());
        
        test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.addHours(-10);
        assertEquals(-5, test.getHours());
        
        test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.addHours(0);
        assertEquals(5, test.getHours());
    }

    //-----------------------------------------------------------------------
    public void testAddMinutes() {
        MutablePeriod test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.addMinutes(10);
        assertEquals(16, test.getMinutes());
        
        test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.addMinutes(-10);
        assertEquals(-4, test.getMinutes());
        
        test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.addMinutes(0);
        assertEquals(6, test.getMinutes());
    }

    //-----------------------------------------------------------------------
    public void testAddSeconds() {
        MutablePeriod test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.addSeconds(10);
        assertEquals(17, test.getSeconds());
        
        test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.addSeconds(-10);
        assertEquals(-3, test.getSeconds());
        
        test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.addSeconds(0);
        assertEquals(7, test.getSeconds());
    }

    //-----------------------------------------------------------------------
    public void testAddMillis() {
        MutablePeriod test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.addMillis(10);
        assertEquals(18, test.getMillis());
        
        test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.addMillis(-10);
        assertEquals(-2, test.getMillis());
        
        test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.addMillis(0);
        assertEquals(8, test.getMillis());
    }

    //-----------------------------------------------------------------------
    public void testSetYears() {
        MutablePeriod test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.setYears(10);
        assertEquals(10, test.getYears());
        
        test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.setYears(-10);
        assertEquals(-10, test.getYears());
        
        test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.setYears(0);
        assertEquals(0, test.getYears());
        
        test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.setYears(1);
        assertEquals(1, test.getYears());
    }

    //-----------------------------------------------------------------------
    public void testSetMonths() {
        MutablePeriod test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.setMonths(10);
        assertEquals(10, test.getMonths());
        
        test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.setMonths(-10);
        assertEquals(-10, test.getMonths());
        
        test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.setMonths(0);
        assertEquals(0, test.getMonths());
        
        test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.setMonths(2);
        assertEquals(2, test.getMonths());
    }

    //-----------------------------------------------------------------------
    public void testSetWeeks() {
        MutablePeriod test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.setWeeks(10);
        assertEquals(10, test.getWeeks());
        
        test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.setWeeks(-10);
        assertEquals(-10, test.getWeeks());
        
        test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.setWeeks(0);
        assertEquals(0, test.getWeeks());
        
        test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.setWeeks(3);
        assertEquals(3, test.getWeeks());
    }

    //-----------------------------------------------------------------------
    public void testSetDays() {
        MutablePeriod test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.setDays(10);
        assertEquals(10, test.getDays());
        
        test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.setDays(-10);
        assertEquals(-10, test.getDays());
        
        test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.setDays(0);
        assertEquals(0, test.getDays());
        
        test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.setDays(4);
        assertEquals(4, test.getDays());
    }

    //-----------------------------------------------------------------------
    public void testSetHours() {
        MutablePeriod test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.setHours(10);
        assertEquals(10, test.getHours());
        
        test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.setHours(-10);
        assertEquals(-10, test.getHours());
        
        test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.setHours(0);
        assertEquals(0, test.getHours());
        
        test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.setHours(5);
        assertEquals(5, test.getHours());
    }

    //-----------------------------------------------------------------------
    public void testSetMinutes() {
        MutablePeriod test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.setMinutes(10);
        assertEquals(10, test.getMinutes());
        
        test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.setMinutes(-10);
        assertEquals(-10, test.getMinutes());
        
        test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.setMinutes(0);
        assertEquals(0, test.getMinutes());
        
        test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.setMinutes(6);
        assertEquals(6, test.getMinutes());
    }

    //-----------------------------------------------------------------------
    public void testSetSeconds() {
        MutablePeriod test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.setSeconds(10);
        assertEquals(10, test.getSeconds());
        
        test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.setSeconds(-10);
        assertEquals(-10, test.getSeconds());
        
        test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.setSeconds(0);
        assertEquals(0, test.getSeconds());
        
        test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.setSeconds(7);
        assertEquals(7, test.getSeconds());
    }

    //-----------------------------------------------------------------------
    public void testSetMillis() {
        MutablePeriod test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.setMillis(10);
        assertEquals(10, test.getMillis());
        
        test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.setMillis(-10);
        assertEquals(-10, test.getMillis());
        
        test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.setMillis(0);
        assertEquals(0, test.getMillis());
        
        test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.setMillis(8);
        assertEquals(8, test.getMillis());
    }

    //-----------------------------------------------------------------------
    public void testSetPeriod_8ints1() {
        MutablePeriod test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.setPeriod(11, 12, 13, 14, 15, 16, 17, 18);
        assertEquals(11, test.getYears());
        assertEquals(12, test.getMonths());
        assertEquals(13, test.getWeeks());
        assertEquals(14, test.getDays());
        assertEquals(15, test.getHours());
        assertEquals(16, test.getMinutes());
        assertEquals(17, test.getSeconds());
        assertEquals(18, test.getMillis());
    }

    public void testSetPeriod_8ints2() {
        MutablePeriod test = new MutablePeriod(100L, PeriodType.getMillisType());
        try {
            test.setPeriod(11, 12, 13, 14, 15, 16, 17, 18);
            fail();
        } catch (IllegalArgumentException ex) {}
        assertEquals(0, test.getYears());
        assertEquals(0, test.getMonths());
        assertEquals(0, test.getWeeks());
        assertEquals(0, test.getDays());
        assertEquals(0, test.getHours());
        assertEquals(0, test.getMinutes());
        assertEquals(0, test.getSeconds());
        assertEquals(100, test.getMillis());
        assertEquals(100, test.toDurationMillis());
    }

    public void testSetPeriod_8ints3() {
        MutablePeriod test = new MutablePeriod(100L, PeriodType.getMillisType());
        test.setPeriod(0, 0, 0, 0, 0, 0, 0, 18);
        assertEquals(0, test.getYears());
        assertEquals(0, test.getMonths());
        assertEquals(0, test.getWeeks());
        assertEquals(0, test.getDays());
        assertEquals(0, test.getHours());
        assertEquals(0, test.getMinutes());
        assertEquals(0, test.getSeconds());
        assertEquals(18, test.getMillis());
        assertEquals(18, test.toDurationMillis());
    }

    public void testSetPeriod_8ints4() {
        MutablePeriod test = new MutablePeriod(0, 0, 0, 0, 5, 6, 7, 8);
        assertEquals(true, test.isPrecise());
        test.setPeriod(11, 12, 13, 14, 15, 16, 17, 18);
        assertEquals(false, test.isPrecise());
        assertEquals(11, test.getYears());
        assertEquals(12, test.getMonths());
        assertEquals(13, test.getWeeks());
        assertEquals(14, test.getDays());
        assertEquals(15, test.getHours());
        assertEquals(16, test.getMinutes());
        assertEquals(17, test.getSeconds());
        assertEquals(18, test.getMillis());
    }

    //-----------------------------------------------------------------------
    public void testSetPeriod_RP1() {
        MutablePeriod test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.setPeriod(new MutablePeriod(11, 12, 13, 14, 15, 16, 17, 18));
        assertEquals(11, test.getYears());
        assertEquals(12, test.getMonths());
        assertEquals(13, test.getWeeks());
        assertEquals(14, test.getDays());
        assertEquals(15, test.getHours());
        assertEquals(16, test.getMinutes());
        assertEquals(17, test.getSeconds());
        assertEquals(18, test.getMillis());
    }

    public void testSetPeriod_RP2() {
        MutablePeriod test = new MutablePeriod(100L, PeriodType.getMillisType());
        try {
            test.setPeriod(new MutablePeriod(11, 12, 13, 14, 15, 16, 17, 18));
            fail();
        } catch (IllegalArgumentException ex) {}
        assertEquals(0, test.getYears());
        assertEquals(0, test.getMonths());
        assertEquals(0, test.getWeeks());
        assertEquals(0, test.getDays());
        assertEquals(0, test.getHours());
        assertEquals(0, test.getMinutes());
        assertEquals(0, test.getSeconds());
        assertEquals(100, test.getMillis());
        assertEquals(100, test.toDurationMillis());
    }

    public void testSetPeriod_RP3() {
        MutablePeriod test = new MutablePeriod(100L, PeriodType.getMillisType());
        test.setPeriod(new MutablePeriod(0, 0, 0, 0, 0, 0, 0, 18));
        assertEquals(0, test.getYears());
        assertEquals(0, test.getMonths());
        assertEquals(0, test.getWeeks());
        assertEquals(0, test.getDays());
        assertEquals(0, test.getHours());
        assertEquals(0, test.getMinutes());
        assertEquals(0, test.getSeconds());
        assertEquals(18, test.getMillis());
        assertEquals(18, test.toDurationMillis());
    }

    public void testSetPeriod_RP4() {
        MutablePeriod test = new MutablePeriod(0, 0, 0, 0, 5, 6, 7, 8);
        assertEquals(true, test.isPrecise());
        test.setPeriod(new MutablePeriod(11, 12, 13, 14, 15, 16, 17, 18));
        assertEquals(false, test.isPrecise());
        assertEquals(11, test.getYears());
        assertEquals(12, test.getMonths());
        assertEquals(13, test.getWeeks());
        assertEquals(14, test.getDays());
        assertEquals(15, test.getHours());
        assertEquals(16, test.getMinutes());
        assertEquals(17, test.getSeconds());
        assertEquals(18, test.getMillis());
    }

    public void testSetPeriod_RP5() {
        MutablePeriod test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.setPeriod((ReadablePeriod) null);
        assertEquals(0, test.getYears());
        assertEquals(0, test.getMonths());
        assertEquals(0, test.getWeeks());
        assertEquals(0, test.getDays());
        assertEquals(0, test.getHours());
        assertEquals(0, test.getMinutes());
        assertEquals(0, test.getSeconds());
        assertEquals(0, test.getMillis());
    }

    //-----------------------------------------------------------------------
    public void testSetPeriod_long_long1() {
        MutablePeriod test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        DateTime dt1 = new DateTime(2002, 6, 9, 13, 15, 17, 19);
        DateTime dt2 = new DateTime(2003, 7, 17, 14, 16, 18, 20);
        test.setPeriod(dt1.getMillis(), dt2.getMillis());
        assertEquals(1, test.getYears());
        assertEquals(1, test.getMonths());
        assertEquals(1, test.getWeeks());
        assertEquals(1, test.getDays());
        assertEquals(1, test.getHours());
        assertEquals(1, test.getMinutes());
        assertEquals(1, test.getSeconds());
        assertEquals(1, test.getMillis());
    }

    public void testSetPeriod_long_long2() {
        MutablePeriod test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        DateTime dt1 = new DateTime(2002, 6, 9, 13, 15, 17, 19);
        DateTime dt2 = new DateTime(2003, 7, 17, 14, 16, 18, 20);
        test.setPeriod(dt2.getMillis(), dt1.getMillis());
        assertEquals(-1, test.getYears());
        assertEquals(-1, test.getMonths());
        assertEquals(-1, test.getWeeks());
        assertEquals(-1, test.getDays());
        assertEquals(-1, test.getHours());
        assertEquals(-1, test.getMinutes());
        assertEquals(-1, test.getSeconds());
        assertEquals(-1, test.getMillis());
    }

    public void testSetPeriod_long_long3() {
        MutablePeriod test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        DateTime dt1 = new DateTime(2002, 6, 9, 13, 15, 17, 19);
        test.setPeriod(dt1.getMillis(), dt1.getMillis());
        assertEquals(0, test.getYears());
        assertEquals(0, test.getMonths());
        assertEquals(0, test.getWeeks());
        assertEquals(0, test.getDays());
        assertEquals(0, test.getHours());
        assertEquals(0, test.getMinutes());
        assertEquals(0, test.getSeconds());
        assertEquals(0, test.getMillis());
    }

    public void testSetPeriod_long_long_NoYears() {
        MutablePeriod test = new MutablePeriod(PeriodType.getAllType().withYearsRemoved());
        DateTime dt1 = new DateTime(2002, 6, 9, 13, 15, 17, 19);
        DateTime dt2 = new DateTime(2003, 7, 17, 14, 16, 18, 20);
        test.setPeriod(dt1.getMillis(), dt2.getMillis());
        assertEquals(0, test.getYears());
        assertEquals(13, test.getMonths());
        assertEquals(1, test.getWeeks());
        assertEquals(1, test.getDays());
        assertEquals(1, test.getHours());
        assertEquals(1, test.getMinutes());
        assertEquals(1, test.getSeconds());
        assertEquals(1, test.getMillis());
    }

    public void testSetPeriod_long_long_NoMonths() {
        MutablePeriod test = new MutablePeriod(PeriodType.getAllType().withMonthsRemoved());
        DateTime dt1 = new DateTime(2002, 6, 9, 13, 15, 17, 19);
        DateTime dt2 = new DateTime(2003, 7, 17, 14, 16, 18, 20);
        test.setPeriod(dt1.getMillis(), dt2.getMillis());
        assertEquals(1, test.getYears());
        assertEquals(0, test.getMonths());
        assertEquals(5, test.getWeeks());
        assertEquals(3, test.getDays());
        assertEquals(1, test.getHours());
        assertEquals(1, test.getMinutes());
        assertEquals(1, test.getSeconds());
        assertEquals(1, test.getMillis());
    }

    public void testSetPeriod_long_long_NoWeeks() {
        MutablePeriod test = new MutablePeriod(PeriodType.getAllType().withWeeksRemoved());
        DateTime dt1 = new DateTime(2002, 6, 9, 13, 15, 17, 19);
        DateTime dt2 = new DateTime(2003, 7, 17, 14, 16, 18, 20);
        test.setPeriod(dt1.getMillis(), dt2.getMillis());
        assertEquals(1, test.getYears());
        assertEquals(1, test.getMonths());
        assertEquals(0, test.getWeeks());
        assertEquals(8, test.getDays());
        assertEquals(1, test.getHours());
        assertEquals(1, test.getMinutes());
        assertEquals(1, test.getSeconds());
        assertEquals(1, test.getMillis());
    }

    public void testSetPeriod_long_long_NoDays() {
        MutablePeriod test = new MutablePeriod(PeriodType.getAllType().withDaysRemoved());
        DateTime dt1 = new DateTime(2002, 6, 9, 13, 15, 17, 19);
        DateTime dt2 = new DateTime(2003, 7, 17, 14, 16, 18, 20);
        test.setPeriod(dt1.getMillis(), dt2.getMillis());
        assertEquals(1, test.getYears());
        assertEquals(1, test.getMonths());
        assertEquals(1, test.getWeeks());
        assertEquals(0, test.getDays());
        assertEquals(25, test.getHours());
        assertEquals(1, test.getMinutes());
        assertEquals(1, test.getSeconds());
        assertEquals(1, test.getMillis());
    }

    public void testSetPeriod_long_long_NoHours() {
        MutablePeriod test = new MutablePeriod(PeriodType.getAllType().withHoursRemoved());
        DateTime dt1 = new DateTime(2002, 6, 9, 13, 15, 17, 19);
        DateTime dt2 = new DateTime(2003, 7, 17, 14, 16, 18, 20);
        test.setPeriod(dt1.getMillis(), dt2.getMillis());
        assertEquals(1, test.getYears());
        assertEquals(1, test.getMonths());
        assertEquals(1, test.getWeeks());
        assertEquals(1, test.getDays());
        assertEquals(0, test.getHours());
        assertEquals(61, test.getMinutes());
        assertEquals(1, test.getSeconds());
        assertEquals(1, test.getMillis());
    }

    public void testSetPeriod_long_long_NoMinutes() {
        MutablePeriod test = new MutablePeriod(PeriodType.getAllType().withMinutesRemoved());
        DateTime dt1 = new DateTime(2002, 6, 9, 13, 15, 17, 19);
        DateTime dt2 = new DateTime(2003, 7, 17, 14, 16, 18, 20);
        test.setPeriod(dt1.getMillis(), dt2.getMillis());
        assertEquals(1, test.getYears());
        assertEquals(1, test.getMonths());
        assertEquals(1, test.getWeeks());
        assertEquals(1, test.getDays());
        assertEquals(1, test.getHours());
        assertEquals(0, test.getMinutes());
        assertEquals(61, test.getSeconds());
        assertEquals(1, test.getMillis());
    }

    public void testSetPeriod_long_long_NoSeconds() {
        MutablePeriod test = new MutablePeriod(PeriodType.getAllType().withSecondsRemoved());
        DateTime dt1 = new DateTime(2002, 6, 9, 13, 15, 17, 19);
        DateTime dt2 = new DateTime(2003, 7, 17, 14, 16, 18, 20);
        test.setPeriod(dt1.getMillis(), dt2.getMillis());
        assertEquals(1, test.getYears());
        assertEquals(1, test.getMonths());
        assertEquals(1, test.getWeeks());
        assertEquals(1, test.getDays());
        assertEquals(1, test.getHours());
        assertEquals(1, test.getMinutes());
        assertEquals(0, test.getSeconds());
        assertEquals(1001, test.getMillis());
    }

    public void testSetPeriod_long_long_NoMillis() {
        MutablePeriod test = new MutablePeriod(PeriodType.getAllType().withMillisRemoved());
        DateTime dt1 = new DateTime(2002, 6, 9, 13, 15, 17, 19);
        DateTime dt2 = new DateTime(2003, 7, 17, 14, 16, 18, 20);
        test.setPeriod(dt1.getMillis(), dt2.getMillis());
        assertEquals(1, test.getYears());
        assertEquals(1, test.getMonths());
        assertEquals(1, test.getWeeks());
        assertEquals(1, test.getDays());
        assertEquals(1, test.getHours());
        assertEquals(1, test.getMinutes());
        assertEquals(1, test.getSeconds());
        assertEquals(0, test.getMillis());
    }

    //-----------------------------------------------------------------------
    public void testSetPeriod_RInterval1() {
        MutablePeriod test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        DateTime dt1 = new DateTime(2002, 6, 9, 13, 15, 17, 19);
        DateTime dt2 = new DateTime(2003, 7, 17, 14, 16, 18, 20);
        test.setPeriod(new Interval(dt1, dt2));
        assertEquals(1, test.getYears());
        assertEquals(1, test.getMonths());
        assertEquals(1, test.getWeeks());
        assertEquals(1, test.getDays());
        assertEquals(1, test.getHours());
        assertEquals(1, test.getMinutes());
        assertEquals(1, test.getSeconds());
        assertEquals(1, test.getMillis());
    }

    public void testSetPeriod_RInterval2() {
        MutablePeriod test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.setPeriod((ReadableInterval) null);
        assertEquals(0, test.getYears());
        assertEquals(0, test.getMonths());
        assertEquals(0, test.getWeeks());
        assertEquals(0, test.getDays());
        assertEquals(0, test.getHours());
        assertEquals(0, test.getMinutes());
        assertEquals(0, test.getSeconds());
        assertEquals(0, test.getMillis());
    }

    //-----------------------------------------------------------------------
    public void testSetPeriod_long1() {
        MutablePeriod test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.setPeriod(100L);
        assertEquals(0, test.getYears());
        assertEquals(0, test.getMonths());
        assertEquals(0, test.getWeeks());
        assertEquals(0, test.getDays());
        assertEquals(0, test.getHours());
        assertEquals(0, test.getMinutes());
        assertEquals(0, test.getSeconds());
        assertEquals(100, test.getMillis());
    }

    public void testSetPeriod_long2() {
        MutablePeriod test = new MutablePeriod();
        test.setPeriod(
            (4L + (3L * 7L) + (2L * 30L) + 365L) * DateTimeConstants.MILLIS_PER_DAY +
            5L * DateTimeConstants.MILLIS_PER_HOUR +
            6L * DateTimeConstants.MILLIS_PER_MINUTE +
            7L * DateTimeConstants.MILLIS_PER_SECOND + 8L);
        // only time fields are precise
        assertEquals(0, test.getYears());  // (4 + (3 * 7) + (2 * 30) + 365) == 450
        assertEquals(0, test.getMonths());
        assertEquals(0, test.getWeeks());
        assertEquals(0, test.getDays());
        assertEquals((450 * 24) + 5, test.getHours());
        assertEquals(6, test.getMinutes());
        assertEquals(7, test.getSeconds());
        assertEquals(8, test.getMillis());
    }

    public void testSetPeriod_long3() {
        MutablePeriod test = new MutablePeriod(PeriodType.getPreciseYearMonthType());
        test.setPeriod(
            (4L + (3L * 7L) + (2L * 30L) + 365L) * DateTimeConstants.MILLIS_PER_DAY +
            5L * DateTimeConstants.MILLIS_PER_HOUR +
            6L * DateTimeConstants.MILLIS_PER_MINUTE +
            7L * DateTimeConstants.MILLIS_PER_SECOND + 8L);
        assertEquals(1, test.getYears());
        assertEquals(2, test.getMonths());
        assertEquals(0, test.getWeeks());
        assertEquals(25, test.getDays());
        assertEquals(5, test.getHours());
        assertEquals(6, test.getMinutes());
        assertEquals(7, test.getSeconds());
        assertEquals(8, test.getMillis());
    }

    public void testSetPeriod_long4() {
        MutablePeriod test = new MutablePeriod(PeriodType.getPreciseYearWeekType());
        test.setPeriod(
            (4L + (3L * 7L) + (2L * 30L) + 365L) * DateTimeConstants.MILLIS_PER_DAY +
            5L * DateTimeConstants.MILLIS_PER_HOUR +
            6L * DateTimeConstants.MILLIS_PER_MINUTE +
            7L * DateTimeConstants.MILLIS_PER_SECOND + 8L);
        assertEquals(1, test.getYears());
        assertEquals(0, test.getMonths());
        assertEquals(12, test.getWeeks());
        assertEquals(1, test.getDays());
        assertEquals(5, test.getHours());
        assertEquals(6, test.getMinutes());
        assertEquals(7, test.getSeconds());
        assertEquals(8, test.getMillis());
    }

    public void testSetPeriod_long_NoYears() {
        long ms =
            (4L + (3L * 7L) + (2L * 30L) + 365L) * DateTimeConstants.MILLIS_PER_DAY +
            5L * DateTimeConstants.MILLIS_PER_HOUR +
            6L * DateTimeConstants.MILLIS_PER_MINUTE +
            7L * DateTimeConstants.MILLIS_PER_SECOND + 8L;
        MutablePeriod test = new MutablePeriod(PeriodType.getPreciseYearMonthType().withYearsRemoved());
        test.setPeriod(ms);
        assertEquals(0, test.getYears());
        assertEquals(15, test.getMonths()); // totalDays=365+85=450=15*30
        assertEquals(0, test.getWeeks());
        assertEquals(0, test.getDays());
        assertEquals(5, test.getHours());
        assertEquals(6, test.getMinutes());
        assertEquals(7, test.getSeconds());
        assertEquals(8, test.getMillis());
        assertEquals(ms, test.toDurationMillis());
    }

    public void testSetPeriod_long_NoMonths() {
        long ms =
            (4L + (3L * 7L) + (2L * 30L) + 365L) * DateTimeConstants.MILLIS_PER_DAY +
            5L * DateTimeConstants.MILLIS_PER_HOUR +
            6L * DateTimeConstants.MILLIS_PER_MINUTE +
            7L * DateTimeConstants.MILLIS_PER_SECOND + 8L;
        MutablePeriod test = new MutablePeriod(PeriodType.getPreciseYearMonthType().withMonthsRemoved());
        test.setPeriod(ms);
        assertEquals(1, test.getYears());
        assertEquals(0, test.getMonths());
        assertEquals(0, test.getWeeks());
        assertEquals(85, test.getDays());
        assertEquals(5, test.getHours());
        assertEquals(6, test.getMinutes());
        assertEquals(7, test.getSeconds());
        assertEquals(8, test.getMillis());
        assertEquals(ms, test.toDurationMillis());
    }

    public void testSetPeriod_long_NoWeeks() {
        long ms =
            (4L + (3L * 7L) + (2L * 30L) + 365L) * DateTimeConstants.MILLIS_PER_DAY +
            5L * DateTimeConstants.MILLIS_PER_HOUR +
            6L * DateTimeConstants.MILLIS_PER_MINUTE +
            7L * DateTimeConstants.MILLIS_PER_SECOND + 8L;
        MutablePeriod test = new MutablePeriod(PeriodType.getPreciseYearWeekType().withWeeksRemoved());
        test.setPeriod(ms);
        assertEquals(1, test.getYears());
        assertEquals(0, test.getMonths());
        assertEquals(0, test.getWeeks());
        assertEquals(85, test.getDays());
        assertEquals(5, test.getHours());
        assertEquals(6, test.getMinutes());
        assertEquals(7, test.getSeconds());
        assertEquals(8, test.getMillis());
        assertEquals(ms, test.toDurationMillis());
    }

    public void testSetPeriod_long_NoDays() {
        long ms =
            (4L + (3L * 7L) + (2L * 30L) + 365L) * DateTimeConstants.MILLIS_PER_DAY +
            5L * DateTimeConstants.MILLIS_PER_HOUR +
            6L * DateTimeConstants.MILLIS_PER_MINUTE +
            7L * DateTimeConstants.MILLIS_PER_SECOND + 8L;
        MutablePeriod test = new MutablePeriod(PeriodType.getPreciseYearMonthType().withDaysRemoved());
        test.setPeriod(ms);
        assertEquals(1, test.getYears());
        assertEquals(2, test.getMonths());
        assertEquals(0, test.getWeeks());
        assertEquals(0, test.getDays());
        assertEquals(5 + 25 * 24, test.getHours());
        assertEquals(6, test.getMinutes());
        assertEquals(7, test.getSeconds());
        assertEquals(8, test.getMillis());
        assertEquals(ms, test.toDurationMillis());
    }

    public void testSetPeriod_long_NoHours() {
        long ms =
            (4L + (3L * 7L) + (2L * 30L) + 365L) * DateTimeConstants.MILLIS_PER_DAY +
            5L * DateTimeConstants.MILLIS_PER_HOUR +
            6L * DateTimeConstants.MILLIS_PER_MINUTE +
            7L * DateTimeConstants.MILLIS_PER_SECOND + 8L;
        MutablePeriod test = new MutablePeriod(PeriodType.getPreciseYearMonthType().withHoursRemoved());
        test.setPeriod(ms);
        assertEquals(1, test.getYears());
        assertEquals(2, test.getMonths());
        assertEquals(0, test.getWeeks());
        assertEquals(25, test.getDays());
        assertEquals(0, test.getHours());
        assertEquals(6 + 5 * 60, test.getMinutes());
        assertEquals(7, test.getSeconds());
        assertEquals(8, test.getMillis());
        assertEquals(ms, test.toDurationMillis());
    }

    public void testSetPeriod_long_NoMinutes() {
        long ms =
            (4L + (3L * 7L) + (2L * 30L) + 365L) * DateTimeConstants.MILLIS_PER_DAY +
            5L * DateTimeConstants.MILLIS_PER_HOUR +
            6L * DateTimeConstants.MILLIS_PER_MINUTE +
            7L * DateTimeConstants.MILLIS_PER_SECOND + 8L;
        MutablePeriod test = new MutablePeriod(PeriodType.getPreciseYearMonthType().withMinutesRemoved());
        test.setPeriod(ms);
        assertEquals(1, test.getYears());
        assertEquals(2, test.getMonths());
        assertEquals(0, test.getWeeks());
        assertEquals(25, test.getDays());
        assertEquals(5, test.getHours());
        assertEquals(0, test.getMinutes());
        assertEquals(7 + 6 * 60, test.getSeconds());
        assertEquals(8, test.getMillis());
        assertEquals(ms, test.toDurationMillis());
    }

    public void testSetPeriod_long_NoSeconds() {
        long ms =
            (4L + (3L * 7L) + (2L * 30L) + 365L) * DateTimeConstants.MILLIS_PER_DAY +
            5L * DateTimeConstants.MILLIS_PER_HOUR +
            6L * DateTimeConstants.MILLIS_PER_MINUTE +
            7L * DateTimeConstants.MILLIS_PER_SECOND + 8L;
        MutablePeriod test = new MutablePeriod(PeriodType.getPreciseYearMonthType().withSecondsRemoved());
        test.setPeriod(ms);
        assertEquals(1, test.getYears());
        assertEquals(2, test.getMonths());
        assertEquals(0, test.getWeeks());
        assertEquals(25, test.getDays());
        assertEquals(5, test.getHours());
        assertEquals(6, test.getMinutes());
        assertEquals(0, test.getSeconds());
        assertEquals(8 + 7 * 1000, test.getMillis());
        assertEquals(ms, test.toDurationMillis());
    }

    public void testSetPeriod_long_NoMillis() {
        long ms =
            (4L + (3L * 7L) + (2L * 30L) + 365L) * DateTimeConstants.MILLIS_PER_DAY +
            5L * DateTimeConstants.MILLIS_PER_HOUR +
            6L * DateTimeConstants.MILLIS_PER_MINUTE +
            7L * DateTimeConstants.MILLIS_PER_SECOND + 8L;
        MutablePeriod test = new MutablePeriod(PeriodType.getPreciseYearMonthType().withMillisRemoved());
        test.setPeriod(ms);
        assertEquals(1, test.getYears());
        assertEquals(2, test.getMonths());
        assertEquals(0, test.getWeeks());
        assertEquals(25, test.getDays());
        assertEquals(5, test.getHours());
        assertEquals(6, test.getMinutes());
        assertEquals(7, test.getSeconds());
        assertEquals(0, test.getMillis());
        assertEquals(ms - 8, test.toDurationMillis());
    }

    //-----------------------------------------------------------------------
    public void testSetPeriod_RD1() {
        MutablePeriod test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.setPeriod(new Duration(100L));
        assertEquals(0, test.getYears());
        assertEquals(0, test.getMonths());
        assertEquals(0, test.getWeeks());
        assertEquals(0, test.getDays());
        assertEquals(0, test.getHours());
        assertEquals(0, test.getMinutes());
        assertEquals(0, test.getSeconds());
        assertEquals(100, test.getMillis());
    }

    public void testSetPeriod_RD2() {
        MutablePeriod test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        long length =
            (4L + (3L * 7L) + (2L * 30L) + 365L) * DateTimeConstants.MILLIS_PER_DAY +
            5L * DateTimeConstants.MILLIS_PER_HOUR +
            6L * DateTimeConstants.MILLIS_PER_MINUTE +
            7L * DateTimeConstants.MILLIS_PER_SECOND + 8L;
        test.setPeriod(new Duration(length));
        // only time fields are precise
        assertEquals(0, test.getYears());  // (4 + (3 * 7) + (2 * 30) + 365) == 450
        assertEquals(0, test.getMonths());
        assertEquals(0, test.getWeeks());
        assertEquals(0, test.getDays());
        assertEquals((450 * 24) + 5, test.getHours());
        assertEquals(6, test.getMinutes());
        assertEquals(7, test.getSeconds());
        assertEquals(8, test.getMillis());
    }

    public void testSetPeriod_RD3() {
        MutablePeriod test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.setPeriod((ReadableDuration) null);
        assertEquals(0, test.getYears());
        assertEquals(0, test.getMonths());
        assertEquals(0, test.getWeeks());
        assertEquals(0, test.getDays());
        assertEquals(0, test.getHours());
        assertEquals(0, test.getMinutes());
        assertEquals(0, test.getSeconds());
        assertEquals(0, test.getMillis());
    }

    //-----------------------------------------------------------------------
    public void testAdd_8ints1() {
        MutablePeriod test = new MutablePeriod(100L);
        test.add(1, 2, 3, 4, 5, 6, 7, 8);
        assertEquals(1, test.getYears());
        assertEquals(2, test.getMonths());
        assertEquals(3, test.getWeeks());
        assertEquals(4, test.getDays());
        assertEquals(5, test.getHours());
        assertEquals(6, test.getMinutes());
        assertEquals(7, test.getSeconds());
        assertEquals(108, test.getMillis());
    }

    public void testAdd_8ints2() {
        MutablePeriod test = new MutablePeriod(100L, PeriodType.getYearMonthType());
        try {
            test.add(1, 2, 3, 4, 5, 6, 7, 8);
            fail();
        } catch (IllegalArgumentException ex) {}
        assertEquals(0, test.getYears());
        assertEquals(0, test.getMonths());
        assertEquals(0, test.getWeeks());
        assertEquals(0, test.getDays());
        assertEquals(0, test.getHours());
        assertEquals(0, test.getMinutes());
        assertEquals(0, test.getSeconds());
        assertEquals(100, test.getMillis());
    }

    //-----------------------------------------------------------------------
    public void testAdd_long1() {
        MutablePeriod test = new MutablePeriod(100L);
        test.add(100L);
        assertEquals(0, test.getYears());
        assertEquals(0, test.getMonths());
        assertEquals(0, test.getWeeks());
        assertEquals(0, test.getDays());
        assertEquals(0, test.getHours());
        assertEquals(0, test.getMinutes());
        assertEquals(0, test.getSeconds());
        assertEquals(200, test.getMillis());
        assertEquals(200L, test.toDurationMillis());
    }

    public void testAdd_long2() {
        MutablePeriod test = new MutablePeriod(100L, PeriodType.getAllType());
        long ms =
            (4L + (3L * 7L) + (2L * 30L) + 365L) * DateTimeConstants.MILLIS_PER_DAY +
            5L * DateTimeConstants.MILLIS_PER_HOUR +
            6L * DateTimeConstants.MILLIS_PER_MINUTE +
            7L * DateTimeConstants.MILLIS_PER_SECOND + 8L;
        test.add(ms);
        // only time fields are precise
        assertEquals(0, test.getYears());  // (4 + (3 * 7) + (2 * 30) + 365) == 450
        assertEquals(0, test.getMonths());
        assertEquals(0, test.getWeeks());
        assertEquals(0, test.getDays());
        assertEquals((450 * 24) + 5, test.getHours());
        assertEquals(6, test.getMinutes());
        assertEquals(7, test.getSeconds());
        assertEquals(108, test.getMillis());
    }

    public void testAdd_long3() {
        MutablePeriod test = new MutablePeriod(100L, PeriodType.getPreciseYearMonthType());
        long ms =
            (4L + (3L * 7L) + (2L * 30L) + 365L) * DateTimeConstants.MILLIS_PER_DAY +
            5L * DateTimeConstants.MILLIS_PER_HOUR +
            6L * DateTimeConstants.MILLIS_PER_MINUTE +
            7L * DateTimeConstants.MILLIS_PER_SECOND + 8L;
        test.add(ms);
        assertEquals(1, test.getYears());
        assertEquals(2, test.getMonths());
        assertEquals(0, test.getWeeks());  // checks that YearMonth type is used in conversion
        assertEquals(25, test.getDays());
        assertEquals(5, test.getHours());
        assertEquals(6, test.getMinutes());
        assertEquals(7, test.getSeconds());
        assertEquals(108, test.getMillis());
        assertEquals(ms + 100L, test.toDurationMillis());
    }

    public void testAdd_long4() {
        MutablePeriod test = new MutablePeriod(100L, PeriodType.getPreciseYearMonthType());
        long ms =0L;
        test.add(ms);
        assertEquals(0, test.getYears());
        assertEquals(0, test.getMonths());
        assertEquals(0, test.getWeeks());
        assertEquals(0, test.getDays());
        assertEquals(0, test.getHours());
        assertEquals(0, test.getMinutes());
        assertEquals(0, test.getSeconds());
        assertEquals(100, test.getMillis());
        assertEquals(100L, test.toDurationMillis());
    }

    public void testAdd_long5() {
        MutablePeriod test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.add(2100L);
        assertEquals(1, test.getYears());
        assertEquals(2, test.getMonths());
        assertEquals(3, test.getWeeks());
        assertEquals(4, test.getDays());
        assertEquals(5, test.getHours());
        assertEquals(6, test.getMinutes());
        assertEquals(9, test.getSeconds());
        assertEquals(108, test.getMillis());
    }

    //-----------------------------------------------------------------------
    public void testAdd_RD1() {
        MutablePeriod test = new MutablePeriod(100L);
        test.add(new Duration(100L));
        assertEquals(0, test.getYears());
        assertEquals(0, test.getMonths());
        assertEquals(0, test.getWeeks());
        assertEquals(0, test.getDays());
        assertEquals(0, test.getHours());
        assertEquals(0, test.getMinutes());
        assertEquals(0, test.getSeconds());
        assertEquals(200, test.getMillis());
        assertEquals(200L, test.toDurationMillis());
    }

    public void testAdd_RD2() {
        MutablePeriod test = new MutablePeriod(100L, PeriodType.getPreciseYearMonthType());
        long ms =
            (4L + (3L * 7L) + (2L * 30L) + 365L) * DateTimeConstants.MILLIS_PER_DAY +
            5L * DateTimeConstants.MILLIS_PER_HOUR +
            6L * DateTimeConstants.MILLIS_PER_MINUTE +
            7L * DateTimeConstants.MILLIS_PER_SECOND + 8L;
        test.add(new Duration(ms));
        assertEquals(1, test.getYears());
        assertEquals(2, test.getMonths());
        assertEquals(0, test.getWeeks());  // checks that YearMonth type is used in conversion
        assertEquals(25, test.getDays());
        assertEquals(5, test.getHours());
        assertEquals(6, test.getMinutes());
        assertEquals(7, test.getSeconds());
        assertEquals(108, test.getMillis());
        assertEquals(ms + 100L, test.toDurationMillis());
    }

    public void testAdd_RD3() {
        MutablePeriod test = new MutablePeriod(100L, PeriodType.getPreciseYearMonthType());
        long ms =0L;
        test.add(new Duration(ms));
        assertEquals(0, test.getYears());
        assertEquals(0, test.getMonths());
        assertEquals(0, test.getWeeks());
        assertEquals(0, test.getDays());
        assertEquals(0, test.getHours());
        assertEquals(0, test.getMinutes());
        assertEquals(0, test.getSeconds());
        assertEquals(100, test.getMillis());
        assertEquals(100L, test.toDurationMillis());
    }

    public void testAdd_RD4() {
        MutablePeriod test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.add(new Duration(2100L));
        assertEquals(1, test.getYears());
        assertEquals(2, test.getMonths());
        assertEquals(3, test.getWeeks());
        assertEquals(4, test.getDays());
        assertEquals(5, test.getHours());
        assertEquals(6, test.getMinutes());
        assertEquals(9, test.getSeconds());
        assertEquals(108, test.getMillis());
    }

    public void testAdd_RD5() {
        MutablePeriod test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.add((ReadableDuration) null);
        assertEquals(1, test.getYears());
        assertEquals(2, test.getMonths());
        assertEquals(3, test.getWeeks());
        assertEquals(4, test.getDays());
        assertEquals(5, test.getHours());
        assertEquals(6, test.getMinutes());
        assertEquals(7, test.getSeconds());
        assertEquals(8, test.getMillis());
    }

    //-----------------------------------------------------------------------
    public void testAdd_RP1() {
        MutablePeriod test = new MutablePeriod(100L);
        test.add(new Period(100L));
        assertEquals(0, test.getYears());
        assertEquals(0, test.getMonths());
        assertEquals(0, test.getWeeks());
        assertEquals(0, test.getDays());
        assertEquals(0, test.getHours());
        assertEquals(0, test.getMinutes());
        assertEquals(0, test.getSeconds());
        assertEquals(200, test.getMillis());
        assertEquals(200L, test.toDurationMillis());
    }

    public void testAdd_RP2() {
        MutablePeriod test = new MutablePeriod(100L);  // All type
        test.add(new Period(1, 2, 3, 4, 5, 6, 7, 8, PeriodType.getPreciseAllType()));
        assertEquals(1, test.getYears());  // add field value, ignore different types
        assertEquals(2, test.getMonths());  // add field value, ignore different types
        assertEquals(3, test.getWeeks());  // add field value, ignore different types
        assertEquals(4, test.getDays());  // add field value, ignore different types
        assertEquals(5, test.getHours());
        assertEquals(6, test.getMinutes());
        assertEquals(7, test.getSeconds());
        assertEquals(108, test.getMillis());
    }

    public void testAdd_RP3() {
        MutablePeriod test = new MutablePeriod(100L, PeriodType.getPreciseYearMonthType());
        test.add(new Period(0L));
        assertEquals(0, test.getYears());
        assertEquals(0, test.getMonths());
        assertEquals(0, test.getWeeks());
        assertEquals(0, test.getDays());
        assertEquals(0, test.getHours());
        assertEquals(0, test.getMinutes());
        assertEquals(0, test.getSeconds());
        assertEquals(100, test.getMillis());
        assertEquals(100L, test.toDurationMillis());
    }

    public void testAdd_RP4() {
        MutablePeriod test = new MutablePeriod(1, 2, 0, 4, 5, 6, 7, 8, PeriodType.getYearMonthType());
        try {
            test.add(new Period(1, 2, 3, 4, 5, 6, 7, 8));  // cannot set weeks
            fail();
        } catch (IllegalArgumentException ex) {}
        assertEquals(1, test.getYears());
        assertEquals(2, test.getMonths());
        assertEquals(0, test.getWeeks());
        assertEquals(4, test.getDays());
        assertEquals(5, test.getHours());
        assertEquals(6, test.getMinutes());
        assertEquals(7, test.getSeconds());
        assertEquals(8, test.getMillis());
    }

    public void testAdd_RP5() {
        MutablePeriod test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.add((ReadablePeriod) null);
        assertEquals(1, test.getYears());
        assertEquals(2, test.getMonths());
        assertEquals(3, test.getWeeks());
        assertEquals(4, test.getDays());
        assertEquals(5, test.getHours());
        assertEquals(6, test.getMinutes());
        assertEquals(7, test.getSeconds());
        assertEquals(8, test.getMillis());
    }

    //-----------------------------------------------------------------------
    public void testAdd_RInterval1() {
        MutablePeriod test = new MutablePeriod(100L);
        test.add(new Interval(100L, 200L));
        assertEquals(0, test.getYears());
        assertEquals(0, test.getMonths());
        assertEquals(0, test.getWeeks());
        assertEquals(0, test.getDays());
        assertEquals(0, test.getHours());
        assertEquals(0, test.getMinutes());
        assertEquals(0, test.getSeconds());
        assertEquals(200, test.getMillis());
        assertEquals(200L, test.toDurationMillis());
    }

    public void testAdd_RInterval2() {
        DateTime dt1 = new DateTime(2004, 6, 9, 0, 0, 0, 0);
        DateTime dt2 = new DateTime(2005, 12, 18, 0, 0, 0, 8);
        MutablePeriod test = new MutablePeriod(100L);  // All type
        test.add(new Interval(dt1, dt2));
        assertEquals(1, test.getYears());  // add field value from interval
        assertEquals(6, test.getMonths());  // add field value from interval
        assertEquals(1, test.getWeeks());  // add field value from interval
        assertEquals(2, test.getDays());  // add field value from interval
        assertEquals(0, test.getHours());  // time zone OK
        assertEquals(0, test.getMinutes());
        assertEquals(0, test.getSeconds());
        assertEquals(108, test.getMillis());
    }

    public void testAdd_RInterval3() {
        MutablePeriod test = new MutablePeriod(100L, PeriodType.getPreciseYearMonthType());
        test.add(new Interval(0L, 0L));
        assertEquals(0, test.getYears());
        assertEquals(0, test.getMonths());
        assertEquals(0, test.getWeeks());
        assertEquals(0, test.getDays());
        assertEquals(0, test.getHours());
        assertEquals(0, test.getMinutes());
        assertEquals(0, test.getSeconds());
        assertEquals(100, test.getMillis());
    }

    public void testAdd_RInterval4() {
        DateTime dt1 = new DateTime(2004, 6, 9, 0, 0, 0, 0);
        DateTime dt2 = new DateTime(2005, 7, 17, 0, 0, 0, 8);
        MutablePeriod test = new MutablePeriod(100L, PeriodType.getYearMonthType());
        test.add(new Interval(dt1, dt2));
        assertEquals(1, test.getYears());
        assertEquals(1, test.getMonths());
        assertEquals(0, test.getWeeks());  // no weeks
        assertEquals(8, test.getDays());  // week added to days
        assertEquals(0, test.getHours());
        assertEquals(0, test.getMinutes());
        assertEquals(0, test.getSeconds());
        assertEquals(108, test.getMillis());
    }

    public void testAdd_RInterval5() {
        MutablePeriod test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        test.add((ReadableInterval) null);
        assertEquals(1, test.getYears());
        assertEquals(2, test.getMonths());
        assertEquals(3, test.getWeeks());
        assertEquals(4, test.getDays());
        assertEquals(5, test.getHours());
        assertEquals(6, test.getMinutes());
        assertEquals(7, test.getSeconds());
        assertEquals(8, test.getMillis());
    }

    //-----------------------------------------------------------------------
    public void testNormalize1() {
        MutablePeriod test = new MutablePeriod(1, 2, 3, 4, 5, 6, 7, 8);
        try {
            test.normalize();
            fail();
        } catch (IllegalStateException ex) {}
    }

    public void testNormalize2() {
        MutablePeriod test = new MutablePeriod(1, 14, 0, 36, 29, 66, 67, 1008, PeriodType.getPreciseYearMonthType());
        //   365 + 14*30 + 6 days
        // extra year created from 12 months of 30 days plus 5 extra days
        // 2*365 +  2*30 + 1 day
        test.normalize();
        assertEquals(2, test.getYears());
        assertEquals(3, test.getMonths());
        assertEquals(0, test.getWeeks());
        assertEquals(2, test.getDays());
        assertEquals(6, test.getHours());
        assertEquals(7, test.getMinutes());
        assertEquals(8, test.getSeconds());
        assertEquals(8, test.getMillis());
    }

}
