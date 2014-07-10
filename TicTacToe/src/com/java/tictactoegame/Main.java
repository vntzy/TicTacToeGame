package com.java.tictactoegame;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        TicTacToeGame game = new TicTacToeGame(3);
        game.play();
    }
}
