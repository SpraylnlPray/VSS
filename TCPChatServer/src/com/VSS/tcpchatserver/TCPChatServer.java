package com.VSS.tcpchatserver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Vector;

public class TCPChatServer {
    static final int port = 4711;
    static int clientCount = 0;

    public static void main(String[] args) {
        ServerSocket server = null;

        Vector<Socket> clients = new Vector<Socket>();
        Vector<BufferedWriter> clientOuts = new Vector<BufferedWriter>();
        Vector<String> users = new Vector<String>();

        try {
            server = new ServerSocket(port);
            server.setSoTimeout(1000);
            System.out.println("Started TCP server on " + port);
            while (System.in.available() == 0) {
                try {
                    Socket client = server.accept();
                    System.out.println("Received connection from " + client.getInetAddress() + ":" + client.getPort());
                    AnswerThread clientThread = new AnswerThread(client, clientCount++, clients, clientOuts, users);
                    clientThread.start();
                } catch (SocketTimeoutException e) {

                }
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        if (server != null) {
            try {
                server.close();
                System.out.println("Closed server");
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
