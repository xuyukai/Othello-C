package com.company.myException;

public class NeedReturnStatementException extends Exception {
    String info;
    String func;
    public NeedReturnStatementException(String func){
        this.func=func;
        info="Function \""+func+"\" has a wrong return statement.";
    }
    @Override
    public String toString() {
        return info;
    }
}
