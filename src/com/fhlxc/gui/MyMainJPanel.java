package com.fhlxc.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
* @author Xingchao Long
* @date 2019/36/31 15:36:23
* @ClassName MyMainJPanel
* @Description 自定义的主界面，用来更方便快速的实现实现样式，例如自定义标题栏、自定义背景图片等等
*/

//消除需要添加序列号的警告
@SuppressWarnings("serial")
public class MyMainJPanel extends JPanel {
    //存放主界面的背景图片
    private Image image;
    //实现标题栏
    private JPanel titleBarJPanel;
    //存放最小化、最大化和关闭按钮
    private JPanel buttonJPanel;
    private MyJButton minButton;
    private MyJButton maxButton;
    private MyJButton closeButton;
    //存放标题栏最左侧的图标
    private MyJLabel imageJLabel;
    //存放标题栏的标题
    private MyJLabel titleJLabel;
    //存放主界面的背景颜色
    private Color backgroundColor;
    //标题栏的高度
    private int titleBarHeight;
    //鼠标的位置，相对于界面，而不是整个屏幕
    private int mouseX;
    private int mouseY;
    //窗口
    private JFrame frame;
    
    private Font font;
    private Color fontColor;
    
    public void setFont(Font font) {
        this.font = font;
    }
    
    public void setFontColor(Color fontColor) {
        this.fontColor = fontColor;
    }
    
    //设置私有变量backgroundColor的值
    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
    
    //设置私有变量image的值
    public void setImage(Image image) {
        this.image = image;
    }
    
    //设置私有变量titleBarHeight的值
    public void setTitleBarHeight(int titleBarHeight) {
        this.titleBarHeight = titleBarHeight;
    }
    
    //设置私有变量frame
    public void setJFrame(JFrame frame) {
        this.frame = frame;
    }
    
    //主界面构造函数，设置主界面的布局为BorderLayout
    public MyMainJPanel() {
        //设置主界面的布局为BorderLayout，可自行百度一下，布局有好处，组件的大小会根据窗口的大小自行调整
        setLayout(new BorderLayout(0, 0));
    }
    
    /**
     * @description 提供设置按钮的外部接口
     * @param textString 按钮的文本内容
     * @param imageString 图片的位置
     * @param color 按钮的原本的背景颜色
     * @param hoverColor 鼠标悬停时，按钮的背景色
     * @param pressColor 鼠标按住时，按钮的背景颜色
     */
    public void setMinButton(String textString, Image image, Color color, Color hoverColor, Color pressColor) {
        //创建最小化按钮
        minButton = new MyJButton();
        //设置按钮的背景图片
        minButton.setImageButton(image);
        //设置按钮的原本的颜色
        minButton.setColor(color);
        //设置按钮按住时的颜色
        minButton.setPressColor(pressColor);
        //设置鼠标悬停时按钮的颜色
        minButton.setHoverColor(hoverColor);
        //设置按钮的文本内容
        minButton.setText(textString);
        //添加动作监听器，即鼠标点击这个动作会触发这个监听器
        minButton.addActionListener(new ActionListener() {
            
            @Override
            //鼠标单击时触发该事件
            public void actionPerformed(ActionEvent e) {
                frame.setExtendedState(JFrame.ICONIFIED);
            }
        });
    }
    
    
    
    /**
     * @description 提供设置按钮的外部接口
     * @param textString 按钮的文本内容
     * @param imageString 图片的位置
     * @param color 按钮的原本的背景颜色
     * @param hoverColor 鼠标悬停时，按钮的背景色
     * @param pressColor 鼠标按住时，按钮的背景颜色
     */
    public void setMaxButton(String textString, Image image, Color color, Color hoverColor, Color pressColor) {
        //创建最大化按钮
        maxButton = new MyJButton();
        //设置按钮的本来颜色
        maxButton.setColor(color);
        //设置按钮按住时的颜色
        maxButton.setPressColor(pressColor);
        //设置鼠标悬停时的按钮颜色
        maxButton.setHoverColor(hoverColor);
        //设置按钮的背景图片
        maxButton.setImageButton(new ImageIcon(image).getImage());
        //设置按钮的文本内容
        maxButton.setText(textString);
        //设置按钮的动作监听器，有动作时自动触发
        maxButton.addActionListener(new ActionListener() {
            
            @Override
            //鼠标单击时触发该事件
            public void actionPerformed(ActionEvent e) {
                if (frame.getExtendedState() == JFrame.NORMAL) {
                    //改变imageButton变量的值，并将最小化的按钮变为不可见，有一个bug，调不好，只好放弃，
                    //即在最大化状态，最小，之后还原，变为正常状态而不是最大状态
                    maxButton.setImageButton(new ImageIcon("图片/normal.png").getImage());
                    minButton.setVisible(false);
                    //改变窗口的状态，变为最大化状态，会自动调用paintComponent函数
                    frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                } else {
                    //在最大状态就变小，并将最小化按钮可见
                    maxButton.setImageButton(new ImageIcon("图片/max.png").getImage());
                    minButton.setVisible(true);
                    frame.setExtendedState(JFrame.NORMAL);
                }
            }
        });
    }
    
    /**
     * @description 提供外部使用接口
     * @param textString 按钮的文本内容
     * @param imageString 图片的存放位置
     * @param color 按钮的原本的背景颜色
     * @param hoverColor 鼠标悬停时，按钮的背景色
     * @param pressColor 鼠标按住时，按钮的背景颜色
     */
    public void setCloseButton(String textString, Image image, Color color, Color hoverColor, Color pressColor) {
        //创建按钮
        closeButton = new MyJButton();
        //设置按钮的原本颜色
        closeButton.setColor(color);
        //设置按钮按住时的颜色
        closeButton.setPressColor(pressColor);
        //设置鼠标悬停时的按钮的颜色
        closeButton.setHoverColor(hoverColor);
        //设置按钮的背景图片
        closeButton.setImageButton(image);
        //设置按钮的文本内容
        closeButton.setText(textString);
        //给按钮添加动作监听器，点击时自动触发
        closeButton.addActionListener(new ActionListener() {
            
            @Override
            //鼠标单击时触发该事件
            public void actionPerformed(ActionEvent e) {
                closeButton.setTmpColor(color);
                
                //退出整个程序
                JDialog dialog = new JDialog();
                dialog.setUndecorated(true);
                MyDialogJPanel myDialogJPanel = new MyDialogJPanel();
                int width = frame.getWidth() / 2;
                int height = frame.getHeight() / 4;
                dialog.setBounds((frame.getWidth() - width) / 2 + frame.getX(), (frame.getHeight() - height) / 2 + frame.getY(), width, height);
                dialog.setModal(true);
                myDialogJPanel.setTitleBarHeight(32);
                myDialogJPanel.setFont(new Font("宋体", Font.PLAIN, 15));
                myDialogJPanel.setFontColor(Color.black);
                myDialogJPanel.setBackgroundColor(Color.white);
                myDialogJPanel.setTitleBarButton("", new ImageIcon("图片/close.png").getImage(), Color.white, new Color(234, 249, 255), new Color(214, 242, 254));
                myDialogJPanel.setTitleBarJPanel(new ImageIcon("图片/startup.png").getImage(), "提示对话框");
                myDialogJPanel.setContentJPanel("确定关闭对话框吗？", new ImageIcon("图片/background.png").getImage(), new Font("宋体", Font.PLAIN, 20), Color.black);
                myDialogJPanel.setOkButton("Ok", null, new Font("宋体", Font.PLAIN, 20), Color.black, Color.white, new Color(234, 249, 255), new Color(214, 242, 254));
                myDialogJPanel.setCancelButton("Cancel", null, new Font("宋体", Font.PLAIN, 20), Color.black, Color.white, new Color(234, 249, 255), new Color(214, 242, 254));
                myDialogJPanel.setButtonJPanel(10);
                dialog.setContentPane(myDialogJPanel);
                dialog.setVisible(true);
                if (myDialogJPanel.getClose()) {
                    System.exit(0);
                }
            }
        });
        
    }
    
    /**
     * @description 对标题栏进行设置
     * @param imageString 标题栏的左上角图标
     * @param title 标题内容
     */
    public void setTitleBarJPanel(Image image, String title) {
        //存放标题栏左上角的图标
        imageJLabel = new MyJLabel();
        //存放标题栏的标题
        titleJLabel = new MyJLabel();
        //存放标题栏
        titleBarJPanel = new JPanel();
        //存放是三个按钮
        buttonJPanel = new JPanel();
        
        //设置标题栏的布局为BorderLayout
        titleBarJPanel.setLayout(new BorderLayout(0, 0));
        titleBarJPanel.setPreferredSize(new Dimension(this.getWidth(), titleBarHeight));
        //设置左上角图标大小，在布局中只能调用下面函数设置大小
        //imageJLabel.setPreferredSize(new Dimension(titleBarHeight, titleBarHeight));
        //设置标签的背景色
        imageJLabel.setColor(backgroundColor);
        //设置标签的文本内容
        imageJLabel.setTextString("");
        //设置标签的背景图片
        imageJLabel.setImage(image);
        
        //设置文本内容的字体
        titleJLabel.setFont(font);
        //设置标签的背景颜色
        titleJLabel.setColor(backgroundColor);
        //设置字体的颜色
        titleJLabel.setFontColor(fontColor);
        //设置标签的文本内容
        titleJLabel.setTextString(title);
        
        //设置三个按钮的布局为BorderLayout
        buttonJPanel.setLayout(new BorderLayout(0, 0));
        //往存放按钮的JPanel里放入按钮，分别放置在西、中、东三个位置
        buttonJPanel.add(minButton, BorderLayout.WEST);
        buttonJPanel.add(maxButton, BorderLayout.CENTER);
        buttonJPanel.add(closeButton, BorderLayout.EAST);
        
        //往存放标题栏的JPanel里放入图标、标题和按钮，分别放置在西、中、东三个位置
        titleBarJPanel.add(imageJLabel, BorderLayout.WEST);
        titleBarJPanel.add(titleJLabel, BorderLayout.CENTER);
        titleBarJPanel.add(buttonJPanel, BorderLayout.EAST);
        
        imageJLabel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });
        
        titleJLabel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });
        
        imageJLabel.addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                if (frame.getExtendedState() != JFrame.MAXIMIZED_BOTH) {
                    Point p = frame.getLocation();
                    frame.setLocation(p.x + e.getX() - mouseX, p.y + e.getY() - mouseY);
                }
            }
        });
        
        titleJLabel.addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                if (frame.getExtendedState() != JFrame.MAXIMIZED_BOTH) {
                    Point p = frame.getLocation();
                    frame.setLocation(p.x + e.getX() - mouseX, p.y + e.getY() - mouseY);
                }
            }
        });
        
        //在北部位置添加标题栏
        this.add(titleBarJPanel, BorderLayout.NORTH);
    }
    
    //重载JPanel的paintComponent函数
    public void paintComponent(Graphics g) {
        //调用父类的该函数
        super.paintComponent(g);
        if (image != null) {
            //绘制背景图片
            g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), backgroundColor, this);
        }
        if (image == null) {
            //绘制背景颜色
            g.setColor(backgroundColor);
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
        }
    }
}
