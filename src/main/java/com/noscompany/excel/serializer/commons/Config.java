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
    private CellAddress startingPosition = new CellAddress(0, 0);
    private int sheetEntryPadding = 1;
    private int spacesBetweenSheetEntries = 1;
    private int spacesBetweenGrids = 1;
    private SheetEntryLayout sheetLayout = TOP_TO_BOTTOM;
    private short labelColor = LIGHT_ORANGE.getIndex();
    private short recordNamesColor = LIME.getIndex();
    private short recordValuesColor = LIGHT_YELLOW.getIndex();
    private short entryBackgroundColor = WHITE.getIndex();

    public static Builder builder(@NonNull File file) {
        return new Builder(file);
    }

    public static Config defaultConfig(@NonNull File file) {
        Config config = new Config();
        config.file = file;
        return config;
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

        public Builder startingPosition(int row, int column) {
            config.startingPosition = new CellAddress(row, column);
            return this;
        }

        public Builder sheetEntryPadding(int value) {
            config.sheetEntryPadding = Math.max(value, config.sheetEntryPadding);
            return this;
        }

        public Builder spacesBetweenSheetEntries(int value) {
            config.spacesBetweenSheetEntries = Math.max(value, config.spacesBetweenSheetEntries);
            return this;
        }

        public Builder spacesBetweenGrids(int value) {
            config.spacesBetweenGrids = Math.max(config.spacesBetweenGrids, value);
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

    public enum SheetEntryLayout {
        TOP_TO_BOTTOM, LEFT_TO_RIGHT;
    }
}