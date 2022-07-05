package com.company.analyser;

public class myIdentifier {
    boolean ifConst;
    int level;
    String value;

    myIdentifier(boolean ifConst) {
        this.ifConst = ifConst;
    }

    myIdentifier(int level) {
        this.level = level;
    }

    myIdentifier(String value) {
        this.value = value;
    }

    myIdentifier(boolean ifConst, int level) {
        this.ifConst = ifConst;
        this.level = level;
    }

    myIdentifier(boolean ifConst, int level, String value) {
        this.ifConst = ifConst;
        this.level = level;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public int getLevel() {
        return level;
    }

    public boolean getIsIfConst() {
        return ifConst;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
