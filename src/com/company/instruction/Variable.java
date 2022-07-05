package com.company.instruction;

public class Variable {
    boolean isConstant;
    boolean isGlobal;
    String variableName;
    String funcName;

    public Variable(boolean constant,boolean global,String name,String funcName){
        isConstant=constant;
        isGlobal=global;
        variableName=name;
        this.funcName=funcName;
    }

    public void setConstant(boolean constant) {
        isConstant = constant;
    }

    public void setFuncName(String funcName) {
        this.funcName = funcName;
    }

    public void setGlobal(boolean global) {
        isGlobal = global;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    public String getFuncName() {
        return funcName;
    }

    public String getVariableName() {
        return variableName;
    }

    public boolean isGlobal() {
        return isGlobal;
    }

    public boolean isConstant() {
        return isConstant;
    }
}
