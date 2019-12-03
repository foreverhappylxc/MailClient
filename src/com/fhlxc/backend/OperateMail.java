package com.fhlxc.backend;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.fhlxc.data.Info;
import com.fhlxc.entity.Mail;
import com.fhlxc.entity.User;

/**
* @author Xingchao Long
* @date 2019/38/27 11:38:14
* @ClassName OperateMail
* @Description 操作邮件的类
*/

public class OperateMail {
    private String info;
    
    //获取邮件
    public List<Mail> popMail(User user) {
        try (Socket socket = new Socket(InetAddress.getByName(user.getServerPOP3()), 110)) {
            //新获取的邮件的列表
            List<Mail> mail = new ArrayList<>();
            socket.setSoTimeout(15000);
            //与服务器对话的输入、输出流
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();
            
            Reader reader = new InputStreamReader(in);
            BufferedReader bufferedReader = new BufferedReader(reader);
            
            Writer writer = new OutputStreamWriter(out);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            
            //下面一大段在与服务器建立连接
            info = bufferedReader.readLine();
            System.out.println(info);
            
            bufferedWriter.write("user " + user.getEmail());
            bufferedWriter.newLine();
            bufferedWriter.flush();
            System.out.println("user " + user.getEmail());
            
            info = bufferedReader.readLine();
            
            System.out.println(info);
            
            bufferedWriter.write("pass " + user.getPwd());
            bufferedWriter.newLine();
            bufferedWriter.flush();
            System.out.println("pass " + user.getPwd());
            
            info = bufferedReader.readLine();
            
            System.out.println(info);
            
            bufferedWriter.write("stat");
            bufferedWriter.newLine();
            bufferedWriter.flush();
            System.out.println("stat");
            
            info = bufferedReader.readLine();
            System.out.println(info);
            
            int num = Integer.parseInt(info.split(" ")[1]);
            
            //获取当前用户的邮箱列表
            List<Integer> mails = user.getMails();
            //获取每一封邮件，如果已经获取就跳过
            for (int i = 1; i <= num; i++) {
                if (mails.contains(i)) {
                    continue;
                }
                bufferedWriter.write("retr " + i);
                bufferedWriter.newLine();
                bufferedWriter.flush();
                System.out.println("retr " + i);
                File file = new File("用户/" + user.getNickName() + " " + user.getEmail() + "/收件箱/" + i + ".mail");
                BufferedWriter w = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
                while (true) {
                    info = bufferedReader.readLine();
                    w.write(info);
                    w.flush();
                    if (info.equals(".")) {
                        break;
                    }
                    w.newLine();
                    w.flush();
                }
                //添加该邮件信息到用户邮件列表里
                user.addMail(i);
                w.close();
                //添加解析过后的邮件到返回的邮件列表里
                mail.add(parseMail(new File("用户/" + user.getNickName() + " " + user.getEmail() + "/收件箱/" + i + ".mail"), false));
            }
            
            //放弃该连接
            bufferedWriter.write("quit");
            bufferedWriter.newLine();
            bufferedWriter.flush();
            System.out.println("quit");
            
            info = bufferedReader.readLine();
            
            System.out.println(info);
            return mail;
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private Integer f(String filename) {
        int x = filename.indexOf(".");
        String string2 = filename.substring(0,x);
        char[] cs = string2.toCharArray();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < cs.length; i++) {
            if(Character.isDigit(cs[i])) {
                builder.append(cs[i]);
            }
        }
        return Integer.parseInt(builder.toString());
    }
    
    //删除邮件操作
    public void deleteMail(int num) {
        try (Socket socket = new Socket(InetAddress.getByName(Info.currUser.getServerPOP3()), 110)) {
            socket.setSoTimeout(15000);
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();
            
            Reader reader = new InputStreamReader(in);
            BufferedReader bufferedReader = new BufferedReader(reader);
            
            Writer writer = new OutputStreamWriter(out);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            
            info = bufferedReader.readLine();
            
            System.out.println(info);
            
            bufferedWriter.write("user " + Info.currUser.getEmail());
            bufferedWriter.newLine();
            bufferedWriter.flush();
            System.out.println("user " + Info.currUser.getEmail());
            
            info = bufferedReader.readLine();
            
            System.out.println(info);
            
            bufferedWriter.write("pass " + Info.currUser.getPwd());
            bufferedWriter.newLine();
            bufferedWriter.flush();
            System.out.println("pass " + Info.currUser.getPwd());
            
            info = bufferedReader.readLine();
            
            System.out.println(info);
            
            //删除邮件
            bufferedWriter.write("dele " + num);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            System.out.println("dele " + num);
            
            info = bufferedReader.readLine();
            
            System.out.println(info);
            
            //quit执行之后，才真正执行删除操作
            bufferedWriter.write("quit");
            bufferedWriter.newLine();
            bufferedWriter.flush();
            System.out.println("quit");
            
            info = bufferedReader.readLine();
            
            System.out.println(info);
            
            //删除本地的文件，并与服务器上的编号保持一致
            String path;
            if (Info.currUser.getState() == User.INBOX) {
                path = "用户/" + Info.currUser.getNickName() + " " + Info.currUser.getEmail() + "/收件箱";
            } else {
                path = "用户/" + Info.currUser.getNickName() + " " + Info.currUser.getEmail() + "/发件箱";
                Info.currUser.setMaxOutBox(Info.currUser.getMaxOutBox() - 1);
            }
            File file = new File(path + "/" + num + ".mail");
            file.delete();
            File f = new File(path);
            if (f.isDirectory()) {
                File[] ff = f.listFiles();
                //排序
                List<File> fileList = Arrays.asList(ff);
                Collections.sort(fileList, new Comparator<File>() {
                    @Override
                    public int compare(File o1, File o2) {
                        if (o1.isDirectory() && o2.isFile())
                            return -1;
                        if (o1.isFile() && o2.isDirectory())
                            return 1;
                        Integer f1 = f(o1.getName());
                        Integer f2 = f(o2.getName());
                        return Integer.compare(f1, f2);
                    }
                });
                //遍历整个文件夹重命名部分文件
                for (File file2: ff) {
                    String fg = file2.getName();
                    String fileName1 = fg.substring(0, fg.lastIndexOf("."));
                    int n = Integer.parseInt(fileName1);
                    if (n > num) {
                        n = n - 1;
                        file2.renameTo(new File(path + "/" + n + ".mail"));
                    }
                }
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    //获取邮件的内容
    public String getContent(Mail mail) {
        String path;
        String s2 = "";
        StringBuilder stringBuilder = new StringBuilder();
        if (Info.currUser.getState() == User.INBOX) {
            path = "用户/" + Info.currUser.getNickName() + " " + Info.currUser.getEmail() + "/收件箱";
        } else {
            path = "用户/" + Info.currUser.getNickName() + " " + Info.currUser.getEmail() + "/发件箱";
        }
        
        File file = new File(path + "/" + mail.getNumber() + ".mail");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            info = reader.readLine();
            boolean error = false;
            
            if (!mail.getContentType().equals("text/plain") && !mail.getContentType().equals("multipart/alternative")) {
                stringBuilder.append("不支持的邮件类型：" + mail.getContentType());
                stringBuilder.append("\n");
                error = true;
            }
            
            //无加密的时候
            if (mail.getTce() == null) {
                mail.setTce("----");
            }
            
            if (!mail.getContentType().equals("multipart/alternative") && !mail.getTce().toLowerCase().equals("quoted-printable") && !mail.getTce().toLowerCase().equals("base64") && !mail.getTce().contains("----")) {
                stringBuilder.append("不支持的邮件格式：" + mail.getTce());
                stringBuilder.append("\n");
                error = true;
            }
            
            //跳过邮件的头部
            while (!info.equals("")) {
                info = reader.readLine();
            }
            
            info = reader.readLine();
            
            if (!error) {
                if (mail.getContentType().equals("text/plain")) {
                    
                    if (mail.getTce().equals("----")) {
                        do {
                            String s1 = new String(info.getBytes(), mail.getType());
                            stringBuilder.append(s1 + "\n");
                            info = reader.readLine();
                        } while (!info.equals("."));
                    } 
                    
                    if (mail.getTce().equals("base64")) {
                        do {
                            stringBuilder.append(info);
                            info = reader.readLine();
                        } while (!info.equals("."));
                        String s1 = stringBuilder.toString();
                        s2 = new String(Info.decoder.decode(s1), mail.getType());
                    }
                    
                    if (mail.getTce().equals("quoted-printable")) {
                        do {
                            stringBuilder.append(info);
                            info = reader.readLine();
                        } while (!info.equals("."));
                        String s1 = stringBuilder.toString();
                        s2 = QuotedPrintable.qpDecoding(s1, mail.getType());
                    }
                    reader.close();
                    return s2;
                }
                
                if (mail.getContentType().equals("multipart/alternative")) {
                    String contentType = "";
                    String charset = "";
                    String cte = "";
                    boolean read = false;
                    String mailType = "--" + mail.getType();
                    
                    do {
                        if (!read) {
                            while (!info.contains(mailType)) {
                                if (info.equals(".")) {
                                    break;
                                }
                                info = reader.readLine();
                            }
                        }
                        read = false;
                        if (info.contains(mailType)) {
                            info = reader.readLine();
                            if (info.equals(".")) {
                                break;
                            }
                            while (!info.contains("Content-Type")) {
                                if (info.equals(".")) {
                                    break;
                                }
                                info = reader.readLine();
                            }
                            if (info.equals(".")) {
                                break;
                            }
                            String[] s = info.split(";");
                            contentType = s[0].split(":")[1].trim();
                            if (s.length > 1) {
                                charset = s[1].split("=")[1].trim().replace("\"", "");
                            } else {
                                info = reader.readLine();
                                charset = info.split("=")[1].trim().replace("\"", "");
                            }
                            info = reader.readLine();
                            if (!info.equals("")) {
                                cte = info.split(":")[1].trim();
                            }
                            
                            if (!contentType.equals("text/plain")) {
                                continue;
                            }
                            
                            //加密方式不支持的时候
                            if (!cte.equals("") && (!cte.toLowerCase().equals("quoted-printable") && !cte.toLowerCase().equals("base64"))) {
                                
                                do {
                                    info = reader.readLine();
                                    
                                    if (info.equals(".")) {
                                        break;
                                    }
                                    
                                    if (info.equals(mailType)) {
                                        read = true;
                                        break;
                                    }
                                    
                                    String s1 = new String(info.getBytes(), charset);
                                    stringBuilder.append(s1);
                                } while (!info.equals("."));
                                s2 = stringBuilder.toString();
                            }
                            
                            if (cte.equals("")) {
                                do {
                                    info = reader.readLine();
                                    
                                    if (info.equals(".")) {
                                        break;
                                    }
                                    
                                    if (info.contains(mailType)) {
                                        read = true;
                                        break;
                                    }
                                    
                                    String s1 = new String(info.getBytes(), charset);
                                    stringBuilder.append(s1 + "\n");
                                } while (!info.equals("."));
                                s2 = stringBuilder.toString();
                            }
                            
                            if (cte.equals("base64")) {
                                do {
                                    info = reader.readLine();
                                    
                                    if (info.equals(".")) {
                                        break;
                                    }
                                    
                                    if (info.contains(mailType)) {
                                        read = true;
                                        break;
                                    }
                                    stringBuilder.append(info);
                                } while (!info.equals("."));
                                String s1 = stringBuilder.toString();
                                s2 = new String(Info.decoder.decode(s1), charset);
                            }
                            
                            if (cte.equals("quoted-printable")) {
                                do {
                                    info = reader.readLine();
                                    
                                    if (info.equals(".")) {
                                        break;
                                    }
                                    
                                    if (info.contains(mailType)) {
                                        read = true;
                                        break;
                                    }
                                    stringBuilder.append(info);
                                } while(!info.equals("."));
                                String s1 = stringBuilder.toString();
                                s2 = QuotedPrintable.qpDecoding(s1, charset);
                            }
                        }
                    } while (!info.equals("."));
                    reader.close();
                    return s2;
                }
            }
            
            do {
                info = reader.readLine();
                stringBuilder.append(info + "\n");
            } while (!info.equals("."));
            String s = stringBuilder.toString();
            reader.close();
            return s;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    //解析邮件的主题等信息（不含内容）
    public Mail parseMail(File f, boolean read) {
        Mail mail = new Mail();
        mail.setRead(read);
        mail.setNumber(Integer.parseInt(f.getName().substring(0, f.getName().lastIndexOf("."))));
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f)))) {
            while (true) {
                info = reader.readLine();
                info.replace("\"", "");
                
                //提取from：字段，两种情况，一种是有编码，一种是无编码
                if (info.contains("From:")) {
                    String sender;
                    info = info.substring(5).trim();
                    String[] s = info.split(" ");
                    if (s.length > 1) {
                        //两种，一种是加密的，一种是未加密的
                        if (info.contains("=?")) {
                            //ss[1]是指定的编码方式，ss[2]是加密的方式，ss[3]是文本的内容
                            String[] ss = s[0].split("\\?");
                            
                            if (ss[2].toUpperCase().equals("B")) {
                                if (ss[1].toLowerCase().equals("utf8")) {
                                    ss[1] = "utf-8";
                                }
                                sender = new String(Info.decoder.decode(ss[3]), ss[1]);
                                sender = sender + s[1];
                            } else {
                                sender = "编码不支持";
                            }
                        } else {
                            //未加密的处理
                            sender = s[0] + s[1];
                        }
                    } else {
                        //处理无昵称类型的
                        sender = s[0].trim();
                    }
                    mail.setSender(sender);
                    continue;
                }
                
                //提取主题的信息
                if (info.contains("Subject:")) {
                    String subject;
                    info = info.substring(8).trim();
                    String[] ss = info.split("\\?");
                    //无编码和有编码两种情况
                    if (ss.length > 1) {
                        if (ss[2].toUpperCase().equals("B")) {
                            if (ss[1].toLowerCase().equals("utf8")) {
                                ss[1] = "utf-8";
                            }
                            subject = new String(Info.decoder.decode(ss[3]), ss[1]);
                        } else {
                            subject = "编码不支持";
                        }
                    } else {
                        subject = ss[0];
                    }
                    mail.setSubject(subject);
                    continue;
                }
                
                //提取时间的信息
                if (info.contains("Date:")) {
                    String date;
                    info = info.substring(5).trim();
                    info = info.replace(",", "");
                    String[] dates = new String[5];
                    dates[0] = "";
                    dates[1] = "";
                    dates[2] = "";
                    dates[3] = "";
                    dates[4] = "";
                    String[] s = info.split(" ");
                    int i = 0;
                    for (String ss: s) {
                        dates[i] = ss;
                        i++;
                        if (i > 4) {
                            break;
                        }
                    }
                    date = dates[3] + " " + dates[2] + " " + dates[1] + " " + dates[0] + " " + dates[4];
                    mail.setDate(date);
                    continue;
                }
                
                //提取收件人字段，和发件人一样
                if (info.contains("To:")) {
                    String recevier;
                    info = info.substring(3).trim();
                    String[] s = info.split(" ");
                    if (s.length > 1) {
                        if (info.contains("=?")) {
                            String[] ss = s[0].split("\\?");
                            
                            if (ss[2].toUpperCase().equals("B")) {
                                if (ss[1].toLowerCase().equals("utf8")) {
                                    recevier = new String(ss[3].getBytes(ss[1]), ss[1]);
                                } else {
                                    recevier = new String(Info.decoder.decode(ss[3]), ss[1]);
                                }
                                recevier = recevier + s[1];
                            } else {
                                recevier = "编码不支持";
                            }
                        } else {
                            recevier = s[0].trim().replace("\"", "") + s[1];
                        }
                    } else {
                        recevier = s[0].trim();
                    }
                    mail.setReceiver(recevier);
                    continue;
                }
                
                //提取邮件的类型信息
                if (info.contains("Content-Type:")) {
                    info = info.substring(13).trim();
                    String[] ss = info.split(";");
                    mail.setContentType(ss[0].trim());
                    if (ss.length > 1) {
                        mail.setType(ss[1].trim().split("=")[1].replace("\"", ""));
                    } else {
                        info = reader.readLine().trim();
                        String type;
                        if (info.contains("\"")) {
                            type = info.split("\"")[1];
                        } else {
                            type = info.split("=")[1];
                        }
                        mail.setType(type);
                    }
                    continue;
                }
                
                //提取邮件的加密方式
                if (info.contains("Content-Transfer-Encoding:")) {
                    info = info.substring(26).trim();
                    mail.setTce(info);
                    continue;
                }
                
                //遇到空行或邮件结束符停下
                if (info.equals("") || info.equals(".")) {
                    break;
                }
            }
            return mail;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public void sendMail(Mail mail) {
        try (Socket socket = new Socket(InetAddress.getByName(Info.currUser.getServerSMTP()), 25)) {
            socket.setSoTimeout(15000);
            //与服务器对话的输入、输出流
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();
            
            Reader reader = new InputStreamReader(in);
            BufferedReader bufferedReader = new BufferedReader(reader);
            
            Writer writer = new OutputStreamWriter(out);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            
            info = bufferedReader.readLine();
            System.out.println(info);
            
            bufferedWriter.write("helo you");
            bufferedWriter.newLine();
            bufferedWriter.flush();
            System.out.println("helo you");
            
            info = bufferedReader.readLine();
            System.out.println(info);
            
            bufferedWriter.write("auth login");
            bufferedWriter.newLine();
            bufferedWriter.flush();
            System.out.println("auth login");
            
            info = bufferedReader.readLine();
            System.out.println(info);
            
            bufferedWriter.write(Info.encoder.encodeToString(Info.currUser.getEmail().getBytes("utf-8")));
            bufferedWriter.newLine();
            bufferedWriter.flush();
            System.out.println(Info.encoder.encodeToString(Info.currUser.getEmail().getBytes("utf-8")));
            
            info = bufferedReader.readLine();
            System.out.println(info);
            
            bufferedWriter.write(Info.encoder.encodeToString(Info.currUser.getPwd().getBytes("utf-8")));
            bufferedWriter.newLine();
            bufferedWriter.flush();
            System.out.println(Info.encoder.encodeToString(Info.currUser.getPwd().getBytes("utf-8")));
            
            info = bufferedReader.readLine();
            System.out.println(info);
            
            bufferedWriter.write("mail from:<" + Info.currUser.getEmail() + ">");
            bufferedWriter.newLine();
            bufferedWriter.flush();
            System.out.println("mail from:<" + Info.currUser.getEmail() + ">");
            
            info = bufferedReader.readLine();
            System.out.println(info);
            
            bufferedWriter.write("rcpt to:<" + mail.getReceiver() + ">");
            bufferedWriter.newLine();
            bufferedWriter.flush();
            System.out.println("rcpt to:<" + mail.getReceiver() + ">");
            
            info = bufferedReader.readLine();
            System.out.println(info);
            
            bufferedWriter.write("data");
            bufferedWriter.newLine();
            bufferedWriter.flush();
            System.out.println("data");
            
            info = bufferedReader.readLine();
            System.out.println(info);
            
            //写数据
            StringBuilder stringBuilder = new StringBuilder();
            String from = "From: " + "\"=?utf-8?B?" + Info.encoder.encodeToString(Info.currUser.getNickName().getBytes("utf-8")) + "?=\" " + mail.getSender();
            String to = "To: <" + mail.getReceiver() + ">";
            String subject = "Subject: =?utf-8?B?" + Info.encoder.encodeToString(mail.getSubject().getBytes("utf-8")) + "?=";
            String contentType = "Content-Type: " + mail.getContentType() + ";";
            String charset = "\tcharset=\"" + mail.getType() + "\"";
            String cte = "Content-Transfer-Encoding: " + mail.getTce();
            String date = "Date: " + mail.getDate();
            String content = Info.encoder.encodeToString(mail.getContent().getBytes("utf-8"));
            
            stringBuilder.append(from + "\n");
            stringBuilder.append(to + "\n");
            stringBuilder.append(subject + "\n");
            stringBuilder.append(contentType + "\n");
            stringBuilder.append(charset + "\n");
            stringBuilder.append(cte + "\n");
            stringBuilder.append(date + "\n");
            stringBuilder.append("\n");
            stringBuilder.append(content);
            //stringBuilder.append("\n.");
            
            bufferedWriter.write(stringBuilder.toString());
            bufferedWriter.newLine();
            bufferedWriter.write(".");
            bufferedWriter.newLine();
            bufferedWriter.flush();
            System.out.println(stringBuilder.toString());
            System.out.println(".");
            
            info = bufferedReader.readLine();
            System.out.println(info);
            
            bufferedWriter.write("quit");
            bufferedWriter.newLine();
            bufferedWriter.flush();
            System.out.println("quit");
            
            info = bufferedReader.readLine();
            System.out.println(info);
            
            int num = Info.currUser.getMaxOutBox() + 1;
            File file = new File("用户/" + Info.currUser.getNickName() + " " + Info.currUser.getEmail() + "/发件箱/" + num + ".mail");
            BufferedWriter writer2 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            stringBuilder.append("\n.");
            writer2.write(stringBuilder.toString());
            writer2.close();
            Info.currUser.setMaxOutBox(num);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
