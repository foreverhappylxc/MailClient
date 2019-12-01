package com.fhlxc.mailclientgui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
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
import javax.swing.ScrollPaneConstants;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import com.fhlxc.backend.GetInfo;
import com.fhlxc.backend.LoginIn;
import com.fhlxc.data.Info;
import com.fhlxc.entity.Mail;
import com.fhlxc.entity.User;
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
    private DefaultTreeModel model;
    //获取已经登录的信息
    private GetInfo getInfo;
    //背景图片和颜色
    private Color backgroundColor;
    private Image backgroundImage;
    private UserNode root;
    
    //提示框
    private InfoDialog infoDialog;
    //右侧的邮件信息面板
    private ContentJPanel contentJPanel;
    //登录和删除
    private LoginIn loginIn;
    
    
    
    //构造函数
    public UserJPanel(JFrame frame, ContentJPanel contentJPanel) {
        this.frame = frame;
        this.contentJPanel = contentJPanel;
        infoDialog = new InfoDialog(frame);
        loginIn = new LoginIn();
        setOpaque(false);
        setPreferredSize(new Dimension(frame.getWidth() * 2 / 10, 0));
        setLayout(new BorderLayout(0, 0));
        getInfo = new GetInfo();
        root = new UserNode("用户");
        //遍历得到已经登录的用户
        List<UserNode> list = getInfo.getUserNodes();
        for (UserNode userNode: list) {
            userNode.add(new UserNode("收件箱"));
            userNode.add(new UserNode("发件箱"));
            root.add(userNode);
        }
        myJTree = new MyJTree(root);
        myJTree.setMyJTree(32);
        setMyJTree();
        model = (DefaultTreeModel) myJTree.getModel();
        //给myTree添加滚动条
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setOpaque(false);
        scrollPane.setBorder(null);
        //设置滚动条样式
        scrollPane.getVerticalScrollBar().setUI(new MyScrollBarUI(new ImageIcon("图片/up.png").getImage(), new ImageIcon("图片/down.png").getImage()));
        scrollPane.getVerticalScrollBar().setUnitIncrement(15);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        //scrollPane.getHorizontalScrollBar().setUI(new MyScrollBarUI(new ImageIcon("图片/left.png").getImage(), new ImageIcon("图片/right.png").getImage()));
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
        
        //设置添加用户的按钮
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
                //一个无线循环用来一直返回登录界面
                while (true) {
                    loginDialog.setVisible(true);
                    //点击登录按钮处理
                    if (loginJPanel.getClose()) {
                        frame.getContentPane().setCursor(new Cursor(Cursor.WAIT_CURSOR));
                        String mail = loginJPanel.getMail();
                        String nickName = loginJPanel.getNickName();
                        String pwd = loginJPanel.getPWD();
                        //首先检查用户是否已经登录
                        if (loginIn.check(mail)) {
                            InfoDialog ii = new InfoDialog(frame);
                            ii.setDialog("该账户已经登录", new ImageIcon("图片/error.png").getImage());
                            ii.setVisible(true);
                            frame.getContentPane().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                        } else {
                            frame.getContentPane().setCursor(new Cursor(Cursor.WAIT_CURSOR));
                            //登录成功添加组件
                            if (loginIn.login(nickName, mail, pwd)) {
                                UserNode userNode = new UserNode(Info.currUser.getNickName(), Info.currUser.getEmail(), null, Info.currUser);
                                userNode.add(new UserNode("收件箱"));
                                userNode.add(new UserNode("发件箱"));
                                root.add(userNode);
                                myJTree.updateUI();
                                myJTree.scrollPathToVisible(new TreePath(userNode.getPath()));
                                frame.getContentPane().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                                break;
                            } else { //失败，做法如下
                                frame.getContentPane().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                                infoDialog.setDialog("登录出错", new ImageIcon("图片/error.png").getImage());
                                infoDialog.setVisible(true);
                            }
                        }
                    } else {
                        frame.getContentPane().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                        loginDialog.setVisible(false);
                        break;
                    }
                }
            }
        });
        
        //删除按钮的设置
        deleteAccountButton.setTextString("删除账户");
        deleteAccountButton.setFontColor(Color.black);
        deleteAccountButton.setHoverColor(new Color(234, 249, 255));
        deleteAccountButton.setPressColor(new Color(214, 242, 254));
        deleteAccountButton.setPreferredSize(new Dimension(0, 32));
        deleteAccountButton.setFont(new Font("楷体", Font.PLAIN, 16));
        deleteAccountButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                UserNode node = (UserNode) myJTree.getLastSelectedPathComponent();
                //选中的是用户节点才删除
                if (node.getLevel() == 1) {
                    frame.getContentPane().setCursor(new Cursor(Cursor.WAIT_CURSOR));
                    loginIn.delete();
                    model.removeNodeFromParent(node);
                    Info.loginedUser.remove(node.getUser());
                    contentJPanel.clearMailDetail();
                    frame.getContentPane().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                } else {
                    infoDialog.setDialog("你选中的不是用户节点", new ImageIcon("图片/error.png").getImage());
                    infoDialog.setVisible(true);
                }
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
        setPreferredSize(new Dimension(frame.getWidth() * 2 / 10, 0));
        infoDialog.setBounds();
    }
    
    //设置背景颜色
    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
    
    //设置背景图片
    public void setBackgroundImage(Image backgroundImage) {
        this.backgroundImage = backgroundImage;
    }
    
    //给添加myJTree监听器
    public void setMyJTree() {
        myJTree.addTreeSelectionListener(new TreeSelectionListener() {
            
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                UserNode node = (UserNode) myJTree.getLastSelectedPathComponent();
                if (node == null) {
                    return;
                }
                
                UserNode parentNode = (UserNode) node.getParent();
                
                //选择的用户节点，切换用户
                if (node.getLevel() == 1) {
                    Info.currUser = node.getUser();
                }
                
                //选择的用户子节点，设置用户，并添加邮件信息
                if (node.getLevel() == 2) {
                    frame.getContentPane().setCursor(new Cursor(Cursor.WAIT_CURSOR));
                    Info.currUser = parentNode.getUser();
                    String path = "用户/" + Info.currUser.getNickName() + " " + Info.currUser.getEmail() + "/" + node.getNickNameString();
                    List<Mail> mails = getInfo.loadInfo(path);
                    contentJPanel.clearMailDetail();
                    for (Mail mail: mails) {
                        contentJPanel.addMailDetail(mail);
                    }
                    if (node.getNickNameString().equals("发件箱")) {
                        contentJPanel.setEditable(false);
                        Info.currUser.setState(User.OUTBOX);
                    }
                    if (node.getNickNameString().equals("收件箱")) {
                        contentJPanel.setEditable(true);
                        Info.currUser.setState(User.INBOX);
                    }
                    contentJPanel.updateUI();
                    frame.getContentPane().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            }
        });
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
