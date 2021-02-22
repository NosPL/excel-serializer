package com.noscompany.excel.serializer.sheet.entry.grid;

/**
 * represents how the object's fields are written to the excel sheet
 */
public enum GridLayout {
    /**
     * it makes fields to be written into excel sheet sequentially top to bottom
     * <p>|field name 1|field name 2 |field name 3 |...</p>
     * <p>|value 1.1|value 2.1|value 3.1|...</p>
     * <p>|value 2.1|value 2.2|value 3.2|...</p>
     * <p>|value 3.1|value 3.2|value 3.3|...</p>
     */
    HORIZONTAL,
    /**
     * it makes fields to be written into excel sheet sequentially from left to right, for example:
     * <p>|field name 1|value 1.1|value 1.2|value 1.3|...</p>
     * <p>|field name 2|value 2.1|value 2.2|value 2.3|...</p>
     * <p>|field name 3|value 3.1|value 3.2|value 3.3|</p>
     * ...
     */
    VERTICAL;
}
