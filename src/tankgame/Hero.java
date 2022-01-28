package tankgame;

import java.util.Vector;

/**
 * @author Zhang Yu
 * @version 1.0
 */
public class Hero extends Tank implements Runnable{
    Vector<Shot> shots = new Vector<>();
    public Hero(int x, int y, int direct) {
        super(x, y, direct);
    }

    public void shot() {
        if (shots.size() < 3) {
            Shot shot = null;
            switch (getDirect()) {
                case 0:
                    shot = new Shot(getX() + 20, getY(), 0);
                    break;
                case 1:
                    shot = new Shot(getX() + 60, getY() + 20, 1);
                    break;
                case 2:
                    shot = new Shot(getX() + 20, getY() + 60, 2);
                    break;
                case 3:
                    shot = new Shot(getX(), getY() + 20, 3);
                    break;
            }
            shots.add(shot);
            new Thread(shot).start();
        }
    }

    @Override
    public void run() {
        while (isLive()) {
            for (int i = 0; i < shots.size(); i++) {
                Shot shot = shots.get(i);
                if (!shot.isLive()) {
                    shots.remove(shot);
                }
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
