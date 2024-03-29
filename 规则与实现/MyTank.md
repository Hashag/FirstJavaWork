#### TankGame

### 1. 游戏规则：

操控己方坦克对敌方坦克进行打击，在消灭指定敌人数目且基地没有被摧毁时，即视为获胜

### 2.实现方式：

开发过程中用到的技术：

① .Java Swing插件： JFrame作为底层容器，JPanel作为画板 ，JButton，JLabel

② .Java多线程：重绘     敌人坦克的生成 （使用工厂方法获取线程池）

③.Java Graphics绘图： 绘制坦克，炮弹，砖块等

④.Java集合：ArrayList存储敌方坦克

⑤.Java IO流：BufferedReader从文件中读入地图数据，BufferedWriter写入完成任务最短时间, AudioInputStream获取音频

具体实现方式：

1. 主界面：

   先创建一个JFrame的静态常量对象，再创建一个类GPanel继承JPanel，将GPanel镶嵌在JFrame上，制作自定义的布局器，将该GPanel的布局器设置为自定义的，并添加一系列按钮

   <img src="G:\Typora\Typora文件\Work\Menu.png" style="zoom:50%;" />



2. 添加各JButton的响应事件，并优先实现开始游戏对应的事件

   **GPanel面板的定时重绘，paint方法的覆盖重写**

   1. 创建抽象Tank父类： CreateTank   （先将坦克等效为一个圆，最后添加图片） 

   子类坦克分为：MyTank：己方坦克； EnenmyTank：敌方坦克

   定义坦克的各属性： 位置，血条，攻击力，昵称等

   设置CreateTank的方法：移动，射击，受伤等方法

   2. 进一步创建坦克的属性类：

   炮弹类：属性：位置，伤害等

   爆炸效果类：特效图片，位置等

   3. 给GPanel添加键盘监听，实现操控己方坦克的移动；敌方坦克获取随机状态

   4. 炮弹的发射机制，坦克的掉血机制，完善坦克与炮弹的碰撞，坦克与窗口边界的碰撞等，给坦克添加图片

   5. 创建地图类GMap：含有砖块父类Brick，基地类MyHome    从文本中读取地图配置

      砖块类： 设置 位置，掉血机制，与坦克之间的碰撞检测，与炮弹之间的碰撞检测，添加图片 (含有子类：普通砖块类  和  铁砖块)

      基地类：继承于砖块类，设置位置，掉血机制，

   6. 给砖块添加图片资源，给游戏添加BGM，设置己方坦克死亡或基地被毁时的失败逻辑； 设置目标分数，设置达到获胜条件时的获胜逻辑；添加再次开始游戏的重置逻辑；记录最佳战绩

3. 添加之后三个JButton的响应事件：

   使GPanel不可见，在JFrame上添加JLabel标签，显示对应的内容

4.退出直接结束进程
