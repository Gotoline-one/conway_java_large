package com.conway.Utilities;

import com.conway.GameBoard.BoardData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileOutput {

    public static void outputJson(BoardData bd, String filename) {
        File file = new File(filename);
        outputJson(bd, file);
    }


    public FileOutput() {
        System.out.println(" TESTING FILE OUTPUT");
    }

    public static void outputJson( BoardData bd) {
        

        System.out.println("{");
        System.out.println("  \"tick_rate_ms\": " + bd.TICK_RATE + ",");
        System.out.println("  \"start_time_ms\": " + bd.startMili + ",");
        System.out.println("  \"data\": [");
    
        for (int i = 0; i < bd.frameCountList.size(); i++) {
            long elapsedNano = bd.nanoTimeList.get(i) - bd.startNano;
            double elapsedMs = elapsedNano / 1_000_000.0;
    
            System.out.print("    { ");
            System.out.print("\"index\": " + i + ", ");
            System.out.print("\"frame_count\": " + bd.frameCountList.get(i) + ", ");
            System.out.print("\"nano_time\": " + bd.nanoTimeList.get(i) + ", ");
            System.out.printf("\"elapsed_ms\": %.3f", elapsedMs);
            System.out.print(" }");
    
            if (i < bd.frameCountList.size() - 1) System.out.println(",");
            else System.out.println();
        }
    
        System.out.println("  ]");
        System.out.println("}");


    }

    public static void outputJson(BoardData bd, File file) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            System.out.println("Writing to file: " + file.getName());
            

            writer.println("{");
            writer.println("  \"tick_rate_ms\": " + bd.TICK_RATE + ",");
            writer.println("  \"start_time_ms\": " + bd.startMili + ",");
            writer.println("  \"data\": [");
        
            for (int i = 0; i < bd.frameCountList.size(); i++) {
                long elapsedNano = bd.nanoTimeList.get(i) - bd.startNano;
                double elapsedMs = elapsedNano / 1_000_000.0;
        
                writer.print("    { ");
                writer.print("\"index\": " + i + ", ");
                writer.print("\"frame_count\": " + bd.frameCountList.get(i) + ", ");
                writer.print("\"nano_time\": " + bd.nanoTimeList.get(i) + ", ");
                writer.printf("\"elapsed_ms\": %.3f", elapsedMs);
                writer.print(" }");
        
                if (i < bd.frameCountList.size() - 1) {
                    writer.println(",");
                } else {
                    writer.println();
                }
            }
        
            writer.println("  ]");
            writer.println("}");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void outputCsv(BoardData bd) {
        
        System.out.println("index,frame_count,nano_time,elapsed_ms");
    
        for (int i = 0; i < bd.frameCountList.size(); i++) {
            long elapsedNano = bd.nanoTimeList.get(i) - bd.startNano;
            double elapsedMs = elapsedNano / 1_000_000.0;
    
            System.out.printf("%d,%d,%d,%.3f%n",
                    i,
                    bd.frameCountList.get(i),
                    bd.nanoTimeList.get(i),
                    elapsedMs
            );
        }
    
    }

    public static void summaryToScreen(BoardData bd){
        // Optionally, add summary info at end:
        long totalElapsedNano = bd.nanoTimeList.get(bd.nanoTimeList.size() - 1) - bd.startNano;
        double totalElapsedMs = totalElapsedNano / 1_000_000.0;
        System.out.printf("Summary: TICK_RATE=%dms, Total Frames=%d, Total Time=%.3fms%n",
                bd.TICK_RATE,
                bd.frameCountList.size(),
                totalElapsedMs
        );
    }


    public static void outputCsv(BoardData bd, File file) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            writer.println("index,frame_count,nano_time,elapsed_ms");
        
            for (int i = 0; i < bd.frameCountList.size(); i++) {
                long elapsedNano = bd.nanoTimeList.get(i) - bd.startNano;
                double elapsedMs = elapsedNano / 1_000_000.0;
        
                writer.printf("%d,%d,%d,%.3f%n",
                        i,
                        bd.frameCountList.get(i),
                        bd.nanoTimeList.get(i),
                        elapsedMs
                );
            }
        
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
}
