package tankgame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Iterator;
import java.util.Vector;

/**
 * @author Zhang Yu
 * @version 1.0
 */
public class MyPanel extends JPanel implements KeyListener, Runnable {
    Hero hero = null;
    Vector<EnemyTank> enemyTanks = new Vector<>();

    public MyPanel() {
        //初始化我方坦克
        hero = new Hero(400, 400, 0);
        //初始化敌方坦克
        for (int i = 0; i < 3; i++) {
            EnemyTank enemyTank = new EnemyTank(100 + 100*i, 100, 2);
            enemyTanks.add(enemyTank);
            new Thread(enemyTank).start();
        }
        //读取记录
        Recorder.readRecord();

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 1000, 750);
        if (hero.isLive())
            drawTank(hero.getX(), hero.getY(), hero.getDirect(), g, 1);

        drawRecord(g);
        //绘制我方子弹
        for (int i = 0; i < hero.shots.size(); i++) {
            Shot shot = hero.shots.get(i);
            if (shot != null && shot.isLive()) {
                drawShot(shot.getX(), shot.getY(), g);
            } else {
                //子弹失效，则删除
                hero.shots.remove(shot);
            }
        }

        //绘制敌方坦克
        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            if (enemyTank.isLive())
                drawTank(enemyTank.getX(), enemyTank.getY(), enemyTank.getDirect(), g, 0);
            //绘制敌方子弹
            //使用for 循环遍历元素remove后，Vector数组会前移，导致有一个元素会没有遍历到
            //使用iterator遍历可以防止remove后没有遍历到的情况，但是会出现并发修改异常
            for (int j = 0; j < enemyTank.shots.size(); j++) {
                Shot shot = enemyTank.shots.get(j);
                if (shot != null && shot.isLive()) {
                    drawShot(shot.getX(), shot.getY(), g);
                } else {
                    //子弹失效，则删除
                    enemyTank.shots.remove(shot);
                }
            }
        }
    }

    public void drawRecord(Graphics g) {
        g.setColor(Color.BLACK);
        Font font = new Font("宋体", Font.BOLD, 25);
        g.setFont(font);
        g.drawString("您累计击毁敌方坦克：", 1020, 30);
        drawTank(1020, 60, 0, g, 0);
        g.setColor(Color.BLACK);
        g.drawString(Recorder.getCount() + "", 1080, 100);
    }

    public void drawTank(int x, int y, int direct, Graphics g, int type) {
        switch (type) {
            case 0:
                g.setColor(Color.RED);
                break;
            case 1:
                g.setColor(Color.GREEN);
                break;
        }

        switch (direct) {
            case 0: //表示向上
                g.fill3DRect(x, y, 10, 60, false);//画出坦克左边的轮子
                g.fill3DRect(x + 30, y, 10, 60, false);//画出坦克右边的轮子
                g.fill3DRect(x + 10, y + 10, 20, 40, false);//画出坦克盖子
                g.fillOval(x + 10, y + 20, 20, 20);//画出圆形的盖子
                g.drawLine(x + 20, y + 30, x + 20, y); //画出坦克的炮管
                break;
            case 1: //表示向右
                g.fill3DRect(x, y, 60, 10, false);//画出坦克上面的轮子
                g.fill3DRect(x, y + 30, 60, 10, false);//画出坦克下面的轮子
                g.fill3DRect(x + 10, y + 10, 40, 20, false);//画出坦克盖子
                g.fillOval(x + 20, y + 10, 20, 20);//画出圆形的盖子
                g.drawLine(x + 30, y + 20, x + 60, y + 20); //画出坦克的炮管
                break;
            case 2: //表示向下
                g.fill3DRect(x, y, 10, 60, false);//画出坦克左边的轮子
                g.fill3DRect(x + 30, y, 10, 60, false);//画出坦克右边的轮子
                g.fill3DRect(x + 10, y + 10, 20, 40, false);//画出坦克盖子
                g.fillOval(x + 10, y + 20, 20, 20);//画出圆形的盖子
                g.drawLine(x + 20, y + 30, x + 20, y + 60); //画出坦克的炮管
                break;
            case 3: //表示向左
                g.fill3DRect(x, y, 60, 10, false);//画出坦克上面的轮子
                g.fill3DRect(x, y + 30, 60, 10, false);//画出坦克下面的轮子
                g.fill3DRect(x + 10, y + 10, 40, 20, false);//画出坦克盖子
                g.fillOval(x + 20, y + 10, 20, 20);//画出圆形的盖子
                g.drawLine(x + 30, y + 20, x, y + 20); //画出坦克的炮管
                break;
            default:
                System.out.println("暂时没有处理");
        }
    }

    public void drawShot(int x, int y, Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillRect(x, y, 1, 1);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                hero.moveUp();
                break;
            case KeyEvent.VK_D:
                hero.moveRight();
                break;
            case KeyEvent.VK_S:
                hero.moveDown();
                break;
            case KeyEvent.VK_A:
                hero.moveLeft();
                break;
            case KeyEvent.VK_J:
                hero.shot();
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //判断敌方子弹是否击中我方
            for (int i = 0; i < enemyTanks.size(); i++) {
                EnemyTank enemyTank = enemyTanks.get(i);
                for (int j = 0; j < enemyTank.shots.size(); j++) {
                    Shot shot = enemyTank.shots.get(j);
                    if(hero != null && hitHero(hero, shot))
                        enemyTank.shots.remove(shot);
                }
            }

            //判断我方子弹是否击中敌方
            for (int i = 0; i < enemyTanks.size(); i++) {
                EnemyTank enemyTank = enemyTanks.get(i);
                for(int j = 0; j < hero.shots.size(); j++) {
                    Shot shot = hero.shots.get(j);
                    if (enemyTank != null && hitEnemy(enemyTank, shot)) {
                        Recorder.addCount();
                        hero.shots.remove(shot);
                    }
                }
            }
            this.repaint();
        }
    }

    public boolean hitHero(Hero hero, Shot shot) {
        return hitTank(hero, shot);
    }

    public boolean hitEnemy(EnemyTank enemyTank, Shot shot) {
        return hitTank(enemyTank, shot);
    }

    public boolean hitTank(Tank tank, Shot shot) {
        switch (tank.getDirect()) {
            case 0:
            case 2:
                if (shot.getX() > tank.getX() && shot.getX() < tank.getX() + 40
                        && shot.getY() > tank.getY() && shot.getY() < tank.getY() + 60) {
                    tank.setLive(false);
                    shot.setLive(false);
                    return true;
                }
                break;
            case 1:
            case 3:
                if (shot.getX() > tank.getX() && shot.getX() < tank.getX() + 60
                        && shot.getY() > tank.getY() && shot.getY() < tank.getY() + 40) {
                    tank.setLive(false);
                    shot.setLive(false);
                    return true;
                }
                break;
        }
        return false;

    }
}
