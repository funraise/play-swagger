package com.example.dto;

import java.util.Objects;

public final class ExampleError {
    private final int code;
    private final String message;

    public ExampleError(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (ExampleError) obj;
        return this.code == that.code &&
                Objects.equals(this.message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, message);
    }

    @Override
    public String toString() {
        return "ExampleError[" +
                "code=" + code + ", " +
                "message=" + message + ']';
    }

}
