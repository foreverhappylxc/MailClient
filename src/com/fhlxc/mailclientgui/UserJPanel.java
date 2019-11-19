package com.fhlxc.mailclientgui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.tree.TreePath;

import com.fhlxc.backend.GetInfo;
import com.fhlxc.backend.LoginIn;
import com.fhlxc.entity.User;
import com.fhlxc.gui.MyDialogJPanel;
import com.fhlxc.gui.MyJButton;

/**
* @author Xingchao Long
* @date 2019/19/07 14:19:16
* @ClassName UserJPanel
* @Description 邮件用户面板
*/

@SuppressWarnings("serial")
public class UserJPanel extends JPanel {
    //传给用户面板的主界面
    private JFrame frame;
    //树形结构的组件
    private MyJTree myJTree;
    //获取已经登录的信息
    private GetInfo GetInfo;
    //背景图片和颜色
    private Color backgroundColor;
    private Image backgroundImage;
    private JDialog dialog = new JDialog();
    private MyDialogJPanel myDialogJPanel = new MyDialogJPanel();
    private UserNode root;
    
    public void dialog() {
        dialog.setModal(true);
        dialog.setUndecorated(true);
        myDialogJPanel.setMyDialog(dialog);
        //设置对话框的标题栏的高度
        myDialogJPanel.setTitleBarHeight(32);
        //设置对话框的字体样式，指的是标题栏的字体
        myDialogJPanel.setFont(new Font("宋体", Font.PLAIN, 15));
        //设置对话框标题栏的字体颜色
        myDialogJPanel.setFontColor(Color.black);
        //设置对话框的背景颜色
        myDialogJPanel.setBackgroundColor(Color.white);
        //设置对话框标题栏的关闭按钮
        myDialogJPanel.setTitleBarButton("", new ImageIcon("图片/close.png").getImage(), Color.white, new Color(234, 249, 255), new Color(214, 242, 254));
        //设置标题栏左上角图标和标题内容
        myDialogJPanel.setTitleBarJPanel(new ImageIcon("图片/startup.png").getImage(), "提示对话框");
        //设置对话框中间部分，左边是图片，右边是文字内容（可设置字体和颜色）
        myDialogJPanel.setContentJPanel("登录出错", new ImageIcon("图片/warning.png").getImage(), new Font("宋体", Font.PLAIN, 20), Color.black);
        //设置下方确定和取消按钮，参数和设置按钮的参数是一样的
        myDialogJPanel.setOkButton("Ok", null, new Font("宋体", Font.PLAIN, 20), Color.black, Color.white, new Color(234, 249, 255), new Color(214, 242, 254));
        myDialogJPanel.setCancelButton("Cancel", null, new Font("宋体", Font.PLAIN, 20), Color.black, Color.white, new Color(234, 249, 255), new Color(214, 242, 254));
        //组合对话框的下层按钮，一定要放在ok和cancel button之后
        myDialogJPanel.setButtonJPanel();
        dialog.setContentPane(myDialogJPanel);
    }
    
    //构造函数
    public UserJPanel(JFrame frame) {
        this.frame = frame;
        setOpaque(false);
        setPreferredSize(new Dimension(frame.getWidth() * 3 / 10, 0));
        setLayout(new BorderLayout(0, 0));
        GetInfo = new GetInfo();
        root = new UserNode("用户");
        //遍历得到已经登录的用户
        List<UserNode> list = GetInfo.getUserNodes();
        for (UserNode userNode: list) {
            userNode.add(new UserNode("收件箱"));
            userNode.add(new UserNode("发件箱"));
            root.add(userNode);
        }
        myJTree = new MyJTree(root);
        myJTree.setMyJTree(32);
        //给myTree添加滚动条
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setOpaque(false);
        scrollPane.setBorder(null);
        //设置滚动条样式
        scrollPane.getVerticalScrollBar().setUI(new MyScrollBarUI());
        scrollPane.setViewportView(myJTree);
        //设置滚动条背景透明
        scrollPane.getViewport().setOpaque(false);
        add(scrollPane);
        //登录对话框
        JDialog loginDialog = new JDialog();
        LoginJPanel loginJPanel = new LoginJPanel();
        loginDialog.setUndecorated(true);
        loginDialog.setModal(true);
        //将对话框传给对话框主界面
        loginJPanel.setMyDialog(loginDialog);
        //设置对话框的标题栏的高度
        loginJPanel.setTitleBarHeight(32);
        //设置对话框的字体样式，指的是标题栏的字体
        loginJPanel.setFont(new Font("宋体", Font.PLAIN, 15));
        //设置对话框标题栏的字体颜色
        loginJPanel.setFontColor(Color.black);
        //设置对话框的背景颜色
        loginJPanel.setBackgroundColor(Color.white);
        //设置对话框标题栏的关闭按钮
        loginJPanel.setTitleBarButton("", new ImageIcon("图片/close.png").getImage(), Color.white, new Color(234, 249, 255), new Color(214, 242, 254));
        //设置标题栏左上角图标和标题内容
        loginJPanel.setTitleBarJPanel(new ImageIcon("图片/startup.png").getImage(), "登录");
        loginJPanel.setContentJPanel();
        //设置下方确定和取消按钮，参数和设置按钮的参数是一样的
        loginJPanel.setOkButton("登录", null, new Font("宋体", Font.PLAIN, 20), Color.black, Color.white, new Color(234, 249, 255), new Color(214, 242, 254));
        loginJPanel.setCancelButton("取消", null, new Font("宋体", Font.PLAIN, 20), Color.black, Color.white, new Color(234, 249, 255), new Color(214, 242, 254));
        //组合对话框的下层按钮，一定要放在ok和cancel button之后
        loginJPanel.setButtonJPanel();
        loginDialog.setContentPane(loginJPanel);
        //存放添加和删除账户的按钮
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        MyJButton addAccountButton = new MyJButton();
        MyJButton deleteAccountButton = new MyJButton();
        addAccountButton.setTextString("添加账户");
        addAccountButton.setFont(new Font("楷体", Font.PLAIN, 16));
        addAccountButton.setFontColor(Color.black);
        addAccountButton.setHoverColor(new Color(234, 249, 255));
        addAccountButton.setPressColor(new Color(214, 242, 254));
        addAccountButton.setPreferredSize(new Dimension(0, 32));
        addAccountButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                //获取当前窗口的位置
                int width = frame.getWidth() / 3;
                int height = frame.getHeight() / 4;
                //每次弹出均使对话框居中
                loginDialog.setBounds((frame.getWidth() - width) / 2 + frame.getX(), (frame.getHeight() - height) / 2 + frame.getY(), width, height);
                //每次弹出窗口，都重设对话框的各组件的大小
                loginJPanel.resetSize();
                loginDialog.setVisible(true);
                //点击登录按钮处理
                if (loginJPanel.getClose()) {
                    LoginIn loginIn = new LoginIn();
                    User user = new User();
                    user.setNickName(loginJPanel.getNickName());
                    user.setEmail(loginJPanel.getMail());
                    user.setPwd(loginJPanel.getPWD());
                    if (loginIn.login(user.getEmail(), user.getServerPOP3(), user.getPwd())) {
                        UserNode userNode = new UserNode(user.getNickName(), user.getEmail(), null);
                        userNode.add(new UserNode("收件箱"));
                        userNode.add(new UserNode("发件箱"));
                        root.add(userNode);
                        myJTree.updateUI();
                        myJTree.scrollPathToVisible(new TreePath(userNode.getPath()));
                    } else {
                        dialog();
                        dialog.setVisible(true);
                    }
                }
            }
        });
        deleteAccountButton.setTextString("删除账户");
        deleteAccountButton.setFontColor(Color.black);
        deleteAccountButton.setHoverColor(new Color(234, 249, 255));
        deleteAccountButton.setPressColor(new Color(214, 242, 254));
        deleteAccountButton.setPreferredSize(new Dimension(0, 32));
        deleteAccountButton.setFont(new Font("楷体", Font.PLAIN, 16));
        deleteAccountButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                
            }
        });
        panel.setLayout(new GridLayout(1, 2));
        panel.add(addAccountButton);
        panel.add(deleteAccountButton);
        add(panel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        setOpaque(false);
    }
    
    //重设大小
    public void resetSize() {
        setPreferredSize(new Dimension(frame.getWidth() *3 / 10, 0));
    }
    
    //设置背景颜色
    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
    
    //设置背景图片
    public void setBackgroundImage(Image backgroundImage) {
        this.backgroundImage = backgroundImage;
    }
    
    //重绘背景
    public void paintComponent(Graphics g) {
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
        } 
        if (backgroundColor != null){
            g.setColor(backgroundColor);
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
        }
    }
}
