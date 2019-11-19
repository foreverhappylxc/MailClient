package com.fhlxc.mailclientgui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.fhlxc.gui.MyJButton;
import com.fhlxc.gui.MyJLabel;

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
    //邮件的摘要集合
    private List<MailDetailJPanel> maiList;
    //背景颜色和图片
    private Color backgroundColor;
    private Image backgroundImage;
    
    public ContentJPanel(JFrame frame) {
        this.frame = frame;
        maiList = new LinkedList<MailDetailJPanel>();
        setLayout(new BorderLayout(0, 0));
        setOpaque(false);
    }
    
    //设置内容部分的大小
    public void setxSize(int width, int height) {
        this.width = width;
        this.height = height;
    }
    
    //设置上方三个按钮，发送邮件、获取邮件、删除邮件
    public void setButtons() {
        boxButton = new JPanel();
        boxButton.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        boxButton.setOpaque(false);
        MyJButton sendEMilButton = new MyJButton();
        MyJButton getEMailButton = new MyJButton();
        MyJButton deleteMailButton = new MyJButton();
        
        sendEMilButton.setHoverColor(new Color(234, 249, 255));
        sendEMilButton.setPressColor(new Color(214, 242, 254));
        sendEMilButton.setFont(new Font("楷体", Font.PLAIN, 16));
        sendEMilButton.setFontColor(Color.black);
        sendEMilButton.setTextString("发送邮件");
        sendEMilButton.setPreferredSize(new Dimension(100, height));
        sendEMilButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        
        getEMailButton.setHoverColor(new Color(234, 249, 255));
        getEMailButton.setPressColor(new Color(214, 242, 254));
        getEMailButton.setFont(new Font("楷体", Font.PLAIN, 16));
        getEMailButton.setFontColor(Color.black);
        getEMailButton.setTextString("获取邮件");
        getEMailButton.setPreferredSize(new Dimension(100, height));
        getEMailButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        
        deleteMailButton.setHoverColor(new Color(234, 249, 255));
        deleteMailButton.setPressColor(new Color(214, 242, 254));
        deleteMailButton.setFont(new Font("楷体", Font.PLAIN, 16));
        deleteMailButton.setFontColor(Color.black);
        deleteMailButton.setTextString("删除邮件");
        deleteMailButton.setPreferredSize(new Dimension(100, height));
        deleteMailButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        
        boxButton.add(sendEMilButton);
        boxButton.add(getEMailButton);
        boxButton.add(deleteMailButton);
        
        this.add(boxButton, BorderLayout.NORTH);
    }
    
    //设置摘要的头部，发送者、主题、时间的文字内容
    public void setMailDetail() {
        mailDetail = new JPanel();
        mailDetail.setOpaque(false);
        mailDetail.setLayout(new VFlowLayout(FlowLayout.LEFT, 0, 0, false, false));
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUI(new MyScrollBarUI());
        scrollPane.setViewportView(mailDetail);
        scrollPane.getViewport().setOpaque(false);
        head = new JPanel();
        sender = new MyJLabel();
        subject = new MyJLabel();
        date = new MyJLabel();
        
        head.setLayout(new BorderLayout(0, 0));
        head.setOpaque(false);
        head.setPreferredSize(new Dimension(width, height));
        
        sender.setFont(new Font("宋体", Font.BOLD , 16));
        sender.setFontColor(Color.black);
        sender.setTextString("发送者");
        sender.setPreferredSize(new Dimension(width / 4, height));
        
        subject.setFont(new Font("宋体", Font.BOLD , 16));
        subject.setFontColor(Color.black);
        subject.setTextString("主题");
        
        date.setFont(new Font("宋体", Font.BOLD , 16));
        date.setFontColor(Color.black);
        date.setTextString("发送时间");
        date.setPreferredSize(new Dimension(width / 4, height));
        
        head.add(sender, BorderLayout.WEST);
        head.add(subject, BorderLayout.CENTER);
        head.add(date, BorderLayout.EAST);
        
        mailDetail.add(head);
        addMailDetail("23", "e", "43", 2);
        this.add(scrollPane, BorderLayout.CENTER);
    }
    
    //添加一封邮件的摘要信息
    public void addMailDetail(String sender, String subject, String date, int num) {
        MailDetailJPanel mailDetailJPanel = new MailDetailJPanel(width, height, sender, subject, date, num);
        mailDetail.add(mailDetailJPanel);
        maiList.add(mailDetailJPanel);
    }
    
    //邮件的文本信息
    public void setTextArea() {
        textField = new JTextArea();
        textField.setEditable(false);
        textField.setBorder(BorderFactory.createLineBorder(new Color(214, 242, 254), 1, true));
        textField.setOpaque(false);
        textField.setPreferredSize(new Dimension(0, (frame.getHeight() - 2 * height) / 2));
        
        this.add(textField, BorderLayout.SOUTH);
    }
    
    //重新设置各组件的大小
    public void resetSize() {
        textField.setPreferredSize(new Dimension(0, (frame.getHeight() - 2 * height) / 2));
        head.setPreferredSize(new Dimension(width, height));
        for (MailDetailJPanel mailDetailJPanel: maiList) {
            mailDetailJPanel.setxSize(width, height);
        }
    }
    
    public void setBackgroundImage(Image backgroundImage) {
        this.backgroundImage = backgroundImage;
    }
    
    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
    
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
