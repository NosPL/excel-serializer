package com.noscompany.excel.serializer;

import java.io.File;

public interface SerializationStrategy {
    void serialize(Iterable<?> objects, File file);

    boolean accepts(Iterable<?> objects);
}
