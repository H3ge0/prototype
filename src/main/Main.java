package main;

import javax.swing.*;
import java.util.Objects;

public class Main {

    public static JFrame frame;

    public static void main(String[] args) {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setTitle("Gopi");
        new Main().setIcon();

        GamePanel gamePanel = new GamePanel();
        frame.add(gamePanel);

        gamePanel.config.loadConfig();
        if(gamePanel.fullScreenOn)
            frame.setUndecorated(true);

        frame.pack();

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        gamePanel.setupGame();
        gamePanel.startGameThread();
    }

    public void setIcon(){
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("player/gopi_down_idle.png")));
        frame.setIconImage(icon.getImage());
    }

}
