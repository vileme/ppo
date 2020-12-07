package com.company.Token.TokenVisitor;

import com.company.Token.Token;
import com.company.Token.TokenImpl.Bracket.BracketToken;
import com.company.Token.TokenImpl.Number.NumberToken;
import com.company.Token.TokenImpl.Operation.Operation;

import java.util.List;

public interface TokenVisitor {
    void visit(NumberToken token);
    void visit(BracketToken token);
    void visit(Operation token);
    void visit (List<Token> tokens);
}
