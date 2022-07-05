package com.company.myException;

public class NeedMainFunctionException extends Exception {
    String info = "Main function is needed.";

    @Override
    public String toString() {
        return info;
    }
}
