package com.java.tictactoegame;

public class Unit {
    private char mark;
    
    public Unit() {
        mark = ' ';
    }
    
    public void mark(char c) {
        mark = c;
    }
    
    public boolean isMarked() {
        return mark != ' ';
    }
    
    public char getMark() {
        return mark;
    }
}
