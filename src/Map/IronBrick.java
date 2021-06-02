package Map;

import Utils.Pool;
import Utils.TankOfUtils;

import java.awt.*;

public class IronBrick extends Brick{





    /***
     *
     * @param positionX 砖块左上角x坐标
     * @param positionY 砖块左上角y坐标
     */
    public IronBrick(int positionX,int positionY) {

        setImage(BRICK_IMAGE[1]); //铁砖
        setBrickHP(IRON_BRICK_HP); //生命值

        setIron(true); //属性值
        setVisible(true);
    }

    /***
     * 重置砖的属性
     */
    public void resetTheIronBrick() {
        setBrickHP(IRON_BRICK_HP);
        setIron(true);
        setVisible(true);
    }

    @Override
    public void drawBrick(Graphics g) {
        if(isVisible()) {
            g.drawImage(BRICK_IMAGE[1],getPositionX(),getPositionY(),null);
            drawExplosion(g);
        } else {
            Pool.BrickPool.giveBackTheIronBrick(this); //不可见就还回去
        }

    }
}
