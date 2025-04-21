package com.conway.AppOptions;

import java.net.InetAddress;

import com.conway.Utilities.*;

// Class to hold parsed options
public  class AppOptions {
    
    public int height = 0;
    public int width = 0;
    public int timeInSeconds = 0;
    public boolean jsonOutput = false;
    public boolean csvOutput = false;
    public boolean debug = false;
    public boolean showHelp = false;
    public String filename = "default";
    public CommandLineFlags flags;
    public boolean quitOnEnd = false;
    public long seed = (long)314597;
    public boolean client = false;
    public boolean server = false;
    public int streamPort = 9876;
    public String streamAddressString = "127.0.0.1";
 
}