package com.fhlxc.mailclientgui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import com.fhlxc.entity.Mail;
import com.fhlxc.gui.MyJLabel;

/**
* @author Xingchao Long
* @date 2019/54/11 10:54:52
* @ClassName MailDetailJPanel
* @Description 存放邮件的摘要部分信息
*/

@SuppressWarnings("serial")
public class MailDetailJPanel extends JPanel {
    //邮件的发送者信息
    private MyJLabel info1;
    //邮件的主题信息
    private MyJLabel info2;
    //邮件的时间信息
    private MyJLabel info3;
    //邮件摘要信息的面板的高和宽
    private int width;
    private int height;
    //存放对应的邮件信息
    private Mail mail;
    
    /**
     * @description 
     * @param width
     * @param height
     * @param sender
     * @param subject
     * @param date
     * @param hideInfo
     */
    public MailDetailJPanel(int width, int height, Mail mail) {
        setOpaque(false);
        this.mail = mail;
        setLayout(new BorderLayout(15, 0));
        setxSize(width, height);
        setSender(mail.getSender() + ";" + mail.getReceiver());
        setSubject(mail.getSubject());
        setDate(mail.getDate());
        setBorder(BorderFactory.createLineBorder(new Color(214, 242, 254), 1, true));
        if (!mail.getRead()) {
            setBold();
        }
    }
    
    public Mail getMail() {
        return mail;
    }
    
    //邮件未读时加粗文字
    public void setBold() {
        info1.setFont(new Font("楷体", Font.BOLD, 15));
        info2.setFont(new Font("楷体", Font.BOLD, 15));
        info3.setFont(new Font("楷体", Font.BOLD, 15));
    }
    
    //邮件文字还原
    public void setPlain() {
        info1.setFont(new Font("楷体", Font.PLAIN, 15));
        info2.setFont(new Font("楷体", Font.PLAIN, 15));
        info3.setFont(new Font("楷体", Font.PLAIN, 15));
    }
    
    //获取邮件细节中的三个标签
    public MyJLabel getInfo1() {
        return info1;
    }
    
    public MyJLabel getInfo2() {
        return info2;
    }
    
    public MyJLabel getInfo3() {
        return info3;
    }
    
    //设置大小
    public void setxSize(int width, int height) {
        this.width = width;
        this.height = height;
        setPreferredSize(new Dimension(width, height));
    }
    
    //设置邮件的发送者、接受者标签
    private void setSender(String sender) {
        info1 = new MyJLabel();
        info1.setPreferredSize(new Dimension(width / 8 * 2, height));
        info1.setFont(new Font("楷体", Font.PLAIN, 15));
        info1.setFontColor(Color.black);
        info1.setLeft(true);
        info1.setTextString(sender);
        add(info1, BorderLayout.WEST);
    }
    
    //设置邮件的主题标签
    private void setSubject(String subject) {
        info2 = new MyJLabel();
        info2.setFont(new Font("楷体", Font.PLAIN, 15));
        info2.setFontColor(Color.black);
        info2.setTextString(subject);
        info2.setLeft(true);
        add(info2, BorderLayout.CENTER);
    }
    
    //设置邮件的时间标签
    private void setDate(String date) {
        info3 = new MyJLabel();
        info3.setPreferredSize(new Dimension(width / 9 * 2, height));
        info3.setFont(new Font("楷体", Font.PLAIN, 15));
        info3.setFontColor(Color.black);
        info3.setTextString(date);
        info3.setLeft(true);
        add(info3, BorderLayout.EAST);
    }
    
}
