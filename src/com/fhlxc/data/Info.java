package com.fhlxc.data;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import com.fhlxc.entity.User;

/**
* @author Xingchao Long
* @date 2019/05/28 12:05:49
* @ClassName Info
* @Description 存储已经登录的用户
*/

public class Info {
    //记录已登录的用户
    public static List<User> loginedUser = new ArrayList<>();
    //记录目前选中的用户
    public static User currUser;
    //解码
    public static Base64.Decoder decoder = Base64.getDecoder();
    public static Base64.Encoder encoder = Base64.getEncoder();
    
    //查找用户是否已登录
    public static boolean find(String mail) {
        for (User user: loginedUser) {
            if (user.getEmail().toLowerCase().equals(mail)) {
                return true;
            }
        }
        return false;
    }
}
