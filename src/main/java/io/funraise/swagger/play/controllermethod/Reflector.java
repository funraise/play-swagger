package io.funraise.swagger.play.controllermethod;

import io.funraise.swagger.ControllerMethod;
import io.funraise.swagger.play.controllermethod.parser.ast.Controller;
import io.funraise.swagger.play.controllermethod.parser.ast.Parameter;
import io.funraise.swagger.util.ParameterTypeComparator;
import java.util.Arrays;
import java.util.stream.Collectors;
import org.apache.commons.lang3.ClassUtils;

public class Reflector {
    private final ClassLoader classLoader;

    public Reflector() {
        classLoader = Reflector.class.getClassLoader();
    }

    public Reflector(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public ControllerMethod reflect(Controller controller) {
        try {
            Class<?> controllerClass = Class.forName(controller.className().fullName(), false, classLoader);
            var comparator = new ParameterTypeComparator();
            var params = controller
                .method()
                .parameters();
            var paramTypes = params
                .stream()
                .map(parameter -> guessType(classLoader, parameter.type()))
                .toArray(Class<?>[]::new);

            var method = Arrays
                .stream(controllerClass.getMethods())
                .filter(m -> m.getName().equals(controller.method().name()))
                .filter(m -> Arrays.equals(m.getParameterTypes(), paramTypes, comparator))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Could not find method: "+ controller));

            var parameters = params.stream().map(Parameter::name).collect(Collectors.toList());

            return new ControllerMethod(method, parameters);

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static Class<?> guessType(ClassLoader classLoader, String type) {
        try {
            switch (type) {
                case "Request":
                    type = "play.mvc.Http.Request";
                    break;
                case "Int":
                    type = "java.lang.Integer";
                    break;
                case "Asset":
                    type = "controllers.Assets.Asset";
                    break;
            }
            // ClassUtils to handle for inner-class
            return ClassUtils.getClass(classLoader, type, false);
        } catch (ClassNotFoundException e1) {
            try {
                return Class.forName("java.lang."+type, false, classLoader);
            } catch (ClassNotFoundException e2) {
                throw new RuntimeException("Type: "+type, e1);
            }
        }
    }
}
