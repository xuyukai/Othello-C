package com.company.token;

import com.company.myException.*;
import com.company.tools.*;

import java.io.*;
import java.util.ArrayList;

public class Tokenizer {

    enum DFAState {
        INITIAL_STATE,
        LEFT_BRACKET_MARK_STATE,
        RIGHT_BRACKET_MARK_STATE,
        LEFT_CURLY_MARK_STATE,
        RIGHT_CURLY_MARK_STATE,
        SMALLER_MARK_STATE,
        GREATER_MARK_STATE,
        EQUAL_MARK_STATE,
        COMMA_MARK_STATE,
        SEMICOLON_MARK_STATE,
        EXCLAMATION_MARK_STATE,
        PLUS_MARK_STATE,
        MINUS_MARK_STATE,
        TIMES_MARK_STATE,
        DIVISION_MARK_STATE,
        IDENTIFIER_STATE,
        DECIMAL_INTEGER_STATE,
        HEXADECIMAL_INTEGER_STATE,
    }

    boolean isInitialized = false;

    BufferedReader br;
    myPair<Integer, Integer> ptr = new myPair<>(0, 0);
    ArrayList<String> buffer = new ArrayList<>();

    public Tokenizer(File file) throws FileNotFoundException {
        br = new BufferedReader(new FileReader(file));
    }

    //unfinished
    public Token nextToken() throws Exception {

        StringBuilder sb = new StringBuilder();
        myPair<Integer, Integer> pos = null; //the start position of present token
        DFAState curState = DFAState.INITIAL_STATE;
        while (true) {
            char current_char = nextChar();
            //System.out.println(current_char+" "+ptr.getX()+" "+ptr.getY());
            //System.out.println(curState);
            switch (curState) {
                case INITIAL_STATE: {
                    if (current_char == 255) {
                        throw new EndOfCompilationException();
                    }
                    boolean invalid = false;
                    if (new isXX().isSpace(current_char)) {
                        break;
                    } else if (!new isXX().isPrint(current_char)) { // control codes and backspace
                        invalid = true;
                    } else if (new isXX().isDigit(current_char)) {// 读到的字符是数字
                        if (current_char == '0') {
                            curState = DFAState.HEXADECIMAL_INTEGER_STATE;
                        } else {
                            curState = DFAState.DECIMAL_INTEGER_STATE; // 切换到十进制整数的状态
                        }
                    } else if (new isXX().isLetter(current_char)) // 读到的字符是英文字母
                        curState = DFAState.IDENTIFIER_STATE; // 切换到标识符的状态
                    else {
                        switch (current_char) {
                            case '=': {
                                curState = DFAState.EQUAL_MARK_STATE;
                                break;
                            }
                            case ';': {
                                curState = DFAState.SEMICOLON_MARK_STATE;
                                break;
                            }
                            case '+': {
                                curState = DFAState.PLUS_MARK_STATE;
                                break;
                            }
                            case '-': {
                                curState = DFAState.MINUS_MARK_STATE;
                                break;
                            }
                            case '<': {
                                curState = DFAState.SMALLER_MARK_STATE;
                                break;
                            }
                            case '>': {
                                curState = DFAState.GREATER_MARK_STATE;
                                break;
                            }
                            case ',': {
                                curState = DFAState.COMMA_MARK_STATE;
                                break;
                            }
                            case '(': {
                                curState = DFAState.LEFT_BRACKET_MARK_STATE;
                                break;
                            }
                            case ')': {
                                curState = DFAState.RIGHT_BRACKET_MARK_STATE;
                                break;
                            }
                            case '{': {
                                curState = DFAState.LEFT_CURLY_MARK_STATE;
                                break;
                            }
                            case '}': {
                                curState = DFAState.RIGHT_CURLY_MARK_STATE;
                                break;
                            }
                            case '!': {
                                curState = DFAState.EXCLAMATION_MARK_STATE;
                                break;
                            }
                            case '*': {
                                curState = DFAState.TIMES_MARK_STATE;
                                break;
                            }
                            case '/': {
                                curState = DFAState.DIVISION_MARK_STATE;
                                break;
                            }
                            default: {
                                invalid = true;
                                break;
                            }
                        }
                    }
                    if (curState != DFAState.INITIAL_STATE) {
                        pos = previousPos();
                    }
                    if (invalid) {
                        // 回退这个字符
                        if (ptr.getX() >= buffer.size() + 2) {
                            unreadLast();
                            // 返回编译错误：非法的输入
                            throw new InvalidInputException(ptr);
                        } else {
                            throw new EndOfCompilationException();
                        }


                    }
                    sb.append(current_char); // 存储读到的字符
                    break;
                }
                case PLUS_MARK_STATE: {
                    unreadLast();
                    return new Token(TokenType.PLUS_MARK, "+", pos, currentPos());
                }
                case COMMA_MARK_STATE: {
                    unreadLast();

                    return new Token(TokenType.COMMA_MARK, ",", pos, currentPos());
                }
                case EQUAL_MARK_STATE: {
                    unreadLast();
                    return new Token(TokenType.EQUAL_MARK, "=", pos, currentPos());
                }
                case MINUS_MARK_STATE: {
                    unreadLast();
                    return new Token(TokenType.MINUS_MARK, "-", pos, currentPos());
                }
                case TIMES_MARK_STATE: {
                    unreadLast();
                    return new Token(TokenType.TIMES_MARK, "*", pos, currentPos());
                }
                case GREATER_MARK_STATE: {
                    unreadLast();
                    return new Token(TokenType.GREATER_MARK, ">", pos, currentPos());
                }
                case SMALLER_MARK_STATE: {
                    unreadLast();
                    return new Token(TokenType.SMALLER_MARK, "<", pos, currentPos());
                }
                case DIVISION_MARK_STATE: {
                    unreadLast();
                    return new Token(TokenType.DIVISION_MARK, "/", pos, currentPos());
                }
                case SEMICOLON_MARK_STATE: {
                    unreadLast();
                    return new Token(TokenType.SEMICOLON_MARK, ";", pos, currentPos());
                }
                case LEFT_CURLY_MARK_STATE: {
                    unreadLast();
                    return new Token(TokenType.LEFT_CURLY_MARK, "{", pos, currentPos());
                }
                case EXCLAMATION_MARK_STATE: {
                    unreadLast();
                    return new Token(TokenType.EXCLAMATION_MARK, "!", pos, currentPos());
                }
                case RIGHT_CURLY_MARK_STATE: {
                    unreadLast();
                    return new Token(TokenType.RIGHT_CURLY_MARK, "}", pos, currentPos());
                }
                case LEFT_BRACKET_MARK_STATE: {
                    unreadLast();
                    return new Token(TokenType.LEFT_BRACKET_MARK, "(", pos, currentPos());
                }
                case RIGHT_BRACKET_MARK_STATE: {
                    unreadLast();
                    return new Token(TokenType.RIGHT_BRACKET_MARK, ")", pos, currentPos());
                }
                case HEXADECIMAL_INTEGER_STATE: {
                    if (current_char == 255) {
                        String tmp;
                        tmp = sb.toString();
                        sb.setLength(0);
                        if (tmp.length() == 1) {
                            return new Token(TokenType.DECIMAL_INTEGER, tmp, pos, currentPos());
                        } else if ((tmp.charAt(1) == 'X' || tmp.charAt(1) == 'x') && tmp.length() >= 3) {
                            int flag = 0;
                            for (int i = 2; i < tmp.length(); i++) {
                                if (tmp.charAt(i) == 'x' || tmp.charAt(i) == 'X') {
                                    flag = 1;
                                    break;
                                }
                            }
                            if (flag == 0) {
                                return new Token(TokenType.HEXADECIMAL_INTEGER, tmp, pos, currentPos());
                            }
                        } else {
                            throw new InvalidInputException(pos);
                        }
                    }
                    boolean invalid = false;
                    if (new isXX().isDigit(current_char) || (current_char >= 'A' && current_char <= 'F') || (current_char >= 'a' && current_char <= 'f') || current_char == 'x' || current_char == 'X') {
                        sb.append(current_char);
                    } else if (new isXX().isLetter(current_char)) {
                        invalid = true;
                    } else {
                        unreadLast();
                        String tmp;
                        tmp = sb.toString();
                        sb.setLength(0);
                        if (tmp.length() == 1) {
                            return new Token(TokenType.DECIMAL_INTEGER, tmp, pos, currentPos());
                        } else if ((tmp.charAt(1) == 'X' || tmp.charAt(1) == 'x') && tmp.length() >= 3) {
                            int flag = 0;
                            for (int i = 2; i < tmp.length(); i++) {
                                if (tmp.charAt(i) == 'x' || tmp.charAt(i) == 'X') {
                                    flag = 1;
                                    break;
                                }
                            }
                            if (flag == 0) {
                                return new Token(TokenType.HEXADECIMAL_INTEGER, tmp, pos, currentPos());
                            }
                        } else {
                            throw new InvalidInputException(pos);
                        }
                    }
                    if (invalid) {
                        unreadLast();
                        throw new InvalidInputException(pos);
                    }
                    break;
                }
                case DECIMAL_INTEGER_STATE: {
                    if (current_char == 255) {
                        String tmp;
                        tmp = sb.toString();
                        sb.setLength(0);
                        return new Token(TokenType.DECIMAL_INTEGER, tmp, pos, currentPos());
                    }
                    boolean invalid = false;
                    if (new isXX().isDigit(current_char)) {
                        sb.append(current_char);
                    } else if (new isXX().isLetter(current_char)) {
                        invalid = true;
                    } else {
                        unreadLast();
                        String tmp = sb.toString();
                        sb.setLength(0);
                        return new Token(TokenType.DECIMAL_INTEGER, tmp, pos, currentPos());
                    }
                    if (invalid) {
                        unreadLast();
                        throw new InvalidInputException(pos);
                    }
                    break;
                }
                case IDENTIFIER_STATE: {
                    if (current_char == 255) {
                        String tmp = sb.toString();
                        sb.setLength(0);
                        switch (tmp) {
                            case "const":
                                return new Token(TokenType.CONST, tmp, pos, currentPos());
                            case "void":
                                return new Token(TokenType.VOID, tmp, pos, currentPos());
                            case "int":
                                return new Token(TokenType.INT, tmp, pos, currentPos());
                            case "char":
                                return new Token(TokenType.CHAR, tmp, pos, currentPos());
                            case "double":
                                return new Token(TokenType.DOUBLE, tmp, pos, currentPos());
                            case "struct":
                                return new Token(TokenType.STRUCT, tmp, pos, currentPos());
                            case "if":
                                return new Token(TokenType.IF, tmp, pos, currentPos());
                            case "else":
                                return new Token(TokenType.ELSE, tmp, pos, currentPos());
                            case "switch":
                                return new Token(TokenType.SWITCH, tmp, pos, currentPos());
                            case "case":
                                return new Token(TokenType.CASE, tmp, pos, currentPos());
                            case "default":
                                return new Token(TokenType.DEFAULT, tmp, pos, currentPos());
                            case "do":
                                return new Token(TokenType.DO, tmp, pos, currentPos());
                            case "return":
                                return new Token(TokenType.RETURN, tmp, pos, currentPos());
                            case "break":
                                return new Token(TokenType.BREAK, tmp, pos, currentPos());
                            case "continue":
                                return new Token(TokenType.CONTINUE, tmp, pos, currentPos());
                            case "print":
                                return new Token(TokenType.PRINT, tmp, pos, currentPos());
                            case "scan":
                                return new Token(TokenType.SCAN, tmp, pos, currentPos());
                            case "while":
                                return new Token(TokenType.WHILE, tmp, pos, currentPos());
                            default:
                                return new Token(TokenType.IDENTIFIER, tmp, pos, currentPos());
                        }
                    }
                    if (new isXX().isLetter(current_char) || new isXX().isDigit(current_char)) {
                        sb.append(current_char);
                    } else {
                        unreadLast();
                        String tmp = sb.toString();
                        sb.setLength(0);
                        switch (tmp) {
                            case "const":
                                return new Token(TokenType.CONST, tmp, pos, currentPos());
                            case "void":
                                return new Token(TokenType.VOID, tmp, pos, currentPos());
                            case "int":
                                return new Token(TokenType.INT, tmp, pos, currentPos());
                            case "char":
                                return new Token(TokenType.CHAR, tmp, pos, currentPos());
                            case "double":
                                return new Token(TokenType.DOUBLE, tmp, pos, currentPos());
                            case "struct":
                                return new Token(TokenType.STRUCT, tmp, pos, currentPos());
                            case "if":
                                return new Token(TokenType.IF, tmp, pos, currentPos());
                            case "else":
                                return new Token(TokenType.ELSE, tmp, pos, currentPos());
                            case "switch":
                                return new Token(TokenType.SWITCH, tmp, pos, currentPos());
                            case "case":
                                return new Token(TokenType.CASE, tmp, pos, currentPos());
                            case "default":
                                return new Token(TokenType.DEFAULT, tmp, pos, currentPos());
                            case "do":
                                return new Token(TokenType.DO, tmp, pos, currentPos());
                            case "return":
                                return new Token(TokenType.RETURN, tmp, pos, currentPos());
                            case "break":
                                return new Token(TokenType.BREAK, tmp, pos, currentPos());
                            case "continue":
                                return new Token(TokenType.CONTINUE, tmp, pos, currentPos());
                            case "print":
                                return new Token(TokenType.PRINT, tmp, pos, currentPos());
                            case "scan":
                                return new Token(TokenType.SCAN, tmp, pos, currentPos());
                            case "while":
                                return new Token(TokenType.WHILE, tmp, pos, currentPos());
                            default:
                                return new Token(TokenType.IDENTIFIER, tmp, pos, currentPos());
                        }
                    }
                    break;
                }
                default: {
                    throw new UnhandledStateException(currentPos());
                }
            }
        }
    }

    //Finished
    public void readCode() throws IOException {
        if (isInitialized) {
            return;
        }
        String line;
        while ((line = br.readLine()) != null) {
            if (line.isEmpty()) {
                buffer.add("\n");
                continue;
            }

            buffer.add(line);
        }
        br.close();
    }

    //Finished
    char nextChar() throws EOFException {
        if (isEOF()) {
            ptr.setX(ptr.getX() + 1);
            return (char) 255;
        }
        char result = buffer.get(ptr.getX()).charAt(ptr.getY());
        ptr = nextPos();
        return result;
    }


    //Finished
    boolean isEOF() {
        return ptr.getX() >= buffer.size();
    }

    //F
    myPair<Integer, Integer> previousPos() throws NoPreviousException {
        if (ptr.getX() == buffer.size() + 1) {
            return new myPair<>(ptr.getX() - 1, ptr.getY());
        }
        if (ptr.getX() == 0 && ptr.getY() == 0) {
            throw new NoPreviousException(currentPos());
        }
        if (ptr.getY() == 0)
            return new myPair<>(ptr.getX() - 1, buffer.get(ptr.getX() - 1).length() - 1);
        else
            return new myPair<>(ptr.getX(), ptr.getY() - 1);
    }

    //Finished
    myPair<Integer, Integer> nextPos() throws EOFException {
        if (ptr.getX() >= buffer.size()) {
            //System.out.println("Here");
            throw new EOFException();
        }
        if (ptr.getY() == buffer.get(ptr.getX()).length() - 1) {
            return new myPair<>(ptr.getX() + 1, 0);
        } else {
            return new myPair<>(ptr.getX(), ptr.getY() + 1);
        }
    }

    void unreadLast() throws NoPreviousException {
        ptr = previousPos();
    }

    myPair<Integer, Integer> currentPos() {
        return ptr;
    }

}