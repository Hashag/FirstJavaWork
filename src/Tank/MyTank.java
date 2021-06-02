package Tank;

import Utils.TankOfUtils;

import java.awt.*;
import  static Utils.AllConstNature.*;

import static Utils.TankOfUtils.getImageURL;

public class MyTank extends CreateTank{

    public  static final int AIM_SCORES = TOTAL_SCORES;  //目标的分

    private  int scores = 0; //我的得分

    public static final Image[] MY_TANK_IMAGE = new Image[4]; //己方坦克图片

    static {
        //在静态代码块中将坦克图片载入(插入顺序与上下右左对应）

        MY_TANK_IMAGE[0] = getImageURL("src\\GameImage\\MyTankImage\\tank_UP.png");
        MY_TANK_IMAGE[1] = getImageURL("src\\GameImage\\MyTankImage\\tank_DOWN.png");
        MY_TANK_IMAGE[2] = getImageURL("src\\GameImage\\MyTankImage\\tank_RIGHT.png");
        MY_TANK_IMAGE[3] = getImageURL("src\\GameImage\\MyTankImage\\tank_LEFT.png");
    }

    /***
     *生成己方坦克
     * @param position_X 坦克位置的X坐标
     * @param position_Y 坦克位置的Y坐标
     * @param direction  坦克初始的朝向
     */
    public MyTank(int position_X,int position_Y,int direction) {
        setPosition_X(position_X);
        setPosition_Y(position_Y);
        setDirection(direction);
        setColor(TankOfUtils.randomColor());  //随机获取坦克颜色
    }

    @Override
    /***
     * 绘制坦克
     * @param g 当前面板的画笔
     */
    public void drawTank(Graphics g) {
        judgeMove(); // 处理坦克的移动

        g.drawImage(MY_TANK_IMAGE[getDirection()],getPosition_X() -getRadius(),getPosition_Y() -getRadius(),null);//依据坦克正中心作图

        drawTankNameAndBloodBar(g); //画出坦克的血条

        fire(g);  //发射炮弹

        drawExplosion(g);  //被击中就画出爆炸效果

        }

    public int getScores() {
        return scores;
    }

    public void setScores(int scores) {
        this.scores = scores;
    }
}
