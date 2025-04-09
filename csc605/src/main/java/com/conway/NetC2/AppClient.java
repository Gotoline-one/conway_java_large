package com.conway.NetC2;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class AppClient {
    public int DEFAULT_PORT = 19;
    Socket socket; 
    public AppClient (){

        
    }
    

    public void daytimeClient(){

        // String hostname = "time.nist.gov";
        Socket socket = null;

        try {
            socket = new Socket("192.168.100.253", 61616);
            socket.setSoTimeout(150);
            
            // InputStream in   = socket.getInputStream();
            OutputStream out = socket.getOutputStream();
            
            // StringBuilder time = new StringBuilder();
            // InputStreamReader reader = new InputStreamReader(in, "ASCII");
            
            OutputStreamWriter writer = new OutputStreamWriter(out,"ASCII");
            while(true){
                writer.write("TEST");
            }
            // for (int c = reader.read(); c != -1; c = reader.read()) {
            //     time.append((char) c);
            // }
            
            // System.out.println(time);

        } catch (IOException ex) {
                System.err.println(ex);
        } finally {
            
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException ex) {
                    // ignore
                }
            }
        }
    }

}
