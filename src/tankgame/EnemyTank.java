package tankgame;

import java.util.Vector;

/**
 * @author Zhang Yu
 * @version 1.0
 */
public class EnemyTank extends Tank implements Runnable {
    Vector<Shot> shots = new Vector<>();

    public EnemyTank(int x, int y, int direct) {
        super(x, y, direct);
    }

    @Override
    public void run() {
        while (isLive()) {
            //敌方发射子弹
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
                        shot = new Shot(getX() + 20, getY() + 60, 1);
                        break;
                    case 3:
                        shot = new Shot(getX() + 20, getY(), 1);
                        break;
                }
                shots.add(shot);
                new Thread(shot).start();
            }

            //敌方方向改变
            int randomDirect = (int) (Math.random() * 4);
            switch (randomDirect) {
                case 0:
                    for (int i = 0; i < 30; i++) {
                        if(getY() <= 0)
                            break;
                        moveUp();
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 1:
                    for (int i = 0; i < 30; i++) {
                        if(getX() + 60 >= 1000)
                            break;
                        moveRight();
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 2:
                    for (int i = 0; i < 30; i++) {
                        if(getY() + 60 >= 750)
                            break;
                        moveDown();
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 3:
                    for (int i = 0; i < 30; i++) {
                        if(getX() <= 0)
                            break;
                        moveLeft();
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }

            //清除敌方失效子弹
            for (int i = 0; i < shots.size(); i++) {
                Shot shot = shots.get(i);
                if(!shot.isLive()) {
                    shots.remove(shot);
                }
            }
        }
    }
}

