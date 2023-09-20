package com.example.exsocket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class Transfer extends Thread {
    BufferedReader bufferedReader;
    BufferedWriter bufferedWriter;

    public Transfer(BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        this.bufferedReader = bufferedReader;
        this.bufferedWriter = bufferedWriter;
    }


    @Override
    public void run() {
        String line;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                bufferedWriter.write(line + "\n");
                bufferedWriter.flush();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
