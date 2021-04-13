package io.funraise.swagger;

import java.util.Objects;

public final class Route {
    private final String httpMethod;
    private final String path;
    private final ControllerMethod controllerMethod;

    public Route(String httpMethod, String path, ControllerMethod controllerMethod) {
        this.httpMethod = httpMethod;
        this.path = path;
        this.controllerMethod = controllerMethod;
    }

    public String httpMethod() {
        return httpMethod;
    }

    public String path() {
        return path;
    }

    public ControllerMethod controllerMethod() {
        return controllerMethod;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Route) obj;
        return Objects.equals(this.httpMethod, that.httpMethod) &&
                Objects.equals(this.path, that.path) &&
                Objects.equals(this.controllerMethod, that.controllerMethod);
    }

    @Override
    public int hashCode() {
        return Objects.hash(httpMethod, path, controllerMethod);
    }

    @Override
    public String toString() {
        return "Route[" +
                "httpMethod=" + httpMethod + ", " +
                "path=" + path + ", " +
                "controllerMethod=" + controllerMethod + ']';
    }

}
