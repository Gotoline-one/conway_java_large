package com.conway.NetC2.ConwayStream;

import java.io.File;

import com.conway.GameBoard.GameBoardView;
import com.conway.GameBoard.GameController;
import com.conway.GameBoard.GameEvent;
import com.conway.GameBoard.GameOfLife;

public class StreamBoardController implements GameController{
    GameOfLife gameLogic;
    GameBoardView gameView; 
    StreamRecvr streamRecvr;



    public StreamBoardController(GameOfLife gameLogic, GameBoardView gameView, StreamRecvr udpGameClient) {
        this.gameLogic =   gameLogic;
        this.gameView = gameView;
        this.streamRecvr = udpGameClient;
        gameView.setController( this);
    }


    @Override
    public GameBoardView getView() {
        return gameView;
    }
    StreamRecvr reciever;
    /**
     *  Control Stream: tell the server(s) to start the game 
     *  View Streams: turn on stream recvr
     * 
     */
    @Override
    public void startGame() {
        System.out.println("StreamBoard Controller: start Stream");
        reciever = new StreamRecvr(gameLogic);
        reciever.start();

    }


    /**
     *  Control Stream: tell the server(s) to stop the game 
     *  View Streams: turn off stream recvr
     * 
     */
    @Override
    public void stopGame() {
        System.out.println("STOP Stream");
        reciever.stop();

    }

    /**
     *  Control Stream: tell the server(s) to reset the game 
     *  View Streams: reset the stream recvr (shutdown and restart socket)
     * 
     */
    @Override
    public void resetGame() {
        System.out.println("Reset Stream");

    }
    
    /**
     *  Control Stream: tell the server(s) to load this seed
     *  View Streams: Nothin
     * 
     */
    @Override
    public void setSeed(String text) {
        System.out.println("Set Seed for future");

    }

     /**
     *  Control Stream: tell the server(s) to load this seed
     *  View Streams: Nothin
     * 
     */
    @Override
    public void loadSeed(String text) {
        System.out.println("Load Seed and use it");
       
    }

    @Override
    public void saveBoardToJSONFile(File file) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveBoardToJSONFile'");
    }

    @Override
    public void saveBoardToCSVFile(File file) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveBoardToCSVFile'");
    }

     /**
     *  Set what happens when game ends
     * 
     */
    @Override
    public void setOnEndGame(GameEvent event) {
       System.out.println("end of the game");
    }


    /**
     *  Controller: depends on context, 
     *      
     * View: Nothing
     */
    public void handleCellClick(int row, int col) {
        // game.setCell(row, col, !game.getCell(row, col));
        // view.drawBoard(game);


    }
}
