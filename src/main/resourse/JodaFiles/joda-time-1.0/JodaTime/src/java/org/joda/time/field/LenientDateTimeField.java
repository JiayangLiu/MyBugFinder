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
package org.joda.time.field;

import org.joda.time.DateTimeField;

/**
 * Converts a strict DateTimeField into a lenient one. By being lenient, the
 * set method accepts out of bounds values, performing an addition instead.
 * <p>
 * LenientDateTimeField is thread-safe and immutable.
 *
 * @author Brian S O'Neill
 * @see org.joda.time.chrono.LenientChronology
 * @see StrictDateTimeField
 * @since 1.0
 */
public class LenientDateTimeField extends DelegatedDateTimeField {

    private static final long serialVersionUID = 8714085824173290599L;

    /**
     * Returns a lenient version of the given field. If it is already lenient,
     * then it is returned as-is. Otherwise, a new LenientDateTimeField is
     * returned.
     */
    public static DateTimeField getInstance(DateTimeField field) {
        if (field == null) {
            return null;
        }
        if (field instanceof StrictDateTimeField) {
            field = ((StrictDateTimeField)field).getWrappedField();
        }
        if (field.isLenient()) {
            return field;
        }
        return new LenientDateTimeField(field);
    }

    protected LenientDateTimeField(DateTimeField field) {
        super(field);
    }

    public final boolean isLenient() {
        return true;
    }

    /**
     * Set values which may be out of bounds. If the value is out of bounds,
     * the instant is first set to the minimum allowed value, and then the
     * difference is added.
     */
    public long set(long instant, int value) {
        int min = getMinimumValue(instant);
        if (value >= min && value < getMaximumValue(instant)) {
            return super.set(instant, value);
        }
        return add(super.set(instant, min), value - min);
    }
}
