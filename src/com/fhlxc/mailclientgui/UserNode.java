package com.fhlxc.mailclientgui;

import javax.swing.ImageIcon;
import javax.swing.tree.DefaultMutableTreeNode;

/**
* @author Xingchao Long
* @date 2019/47/07 12:47:55
* @ClassName UserNode
* @Description 存放用户的节点
*/

@SuppressWarnings("serial")
public class UserNode extends DefaultMutableTreeNode {
    //用户的昵称
    private String nickNameString;
    //用户的邮箱信息
    private String mailAccountString;
    //用户的图片，暂不实现
    private ImageIcon icon;
    
    //用户第一个根节点的设置
    public UserNode(String nickNameString) {
        super();
        this.nickNameString = nickNameString;
    }
    
    //用于之后的账户节点设置
    public UserNode(String nickNameString, String mailAccountString, ImageIcon icon) {
        super();
        this.nickNameString = nickNameString;
        this.mailAccountString = mailAccountString;
        this.icon = icon;
    }

    //一系列的get/set函数
    public String getNickNameString() {
        return nickNameString;
    }

    public void setNickNameString(String nickNameString) {
        this.nickNameString = nickNameString;
    }

    public String getMailAccountString() {
        return mailAccountString;
    }

    public void setMailAccountString(String mailAccountString) {
        this.mailAccountString = mailAccountString;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }
}
