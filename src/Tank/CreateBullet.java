package Tank;

import java.awt.*;
import static Tank.CreateTank.*;
import  static Utils.AllConstNature.*;

public class CreateBullet {

    public static final Image[] BULLET_IMAGE = new Image[4];
    static {
        Toolkit toolkit = Toolkit.getDefaultToolkit();  //获取图片URL的工具
        BULLET_IMAGE[0] = toolkit.createImage("src\\GameImage\\BulletImage\\Style\\One\\bullet_up.png");
        BULLET_IMAGE[1] = toolkit.createImage("src\\GameImage\\BulletImage\\Style\\One\\bullet_down.png");
        BULLET_IMAGE[2] = toolkit.createImage("src\\GameImage\\BulletImage\\Style\\One\\bullet_right.png");
        BULLET_IMAGE[3] = toolkit.createImage("src\\GameImage\\BulletImage\\Style\\One\\bullet_left.png");
    }

    public static final int DEFAULT_RADIUS_OF_BULLET = DEFAULT_RADIUS / 4;  // TODO 暂定大小为坦克的1/5
    public static final int DEFAULT_SPEED_OF_BULLET = 10; // TODO  暂定每帧移速为坦克2倍

    private int radius = DEFAULT_RADIUS_OF_BULLET;  //炮弹大小
    private  int speed = DEFAULT_SPEED_OF_BULLET;  //炮弹移速
    private int position_X;  //位置(即炮管口末端）
    private int position_Y;
    private boolean  isVisible = true;  //炮弹是否飞出界面 （默认是可见的）

    private int attack; //攻击力  //TODO  攻击力没有设置
    private int direction;
    private Color color; // 坦克的颜色
    private boolean isEnemy = false; //是否属于敌人的炮弹

    /***
     * 炮弹的相关属性依据坦克而定
     * @param position_X x
     * @param position_Y y
     * @param direction  坦克方向
     * @param color  坦克的颜色
     */
    public CreateBullet(int position_X, int position_Y, int direction, Color color,int attack) {
        this.position_X = position_X;
        this.position_Y = position_Y;
        this.direction = direction;
        this.color = color;
        this.attack = attack;
    }

    /***
     * 重置炮弹属性
     */
    public void resetTheBullet() {
        isVisible = true;
    }

    /***
     * 绘制炮弹（敌人为彩色，己方为指定类型）
     * @param graphics 系统画笔
     *
     */
    public void drawBullet(Graphics graphics) { //供坦克调用发射炮弹
        if (isVisible) { //不可见就不再进行绘制
        if(!isEnemy) {
                graphics.drawImage(BULLET_IMAGE[direction], position_X - radius, position_Y - radius, null);

            } else {
                graphics.setColor(this.color); //画笔改变为坦克的颜色
                graphics.fillOval(position_X, position_Y, 2 * radius, 2 * radius);
            }
            judge();
        }
    }
//TODO   炮弹处理的逻辑
    /***
     * 处理炮弹打击目标的反应
     */
    private void judge() {
        moveBullet();
    }
        /***
         * 炮弹移动 (炮弹可以出边界)
         */
        private void moveBullet(){
            switch (direction) {
                case DIR_UP:
                    position_Y -= speed;
                    if(position_Y < 0) isVisible = false;
                    break;
                case DIR_DOWN:
                    position_Y += speed;
                    if(position_Y > FRAME_HEIGHT) isVisible = false;
                    break;
                case DIR_RIGHT:
                    position_X += speed;
                    if(position_X > FRAME_WIDTH) isVisible = false;
                    break;
                case DIR_LEFT:
                    position_X -= speed;
                    if (position_X < 0) isVisible = false;
                    break;
            }
        }

    /***********************************以下是炮弹相关属性设置/获取方法****************************************/
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

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public boolean isEnemy() {
        return isEnemy;
    }

    public void setEnemy(boolean enemy) {
        isEnemy = enemy;
    }
}
