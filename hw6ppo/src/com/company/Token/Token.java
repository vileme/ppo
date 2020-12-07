package com.company.Token;

import com.company.Token.TokenVisitor.TokenVisitor;

public interface Token {
    void accept (TokenVisitor visitor);
}
