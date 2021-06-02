package Tank;

import static Utils.AllConstNature.*;

import Map.Brick;
import Map.GMap;
import Utils.Pool;
import Utils.TankOfUtils;

import javax.sound.sampled.Clip;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import  static GameFrame.GPanel.*;

public abstract class CreateTank {

    //TODO 给各属性设置默认值
    public static final int DEFAULT_SPEED = 4;   //每帧跑4像素
    public static final int DEFAULT_RADIUS = 20; //默认半径20像素
    public static final int DEFAULT_HP = 1000; //默认生命值

    //四个方向(上下左右）
    public static final int DIR_UP = 0;
    public static final int DIR_DOWN = 1;
    public static final int DIR_RIGHT = 2;
    public static final int DIR_LEFT = 3;
    //坦克三种状态
    public static final int STATE_STAND = 0; //静止
    public static final int STATE_MOVE = 1;   //移动
    public static final int STATE_DEAD = 2; //阵亡

    //坦克攻击力范围
    public static final int ATK_MAX = 100; //最大伤害
    public static final int ATK_MIN = 80;  //最小伤害

    //坦克的属性
    private int radius = DEFAULT_RADIUS;//坦克半径
    private int speed = DEFAULT_SPEED; //每帧移速
    private int position_X;
    private int position_Y;
    private int hp = DEFAULT_HP;  //生命值
    private int attack = TankOfUtils.getAttack(); //攻击力
    private int direction;
    private int state = STATE_STAND; //坦克状态（静止/运动）
    private Color color; // 坦克的颜色
    private boolean isEnemy = false; //默认不是敌方坦克
    private BloodBar bloodBar = new BloodBar(); //坦克的血条
    private String nickName = TankOfUtils.getTankNickname(); //坦克昵称

    //装爆炸特效的集合（坦克可能被多个子弹命中）
    public final  List<ExplosionSpecialEffects> explosionList = new ArrayList<>(50);

    //TODO 定义炮弹弹夹
    public final List<CreateBullet> bulletList = new ArrayList<>();  //装炮弹的集合（弹夹）

    /***
     * 使用子类方法绘制坦克
     * @param g 当前面板的画笔
     */
    public abstract void  drawTank(Graphics g);


    //TODO 坦克运动状态其他情况（阵亡）待完善
    /***
     * 判断坦克的状态，并进行对应的操作
     */
    public void judgeMove() {
        switch (state) {
            case STATE_STAND:
                break;
            case STATE_MOVE:
                moveTank();  //使坦克移动
                break;
            case STATE_DEAD:
                //System.exit(0);
                die(); //阵亡
                break;
        }
    }

    /***
     * 坦克阵亡的逻辑
     */
    private void die() {
        if(!isEnemy) { //敌人阵亡时
        //己方阵亡时

            gameState = STATE_FAIL; //将游戏状态设置为结束
            Clip clip = TankOfUtils.playMusic("src\\BGM\\lose.wav");
            clip.start();
            MUSIC_THEME.stop(); //停止播放主题背景
        }
    }

    /***
     * 实现坦克移动(四个方向移动） 并检测与墙体的碰撞
     */
    private void moveTank() {
        //记录之前位置
        int beX = position_X;
        int beY = position_Y;
                    switch (direction) {
                        case DIR_UP: //上移
                                position_Y -= speed;
                                if (position_Y - radius < radius)
                                    position_Y = radius;
                                break;
                        case DIR_DOWN: //下移
                                position_Y += speed;
                                if (position_Y + radius > jPanelHeight)
                                    position_Y = beY;
                            break;
                        case DIR_RIGHT: //右移
                                position_X += speed;
                                if (position_X + radius > jPanelWidth)
                                    position_X = beX;
                            break;
                        case DIR_LEFT: // 左移
                                position_X -= speed;
                                if (position_X - radius < 0)
                                    position_X = beX;
                            break;
                    }

                    //检测与砖块的碰撞
        for (int i = 0; i < GAME_MAP_ROWS; i++) {
            for (int j = 0; j < GAME_MAP_COLUMNS; j++) {
                Object o = GMap.PROPS[i][j];
                if (!Objects.equals(o, null)) {
                    if (o instanceof Brick) {
                        Brick brick = (Brick) o;
                        boolean b = TankOfUtils.isCollide(position_X, position_Y, radius, ((Brick) o).getPositionX(), ((Brick) o).getPositionY(), Brick.width, Brick.height);
                        if(b) {
                            switch (direction) {
                                case DIR_UP:
                                case DIR_DOWN:
                                    position_Y = beY;
                                    break;
                                case DIR_RIGHT:
                                case DIR_LEFT:
                                    position_X = beX;
                                    break;
                            }
                        }
                    }
                }
            }
        }
                    } // TODO 待添加其他种类道具


    /***
     * 从炮弹池中取出炮弹炮弹,并修正炮弹相关参数，装入弹夹中，以供发射
     */
    public void generateBullet() {
            int x = position_X;
            int y =position_Y;
            switch (direction) {
                case DIR_UP:
                    y = position_Y - radius;
                    x -= 2;
                    break;
                case DIR_DOWN:
                    y = position_Y + radius;
                    x -= 2;
                    break;
                case DIR_RIGHT:
                    x = position_X + radius;
                    y -= 2;
                    break;
                case DIR_LEFT:
                    x = position_X - radius;
                    y -= 2;
                    break;
            }
            //依附于坦克的基本信息创建炮弹
            CreateBullet bulletFromPool = Pool.BulletPool.getBulletFromPool();
            //设置炮弹信息
            bulletFromPool.setPosition_X(x);
            bulletFromPool.setPosition_Y(y);
            bulletFromPool.setDirection(direction);
            bulletFromPool.setColor(color);
            bulletFromPool.setAttack(attack);
            bulletFromPool.setEnemy(isEnemy);

            bulletList.add(bulletFromPool); //将炮弹装入弹夹中

    }


    /***
     * 发射炮弹（发射完了之后就归还）
     */
    public void fire(Graphics graphics) {

        //遍历弹夹，打出现存的炮弹
        for (int i = 0; i < bulletList.size(); i++) {
            CreateBullet bullet = bulletList.get(i);
            if(!bullet.isVisible()) {
                bullet.setVisible(true);

                CreateBullet remove = bulletList.remove(i); //（必须将此行单独写为一行，否则无法及时移除无用的炮弹）不一定

                Pool.BulletPool.giveBackTheBullet(remove);

            }
            //
        }
        for (CreateBullet bullet : bulletList) {
            bullet.drawBullet(graphics); //在炮筒处绘制炮弹
        }


    }

    /***
     * 被炮弹击中时添加爆炸特效到集合中
     */
    public void addExplosion(CreateBullet bullet) {
        ExplosionSpecialEffects explosionFromPool = Pool.ExplosionPool.getExplosionFromPool();
        //修正爆炸效果属性
        explosionFromPool.setPosition_X(getPosition_X());
        explosionFromPool.setGetPosition_Y(getPosition_Y());
        explosionList.add(explosionFromPool); //装入集合中
        Clip clip = TankOfUtils.playMusic("src\\BGM\\bang.wav");
        clip.start();
        getHurt(bullet); //受到炮弹打击掉血
    }

    /***
     * 画出坦克实时的爆炸效果
     */
    public void drawExplosion(Graphics graphics) {
        for (ExplosionSpecialEffects explosion : explosionList) {
            explosion.drawExplosion(graphics); //画出爆炸效果
        }

        //及时归还爆炸效果
        for (int i = 0; i < explosionList.size(); i++) {
            ExplosionSpecialEffects explosion = explosionList.get(i);
            if(!explosion.isVisible()) {

                ExplosionSpecialEffects remove = explosionList.remove(i);
                Pool.ExplosionPool.giveBackTheExplosion(remove);
            }
        }
    }

    /***
     * 受到炮弹打击
     */
    private void getHurt(CreateBullet bullet) {
        if(hp - bullet.getAttack() > 0) {
            hp -= bullet.getAttack(); //被击中就减少血量
        } else {
            state = STATE_DEAD; //血条空了就切换状态
        }

    }
// TODO 待调整高度
    /***
     * 画出坦克昵称
     */
    private void drawNickName(Graphics g) {
        int nameLen = BloodBar.lenBar/4; //名字宽度
        int nameHei = BloodBar.heightBar <<1; //名字高度
        int nameX = bloodBar.barX ; //x坐标
        int nameY = bloodBar.barY -2; //y坐标
        g.setColor(Color.BLACK);
        g.setFont(FONT_TANK_NAME); //设置字体样式
        g.drawString(nickName,nameX - nameLen,nameY - nameHei); //获取昵称

    }

    /***
     * 画出坦克的血条
     * @param g
     */
    public void drawTankNameAndBloodBar(Graphics g) {

        bloodBar.drawBloodBar(g); //画出坦克的血条
        drawNickName(g);

    }

    //血条类
    private class BloodBar{
        public static  final  int heightBar = 5; //血条默认高度
        public static final int lenBar = 40; //血条默认长度
        private int barX; //血条x坐标
        private int barY; //血条y坐标

        /***
         * 画出血条
         * @param graphics 系统提供的画笔
         */
        public void drawBloodBar(Graphics graphics) {

            barX = position_X;
            barY = position_Y - radius - 2;
            int nowHpLen;
           /* if(hp < 0) {
               nowHpLen = 0;
            } else {*/
                nowHpLen = (int)(hp/(double)DEFAULT_HP * lenBar); //将血条按百分比绘制
            /*}*/


            graphics.setColor(Color.ORANGE); //血条底色
            graphics.fillRect(barX-lenBar/2,barY-heightBar,lenBar,heightBar); //画出血条框架
            graphics.setColor(Color.green); //边框颜色
            graphics.drawRect(barX-lenBar/2,barY-heightBar,lenBar,heightBar); //描边框
            graphics.setColor(Color.RED); //血条颜色

            graphics.fillRect(barX-lenBar/2,barY-heightBar,nowHpLen,heightBar);
        }

    }


    /***************************************** 属性的获取与设置************************************************/


    public boolean isEnemy() {
        return isEnemy;
    }

    public void setEnemy(boolean enemy) {
        isEnemy = enemy;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }
    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getPosition_X() {
        return position_X;
    }

    public void setPosition_X(int position_X) {
        this.position_X = position_X;
    }

    public int getPosition_Y() {
        return position_Y;
    }

    public void setPosition_Y(int position_Y) {
        this.position_Y = position_Y;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public BloodBar getBloodBar() {
        return bloodBar;
    }

    public void setBloodBar(BloodBar bloodBar) {
        this.bloodBar = bloodBar;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getNickName() {
        return nickName;
    }
}
