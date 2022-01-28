package tankgame;

/**
 * @author Zhang Yu
 * @version 1.0
 */
public class Tank {
    private int x;
    private int y;
    private int direct;
    private int speed = 5;
    private boolean isLive = true;

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setLive(boolean live) {
        isLive = live;
    }

    public int getSpeed() {
        return speed;
    }

    public boolean isLive() {
        return isLive;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDirect() {
        return direct;
    }

    public Tank(int x, int y, int direct) {
        this.x = x;
        this.y = y;
        this.direct = direct;
    }

    public void moveRight(){
        if(getDirect() != 1)
            setDirect(1);
        setX(getX() + speed);
    }
    public void moveLeft(){
        if(getDirect() != 3)
            setDirect(3);
        setX(getX() - speed);
    }
    public void moveUp(){
        if(getDirect() != 0)
            setDirect(0);
        setY(getY() - speed);
    }
    public void moveDown(){
        if(getDirect() != 2)
            setDirect(2);
        setY(getY() + speed);
    }
}
