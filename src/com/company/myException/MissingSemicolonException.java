package com.company.myException;

import com.company.tools.myPair;

public class MissingSemicolonException extends Exception {
    String info;
    myPair<Integer,Integer> position;
    public MissingSemicolonException(myPair<Integer, Integer> pos){
        this.position=new myPair<Integer, Integer>(pos.getX(),pos.getY());
        info="ERROR: ';' is missing at line "+(this.position.getX()+1)+" .";
    }

    @Override
    public String toString() {
        return info;
    }
}
