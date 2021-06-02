package Utils;

import Tank.CreateBullet;
import javax.sound.sampled.*;
import java.awt.*;
import java.io.File;
import java.util.Random;

import static Utils.AllConstNature.*;
import static Tank.CreateTank.*;
import static Tank.EnemyTank.*;

/**********************************此方法原本是为了发射彩色炮弹的，但添加图片后不适用自身的坦克***********************8***/

public class TankOfUtils {
    public static final Random random = new Random();

    /***
     * 得到坦克的颜色
     * @return Color:坦克的颜色
     */

    public static Color randomColor() {
        Random random = new Random();
        int colorR = random.nextInt(255);
        int colorG = random.nextInt(255);
        int colorB = random.nextInt(255);
        return new Color(colorR, colorG, colorB);
    }

    /***
     * 获得敌方坦克的初始生成点
     * @return int  敌人的位置
     */
    public static int getRandomPositionOfEnemy() {
        return random.nextInt(ENEMY_BORN_IN_RIGHT + 1);
    }

    /***
     * 敌人随机获取一个状态(运动 或 静止）
     * @return 敌人的状态
     */
    public static int getRandomStateOfEnemy() {
        return random.nextInt(STATE_MOVE + 1);
    }

    /***
     *用于随机敌人是否进行射击
     * @return 敌人射击的几率
     */
    public static int getRandomChanceToShot() {
        return random.nextInt(100);
    }

    /***
     * 随机获取运动方向
     * @return 随机的方向
     */
    public static int getRandomDirection() {
        return random.nextInt(DIR_LEFT + 1);
    }

    /***
     * 获取坦克样式
     * @return 随机的样式
     */
    public static Image[] getEnemyTankStyle() {
        int style = random.nextInt(3);
        switch (style) {
            case 0:
                return ENEMY_TANK_IMAGE_ONE;

            case 1:
                return ENEMY_TANK_IMAGE_TWO;
            default:
                return ENEMY_TANK_IMAGE_THREE;
        }
    }

    /***
     *
     * @param bullet 炮弹
     * @param rTank 目标半径坐标
     * @param xTank 目标x坐标
     * @param yTank 目标y坐标
     * @return 是否击中
     */
    public static boolean isShotTheEnemy(CreateBullet bullet, int rTank, int xTank, int yTank) {

        int rBullet = bullet.getRadius();


        int xBullet = bullet.getPosition_X();
        int yBullet = bullet.getPosition_Y();
        double distance = Math.sqrt((xTank - xBullet) * (xTank - xBullet) + (yTank - yBullet) * (yTank - yBullet));
        return rTank + rBullet > distance; //如果距离小于半径之和，则返回击中
    }

    /***
     * 获得图片资源
     * @return 获得的图片
     * @param  urlPath 图片资源路径
     */
    public static Image getImageURL(String urlPath) {
        //createImage是一个延迟方法：即不能立即获取到资源 ， 须提前获取   和 getImage不同
        return Toolkit.getDefaultToolkit().getImage(urlPath);
    }

    /***
     * 随机返回伤害值
     * @return 返回的炮弹伤害值
     */
    public static int getAttack() {
        return ATK_MIN + random.nextInt(ATK_MAX - ATK_MIN + 1);
    }

    /***
     * 随机得到坦克昵称
     * @return 坦克的昵称
     */
    public static String getTankNickname() {
        return TANK_NICKNAME[random.nextInt(TANK_NICKNAME.length)];
    }


    /***
     * 判断坦克和地形是否碰撞
     *  坦克的x ，y 是中心处坐标；  砖的x，y是左上角处坐标
     * @param tankX 坦克x
     * @param tankY 坦克y
     * @param tankR 坦克r
     * @param brickX 砖x
     * @param brickY 砖y
     * @param brickW 砖宽
     * @param brickH 砖高
     * @return 检测结果
     */
    public static boolean isCollide(int tankX, int tankY, int tankR, int brickX, int brickY, int brickW, int brickH) {
        int Lx = Math.abs(tankX - (brickX + brickW / 2)); //两中心在x方向的距离
        int Ly = Math.abs(tankY - (brickY + brickH / 2)); //两中心在y方向的距离
        return Lx <= (2 * tankR + brickW) / 2 && Ly <= (2 * tankR + brickH) / 2;
    }


    /***
     * 循环播放音乐
     * @param musicLocation 音乐路径
     * @return  得到的音频资源
     */
    public static Clip playMusic(String musicLocation) {
        Clip clip = null;
        try {
            File musicPath = new File(musicLocation);

            if (musicPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                 clip = AudioSystem.getClip();
                clip.open(audioInput);
                //clip.start();
                //clip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                System.out.println("NO such audio exits!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return clip;
    }


}
