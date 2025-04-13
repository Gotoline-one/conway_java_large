package com.conway.GameBoard;

import java.util.Random;

public class GameOfLife {
    private final Random random; 
    private Board theBoard;

    public GameOfLife(int height, int width, long seed){
        theBoard = new Board(height,width);

        theBoard.HEIGHT = height;
        theBoard.WIDTH  = width;
        random = new Random(seed);
        theBoard.board  = new boolean[theBoard.HEIGHT][theBoard.WIDTH];
    }

    public GameOfLife(int height, int width){
        theBoard = new Board(height,width);
        
        theBoard.HEIGHT = height;
        theBoard.WIDTH  = width;

        random = new Random();
        theBoard.board  = new boolean[theBoard.HEIGHT][theBoard.WIDTH];
    }
    
    public boolean getCell(int row, int col){ return theBoard.board[row][col]; }
    public int getHEIGHT(){return theBoard.HEIGHT;}
    public int getWIDTH(){return theBoard.WIDTH;}
    public  void randomizeBoard() {
        for (int row = 0; row < theBoard.HEIGHT; row++) {
            for (int col = 0; col < theBoard.WIDTH; col++) {
                theBoard.board[row][col] = random.nextDouble() > 0.7;
            }
        }
    }

      // Update board to next generation
    public void updateBoard() {
        boolean[][] nextGen = new boolean[theBoard.HEIGHT][theBoard.WIDTH];

        for (int row = 0; row < theBoard.HEIGHT; row++) {
            for (int col = 0; col < theBoard.WIDTH; col++) {
                int neighbors = countNeighbors(row, col);
                if (theBoard.board[row][col]) {
                    nextGen[row][col] = (neighbors == 2 || neighbors == 3);
                } else {
                    nextGen[row][col] = (neighbors == 3);
                }
            }
        }

        theBoard.board = nextGen;
    }

    // Method to print the current state of the board
    public void printBoard() {
        for (int row = 0; row < theBoard.HEIGHT; row++) {
            for (int col = 0; col < theBoard.WIDTH; col++) {
                System.out.print(theBoard.board[row][col] ? "O" : ".");
            }
            System.out.println();
        }
    }

    // Count live neighbors
    private int countNeighbors(int row, int col) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {// Skip self
                    continue;
                }
                int r = (row + i + theBoard.HEIGHT) % theBoard.HEIGHT; // Wrap-around
                int c = (col + j + theBoard.WIDTH) % theBoard.WIDTH;
                if (theBoard.board[r][c]) count++;
            }
        }
        return count;
    }

    public void setSeed(long seed) {
        random.setSeed(seed);
    }

    public void setCell(int row, int col, boolean b) {
        theBoard.board[row][col] = b;
    }

	public Board getBoard() {
            return theBoard;
	}

}
