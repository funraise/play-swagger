package io.funraise.swagger;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

public final class ControllerMethod {
    private final Method method;
    private final List<String> parameters;

    public ControllerMethod(Method method, List<String> parameters) {
        this.method = method;
        this.parameters = parameters;
    }

    public Method method() {
        return method;
    }

    public List<String> parameters() {
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
