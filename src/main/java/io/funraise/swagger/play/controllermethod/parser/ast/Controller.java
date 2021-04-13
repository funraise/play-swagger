package io.funraise.swagger.play.controllermethod.parser.ast;

import java.util.Objects;

public final class Controller {
    private final ClassName className;
    private final Method method;

    public Controller(ClassName className, Method method) {
        this.className = className;
        this.method = method;
    }

    public ClassName className() {
        return className;
    }

    public Method method() {
        return method;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Controller) obj;
        return Objects.equals(this.className, that.className) &&
                Objects.equals(this.method, that.method);
    }

    @Override
    public int hashCode() {
        return Objects.hash(className, method);
    }

    @Override
    public String toString() {
        return "Controller[" +
                "className=" + className + ", " +
                "method=" + method + ']';
    }

}
