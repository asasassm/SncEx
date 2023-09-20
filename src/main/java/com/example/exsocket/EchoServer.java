package com.example.exsocket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

/*
 * 멀티 커넥트 서버 여러개의 echo서버 생성
 */
public class EchoServer extends Thread {
    Socket socket;

    public EchoServer(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader reader =
                new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter writer =
                        new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {
            while (!Thread.currentThread().isInterrupted()) {
                writer.write(reader.readLine() + "\n");
                writer.flush();
            }
        } catch (IOException e) {

        }

        try {
            socket.close();
        } catch (IOException ignore) {

        }
    }

    public static void main(String[] args) {

        List<EchoServer> serverList = new LinkedList<>();
        int port = 1234;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (!Thread.currentThread().isInterrupted()) {
                Socket socket = serverSocket.accept();

                EchoServer server = new EchoServer(socket);
                serverList.add(server);
                server.start();
            }
        } catch (IOException e) {

        }

        for (EchoServer s : serverList) {
            s.interrupt();
            try {
                s.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
