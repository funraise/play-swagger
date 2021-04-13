package io.funraise.swagger.play.controllermethod.parser;

import java.util.ArrayList;
import java.util.List;

public class Lexer {

    public List<Token> lex(String invocation) {
        var substring = invocation;
        var tokens = new ArrayList<Token>();
        outer:
        while (substring.length() > 0) {
            for (var tokenType : TokenType.values()) {
                var matcher = tokenType.getPattern().matcher(substring);
                if (matcher.find() && matcher.start() == 0) {
                    var value = substring.substring(0, matcher.end());
                    tokens.add(new Token(tokenType, value));
                    substring = substring.substring(matcher.end());
                    continue outer;
                }
            }
            throw new RuntimeException("Unexpected symbol: "+substring);
        }

        return tokens;
    }


}
