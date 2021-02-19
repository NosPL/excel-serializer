package com.noscompany.excel.converter;

import lombok.Getter;
import lombok.NonNull;

import java.io.File;

import static com.noscompany.excel.converter.Config.MainObjectWritingDirection.TOP_TO_BOTTOM;
import static com.noscompany.excel.converter.Config.SimpleFieldsOrientation.VERTICAL;

@Getter
public class Config {
    private boolean objectLabel = false;
    private boolean collectionLabels = true;
    private SimpleFieldsOrientation simpleFieldsOrientation = VERTICAL;
    private File file;
    private int entryPadding;
    private int spaceBetweenEntries = 0;
    private int spaceBetweenCollections = 0;
    private int spaceBetweenSimpleFieldsAndCollections = 0;
    private MainObjectWritingDirection mainObjectWritingDirection = TOP_TO_BOTTOM;
    private CellAddress startingPosition = new CellAddress(0, 0);

    public int mainObjectLabelOffset() {
        return objectLabel ? 1 : 0;
    }

    public static Builder builder(File file) {
        return new Builder(file);
    }

    static class Builder {
        private Config config;

        Builder(File file) {
            this.config = new Config();
            config.file = file;
        }

        public Builder objectLabel(boolean value) {
            config.objectLabel = value;
            return this;
        }

        public Builder collectionLabels(boolean value) {
            config.collectionLabels = value;
            return this;
        }

        public Builder simpleFieldsOrientation(@NonNull SimpleFieldsOrientation value) {
            config.simpleFieldsOrientation = value;
            return this;
        }

        public Builder file(@NonNull File value) {
            config.file = value;
            return this;
        }

        public Builder spaceBetweenEntries(int value) {
            config.spaceBetweenEntries = Math.max(value, 0);
            return this;
        }

        public Builder spaceBetweenSimpleFieldsAndCollections(int value) {
            config.spaceBetweenSimpleFieldsAndCollections = Math.max(value, 0);
            return this;
        }

        public Builder spaceBetweenCollections(int value) {
            config.spaceBetweenCollections = Math.max(value, 0);
            return this;
        }

        public Builder mainObjectWritingDirection(@NonNull Config.MainObjectWritingDirection value) {
            config.mainObjectWritingDirection = value;
            return this;
        }

        public Builder startingPosition(@NonNull int row, int column) {
            config.startingPosition = new CellAddress(row, column);
            return this;
        }

        public ExcelGenerator build() {
            return new ExcelGenerator(config);
        }

        public Builder entryPadding(int value) {
            config.entryPadding = value;
            return this;
        }
    }

    /**
     * represents how the object's fields are written to the excel sheet
     */
    public enum SimpleFieldsOrientation {
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

    public enum MainObjectWritingDirection {
        TOP_TO_BOTTOM, LEFT_TO_RIGHT;
    }
}
