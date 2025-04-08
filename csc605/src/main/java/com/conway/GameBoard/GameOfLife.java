package com.conway.GameBoard;

import java.util.Random;

public class GameOfLife {
    private static int WIDTH  = 50;
    private static int HEIGHT = 50;
    private boolean[][] board;
    private final Random random; 

    public GameOfLife(int height, int width, long seed){
        HEIGHT = height;
        WIDTH  = width;
        random = new Random(seed);
        board  = new boolean[HEIGHT][WIDTH];
    }

    public GameOfLife(int height, int width){
        HEIGHT = height;
        WIDTH  = width;
        random = new Random();
        board  = new boolean[HEIGHT][WIDTH];
    }
    
    public boolean getCell(int row, int col){ return board[row][col]; }

    public  void randomizeBoard() {
        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                board[row][col] = random.nextDouble() > 0.7;
            }
        }
    }

      // Update board to next generation
    public void updateBoard() {
        boolean[][] nextGen = new boolean[HEIGHT][WIDTH];

        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                int neighbors = countNeighbors(row, col);
                if (board[row][col]) {
                    nextGen[row][col] = (neighbors == 2 || neighbors == 3);
                } else {
                    nextGen[row][col] = (neighbors == 3);
                }
            }
        }

        board = nextGen;
    }

    // Method to print the current state of the board
    public void printBoard() {
        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                System.out.print(board[row][col] ? "O" : ".");
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
                int r = (row + i + HEIGHT) % HEIGHT; // Wrap-around
                int c = (col + j + WIDTH) % WIDTH;
                if (board[r][c]) count++;
            }
        }
        return count;
    }

    public void setSeed(long seed) {
        random.setSeed(seed);
    }

    public void setCell(int row, int col, boolean b) {
        board[row][col] = b;
    }

}
