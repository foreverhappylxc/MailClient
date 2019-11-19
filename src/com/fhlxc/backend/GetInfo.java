package com.fhlxc.backend;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ImageIcon;

import com.fhlxc.mailclientgui.UserNode;

/**
* @author Xingchao Long
* @date 2019/27/09 16:27:24
* @ClassName GetInfo
* @Description 获取已登录的用户
*/

public class GetInfo {
    public List<UserNode> getUserNodes() {
        List<UserNode> userNodes = new LinkedList<>();
        String path = new String("用户");
        File file = new File(path);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    String fileName = files[i].getName();
                    String[] info = fileName.split(" ");
                    if (info.length != 2) {
                        continue;
                    }
                    ImageIcon icon = new ImageIcon(path + "/" + fileName + "/image.png");
                    UserNode userNode = new UserNode(info[0], info[1], icon);
                    userNodes.add(userNode);
                }
            }
        }
        return userNodes;
    }
}
