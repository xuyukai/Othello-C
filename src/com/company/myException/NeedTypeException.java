package com.company.myException;

import com.company.tools.myPair;

public class NeedTypeException extends Exception {
    String info;
    myPair<Integer,Integer> position;
    public NeedTypeException(myPair<Integer, Integer> pos){
        this.position=new myPair<Integer, Integer>(pos.getX(),pos.getY());
        info="ERROR: Type needed at line "+(this.position.getX()+1)+" .";
    }

    @Override
    public String toString() {
        return info;
    }
}
