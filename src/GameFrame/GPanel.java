package GameFrame;

import Map.Brick;
import Map.GMap;
import Map.IronBrick;
import Map.NormalBrick;
import Tank.*;
import Utils.Pool;
import Utils.TankOfUtils;

import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import  static Utils.AllConstNature.*;
import  static Tank.CreateTank.*;

public class GPanel extends JPanel {

    //游戏结束的背景图片
    private static final Image WIN_BACKGROUND = TankOfUtils.getImageURL("src\\GameImage\\Background\\OhtoAi.png");
    private static final Image OVER_BACKGROUND = TankOfUtils.getImageURL("src\\GameImage\\Background\\gameover.png");
    private static final Image GAMING = TankOfUtils.getImageURL("src\\GameImage\\Background\\bk.png");



    //游戏地图  TODO 看是否需要做成集合
    GMap gMap = null;

    //设置JPanel画板的高度和宽度（给坦克确定位置使用）
    public static int jPanelHeight = 0;
    public static int jPanelWidth = 0;
    //敌方坦克的容器
    private static final List<CreateTank> LIST_OF_ENEMY = new ArrayList<>(ENEMY_MAX_NUMBER);
    //坦克属性
    private MyTank myTank = null;

    //TODO 先尝试定义一个flag来让敌方坦克后产生
    private int flag = 0;
    //添加一个分数是否绘制
    private boolean isDrawScores = false;
    //定义一个计时器统计通关时间
    private long winTime ;
    //尝试定义一个计时器，替代生产坦克线程的sleep方法
    private long timeCounter = System.currentTimeMillis();
    //定义一个射击间隔计时器
    private long shotIntervalCounter = System.currentTimeMillis();

//TODO 是否添加游戏状态(这里将游戏状态设为其它值，不等于默认值)
    //添加游戏的状态(用static的话，后面可能无法序列化）
    public static int gameState = STATE_MENU; //默认是主菜单状态

    //创建一个窗口
    public GPanel(){
        J_FRAME.add(this); //添加绘板
        initialFrame(); //初始化界面
        requestFocusInWindow(true); //让键盘能适时获得焦点
        repaintTheGame(); //定时重绘

    }

    /***
     * 对窗口进行初始化
     */
    private void initialFrame() {
        //TODO 设置图片
        setFocusable(true); //将键盘读入焦点给JPanel面板

        addEventForAllButton(); //添加按键监听
        J_FRAME.setSize(FRAME_WIDTH, FRAME_HEIGHT); //窗口大小
        J_FRAME.setLocationRelativeTo(null); //居中显示
        J_FRAME.setResizable(false); //大小不可改变
        J_FRAME.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //可关闭
        J_FRAME.setVisible(true); //可见性
    }

    /***
     * 重写自父类的paint方法，给面板自己调用
     * @param g 系统的画笔
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g); //父类方法必须放在前面，以生成画笔Graphics
        // 捕获异常 ConcurrentModificationException (产生原因： 使用了迭代器遍历集合 增强for）
            try { // 捕获异常，避免出现某些不必要的bug使程序崩溃
                switch(gameState) {
                case STATE_MENU:
                    //TODO 设置图片(是否需要优化）
                    this.setLayout(new GameLayout.GLayout());//添加自己的游戏布局器
                    //添加背景颜色（也可以换成图片）
                    //添加button
                    for (JButton jButton : J_BUTTONS) {
                        this.add(jButton);
                    }

                    break;
                case STATE_START:


                    gameStartDrawTotal(g);

                    break;
                case STATE_SCORE:
                    break;
                case STATE_HELP:


                    break;
                case STATE_ABOUT:
                    break;
                case STATE_FAIL:
                    gameOverDrawTotal(g);

                    break;
                case STATE_WIN:
                     gameWinDrawTotal(g);
                    break;

                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("敌人集合正在被同时访问！");
            }


    }
//TODO 日后背景为换图片
    /***
     * 游戏开始时绘制
     * @param g
     */
    private void gameStartDrawTotal(Graphics g) {
        g.drawImage(GAMING,0,0,null);



        drawEnemyTank(g); //画出敌方坦克
        myTank.drawTank(g); //画出己方坦克

        gMap.drawMap(g); //画出地图道具



        shotEnemy(g); //射击敌人的逻辑
        if (isDrawScores) {
            g.setFont(FONT_BUTTON);
            g.setColor(Color.BLUE);
            g.drawString("目标分数： " + TOTAL_SCORES,100,100);
            g.drawString("当前得分：" + myTank.getScores(),100,200);
            g.drawString("坦克生命值：" + myTank.getHp(),100,300);
            g.drawString("基地生命值：" + gMap.getMyHome().getBrickHP(),100,400);

        }
    }

    /***
     * 绘制游戏结束背景
     * @param g
     */
    private void gameOverDrawTotal(Graphics g) {

        int width = OVER_BACKGROUND.getWidth(null);
        int height = OVER_BACKGROUND.getHeight(null);
        g.drawImage(OVER_BACKGROUND,jPanelWidth-width>>1,(jPanelHeight-height>>1) - 50,null);
        g.setColor(TankOfUtils.randomColor());
        g.setFont(FONT_TANK_EXIT_MESSAGE);

        g.drawString(EXIT_MESSAGE,0,jPanelHeight-50);

    }

    /***
     * 游戏获胜逻辑
     * @param g
     */
    private void gameWinDrawTotal(Graphics g) {
        int width = WIN_BACKGROUND.getWidth(null);
        int height = WIN_BACKGROUND.getHeight(null);
        g.drawImage(WIN_BACKGROUND,jPanelWidth -width>>1,jPanelHeight-height>>1,null);
        g.setColor(TankOfUtils.randomColor());
        g.setFont(FONT_TANK_EXIT_MESSAGE);
        g.drawString(WINNING_MESSAGE,0,jPanelHeight-50);

    }

    /***
     * 为所有button注册监听
     */
    private void addEventForAllButton() {
        //为BUTTON_START添加事件
        BUTTON_START.addActionListener(e-> eventOfStartGame());
        //为BUTTON_HELP添加事件
        BUTTON_HELP.addActionListener(e->eventOfHelpGame());
        //为BUTTON_ABOUT添加事件
        BUTTON_ABOUT.addActionListener(e->eventOfAboutGame());
        //为推出游戏添加事件
        BUTTON_OVER.addActionListener(e->eventOfExit());
        //为BUTTOn_SCORE添加事件
        BUTTON_SCORE.addActionListener(e->eventOfScore());
        //TODO  其他按键未添加监听
    }

    /***
     * BUTTON_OVER中的事件
     */
    private void eventOfExit() {
        System.exit(0);
    }

    /***
     * BUTTON_SCORE中的事件
     */
    private void eventOfScore() {
        gameState = STATE_SCORE;
        this.setVisible(false);
        Container contentPane = J_FRAME.getContentPane();
        contentPane.setLayout(new GameLayout.Score());
        contentPane.add(BUTTON_BACK);
        try(BufferedReader br = new BufferedReader(new FileReader("src\\Entrance\\Of\\Best"))){
            String s = br.readLine();
            if (s == null) {
                SCORE_PANEL[0].setText("暂无数据");
            } else {
                int i = Integer.parseInt(s);
                int minute = i / 60000;
                int mills = (i - minute * 6000) /1000;
                SCORE_PANEL[0].setText("最少用时： " + minute + " 分" + mills+ " 秒");
            }
        } catch(Exception e) {
            System.out.println("分数读取失败！");
        }
        for (JLabel jLabel : SCORE_PANEL) {
            contentPane.add(jLabel);
        }
        BUTTON_BACK.addActionListener(e->{
            for (JLabel jLabel : SCORE_PANEL) {
                contentPane.remove(jLabel);
            }
            this.setVisible(true);
            gameState = STATE_MENU;
        });

    }

    /***
     * BUTTON_HELP中的事件
     */
    private void eventOfHelpGame() {
        gameState = STATE_HELP;
        //在JFrame上进行显示
        this.setVisible(false);
        Container contentPane = J_FRAME.getContentPane();
        contentPane.setLayout(new GameLayout.HelpLayout());
        contentPane.add(BUTTON_BACK);

        for (JLabel helpLabel : HELP_LABELS) {
            contentPane.add(helpLabel);
        }

        BUTTON_BACK.addActionListener(e->{
            for (JLabel helpLabel : HELP_LABELS) {
                contentPane.remove(helpLabel);
            }
            this.setVisible(true);
            gameState = STATE_MENU;
        });
    }

    /***
     * BUTTON_ABOUT中的事件
     */
    private void eventOfAboutGame() {
        gameState = STATE_ABOUT;
        //在JFrame上进行显示
        this.setVisible(false);
        Container contentPane = J_FRAME.getContentPane();
        contentPane.setLayout(new GameLayout.AboutLayout());
        contentPane.add(BUTTON_BACK);
        for (JLabel aboutLabel : ABOUT_LABELS) {
            contentPane.add(aboutLabel);
        }
        contentPane.add(BUTTON_BACK);
        BUTTON_BACK.addActionListener(e->{
            for (JLabel aboutLabel : ABOUT_LABELS) {
                contentPane.remove(aboutLabel);
            }
            this.setVisible(true);
            gameState = STATE_MENU;
        });

    }


    /***
     * BUTTON_START中的事件
     */
    private void eventOfStartGame() {
        winTime = System.currentTimeMillis(); //开始计时
        //在开始游戏时才对画板高宽进行赋值
        jPanelHeight = this.getHeight();
        jPanelWidth = this.getWidth();

        gMap = new GMap(); //创建游戏地图
        //切换游戏状态
        gameState = STATE_START;
        //TODO 看之后是否需要优化
        MUSIC_THEME.loop(Clip.LOOP_CONTINUOUSLY);


        myTank = new MyTank(200,800,DIR_UP); //创建坦克对象

        addKeyListener(); //开始游戏后添加键盘监听

        for (JButton jButton : J_BUTTONS) { //移除面板的所有按钮
            this.remove(jButton);
        }

        //TODO 开始游戏的监听事件


    }

    /***
     * 为键盘注册监听并添加事件
     */
    private void addKeyListener() {
        this.addKeyListener(new KeyAdapter() {
            //按下按键的事件
            @Override
            public void keyPressed(KeyEvent e) {
                keyEvent(e); //事件
            }
            //松开按键的事件
            @Override
            public void keyReleased(KeyEvent e) { //TODO 后期看需不需要指定松开哪些按键
                int keyCode = e.getKeyCode();
                switch (keyCode) {
                    case KeyEvent.VK_W:
                    case KeyEvent.VK_UP:

                    case KeyEvent.VK_S:
                    case KeyEvent.VK_DOWN:

                    case KeyEvent.VK_D:
                    case KeyEvent.VK_RIGHT:

                    case KeyEvent.VK_A:
                    case KeyEvent.VK_LEFT:
                        myTank.setState(STATE_STAND); //只有在松开移动键时才会进入静止状态 （否则停止射击时会停下）
                        break;
                    case KeyEvent.VK_R:
                        if(gameState == STATE_START) {
                            isDrawScores = false;
                        }

                        break;
                }
            }
        });
    }

    /***
     * 键盘的事件(改变朝向 和 坦克的运动状态）
     * @param e 键值
     */
    private void keyEvent(KeyEvent e) {
        int keyCode = e.getKeyCode(); //获取键对应的值
        switch (keyCode) {
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                myTank.setDirection(DIR_UP);
                myTank.setState(STATE_MOVE); //让坦克进入MOVE状态
                break;
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                myTank.setDirection(DIR_DOWN);
                myTank.setState(STATE_MOVE);//让坦克进入MOVE状态
                break;
            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                myTank.setDirection(DIR_RIGHT);
                myTank.setState(STATE_MOVE); //让坦克进入MOVE状态
                break;
            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                myTank.setDirection(DIR_LEFT);
                myTank.setState(STATE_MOVE); //让坦克进入MOVE状态
                break;
            case KeyEvent.VK_SPACE:
            case KeyEvent.VK_F:
                if(System.currentTimeMillis() - shotIntervalCounter > SHOT_INTERVAL) {
                    myTank.generateBullet(); //控制弹夹中炮弹的有无
                    Clip clip = TankOfUtils.playMusic("src\\BGM\\fire.wav");
                    clip.start();
                    shotIntervalCounter = System.currentTimeMillis(); //重置计时器
                }
                break;
            case KeyEvent.VK_R:
                if(gameState == STATE_START) {
                    isDrawScores = true;
                }
                break;
            case KeyEvent.VK_ESCAPE:  //返回主界面
                if(myTank.getState() == STATE_DEAD || myTank.getScores() == TOTAL_SCORES || gMap.getMyHome().isDead()) { //只有在自己的坦克阵亡时才生效
                    gameState = STATE_MENU;
                    resetTheGame(); //重置游戏
                }
                break;
        }
    }



    /***
     * 重绘当前面板
     */
    private void repaintTheGame() {
        THREAD_POOL.submit(()->{
            while(true) {
                repaint();
                try {
                    Thread.sleep(REPAINT_INTERVAL);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //TODO 感觉后期这里会出问题（绘制坦克和之后的 添加 / 移除 坦克不再同一个线程上）
    /***
     * 调用画笔绘出坦克
     * @param g 系统的画笔
     */
    private void drawEnemyTank(Graphics g) {

        getEnemyTank();  //产生敌人
        //遍历当前坦克集合，画出所有坦克
        for (int i = LIST_OF_ENEMY.size() - 1; i >= 0; i--) {
            LIST_OF_ENEMY.get(i).drawTank(g);
        }
    }

    /***
     * 生产敌方坦克的线程
     *
     */
    private void getEnemyTank() {
        THREAD_POOL.submit(()->{

            //TODO 让敌人等待一秒再生成
            if(flag == 0) {
                flag =1;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            enemyIsDead(); //判断敌人是否阵亡
                if(System.currentTimeMillis() - timeCounter >= ENEMY_BORN_INTERVAL) {
                    if(LIST_OF_ENEMY.size() < ENEMY_MAX_NUMBER) {
                        EnemyTank enemyTank = Pool.EnemyTankPool.getEnemyFromPool(); //从池中取出一个初始化好的坦克
                        addEnemy(enemyTank);
                    }
                    timeCounter = System.currentTimeMillis(); //重置时间
                }
        });
    }

    /***
     * 在生产坦克的线程中判断敌人是否阵亡,并为自己加分
     */
    private void enemyIsDead() {

        for (int i = 0; i < LIST_OF_ENEMY.size(); i++) {
            CreateTank enemyTank = LIST_OF_ENEMY.get(i);
            if(enemyTank.getState() == STATE_DEAD) {
                myTank.setScores(myTank.getScores() +1);
                if(myTank.getScores() == MyTank.AIM_SCORES) {
                    gameState =STATE_WIN; //达到目标分数切换游戏状态
                    winTime = System.currentTimeMillis() - winTime;//得到时间
                    try(BufferedReader br = new BufferedReader(new FileReader("src\\Entrance\\Of\\Best"));
                        BufferedWriter bw = new BufferedWriter(new FileWriter("src\\Entrance\\Of\\Best"))) {
                        String s = br.readLine();
                        if(s != null) {
                            long beforeTime = Integer.parseInt(s);
                            long minTime = winTime < beforeTime? winTime:beforeTime;
                            bw.write(minTime + "");
                        } else {
                            bw.write(winTime + "");
                        }

                    } catch (Exception e) {
                        System.out.println("分数存取失败！");
                    }
                    Clip clip = TankOfUtils.playMusic("src\\BGM\\win.wav");
                    clip.start();
                    MUSIC_THEME.stop();
                }
                removeEnemy((EnemyTank) enemyTank);  //移除阵亡的敌人
                Pool.EnemyTankPool.giveBackTheEnemy((EnemyTank) enemyTank); //返还回池中
            }
        }
    }

    //TODO 添加击中特效 创建特效对象池
    /***
     * 炮弹击中敌人的处理逻辑 和己方基地
     * @param graphics :系统的画笔
     */
    private void shotEnemy(Graphics graphics) {
        //遍历敌方坦克
        //TODO ------- 实验
        for (int i = LIST_OF_ENEMY.size() - 1; i >= 0; i--) {
            CreateTank enemy = LIST_OF_ENEMY.get(i);
            //遍历我的弹夹,检验我的炮弹是否打中了敌方坦克
                for (int i1 = myTank.bulletList.size() - 1; i1 >= 0; i1--) {
                    CreateBullet bullet = myTank.bulletList.get(i1);
                    if(TankOfUtils.isShotTheEnemy(bullet, enemy.getRadius(),enemy.getPosition_X(),enemy.getPosition_Y())) {
                        bullet.setVisible(false);  //击中敌人后便消失
                        //TODO  等会改进
                        enemy.addExplosion(bullet);//添加爆炸到集合中
                    }
                    bulletIsShotTheBrick(bullet); //我的炮弹是否击中墙体的判断
                    isBulletShotHome(bullet);
                }

            //遍历敌人的弹夹，检验我的坦克是否被击中
                for (int i1 = enemy.bulletList.size() - 1; i1 >= 0; i1--) {
                    CreateBullet bullet = enemy.bulletList.get(i1);
                    if(TankOfUtils.isShotTheEnemy(bullet,myTank.getRadius(),myTank.getPosition_X(),myTank.getPosition_Y())) {
                        bullet.setVisible(false);
                        //TODO  等会改进
                        myTank.addExplosion(bullet); //添加爆炸到集合中
                    }
                    bulletIsShotTheBrick(bullet); //敌人的炮弹是否击中墙体的判断
                    isBulletShotHome(bullet);
                }
        }
    }


    /***
     * 炮弹是否击中基地
     * @param bullet
     */
    private void isBulletShotHome(CreateBullet bullet) {

        int x = gMap.getMyHome().getPositionX();
        int y = gMap.getMyHome().getPositionY();
        int width = gMap.getMyHome().getWidth();
        int height = gMap.getMyHome().getHeight();
        if(TankOfUtils.isShotTheEnemy(bullet, width /2, x + width/2,y+ height/2)) {
            gMap.getMyHome().addExplosion(bullet);
            bullet.setVisible(false);
        }
    }

    /***
     * 子弹击中墙体
     */
    private void bulletIsShotTheBrick(CreateBullet bullet) {
        for (int i = 0; i < GAME_MAP_ROWS; i++) {
            for (int j = 0; j < GAME_MAP_COLUMNS; j++) {
                Object o = GMap.PROPS[i][j];
                if(!Objects.equals(o,null)) {
                    if(o instanceof IronBrick) { //铁砖
                        IronBrick iron = (IronBrick)o;
                        boolean b = TankOfUtils.isShotTheEnemy(bullet, Brick.height / 2, iron.getPositionX() + Brick.width / 2, iron.getPositionY() + Brick.height / 2);
                        if(b) {
                            iron.addExplosion(bullet,i,j);
                            bullet.setVisible(false);
                        }
                    } else if(o instanceof NormalBrick) { //普通砖
                        NormalBrick normal = (NormalBrick)o;
                        boolean b = TankOfUtils.isShotTheEnemy(bullet, Brick.height / 2, normal.getPositionX() + Brick.width / 2, normal.getPositionY() + Brick.height / 2);
                        if(b) {
                            normal.addExplosion(bullet,i,j);
                            bullet.setVisible(false);
                        }
                    } //TODO 待添加其他道具
                }
            }

        }

    }

    //TODO 具体怎么去重置
    /***
     * 重置游戏(此时游戏已结束，不需要考虑多线程同步修改问题）
     */
    private void resetTheGame() {
        flag = 0; //重置延迟标记
        for (CreateTank enemy : LIST_OF_ENEMY) {
            //退还炮弹
            for (CreateBullet bullet : enemy.bulletList) {
                Pool.BulletPool.giveBackTheBullet(bullet);
            }
            //退还爆炸
            for (ExplosionSpecialEffects explosion : enemy.explosionList) {
                Pool.ExplosionPool.giveBackTheExplosion(explosion);
            }
            //退还敌人
            Pool.EnemyTankPool.giveBackTheEnemy((EnemyTank) enemy);
        }
        LIST_OF_ENEMY.clear(); //清空敌人
        //清空砖块
        for (int i = 0; i < GAME_MAP_ROWS; i++) {
            for (int j = 0; j <GAME_MAP_COLUMNS; j++) {
                Object o = GMap.PROPS[i][j];
                if(!Objects.equals(o,null)) {
                    if(o instanceof NormalBrick) {
                        NormalBrick no = (NormalBrick) o;
                        Pool.BrickPool.giveBackTheNormalBrick(no);
                    } else if(o instanceof IronBrick) {
                        IronBrick ir = (IronBrick) o;
                        Pool.BrickPool.giveBackTheIronBrick(ir);
                    }
                    Brick brick = (Brick) o;
                    for (ExplosionSpecialEffects ex : ((Brick) o).getExplosionBrick()) {
                        Pool.ExplosionPool.giveBackTheExplosion(ex);
                    }
                }
            }
        }
        GMap.readMapProp(); //重新读入地图信息
        //退换自己坦克的属性
        for (CreateBullet b : myTank.bulletList) {
            Pool.BulletPool.giveBackTheBullet(b);
        }
        for (ExplosionSpecialEffects ex : myTank.explosionList) {
            Pool.ExplosionPool.giveBackTheExplosion(ex);
        }
    }

    /**
     * 同步添加敌人
     * @param enemy
     */
    public static synchronized void addEnemy(EnemyTank enemy) {
        LIST_OF_ENEMY.add(enemy);
    }

    public static synchronized  void removeEnemy(EnemyTank enemy) {
        Pool.EnemyTankPool.giveBackTheEnemy(enemy);
        LIST_OF_ENEMY.remove(enemy);
    }



    }
