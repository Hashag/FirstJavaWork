package Map;

import Utils.Pool;
import Utils.TankOfUtils;

import java.awt.*;

public class NormalBrick extends Brick{



    /***
     *
     * @param positionX 砖块左上角x坐标
     * @param positionY 砖块左上角y坐标
     */
    public NormalBrick(int positionX,int positionY) {


        setImage(BRICK_IMAGE[0]);//土砖
        setBrickHP(NORMAL_BRICK_HP); //生命值
        setIron(false); //属性值
        setVisible(true);
    }

    /***
     * 重置砖的属性
     */
    public void resetTheNormalBrick() {
        setBrickHP(NORMAL_BRICK_HP);
        setIron(false);
        setVisible(true);
    }

    @Override
    public void drawBrick(Graphics g) {
        if(isVisible()) {
            g.drawImage(BRICK_IMAGE[0],getPositionX(),getPositionY(),null);
            drawExplosion(g);
        } else  {
            Pool.BrickPool.giveBackTheNormalBrick(this); //不可见就还回去
        }
    }
}
