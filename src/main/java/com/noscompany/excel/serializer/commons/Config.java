package com.noscompany.excel.serializer.commons;

import com.noscompany.excel.serializer.ExcelSerializer;
import lombok.Getter;
import lombok.NonNull;

import java.io.File;

import static com.noscompany.excel.serializer.commons.Config.SheetEntryLayout.TOP_TO_BOTTOM;
import static org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined.*;

@Getter
public class Config {
    private File file;

    private boolean labels = true;

    private boolean omitEmptyObjects = false;

    private CellAddress startingPosition = new CellAddress(0, 0);

    private int sheetEntryPadding = 1;
    private int sheetEntryOffset = 1;
    private int entryElementOffset = 1;

    private SheetEntryLayout sheetLayout = TOP_TO_BOTTOM;

    private short labelColor = LIGHT_ORANGE.getIndex();
    private short recordNamesColor = LIME.getIndex();
    private short recordValuesColor = LIGHT_YELLOW.getIndex();
    private short entryBackgroundColor = WHITE.getIndex();

    public static Builder builder(@NonNull File file) {
        return new Builder(file);
    }

    public static class Builder {
        private Config config;

        Builder(File file) {
            this.config = new Config();
            config.file = file;
        }

        public Builder labels(boolean value) {
            config.labels = value;
            return this;
        }

        public Builder omitEmptyObjects(boolean value) {
            config.omitEmptyObjects = value;
            return this;
        }

        public Builder startingPosition(int row, int column) {
            config.startingPosition = new CellAddress(row, column);
            return this;
        }

        public Builder sheetEntryPadding(int value) {
            config.sheetEntryPadding = Math.max(value, config.sheetEntryPadding);
            return this;
        }

        public Builder sheetEntryOffset(int value) {
            config.sheetEntryOffset = Math.max(value, config.sheetEntryOffset);
            return this;
        }

        public Builder entryElementOffset(int value) {
            config.entryElementOffset = Math.max(config.entryElementOffset, value);
            return this;
        }

        public Builder sheetLayout(@NonNull Config.SheetEntryLayout value) {
            config.sheetLayout = value;
            return this;
        }

        public Builder labelColor(short value) {
            config.labelColor = value;
            return this;
        }

        public Builder recordNamesColor(short value) {
            config.recordNamesColor = value;
            return this;
        }

        public Builder recordValuesColor(short value) {
            config.recordValuesColor = value;
            return this;
        }

        public Builder entryBackgroundColor(short value) {
            config.entryBackgroundColor = value;
            return this;
        }

        public ExcelSerializer build() {
            return new ExcelSerializer(config);
        }
    }

    /**
     * represents how the object's fields are written to the excel sheet
     */
    public enum RecordLayout {
        /**
         * it makes fields to be written into excel sheet sequentially top to bottom
         * <p>|field name 1|field name 2 |field name 3 |...</p>
         * <p>|value 1.1|value 2.1|value 3.1|...</p>
         * <p>|value 2.1|value 2.2|value 3.2|...</p>
         * <p>|value 3.1|value 3.2|value 3.3|...</p>
         */
        VERTICAL,
        /**
         * it makes fields to be written into excel sheet sequentially from left to right, for example:
         * <p>|field name 1|value 1.1|value 1.2|value 1.3|...</p>
         * <p>|field name 2|value 2.1|value 2.2|value 2.3|...</p>
         * <p>|field name 3|value 3.1|value 3.2|value 3.3|</p>
         * ...
         */
        HORIZONTAL;
    }

    public enum SheetEntryLayout {
        TOP_TO_BOTTOM, LEFT_TO_RIGHT;
    }
}