package com.conway.ConwayNetworked;

import java.io.File;

import com.conway.ConwayNetworked.ConwayStream.StreamRecvr;
import com.conway.ConwayNetworked.ConwayStream.UDPReceiverApp;
import com.conway.GameBoard.GameBoardView;
import com.conway.GameBoard.GameController;
import com.conway.GameBoard.GameEvent;
import com.conway.GameBoard.GameOfLife;

public class NetBoardController implements GameController{
    GameOfLife game;
    GameBoardView gameView; 
    StreamRecvr streamReciever;
    UDPReceiverApp udpReceiverApp;
    AppClient appClient;
    AppServer appServer;

    public NetBoardController(GameOfLife gameLogic){
        this.game =   gameLogic;

        udpReceiverApp.setOnBoardUpdate(() -> {
            System.out.println("Recieved new board " + System.currentTimeMillis());
            gameLogic.getBoard().setBoard(UDPReceiverApp.latestBoard);
            gameView.drawBoard(gameLogic);
        });
    }

    public void updateGameboard(){
        System.out.println("Recieved new board " + System.currentTimeMillis());
        game.getBoard().setBoard(UDPReceiverApp.latestBoard);
        gameView.drawBoard(game);

    }

    public NetBoardController(GameOfLife gameLogic, GameBoardView gameView, StreamRecvr udpGameClient) {
        this.game =   gameLogic;
        this.gameView = gameView;
        this.streamReciever = udpGameClient;
        gameView.setController( this);

    }

    public NetBoardController(GameOfLife gameLogic, GameBoardView gameView2, AppClient client) {
        this.game =   gameLogic;
        this.gameView = gameView2;
        this.appClient = client;
        appClient.setController(this);
        
        appClient.setOnBoardUpdate(() -> {
            System.out.println("Recieved new board " + System.currentTimeMillis());
            gameLogic.getBoard().setBoard(UDPReceiverApp.latestBoard);
            gameView.drawBoard(gameLogic);
        });
	}

	public NetBoardController(GameOfLife gameLogic,  AppServer appServer) {
        this.game =   gameLogic;
        this.appServer = appServer;
        // gameView.setController( this);
    }

    @Override
    public GameBoardView getView() {
        return gameView;
    }

    /**
     *  Control Stream: tell the server(s) to start the game 
     *  View Streams: turn on stream recvr
     * 
     */
    @Override
    public void startGame() {
        System.out.println("StreamBoard Controller: start Stream");
        streamReciever = new StreamRecvr(game);
        streamReciever.start();

    }


    /**
     *  Control Stream: tell the server(s) to stop the game 
     *  View Streams: turn off stream recvr
     * 
     */
    @Override
    public void stopGame() {
        System.out.println("STOP Stream");
        streamReciever.stop();

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
        game.setCell(row, col, game.getCell(row, col));
        //  view.drawBoard(game);


    }

    public void randomizeBoard() {
    //    this.game.getBoard().
    }



}
