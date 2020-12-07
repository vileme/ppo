package com.company;

import com.company.Token.Token;
import com.company.Token.TokenVisitor.CalculatorVisitor;
import com.company.Token.TokenVisitor.TransferVisitor;
import com.company.Token.Tokenizer.Tokenizer;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String expr = in.nextLine();
        Tokenizer tokenizer = new Tokenizer();
        List<Token> tokens = tokenizer.tokenize(expr);
        TransferVisitor tf = new TransferVisitor();
        List<Token> opz = tf.getOpzExpr(tokens);
        System.out.println(opz);
        CalculatorVisitor cv = new CalculatorVisitor();
        System.out.println(cv.getAnswer(opz));
    }
}
