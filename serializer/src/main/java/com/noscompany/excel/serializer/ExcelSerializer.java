package com.noscompany.excel.serializer;

import com.noscompany.excel.commons.Config;
import com.noscompany.excel.commons.JessyObject;
import com.noscompany.excel.serializer.strategies.SerializationStrategyList;
import lombok.NonNull;

import java.io.File;
import java.util.List;

public class ExcelSerializer {
    private final SerializationStrategyList serializingStrategies;

    public ExcelSerializer(Config config) {
        this.serializingStrategies = new SerializationStrategyList(config);
    }

    public void serialize(JessyObject object, File file) {
        serialize(List.of(object), file);
    }

    public void serialize(@NonNull Iterable<? extends JessyObject> objects, @NonNull File file) {
        serializingStrategies
                .findFor(objects)
                .ifPresent(strategy -> strategy.serialize(objects, file));
    }
}