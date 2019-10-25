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

import org.joda.time.Chronology;
import org.joda.time.DateTimeZone;

/**
 * InstantConverter defines how an object is converted to milliseconds/chronology.
 *
 * @author Stephen Colebourne
 * @since 1.0
 */
public interface InstantConverter extends Converter {

    /**
     * Extracts the millis from an object of this converter's type.
     * 
     * @param object  the object to convert, must not be null
     * @return the millisecond instant
     * @throws ClassCastException if the object is invalid
     */
    long getInstantMillis(Object object);
    
    /**
     * Extracts the millis from an object of this converter's type. The zone
     * parameter is a hint to the converter, should it require a time zone to
     * aid in conversion.
     * 
     * @param object  the object to convert, must not be null
     * @param zone  the zone to use, null means default zone
     * @return the millisecond instant
     * @throws ClassCastException if the object is invalid
     */
    long getInstantMillis(Object object, DateTimeZone zone);
    
    /**
     * Extracts the millis from an object of this converter's type. The chrono
     * parameter is a hint to the converter, should it require a chronology to
     * aid in conversion.
     * 
     * @param object  the object to convert, must not be null
     * @param chrono  the chronology to use, null means ISOChronology
     * @return the millisecond instant
     * @throws ClassCastException if the object is invalid
     */
    long getInstantMillis(Object object, Chronology chrono);
    
    //-----------------------------------------------------------------------
    /**
     * Extracts the chronology from an object of this converter's type.
     * 
     * @param object  the object to convert, must not be null
     * @return the chronology, never null
     * @throws ClassCastException if the object is invalid
     */
    Chronology getChronology(Object object);
    
    /**
     * Extracts the chronology from an object of this converter's type
     * where the time zone is specified.
     * 
     * @param object  the object to convert, must not be null
     * @param zone  the specified zone to use, null means default zone
     * @return the chronology, never null
     * @throws ClassCastException if the object is invalid
     */
    Chronology getChronology(Object object, DateTimeZone zone);
    
    /**
     * Extracts the chronology from an object of this converter's type
     * where the chronology is specified.
     * 
     * @param object  the object to convert, must not be null
     * @param chrono  the chronology to use, null means ISOChronology
     * @return the chronology, never null
     * @throws ClassCastException if the object is invalid
     */
    Chronology getChronology(Object object, Chronology chrono);
    
}
