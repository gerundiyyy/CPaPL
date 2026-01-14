package org.gerundiyyy;

import java.io.*;
import java.net.*;
import java.util.HashMap;

public class ChatClient {
    private int ClientId;
    private Socket socket = null;
    private BufferedReader dis;

    ChatClient(int ClientId){
        this.ClientId = ClientId;
        try{
            initSocket();
            initReader();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void initSocket() throws IOException {
        socket = new Socket( "localhost" , 8030);
    }

    public void initReader() throws IOException {
        dis = new BufferedReader(new InputStreamReader(
                socket.getInputStream()));
    }

    public void readMesssages(){
        try {
            String msg = dis.readLine();
            System.out.println(msg);
        } catch (IOException e) {
            System. out.println( "error : " + e);
        }
    }

    public int getClientId() {
        return ClientId;
    }

    public void setClientId(int ClientId) {
        this.ClientId = ClientId;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public BufferedReader getDis() {
        return dis;
    }

    public void setDis(BufferedReader dis) {
        this.dis = dis;
    }
}
