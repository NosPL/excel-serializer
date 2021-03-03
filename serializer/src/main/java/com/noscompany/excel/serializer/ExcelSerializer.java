package com.noscompany.excel.serializer;

import com.noscompany.excel.commons.Config;
import com.noscompany.excel.serializer.strategies.SerializationStrategyList;
import lombok.NonNull;

import java.io.File;
import java.util.Optional;

public class ExcelSerializer {
    private final SerializationStrategyList serializingStrategies;

    public ExcelSerializer(Config config) {
        this.serializingStrategies = new SerializationStrategyList(config);
    }

    public void serialize(@NonNull Iterable<?> objects, @NonNull File file) {
        Optional<SerializationStrategy> aFor = serializingStrategies
                .findFor(objects);
        aFor
                .ifPresent(strategy -> strategy.serialize(objects, file));
    }
}