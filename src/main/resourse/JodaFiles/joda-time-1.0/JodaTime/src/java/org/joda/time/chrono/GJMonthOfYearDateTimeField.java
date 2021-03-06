/*
 *  Copyright 2001-2005 Stephen Colebourne
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.joda.time.chrono;

import java.util.Locale;

import org.joda.time.DateTimeConstants;
import org.joda.time.DateTimeFieldType;
import org.joda.time.DurationField;
import org.joda.time.field.FieldUtils;
import org.joda.time.field.ImpreciseDateTimeField;

/**
 * Provides time calculations for the month of the year component of time.
 *
 * @author Guy Allard
 * @author Stephen Colebourne
 * @author Brian S O'Neill
 * @since 1.0
 */
final class GJMonthOfYearDateTimeField extends ImpreciseDateTimeField {

    /** Serialization version */
    private static final long serialVersionUID = -4748157875845286249L;

    private static final int MIN = DateTimeConstants.JANUARY;
    private static final int MAX = DateTimeConstants.DECEMBER;

    private final BaseGJChronology iChronology;

    /**
     * Restricted constructor
     */
    GJMonthOfYearDateTimeField(BaseGJChronology chronology) {
        super(DateTimeFieldType.monthOfYear(), chronology.getAverageMillisPerMonth());
        iChronology = chronology;
    }

    public boolean isLenient() {
        return false;
    }

    /**
     * Get the Month component of the specified time instant.
     *
     * @see org.joda.time.DateTimeField#get(long)
     * @see org.joda.time.ReadableDateTime#getMonthOfYear()
     * @param instant  the time instant in millis to query.
     * @return the month extracted from the input.
     */
    public int get(long instant) {
        return iChronology.getMonthOfYear(instant);
    }

    public String getAsText(int fieldValue, Locale locale) {
        return GJLocaleSymbols.forLocale(locale).monthOfYearValueToText(fieldValue);
    }

    public String getAsShortText(int fieldValue, Locale locale) {
        return GJLocaleSymbols.forLocale(locale).monthOfYearValueToShortText(fieldValue);
    }

    /**
     * Add the specified month to the specified time instant.
     * The amount added may be negative.<p>
     * If the new month has less total days than the specified
     * day of the month, this value is coerced to the nearest
     * sane value. e.g.<p>
     * 07-31 - (1 month) = 06-30<p>
     * 03-31 - (1 month) = 02-28 or 02-29 depending<p>
     * 
     * @see org.joda.time.DateTimeField#add
     * @see org.joda.time.ReadWritableDateTime#addMonths(int)
     * @param instant  the time instant in millis to update.
     * @param months  the months to add (can be negative).
     * @return the updated time instant.
     */
    public long add(long instant, int months) {
        if (months == 0) {
            return instant; // the easy case
        }
        //
        // Save time part first.
        //
        long timePart = iChronology.getMillisOfDay(instant);
        //
        //
        // Get this year and month.
        //
        int thisYear = iChronology.getYear(instant);
        int thisMonth = iChronology.getMonthOfYear(instant, thisYear);
        // ----------------------------------------------------------
        //
        // Do not refactor without careful consideration.
        // Order of calculation is important.
        //
        int yearToUse;
        // Initially, monthToUse is zero-based
        int monthToUse = thisMonth - 1 + months;
        if (monthToUse >= 0) {
            yearToUse = thisYear + (monthToUse / MAX);
            monthToUse = (monthToUse % MAX) + 1;
        } else {
            yearToUse = thisYear + (monthToUse / MAX) - 1;
            monthToUse = Math.abs(monthToUse);
            int remMonthToUse = monthToUse % MAX;
            // Take care of the boundary condition
            if (remMonthToUse == 0) {
                remMonthToUse = MAX;
            }
            monthToUse = MAX - remMonthToUse + 1;
            // Take care of the boundary condition
            if (monthToUse == 1) {
                yearToUse += 1;
            }
        }
        // End of do not refactor.
        // ----------------------------------------------------------

        //
        // Quietly force DOM to nearest sane value.
        //
        int dayToUse = iChronology.getDayOfMonth(instant, thisYear, thisMonth);
        int maxDay = iChronology.getDaysInYearMonth(yearToUse, monthToUse);
        if (dayToUse > maxDay) {
            dayToUse = maxDay;
        }
        //
        // get proper date part, and return result
        //
        long datePart =
            iChronology.getYearMonthDayMillis(yearToUse, monthToUse, dayToUse);
        return datePart + timePart;
    }

    public long add(long instant, long months) {
        int i_months = (int)months;
        if (i_months == months) {
            return add(instant, i_months);
        }

        // Copied from add(long, int) and modified slightly:

        long timePart = iChronology.getMillisOfDay(instant);

        int thisYear = iChronology.getYear(instant);
        int thisMonth = iChronology.getMonthOfYear(instant, thisYear);

        long yearToUse;
        long monthToUse = thisMonth - 1 + months;
        if (monthToUse >= 0) {
            yearToUse = thisYear + (monthToUse / MAX);
            monthToUse = (monthToUse % MAX) + 1;
        } else {
            yearToUse = thisYear + (monthToUse / MAX) - 1;
            monthToUse = Math.abs(monthToUse);
            int remMonthToUse = (int)(monthToUse % MAX);
            if (remMonthToUse == 0) {
                remMonthToUse = MAX;
            }
            monthToUse = MAX - remMonthToUse + 1;
            if (monthToUse == 1) {
                yearToUse += 1;
            }
        }

        if (yearToUse < iChronology.getMinYear() ||
            yearToUse > iChronology.getMaxYear()) {

            throw new IllegalArgumentException
                ("Magnitude of add amount is too large: " + months);
        }

        int i_yearToUse = (int)yearToUse;
        int i_monthToUse = (int)monthToUse;

        int dayToUse = iChronology.getDayOfMonth(instant, thisYear, thisMonth);
        int maxDay = iChronology.getDaysInYearMonth(i_yearToUse, i_monthToUse);
        if (dayToUse > maxDay) {
            dayToUse = maxDay;
        }

        long datePart =
            iChronology.getYearMonthDayMillis(i_yearToUse, i_monthToUse, dayToUse);
        return datePart + timePart;
    }

    /**
     * Add to the Month component of the specified time instant
     * wrapping around within that component if necessary.
     * 
     * @see org.joda.time.DateTimeField#addWrapField
     * @param instant  the time instant in millis to update.
     * @param months  the months to add (can be negative).
     * @return the updated time instant.
     */
    public long addWrapField(long instant, int months) {
        return set(instant, FieldUtils.getWrappedValue(get(instant), months, MIN, MAX));
    }

    public long getDifferenceAsLong(long minuendInstant, long subtrahendInstant) {
        if (minuendInstant < subtrahendInstant) {
            return -getDifference(subtrahendInstant, minuendInstant);
        }

        int minuendYear = iChronology.getYear(minuendInstant);
        int minuendMonth = iChronology.getMonthOfYear(minuendInstant, minuendYear);
        int subtrahendYear = iChronology.getYear(subtrahendInstant);
        int subtrahendMonth = iChronology.getMonthOfYear(subtrahendInstant, subtrahendYear);

        long difference = (minuendYear - subtrahendYear) * 12L + minuendMonth - subtrahendMonth;

        // Before adjusting for remainder, account for special case of add
        // where the day-of-month is forced to the nearest sane value.
        int minuendDom = iChronology.getDayOfMonth
            (minuendInstant, minuendYear, minuendMonth);
        if (minuendDom == iChronology.getDaysInYearMonth(minuendYear, minuendMonth)) {
            // Last day of the minuend month...
            int subtrahendDom = iChronology.getDayOfMonth
                (subtrahendInstant, subtrahendYear, subtrahendMonth);
            if (subtrahendDom > minuendDom) {
                // ...and day of subtrahend month is larger.
                // Note: This works fine, but it ideally shouldn't invoke other
                // fields from within a field.
                subtrahendInstant = iChronology.dayOfMonth().set(subtrahendInstant, minuendDom);
            }
        }

        // Inlined remainder method to avoid duplicate calls.
        long minuendRem = minuendInstant
            - iChronology.getYearMonthMillis(minuendYear, minuendMonth);
        long subtrahendRem = subtrahendInstant
            - iChronology.getYearMonthMillis(subtrahendYear, subtrahendMonth);

        if (minuendRem < subtrahendRem) {
            difference--;
        }

        return difference;
    }

    /**
     * Set the Month component of the specified time instant.<p>
     * If the new month has less total days than the specified
     * day of the month, this value is coerced to the nearest
     * sane value. e.g.<p>
     * 07-31 to month 6 = 06-30<p>
     * 03-31 to month 2 = 02-28 or 02-29 depending<p>
     * 
     * @param instant  the time instant in millis to update.
     * @param month  the month (1,12) to update the time to.
     * @return the updated time instant.
     * @throws IllegalArgumentException  if month is invalid
     */
    public long set(long instant, int month) {
        FieldUtils.verifyValueBounds(this, month, MIN, MAX);
        //
        int thisYear = iChronology.getYear(instant);
        //
        int thisDom = iChronology.getDayOfMonth(instant, thisYear);
        int maxDom = iChronology.getDaysInYearMonth(thisYear, month);
        if (thisDom > maxDom) {
            // Quietly force DOM to nearest sane value.
            thisDom = maxDom;
        }
        // Return newly calculated millis value
        return iChronology.getYearMonthDayMillis(thisYear, month, thisDom) +
            iChronology.getMillisOfDay(instant);
    }

    /**
     * Convert the specified text and locale into a value.
     * 
     * @param text  the text to convert
     * @param locale  the locale to convert using
     * @return the value extracted from the text
     * @throws IllegalArgumentException if the text is invalid
     */
    protected int convertText(String text, Locale locale) {
        return GJLocaleSymbols.forLocale(locale).monthOfYearTextToValue(text);
    }

    public DurationField getRangeDurationField() {
        return iChronology.years();
    }

    public boolean isLeap(long instant) {
        int thisYear = iChronology.getYear(instant);
        int thisMonth = iChronology.getMonthOfYear(instant, thisYear);
        if (thisMonth != 2) {
            return false;
        } else {
            return 29 == iChronology.getDaysInYearMonth(thisYear, thisMonth);
        }
    }

    public int getLeapAmount(long instant) {
        return isLeap(instant) ? 1 : 0;
    }

    public DurationField getLeapDurationField() {
        return iChronology.days();
    }

    public int getMinimumValue() {
        return MIN;
    }

    public int getMaximumValue() {
        return MAX;
    }

    public int getMaximumTextLength(Locale locale) {
        return GJLocaleSymbols.forLocale(locale).getMonthMaxTextLength();
    }

    public int getMaximumShortTextLength(Locale locale) {
        return GJLocaleSymbols.forLocale(locale).getMonthMaxShortTextLength();
    }

    public long roundFloor(long instant) {
        int year = iChronology.getYear(instant);
        int month = iChronology.getMonthOfYear(instant, year);
        return iChronology.getYearMonthMillis(year, month);
    }

    public long remainder(long instant) {
        return instant - roundFloor(instant);
    }

    /**
     * Serialization singleton
     */
    private Object readResolve() {
        return iChronology.monthOfYear();
    }
}
