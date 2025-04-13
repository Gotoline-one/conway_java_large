package com.conway.GameBoard;

import java.io.File;

public interface GameController {

    GameBoardView getView();

    void startGame();

    void stopGame();

    void resetGame();

    void setSeed(String text);

    void loadSeed(String text);

    void saveBoardToJSONFile(File file);

	void saveBoardToCSVFile(File file);

	void setOnEndGame(GameEvent even);

    void handleCellClick(int finalRow, int finalCol);


}
