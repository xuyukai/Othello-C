package com.company.instruction;

import java.util.ArrayList;

public class Function {
    String funcName;
    int paraNum;
    boolean isVoidFunc;

    public Function(String funcname,int num,boolean isVoidFunc){
        funcName=funcname;
        paraNum=num;
        this.isVoidFunc=isVoidFunc;
    }

    public String getFuncName() {
        return funcName;
    }

    public int getParaNum() {
        return paraNum;
    }

    public void setFuncName(String funcName) {
        this.funcName = funcName;
    }

    public void setParaNum(int paraNum) {
        this.paraNum = paraNum;
    }

    public void setVoidFunc(boolean voidFunc) {
        isVoidFunc = voidFunc;
    }

    public boolean isVoidFunc() {
        return isVoidFunc;
    }
}
