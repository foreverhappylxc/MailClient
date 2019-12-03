package com.fhlxc.mailclientgui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import com.fhlxc.backend.OperateMail;
import com.fhlxc.data.Info;
import com.fhlxc.entity.Mail;
import com.fhlxc.mygui.MyJButton;
import com.fhlxc.mygui.MyJLabel;

/**
* @author Xingchao Long
* @date 2019/26/10 17:26:40
* @ClassName ContentJPanel
* @Description 存放右侧邮件相关内容的面板,遗留问题，MailDetail悬停颜色改变问题未解决
*/

@SuppressWarnings("serial")
public class ContentJPanel extends JPanel {
    //传入的窗口
    private JFrame frame;
    //存放一组按钮，即发送、删除、获取按钮
    private JPanel boxButton;
    //存放邮箱摘要信息的面板
    private JPanel mailDetail;
    //邮件的文本内容
    private JTextArea textField;
    //标明发送者一列
    private MyJLabel sender;
    //标明主题信息一列
    private MyJLabel subject;
    //标明时间信息一列
    private MyJLabel date;
    //邮件信息头一列，存放发送者、时间、主题
    private JPanel head;
    //邮件摘要内容的宽度和高度
    private int width;
    private int height;
    //当前选中的邮件细节面板
    private MailDetailJPanel currMailDetailJPanel;
    //当前用户的所有邮件信息
    private List<MailDetailJPanel> maiList;
    //背景颜色和图片
    private Color backgroundColor;
    private Image backgroundImage;
    //操作邮件的对象
    private OperateMail operateMail;
    
    //上方三个按钮
    private MyJButton sendEMilButton;
    private MyJButton getEMailButton;
    private MyJButton deleteMailButton;
    
    private JScrollPane scrollPane1;
    private JDialog sendDialog;
    private SendJPanel sendJPanel;
    private InfoDialog infoDialog;
    
    private final String[] WEEK = {"Mon,", "Tue,", "Wen,", "Thu,", "Fri,", "Sat,", "Sun,"};
    
    
    //构造函数初始化
    public ContentJPanel(JFrame frame) {
        this.frame = frame;
        maiList = new LinkedList<MailDetailJPanel>();
        head = new JPanel();
        sender = new MyJLabel();
        subject = new MyJLabel();
        date = new MyJLabel();
        operateMail = new OperateMail();
        infoDialog = new InfoDialog(frame);
        setLayout(new BorderLayout(0, 0));
        setOpaque(false);
        //登录对话框
        sendDialog = new JDialog();
        sendJPanel = new SendJPanel();
        sendDialog.setUndecorated(true);
        sendDialog.setModal(true);
        //将对话框传给对话框主界面
        sendJPanel.setMyDialog(sendDialog);
        //设置对话框的标题栏的高度
        sendJPanel.setTitleBarHeight(32);
        //设置对话框的字体样式，指的是标题栏的字体
        sendJPanel.setFont(new Font("宋体", Font.PLAIN, 15));
        //设置对话框标题栏的字体颜色
        sendJPanel.setFontColor(Color.black);
        //设置对话框的背景颜色
        sendJPanel.setBackgroundColor(Color.white);
        //设置对话框标题栏的关闭按钮
        sendJPanel.setTitleBarButton("", new ImageIcon("图片/close.png").getImage(), Color.white, new Color(234, 249, 255), new Color(214, 242, 254));
        //设置标题栏左上角图标和标题内容
        if (Info.currUser != null) {
            sendJPanel.setTitleBarJPanel(new ImageIcon("图片/startup.png").getImage(), Info.currUser.getNickName() + " " + Info.currUser.getEmail());
        } else {
            sendJPanel.setTitleBarJPanel(new ImageIcon("图片/startup.png").getImage(), "发送邮件");
        }
        sendJPanel.setContentJPanel();
        //设置下方确定和取消按钮，参数和设置按钮的参数是一样的
        sendJPanel.setOkButton("发送", null, new Font("宋体", Font.PLAIN, 20), Color.black, Color.white, new Color(234, 249, 255), new Color(214, 242, 254));
        sendJPanel.setCancelButton("取消", null, new Font("宋体", Font.PLAIN, 20), Color.black, Color.white, new Color(234, 249, 255), new Color(214, 242, 254));
        //组合对话框的下层按钮，一定要放在ok和cancel button之后
        sendJPanel.setButtonJPanel();
        sendDialog.setContentPane(sendJPanel);
    }
    
    //设置内容部分的大小
    public void setxSize(int width, int height) {
        this.width = width;
        this.height = height;
    }
    
    //设置获取按钮可按与不可按
    public void setEditable(boolean editable) {
        getEMailButton.setEnabled(editable);
    }
    
    //设置上方三个按钮，发送邮件、获取邮件、删除邮件
    public void setButtons() {
        boxButton = new JPanel();
        boxButton.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        boxButton.setOpaque(false);
        sendEMilButton = new MyJButton();
        getEMailButton = new MyJButton();
        deleteMailButton = new MyJButton();
        
        //设置发送按钮
        sendEMilButton.setHoverColor(new Color(234, 249, 255));
        sendEMilButton.setPressColor(new Color(214, 242, 254));
        sendEMilButton.setFont(new Font("楷体", Font.PLAIN, 16));
        sendEMilButton.setFontColor(Color.black);
        sendEMilButton.setTextString("发送邮件");
        sendEMilButton.setPreferredSize(new Dimension(100, height));
        sendEMilButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                int width = frame.getWidth() / 4 * 2;
                int height = frame.getHeight() / 4 * 3;
                //每次弹出均使对话框居中
                sendDialog.setBounds((frame.getWidth() - width) / 2 + frame.getX(), (frame.getHeight() - height) / 2 + frame.getY(), width, height);
                //每次弹出窗口，都重设对话框的各组件的大小
                sendJPanel.resetSize();
                if (Info.currUser != null) {
                    sendJPanel.setTitle(Info.currUser.getNickName() + " " + Info.currUser.getEmail());
                }
                while (true) {
                    sendDialog.setVisible(true);
                    //点击发送按钮处理
                    if (sendJPanel.getClose()) {
                        Mail mail = new Mail();
                        mail.setSubject(sendJPanel.getSubject().trim());
                        mail.setReceiver(sendJPanel.getTo().trim());
                        mail.setContent(sendJPanel.getContent());
                        Calendar cal = Calendar.getInstance();
                        int y=cal.get(Calendar.YEAR);      
                        int m=cal.get(Calendar.MONTH);      
                        int d=cal.get(Calendar.DATE);      
                        int h=cal.get(Calendar.HOUR_OF_DAY);      
                        int mi=cal.get(Calendar.MINUTE);      
                        int s=cal.get(Calendar.SECOND);
                        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
                        String Date = WEEK[w] + " " + d + " " + m + " " + y + " " + h + ":" + s + ":" + mi + " +0800";
                        mail.setDate(Date);
                        mail.setSender("<" + Info.currUser.getEmail() + ">");
                        mail.setContentType("text/plain");
                        mail.setTce("base64");
                        mail.setType("utf-8");
                        if (mail.getReceiver().matches("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$"))
                        {
                            frame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
                            operateMail.sendMail(mail);
                            frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                            break;
                        } else {
                            infoDialog.setDialog("邮件接收人错误", new ImageIcon("图片/error.png").getImage());
                            infoDialog.setVisible(true);
                        }
                    } else {
                        break;
                    }
                }
            }
        });
        
        //设置获取邮件的按钮
        getEMailButton.setHoverColor(new Color(234, 249, 255));
        getEMailButton.setPressColor(new Color(214, 242, 254));
        getEMailButton.setFont(new Font("楷体", Font.PLAIN, 16));
        getEMailButton.setFontColor(Color.black);
        getEMailButton.setTextString("获取邮件");
        getEMailButton.setPreferredSize(new Dimension(100, height));
        getEMailButton.addActionListener(new ActionListener() {
            
            @Override
            //获取邮件并添加到邮件信息面板中
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().setCursor(new Cursor(Cursor.WAIT_CURSOR));
                List<Mail> mails = operateMail.popMail(Info.currUser);
                for (Mail mail: mails) {
                    addMailDetail(mail);
                }
                mailDetail.updateUI();
                frame.getContentPane().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
        
        //设置邮件删除按钮
        deleteMailButton.setHoverColor(new Color(234, 249, 255));
        deleteMailButton.setPressColor(new Color(214, 242, 254));
        deleteMailButton.setFont(new Font("楷体", Font.PLAIN, 16));
        deleteMailButton.setFontColor(Color.black);
        deleteMailButton.setTextString("删除邮件");
        deleteMailButton.setPreferredSize(new Dimension(100, height));
        deleteMailButton.addActionListener(new ActionListener() {
            
            @Override
            //删除邮件信息
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().setCursor(new Cursor(Cursor.WAIT_CURSOR));
                operateMail.deleteMail(currMailDetailJPanel.getMail().getNumber());
                deleteMail();
                mailDetail.updateUI();
                frame.getContentPane().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
        
        boxButton.add(sendEMilButton);
        boxButton.add(getEMailButton);
        boxButton.add(deleteMailButton);
        
        this.add(boxButton, BorderLayout.NORTH);
    }
    
    //辅助删除邮件，重置用户列表信息，面板中的邮件信息
    private void deleteMail() {
        int n = currMailDetailJPanel.getMail().getNumber();
        Info.currUser.getMails().clear();
        for (MailDetailJPanel mail: maiList) {
            int i = mail.getMail().getNumber();
            if (i > n) {
                i = i - 1;
                mail.getMail().setNumber(i);
            }
            Info.currUser.addMail(i);
        }
        maiList.remove(currMailDetailJPanel);
        mailDetail.remove(currMailDetailJPanel);
    }
    
    //设置邮件细节的面板
    public void setMailDetail() {
        mailDetail = new JPanel();
        mailDetail.setOpaque(false);
        mailDetail.setLayout(new VFlowLayout(FlowLayout.LEFT, 0, 0, false, false));
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUI(new MyScrollBarUI(new ImageIcon("图片/up.png").getImage(), new ImageIcon("图片/down.png").getImage()));
        scrollPane.getVerticalScrollBar().setUnitIncrement(15);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        //scrollPane.getHorizontalScrollBar().setUI(new MyScrollBarUI(new ImageIcon("图片/left.png").getImage(), new ImageIcon("图片/right.png").getImage()));
        scrollPane.setViewportView(mailDetail);
        scrollPane.getViewport().setOpaque(false);
        setHead();
        //addMailDetail("<z15881614642@163.com>;1771583929@qq.com", "e", "43", 2);
        this.add(scrollPane, BorderLayout.CENTER);
    }
    
    //添加邮件的头部
    private void setHead() {
        head.setLayout(new BorderLayout(15, 0));
        head.setOpaque(false);
        head.setPreferredSize(new Dimension(width, height));
        
        sender.setFont(new Font("宋体", Font.BOLD , 16));
        sender.setFontColor(Color.black);
        sender.setTextString("发送者;接收者");
        sender.setPreferredSize(new Dimension(width / 8 * 2, height));
        sender.setLeft(true);
        
        subject.setFont(new Font("宋体", Font.BOLD , 16));
        subject.setFontColor(Color.black);
        subject.setTextString("主题");
        subject.setLeft(true);
        
        date.setFont(new Font("宋体", Font.BOLD , 16));
        date.setFontColor(Color.black);
        date.setTextString("发送时间");
        date.setPreferredSize(new Dimension(width / 9 * 2, height));
        date.setLeft(true);
        
        head.add(sender, BorderLayout.WEST);
        head.add(subject, BorderLayout.CENTER);
        head.add(date, BorderLayout.EAST);
        
        mailDetail.add(head);
    }
    
    //清空邮件面板中的邮件，并清空
    public void clearMailDetail() {
        mailDetail.removeAll();
        maiList.clear();
        mailDetail.updateUI();
        setHead();
    }
    
    //辅助点击事件
    private void clickedMail(MailDetailJPanel p) {
        if (currMailDetailJPanel == null) {
            frame.getContentPane().setCursor(new Cursor(Cursor.WAIT_CURSOR));
            currMailDetailJPanel = p;
            currMailDetailJPanel.getInfo1().setColor(new Color(214, 242, 254));
            currMailDetailJPanel.getInfo2().setColor(new Color(214, 242, 254));
            currMailDetailJPanel.getInfo3().setColor(new Color(214, 242, 254));
            currMailDetailJPanel.setOpaque(true);
            currMailDetailJPanel.setBackground(new Color(214, 242, 254));
            currMailDetailJPanel.setPlain();
            currMailDetailJPanel.updateUI();
            String s = operateMail.getContent(currMailDetailJPanel.getMail());
            textField.setText(s);
            textField.updateUI();
            scrollPane1.getVerticalScrollBar().setValue(scrollPane1.getVerticalScrollBar().getMinimum());
            frame.getContentPane().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            return;
        }
        if (p != currMailDetailJPanel) {
            frame.getContentPane().setCursor(new Cursor(Cursor.WAIT_CURSOR));
            currMailDetailJPanel.getInfo1().setColor(null);
            currMailDetailJPanel.getInfo2().setColor(null);
            currMailDetailJPanel.getInfo3().setColor(null);
            currMailDetailJPanel.setOpaque(false);
            currMailDetailJPanel.updateUI();
            currMailDetailJPanel = p;
            currMailDetailJPanel.getInfo1().setColor(new Color(214, 242, 254));
            currMailDetailJPanel.getInfo2().setColor(new Color(214, 242, 254));
            currMailDetailJPanel.getInfo3().setColor(new Color(214, 242, 254));
            currMailDetailJPanel.setOpaque(true);
            currMailDetailJPanel.setBackground(new Color(214, 242, 254));
            currMailDetailJPanel.setPlain();
            currMailDetailJPanel.updateUI();
            String s = operateMail.getContent(currMailDetailJPanel.getMail());
            textField.setText(s);
            textField.updateUI();
            scrollPane1.getVerticalScrollBar().setValue(scrollPane1.getVerticalScrollBar().getMinimum());
            frame.getContentPane().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }
    
    //添加邮件细节信息，并且添加点击事件
    public void addMailDetail(Mail mail) {
        MailDetailJPanel mailDetailJPanel = new MailDetailJPanel(width, height, mail);
        mailDetailJPanel.getInfo1().addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                MyJLabel m = (MyJLabel) e.getSource();
                MailDetailJPanel p = (MailDetailJPanel) m.getParent();
                clickedMail(p);
            }
        });;
        mailDetailJPanel.getInfo2().addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                MyJLabel m = (MyJLabel) e.getSource();
                MailDetailJPanel p = (MailDetailJPanel) m.getParent();
                
                clickedMail(p);
            }
        });;
        mailDetailJPanel.getInfo3().addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                MyJLabel m = (MyJLabel) e.getSource();
                MailDetailJPanel p = (MailDetailJPanel) m.getParent();
                
                clickedMail(p);
            }
        });;
        mailDetail.add(mailDetailJPanel);
        maiList.add(mailDetailJPanel);
    }
    
    //邮件的文本信息
    public void setTextArea() {
        textField = new JTextArea();
        scrollPane1 = new JScrollPane();
        textField.setEditable(false);
        textField.setLineWrap(true);
        textField.setBorder(BorderFactory.createLineBorder(new Color(214, 242, 254), 1, true));
        textField.setOpaque(false);
        textField.setFont(new Font("宋体", Font.PLAIN, 16));
        scrollPane1.setViewportView(textField);
        scrollPane1.setPreferredSize(new Dimension(0, (frame.getHeight() - 2 * height) / 2));
        scrollPane1.setBorder(null);
        scrollPane1.setOpaque(false);
        scrollPane1.getVerticalScrollBar().setUI(new MyScrollBarUI(new ImageIcon("图片/up.png").getImage(), new ImageIcon("图片/down.png").getImage()));
        scrollPane1.getVerticalScrollBar().setUnitIncrement(15);
        scrollPane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane1.getViewport().setOpaque(false);
        this.add(scrollPane1, BorderLayout.SOUTH);
    }
    
    //重新设置各组件的大小
    public void resetSize() {
        textField.setPreferredSize(new Dimension(0, (frame.getHeight() - 2 * height) / 2));
        head.setPreferredSize(new Dimension(width, height));
        for (MailDetailJPanel mailDetailJPanel: maiList) {
            mailDetailJPanel.setxSize(width, height);
        }
    }
    
    //设置背景图片
    public void setBackgroundImage(Image backgroundImage) {
        this.backgroundImage = backgroundImage;
    }
    
    //设置背景颜色
    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
    
    //重绘此面板
    public void paintComponent(Graphics g) {
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
        } 
        if (backgroundColor != null) {
            g.setColor(backgroundColor);
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
        }
    }
}
