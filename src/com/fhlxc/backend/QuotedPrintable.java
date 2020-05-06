package com.fhlxc.backend;

import java.io.ByteArrayOutputStream;

/**
* @author Xingchao Long
* @date 2019/02/30 21:02:11
* @ClassName QuotedPrintable
* @Description 解码quoted-printable
*/

public class QuotedPrintable {
    public final static String qpDecoding(String str, String charset) {
        if (str == null) {
            return "";
        }
        try {
            str = str.replaceAll("=\n", "");
            byte[] bytes = str.getBytes(charset);
            for (int i = 0; i < bytes.length; i++) {
                byte b = bytes[i];
                if (b != 95) {
                    bytes[i] = b;
                } else {
                    bytes[i] = 32;
                }
            }
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            for (int i = 0; i < bytes.length; i++) {
                int b = bytes[i];
                if (b == '=') {
                    try {
                        int u = Character.digit((char) bytes[++i], 16);
                        int l = Character.digit((char) bytes[++i], 16);
                        if (u == -1 || l == -1) {
                            continue;
                        }
                        buffer.write((char) ((u << 4) + l));
                    } catch (ArrayIndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }
                } else {
                    buffer.write(b);
                }
            }
            return new String(buffer.toByteArray(), charset);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /*public static void main(String ... args) {
        String a = "=BC=DA=A5=DF=A7Q=B3=A1=B8=A8=AE=E6=A1G[=AEi=C4=FD=B3]=ADp]HELLO INDUSTRY 4.0=A1A=A4u=B7~4.0=AE=C9=A5N=A5=BF=A6=A1=B1=D2=B0=CA=A1COLILY x OYA presents";
        String b = qpDecoding(a, "big5");
        System.out.println(b);
    }*/
}
