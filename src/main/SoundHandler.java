package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.net.URL;

public class SoundHandler {

    Clip clip;
    URL[] soundURL = new URL[30];
    FloatControl fc;
    int volumeScale = 3;
    float volume;

    public SoundHandler(){
        soundURL[0] = getClass().getResource("/sounds/Music.wav");
        soundURL[1] = getClass().getResource("/sounds/drink.wav");
        soundURL[2] = getClass().getResource("/sounds/no.wav");
        soundURL[3] = getClass().getResource("/sounds/cursor.wav");
        soundURL[4] = getClass().getResource("/sounds/item.wav");
        soundURL[5] = getClass().getResource("/sounds/eat.wav");
        soundURL[6] = getClass().getResource("/sounds/levelup.wav");
        soundURL[7] = getClass().getResource("/sounds/gopi_hit.wav");
        soundURL[8] = getClass().getResource("/sounds/monster_hit.wav");
        soundURL[9] = getClass().getResource("/sounds/fireball.wav");
        soundURL[10] = getClass().getResource("/sounds/select.wav");
        soundURL[11] = getClass().getResource("/sounds/throw.wav");
        soundURL[12] = getClass().getResource("/sounds/rock.wav");
        soundURL[13] = getClass().getResource("/sounds/gopi_death.wav");
        soundURL[14] = getClass().getResource("/sounds/door.wav");
        soundURL[15] = getClass().getResource("/sounds/sleep.wav");
        soundURL[16] = getClass().getResource("/sounds/apol_charge.wav");
        soundURL[17] = getClass().getResource("/sounds/apol_shoot.wav");
        soundURL[18] = getClass().getResource("/sounds/guard.wav");
        soundURL[19] = getClass().getResource("/sounds/parry.wav");
        soundURL[20] = getClass().getResource("/sounds/dialogue.wav");
    }

    public void setFile(int i){
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
            fc=(FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
            changeVolume();
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

    public void changeVolume(){
        switch(volumeScale){
            case 0 -> volume=-80f;
            case 1 -> volume=-30f;
            case 2 -> volume=-20f;
            case 3 -> volume=-12f;
            case 4 -> volume=-5f;
            case 5 -> volume=1f;
            case 6 -> volume=6f;
        }
        fc.setValue(volume);
    }

}
