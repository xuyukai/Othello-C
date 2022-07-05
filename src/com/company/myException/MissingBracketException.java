package com.company.myException;

import com.company.tools.myPair;

public class MissingBracketException extends Exception {
    String info;
    myPair<Integer,Integer> position;
    public MissingBracketException(myPair<Integer, Integer> pos){
        this.position=new myPair<Integer, Integer>(pos.getX(),pos.getY());
        info="ERROR: Bracket is missing at line "+(this.position.getX()+1)+" column "+this.position.getY()+" .";
    }

    @Override
    public String toString() {
        return info;
    }
}
