package com.VSS.tcpechouiclient;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ReceiveThread extends Thread {
    JTextArea _textArea;
    Socket _client;
    String _server;
    int _port;

    public ReceiveThread(JTextArea textArea, Socket client) {
        _textArea = textArea;
        _client = client;
    }

    public void run() {
        try {
            System.out.println("Connected to " + _client.getInetAddress().getHostAddress() + " at port " + _client.getPort() + " from local port " + _client.getLocalPort());
            BufferedReader fromServer = new BufferedReader(new InputStreamReader(_client.getInputStream()));

            while(true) {
                String echo = fromServer.readLine();
                _textArea.append(echo + "\n");
                if (echo.equals("exit")) {
                    break;
                }
            }
            _client.close();
            System.out.println("Closed client socket");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
