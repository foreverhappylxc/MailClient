package com.fhlxc.mailclientgui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

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
    //隐藏的信息，标识属于哪封邮件
    private int hideInfo;
    //邮件的摘要信息
    private String sender;
    private String subject;
    private String date;
    //邮件摘要信息的面板的高和宽
    private int width;
    private int height;
    
    /**
     * @description 
     * @param width
     * @param height
     * @param sender
     * @param subject
     * @param date
     * @param hideInfo
     */
    public MailDetailJPanel(int width, int height, String sender, String subject, String date, int hideInfo) {
        this.hideInfo = hideInfo;
        setOpaque(false);
        setLayout(new BorderLayout(0, 0));
        setxSize(width, height);
        setSender(sender);
        setSubject(subject);
        setDate(date);
        setBorder(BorderFactory.createLineBorder(new Color(214, 242, 254), 1, true));
    }
    
    //获取邮件细节标识的邮件
    public int getHideInfo() {
        return hideInfo;
    }
    
    //设置大小
    public void setxSize(int width, int height) {
        this.width = width;
        this.height = height;
        setPreferredSize(new Dimension(width, height));
    }
    
    //设置邮件的发送者标签
    private void setSender(String sender) {
        this.sender = sender;
        info1 = new MyJLabel();
        info1.setPreferredSize(new Dimension(width / 4, height));
        info1.setFont(new Font("宋体", Font.PLAIN, 16));
        info1.setFontColor(Color.black);
        info1.setTextString(this.sender);
        add(info1, BorderLayout.WEST);
    }
    
    //设置邮件的主题标签
    private void setSubject(String subject) {
        this.subject = subject;
        info2 = new MyJLabel();
        info2.setFont(new Font("宋体", Font.PLAIN, 16));
        info2.setFontColor(Color.black);
        info2.setTextString(this.subject);
        add(info2, BorderLayout.CENTER);
    }
    
    //设置邮件的时间标签
    private void setDate(String date) {
        this.date = date;
        info3 = new MyJLabel();
        info3.setPreferredSize(new Dimension(width / 4, height));
        info3.setFont(new Font("宋体", Font.PLAIN, 16));
        info3.setFontColor(Color.black);
        info3.setTextString(this.date);
        add(info3, BorderLayout.EAST);
    }
    
}
