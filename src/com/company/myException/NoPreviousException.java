package com.company.myException;

import com.company.tools.myPair;

public class NoPreviousException extends Exception {
    String info;
    myPair<Integer,Integer> position;
    public NoPreviousException(myPair<Integer, Integer> pos){
        this.position=new myPair<Integer, Integer>(pos.getX(),pos.getY());
        info="ERROR: No previous character at line "+(this.position.getX()+1)+" .";
    }

    @Override
    public String toString() {
        return info;
    }

}
