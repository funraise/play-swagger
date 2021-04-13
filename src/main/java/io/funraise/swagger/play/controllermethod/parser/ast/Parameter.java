package io.funraise.swagger.play.controllermethod.parser.ast;

import java.util.Objects;

public final class Parameter {
    private final String name;
    private final String type;

    public Parameter(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String name() {
        return name;
    }

    public String type() {
        return type;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Parameter) obj;
        return Objects.equals(this.name, that.name) &&
                Objects.equals(this.type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type);
    }

    @Override
    public String toString() {
        return "Parameter[" +
                "name=" + name + ", " +
                "type=" + type + ']';
    }

}
