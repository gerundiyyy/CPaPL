package org.example;

import java.io.*;
import java.net.*;
public class MyServerSocket {
    public static void main(String[] args) throws Exception {
        Socket s = null;
        try { // посылка строки клиенту
            ServerSocket server = new ServerSocket(8030);
            s = server.accept();
            PrintStream ps = new PrintStream(s.getOutputStream());
            ps.println( "hello !" );
            ps.flush();
            s.close(); // разрыв соединения
        } catch (IOException e) {
            System. out.println( "error : " + e);
        }
    }
}

