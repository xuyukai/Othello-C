package com.company.token;

public enum TokenType {
    NULL_TOKEN,
    //Reserved-word
    CONST,
    VOID,
    INT,
    CHAR,
    DOUBLE,
    STRUCT,
    IF,
    ELSE,
    SWITCH,
    CASE,
    DEFAULT,
    DO,
    RETURN,
    BREAK,
    CONTINUE,
    PRINT,
    SCAN,
    WHILE,
    //Marks
    LEFT_BRACKET_MARK,//(
    RIGHT_BRACKET_MARK,//)
    LEFT_CURLY_MARK,//{
    RIGHT_CURLY_MARK,//}
    SMALLER_MARK,//<
    GREATER_MARK,//>
    EQUAL_MARK,//=
    COMMA_MARK,//,
    SEMICOLON_MARK,//;
    EXCLAMATION_MARK,//!
    PLUS_MARK,//+
    MINUS_MARK,//-
    TIMES_MARK,//*
    DIVISION_MARK,// /

    IDENTIFIER,
    DECIMAL_INTEGER,
    HEXADECIMAL_INTEGER,
}
