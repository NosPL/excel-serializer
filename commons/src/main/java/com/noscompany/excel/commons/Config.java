package com.noscompany.excel.commons;

import lombok.Getter;
import lombok.NonNull;

import java.awt.*;

import static com.noscompany.excel.commons.Config.SheetLayout.VERTICAL;

@Getter
public class Config {
    private CellAddress startingPosition = new CellAddress(0, 0);
    private int sheetEntryPadding = 1;
    private int spacesBetweenSheetEntries = 1;
    private int spacesBetweenTables = 1;
    private SheetLayout sheetLayout = VERTICAL;
    private Color tableTitleColor = Color.ORANGE;
    private Color recordLabelsColor = Color.GREEN;
    private Color recordValuesColor = Color.GREEN;
    private Color sheetEntryBackgroundColor = Color.WHITE;
    private boolean allowNestedCollections = true;
    private boolean flattenNestedCollection = true;
    private boolean indexedTableRecords = false;

    public enum SheetLayout {
        VERTICAL, HORIZONTAL
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Config defaultConfig() {
        return new Config();
    }

    public static class Builder {
        private final Config config;

        Builder() {
            this.config = new Config();
        }

        public Builder startingPosition(int row, int column) {
            config.startingPosition = new CellAddress(row, column);
            return this;
        }

        public Builder sheetEntryPadding(int value) {
            config.sheetEntryPadding = Math.max(value, 0);
            return this;
        }

        public Builder spacesBetweenSheetEntries(int value) {
            config.spacesBetweenSheetEntries = Math.max(value, 0);
            return this;
        }

        public Builder spacesBetweenTables(int value) {
            config.spacesBetweenTables = Math.max(0, value);
            return this;
        }

        public Builder sheetLayout(@NonNull Config.SheetLayout value) {
            config.sheetLayout = value;
            return this;
        }

        public Builder tablesTitleColor(Color value) {
            config.tableTitleColor = value;
            return this;
        }

        public Builder recordLabelsColor(Color value) {
            config.recordLabelsColor = value;
            return this;
        }

        public Builder recordsColor(Color value) {
            config.recordValuesColor = value;
            return this;
        }

        public Builder sheetEntryBackgroundColor(Color value) {
            config.sheetEntryBackgroundColor = value;
            return this;
        }

        public Builder allowNestedCollections(boolean value) {
            config.allowNestedCollections = value;
            return this;
        }

        public Builder flattenNestedCollections(boolean value) {
            config.flattenNestedCollection = value;
            return this;
        }

        public Builder indexedTableRecords(boolean value) {
            config.indexedTableRecords = value;
            return this;
        }

        public Config build() {
            return config;
        }
    }
}