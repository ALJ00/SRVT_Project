/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package srvt_app;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Control;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

/**
 *
 * @author armas
 */
public class SRVT_app {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws LineUnavailableException, InterruptedException {
        // TODO code application logic here
        
        AudioFormat format = new AudioFormat(8000.0F, 16, 1, true, false);
        
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
        
        if(!AudioSystem.isLineSupported(info)){
            System.out.println("Line is not supported");
        }
        
        final TargetDataLine targetDataLine = (TargetDataLine) AudioSystem.getLine(info);
        
        targetDataLine.open();      
        
        targetDataLine.start();
        
        Thread stopper = new Thread(new Runnable(){
            
            @Override
            public void run(){
                
                try {
                    targetDataLine.open();
                } catch (LineUnavailableException ex) {
                    Logger.getLogger(SRVT_app.class.getName()).log(Level.SEVERE, null, ex);
                }
            
                AudioInputStream audioStream = new AudioInputStream(targetDataLine);
                File wavFile = new File("C:\\Users\\armas\\recording.wav");
                
                try {
                    AudioSystem.write(audioStream, AudioFileFormat.Type.WAVE, wavFile);
                } catch (IOException ex) {
                    Logger.getLogger(SRVT_app.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
            
        });
        
        stopper.start();
        Thread.sleep(5000);
        System.out.println("starting recording");
        
        
        
        //targetDataLine.stop();
        //targetDataLine.close();
        
        System.out.println("End test");
        
        
    }
    
}
