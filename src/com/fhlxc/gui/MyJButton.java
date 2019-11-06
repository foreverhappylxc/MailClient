package com.fhlxc.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

/**
* @author Xingchao Long
* @date 2019/04/02 17:04:29
* @ClassName MyJButton
* @Description 自定义的JButton类
*/

@SuppressWarnings("serial")
public class MyJButton extends JButton {
    //按钮最原本的颜色
    private Color color;
    //鼠标悬停时，按钮的颜色
    private Color hoverColor;
    //鼠标按住按钮时的颜色
    private Color pressColor;
    //一个临时的变量，存放具体的颜色
    private Color tmpColor;
    //存放按钮的背景图像
    private Image imageButton;
    //存储按键文本内容
    private String textString;
    //字体的颜色
    private Color fontColor;
    //存放字体
    private Font font;
    
    //实现MouseListener接口
    private class MyMouseListener implements MouseListener {
        
        //注解标识这是一个重写的函数
        @Override
        //鼠标在按钮上松开时会自动调用这个函数，使按钮的背景色变为其本来的颜色
        public void mouseReleased(MouseEvent e) {
            //当是鼠标左键点击时，重设背景色
            if (e.getButton() == MouseEvent.BUTTON1) {
                tmpColor = color;
            }
        }
        
        @Override
        //鼠标在按钮上按住时会自动调用这个函数，使按钮的背景色变为按住时的颜色
        public void mousePressed(MouseEvent e) {
            //当是鼠标左键点击时，重设背景色
            if (e.getButton() == MouseEvent.BUTTON1) {
                tmpColor = pressColor;
            }
        }
        
        @Override
        //鼠标离开按钮时会自动调用这个函数，使按钮的背景色变为原本的颜色
        public void mouseExited(MouseEvent e) {
            tmpColor = color;
        }
        
        @Override
        //鼠标进入按钮时会自动调用这个函数，使按钮的背景色变为鼠标悬停按钮上的颜色
        public void mouseEntered(MouseEvent e) {
            tmpColor = hoverColor;
        }
        
        @Override
        //鼠标点击时自动调用这个函数
        public void mouseClicked(MouseEvent e) {
            //当是鼠标左键点击时，重设背景色
            if (e.getButton() == MouseEvent.BUTTON1) {
                tmpColor = color;
            }
        }
    }

    //设置按钮原本的颜色
    public void setColor(Color color) {
        this.color = color;
        tmpColor = color;
    }

    //设置鼠标悬停时的按钮的颜色
    public void setHoverColor(Color hoverColor) {
        this.hoverColor = hoverColor;
    }

    //设置按钮按住时的按钮的颜色
    public void setPressColor(Color pressColor) {
        this.pressColor = pressColor;
    }
    
    public void setTmpColor(Color tmpColor) {
        this.tmpColor = tmpColor;
    }

    //设置按钮的背景图片
    public void setImageButton(Image imageButton) {
        this.imageButton = imageButton;
    }
    
    //设置按钮的背景图像
    public void setTextString(String textString) {
        this.textString = textString;
    }
    
    //设置字体的颜色
    public void setFontColor(Color fontColor) {
        this.fontColor = fontColor;
    }
    
    //设置字体
    public void setFont(Font font) {
        this.font = font;
    }
    
    //构造函数，初始化textString，以免其为null，报错并添加鼠标监听器
    public MyJButton() {
        textString = "";
        //添加鼠标监听器，以实现按钮的不同状态的颜色变化
        this.addMouseListener(new MyMouseListener());
        setBorderPainted(false);
    }
    
  //重载了JButton的paintComponent函数，每次需要绘制时会主动调用，例如显示调用repaint函数、窗口状态发生变化……
    public void paintComponent(Graphics g) {
        //调用父类的这个函数，绘制默认的样式，因为有时可能只是改变局部的样式，并不需要改变整个样式
        super.paintComponent(g);
        //不绘制背景图片的时候，imageButton为空
        if (imageButton != null) {
            /**
            * 功能 绘制背景图片
            * 参数1 需要绘制的内容
            * 参数2、3 定位，即从哪个位置开始绘制图片
            * 参数4、5 大小，设置绘制区域的大小，会自动根据这个区域大小来绘制图片，即与图片大小无关
            * 参数6 背景颜色，即在什么样的背景上绘制该图片
            * 参数7 观察者，有什么用目前暂时还不清楚
            */
            g.drawImage(imageButton, 0, 0, this.getWidth(), this.getHeight(), tmpColor, this);
        } 
        if (imageButton == null) {
            //设置画笔的颜色
            g.setColor(tmpColor);
            //绘制矩形框，会填充举行中间，参数1、2是定位，参数3、4是大小
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
        }
        
        //获取字体的对象
        FontMetrics metrics = g.getFontMetrics(font);
        //获取文本的宽度
        int strWidth = metrics.stringWidth(textString);
        //获取文字的高度
        int strHeight = metrics.getHeight();
        
        //设置字体的颜色
        g.setColor(fontColor);
        //设置字体
        g.setFont(font);
        //绘制文本，并使文字居中
        g.drawString(textString, (this.getWidth() - strWidth) / 2, (this.getHeight() - strHeight) / 2 + metrics.getAscent());
    }
}
