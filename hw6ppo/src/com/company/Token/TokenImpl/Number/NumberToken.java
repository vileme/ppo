package com.company.Token.TokenImpl.Number;

import com.company.Token.Token;
import com.company.Token.TokenVisitor.TokenVisitor;

public class NumberToken implements Token {
    private int value;

    public NumberToken(int v){
        this.value = v;
    }
    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "NUMBER : " + value;
    }
}
