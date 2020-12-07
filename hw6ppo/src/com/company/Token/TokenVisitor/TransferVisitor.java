package com.company.Token.TokenVisitor;

import com.company.Token.Token;
import com.company.Token.TokenImpl.Bracket.BracketImpl.CloseBracket;
import com.company.Token.TokenImpl.Bracket.BracketImpl.OpenBracket;
import com.company.Token.TokenImpl.Bracket.BracketToken;
import com.company.Token.TokenImpl.Number.NumberToken;
import com.company.Token.TokenImpl.Operation.Operation;
import com.company.Token.TokenImpl.Operation.OperationImpl.Divide;
import com.company.Token.TokenImpl.Operation.OperationImpl.Mul;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class TransferVisitor implements TokenVisitor {
    public List<Token> getOpzExpr(List<Token> tokens) {
        visit(tokens);
        return opzExpr;
    }

    private List<Token> opzExpr = new ArrayList<>();
    public Stack<Token> st = new Stack<>();

    private int getPrio(Token token) {
        if (token instanceof Divide || token instanceof Mul) {
            return 3;
        } else if (token instanceof BracketToken) {
            return 1;
        } else return 2;
    }

    @Override
    public void visit(NumberToken token) {
        opzExpr.add(token);
    }

    @Override
    public void visit(BracketToken token) {
        if (token instanceof OpenBracket) {
            st.push(token);
        } else if (token instanceof CloseBracket) {
            while (!(st.peek() instanceof OpenBracket)) {
                opzExpr.add(st.pop());
            }
            st.pop();
        }
    }

    @Override
    public void visit(Operation token) {
        while (!st.empty()) {
            Token lastOp = st.peek();
            if (getPrio(lastOp) < getPrio(token)) {
                st.push(token);
                return;
            } else {
                opzExpr.add(lastOp);
                st.pop();
            }
        }
        st.push(token);

    }

    @Override
    public void visit(List<Token> tokens) {
        for (Token t : tokens) {
            t.accept(this);
        }
        while (!st.empty()) {
            opzExpr.add(st.pop());
        }
    }
}
