package com.fhlxc.mailclientgui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicScrollBarUI;

import com.fhlxc.mygui.MyJButton;

/**
* @author Xingchao Long
* @date 2019/36/10 18:36:14
* @ClassName ScrollBarUI
* @Description 实现自定义的滚动条
*/

public class MyScrollBarUI extends BasicScrollBarUI {
    private Image image1;
    private Image image2;
    
    public MyScrollBarUI(Image image1, Image image2) {
        this.image1 = image1;
        this.image2 = image2;
    }
    
    //设置滑轮的大小
    public Dimension getPreferredSize(JComponent c) {
        c.setPreferredSize(new Dimension(10, 0));
        return super.getPreferredSize(c);
    }
    
    //绘制轨道
    public void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        g.setColor(new Color(234, 249, 255));
        g.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
    }
    
    //绘制滚动条
    public void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        g.translate(thumbBounds.x, thumbBounds.y);
        g.setColor(new Color(103, 211, 255));
        g.fillRoundRect(0, 0, 10, thumbBounds.height - 1, 5, 5);
    }
    
    //创建向上的按钮
    public MyJButton createDecreaseButton(int orientation) {
        MyJButton upButton = new MyJButton();
        upButton.setPressColor(new Color(234, 249, 255));
        upButton.setHoverColor(new Color(214, 242, 254));
        upButton.setColor(new Color(103, 211, 255));
        upButton.setImageButton(image1);
        return upButton;
    }
    
    //创建向下的按钮
    public MyJButton createIncreaseButton(int orientation) {
        MyJButton downButton = new MyJButton();
        downButton.setPressColor(new Color(234, 249, 255));
        downButton.setHoverColor(new Color(214, 242, 254));
        downButton.setColor(new Color(103, 211, 255));
        downButton.setImageButton(image2);
        return downButton;
    }
}
