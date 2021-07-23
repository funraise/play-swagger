package io.funraise.swagger.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.models.properties.StringProperty;

public class ModelResolver extends io.swagger.jackson.ModelResolver {
    public ModelResolver(ObjectMapper mapper) {
        super(mapper);
    }

    // Enum was not creating elements correctly
    @Override
    protected void _addEnumProps(Class<?> propClass, StringProperty property) {
    }
}
