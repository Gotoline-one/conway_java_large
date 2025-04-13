package com.conway.GameBoard;

public class Board {
    public int WIDTH  = 50;
    public int HEIGHT = 50;
    public boolean[][] board;


    public Board(int newHeight, int newidth) {
        setHEIGHT(newHeight);
        setWIDTH(newidth);

        board = new boolean[HEIGHT][WIDTH];
    }
    public int getWIDTH() {
        return WIDTH;
    }
    public void setWIDTH(int wIDTH) {
        WIDTH = wIDTH;
    }
    public int getHEIGHT() {
        return HEIGHT;
    }
    public void setHEIGHT(int hEIGHT) {
        HEIGHT = hEIGHT;
    }

    public boolean[][] getBoard() {
        return board;
    }

    public int  setSquare(int row, int col, boolean data){
        if(!(row <this.HEIGHT && col < this.WIDTH)){
            return -1; 
        }

        this.board[row][col] = data;
        return 0;
    }

    public boolean  getSquare(int row, int col){
        return board[row][col];
    }

    public void setBoard(Board recieveData) {
        for(int row = 0; row < recieveData.getHEIGHT(); row++){
            for( int col = 0; col < recieveData.getWIDTH(); col ++){
                boolean temp =recieveData.getSquare(row, col);
                setSquare(row,col,temp );
            }
        }
        
    }

}
