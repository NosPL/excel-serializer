package com.noscompany.excel.serializer.strategies;

import com.noscompany.excel.commons.Config;
import com.noscompany.excel.serializer.SerializationStrategy;

import java.util.List;
import java.util.Optional;

public class SerializationStrategyList {
    private final List<SerializationStrategy> serializingStrategies;

    public SerializationStrategyList(Config config) {
        this.serializingStrategies = List.of(
                new FlatObjectsStrategy(config),
                new ComplexObjectsStrategy(config));
    }

    public Optional<SerializationStrategy> findFor(Iterable<?> objects) {
        return serializingStrategies
                .stream()
                .filter(strategy -> strategy.accepts(objects))
                .findFirst();
    }
}
