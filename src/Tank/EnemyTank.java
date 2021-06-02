package Tank;

import Utils.TankOfUtils;

import java.awt.*;

import static GameFrame.GPanel.*;
import static Utils.AllConstNature.*;
import static Utils.TankOfUtils.getImageURL;

public class EnemyTank extends  CreateTank{
    public static final Image[] ENEMY_TANK_IMAGE_ONE = new Image[4]; //敌方坦克图片样式1
    public static final Image[] ENEMY_TANK_IMAGE_TWO = new Image[4]; //敌方坦克图片样式2
    public static final Image[] ENEMY_TANK_IMAGE_THREE = new Image[4]; //敌方坦克图片样式3

    static {
        //在静态代码块中将坦克图片载入(插入顺序与上下右左对应）

        ENEMY_TANK_IMAGE_ONE[0] = getImageURL("src\\GameImage\\EnemyTankImage\\StyleTwo\\enemy_UP.png");
        ENEMY_TANK_IMAGE_ONE[1] = getImageURL("src\\GameImage\\EnemyTankImage\\StyleTwo\\enemy_DOWN.png");
        ENEMY_TANK_IMAGE_ONE[2] = getImageURL("src\\GameImage\\EnemyTankImage\\StyleTwo\\enemy_RIGHT.png");
        ENEMY_TANK_IMAGE_ONE[3] = getImageURL("src\\GameImage\\EnemyTankImage\\StyleTwo\\enemy_LEFT.png");

        ENEMY_TANK_IMAGE_TWO[0] = getImageURL("src\\GameImage\\EnemyTankImage\\StyleOne\\enemy_1up.png");
        ENEMY_TANK_IMAGE_TWO[1] = getImageURL("src\\GameImage\\EnemyTankImage\\StyleOne\\enemy_1down.png");
        ENEMY_TANK_IMAGE_TWO[2] = getImageURL("src\\GameImage\\EnemyTankImage\\StyleOne\\enemy_1right.png");
        ENEMY_TANK_IMAGE_TWO[3] = getImageURL("src\\GameImage\\EnemyTankImage\\StyleOne\\enemy_1left.png");

        ENEMY_TANK_IMAGE_THREE[0] = getImageURL("src\\GameImage\\EnemyTankImage\\StyleThree\\enemy_3up.png");
        ENEMY_TANK_IMAGE_THREE[1] = getImageURL("src\\GameImage\\EnemyTankImage\\StyleThree\\enemy_3down.png");
        ENEMY_TANK_IMAGE_THREE[2] = getImageURL("src\\GameImage\\EnemyTankImage\\StyleThree\\enemy_3right.png");
        ENEMY_TANK_IMAGE_THREE[3] = getImageURL("src\\GameImage\\EnemyTankImage\\StyleThree\\enemy_3left.png");
    }

    private long pastTime = System.currentTimeMillis();  //敌人坦克的计时器
    private Image[] EnemyTankStyle; //敌方坦克的样式
    //射击间隔计时器
    private long shotIntervalCounter = System.currentTimeMillis();

    /***
     * 生成敌方坦克
     */
    public EnemyTank() {};

    /***
     * 重置当前坦克信息
     */
    public void resetEnemyTank() {
        int radius = getRadius();
        int x = TankOfUtils.getRandomPositionOfEnemy() == ENEMY_BORN_IN_LEFT ? radius : jPanelWidth - radius; //　坦克出生的ｘ坐标
        int y = radius; //坦克出生的ｙ坐标
        //TODO 敌方坦克的属性未设置完成
        setPosition_X(x); // x坐标
        setPosition_Y(y);// y坐标
        setHp(DEFAULT_HP);
        setColor(TankOfUtils.randomColor()); //颜色随机
        setDirection(DIR_DOWN);  //direction默认向下
        setState(STATE_MOVE);  //moveState 默认移动

        setEnemy(true);  //设置敌方坦克属性
        setEnemyTankStyle(TankOfUtils.getEnemyTankStyle());  //随机获取坦克的样式
    }

    @Override
    /***
     * 重写父类方法，绘制敌方坦克
     */
    public void drawTank(Graphics g) {
        theAIEnemy(); // 自主控制
        judgeMove(); // 处理坦克的移动
        g.drawImage(EnemyTankStyle[getDirection()],getPosition_X() -getRadius(),getPosition_Y() -getRadius(),null); //依据坦克正中心作图

        drawTankNameAndBloodBar(g); //画出坦克的血条

        fire(g);//发射炮弹

        drawExplosion(g);//被击中就画出爆炸效果

    }

    /***
     * 给敌方坦克添加自主性
     */
    private void theAIEnemy() {
        long nowTime = System.currentTimeMillis();
        //更新射击状态
        if(nowTime - pastTime > ABLE_TO_SHOT_INTERVAL) {
            int chance = TankOfUtils.getRandomChanceToShot();
            if(chance < CHANCE_OF_ABLE_TO_SHOT) {
                if(System.currentTimeMillis() - shotIntervalCounter > SHOT_INTERVAL) {
                    generateBullet();//就生成子弹开火

                    shotIntervalCounter = System.currentTimeMillis(); //重置计时器
                }

            }
        }
        if(nowTime - pastTime > ABLE_TO_MOVE_INTERVAL) {
            //更新方向
            int dir = TankOfUtils.getRandomDirection();
            setDirection(dir);
            //定时更新敌人运动状态
            int state = TankOfUtils.getRandomStateOfEnemy();
            setState(state);
            pastTime = System.currentTimeMillis();  //重置状态计算时间
        }
    }

/****************************************************************属性的获取方法***********************************************/
    public long getPastTime() {
        return pastTime;
    }

    public void setPastTime(long pastTime) {
        this.pastTime = pastTime;
    }

    public Image[] getEnemyTankStyle() {
        return EnemyTankStyle;
    }

    public void setEnemyTankStyle(Image[] enemyTankStyle) {
        EnemyTankStyle = enemyTankStyle;
    }
}
