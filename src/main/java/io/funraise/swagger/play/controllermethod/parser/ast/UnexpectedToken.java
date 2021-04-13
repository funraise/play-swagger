package io.funraise.swagger.play.controllermethod.parser.ast;

import io.funraise.swagger.play.controllermethod.parser.Token;
import io.funraise.swagger.play.controllermethod.parser.TokenType;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UnexpectedToken extends RuntimeException {
    public UnexpectedToken(Token actual, TokenType... expected) {
        super("Expecting: " + Stream
            .of(expected)
            .map(TokenType::toString)
            .collect(Collectors.joining(", ")) + " Got: " + actual);
    }
}
