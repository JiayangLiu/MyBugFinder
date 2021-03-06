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
package org.joda.time.convert;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.joda.time.Chronology;
import org.joda.time.DateTimeZone;
import org.joda.time.PeriodType;
import org.joda.time.MutablePeriod;
import org.joda.time.chrono.ISOChronology;
import org.joda.time.chrono.JulianChronology;

/**
 * This class is a Junit unit test for LongConverter.
 *
 * @author Stephen Colebourne
 */
public class TestLongConverter extends TestCase {

    private static final DateTimeZone UTC = DateTimeZone.UTC;
    private static final DateTimeZone PARIS = DateTimeZone.getInstance("Europe/Paris");
    private static final Chronology ISO = ISOChronology.getInstance();
    private static final Chronology JULIAN = JulianChronology.getInstance();
    private static final Chronology ISO_PARIS = ISOChronology.getInstance(PARIS);
    
    private DateTimeZone zone = null;

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }

    public static TestSuite suite() {
        return new TestSuite(TestLongConverter.class);
    }

    public TestLongConverter(String name) {
        super(name);
    }

    //-----------------------------------------------------------------------
    public void testSingleton() throws Exception {
        Class cls = LongConverter.class;
        assertEquals(false, Modifier.isPublic(cls.getModifiers()));
        assertEquals(false, Modifier.isProtected(cls.getModifiers()));
        assertEquals(false, Modifier.isPrivate(cls.getModifiers()));
        
        Constructor con = cls.getDeclaredConstructor(null);
        assertEquals(1, cls.getDeclaredConstructors().length);
        assertEquals(true, Modifier.isProtected(con.getModifiers()));
        
        Field fld = cls.getDeclaredField("INSTANCE");
        assertEquals(false, Modifier.isPublic(fld.getModifiers()));
        assertEquals(false, Modifier.isProtected(fld.getModifiers()));
        assertEquals(false, Modifier.isPrivate(fld.getModifiers()));
    }

    //-----------------------------------------------------------------------
    public void testSupportedType() throws Exception {
        assertEquals(Long.class, LongConverter.INSTANCE.getSupportedType());
    }

    //-----------------------------------------------------------------------
    public void testGetInstantMillis_Object() throws Exception {
        assertEquals(123L, LongConverter.INSTANCE.getInstantMillis(new Long(123L)));
    }

    public void testGetInstantMillis_Object_Zone() throws Exception {
        assertEquals(123L, LongConverter.INSTANCE.getInstantMillis(new Long(123L), PARIS));
        assertEquals(123L, LongConverter.INSTANCE.getInstantMillis(new Long(123L), (DateTimeZone) null));
    }

    public void testGetInstantMillis_Object_Chronology() throws Exception {
        assertEquals(123L, LongConverter.INSTANCE.getInstantMillis(new Long(123L), JULIAN));
        assertEquals(123L, LongConverter.INSTANCE.getInstantMillis(new Long(123L), (Chronology) null));
    }

    //-----------------------------------------------------------------------
    public void testGetChronology_Object() throws Exception {
        assertEquals(ISO, LongConverter.INSTANCE.getChronology(new Long(123L)));
    }

    public void testGetChronology_Object_Zone() throws Exception {
        assertEquals(ISO_PARIS, LongConverter.INSTANCE.getChronology(new Long(123L), PARIS));
        assertEquals(ISO, LongConverter.INSTANCE.getChronology(new Long(123L), (DateTimeZone) null));
    }

    public void testGetChronology_Object_Chronology() throws Exception {
        assertEquals(JULIAN, LongConverter.INSTANCE.getChronology(new Long(123L), JULIAN));
        assertEquals(ISO, LongConverter.INSTANCE.getChronology(new Long(123L), (Chronology) null));
    }

    //-----------------------------------------------------------------------
    public void testGetDurationMillis_Object() throws Exception {
        assertEquals(123L, LongConverter.INSTANCE.getDurationMillis(new Long(123L)));
    }

    //-----------------------------------------------------------------------
    public void testGetPeriodType_Object() throws Exception {
        assertEquals(PeriodType.getAllType(),
            LongConverter.INSTANCE.getPeriodType(new Long(123L), false));
        assertEquals(PeriodType.getPreciseAllType(),
            LongConverter.INSTANCE.getPeriodType(new Long(123L), true));
    }

    public void testSetInto_Object() throws Exception {
        MutablePeriod m = new MutablePeriod(PeriodType.getMillisType());
        LongConverter.INSTANCE.setInto(m, new Long(123L));
        assertEquals(123L, m.toDurationMillis());
    }

    //-----------------------------------------------------------------------
    public void testToString() {
        assertEquals("Converter[java.lang.Long]", LongConverter.INSTANCE.toString());
    }

}
