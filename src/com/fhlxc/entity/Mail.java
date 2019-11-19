package com.fhlxc.entity;

/**
* @author Xingchao Long
* @date 2019/27/13 08:27:41
* @ClassName Mail
* @Description 邮件信息的存储
*/

public class Mail {
    private int number;
    private String sender;
    private String subject;
    private String date;
    private String contentType;
    private String content;
    
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
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
}
