package Map;

import GameFrame.GPanel;
import Tank.CreateBullet;
import Tank.CreateTank;
import Tank.ExplosionSpecialEffects;
import Utils.Pool;
import Utils.TankOfUtils;

import javax.sound.sampled.Clip;
import java.awt.*;
import static Utils.AllConstNature.*;


public class MyHome extends Brick{

    public static  Image homeImage; //基地的图片
    public static final int DEFAULT_WIDTH = Brick.width*6; //默认宽
    public static final int DEFAULT_HEIGHT = Brick.height*6; //默认高
    private int width = DEFAULT_WIDTH;
    private int height = DEFAULT_HEIGHT;
    private boolean isDead = false;

    static  {
        homeImage = TankOfUtils.getImageURL("src\\GameImage\\Home\\jiege.png");
    }

    public MyHome() {
        int x = (GPanel.jPanelWidth - width)/2;
        int y = GPanel.jPanelHeight - height;
        setImage(homeImage);
        setBrickHP(CreateTank.DEFAULT_HP / 2);
        setPositionX(x+22);
        setPositionY(y);
    }

    /***
     * 画出基地
     * @param graphics
     */
    public void drawHome(Graphics graphics) {

        graphics.drawImage(homeImage,getPositionX(),getPositionY(),null);
        drawExplosion(graphics);


    }

    /***
     *
     * @param bullet 打中基地的炮弹
     */
    private void getHurt(CreateBullet bullet) {
        int attack = bullet.getAttack();
        if(getBrickHP() - attack > 0) {
            setBrickHP(getBrickHP() -attack);

        } else  {
            //TODO  add result to when base is destroyed
            isDead = true;
            GPanel.gameState = STATE_FAIL;
            Clip clip = TankOfUtils.playMusic("src\\BGM\\lose.wav");
            clip.start();
            MUSIC_THEME.stop(); //停止播放主题背景
        }
    }


    /***
     *
     * @param bullet 打中基地的炮弹
     */
    public void addExplosion(CreateBullet bullet) {
        ExplosionSpecialEffects explosionFromPool = Pool.ExplosionPool.getExplosionFromPool();
        //修正爆炸效果属性
        explosionFromPool.setPosition_X(getPositionX() + width/2);
        explosionFromPool.setGetPosition_Y(getPositionY()+height/2);
        getExplosionBrick().add(explosionFromPool); //装入集合中
        getHurt(bullet); //受到炮弹打击掉血
    }

    /**************************************************************************************/
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }





    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }
}
