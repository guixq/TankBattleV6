import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

public class TankGame01 extends JFrame implements ActionListener {
    public static void main(String[] args) {
        TankGame01 tankGame01=new TankGame01();
    }
    private MyStartPanel msp=null;
    private Mypanel mypanel=null;
    public TankGame01(){
        JMenuBar jmb=new JMenuBar();
        JMenu jm1=new JMenu("游戏(O)");
        JMenu jm2=new JMenu("开始游戏（g）");
        jm1.setMnemonic('o');
        jm2.setMnemonic('g');
        JMenuItem jmi1=new JMenuItem("一般模式（q）");
        JMenuItem jmi2=new JMenuItem("地狱模式（w）");
        JMenuItem jmi3=new JMenuItem("退出游戏(e)");
        jmi1.setMnemonic('q');
        jmi1.addActionListener(this);
        jmi1.setActionCommand("common");
        jmi2.setMnemonic('w');
        jmi2.addActionListener(this);
        jmi2.setActionCommand("difficult");
        jmi3.setMnemonic('e');
        jmi3.addActionListener(this);
        jmi3.setActionCommand("exit");
        jm1.add(jm2);
        jm1.add(jmi3);
        jm2.add(jmi1);
        jm2.add(jmi2);
        jmb.add(jm1);
        this.setJMenuBar(jmb);
        this.setTitle("坦克大战");
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        msp=new MyStartPanel();
        Thread thread=new Thread(msp);
        thread.start();
        this.getContentPane().add(msp);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("common")){
            EnemyTank.speed=5;
            Shot.speed=3;
            mypanel=new Mypanel(15);
            mypanel.myTank.speed=5;
            Thread t=new Thread(mypanel);
            t.start();
            //移除开始面板
            this.remove(msp);
            //加入游戏面板
            this.add(mypanel);
            //给jFrame注册键盘监听器
            this.addKeyListener(mypanel);
            //刷新面板
            this.setVisible(true);
        }else if(e.getActionCommand().equals("difficult")){
            EnemyTank.speed=15;
            Shot.speed=8;
            mypanel=new Mypanel(30);
            mypanel.myTank.speed=10;
            Thread t=new Thread(mypanel);
            t.start();
            //移除开始面板
            this.remove(msp);
            //加入游戏面板
            this.add(mypanel);
            //给jFrame注册键盘监听器
            this.addKeyListener(mypanel);
            //刷新面板
            this.setVisible(true);
        }
        else if (e.getActionCommand().equals("exit")){
            System.exit(0);
        }
    }
    class MyStartPanel extends JPanel implements Runnable{
        int times=0;
//        Image image;
//
//        {
//            try {
//                image = ImageIO.read(new File("src/imgs/BackGround.jpg"));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }

        public void paint(Graphics g){
            super.paint(g);
            g.setColor(Color.BLACK);
            g.fillRect(0,0,1950,900);
            g.setColor(Color.BLUE);
            Font font=new Font("华文新魏",Font.BOLD,60);
            g.setFont(font);
//            g.drawImage(image,0,0,1950,950,this);
            if (times%2==0) {
                g.drawString("坦克大战", 600, 250);
            }

        }

        @Override
        public void run() {
            while (true){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                times++;
                if (times==200){
                    times=0;
                }
                this.repaint();
            }
        }
    }

    //我的面板
    class Mypanel extends JPanel implements KeyListener ,Runnable{
        MyTank myTank=null;
        Vector<EnemyTank> ets=new Vector<EnemyTank>();
        Vector<Bomb> bombs=new Vector<Bomb>();
        Image image1=null;
        Image image2=null;
        Image image3=null;
        Image image4=null;
        Image image5=null;
        int enSize;
        //人为创造监听对象，使敌方坦克复活
        Detector dt=null;
        public Mypanel(int enSize){
            myTank=new MyTank(500,500);
            dt=new Detector();
            try {
                image1= ImageIO.read(new File("src/imgs/blast1.gif"));
                image2= ImageIO.read(new File("src/imgs/blast2.gif"));
                image3= ImageIO.read(new File("src/imgs/blast3.gif"));
                image4= ImageIO.read(new File("src/imgs/blast4.gif"));
                image5= ImageIO.read(new File("src/imgs/blast5.gif"));
            } catch (IOException e) {
                e.printStackTrace();
            }
//            image1=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/imgs/blast1.gif"));
//            image2=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/imgs/blast2.gif"));
//            image3=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/imgs/blast3.gif"));
//            image4=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/imgs/blast4.gif"));
//            image5=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/imgs/blast5.gif"));
            this.enSize=enSize;
            for (int i = 0; i < enSize; i++) {
                EnemyTank enemyTank=new EnemyTank((i)*64,0);
                enemyTank.setType(1);
                enemyTank.getMypanelEts(ets);
                Thread thread=new Thread(enemyTank);
                thread.start();
                ets.add(enemyTank);
            }
            dt.getAInit(ets,enSize);
            dt.getMyTank(myTank);
            Thread thread=new Thread(dt);
            thread.start();
//            for (int i = 30; i < enSize; i++) {
//                EnemyTank enemyTank=new EnemyTank((i-30)*64,850);
//                enemyTank.setType(1);
//                enemyTank.getMypanelEts(ets);
//                Thread thread=new Thread(enemyTank);
//                thread.start();
//                ets.add(enemyTank);
//            }
        }
        public void paint(Graphics g){
            super.paint(g);
            g.setColor(Color.BLACK);
            g.fillRect(0,0,1950,900);
            //如果我的坦克活着，画出我的坦克以及我的坦克的子弹
            if (myTank.isLive) {
                drawTank(myTank.getX(), myTank.getY(), g, myTank.getDirect(), myTank.getType());
                for (int i=0;i<myTank.ss.size();i++) {
                    Shot shot=myTank.ss.get(i);
                    if (shot != null && shot.isLive) {
                        g.setColor(Color.GREEN);
                        g.fillOval(shot.x, shot.y, 3, 3);
                    }
                    if (!shot.isLive) {
                        myTank.ss.remove(shot);
                    }
                }
            }
            //如果敌人坦克活着，画出敌人坦克以及敌人坦克子弹
            for (int i = 0; i < ets.size(); i++) {
                EnemyTank et=ets.get(i);
                if (et.isLive) {
                    this.drawTank(et.getX(), et.getY(), g, et.getDirect(), et.getType());
                    for (int j = 0; j < et.ss.size(); j++) {
                        Shot shot=et.ss.get(j);
                        if (shot.isLive) {
                            g.setColor(Color.YELLOW);
                            g.fillOval(shot.x,shot.y,3,3);
                        }else{
                            et.ss.remove(shot);
                        }
                    }
                }
            }
            //画出爆炸集合中的对象
            for (int i = 0; i < bombs.size(); i++) {
                Bomb bomb=bombs.get(i);
                    if (bomb.life > 12) {
                        g.drawImage(image5, bomb.x, bomb.y, 30, 30, this);
                    }
                    else if (bomb.life > 9)
                        g.drawImage(image4, bomb.x, bomb.y, 30, 30, this);
                    else if (bomb.life > 6)
                        g.drawImage(image3, bomb.x, bomb.y, 30, 30, this);
                    else if (bomb.life > 3)
                        g.drawImage(image2, bomb.x, bomb.y, 30, 30, this);
                    else
                        g.drawImage(image1, bomb.x, bomb.y, 30, 30, this);
                    bomb.lifeDown();
                    if (bomb.life == 0) {
                        bombs.remove(bomb);
                    }
            }
            g.setColor(Color.BLUE);
            Font font=new Font("华文新魏",Font.BOLD,40);
            g.setFont(font);
            g.drawString("已击杀坦克数："+Recorder.etNums,300,950);
            g.setColor(Color.RED);
            g.drawString("剩余复活次数："+Recorder.myTankLife,1000,950);
        }
        //通过graphicsColor和graphicsColor01两个辅助函数控制画笔颜色画出敌我坦克
        private void drawTank(int x,int y,Graphics g,int direct,int type){
            switch (direct){
                case 1:
                    graphicsColor(g,type);
                    g.fill3DRect(x,y,10,40,false);
                    g.fill3DRect(x+30,y,10,40,false);
                    g.fill3DRect(x+10,y+10,20,20,false);
                    graphicsColor01(g,type);
                    g.fillOval(x+10,y+10,20,20);
                    graphicsColor(g,type);
                    g.drawLine(x+20,y+20,x+20,y-10);
                    break;
                case 2:
                    graphicsColor(g,type);
                    g.fill3DRect(x,y,10,40,false);
                    g.fill3DRect(x+30,y,10,40,false);
                    g.fill3DRect(x+10,y+10,20,20,false);
                    graphicsColor01(g,type);
                    g.fillOval(x+10,y+10,20,20);
                    graphicsColor(g,type);
                    g.drawLine(x+20,y+20,x+20,y+50);
                    break;
                case 3:
                    graphicsColor(g,type);
                    g.fill3DRect(x,y,40,10,false);
                    g.fill3DRect(x,y+30,40,10,false);
                    g.fill3DRect(x+10,y+10,20,20,false);
                    graphicsColor01(g,type);
                    g.fillOval(x+10,y+10,20,20);
                    graphicsColor(g,type);
                    g.drawLine(x+20,y+20,x-10,y+20);
                    break;
                case 4:
                    graphicsColor(g,type);
                    g.fill3DRect(x,y,40,10,false);
                    g.fill3DRect(x,y+30,40,10,false);
                    g.fill3DRect(x+10,y+10,20,20,false);
                    graphicsColor01(g,type);
                    g.fillOval(x+10,y+10,20,20);
                    graphicsColor(g,type);
                    g.drawLine(x+20,y+20,x+50,y+20);
                    break;
            }
        }
        //辅助画笔颜色
        private void graphicsColor(Graphics g,int type){
            switch (type){
                case 0:
                    g.setColor(Color.WHITE);
                    break;
                case 1:
                    g.setColor(Color.PINK);
                    break;
            }
        }
        //辅助画笔颜色01
        private void graphicsColor01(Graphics g,int type){
            switch (type){
                case 0:
                    g.setColor(Color.BLUE);
                    break;
                case 1:
                    g.setColor(Color.YELLOW);
                    break;
            }
        }
        //判断子弹是否击中坦克，倘若击中，将双方设为死亡，并创造爆炸对象。
        public void ifHitTank(Shot s,Tank e){
            if (s.x>e.getX()&&s.x<e.getX()+40&&s.y>e.getY()&&s.y<e.getY()+40){
                s.isLive=false;
                e.isLive=false;
                if (ets.contains(e)){
                Recorder.plus();
                ets.remove(e);
                }else {
                    Recorder.reduce();
                }
                Bomb bomb=new Bomb(e.getX(),e.getY());
                bombs.add(bomb);
            }
        }
        @Override
        //面板作为线程启动
        public void run() {
            while (true){
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //判断我的坦克的子弹是否击中敌方坦克
                for (int i = 0; i < myTank.ss.size(); i++) {
                    Shot s=myTank.ss.get(i);
                    for (int j = 0; j < ets.size(); j++) {
                        EnemyTank et=ets.get(j);
                        if (et.isLive)
                            this.ifHitTank(s,et);
                    }
                }
                //判断敌方坦克子弹是否击中我方坦克
                for (int i = 0; i < ets.size(); i++) {
                    EnemyTank et=ets.get(i);
                    for (int j = 0; j < et.ss.size(); j++) {
                        Shot shot=et.ss.get(j);
                        if (myTank.isLive)
                        ifHitTank(shot,myTank);
                    }
                }
                //重绘10毫秒后的界面
                repaint();
            }
        }

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()){
                case 37:
                    myTank.setDirect(3);
                    myTank.tankMove(myTank.getDirect());
                    break;
                case 38:
                    myTank.setDirect(1);
                    myTank.tankMove(myTank.getDirect());
                    break;
                case 39:
                    myTank.setDirect(4);
                    myTank.tankMove(myTank.getDirect());
                    break;
                case 40:
                    myTank.setDirect(2);
                    myTank.tankMove(myTank.getDirect());
                    break;
            }
            if (e.getKeyCode()==KeyEvent.VK_SPACE) {
                this.myTank.shoot();
            }
            repaint();
        }
        @Override
        public void keyReleased(KeyEvent e) {

        }
    }
}

