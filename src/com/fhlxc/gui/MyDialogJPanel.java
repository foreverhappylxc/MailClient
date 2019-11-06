package com.fhlxc.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

/**
* @author Xingchao Long
* @date 2019/55/04 20:55:58
* @ClassName MyDialogJPanel.java
* @Description 对话框的自定义JPanel
*/

@SuppressWarnings("serial")
public class MyDialogJPanel extends JPanel {
    private Color backgroundColor;
    private Image backgroundImage;
    private int titleBarHeight;
    private JPanel titleBarJPanel;
    private MyJLabel titleBarImageJLabel;
    private MyJLabel titleBarTitleJLabel;
    private MyJButton titleBarButton;
    private JPanel contentJPanel;
    private MyJLabel contentImageJLabel;
    private MyJLabel contentTextJLabel;
    private JPanel buttonJPanel;
    private MyJButton okButton;
    private MyJButton cancelButton;
    
    private boolean close;
    private Font font;
    private Color fontColor;
    
    public void setTitleBarHeight(int titleBarHeight) {
        this.titleBarHeight = titleBarHeight;
    }
    
    public void setTitleBarJPanel(Image image, String title) {
        titleBarJPanel = new JPanel();
        titleBarImageJLabel = new MyJLabel();
        titleBarTitleJLabel = new MyJLabel();
        
        titleBarJPanel.setLayout(new BorderLayout(0, 0));
        titleBarJPanel.setPreferredSize(new Dimension(this.getWidth(), titleBarHeight));
        
        titleBarImageJLabel.setColor(Color.white);
        titleBarImageJLabel.setImage(image);
        
        titleBarTitleJLabel.setFont(font);
        titleBarTitleJLabel.setColor(Color.white);
        titleBarTitleJLabel.setFontColor(fontColor);
        titleBarTitleJLabel.setTextString(title);

        
        titleBarJPanel.add(titleBarImageJLabel, BorderLayout.WEST);
        titleBarJPanel.add(titleBarTitleJLabel, BorderLayout.CENTER);
        titleBarJPanel.add(titleBarButton, BorderLayout.EAST);
        
        this.add(titleBarJPanel, BorderLayout.NORTH);
    }
    
    public void setTitleBarButton(String textString, Image image, Color color, Color hoverColor, Color pressColor) {
        titleBarButton = new MyJButton();
        
        titleBarButton.setTextString(textString);
        titleBarButton.setColor(color);
        titleBarButton.setHoverColor(hoverColor);
        titleBarButton.setPressColor(pressColor);
        titleBarButton.setImageButton(image);
    }
    
    public void setContentJPanel(String textString, Image image, Font font, Color fontColor) {
        contentJPanel = new JPanel();
        contentImageJLabel = new MyJLabel();
        contentTextJLabel = new MyJLabel();
        
        GridBagLayout gridBagLayout = new GridBagLayout();
        contentJPanel.setBackground(Color.RED);
        contentJPanel.setPreferredSize(new Dimension(0, 50));
        contentJPanel.setLayout(gridBagLayout);
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        
        contentImageJLabel.setColor(backgroundColor);
        contentImageJLabel.setImage(image);
        
        contentTextJLabel.setColor(backgroundColor);
        contentTextJLabel.setFont(font);
        contentTextJLabel.setFontColor(fontColor);
        contentTextJLabel.setTextString(textString);
        
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight = 1;
        contentJPanel.add(contentImageJLabel, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.gridheight = 6;
        contentJPanel.add(contentTextJLabel, gridBagConstraints);
        this.add(contentJPanel, BorderLayout.CENTER);
    }
    
    public void setButtonJPanel(int bottom) {
        buttonJPanel= new JPanel();
        
        buttonJPanel.setLayout(new BorderLayout(0, 0));
        
        buttonJPanel.add(okButton, BorderLayout.WEST);
        buttonJPanel.add(cancelButton, BorderLayout.CENTER);
        
        this.add(buttonJPanel, BorderLayout.SOUTH);
    }
    
    public void setOkButton(String textString, Image image, Font font, Color fontColor, Color color, Color hoverColor, Color pressColor) {
        okButton = new MyJButton();
        okButton.setTextString(textString);
        okButton.setImageButton(image);
        okButton.setFont(font);
        okButton.setFontColor(fontColor);
        okButton.setColor(color);
        okButton.setPressColor(pressColor);
        okButton.setHoverColor(hoverColor);
        
        okButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                close = true;
            }
        });
    }
    
    public void setCancelButton(String textString, Image image, Font font, Color fontColor, Color color, Color hoverColor, Color pressColor) {
        cancelButton = new MyJButton();
        cancelButton.setTextString(textString);
        cancelButton.setImageButton(image);
        cancelButton.setFont(font);
        cancelButton.setFontColor(fontColor);
        cancelButton.setColor(color);
        cancelButton.setPressColor(pressColor);
        cancelButton.setHoverColor(hoverColor);
        
        cancelButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                close = false;
            }
        });
    }
    
    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
    
    public void setBackgroundImage(Image backgroundImage) {
        this.backgroundImage = backgroundImage;
    }
    
    public void setFont(Font font) {
        this.font = font;
    }
    
    public void setFontColor(Color fontColor) {
        this.fontColor = fontColor;
    }
    
    public boolean getClose() {
        return close;
    }
    
    public MyDialogJPanel() {
        setLayout(new BorderLayout(0, 0));
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), backgroundColor, this);
        } else {
            g.setColor(backgroundColor);
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
        }
    }
}
