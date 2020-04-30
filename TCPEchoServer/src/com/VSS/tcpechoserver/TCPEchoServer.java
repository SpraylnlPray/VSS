package com.VSS.tcpechoserver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPEchoServer {
    static final int port = 4711;

    public static void main(String[] args) {
        ServerSocket server = null;
        int clientCount = 0;
        try {
            server = new ServerSocket(port);
            System.out.println("Started TCP server on " + port);
            while(System.in.available() == 0) {
                Socket client = server.accept();
                System.out.println("Received connection from " + client.getInetAddress() + ":" + client.getPort());
                AnswerThread answerThread = new AnswerThread(client, clientCount++);
                answerThread.start();
            }
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
        if (server != null) {
            try {
                server.close();
                System.out.println("Closed server");
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
