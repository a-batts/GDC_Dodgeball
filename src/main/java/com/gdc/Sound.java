package com.gdc;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class Sound {

    private Clip sound;

    public Sound(String path){
        try{
            InputStream is = new FileInputStream(path);
            AudioInputStream stream = AudioSystem.getAudioInputStream(is);
            Line.Info dataLine = new DataLine.Info(Clip.class, stream.getFormat());
            sound = (Clip) AudioSystem.getLine(dataLine);
            sound.open(stream);
        }
        catch(FileNotFoundException e){
            JOptionPane.showMessageDialog(null, "An asset does not exist");
        } catch (UnsupportedAudioFileException | IOException| LineUnavailableException e) {
            JOptionPane.showMessageDialog(null, "An asset was unable to be loaded");
        }
    }

    public void play(){
        sound.start();
    }

    public void stop(){
        sound.stop();
    }
}
