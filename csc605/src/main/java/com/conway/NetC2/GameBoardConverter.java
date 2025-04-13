package com.conway.NetC2;

import java.nio.ByteBuffer;

import com.conway.GameBoard.Board;
import com.conway.GameBoard.GameOfLife;
import com.google.protobuf.InvalidProtocolBufferException;

public class GameBoardConverter {

    public static byte[] serialize(Board boardData) {
        UDPGameProto.StreamBoard.Builder boardBuilder = UDPGameProto.StreamBoard.newBuilder()
                .setHeight(boardData.getHEIGHT())
                .setWidth(boardData.getWIDTH());
      
        for (int i = 0; i < boardData.getHEIGHT(); i++) {
            UDPGameProto.Row.Builder rowBuilder = UDPGameProto.Row.newBuilder()
                    .setRowIndex(i);
            for (int j = 0; j < boardData.getWIDTH(); j++) {
                rowBuilder.addCells(boardData.getBoard()[i][j]);
            }
            UDPGameProto.BoardChunk.Builder chunkBuilder = UDPGameProto.BoardChunk.newBuilder()
                    .setChunkId(i)
                    .addRows(rowBuilder.build());
            boardBuilder.addChunks(chunkBuilder.build());
        }

        return boardBuilder.build().toByteArray();
    }




    public static byte[] serialize(GameOfLife game) {
        UDPGameProto.StreamBoard.Builder boardBuilder = UDPGameProto.StreamBoard.newBuilder()
                .setHeight(game.getHEIGHT())
                .setWidth(game.getWIDTH());
      
        for (int i = 0; i < game.getHEIGHT(); i++) {
            UDPGameProto.Row.Builder rowBuilder = UDPGameProto.Row.newBuilder()
                    .setRowIndex(i);
            for (int j = 0; j < game.getWIDTH(); j++) {
                rowBuilder.addCells(game.getCell(i,j));
            }
            UDPGameProto.BoardChunk.Builder chunkBuilder = UDPGameProto.BoardChunk.newBuilder()
                    .setChunkId(i)
                    .addRows(rowBuilder.build());
            boardBuilder.addChunks(chunkBuilder.build());
        }
        return boardBuilder.build().toByteArray();
    }



 public static Board deserialize(ByteBuffer buffer) throws InvalidProtocolBufferException {
    
        UDPGameProto.StreamBoard protoBoard = UDPGameProto.StreamBoard.parseFrom(buffer);
        Board boardData = new Board(protoBoard.getHeight(), protoBoard.getWidth());
      
        for (UDPGameProto.BoardChunk chunk : protoBoard.getChunksList()) {
            for (UDPGameProto.Row row : chunk.getRowsList()) {
                int rowIndex = row.getRowIndex();
                for (int j = 0; j < row.getCellsCount(); j++) {
                    boardData.setSquare(rowIndex, j, row.getCells(j));
                }
            }
        }

        return boardData;
    }




}
