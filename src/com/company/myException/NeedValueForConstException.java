package com.company.myException;

import com.company.tools.myPair;

public class NeedValueForConstException extends Exception {
    String info;
    myPair<Integer,Integer> position;
    public NeedValueForConstException(myPair<Integer, Integer> pos){
        this.position=new myPair<Integer, Integer>(pos.getX(),pos.getY());
        info="ERROR: Constant needed value at line "+(this.position.getX()+1)+" column "+this.position.getY()+" .";
    }

    @Override
    public String toString() {
        return info;
    }
}
