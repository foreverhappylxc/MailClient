package com.fhlxc.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JButton;

/**
* @author Xingchao Long
* @date 2019/35/31 20:35:05
* @ClassName MyJLable
* @Description 可修改背景图片的JLable
*/

@SuppressWarnings("serial")
//还未完全成为自定义的Jlabel，因为只能默认白色，放入图片
public class MyJLabel extends JButton {
    private Image image;
    private String textString;
    private Font font;
    private Color fontColor;
    private Color color;
    
    //设置背景色
    public void setColor(Color color) {
        this.color = color;
    }
    
    //设置文本内容
    public void setTextString(String textString) {
        this.textString = textString;
    }
    
    //设置背景图片
    public void setImage(Image image) {
        this.image = image;
    }
    
    //设置字体
    public void setFont(Font font) {
        this.font = font;
    }
    
    //设置字体的颜色
    public void setFontColor(Color fontColor) {
        this.fontColor = fontColor;
    }
    
    //构造函数初始化textString，否则textString为null而不是空字符串，会报错
    public MyJLabel() {
        this.textString = "";
        setBorderPainted(false);
    }
    
    //重载父类的paintComponent函数
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            //绘制图像，详细解释见MyMainJPanel类中
            g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), color, this);
        }
        
        if (image == null) {
            //画笔的颜色
            g.setColor(color);
            //绘制填色的矩形
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
        }
        
        //获取字体的对象
        FontMetrics metrics = g.getFontMetrics(font);
        //获取文本的宽度
        int strWidth = metrics.stringWidth(textString);
        //获取文字的高度
        int strHeight = metrics.getHeight();
        
        //设置画笔颜色
        g.setColor(fontColor);
        //设置画笔的字体
        g.setFont(font);
        //绘制字符串，文字的定位和其他的定位不太一样，有一个基线，所以设置文字居中，稍微不一样
        g.drawString(textString, (this.getWidth() - strWidth) / 2, (this.getHeight() - strHeight) / 2 + metrics.getAscent());
    }
}
