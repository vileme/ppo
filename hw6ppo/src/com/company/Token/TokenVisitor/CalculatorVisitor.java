package com.company.Token.TokenVisitor;

import com.company.Token.Token;
import com.company.Token.TokenImpl.Bracket.BracketToken;
import com.company.Token.TokenImpl.Number.NumberToken;
import com.company.Token.TokenImpl.Operation.Operation;
import com.company.Token.TokenImpl.Operation.OperationImpl.Divide;
import com.company.Token.TokenImpl.Operation.OperationImpl.Minus;
import com.company.Token.TokenImpl.Operation.OperationImpl.Mul;
import com.company.Token.TokenImpl.Operation.OperationImpl.Plus;

import java.util.List;
import java.util.Stack;

public class CalculatorVisitor implements TokenVisitor {

    private Stack<Integer> st = new Stack<>();

    public int getAnswer(List<Token> opz) {
        visit(opz);
        return st.pop();
    }

    @Override
    public void visit(NumberToken token) {
        st.push(token.getValue());
    }

    @Override
    public void visit(BracketToken token) {
        throw new IllegalArgumentException();
    }

    @Override
    public void visit(Operation token) {
        Integer first = st.pop();
        Integer second = st.pop();
        if (token instanceof Plus) {
            st.push(first + second);
        } else if (token instanceof Minus) {
            st.push(second - first);
        } else if (token instanceof Divide) {
            st.push(second / first);
        } else if (token instanceof Mul) {
            st.push(first * second);
        }
    }

    @Override
    public void visit(List<Token> tokens) {
        for (Token t : tokens) {
            t.accept(this);
        }
    }
}
