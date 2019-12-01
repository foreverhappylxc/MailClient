package com.fhlxc.backend;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
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

import com.fhlxc.data.Info;
import com.fhlxc.entity.User;

/**
* @author Xingchao Long
* @date 2019/30/12 20:30:46
* @ClassName LoginIn
* @Description 邮箱登录的代码实现
*/

public class LoginIn {
    //暂存服务器的返回字符串
    private String info = "";
    //文件夹的路径
    private File filePath;
    //用户信息问价
    private File userInfo;
    //文件输入流
    private BufferedWriter fileWriter;
    //socket的写入写出缓冲流
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    
    //检查是否账户已登录
    public boolean check(String mail) {
        return Info.find(mail.toLowerCase());
    }
    
    //登录邮箱服务器
    public boolean login(String nickName, String mail, String pwd) {
        if (nickName.equals("")) {
            nickName = "$";
        }
        User user = new User();
        user.setNickName(nickName);
        user.setEmail(mail);
        user.setPwd(pwd);
        
        try (Socket socket = new Socket(InetAddress.getByName(user.getServerPOP3()), 110)) {
            socket.setSoTimeout(15000);
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();
            
            Reader reader = new InputStreamReader(in);
            bufferedReader = new BufferedReader(reader);
            
            Writer writer = new OutputStreamWriter(out);
            bufferedWriter = new BufferedWriter(writer);
            
            info = bufferedReader.readLine();
            
            System.out.println(info);
            
            bufferedWriter.write("user " + mail);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            System.out.println("user " + mail);
            
            info = bufferedReader.readLine();
            
            System.out.println(info);
            if (info.contains("-ERR")) {
                //System.out.println("-ERR");
                return false;
            }
            
            bufferedWriter.write("pass " + pwd);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            System.out.println("pass " + pwd);
            
            info = bufferedReader.readLine();
            
            System.out.println(info);
            if (info.contains("-ERR")) {
                //System.out.println("-ERR");
                return false;
            }
            
            //创建文件夹
            filePath = new File("用户/" + nickName + " " + mail);
            if (!filePath.exists()) {
                filePath.mkdir();
                File inBox = new File(filePath.getPath() + "/收件箱");
                inBox.mkdir();
                File outBox = new File(filePath.getPath() + "/发件箱");
                outBox.mkdir();
            }
            
            //创建用户信息的文本文件
            userInfo = new File(filePath.getPath() + "/user.txt");
            FileOutputStream fileInputStream = new FileOutputStream(userInfo);
            Writer ww = new OutputStreamWriter(fileInputStream);
            fileWriter = new BufferedWriter(ww);
            //fileWriter.write(nickName);
            //fileWriter.newLine();
            //fileWriter.write(mail);
            //fileWriter.newLine();
            fileWriter.write(pwd);
            fileWriter.flush();
            fileWriter.close();
            ww.close();
            fileInputStream.close();
            
            //开始下载邮件
            //获取邮件的数量
            bufferedWriter.write("stat");
            bufferedWriter.newLine();
            bufferedWriter.flush();
            System.out.println("stat");
            
            info = bufferedReader.readLine();
            int num = Integer.parseInt(info.split(" ")[1]);
            for (int i = 1; i <= num; i++) {
                bufferedWriter.write("retr " + i);
                bufferedWriter.newLine();
                bufferedWriter.flush();
                System.out.println("retr " + i);
                File file = new File(filePath.getPath() + "/收件箱/" + i + ".mail");
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
                user.addMail(i);
                w.close();
            }
            
            bufferedWriter.write("quit");
            bufferedWriter.newLine();
            bufferedWriter.flush();
            System.out.println("quit");
            
            info = bufferedReader.readLine();
            
            System.out.println(info);
            
            Info.loginedUser.add(user);
            Info.currUser = user;
            //关闭流
            bufferedWriter.close();
            bufferedReader.close();
            writer.close();
            reader.close();
            in.close();
            out.close();
            return true;
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    //删除用户
    public void delete() {
        String path = "用户/" + Info.currUser.getNickName() + " " + Info.currUser.getEmail();
        File file = new File(path);
        
        //删除用户相关的所有文件
        if (file.isDirectory()) {
            File[] file2 = file.listFiles();
            for (File f: file2) {
                if (f.isDirectory()) {
                    File[] files = f.listFiles();
                    for (File ff: files) {
                        ff.delete();
                    }
                    f.delete();
                } else {
                    f.delete();
                }
            }
        }
        
        file.delete();
    }
    
    /*public static void main(String ... args) {
        LoginIn loginIn = new LoginIn();
        loginIn.login("wo", "z15881614642@163.com", "fhlxc447730");
        loginIn.delete();
    }*/
}
