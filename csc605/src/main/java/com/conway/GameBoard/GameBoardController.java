package com.conway.GameBoard;

import com.conway.FileOutput;
import com.conway.GameOfLife;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;

public class GameBoardController {
    private GameBoardView view;
    private GameOfLife game;
    private BoardData boardData;

    private Timeline timeline;
    private AnimationTimer fpsCounter;
    private PauseTransition stopTimer;

    private GameEvent onEnd;
    private GameEvent onStart;

    private Boolean isGameRunning = false;


    public GameBoardController(GameOfLife newGame, GameBoardView newView, double gameTimeLimit) {
        this.view = newView;
        this.game = newGame;
        view.setController( this);
        // Initialize boardData
        boardData = new BoardData();
        boardData.TICK_RATE = 20;
        boardData.frameCountList = new ArrayList<>();
        boardData.nanoTimeList = new ArrayList<>();
        boardData.startNano = System.nanoTime();
        boardData.startMili = System.currentTimeMillis();
        
        //first things first
        game.randomizeBoard();

        // Set up the game loop to update the model and view
        timeline = new Timeline(
            new KeyFrame(Duration.millis(boardData.TICK_RATE), 
                e -> {  
                    game.updateBoard(); 
                    view.drawBoard(game);
                }
            )
        );
        timeline.setCycleCount(Timeline.INDEFINITE);

        // Set up an AnimationTimer to count frames (FPS counter)
        fpsCounter = new AnimationTimer() {
            @Override
            public void handle(long now) {
                boardData.frameCount++;
                if (boardData.lastFpsTime == 0) {
                    boardData.lastFpsTime = now;
                } else if (now - boardData.lastFpsTime >= 1_000_000_000) { // 1 second
                    boardData.frameCountList.add(boardData.frameCount);
                    boardData.frameCount = 0;
                    boardData.lastFpsTime = now;
                    boardData.nanoTimeList.add(System.nanoTime());
                }
            }
        };

        if(gameTimeLimit >0){
            // Set up a timer based on input gameTimeLimit (in seconds) to stop the game after a certain time
            // This timer will stop the game and call the gameEnd method
            // when the time limit is reached
            // Convert gameTimeLimit from seconds to milliseconds for PauseTransition
            // and set the duration for the stopTimer
            
            stopTimer = new PauseTransition(Duration.millis((gameTimeLimit + 1) * 1_000));
            stopTimer.setOnFinished(event -> {
                timeline.stop();
                fpsCounter.stop();
                gameEnd();
            });
            stopTimer.play();
        }
        
        // Set up the grid event handler
        // view.setOnCellClick(this::handleCellClick);

        // Start the game and all timers
        gameStart();
        timeline.play();
        fpsCounter.start();
    }

    // Hook registration for game end
    public void setOnEndGame(GameEvent event) {
        this.onEnd = event;
    }

    // Hook registration for game start
    public void setOnStartGame(GameEvent event) {
        this.onStart = event;
    }

    private void gameEnd() {
        // System.out.println("Game is exiting...");
        if (onEnd != null) {
            onEnd.execute();
        }
    }

    private void gameStart() {
        // System.out.println("Starting Game");
        if (onStart != null) {
            onStart.execute();
        }
    }

    public void saveBoardToCSVFile(File filePointer) {
        FileOutput.outputCsv(boardData, filePointer);
    }

    public void saveBoardToJSONFile(File filePointer) {
        System.out.println("GameBoard CTRL: - Saving to JSON file: " + filePointer);
        FileOutput.outputJson(boardData, filePointer);
        // FileOutput.outputJson(boardData);
    }

    public void startGame() {

        System.out.println("Starting Game");
        timeline.play();
        fpsCounter.start();
        isGameRunning = true;

    }

    public void resetGame() {
        timeline.stop();
        fpsCounter.stop();
        game.randomizeBoard();
        view.drawBoard(game);
        boardData.reset();
        isGameRunning = false;

    }

    public void stopGame() {
        timeline.stop();
        fpsCounter.stop();
        isGameRunning = false;

    }

    public void setSeed(String text) {
        try {
            long seed = Long.parseLong(text);
            game.setSeed(seed);
            game.randomizeBoard();
            view.drawBoard(game);
        } catch (NumberFormatException e) {
            System.err.println("Invalid seed value: " + text);
            System.out.println("Please enter a valid seed value");
            System.out.println("Using PI as seed value");

            game.setSeed(3141592653589793238L);
            game.randomizeBoard();
        }
    }
        

    public GameBoardView getView() {
       return view;
    }

    public void loadSeed(String text) {
        long seed;
        try{        
            seed = Long.parseLong(text);
        
        }catch (NumberFormatException e) {
            System.err.println("Invalid seed value: " + text);
            System.out.println("Please enter a valid seed value");
            seed = 3214546846l;
        }

        game.setSeed(seed);
        game.randomizeBoard();
        view.drawBoard(game);

    }

    // Handle cell click event
    public void handleCellClick(int row, int col) {
        game.setCell(row, col, !game.getCell(row, col));
        view.drawBoard(game);


    }

    
}
