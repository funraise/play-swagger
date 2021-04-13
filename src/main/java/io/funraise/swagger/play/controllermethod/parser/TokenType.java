package io.funraise.swagger.play.controllermethod.parser;

import static java.util.regex.Pattern.compile;

import java.util.regex.Pattern;

public enum TokenType {
    NULL_VALUE(compile("null")),
    STRING_VALUE(compile("\"[^\"]*\"")),
    NUMBER_VALUE(compile("-?[0-9][0-9.]*")),
    BOOLEAN_VALUE(compile("true|false")),
    LABEL(compile("\\p{javaJavaIdentifierPart}+")),
    WHITESPACE(compile(" ")),
    COMMA(compile(",")),
    PACKAGE_SEPARATOR(compile("\\.")),
    OPEN_PARENS(compile("\\(")),
    CLOSE_PARENS(compile("\\)")),
    OPEN_BRACKET(compile("\\[")),
    CLOSE_BRACKET(compile("]")),
    COLON(compile(":")),
    FIXED_ASSIGNMENT(compile("=")),
    DEFAULT_VALUE_ASSIGNMENT(compile("\\?=")),
    ;

    private final Pattern pattern;

    TokenType(Pattern pattern) {
        this.pattern = pattern;
    }

    public Pattern getPattern() {
        return pattern;
    }
}
