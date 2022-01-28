package tankgame;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author Zhang Yu
 * @version 1.0
 */
public class StartGame extends JFrame {
    MyPanel mp = null;

    public static void main(String[] args) {
        StartGame startGame = new StartGame();
    }

    public StartGame() {
        mp = new MyPanel();
        this.add(mp);//把面板加入到绘图区，里面会自动调用paint方法
        this.setSize(1500, 850);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.addKeyListener(mp);
        Thread thread = new Thread(mp);
        thread.start();
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Recorder.saveRecord();
            }
        });
    }
}
