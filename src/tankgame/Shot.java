package tankgame;

/**
 * @author Zhang Yu
 * @version 1.0
 */
public class Shot implements Runnable{
    private int x;
    private int y;
    private int direct;
    private int speed = 5;
    private boolean isLive = true;

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSpeed() {
        return speed;
    }

    public Shot(int x, int y, int direct) {
        this.x = x;
        this.y = y;
        this.direct = direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }

    public void setLive(boolean live) {
        isLive = live;
    }

    public int getDirect() {
        return direct;
    }

    public boolean isLive() {
        return isLive;
    }

    @Override
    public void run() {
        while (isLive) {
            switch (direct) {
                case 0:
                    y -= speed;
                    break;
                case 1:
                    x += speed;
                    break;
                case 2:
                    y += speed;
                    break;
                case 3:
                    x -= speed;
                    break;
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(!(x >= 0 && x <= 1000 && y >= 0 && y <= 750)) {
                isLive = false;
            }
        }
    }
}
