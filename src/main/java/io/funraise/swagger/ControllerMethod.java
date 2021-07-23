package io.funraise.swagger;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

public final class ControllerMethod {
    public final static class Parameter {
        private final String name;
        private final Class<?> type;

        public Parameter(String name, Class<?> type) {
            this.name = name;
            this.type = type;
        }

        public String name() {
            return name;
        }

        public Class<?> type() {
            return type;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Parameter parameter = (Parameter) o;
            return Objects.equals(name, parameter.name) && Objects.equals(type, parameter.type);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, type);
        }

        @Override
        public String toString() {
            return "Parameter{" +
                "name='" + name + '\'' +
                ", type=" + type +
                '}';
        }
    }

    private final Method method;
    private final List<Parameter> parameters;

    public ControllerMethod(Method method, List<Parameter> parameters) {
        this.method = method;
        this.parameters = parameters;
    }

    public Method method() {
        return method;
    }

    public List<Parameter> parameters() {
        return parameters;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (ControllerMethod) obj;
        return Objects.equals(this.method, that.method) &&
                Objects.equals(this.parameters, that.parameters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, parameters);
    }

    @Override
    public String toString() {
        return "ControllerMethod[" +
                "method=" + method + ", " +
                "parameters=" + parameters + ']';
    }

}
