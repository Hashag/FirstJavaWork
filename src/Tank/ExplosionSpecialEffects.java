package Tank;

import java.awt.*;

import static Utils.TankOfUtils.getImageURL;

public class ExplosionSpecialEffects {

    //TODO 暂定爆炸效果为4帧
    public static final Image[] EXPLOSION_IMAGE = new Image[10];  //爆炸效果的图片
    static {
        EXPLOSION_IMAGE[0] = getImageURL("src\\GameImage\\Boom\\Boom1.png");
        EXPLOSION_IMAGE[1] = getImageURL("src\\GameImage\\Boom\\Boom2.png");
        EXPLOSION_IMAGE[2] = getImageURL("src\\GameImage\\Boom\\Boom3.png");
        EXPLOSION_IMAGE[3] = getImageURL("src\\GameImage\\Boom\\Boom4.png");
        EXPLOSION_IMAGE[4] = getImageURL("src\\GameImage\\Boom\\h1.png");
        EXPLOSION_IMAGE[5] = getImageURL("src\\GameImage\\Boom\\h2.png");
        EXPLOSION_IMAGE[6] = getImageURL("src\\GameImage\\Boom\\h3.png");
        EXPLOSION_IMAGE[7] = getImageURL("src\\GameImage\\Boom\\h4.png");
        EXPLOSION_IMAGE[8] = getImageURL("src\\GameImage\\Boom\\h5.png");
        EXPLOSION_IMAGE[9] = getImageURL("src\\GameImage\\Boom\\h6.png");
    }
    public   static final int IMAGE_FPS = 2; // 显示一张图片所需帧数（可手动调节）

    private static final int TOTAL_FPS_NEED = EXPLOSION_IMAGE.length * IMAGE_FPS; //播放完爆炸效果所需的总帧数

    private int fpsCounter = 0; //  帧数计时器

    private int position_X; //爆炸效果所在的x坐标
    private int getPosition_Y; //爆炸效果所在的y坐标
    //设置该属性原因： 判断特效是否可见，不可见就不再绘制，从CreatTank的爆炸集合中移除
    private boolean isVisible = true;

    /***
     * 默认的构造器
     */
    public ExplosionSpecialEffects() {
    }

    /***
     * 重置属性
     */
    public void resetTheExplosion() {
        setVisible(true);
    }

    /***
     * 绘制爆炸效果（应该实时跟着坦克的位置变化而定）
     * @param graphics 系统提供的画笔
     */
    public void drawExplosion(Graphics graphics) {
        if(isVisible) { //可见才绘制
            Image image = EXPLOSION_IMAGE[fpsCounter / IMAGE_FPS];
            int halfImageWidth = image.getWidth(null) >> 1;  //图片宽的一半
            int halfImageHeight = image.getHeight(null) >> 1; //图片高的一半
            graphics.drawImage(image, position_X - halfImageWidth, getPosition_Y - halfImageHeight, null); //绘制图片
            fpsCounter++; //帧数加一
            if (fpsCounter >= TOTAL_FPS_NEED) {
                isVisible = false; //将爆炸效果可见性
                fpsCounter = 0; //重置帧数计时器
            }
        }

    }

/*************************************************获取/设置属性的方法**************************************************/
    public int getPosition_X() {
        return position_X;
    }

    public void setPosition_X(int position_X) {
        this.position_X = position_X;
    }

    public int getGetPosition_Y() {
        return getPosition_Y;
    }

    public void setGetPosition_Y(int getPosition_Y) {
        this.getPosition_Y = getPosition_Y;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }
}
