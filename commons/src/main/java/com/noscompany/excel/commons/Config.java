package com.noscompany.excel.commons;

import io.vavr.control.Option;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.awt.*;

import static com.noscompany.excel.commons.Config.SheetLayout.VERTICAL;
import static lombok.AccessLevel.PACKAGE;

@Getter
public class Config {
    private CellAddress startingPosition = new CellAddress(0, 0);
    private int spacesBetweenSheetEntries = 1;
    private int spacesBetweenTables = 1;
    private SheetLayout sheetLayout = VERTICAL;
    private Color tableTitleColor = Color.GREEN;
    private Color recordLabelsColor = Color.CYAN;
    private Color recordIndexColor = Color.PINK;
    private Color recordValuesColor = Color.WHITE;
    private boolean allowNestedCollections = true;
    private boolean flattenNestedCollection = true;
    private boolean indexedTableRecords = false;
    private Option<BackgroundConfig> backgroundConfig = Option.none();

    public enum SheetLayout {
        VERTICAL, HORIZONTAL
    }

    public int getSheetEntryPadding() {
        return backgroundConfig
                .map(BackgroundConfig::getPadding)
                .getOrElse(0);
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

        public Builder setBackground(int padding) {
            config.backgroundConfig = config.backgroundConfig
                    .map(bc -> bc.padding(padding))
                    .orElse(Option.of(new BackgroundConfig(padding, Color.WHITE)));
            return this;
        }

        public Builder setBackground(Color color) {
            config.backgroundConfig = config.backgroundConfig
                    .map(bc -> bc.color(color))
                    .orElse(Option.of(new BackgroundConfig(0, color)));
            return this;
        }

        public Builder setBackground(int padding, Color color) {
            config.backgroundConfig = Option.of(new BackgroundConfig(padding, color));
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

        public Builder recordIndexColor(Color value) {
            config.recordIndexColor = value;
            return this;
        }

        public Builder recordsColor(Color value) {
            config.recordValuesColor = value;
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

    @Getter
    @AllArgsConstructor(access = PACKAGE)
    public static class BackgroundConfig {
        int padding;
        Color color;

        BackgroundConfig padding(int padding) {
            this.padding = padding;
            return this;
        }

        BackgroundConfig color(Color color) {
            this.color = color;
            return this;
        }
    }
}