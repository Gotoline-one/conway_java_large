package com.conway.GameBoard.NetIO;

public class NIOTester {



    public static void main(String[] args) {
        NetOut netOut = new NetOut();
        try {
            netOut.timeTest();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
