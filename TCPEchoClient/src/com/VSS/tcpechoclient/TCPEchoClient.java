package com.VSS.tcpechoclient;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public class TCPEchoClient {
    static final int port = 4711;
    static final String host = "localhost";
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            Socket client = new Socket();
            client.connect(new InetSocketAddress(host, port), 1000);
            System.out.println("Connected to " + client.getInetAddress().getHostAddress() + " at port " + client.getPort() + " from local port " + client.getLocalPort());
            BufferedWriter toServer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            BufferedReader fromServer = new BufferedReader(new InputStreamReader(client.getInputStream()));
            System.out.println("Enter string to echo");
            while(true) {
                String echo = fromServer.readLine();
                System.out.println(echo);
                if (echo.equals("exit")) {
                    break;
                }
                String input = br.readLine();
                toServer.write(input);
                toServer.newLine();
                toServer.flush();
            }
            client.close();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
