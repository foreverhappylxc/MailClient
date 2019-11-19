package com.fhlxc.mailclientgui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
* @author Xingchao Long
* @date 2019/12/07 13:12:59
* @ClassName MyJTree
* @Description 自定义树，重绘背景
*/

@SuppressWarnings("serial")
//自定义的树的结构
public class MyJTree extends JTree {
    //渲染树中的节点
    private class UserNodeRenderer extends DefaultTreeCellRenderer {
        
        public UserNodeRenderer() {
            setOpaque(false);
        }
        
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
            hasFocus = false;
            super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
            UserNode userNode = (UserNode) value;
            ImageIcon icon = new ImageIcon(userNode.getIcon() + "");
            int level = userNode.getLevel();
            icon.setImage(icon.getImage().getScaledInstance(tree.getRowHeight(), tree.getRowHeight(), Image.SCALE_DEFAULT));
            setIcon(icon);
            //渲染不同层次的节点
            switch (level) {
                case 0:
                    setFont(new Font("微软雅黑", Font.ITALIC + Font.BOLD, 14));
                    setBackgroundSelectionColor(Color.white);
                    break;
                case 1:
                    setFont(new Font("微软雅黑", Font.BOLD, 12));
                    setBackgroundSelectionColor(new Color(214, 242, 254));
                    break;
                case 2:
                    setFont(new Font("微软雅黑", Font.PLAIN, 10));
                    setBackgroundSelectionColor(new Color(234, 249, 255));
                    break;
                case 3:
                    setFont(new Font("微软雅黑", Font.PLAIN, 9));
                    break;
                default:
                    break;
            }
            //如果用户的邮箱信息为空，设置昵称
            if (userNode.getMailAccountString() == null) {
                setText(userNode.getNickNameString());
            } else {
                setText(userNode.getNickNameString() + "<" + userNode.getMailAccountString() + ">");
            }
            setIconTextGap(15);
            return this;
        }
    }
    
    //调用超类的构造函数
    public MyJTree(DefaultMutableTreeNode root) {
        super(root);
    }
    
    //设置这个树
    public void setMyJTree(int rowHeight) {
        setRowHeight(rowHeight);
        putClientProperty("JTree.lineStyle", "Horizontal");
        setOpaque(false);
        setCellRenderer(new UserNodeRenderer());
    }
}
