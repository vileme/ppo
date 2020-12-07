package com.company.Token.TokenImpl.Bracket;

import com.company.Token.Token;
import com.company.Token.TokenVisitor.TokenVisitor;

public abstract class BracketToken implements Token {
    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }
}
