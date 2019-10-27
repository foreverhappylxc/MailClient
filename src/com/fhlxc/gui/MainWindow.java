package com.fhlxc.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;

/**
* @author Xingchao Long
* @date 2019/21/23 13:21:32
* @ClassName MainWindow
* @Description 邮件客户端的主界面
*/

public class MainWindow extends JFrame {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainWindow frame = new MainWindow();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public MainWindow() {
        setBackground(Color.WHITE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1017, 695);
        contentPane = new JPanel();
        contentPane.setBackground(Color.WHITE);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
        
        JPanel panel = new JPanel();
        panel.setBorder(null);
        panel.setBackground(Color.WHITE);
        contentPane.add(panel, BorderLayout.NORTH);
        
        JButton getMail = new JButton("获取邮件");
        getMail.setBackground(Color.WHITE);
        
        JButton newMail = new JButton("新建邮件");
        newMail.setBackground(Color.WHITE);
        GroupLayout gl_panel = new GroupLayout(panel);
        gl_panel.setHorizontalGroup(
            gl_panel.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panel.createSequentialGroup()
                    .addComponent(getMail)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(newMail)
                    .addContainerGap(833, Short.MAX_VALUE))
        );
        gl_panel.setVerticalGroup(
            gl_panel.createParallelGroup(Alignment.TRAILING)
                .addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
                    .addComponent(getMail, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
                    .addComponent(newMail, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
        );
        panel.setLayout(gl_panel);
    }
}
