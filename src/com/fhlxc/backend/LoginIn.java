package com.fhlxc.backend;

import java.io.BufferedReader;
import java.io.BufferedWriter;
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

/**
* @author Xingchao Long
* @date 2019/30/12 20:30:46
* @ClassName LoginIn
* @Description 邮箱登录的代码实现
*/

public class LoginIn {
    private String info = "";
    
    public boolean login(String mail, String server, String pwd) {
        try (Socket socket = new Socket(InetAddress.getByName(server), 110)) {
            socket.setSoTimeout(15000);
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();
            
            Reader reader = new InputStreamReader(in);
            BufferedReader bufferedReader = new BufferedReader(reader);
            
            Writer writer = new OutputStreamWriter(out);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            
            info = bufferedReader.readLine();
            
            System.out.println(info);
            
            bufferedWriter.write("user " + mail);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            System.out.println("user " + mail);
            
            info = bufferedReader.readLine();
            
            System.out.println(info);
            
            if (info.contains("+OK")) {
                //System.out.println("+OK");
            }
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
            
            if (info.contains("+OK")) {
                //System.out.println("+OK");
            }
            if (info.contains("-ERR")) {
                //System.out.println("-ERR");
                return false;
            }
            
            socket.close();
            return true;
            
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static void main(String ... args) {
        LoginIn loginIn = new LoginIn();
        loginIn.login("z15881614642@163.com", "pop.163.com", "fhlxc447730");
    }
}
