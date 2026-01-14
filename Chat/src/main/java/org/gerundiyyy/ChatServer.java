package org.gerundiyyy;

import java.io.*;
import java.net.*;
import java.util.Vector;

public class ChatServer {
    ServerSocket server;
    // Вектор для хранения потоков вывода всех клиентов
    private Vector<PrintStream> clientOutputStreams = new Vector<>();

    public static void main(String[] args) {
        ChatServer server = new ChatServer();
        try {
            server.initServerSocket();
            // Бесконечный цикл для принятия подключений
            while (true) {
                server.acceptClient();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void initServerSocket() throws IOException {
        server = new ServerSocket(8030);
        System.out.println("Server started on port 8030");
    }

    public void acceptClient() throws IOException {
        // Ждем нового клиента
        Socket clientSocket = server.accept();
        System.out.println("New client connected: " + clientSocket);

        // Создаем поток вывода для этого клиента и добавляем в вектор
        PrintStream ps = new PrintStream(clientSocket.getOutputStream());
        clientOutputStreams.add(ps);

        // Создаем поток для чтения сообщений от этого клиента
        Thread readerThread = new Thread(new ClientHandler(clientSocket, ps, this));
        readerThread.start();

        // Отправляем приветственное сообщение этому клиенту
        ps.println("Hello client! You are connected.");
        ps.flush();
    }

    // Метод для рассылки сообщения всем клиентам
    public void broadcastMessage(String message) {
        for (PrintStream ps : clientOutputStreams) {
            ps.println(message);
            ps.flush();
        }
    }

    // Внутренний класс для обработки сообщений от клиента
    class ClientHandler implements Runnable {
        private Socket clientSocket;
        private PrintStream ps;
        private ChatServer server;
        private BufferedReader dis;

        public ClientHandler(Socket socket, PrintStream ps, ChatServer server) throws IOException {
            this.clientSocket = socket;
            this.ps = ps;
            this.server = server;
            dis = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        }

        @Override
        public void run() {
            try {
                String message;
                while ((message = dis.readLine()) != null) {
                    System.out.println("Received from client: " + message);
                    // Рассылаем полученное сообщение всем клиентам
                    server.broadcastMessage("Client says: " + message);
                }
            } catch (IOException e) {
                System.out.println("Client disconnected: " + clientSocket);
            } finally {
                // Удаляем поток вывода при отключении клиента
                clientOutputStreams.remove(ps);
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}