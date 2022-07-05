package com.company.myException;

import com.company.tools.myPair;

public class NotIdentifierException extends Exception {
    String info;
    myPair<Integer,Integer> position;
    public NotIdentifierException(myPair<Integer, Integer> pos){
        this.position=new myPair<Integer, Integer>(pos.getX(),pos.getY());
        info="ERROR: Not an identifier at line "+(this.position.getX()+1)+" .";
    }

    @Override
    public String toString() {
        return info;
    }
}
