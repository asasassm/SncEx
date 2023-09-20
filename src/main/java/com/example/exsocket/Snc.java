package com.example.exsocket;
// Transger가 같은 패키지 안에 있으니 exsocket안에서 찾으려니 없고 java에서 찾아야지 찾아짐


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Snc {

    public static void run(Socket socket) {
        try (BufferedReader socketIn =
                new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter socketOut =
                        new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                BufferedReader terminalIn = new BufferedReader(new InputStreamReader(System.in));
                BufferedWriter terminalOut = new BufferedWriter(new OutputStreamWriter(System.out)); // 굳이
                                                                                                     // 필요는
                                                                                                     // 없음
        ) {
            // client만 해준거
            Transfer transger = new Transfer(socketIn, terminalOut); // 입력을 받아서 터미널 아웃으로 출력할거다
            Transfer transger2 = new Transfer(terminalIn, socketOut); //

            transger.start();
            transger2.start();
            transger.join();
            transger2.join();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (IOException e) {

        }
    }

    public static void main(String[] args) {
        String host = "localhost";
        int port = 12345;
        // Reader =char단위
        boolean check = true;

        if (args.length < 2) {
            System.out.println("매개인수가 부족");
        }
        // equals가 내용을 비교하는거라 레퍼런스타입에는 무조건 써야함
        if (!args[0].equals("-l")) {
            host = args[0];
            check = false;
        }
        try {
            port = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.err.println(e);
            System.exit(1); // 1은 비정상 종료
        }
        // 구분
        if (check) {
            try (ServerSocket serverSocket = new ServerSocket(port);
                    Socket socket = serverSocket.accept();) {
                run(socket);
            } catch (IOException e) {

            }
        } else {
            // 클라이언트
            try (Socket socket = new Socket(host, port);) {
                run(socket);
            } catch (IOException e) {

            }
        }



    }
}


