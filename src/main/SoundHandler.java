package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class SoundHandler {

    Clip clip;
    URL[] soundURL = new URL[30];

    public SoundHandler(){
        soundURL[0] = getClass().getResource("/sounds/Music.wav");
        soundURL[1] = getClass().getResource("/sounds/drink.wav");
        soundURL[2] = getClass().getResource("/sounds/no.wav");
        soundURL[3] = getClass().getResource("/sounds/rabbit.wav");
        soundURL[4] = getClass().getResource("/sounds/idle.wav");
        soundURL[5] = getClass().getResource("/sounds/lost.wav");
        soundURL[6] = getClass().getResource("/sounds/won.wav");
        soundURL[7] = getClass().getResource("/sounds/gopi_hit.wav");
        soundURL[8] = getClass().getResource("/sounds/monster_hit.wav");
        soundURL[9] = getClass().getResource("/sounds/fireball.wav");
    }

    public void setFile(int i){
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        }catch (Exception ignored){

        }
    }

    public void play(){
        clip.start();
    }

    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop(){
        clip.stop();
    }

}
