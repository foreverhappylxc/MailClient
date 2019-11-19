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
    private String nickName;
    private String pwd;
    private String email;
    private String serverPOP3;
    private String serverSMTP;
    private List<Mail> mails;
    
    public void addMail(int number, String sender, String subject, String date, String contentType) {
        
    }
    
    public User() {
        mails = new ArrayList<Mail>();
    }
    
    public void addMail(Mail mail) {
        mails.add(mail);
    }
    
    public void deleteMail(int mail) {
        mails.remove(mail);
    }
    
    public List<Mail> getMails() {
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
