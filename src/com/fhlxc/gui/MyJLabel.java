package com.fhlxc.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
* @author Xingchao Long
* @date 2019/35/31 20:35:05
* @ClassName MyJLable
* @Description 可修改背景图片的JLable
*/

@SuppressWarnings("serial")
//还未完全成为自定义的Jlabel，因为只能默认白色，放入图片
public class MyJLabel extends JLabel {
    //存放背景图片
    private ImageIcon icon;
    private Image image;
    //是否绘制背景图片
    private boolean background;
    
    public MyJLabel() {
        //调用父类的构造函数
        super();
    }
    
    /**
     * @description 构造函数，new时自动调用
     * @param background 是否绘制背景图片
     * @param imageString 存入图片位置
     */
    public MyJLabel(boolean background, String imageString) {
        this.background = background;
        
        //初始化图像
        icon = new ImageIcon(imageString);
        image = icon.getImage();
    }
    
    //重载父类的paintComponent函数
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (background) {
            //绘制图像，详细解释见MyMainJPanel类中
            g.drawImage(image, 0, 0, icon.getIconWidth(), icon.getIconHeight(), Color.white, this);
        } else {
            //画笔的颜色
            g.setColor(Color.white);
            //绘制填色的矩形
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
        }
    }
}
