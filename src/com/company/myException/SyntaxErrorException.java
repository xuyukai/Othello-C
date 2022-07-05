package com.company.myException;

import com.company.tools.myPair;

public class SyntaxErrorException extends Exception {
    String info;
    myPair<Integer,Integer> position;
    public SyntaxErrorException(myPair<Integer, Integer> pos){
        this.position=new myPair<Integer, Integer>(pos.getX(),pos.getY());
        info="ERROR: Syntax error at line "+(this.position.getX()+1)+" column "+this.position.getY()+" .";
    }

    @Override
    public String toString() {
        return info;
    }
}
