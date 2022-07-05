package com.company.tools;

public class myPair<T,F> {
    private T x;
    private F y;

    public myPair(T x, F y){
        this.x=x;
        this.y=y;
    }

    public F getY() {
        return y;
    }

    public T getX() {
        return x;
    }

    public void setX(T x) {
        this.x = x;
    }

    public void setY(F y) {
        this.y = y;
    }

}
