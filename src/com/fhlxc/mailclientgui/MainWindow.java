package com.fhlxc.mailclientgui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import com.fhlxc.mygui.MyMainJPanel;

import java.awt.BorderLayout;
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
        setBounds((Toolkit.getDefaultToolkit().getScreenSize().width - 1500) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - 950) / 2, 1500, 950);
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
        //设置主界面背景的颜色
        contentPane.setBackgroundColor(Color.white);
        //设置主界面能的标题字体样式
        contentPane.setFont(new Font("宋体", Font.PLAIN, 15));
        //设置主界面标题的字的颜色
        contentPane.setFontColor(Color.black);
        //设置主界面的最小化、最大化按钮，参数1：按钮上的文字;参数2：按钮上图片的路径;参数3：按钮的背景颜色；参数4、5、6：按钮三种状态的颜色
        contentPane.setMinButton("", new ImageIcon("图片/min.png").getImage(), Color.white, new Color(234, 249, 255), new Color(214, 242, 254));
        contentPane.setMaxButton("", new ImageIcon("图片/max.png").getImage(), Color.white, new Color(234, 249, 255), new Color(214, 242, 254));
        //设置主界面关闭按钮，参数1：按钮的文本内容；参数2：传入图片;参数3、4、5：按钮三种状态的颜色
        contentPane.setCloseButton("", new ImageIcon("图片/close.png").getImage(), Color.white, new Color(234, 249, 255), new Color(214, 242, 254));
        //设置标题栏，一定要在设置按钮之后使用它，参数1：传入图片，参数2：标题内容
        contentPane.setTitleBarJPanel(new ImageIcon("图片/startup.png").getImage(), "邮件客户端");
        //新建一个对话框
        InfoDialog infoDialog = new InfoDialog(this);
        infoDialog.setDialog("确定关闭对话框吗？", new ImageIcon("图片/warning.png").getImage());
        infoDialog.setVisible(false);
        //把对话框窗口和对话框内容传给主界面，便于直接操作
        contentPane.setDialog(infoDialog.getDialog(), infoDialog.getDialogJPanel());
        //窗口里设置JPanel，这个方法会自动设置JPanel的大小为窗口大小，如用add则需自己设置大小
        setContentPane(contentPane);
        ContentJPanel contentJPanel = new ContentJPanel(this);
        UserJPanel userJPanel = new UserJPanel(this, contentJPanel);
        //userJPanel.setBackgroundImage(new ImageIcon("图片/userBackground.png").getImage());
        //userJPanel.setBackgroundColor(Color.white);
        contentPane.add(userJPanel, BorderLayout.WEST);
        contentPane.add(contentJPanel, BorderLayout.CENTER);
        contentJPanel.setxSize(this.getWidth() * 8 / 10, 32);
        contentJPanel.setButtons();
        contentJPanel.setTextArea();
        contentJPanel.setMailDetail();
        //contentJPanel.setBackgroundImage(new ImageIcon("图片/mailBackground.png").getImage());
        //contentJPanel.setBackgroundColor(Color.white);
        contentPane.getMaxButton().addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                if (getExtendedState() == JFrame.NORMAL) {
                    //改变imageButton变量的值，并将最小化的按钮变为不可见，有一个bug，调不好，只好放弃，
                    //即在最大化状态，最小，之后还原，变为正常状态而不是最大状态
                    contentPane.getMaxButton().setImageButton(new ImageIcon("图片/normal.png").getImage());
                    contentPane.getMinButton().setVisible(false);
                    //改变窗口的状态，变为最大化状态，会自动调用paintComponent函数
                    setExtendedState(JFrame.MAXIMIZED_BOTH);
                    userJPanel.resetSize();
                    contentJPanel.setxSize(getWidth() * 7 / 10, 32);
                    contentJPanel.resetSize();
                } else {
                    //在最大状态就变小，并将最小化按钮可见
                    contentPane.getMaxButton().setImageButton(new ImageIcon("图片/max.png").getImage());
                    contentPane.getMinButton().setVisible(true);
                    //使窗口恢复正常
                    setExtendedState(JFrame.NORMAL);
                    userJPanel.resetSize();
                    contentJPanel.setxSize(getWidth() * 7 / 10, 32);
                    contentJPanel.resetSize();
                }
            }
        });
    }
}
