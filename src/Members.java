import java.util.Vector;

class Tank{
    private int direct=1;
    private int x=0;
    private int y=0;
    private int type=0;
    boolean isLive=true;
    Vector<Shot> ss=new Vector<>();
    Shot s=null;
    int speed;
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }
    public Tank(int x,int y){
        this.x=x;
        this.y=y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void shoot(){
        //如果坦克活着，那么可以开枪，创建一颗子弹。
        if (isLive) {
            switch (direct) {
                case 1:
                    s = new Shot(x + 20, y - 10, direct);
                    ss.add(s);
                    break;
                case 2:
                    s = new Shot(x + 20, y + 50, direct);
                    ss.add(s);
                    break;
                case 3:
                    s = new Shot(x - 10, y + 20, direct);
                    ss.add(s);
                    break;
                case 4:
                    s = new Shot(x + 50, y + 20, direct);
                    ss.add(s);
                    break;
            }
            Thread t = new Thread(s);
            t.start();
        }

    }
}
class MyTank extends Tank{
    public MyTank(int x,int y){
        super(x,y);
        speed=10;
    }
    //坦克移动函数
    public void tankMove(int direct){
        switch (direct){
            case 1:
                this.setY(this.getY() - speed);
                break;
            case 2:
                this.setY(this.getY() + speed);
                break;
            case 3:
                this.setX(this.getX() - speed);
                break;
            case 4:
                this.setX(this.getX()+speed);
                break;
        }
    }
}
//由于敌人坦克移动的不确定性，所以其作为线程出现
class EnemyTank extends Tank implements Runnable{
    private Vector<EnemyTank> ets=new Vector<EnemyTank>();
    private int times=0;
    public static int speed;
    public EnemyTank(int x,int y){
        super(x,y);
    }
    //获得外界坦克以判断是否与其相撞
    public void getMypanelEts(Vector<EnemyTank> ets){
        this.ets=ets;
    }
    //判断是否与坦克相撞，若相撞返回真
    public boolean ifTouchOtherTank(){
        boolean b=false;
        switch (this.getDirect()){
            case 1:
                for (int i = 0; i < ets.size(); i++) {
                    EnemyTank et=ets.get(i);
                    if (et!=this) {
                            if (this.getX() >= et.getX() && this.getX() <= et.getX() + 40 && this.getY() >= et.getY() && this.getY() <= et.getY() + 40)
                                return true;
                            if (this.getX() + 40 >= et.getX() && this.getX() + 40 <= et.getX() + 40 && this.getY() >= et.getY() && this.getY() <= et.getY() + 40)
                                return true;

                    }
                }
                break;
            case 2:
                for (int i = 0; i < ets.size(); i++) {
                    EnemyTank et=ets.get(i);
                    if (et!=this) {
                        if (this.getX() >= et.getX() && this.getX() <= et.getX() + 40 && this.getY()+40 >= et.getY() && this.getY()+40 <= et.getY() + 40)
                            return true;
                        if (this.getX() + 40 >= et.getX() && this.getX() + 40 <= et.getX() + 40 && this.getY()+40 >= et.getY() && this.getY()+40 <= et.getY() + 40)
                            return true;

                    }
                }
                break;
            case 3:
                for (int i = 0; i < ets.size(); i++) {
                    EnemyTank et=ets.get(i);
                    if (et!=this) {
                        if (this.getX() >= et.getX() && this.getX() <= et.getX() + 40 && this.getY() >= et.getY() && this.getY() <= et.getY() + 40)
                            return true;
                        if (this.getX()  >= et.getX() && this.getX()  <= et.getX() + 40 && this.getY()+40 >= et.getY() && this.getY()+40 <= et.getY() + 40)
                            return true;

                    }
                }
                break;
            case 4:
                for (int i = 0; i < ets.size(); i++) {
                    EnemyTank et=ets.get(i);
                    if (et!=this) {
                        if (this.getX()+40 >= et.getX() && this.getX()+40 <= et.getX() + 40 && this.getY() >= et.getY() && this.getY() <= et.getY() + 40)
                            return true;
                        if (this.getX()+40  >= et.getX() && this.getX()+40  <= et.getX() + 40 && this.getY()+40 >= et.getY() && this.getY()+40 <= et.getY() + 40)
                            return true;

                    }
                }
                break;
        }
        return false;
    }

    @Override
    //坦克作为线程
    public void run() {
        //坦克创建伊始即获得一个随机方向
        this.setDirect((int)(Math.random()*4+1));
        while (true){
            switch (this.getDirect()){
                case 1:
                    for (int i = 0; i < 40; i++) {
                        try {
                            Thread.sleep(30);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (this.getY()>0&&!this.ifTouchOtherTank())
                        this.setY(this.getY() - speed);
                        if (this.getY()<=0||this.ifTouchOtherTank())
                            break;
                    }
                    break;
                case 2:
                    for (int i = 0; i < 40; i++) {
                        try {
                            Thread.sleep(30);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (this.getY()<850&&!this.ifTouchOtherTank())
                        this.setY(this.getY() + speed);
                        if (this.getY()>=850||this.ifTouchOtherTank())
                            break;
                    }
                    break;
                case 3:
                    for (int i = 0; i < 40; i++) {
                        try {
                            Thread.sleep(30);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (this.getX()>0&&!this.ifTouchOtherTank())
                        this.setX(this.getX() - speed);
                        if (this.getX()<=0||this.ifTouchOtherTank())
                            break;
                    }
                    break;
                case 4:
                    for (int i = 0; i < 40; i++) {
                        try {
                            Thread.sleep(30);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (this.getX()<1850&&!this.ifTouchOtherTank())
                        this.setX(this.getX()+speed);
                        if (this.getX()>=1850||this.ifTouchOtherTank())
                            break;
                    }
                    break;
            }
            this.setDirect((int)(Math.random()*4+1));
            times++;
            if (times%2==0) {
                if (this.isLive) {
                    if (this.ss.size() < 5) {
                        Shot shot = null;
                        switch (this.getDirect()) {
                            case 1:
                                shot = new Shot(this.getX() + 20, this.getY() - 10, this.getDirect());
                                this.ss.add(shot);
                                break;
                            case 2:
                                shot = new Shot(this.getX() + 20, this.getY() + 50, this.getDirect());
                                this.ss.add(shot);
                                break;
                            case 3:
                                shot = new Shot(this.getX() - 10, this.getY() + 20, this.getDirect());
                                this.ss.add(shot);
                                break;
                            case 4:
                                shot = new Shot(this.getX() + 50, this.getY() + 20, this.getDirect());
                                this.ss.add(shot);
                                break;
                        }
                        Thread thread1 = new Thread(shot);
                        thread1.start();
                    }
                }
            }
            if (!this.isLive)
                break;
        }
    }
}
class Shot implements Runnable{
    int x;
    int y;
    private int direct;
    static int speed;
    boolean isLive=true;
    public Shot(int x,int y,int direct){
        this.x=x;
        this.y=y;
        this.direct=direct;
    }
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            switch (direct) {
                case 1:
                    y -= speed;
                    break;
                case 2:
                    y += speed;
                    break;
                case 3:
                    x -= speed;
                    break;
                case 4:
                    x += speed;
                    break;
            }
            if (x<0||x>1850||y<0||y>900) {
                isLive=false;
                break;
            }
        }
    }
}
class Bomb{
    int x;
    int y;
    int life=15;
    boolean isLive=true;
    public Bomb(int x,int y){
        this.x=x;
        this.y=y;
    }
    public void lifeDown(){
        if (life>0){
            life--;
        }
        else {
            isLive=false;
        }
    }
}
class Detector implements Runnable{
    Vector<EnemyTank> ets;
    MyTank myTank;
    int enSize;
    public void getAInit(Vector<EnemyTank> ets,int enSize){
        this.ets=ets;
        this.enSize=enSize;
    }
    public void getMyTank(MyTank myTank){
        this.myTank=myTank;
    }

    @Override
    public void run() {
        while (true) {
            if (ets.size() < enSize) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                EnemyTank enemyTank = new EnemyTank((int) (Math.random() * 1800), (int) (Math.random() * 800));
                enemyTank.setType(1);
                enemyTank.getMypanelEts(ets);
                Thread thread = new Thread(enemyTank);
                thread.start();
                ets.add(enemyTank);
            }
            if (Recorder.myTankLife>0) {
                if (!myTank.isLive) {
                    myTank.isLive=true;
                }
            }
        }
    }
}
class Recorder{
    static int etNums=0;
    static int myTankLife=10;
    public static void plus(){
        etNums++;
    }
    public static void reduce(){
        if (myTankLife>0)
        myTankLife--;
    }
}

