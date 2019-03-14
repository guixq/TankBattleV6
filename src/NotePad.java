import java.awt.*;
import javax.swing.*;

public class NotePad extends JFrame{
    JMenuBar jmb;	//菜单条组件
    JMenu menu1, menu2, menu3, menu4, menu5;//菜单
    JMenuItem item1, item2, item3, item4, item5, item6;//菜单项



    public NotePad(){
        //创建菜单
        jmb = new JMenuBar();

        menu1 = new JMenu("文件(F)");
        menu1.setMnemonic('f');	//助记符
        menu2 = new JMenu("编辑");
        menu2.setMnemonic('E');
        menu3 = new JMenu("格式");
        menu4 = new JMenu("查看");
        menu5 = new JMenu("帮助");
        item1 = new JMenuItem("打开");
        item2 = new JMenuItem("保存(S)");
        item3 = new JMenuItem("另存为");
        item4 = new JMenuItem("页面设置");
        item5 = new JMenuItem("打印");
        item6 = new JMenuItem("退出");
        menu1.add(item1);
        menu1.add(item2);
        menu1.add(item3);
        menu1.addSeparator();
        menu1.add(item4);
        menu1.add(item5);
        menu1.add(item6);
        //将菜单加入至菜单栏
        jmb.add(menu1);
        jmb.add(menu2);
        jmb.add(menu3);
        jmb.add(menu4);
        jmb.add(menu5);

        this.setJMenuBar(jmb);	//添加菜单栏，不能设定位置，会自动放在最上部
        this.setTitle("NotePad");
        this.setSize(600, 500);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        NotePad np = new NotePad();

    }


}



