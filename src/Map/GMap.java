package Map;

import Utils.Pool;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Objects;

import  static Utils.AllConstNature.*;

public class GMap {
    public static final int NOR_BRICK = 1; //普通砖为1
    public static final int IRO_BRICK = 2; //铁砖为2

    //TODO 修正参数 暂且 为2 * 32的放置
    public static final Object[][] PROPS = new Object[GAME_MAP_ROWS][GAME_MAP_COLUMNS];

    //读入地图信息
    static{
        readMapProp(); //读入道具信息
    }
    //基地
    private final MyHome myHome = new MyHome();



    /***
     * 读入道具
     */
    public static void readMapProp() {

        try(FileReader fr = new FileReader("src\\Map\\gameMap1\\GameMap02");
            BufferedReader br = new BufferedReader(fr)) {

            String[] split;
            for (int row = 0; row < GAME_MAP_ROWS; row++) {
                split = br.readLine().split(SPLIT_SIGNAL); //以 ，切割
                for (int column = 0; column < GAME_MAP_COLUMNS; column++) {
                    PROPS[row][column] = judgeTheProp(Integer.parseInt(split[column]),row,column); //将地图道具存入数组中
                }
            }
        } catch(Exception e) {
            System.out.println("地图信息读取失败！");
        }
    }

    /***
     * 判断地图道具
     * @param flag 种类
     * @param row 所在行
     * @param column 所在列
     * @return 返回的道具
     */
    public static Object judgeTheProp(int flag,int row,int column) {

        switch(flag) {
            case NOR_BRICK: //返回普通砖
                NormalBrick tHeNormalBrick = Pool.BrickPool.getTHeNormalBrick();
                int width = Brick.width;
                int height = Brick.height;
                tHeNormalBrick.setPositionX(BOUND_OF_PROP_X + width*column);
                tHeNormalBrick.setPositionY(BOUND_OF_PROP_Y + height*row);
                return tHeNormalBrick;
            case IRO_BRICK: //返回铁砖
                IronBrick tHeIronBrick = Pool.BrickPool.getTHeIronBrick();
                int width1 = Brick.width;
                int height1 = Brick.height;
                tHeIronBrick.setPositionX(BOUND_OF_PROP_X + width1*column);
                tHeIronBrick.setPositionY(BOUND_OF_PROP_Y + height1*row);
                return tHeIronBrick;
            default:
                return null;
        }
    }

    /***
     * 为地图添加道具元素
     */
    private void addPropElements() {

    }
    /***
     * 绘制地图
     * @param graphics 系统的画笔
     */
    //TODO i的值待修改
    public void drawMap(Graphics graphics) {
        for (int i = 0; i < GAME_MAP_ROWS; i++) {
            for (int j = 0; j < GAME_MAP_COLUMNS; j++) {
                Object o = PROPS[i][j];
                if(!Objects.equals(o,null)) {
                    if(o instanceof IronBrick) { //铁砖
                        IronBrick iron = (IronBrick)o;
                        iron.drawBrick(graphics);
                    } else if(o instanceof NormalBrick) { //普通砖
                        NormalBrick normal = (NormalBrick)o;
                        normal.drawBrick(graphics);
                    } //TODO 待添加其他道具
                }
            }
        }
        myHome.drawHome(graphics); //画出基地

    }

    public MyHome getMyHome() {
        return myHome;
    }
}
