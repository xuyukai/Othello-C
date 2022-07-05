package com.company.myException;

import com.company.tools.myPair;

public class NotDeclaredException extends Exception {
    String info;
    myPair<Integer,Integer> position;
    public NotDeclaredException(myPair<Integer, Integer> pos){
        this.position=new myPair<Integer, Integer>(pos.getX(),pos.getY());
        info="ERROR: Variable at line "+(this.position.getX()+1)+" column "+this.position.getY()+" is not declared.";
    }

    @Override
    public String toString() {
        return info;
    }
}
