package io.funraise.swagger.play;

import io.funraise.swagger.Route;
import io.funraise.swagger.ControllerMethod;
import io.funraise.swagger.play.controllermethod.Reflector;
import io.funraise.swagger.play.controllermethod.parser.Lexer;
import io.funraise.swagger.play.controllermethod.parser.Parser;
import java.util.regex.Pattern;

public class RouteFactory {

    private static final Pattern PATH_PATTERN = Pattern.compile("([:*$])(\\p{javaJavaIdentifierPart}*)(?:<(.*)>)?");

    private final Lexer lexer;
    private final Parser parser;
    private final Reflector reflector;

    public RouteFactory() {
        lexer = new Lexer();
        parser = new Parser();
        reflector = new Reflector();
    }

    private String path(String pathPattern) {
        return PATH_PATTERN.matcher(pathPattern).replaceAll("{$2}");
    }

    private ControllerMethod controllerMethod(String invocation) {
        var tokens = lexer.lex(invocation);
        var ast = parser.parse(tokens);
        return reflector.reflect(ast);
    }

    public Route create(String httpMethod, String pathPattern, String invocation) {
        var path = path(pathPattern);
        var controllerMethod = controllerMethod(invocation);
        return new Route(httpMethod, path, controllerMethod);
    }
}
