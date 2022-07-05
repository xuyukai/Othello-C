package com.company.myException;

import com.company.tools.myPair;

public class InvalidInputException extends Exception {
    String info;
    myPair<Integer,Integer> position;
    public InvalidInputException(myPair<Integer, Integer> pos){
        this.position=new myPair<Integer, Integer>(pos.getX(),pos.getY());
        info="ERROR: Invalid input at line "+(this.position.getX()+1)+" .";
    }

    @Override
    public String toString() {
        return info;
    }
}
