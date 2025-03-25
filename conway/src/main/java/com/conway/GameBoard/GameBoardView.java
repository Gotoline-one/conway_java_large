package com.conway.GameBoard;

import com.conway.GameOfLife;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GameBoardView {
    private static int  WIDTH;
    private static int HEIGHT;
    private static final int CELL_SIZE = 20;

    private Rectangle[][] rectangles;
    private GridPane grid;
    private GameBoardController controller;

    public GameBoardView(int myHEIGHT, int myWIDTH) {
        WIDTH = myWIDTH;
        HEIGHT = myHEIGHT;
        
        // Set up the grid layout for the board
        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        
        // Instantiate the rectangles array
        rectangles = new Rectangle[HEIGHT][WIDTH];

        // Initialize each rectangle (cell) and add it to the grid
        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                rectangles[row][col] = new Rectangle(CELL_SIZE, CELL_SIZE);
                                rectangles[row][col].setStroke(Color.LIGHTGRAY);
                // Set an initial fill; actual color will be updated by the controller
                rectangles[row][col].setFill(Color.WHITE);
                
                // Add onClick handler for each cell
                int finalRow = row;
                int finalCol = col;
                rectangles[row][col].setOnMouseClicked(event ->handleCellClick(finalRow, finalCol));
                grid.add(rectangles[row][col], col, row);
            }
        }
    }

    // Provides the root node (GridPane) so it can be placed in the scene
    public GridPane getView() {
        return grid;
    }

    // Update the board's visual state based on the game model
    public void drawBoard(GameOfLife game) {
        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                boolean isAlive = game.getCell(row, col);
                rectangles[row][col].setFill(isAlive ? Color.BLACK : Color.WHITE);
            }
        }
    }

    // Methods to provide dimensions for the Scene
    public int getWidthInPixels() {
        return WIDTH * CELL_SIZE;
    }

    public int getHeightInPixels() {
        return HEIGHT * CELL_SIZE;
    }

    public void handleCellClick(int finalRow, int finalCol){
        controller.handleCellClick(finalRow, finalCol);
    }

    public void setController(GameBoardController controller) {
        this.controller = controller;


}
    
}
