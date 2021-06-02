package Map;

import Tank.CreateBullet;
import Tank.ExplosionSpecialEffects;
import Utils.Pool;
import Utils.TankOfUtils;

import javax.sound.sampled.Clip;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static Tank.CreateTank.*;

public  class Brick {

    public static final Image[] BRICK_IMAGE = new Image[2];

    static {
        BRICK_IMAGE[0] = TankOfUtils.getImageURL("src\\GameImage\\Brick\\brick.png");
        BRICK_IMAGE[1] = TankOfUtils.getImageURL("src\\GameImage\\Brick\\iron.png");
    }
    public static final int IRON_BRICK_HP = DEFAULT_HP * 3; //铁砖默认生命值
    public static final int NORMAL_BRICK_HP = DEFAULT_HP; //普通砖默认生命值

    //TODO  (图片资源无法加载）
    //保持砖的图片大小相同：24*24
    public static final int width = 24;
    public static final int height = 24;

    private int brickHP;
    private boolean isIron = false;
    private int positionX;
    private int positionY;
    private Image image;
    private boolean isVisible = true; //可见性

    private final List<ExplosionSpecialEffects> explosionBrick = new ArrayList<>(50); //砖块的爆炸效果

    //绘制砖块
    public  void drawBrick(Graphics g) {
        //调用子类的方法
    }

    /***
     * 被炮弹击中时添加爆炸特效到集合中
     */
    public void addExplosion(CreateBullet bullet,int row,int column) {
        ExplosionSpecialEffects explosionFromPool = Pool.ExplosionPool.getExplosionFromPool();
        //修正爆炸效果属性
        explosionFromPool.setPosition_X(positionX+width/2);
        explosionFromPool.setGetPosition_Y(positionY+height/2);
        explosionBrick.add(explosionFromPool); //装入集合中
        Clip clip = TankOfUtils.playMusic("src\\BGM\\hit.wav");
        clip.start();
        getHurt(bullet,row,column); //受到炮弹打击掉血
    }

    /***
     * 画出砖实时的爆炸效果
     */
    public void drawExplosion(Graphics graphics) {
        for (ExplosionSpecialEffects explosion : explosionBrick) {
            explosion.drawExplosion(graphics); //画出爆炸效果
        }

        //及时归还爆炸效果
        for (int i = 0; i < explosionBrick.size(); i++) {
            ExplosionSpecialEffects explosion = explosionBrick.get(i);
            if(!explosion.isVisible()) {

                ExplosionSpecialEffects remove = explosionBrick.remove(i);
                Pool.ExplosionPool.giveBackTheExplosion(remove);
            }
        }
    }

    /***
     * 砖块被打击
     */
    public void getHurt(CreateBullet bullet,int row,int column) {
        if(brickHP - bullet.getAttack() > 0) {
            brickHP -=bullet.getAttack();
        } else{
            isVisible = false; //被摧毁了就不可见了
            GMap.PROPS[row][column] = null; //移走被破坏的墙体
        }
    }

    /**********************************************属性获取的方法**************************************************/



    public int getBrickHP() {
        return brickHP;
    }

    public void setBrickHP(int brickHP) {
        this.brickHP = brickHP;
    }

    public boolean isIron() {
        return isIron;
    }

    public void setIron(boolean iron) {
        isIron = iron;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public List<ExplosionSpecialEffects> getExplosionBrick() {
        return explosionBrick;
    }
}
