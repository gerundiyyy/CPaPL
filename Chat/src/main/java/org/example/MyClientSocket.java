package org.example;

import java.io.*;
import java.net.*;
public class MyClientSocket {
    public static void main(String[] args) {
        Socket socket = null;
        try {// получение строки клиентом
            socket = new Socket( "localhost" , 8030);
            BufferedReader dis = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
            String msg = dis.readLine();
            System.out.println(msg);
        } catch (IOException e) {
            System. out.println( "error : " + e);
        }
    }
}
