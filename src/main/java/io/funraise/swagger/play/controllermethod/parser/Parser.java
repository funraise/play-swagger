package io.funraise.swagger.play.controllermethod.parser;

import io.funraise.swagger.play.controllermethod.parser.ast.Controller;
import io.funraise.swagger.play.controllermethod.parser.ast.ClassName;
import io.funraise.swagger.play.controllermethod.parser.ast.Method;
import io.funraise.swagger.play.controllermethod.parser.ast.Parameter;
import io.funraise.swagger.play.controllermethod.parser.ast.UnexpectedToken;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

public class Parser {
    /*
    <controller> ::= <class-name> "." <method>
    <class-name> ::= <label> | <label> "[" <class-name> "]" | <label> "." <class-name>
    <method> ::= <label> "(" <parameter-list> ")"
    <parameter-list> ::= <parameter> | <parameter> "," <parameter-list>
    <parameter> ::= <label> | <label> ":" <class-name> | <label> ":" <class-name> <assignment> <value>
    <assignment> ::= "=" | "?="
    <value> ::= "NULL_VALUE" | "STRING_VALUE" | "BOOLEAN_VALUE"
     */
    public Controller parse(List<Token> tokens) {
        var iterator = tokens.listIterator();
        var className = className(iterator);
        var method = method(iterator);

        return new Controller(className, method);
    }

    private ClassName className(ListIterator<Token> iterator) {
        var tokens = fqcn(iterator);
        var fullName = tokens
            .subList(0, tokens.size() - 1)
            .stream()
            .map(Token::value)
            .collect(Collectors.joining("."));

        expect(iterator, TokenType.OPEN_PARENS);

        iterator.previous();
        iterator.previous();

        return new ClassName(fullName);
    }

    private List<Token> fqcn(ListIterator<Token> iterator) {
        List<Token> labels = new ArrayList<>();
        var token = expect(iterator, TokenType.LABEL);
        labels.add(token);
        token = iterator.next();
        while (token.type().equals(TokenType.PACKAGE_SEPARATOR)) {
            token = expect(iterator, TokenType.LABEL);
            labels.add(token);
            token = iterator.next();
        }

        if (token.type().equals(TokenType.OPEN_BRACKET)) {
            fqcn(iterator);
            expect(iterator, TokenType.CLOSE_BRACKET);
        } else {
            iterator.previous();
        }

        return labels;
    }

    private Method method(ListIterator<Token> iterator) {
        var methodNameToken = expect(iterator, TokenType.LABEL);

        expect(iterator, TokenType.OPEN_PARENS);

        var token = iterator.next();
        if (token.type().equals(TokenType.CLOSE_PARENS)) {
            return new Method(
                methodNameToken.value(),
                List.of()
            );
        } else {
            token = iterator.previous();
            List<Parameter> parameters = new ArrayList<>();
            while (!token.type().equals(TokenType.CLOSE_PARENS)) {
                parameters.add(parameter(iterator));
                token = iterator.next();
                if (token.type().equals(TokenType.COMMA)) {
                    token = iterator.next();
                }
            }

            return new Method(
                methodNameToken.value(),
                parameters
            );
        }
    }

    private Parameter parameter(ListIterator<Token> iterator) {
        consumeWhitespace(iterator);
        var parameterNameToken = expect(iterator, TokenType.LABEL);
        consumeWhitespace(iterator);
        var token = iterator.next();

        String parameterType = "";
        if (token.type().equals(TokenType.COLON)) {
            consumeWhitespace(iterator);
            parameterType = fqcn(iterator).stream().map(Token::value).collect(Collectors.joining("."));
        }
        consumeWhitespace(iterator);

        token = iterator.next();
        if (token.type().equals(TokenType.FIXED_ASSIGNMENT) || token.type().equals(TokenType.DEFAULT_VALUE_ASSIGNMENT)) {
            consumeWhitespace(iterator);
            expect(iterator, TokenType.STRING_VALUE, TokenType.NUMBER_VALUE, TokenType.NULL_VALUE, TokenType.BOOLEAN_VALUE);
            consumeWhitespace(iterator);
        } else {
            iterator.previous();
        }

        return new Parameter(parameterNameToken.value(), parameterType);
    }

    private void consumeWhitespace(ListIterator<Token> iterator) {
        var token = iterator.next();
        while (token.type().equals(TokenType.WHITESPACE)) {
            token = iterator.next();
        }
        iterator.previous();
    }

    private Token expect(ListIterator<Token> iterator, TokenType... type) {
        Token token = iterator.next();
        if (!List.of(type).contains(token.type())) {
            throw new UnexpectedToken(token, type);
        }
        return token;
    }
}
