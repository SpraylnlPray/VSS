package com.VSS.tcpechouiclient;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ReceiveThread extends Thread {
    JTextArea _textArea;
    JTextArea _onlineArea;
    Socket _client;

    public ReceiveThread(JTextArea textArea, Socket client, JTextArea onlineArea) {
        _textArea = textArea;
        _client = client;
        _onlineArea = onlineArea;
    }

    public void run() {
        try {
            System.out.println("Connected to " + _client.getInetAddress().getHostAddress() + " at port " + _client.getPort() + " from local port " + _client.getLocalPort());
            BufferedReader fromServer = new BufferedReader(new InputStreamReader(_client.getInputStream()));

            while (true) {
                String msgFromServer = fromServer.readLine();
                if (msgFromServer == null) {
                    break;
                }
                System.out.println("Received message " + msgFromServer);
                if (IsSystemMessage(msgFromServer)) {
                    if (IsLoginMessage(msgFromServer)) {
                        _textArea.append("User " + GetName(msgFromServer) + " connected\n");
                    } else if (IsAddMessage(msgFromServer)) {
                        _onlineArea.append(GetName(msgFromServer + "\n"));
                    } else if (IsExitMessage(msgFromServer)) {
                        _textArea.append("User " + GetName(msgFromServer) + " disconnected\n");
                    } else if (IsNameExists(msgFromServer)) {
                        _client.close();
                        System.out.println("Closed client socket");
                        break;
                    }

                } else {
                    String usrName = GetSenderName(msgFromServer);
                    String msg = GetMessage(msgFromServer);
                    System.out.println("Sender: " + usrName);
                    System.out.println("message: " + msg);
                    _textArea.append(usrName + ": " + msg + "\n");

                }
            }
            _client.close();
            System.out.println("Closed client socket");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean IsSystemMessage(String msg) {
        return msg.startsWith("#");
    }

    private boolean IsLoginMessage(String msg) {
        return msg.startsWith("#lgn");
    }

    private String GetName(String msg) {
        return msg.substring(5);
    }

    private boolean IsAddMessage(String msg) {
        return msg.startsWith("#add");
    }

    private boolean IsExitMessage(String msg) {
        return msg.startsWith("#ext");
    }

    private String GetSenderName(String msg) {
        int endIndex = msg.indexOf(" ");
        return msg.substring(0, endIndex);
    }

    private String GetMessage(String msg) {
        int start = msg.indexOf(" ");
        return msg.substring(start);
    }

    private boolean IsNameExists(String msg) {
        return msg.startsWith("#nex");
    }
}
