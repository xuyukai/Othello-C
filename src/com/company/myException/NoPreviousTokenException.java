package com.company.myException;

import com.company.tools.myPair;

public class NoPreviousTokenException extends Exception {
    String info;
    myPair<Integer,Integer> position;
    public NoPreviousTokenException(myPair<Integer, Integer> pos){
        this.position=new myPair<Integer, Integer>(pos.getX(),pos.getY());
        info="ERROR: No previous token at line "+(this.position.getX()+1)+" .";
    }

    @Override
    public String toString() {
        return info;
    }
}
