package com.company.myException;

import com.company.tools.myPair;

public class AlreadyDeclaredException extends Exception {
    String info;
    myPair<Integer,Integer> position;
    public AlreadyDeclaredException(myPair<Integer, Integer> pos){
        this.position=new myPair<Integer, Integer>(pos.getX(),pos.getY());
        info="ERROR: Identifier at line "+(this.position.getX()+1)+" is already declared.";
    }

    @Override
    public String toString() {
        return info;
    }
}
