package com.company.myException;

import com.company.tools.myPair;

public class NoNeedOfReturnException extends Exception {
    String info;
    myPair<Integer,Integer> position;
    public NoNeedOfReturnException(myPair<Integer, Integer> pos){
        this.position= new myPair<>(pos.getX(), pos.getY());
        info="ERROR: Cannot return a certain value in a \'void\' function at line "+(this.position.getX()+1)+" .";
    }

    @Override
    public String toString() {
        return info;
    }
}
