package io.funraise.swagger.play.controllermethod.parser.ast;

import java.util.List;
import java.util.Objects;

public final class Method {
    private final String name;
    private final List<Parameter> parameters;

    public Method(String name, List<Parameter> parameters) {
        this.name = name;
        this.parameters = parameters;
    }

    public String name() {
        return name;
    }

    public List<Parameter> parameters() {
        return parameters;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Method) obj;
        return Objects.equals(this.name, that.name) &&
                Objects.equals(this.parameters, that.parameters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, parameters);
    }

    @Override
    public String toString() {
        return "Method[" +
                "name=" + name + ", " +
                "parameters=" + parameters + ']';
    }

}
