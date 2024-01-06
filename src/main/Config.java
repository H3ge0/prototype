package main;

import java.io.*;

public class Config {

    GamePanel gp;

    public Config(GamePanel gp){
        this.gp = gp;
    }

    public void saveConfig(){
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("config.txt"));

            //Full Screen
            if(gp.fullScreenOn)
                bw.write("On");
            else
                bw.write("Off");
            bw.newLine();

            //Music Volume
            bw.write(String.valueOf(gp.music.volumeScale));
            bw.newLine();

            //SFX Volume
            bw.write(String.valueOf(gp.soundEffect.volumeScale));
            bw.newLine();

            //HP and Energy
            if(gp.barMode)
                bw.write("Bar");
            else
                bw.write("Normal");

            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadConfig(){
        try {
            BufferedReader br = new BufferedReader(new FileReader("config.txt"));

            //Full Screen
            String str = br.readLine();
            if(str.equals("On")){
                gp.fullScreenOn=true;
            }else if(str.equals("Off")){
                gp.fullScreenOn=false;
            }

            //Music Volume
            str = br.readLine();
            gp.music.volumeScale=Integer.parseInt(str);

            //SFX Volume
            str = br.readLine();
            gp.soundEffect.volumeScale=Integer.parseInt(str);

            //HP and Energy
            str = br.readLine();
            if(str.equals("Bar"))
                gp.barMode = true;
            else if(str.equals("Normal"))
                gp.barMode = false;

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
