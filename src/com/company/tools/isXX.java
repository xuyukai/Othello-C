package com.company.tools;

public class isXX {

    //Finished
    public boolean isSpace(char ch){
        return ch == 0x20 ||ch==0x09|| ch == 0x0a || ch == 0x0b ||ch==0x0d;
    }
    //UF
    public boolean isPrint(char ch){
        return ch>=0x20&&ch<=0x7e;
    }

    //F
    public boolean isDigit(char ch){
        return ch >= 48 && ch <= 57;
    }

    //F
    public boolean isLetter(char ch){
        return (ch >= 65 && ch <= 90) || (ch >= 97 && ch <= 122);
    }
}
