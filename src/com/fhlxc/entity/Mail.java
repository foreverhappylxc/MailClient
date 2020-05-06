package com.fhlxc.entity;

/**
* @author Xingchao Long
* @date 2019/27/13 08:27:41
* @ClassName Mail
* @Description 邮件信息的存储
*/

public class Mail {
    //邮件的编号
    private int number;
    //邮件的发送者
    private String sender;
    private String receiver;
    //邮件的主题
    private String subject;
    //邮件发送的时间
    private String date;
    //邮件的类型
    private String contentType;
    //邮件的加密方式
    private String tce;
    //类型下方的一行
    private String type;
    //邮件是否已读
    private boolean read;
    //邮箱内容
    private String content;
    
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean getRead() {
        return read;
    }
    
    public void setRead(boolean read) {
        this.read = read;
    }
    
    public void setTce(String tce) {
        this.tce = tce;
    }
    
    public String getTce() {
        return tce;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public String getType() {
        return type;
    }
    
    public String getReceiver() {
        return receiver;
    }
    
    public String getSender() {
        return sender;
    }
    
    public void setNumber(int number) {
        this.number = number;
    }
    
    public int getNumber() {
        return this.number;
    }
    
    public void setSender(String sender) {
        this.sender = sender;
    }
    
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
    
    public String getSubject() {
        return subject;
    }
    
    public void setSubject(String subject) {
        this.subject = subject;
    }
    
    public String getDate() {
        return date;
    }
    
    public void setDate(String date) {
        this.date = date;
    }
    
    public String getContentType() {
        return contentType;
    }
    
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
