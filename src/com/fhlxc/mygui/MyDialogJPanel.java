package com.fhlxc.mygui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JPanel;

import com.fhlxc.mygui.MyJButton;
import com.fhlxc.mygui.MyJLabel;

/**
* @author Xingchao Long
* @date 2019/55/04 20:55:58
* @ClassName MyDialogJPanel.java
* @Description 对话框的自定义JPanel
*/

@SuppressWarnings("serial")
public class MyDialogJPanel extends JPanel {
    //对话框背景颜色
    private Color backgroundColor;
    //对话框背景图片
    private Image backgroundImage;
    //对话框标题栏的高度
    private int titleBarHeight;
    //对话框的标题栏
    private JPanel titleBarJPanel;
    //标题栏左上角的图片
    private MyJLabel titleBarImageJLabel;
    //标题栏的文本内容
    private MyJLabel titleBarTitleJLabel;
    //标题栏的关闭按钮
    private MyJButton titleBarButton;
    //对话框中间部分内容
    private JPanel contentJPanel;
    //对话框中间左侧图片
    private MyJLabel contentImageJLabel;
    //对话框中间右侧文字内容
    private MyJLabel contentTextJLabel;
    //对话框底部按钮部分
    private JPanel buttonJPanel;
    //对话框的确定按钮
    private MyJButton okButton;
    //对话框的取消按钮
    private MyJButton cancelButton;
    //外层对话框
    private JDialog myDialog;
    
    //对话框的返回结果
    private boolean close;
    //对话框的字体
    private Font font;
    //对话框的字体颜色
    private Color fontColor;
    
    //传入外部对话框
    public void setMyDialog(JDialog myDialog) {
        this.myDialog = myDialog;
    }
    
    //重新设置组建的大小
    public void resetSize() {
        //设置确定按钮的大小
        okButton.setPreferredSize(new Dimension(myDialog.getWidth() / 2, myDialog.getHeight() / 5));
        //设置图片的大小
        contentImageJLabel.setPreferredSize(new Dimension(myDialog.getWidth() / 3 * 1, 0));
        //设置标题栏的高度
        titleBarJPanel.setPreferredSize(new Dimension(this.getWidth(), titleBarHeight));
    }
    
    //设置标题栏的宽度
    public void setTitleBarHeight(int titleBarHeight) {
        this.titleBarHeight = titleBarHeight;
    }
    
    //设置标题栏，第一个参数标题栏的左上角图像，第二个参数是标题栏的标题名
    public void setTitleBarJPanel(Image image, String title) {
        titleBarJPanel = new JPanel();
        titleBarImageJLabel = new MyJLabel();
        titleBarTitleJLabel = new MyJLabel();
        
        //设置标题栏的按钮
        titleBarJPanel.setLayout(new BorderLayout(0, 0));
        titleBarJPanel.setPreferredSize(new Dimension(this.getWidth(), titleBarHeight));
        
        //设置中间部分的左侧图片
        titleBarImageJLabel.setColor(Color.white);
        titleBarImageJLabel.setImage(image);
        
        //设置标题栏的字体、背景颜色、字体颜色、标题内容
        titleBarTitleJLabel.setFont(font);
        titleBarTitleJLabel.setColor(Color.white);
        titleBarTitleJLabel.setFontColor(fontColor);
        titleBarTitleJLabel.setTextString(title);

        //往标题栏里添加组件
        titleBarJPanel.add(titleBarImageJLabel, BorderLayout.WEST);
        titleBarJPanel.add(titleBarTitleJLabel, BorderLayout.CENTER);
        titleBarJPanel.add(titleBarButton, BorderLayout.EAST);
        
        //添加标题栏
        this.add(titleBarJPanel, BorderLayout.NORTH);
    }
    
    /**
     * @description 设置标题栏的关闭按钮
     * @param textString 按钮的文本
     * @param image 按钮的图像
     * @param color 按钮的颜色
     * @param hoverColor 鼠标悬停时按钮的颜色
     * @param pressColor 鼠标按住时按钮的颜色
     */
    public void setTitleBarButton(String textString, Image image, Color color, Color hoverColor, Color pressColor) {
        titleBarButton = new MyJButton();
        
        titleBarButton.setTextString(textString);
        titleBarButton.setColor(color);
        titleBarButton.setHoverColor(hoverColor);
        titleBarButton.setPressColor(pressColor);
        titleBarButton.setImageButton(image);
        
        //关闭对话框，返回false，不结束程序
        titleBarButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                close = false;
                myDialog.setVisible(false);
            }
        });
    }
    
    /**
     * @description 设置对话框中间的内容
     * @param textString 中间部分右侧的文本内容
     * @param image 中间部分左侧的图像
     * @param font 中间部分右侧的字体样式
     * @param fontColor 中间部分右侧的字体颜色
     */
    public void setContentJPanel(String textString, Image image, Font font, Color fontColor) {
        contentJPanel = new JPanel();
        contentImageJLabel = new MyJLabel();
        contentTextJLabel = new MyJLabel();
        
        contentJPanel.setLayout(new BorderLayout(0, 0));
        
        //设置图像部分
        contentImageJLabel.setColor(backgroundColor);
        contentImageJLabel.setPreferredSize(new Dimension(myDialog.getWidth() / 3 * 1, 0));
        contentImageJLabel.setImage(image);
        
        //设置文本部分
        contentTextJLabel.setColor(backgroundColor);
        contentTextJLabel.setFont(font);
        contentTextJLabel.setFontColor(fontColor);
        contentTextJLabel.setTextString(textString);
        
        //添加图像和文本
        contentJPanel.add(contentImageJLabel, BorderLayout.WEST);
        contentJPanel.add(contentTextJLabel, BorderLayout.CENTER);
        
        //添加中间部分到对话框
        this.add(contentJPanel, BorderLayout.CENTER);
    }
    
    //设置对话框底部的按钮
    public void setButtonJPanel() {
        buttonJPanel= new JPanel();
        
        buttonJPanel.setLayout(new BorderLayout(0, 0));
        
        //添加确定和取消按钮到底部
        buttonJPanel.add(okButton, BorderLayout.WEST);
        buttonJPanel.add(cancelButton, BorderLayout.CENTER);
        
        //添加底部到对话框
        this.add(buttonJPanel, BorderLayout.SOUTH);
    }
    
    /**
     * @description 设置确定按钮
     * @param textString 设置按钮的文本
     * @param image 设置按钮的图像
     * @param font 设置字体的样式
     * @param fontColor 设置字体的颜色
     * @param color 设置按钮颜色
     * @param hoverColor 设置鼠标悬停时按钮的颜色
     * @param pressColor 设置鼠标按住时按钮的颜色
     */
    public void setOkButton(String textString, Image image, Font font, Color fontColor, Color color, Color hoverColor, Color pressColor) {
        okButton = new MyJButton();
        okButton.setPreferredSize(new Dimension(myDialog.getWidth() / 2, myDialog.getHeight() / 5)); 
        okButton.setTextString(textString);
        okButton.setImageButton(image);
        okButton.setFont(font);
        okButton.setFontColor(fontColor);
        okButton.setColor(color);
        okButton.setPressColor(pressColor);
        okButton.setHoverColor(hoverColor);
        
        //添加监听器，使得窗口消失，返回true
        okButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                close = true;
                myDialog.setVisible(false);
            }
        });
    }
    
    /**
     * @description 设置取消按钮
     * @param textString 设置按钮的文本
     * @param image 设置按钮的图像
     * @param font 设置字体的样式
     * @param fontColor 设置字体的颜色
     * @param color 设置按钮颜色
     * @param hoverColor 设置鼠标悬停时按钮的颜色
     * @param pressColor 设置鼠标按住时按钮的颜色
     */
    public void setCancelButton(String textString, Image image, Font font, Color fontColor, Color color, Color hoverColor, Color pressColor) {
        cancelButton = new MyJButton();
        cancelButton.setTextString(textString);
        cancelButton.setImageButton(image);
        cancelButton.setFont(font);
        cancelButton.setFontColor(fontColor);
        cancelButton.setColor(color);
        cancelButton.setPressColor(pressColor);
        cancelButton.setHoverColor(hoverColor);
        
        //添加监听器，使得窗口消失，返回false
        cancelButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                close = false;
                myDialog.setVisible(false);
            }
        });
    }
    
    //设置对话框背景颜色
    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
    
    //设置对话框的背景图像
    public void setBackgroundImage(Image backgroundImage) {
        this.backgroundImage = backgroundImage;
    }
    
    //设置对话框标题栏的字体样式
    public void setFont(Font font) {
        this.font = font;
    }
    
    //设置对话框标题栏的字体颜色
    public void setFontColor(Color fontColor) {
        this.fontColor = fontColor;
    }
    
    //对话框关闭后的返回值
    public boolean getClose() {
        return close;
    }
    
    //构造函数设置边框布局
    public MyDialogJPanel() {
        setLayout(new BorderLayout(0, 0));
        setOpaque(false);
    }
    
    //重载绘制函数，实现背景图片和文字
    public void paintComponent(Graphics g) {
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), backgroundColor, this);
        } 
        if (backgroundImage == null && backgroundColor != null) {
            g.setColor(backgroundColor);
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
        }
    }
}
