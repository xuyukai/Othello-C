package com.company.myException;

import com.company.tools.myPair;

public class NoChangeConstException extends Exception {
    String info;
    myPair<Integer,Integer> position;
    public NoChangeConstException(myPair<Integer, Integer> pos){
        this.position=new myPair<Integer, Integer>(pos.getX(),pos.getY());
        info="ERROR: Cannot change the value of a constant at line "+(this.position.getX()+1)+" .";
    }

    @Override
    public String toString() {
        return info;
    }
}
