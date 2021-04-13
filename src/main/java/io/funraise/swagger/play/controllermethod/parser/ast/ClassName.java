package io.funraise.swagger.play.controllermethod.parser.ast;

import java.util.Objects;

public final class ClassName {
    private final String fullName;

    public ClassName(String fullName) {
        this.fullName = fullName;
    }

    public String fullName() {
        return fullName;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (ClassName) obj;
        return Objects.equals(this.fullName, that.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullName);
    }

    @Override
    public String toString() {
        return "ClassName[" +
                "fullName=" + fullName + ']';
    }

}
