package com.conway.GameBoard.NetIO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.Socket;

import com.conway.GameBoard.BoardData;

public class NetOut {

    public void generateCharacters(OutputStream out) throws IOException{
        
        int firstPrintableChar = 33;
        int numberOfPrintableChars = 94;
        int numberOfCharsPerLine = 72;

        int start = firstPrintableChar;
        while (true) {
            for (int i = start; i < start + numberOfCharsPerLine; i++) {
                out.write(((i - firstPrintableChar) % numberOfPrintableChars) + firstPrintableChar);
            }
            out.write('\r'); // carriage return
            out.write('\n'); // line feed
            start = ((start + 1) - firstPrintableChar) % numberOfPrintableChars + firstPrintableChar;
        }

    }

    public void timeTest(){

        // try (Socket socket = new Socket("time.nist.gov", 13)) {
        try (Socket socket = new Socket("192.168.100.190", 123)) {

            socket.setSoTimeout(15000);
            InputStream in = socket.getInputStream();
            StringBuilder time = new StringBuilder();

            InputStreamReader reader = new InputStreamReader(in, "ASCII");
            for (int c = reader.read(); c != -1; c = reader.read()) {
                time.append((char) c);
            }

            System.out.println(time);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


