package Utils;

import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class AllConstNature {
    //游戏界面相关信息
    public static final  String GAME_TITLE = "保卫杰哥";
    public static final int FRAME_WIDTH = 900; //窗口宽900像素
    public static final int FRAME_HEIGHT = 900; //窗口高900像素

/********************************************基本界面************************************************************/
    public static final Font FONT_BUTTON = new Font("宋体", Font.BOLD,15);  //设置Button的字体样式
    public static final Font FONT_TANK_NAME = new Font("宋体", Font.PLAIN,12);  //设置Button的字体样式
    public static final Font FONT_TANK_EXIT_MESSAGE = new Font("宋体", Font.PLAIN,50);  //设置Button的字体样式

    public static final String EXIT_MESSAGE = "ESC键回到主界面";
    public static final String WINNING_MESSAGE = "获胜";
    public static final int TOTAL_SCORES = 12;  //总分

    //自己添加坦克的昵称对象池
    public  static final String[] TANK_NICKNAME   = {
            "盖伦","亚索","诺手","杰哥","阿伟","彬彬","VAN","lbw","狂风之力"
    };


    public  static final String[] MAIN_BORD   = {
            "开始游戏",
            "历史最佳",
            "游戏帮助",
            "关于游戏",
            "退出游戏"
    };
    //主菜单的五个按钮
    public static final JButton BUTTON_START = new JButton();
    public static final JButton BUTTON_SCORE = new JButton();
    public static final JButton BUTTON_HELP = new JButton();
    public static final JButton BUTTON_ABOUT = new JButton();
    public static final JButton BUTTON_OVER = new JButton();
    //并存入集合
    public static final JButton[] J_BUTTONS = {BUTTON_START, BUTTON_SCORE, BUTTON_HELP, BUTTON_ABOUT,BUTTON_OVER};

    //帮助菜单的返回按钮
    public static final JButton BUTTON_BACK = new JButton("返回");
     // 帮助的提示信息
    public static final JLabel[] HELP_LABELS = {new JLabel(),new JLabel(),new JLabel(),new JLabel()};
    public static final JLabel[] ABOUT_LABELS = {new JLabel(),new JLabel()};
    public static final JLabel[] SCORE_PANEL = {new JLabel()};

    //游戏音频
    public static final Clip MUSIC_THEME = TankOfUtils.playMusic("src\\BGM\\Theme.wav");
 /******************************************游戏的容器****************************************************************/

//游戏的底层容器
 public static final JFrame J_FRAME = new JFrame(GAME_TITLE);

    /*********************************************游戏状态相关信息*************************************************************/

    public static final int STATE_START = 0;
    public static final int STATE_SCORE = 1;
    public static final int STATE_HELP = 2;
    public static final int STATE_ABOUT   = 3;
    public static final int STATE_MENU  = 4; //主菜单状态
    public static final int STATE_FAIL = 5;
    public static final int STATE_WIN  = 6;


    public static final long REPAINT_INTERVAL = 17; //重绘间隔(当前60帧）

/*****************************************************敌方坦克信息******************************************************/
    //敌方坦克的出生点为左上角(0)　或　右上角(1)
    public static final int ENEMY_BORN_IN_LEFT = 0;
    public static final int ENEMY_BORN_IN_RIGHT = 1;

    public static final int ENEMY_MAX_NUMBER = 10; //地图上最多10辆敌方坦克
    public static final int ENEMY_BORN_INTERVAL = 5000; //五秒产生一辆
    public static final long SHOT_INTERVAL = 300; //0.3s射击间隔


    //敌方坦克
    public static final  long  ABLE_TO_MOVE_INTERVAL = 3000; // 三秒获得一个状态
    public static final long  ABLE_TO_SHOT_INTERVAL = 1000; //可以射击的时间间隔
    public static final  int CHANCE_OF_ABLE_TO_SHOT = 5; // 从0~100点中选
/*******************************************************线程池*************************************************************/
    //用于多线程的任务线程池

    public static final  int THREAD_POOL_MAX_NUMBER = 10;  //最多有20个线程
    public static  ExecutorService THREAD_POOL; //线程池

    static {
         THREAD_POOL = Executors.newFixedThreadPool(2); //控制线程个数
    }


/*******************************************************地图界面*********************************************************/

    public static final int BRICK_MAX = 100; //最大砖块数

    public static final int GAME_MAP_COLUMNS = 32; //X方向最大砖块数
    public static final int GAME_MAP_ROWS = 34; //Y方向最大砖块数
    public static final String SPLIT_SIGNAL = ","; //配置信息分隔符
    public static final int PROPS_MAX = 864; //最多放864个道具
    public static final int BOUND_OF_PROP_X = 57; //道具左上角边界x坐标为50
    public static final int BOUND_OF_PROP_Y = 50; //道具左上角边界y坐标为50











}
