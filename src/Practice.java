import javax.swing.*;
import java.awt.*;

public class Practice extends JFrame {
    JMenuBar jmb=null;
    JMenu jm1=null;
    JMenuItem jmi=null;
    private Menu menu=null;
    public Practice(){

        jmb=new JMenuBar();
        jm1=new JMenu("kaishi");
        jmi=new JMenuItem("S");
        jm1.add(jmi);
        jmb.add(jm1);
        JFrame jFrame=new JFrame("坦克大战");
        jFrame.setBounds(600,100,800,600);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
        jFrame.setJMenuBar(jmb);
//        this.setJMenuBar(jmb);	//添加菜单栏，不能设定位置，会自动放在最上部
//        this.setTitle("NotePad");
//        this.setSize(600, 500);
//        this.setVisible(true);
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
    public static void main(String[] args) {
        Practice practice=new Practice();

    }
}
