package com.fhlxc.gui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.awt.Color;

/**
* @author Xingchao Long
* @date 2019/21/23 13:21:32
* @ClassName MainWindow
* @Description 邮件客户端的主界面
*/

@SuppressWarnings("serial")
public class MainWindow extends JFrame {
    private MyMainJPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        //线程，frame放入线程中，以便处理任何事件，界面程序都会有两个线程，一个监听用户操作，一个实现后台处理
        EventQueue.invokeLater(new Runnable() {
            //实现runnable接口的run方法
            public void run() {
                try {
                    MainWindow frame = new MainWindow();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public MainWindow() {
        //设置窗口打下，获取屏幕的尺寸，并通过某些操作，设置窗口居中显示
        setBounds((Toolkit.getDefaultToolkit().getScreenSize().width - 1000) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - 750) / 2, 1000, 750);
        //去除默认的标题栏
        setUndecorated(true);
        //新建一个JPanel，当做主界面
        contentPane = new MyMainJPanel();
        //传入窗口给主界面
        contentPane.setJFrame(this);
        //设置主界面的背景图像
        contentPane.setImage(new ImageIcon("图片/background.png").getImage());
        //设置主界面标题栏的高度
        contentPane.setTitleBarHeight(32);
        //设置主界面标题栏的颜色
        contentPane.setBackgroundColor(Color.white);
        contentPane.setFont(new Font("宋体", Font.PLAIN, 15));
        contentPane.setFontColor(Color.black);
        //设置主界面的最小化、最大化按钮，参数1：传入窗口，JFrame;参数2：按钮的文本内容;参数3：图片路径；参数4、5、6：按钮三种状态的颜色
        contentPane.setMinButton("", new ImageIcon("图片/min.png").getImage(), Color.white, new Color(234, 249, 255), new Color(214, 242, 254));
        contentPane.setMaxButton("", new ImageIcon("图片/max.png").getImage(), Color.white, new Color(234, 249, 255), new Color(214, 242, 254));
        //设置主界面关闭按钮，参数1：按钮的文本内容；参数2：传入图片的路径;参数3、4、5：按钮三种状态的颜色
        contentPane.setCloseButton("", new ImageIcon("图片/close.png").getImage(), Color.white, new Color(234, 249, 255), new Color(214, 242, 254));
        //设置标题栏，一定要在设置按钮之后使用它，参数1：传入窗口，参数2：图片位置；参数3：标题名
        contentPane.setTitleBarJPanel(new ImageIcon("图片/startup.png").getImage(), "邮件客户端");
        //窗口里设置JPanel，这个方法会自动设置JPanel的大小为窗口大小，如用add则需自己设置大小
        setContentPane(contentPane);
    }
}
