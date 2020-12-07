package com.company.Token.TokenImpl.Operation;

import com.company.Token.Token;
import com.company.Token.TokenVisitor.TokenVisitor;

public abstract class Operation implements Token {
    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }
}
