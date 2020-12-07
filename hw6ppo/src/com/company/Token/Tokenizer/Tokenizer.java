package com.company.Token.Tokenizer;

import com.company.Token.Token;
import com.company.Token.TokenImpl.Bracket.BracketImpl.CloseBracket;
import com.company.Token.TokenImpl.Bracket.BracketImpl.OpenBracket;
import com.company.Token.TokenImpl.Number.NumberToken;
import com.company.Token.TokenImpl.Operation.OperationImpl.Divide;
import com.company.Token.TokenImpl.Operation.OperationImpl.Minus;
import com.company.Token.TokenImpl.Operation.OperationImpl.Mul;
import com.company.Token.TokenImpl.Operation.OperationImpl.Plus;

import java.util.ArrayList;
import java.util.List;

public class Tokenizer {

    private State currentState;
    private List<Token> tokens;

    public Tokenizer() {
        this.currentState = new StartStateImpl();
        this.tokens = new ArrayList<>();
    }

    private void consume(char c) {
        currentState.process(c);
    }

    public List<Token> tokenize(String expr) {
        if (!(currentState instanceof StartStateImpl)) {
            System.out.println("Tokenizer should be in start state");
        }
        expr.chars().forEach(it -> consume((char) it));
        endOfFile();
        return tokens;
    }

    private void endOfFile() {
        currentState.eof();
    }

    private abstract static class State {
        public abstract void process(char c);

        public abstract void eof();
    }

    private class IntStateImpl extends State {
        int n = 0;

        @Override
        public void process(char c) {
            if (c >= '0' && c <= '9') {
                n = n * 10 + (c - '0');
            } else {
                tokens.add(new NumberToken(n));
                currentState = new StartStateImpl();
                consume(c);
            }
        }

        @Override
        public void eof() {
            tokens.add(new NumberToken(n));
        }
    }

    private class StartStateImpl extends State {

        @Override
        public void process(char c) {
            switch (c) {
                case ' ':
                    break;
                case '(':
                    tokens.add(new OpenBracket());
                    break;
                case ')':
                    tokens.add(new CloseBracket());
                    break;
                case '+':
                    tokens.add(new Plus());
                    break;
                case '/':
                    tokens.add(new Divide());
                    break;
                case '*':
                    tokens.add(new Mul());
                    break;
                case '-':
                    tokens.add(new Minus());
                    break;
                default:
                    currentState = new IntStateImpl();
                    currentState.process(c);
            }
        }

        @Override
        public void eof() {
            return;
        }
    }
}