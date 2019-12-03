package com.fhlxc.backend;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ImageIcon;

import com.fhlxc.data.Info;
import com.fhlxc.entity.Mail;
import com.fhlxc.entity.User;
import com.fhlxc.mailclientgui.UserNode;

/**
* @author Xingchao Long
* @date 2019/27/09 16:27:24
* @ClassName GetInfo
* @Description 获取本地文件中的信息
*/

public class GetInfo {
    //软件打开自动登录，加载已经登录的用户信息
    public List<UserNode> getUserNodes() {
        List<UserNode> userNodes = new LinkedList<>();
        String path = new String("用户");
        //遍历全部用户文件夹，添加信息
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
                    User user = new User();
                    user.setNickName(info[0]);
                    user.setEmail(info[1]);
                    
                    //读取文件，添加密码的信息
                    File f = new File(files[i].getPath() + "/user.txt");
                    try {
                        BufferedReader buff = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
                        user.setPwd(buff.readLine());
                        buff.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    
                    //添加收件箱的内容
                    File ff = new File(files[i].getPath() + "/收件箱");
                    if (ff.isDirectory()) {
                        File[] files2 = ff.listFiles();
                        for (int i1 = 0; i1 < files2.length; i1++) {
                            String fg = files2[i1].getName();
                            String fileName1 = fg.substring(0, fg.lastIndexOf("."));
                            user.addMail(Integer.parseInt(fileName1));
                        }
                    }
                    
                    int max = 0;
                    File f1 = new File(files[i].getPath() + "/发件箱");
                    if (f1.isDirectory()) {
                        File[] files2 = f1.listFiles();
                        for (int i1 = 0; i1 < files2.length; i1++) {
                            String fg = files2[i1].getName();
                            String fileName1 = fg.substring(0, fg.lastIndexOf("."));
                            if (max < Integer.parseInt(fileName1)) {
                                max = Integer.parseInt(fileName1);
                            }
                        }
                    }
                    user.setMaxOutBox(max);
                    
                    //将此用户添加到用户列表
                    Info.loginedUser.add(user);
                    //新创用户节点，并添加到链表中，返回
                    UserNode userNode = new UserNode(info[0], info[1], icon, user);
                    userNodes.add(userNode);
                }
            }
            if (Info.loginedUser.size() > 0) {
                Info.currUser = Info.loginedUser.get(0);
            }
        }
        return userNodes;
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
    
    //加载邮件的主题等内容
    public List<Mail> loadInfo(String path) {
        List<Mail> list = new ArrayList<>();
        OperateMail operateMail = new OperateMail();
        File file = new File(path);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            List<File> fileList = Arrays.asList(files);
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
            for (File f: files) {
                list.add(operateMail.parseMail(f, true));
            }
        }
        return list;
    }
    
    /*public static void main(String ... args) {
        GetInfo getInfo = new GetInfo();
        List<Mail> mail = getInfo.loadInfo("用户/$ 1771583929@qq.com/收件箱");
        for (Mail m: mail) {
            System.out.println(m.getContentType());
            System.out.println(m.getDate());
            System.out.println(m.getNumber());
            System.out.println(m.getRead());
            System.out.println(m.getReceiver());
            System.out.println(m.getSender());
            System.out.println(m.getSubject());
            System.out.println(m.getTce());
            System.out.println(m.getType());
            System.out.println();
        }
    }*/
}
