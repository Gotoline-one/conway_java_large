package com.conway.AppOptions;

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
    public boolean quitOnEnd;
    public long seed = (long)314597;
    public boolean client = false;
    public boolean server = false;
    
    

 
}