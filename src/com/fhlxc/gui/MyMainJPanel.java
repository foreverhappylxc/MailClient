package com.fhlxc.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
* @author Xingchao Long
* @date 2019/36/31 15:36:23
* @ClassName MyMainJPanel
* @Description 自定义的主界面，用来更方便快速的实现实现样式，例如自定义标题栏、自定义背景图片等等
*/

//消除需要添加序列号的警告
@SuppressWarnings("serial")
public class MyMainJPanel extends JPanel {
    //用来存放背景图片
    private ImageIcon icon;
    private Image image;
    //实现标题栏
    private JPanel titleBarJPanel;
    //存放最小化、最大化和关闭按钮
    private JPanel buttonJPanel;
    private MinButton minButton;
    private MaxButton maxButton;
    private CloseButton closeButton;
    //存放标题栏最左侧的图标
    private MyJLabel imageJLabel;
    //存放标题栏的标题
    private MyJLabel titleJLabel;
    //用来判断是否要更改背景图片
    private boolean background;
    //存放背景的颜色
    private Color backgroundColor;
    //标题栏的高度
    private int titleBarHeight;
    //鼠标的位置，相对于界面，而不是整个屏幕
    private int mouseX;
    private int mouseY;
    
    //实现自定义的最小化button
    private class MinButton extends JButton {
        //一个消除警告的注解，这里的警告是未使用，原因是将颜色值赋给了tmpColor，使用的是tmpColor
        @SuppressWarnings("unused")
        //按钮最原本的颜色
        private Color color;
        @SuppressWarnings("unused")
        //鼠标悬停时，按钮的颜色
        private Color hoverColor;
        @SuppressWarnings("unused")
        //鼠标按住按钮时的颜色
        private Color pressColor;
        //一个临时的变量，存放具体的颜色
        private Color tmpColor;
        //存放按钮的背景图片
        private ImageIcon iconButton;
        private Image imageButton;
        
        /**
         * @description 自定的最大化按钮
         * @param frame 传入窗口
         * @param imageString 图片的位置
         * @param color 按钮的原本的背景颜色
         * @param hoverColor 鼠标悬停时，按钮的背景色
         * @param pressColor 鼠标按住时，按钮的背景颜色
         */
        public MinButton(JFrame frame, String imageString, Color color, Color hoverColor, Color pressColor) {
            this.color = color;
            this.hoverColor = hoverColor;
            this.pressColor = pressColor;
            //图片放入iconButton中
            iconButton = new ImageIcon(imageString);
            //将获得图片变成图像，放入imageButton中
            imageButton = iconButton.getImage();
            
            tmpColor = color;
            
            //给这个按钮添加鼠标监听器，采用匿名内部类的方式，实现该接口的四个方法
            this.addMouseListener(new MouseListener() {
                
                //注解标识这是一个重写的函数
                @Override
                //鼠标在按钮上松开时会自动调用这个函数，使按钮的背景色变为其本来的颜色
                public void mouseReleased(MouseEvent e) {
                    tmpColor = color;
                    //rpaint函数会调用下面的paintComponent函数
                    repaint();
                }
                
                @Override
                //鼠标在按钮上按住时会自动调用这个函数，使按钮的背景色变为按住时的颜色
                public void mousePressed(MouseEvent e) {
                    tmpColor = pressColor;
                    repaint();
                }
                
                @Override
                //鼠标离开按钮时会自动调用这个函数，使按钮的背景色变为原本的颜色
                public void mouseExited(MouseEvent e) {
                    tmpColor = color;
                    repaint();
                }
                
                @Override
                //鼠标进入按钮时会自动调用这个函数，使按钮的背景色变为鼠标悬停按钮上的颜色
                public void mouseEntered(MouseEvent e) {
                    tmpColor = hoverColor;
                    repaint();
                }
                
                @Override
                //鼠标点击时自动调用这个函数
                public void mouseClicked(MouseEvent e) {
                    //当是鼠标左键点击时，重设背景色，并使得窗口主界面变为最小化状态
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        tmpColor = color;
                        //是窗口变为最小化状态
                        frame.setExtendedState(JFrame.ICONIFIED);;
                    }
                }
            });
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
            } else {
                //设置画笔的颜色
                g.setColor(tmpColor);
                //绘制矩形框，会填充举行中间，参数1、2是定位，参数3、4是大小
                g.fillRect(0, 0, this.getWidth(), this.getHeight());
            }
        }
    }
    
    /**
     * @description 提供设置按钮的外部接口
     * @param frame 传入窗口
     * @param imageString 图片的位置
     * @param color 按钮的原本的背景颜色
     * @param hoverColor 鼠标悬停时，按钮的背景色
     * @param pressColor 鼠标按住时，按钮的背景颜色
     */
    public void setMinButton(JFrame frame, String imageString, Color color, Color hoverColor, Color pressColor) {
        //创建最小化按钮
        minButton = new MinButton(frame, imageString, color, hoverColor, pressColor);
        //设置最小化按钮的长和宽，Dimension是一个规模的类，布局只能使用下面这个函数改变大小
        minButton.setPreferredSize(new Dimension((int)(1.5 * titleBarHeight), titleBarHeight));
        //设置按钮无边框
        minButton.setBorderPainted(false);
    }
    
    private class MaxButton extends JButton {
        //注解消除警告
        @SuppressWarnings("unused")
        //用来存放按钮原本的颜色
        private Color color;
        @SuppressWarnings("unused")
        //存放鼠标悬停时的按钮的颜色
        private Color hoverColor;
        @SuppressWarnings("unused")
        //存放鼠标按住时，按钮的颜色
        private Color pressColor;
        //暂时存放按钮的颜色
        private Color tmpColor;
        //存放按钮的图片
        private ImageIcon iconButton;
        private Image imageButton;
        
        /**
         * @description 实现自定义的最大化按钮，和最小化相似，但有不同之处
         * @param frame 传入窗口
         * @param imageString 图片的存放位置
         * @param color 按钮的原本的背景颜色
         * @param hoverColor 鼠标悬停时，按钮的背景色
         * @param pressColor 鼠标按住时，按钮的背景颜色
         */
        public MaxButton(JFrame frame, String imageString, Color color, Color hoverColor, Color pressColor) {
            this.color = color;
            this.hoverColor = hoverColor;
            this.pressColor = pressColor;
            
            iconButton = new ImageIcon(imageString);
            imageButton = iconButton.getImage();
            
            tmpColor = color;
            
            //关于这个下面这段代码具体解释和上面minButton一样
            this.addMouseListener(new MouseListener() {
                
                @Override
                public void mouseReleased(MouseEvent e) {
                    tmpColor = color;
                    repaint();
                }
                
                @Override
                public void mousePressed(MouseEvent e) {
                    tmpColor = pressColor;
                    repaint();
                }
                
                @Override
                public void mouseExited(MouseEvent e) {
                    tmpColor = color;
                    repaint();
                }
                
                @Override
                public void mouseEntered(MouseEvent e) {
                    tmpColor = hoverColor;
                    repaint();
                }
                
                @Override
                public void mouseClicked(MouseEvent e) {
                    //如果是鼠标左键点击，先判断此时窗口所处的状态，如果是最大化，就变小；如果是正常大小，就变大
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        if (frame.getExtendedState() == JFrame.NORMAL) {
                            //改变imageButton变量的值，并将最小化的按钮变为不可见，有一个bug，调不好，只好放弃，
                            //即在最大化状态，最小，之后还原，变为正常状态而不是最大状态
                            imageButton = new ImageIcon("图片/normal.png").getImage();
                            minButton.setVisible(false);
                            //改变窗口的状态，变为最大化状态，会自动调用paintComponent函数
                            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                        } else {
                            //在最大状态就变小，并将最小化按钮可见
                            imageButton = new ImageIcon("图片/max.png").getImage();
                            minButton.setVisible(true);
                            frame.setExtendedState(JFrame.NORMAL);
                        }
                    }
                }
            });
        }
        
        //重载了JButton的paintComponent函数，每次需要绘制时会主动调用，例如显示调用repaint函数、窗口状态发生变化……
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (imageButton != null) {
                //绘制图像，参数1：要绘制的图像，参数2,3：位置，参数4,5：大小，参数6：暂时不知有什么用，是一个图像观察者
                g.drawImage(imageButton, 0, 0, this.getWidth(), this.getHeight(), tmpColor, this);
            } else {
                //设置画笔的颜色
                g.setColor(tmpColor);
                //画一个被填充的矩形，参数1,2：位置，参数3,4：大小
                g.fillRect(0, 0, this.getWidth(), this.getHeight());
            }
        }
    }
    
    /**
     * @description 提供设置按钮的外部接口
     * @param frame 传入窗口
     * @param imageString 图片的位置
     * @param color 按钮的原本的背景颜色
     * @param hoverColor 鼠标悬停时，按钮的背景色
     * @param pressColor 鼠标按住时，按钮的背景颜色
     */
    public void setMaxButton(JFrame frame, String imageString, Color color, Color hoverColor, Color pressColor) {
        //创建最大化按钮
        maxButton = new MaxButton(frame, imageString, color, hoverColor, pressColor);
        //设置按钮无边框
        maxButton.setBorderPainted(false);
    }
    
    private class CloseButton extends JButton {
        @SuppressWarnings("unused")
        private Color color;
        @SuppressWarnings("unused")
        private Color hoverColor;
        @SuppressWarnings("unused")
        private Color pressColor;
        private Color tmpColor;
        private ImageIcon iconButton;
        private Image imageButton;
        
        /**
         * @description 实现自定义的最大化按钮，和最小化相似，但有不同之处
         * @param imageString 图片的存放位置
         * @param color 按钮的原本的背景颜色
         * @param hoverColor 鼠标悬停时，按钮的背景色
         * @param pressColor 鼠标按住时，按钮的背景颜色
         */
        public CloseButton(String imageString, Color color, Color hoverColor, Color pressColor) {
            this.color = color;
            this.hoverColor = hoverColor;
            this.pressColor = pressColor;
            
            iconButton = new ImageIcon(imageString);
            imageButton = iconButton.getImage();
            
            tmpColor = color;
            
            //关于这个下面这段代码具体解释和上面minButton一样
            this.addMouseListener(new MouseListener() {
                
                @Override
                public void mouseReleased(MouseEvent e) {
                    tmpColor = color;
                    repaint();
                }
                
                @Override
                public void mousePressed(MouseEvent e) {
                    tmpColor = pressColor;
                    repaint();
                }
                
                @Override
                public void mouseExited(MouseEvent e) {
                    tmpColor = color;
                    repaint();
                }
                
                @Override
                public void mouseEntered(MouseEvent e) {
                    tmpColor = hoverColor;
                    repaint();
                }
                
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        //退出程序
                        System.exit(0);
                    }
                }
            });
        }
        
        //重载了JButton的paintcomponent函数
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (imageButton != null) {
                //在一个背景色上绘制图片
                g.drawImage(imageButton, 0, 0, this.getWidth(), this.getHeight(), tmpColor, this);
            } else {
                //设置画笔的颜色
                g.setColor(tmpColor);
                //画一个被填充的矩形
                g.fillRect(0, 0, this.getWidth(), this.getHeight());
            }
        }
    }
    
    /**
     * @description 提供外部使用接口
     * @param imageString 图片的存放位置
     * @param color 按钮的原本的背景颜色
     * @param hoverColor 鼠标悬停时，按钮的背景色
     * @param pressColor 鼠标按住时，按钮的背景颜色
     */
    public void setCloseButton(String imageString, Color color, Color hoverColor, Color pressColor) {
        //创建按钮
        closeButton = new CloseButton(imageString, color, hoverColor, pressColor);
        //设置按钮无边框
        closeButton.setBorderPainted(false);
    }
    
    /**
     * @description 主界面的构造函数，首次创建对象时调用
     * @param background 是否绘制背景图片
     * @param imageString 存放背景图片的物理位置
     * @param color 主界面的背景颜色
     * @param titleBarHeight 标题栏的高
     */
    public MyMainJPanel(boolean background, String imageString, Color color, int titleBarHeight) {
        this.titleBarHeight = titleBarHeight;
        
        //设置主界面的布局为BorderLayout，可自行百度一下，布局有好处，组件的大小会根据窗口的大小自行调整
        setLayout(new BorderLayout(0, 0));
        
        //初始变量
        this.background = background;
        this.backgroundColor = color;
        
        //如果要绘制背景，就将图片内容放入image
        if (background) {
            icon = new ImageIcon(imageString);
            image = icon.getImage();
        }
    }
    
    /**
     * @description 对标题栏进行设置
     * @param frame 传入窗口
     * @param imageString
     * @param title
     */
    public void setTitleBarJPanel(JFrame frame, String imageString, String title) {
        //存放标题栏左上角的图标
        imageJLabel = new MyJLabel(true, imageString);
        //存放标题栏的标题
        titleJLabel = new MyJLabel();
        //存放标题栏
        titleBarJPanel = new JPanel();
        //存放是三个按钮
        buttonJPanel = new JPanel();
        
        //设置标题
        titleJLabel.setText(title);
        //设置文本居中
        titleJLabel.setHorizontalAlignment(SwingConstants.CENTER);
        //设置标题栏的布局为BorderLayout
        titleBarJPanel.setLayout(new BorderLayout(0, 0));
        //设置左上角图标大小，在布局中只能调用下面函数设置大小
        imageJLabel.setPreferredSize(new Dimension(titleBarHeight, titleBarHeight));
        
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
        
        //标题栏添加鼠标监听器，获取当前鼠标位置
        titleBarJPanel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                //获取鼠标所处位置的坐标
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });
        //标题栏添加鼠标移动监听器，来移动窗口位置
        titleBarJPanel.addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                //窗口处于最大状态时，不能通过鼠标拖动窗口
                if (frame.getExtendedState() != JFrame.MAXIMIZED_BOTH) {
                    //获取窗口当前位置
                    Point p = frame.getLocation();
                    //设置窗口位于相对鼠标的位置，即跟随鼠标移动
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
        if (background) {
            //绘制背景图片
            g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
        } else {
            //绘制背景颜色
            g.setColor(backgroundColor);
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
        }
    }
}
