package com.company.token;

import com.company.tools.myPair;

public class Token {
    TokenType type;
    myPair<Integer,Integer> startPos;
    myPair<Integer,Integer> endPos;
    String value;

    Token(TokenType type,String value, int start_line,int start_column,int end_line,int end_column){
        this.type=type;
        this.value=value;
        this.startPos= new myPair<>(start_line, start_column);
        this.endPos= new myPair<>(end_line, end_column);
    }

    Token(TokenType type,String value, myPair<Integer,Integer> start,myPair<Integer,Integer> end){
        this.type=type;
        this.value=value;
        this.startPos= new myPair<>(start.getX(), start.getY());
        this.endPos= new myPair<>(end.getX(), end.getY());
    }

    public myPair<Integer,Integer> getStartPos() {
        return startPos;
    }

    public myPair<Integer,Integer> getEndPos() {
        return endPos;
    }

    public String getValue() {
        return value;
    }

    public TokenType getType() {
        return type;
    }
}
