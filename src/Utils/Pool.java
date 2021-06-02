package Utils;

import Map.IronBrick;
import Map.NormalBrick;
import Tank.CreateBullet;
import Tank.EnemyTank;
import Tank.ExplosionSpecialEffects;

import java.util.ArrayList;
import java.util.List;
import static Utils.AllConstNature.*;

/******************************************************两个常量池逻辑一样*********************************************/
public class Pool {

    public static final int ALL_POOL_MAX_SIZE = 300;

//炮弹池内部类
   public static class BulletPool {
        public static final int POOL_MAX_SIZE = ALL_POOL_MAX_SIZE; // 最多添加300发炮弹
        public static final List<CreateBullet> BULLET_POOL = new ArrayList<>(POOL_MAX_SIZE); //创建集合设定大小，减少不必要的内存浪费

        //初始化常量池
        static {
            for (int i = 0; i < POOL_MAX_SIZE; i++) {
                BULLET_POOL.add(new CreateBullet(0, 0, 0, null, 0)); //炮弹均为默认值 故在使用前要修改参数
            }
        }

        /***
         * 获取炮弹池中炮弹
         */
        public static CreateBullet getBulletFromPool() {
            int presentSize = BULLET_POOL.size();
            CreateBullet bullet;
            if(presentSize > 0) {
                bullet = BULLET_POOL.remove(presentSize - 1);
            } else {
                bullet = new CreateBullet(0, 0, 0, null, 0);//拿完了就创建默认的
            }
            bullet.resetTheBullet(); //重置属性
            return bullet;
        }

        /***
         * 坦克用完了就将炮弹还回来
         */
        public static void giveBackTheBullet(CreateBullet bullet) {
            int presentSize = BULLET_POOL.size();

            if (presentSize > POOL_MAX_SIZE) return;// 池子满了就不需要再还了
            BULLET_POOL.add(bullet);
        }
    }

    //爆炸效果池
    public static class ExplosionPool{

        public static final int POOL_MAX_SIZE = ALL_POOL_MAX_SIZE; // 最多添加300个爆炸效果
        public static final List<ExplosionSpecialEffects> EXPLOSION_SPECIAL_EFFECTS_POOL = new ArrayList<>(POOL_MAX_SIZE); //创建集合设定大小，减少不必要的内存浪费

        //初始化常量池
        static {
            for (int i = 0; i < POOL_MAX_SIZE; i++) {
                EXPLOSION_SPECIAL_EFFECTS_POOL.add(new ExplosionSpecialEffects()); //爆炸效果属性均为默认值 故在使用前要修改参数
            }
        }

        /***
         * 从爆炸效果常量池中取出爆炸效果
         * @return
         */
        public static ExplosionSpecialEffects getExplosionFromPool() {
            int presentSize = EXPLOSION_SPECIAL_EFFECTS_POOL.size();
            ExplosionSpecialEffects ex;
            if(presentSize > 0) {
                ex = EXPLOSION_SPECIAL_EFFECTS_POOL.remove(presentSize - 1);
            } else {
                ex = new ExplosionSpecialEffects();//拿完了就创建默认的
            }
            ex.resetTheExplosion(); //重置属性
            return ex;
        }

        /***
         * 坦克用完了就将爆炸效果还回来
         */
        public static void giveBackTheExplosion(ExplosionSpecialEffects explosionSpecialEffects) {
            int presentSize = EXPLOSION_SPECIAL_EFFECTS_POOL.size();
            if (presentSize > POOL_MAX_SIZE) return;// 池子满了就不需要再还了
            EXPLOSION_SPECIAL_EFFECTS_POOL.add(explosionSpecialEffects);
        }
    }

    //敌方坦克的对象池
    public static class EnemyTankPool {

       public static final int MAX_TANK = ALL_POOL_MAX_SIZE / 3;
        public static final List<EnemyTank> ENEMY_TANK_POOL = new ArrayList<>(MAX_TANK); //创建集合设定大小，减少不必要的内存浪费
        //初始化常量池
        static {
            for (int i = 0; i < MAX_TANK; i++) {
                ENEMY_TANK_POOL.add(new EnemyTank()); //敌人坦克均为默认值
            }
        }

        /***
         * 从敌人坦克常量池中取出坦克
         * @return
         */
        public static EnemyTank getEnemyFromPool() {
            int presentSize = ENEMY_TANK_POOL.size();
            EnemyTank enemyTank = null;
            //拿完了就创建默认的
            if (presentSize > 0) {
                enemyTank = ENEMY_TANK_POOL.remove(presentSize-1);
            } else{
                enemyTank = new EnemyTank();
            }
            enemyTank.resetEnemyTank();  //取出之前更新坦克信息
            return enemyTank;
        }

        /***
         * 坦克阵亡就将爆炸效果还回来
         */
        public static void giveBackTheEnemy(EnemyTank enemyTank) {
            int presentSize = ENEMY_TANK_POOL.size();
            if (presentSize > MAX_TANK) return;// 池子满了就不需要再还了
            ENEMY_TANK_POOL.add(enemyTank);
        }
    }

    //砖块池
    public static class BrickPool{
       public static final List<NormalBrick> BRICK_NORMAL_POOL = new ArrayList<>(BRICK_MAX);
       public static final List<IronBrick> BRICK_IRON_POOL = new ArrayList<>(BRICK_MAX);

    static {
        for (int i = 0; i < BRICK_MAX; i++) {
            //生成默认的砖
            BRICK_NORMAL_POOL.add(new NormalBrick(0,0));
            BRICK_IRON_POOL.add(new IronBrick(0,0));
        }
    }

        /***
         * 得到普通砖
         * @return
         */
    public static NormalBrick getTHeNormalBrick() {
        int presentSize = BRICK_NORMAL_POOL.size();
        NormalBrick normalBrick = null;
        //拿完了就创建默认的
        if (presentSize > 0) {
            normalBrick = BRICK_NORMAL_POOL.remove(presentSize-1);
        } else{
            normalBrick = new NormalBrick(0,0);
        }
        normalBrick.resetTheNormalBrick(); //重置砖的属性
        return normalBrick;
    }

        /***
         * 得到铁砖
         * @return
         */
        public static IronBrick getTHeIronBrick() {
            int presentSize = BRICK_IRON_POOL.size();
            IronBrick ironBrick = null;
            //拿完了就创建默认的
            if (presentSize > 0) {
                ironBrick = BRICK_IRON_POOL.remove(presentSize-1);
            } else{
                ironBrick = new IronBrick(0,0);
            }
            ironBrick.resetTheIronBrick();// 重置砖的属性
            return ironBrick;
        }

        /***
         * 被破坏就将砖块还回来
         */
        public static void giveBackTheNormalBrick(NormalBrick normalBrick) {
            int presentSize = BRICK_NORMAL_POOL.size();
            if (presentSize > BRICK_MAX) return;// 池子满了就不需要再还了
            BRICK_NORMAL_POOL.add(normalBrick);
        }

        /***
         * 被破坏就将砖块还回来
         */
        public static void giveBackTheIronBrick(IronBrick ironBrick) {
            int presentSize = BRICK_IRON_POOL.size();
            if (presentSize > BRICK_MAX) return;// 池子满了就不需要再还了
            BRICK_IRON_POOL.add(ironBrick);
        }
}
}

