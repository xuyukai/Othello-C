package com.company.myException;

import com.company.tools.myPair;

public class WrongNumberOfParameterException extends Exception {
    String info;
    myPair<Integer,Integer> position;
    public WrongNumberOfParameterException(myPair<Integer, Integer> pos){
        this.position=new myPair<Integer, Integer>(pos.getX(),pos.getY());
        info="ERROR: Function calling at line "+(this.position.getX()+1)+" column "+this.position.getY()+" has wrong number of parameters.";
    }

    @Override
    public String toString() {
        return info;
    }
}
