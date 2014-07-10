package com.java.tictactoegame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TicTacToeGame {
    private Unit[][] board;
    private int[] last;
    private int markedUnits;
    private String winner;
    
    public TicTacToeGame() {
        this(3);
    }
    
    public TicTacToeGame(int scale) {
        board = new Unit[scale][scale];
        for (int y = 0; y < board.length; y++){
            for (int x = 0; x < board[y].length; x++) {
                board[y][x] = new Unit();
            }
        }
        last = new int[] {0, 0};
        markedUnits = 0;
        winner = "";
    }
    
    private void visualizeBoard() {
        for (Unit[] line: board) {
            for (Unit unit: line) {
                System.out.print("|" + unit.getMark());
            }
            System.out.print("|" + System.lineSeparator());
        }
    }
    
    public void play() throws IOException {
        boolean turn = true;
        do {
            visualizeBoard();
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String input = br.readLine();
            if(input.equals("q")){
                System.out.println("The game is over");
                return;
            }
            if(!checkInput(input)) {
                System.out.println("Invalid input. Insert new coordinates");
                continue;
            }
            
            int x = Integer.parseInt(input.split(" ")[0]);
            int y = Integer.parseInt(input.split(" ")[1]);
            
            try {
                markUnit(x, y, checkTurn(turn));
            } catch (IncorrectMarkingException e) {
                System.out.println("This unit is already marked! Insert new coordinates!");
                continue;
            } catch (OutOfBoardException e) {
                System.out.println("The coordinates are out of the board! Insert new coordinates!");
                continue;
            }
            
            turn = changeTurn(turn);
        } while (!isFinished());
        
        if (winner.equals("")) {
            if (board[last[0]][last[1]].getMark() == 'O') {
                winner = "The winner is Player 1";
            } else {
                winner = "The winner is Player 2";
            }
        }
        System.out.println(winner);
    }
    
    private boolean changeTurn(boolean turn) {
        return !turn;
    }
    private boolean checkRows(){
        for (Unit[] line: board) {
            boolean finished = true;
            Unit unit = line[0];
            for (Unit u: line) {
                if (!(u.getMark() == unit.getMark() && u.isMarked())) {
                    finished = false;
                    continue;
                }
            }
            if (finished) {
                return true;
            }
        }
        return false;
    }
    
    private boolean checkColumns(){
        for(int x = 0; x < board.length; x++){
            boolean finished = true;
            Unit aUnit = board[x][0];
            for(int y = 0; y < board.length; y++){
                if(!(aUnit.getMark() == board[y][x].getMark() && board[y][x].isMarked())){
                    finished = false;
                    break;
                }
            }
            if (finished) {
                return true;
            }
        }
        return false;
    }
    
    private boolean checkMainDiagonal(){
        Unit unit = board[0][0];
        for (int x = 0; x < board.length; x++) {
            if(!(board[x][x].getMark() == unit.getMark() && board[x][x].isMarked())) {
               return false;
            }
        }
        return true;
    }
    
    private boolean checkSecondDiagonal() {
        Unit unit = board[board.length-1][0];
        for (int x = board.length-1; x > -1; x--) {
            if(!(board[x][x].getMark() == unit.getMark() && board[x][x].isMarked())) {
               return false;
            }
        }
        return true;
    }
    
    private boolean isFinished() {
        if (markedUnits == board.length * board.length) {
            winner = "It's a draw!";
            return true;
        }
        return checkColumns() || checkRows() || checkMainDiagonal() || checkSecondDiagonal();
    }
    
    private char checkTurn(boolean turn) {
        if (turn) {
            return 'O';
        } else {
            return 'X';
        }
    }
    
    private boolean checkInput(String input){
        return input.matches("^\\d+ \\d+$");
    }
    
    private void markUnit(int x, int y, char c) throws IncorrectMarkingException, OutOfBoardException {
        if ((x >= board.length || x < 0) || (y >= board.length || y < 0)) {
            throw new OutOfBoardException();
        }
        if (board[x][y].isMarked()) {
            throw new IncorrectMarkingException();
        }
        board[x][y].mark(c);
        last[0] = x;
        last[1] = y;
    }
}