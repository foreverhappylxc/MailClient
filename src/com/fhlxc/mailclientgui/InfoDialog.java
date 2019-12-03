package com.fhlxc.mailclientgui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;

import com.fhlxc.mygui.MyDialogJPanel;

/**
* @author Xingchao Long
* @date 2019/54/27 15:54:01
* @ClassName InfoDialog
* @Description 提示对话框，只改变文字内容和图片
*/

public class InfoDialog {
    private JDialog dialog;
    private MyDialogJPanel myDialogJPanel;
    private JFrame frame;
    
    public InfoDialog(JFrame frame) {
        this.frame = frame;
        dialog = new JDialog();
        myDialogJPanel = new MyDialogJPanel();
        dialog.setModal(true);
        dialog.setUndecorated(true);
    }
    
    //设置对话框显示的文字和图片
    public void setDialog(String text, Image image) {
        //获取当前窗口的位置
        int width = frame.getWidth() / 2;
        int height = frame.getHeight() / 4;
        dialog.setBounds((frame.getWidth() - width) / 2 + frame.getX(), (frame.getHeight() - height) / 2 + frame.getY(), width, height);
        myDialogJPanel.setMyDialog(dialog);
        //设置对话框的标题栏的高度
        myDialogJPanel.setTitleBarHeight(32);
        //设置对话框的字体样式，指的是标题栏的字体
        myDialogJPanel.setFont(new Font("宋体", Font.PLAIN, 15));
        //设置对话框标题栏的字体颜色
        myDialogJPanel.setFontColor(Color.black);
        //设置对话框的背景颜色
        myDialogJPanel.setBackgroundColor(Color.white);
        //设置对话框标题栏的关闭按钮
        myDialogJPanel.setTitleBarButton("", new ImageIcon("图片/close.png").getImage(), Color.white, new Color(234, 249, 255), new Color(214, 242, 254));
        //设置标题栏左上角图标和标题内容
        myDialogJPanel.setTitleBarJPanel(new ImageIcon("图片/startup.png").getImage(), "提示对话框");
        //设置对话框中间部分，左边是图片，右边是文字内容（可设置字体和颜色）
        myDialogJPanel.setContentJPanel(text, image, new Font("宋体", Font.PLAIN, 20), Color.black);
        //设置下方确定和取消按钮，参数和设置按钮的参数是一样的
        myDialogJPanel.setOkButton("Ok", null, new Font("宋体", Font.PLAIN, 20), Color.black, Color.white, new Color(234, 249, 255), new Color(214, 242, 254));
        myDialogJPanel.setCancelButton("Cancel", null, new Font("宋体", Font.PLAIN, 20), Color.black, Color.white, new Color(234, 249, 255), new Color(214, 242, 254));
        //组合对话框的下层按钮，一定要放在ok和cancel button之后
        myDialogJPanel.setButtonJPanel();
        dialog.setContentPane(myDialogJPanel);
    }
    
    //设置可不可见
    public void setVisible(boolean visible) {
        dialog.setVisible(visible);
    }
    
    //重新设置大小
    public void setBounds() {
        int width = frame.getWidth() / 2;
        int height = frame.getHeight() / 4;
        dialog.setBounds((frame.getWidth() - width) / 2 + frame.getX(), (frame.getHeight() - height) / 2 + frame.getY(), width, height);
    }
    
    //得到对话框
    public JDialog getDialog() {
        return dialog;
    }
    
    //得到对话框面板
    public MyDialogJPanel getDialogJPanel() {
        return myDialogJPanel;
    }
}
