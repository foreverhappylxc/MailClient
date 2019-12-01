package com.fhlxc.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
* @author Xingchao Long
* @date 2019/33/12 20:33:27
* @ClassName User
* @Description 用户信息的实体类
*/

public class User {
    //用户的昵称
    private String nickName;
    //用户的密码信息
    private String pwd;
    //用户的邮件信息
    private String email;
    //用户的邮箱服务器
    private String serverPOP3;
    private String serverSMTP;
    //用户的邮件列表信息
    private List<Integer> mails;
    //用于所处的状态
    private int state;
    private int maxOutBox;
    
    //用户处于收件箱状态
    public static final int INBOX = 0;
    //用户处于发件箱状态
    public static final int OUTBOX = 1;
    
    public int getMaxOutBox() {
        return maxOutBox;
    }

    public void setMaxOutBox(int maxOutBox) {
        this.maxOutBox = maxOutBox;
    }

    public User() {
        mails = new ArrayList<>();
    }
    
    public void setState(int state) {
        this.state = state;
    }
    
    public int getState() {
        return state;
    }
    
    public void addMail(Integer mail) {
        mails.add(mail);
    }
    
    public List<Integer> getMails() {
        return mails;
    }
    
    public String getNickName() {
        return nickName;
    }
    
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    
    public String getPwd() {
        return pwd;
    }
    
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
    
    public String getEmail() {
        return email;
    }
    
    //设置邮件的时候同时设置服务器名称
    public void setEmail(String email) {
        String regx = "@[\\s\\S]*\\.";
        Pattern pattern = Pattern.compile(regx);
        Matcher matcher = pattern.matcher(email);
        while (matcher.find()) {
            String s = matcher.group();
            s = s.substring(1, s.length() - 1);
            this.serverPOP3 = "pop." + s + ".com";
            this.serverSMTP = "smtp." + s + ".com";
        }
        
        this.email = email;
    }
    
    public String getServerPOP3() {
        return serverPOP3;
    }
    
    public String getServerSMTP() {
        return serverSMTP;
    }
}
