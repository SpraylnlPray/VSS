package com.VSS.tcpechouiclient;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ChatClient extends JFrame {
    private JPanel rootPanel;
    private JButton sendButton;
    private JTextField inputField;
    private JTextArea messageArea;
    private JPanel onlinePanel;
    private JLabel onlineHeader;

    public ChatClient(String server, String usrName, int port) throws IOException {
        add(rootPanel);

        setTitle("User " + usrName + " at " + server + ":" + port);
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Socket client = new Socket();
        client.connect(new InetSocketAddress(server, port), 1000);

        Thread clientThread = new Thread(new ReceiveThread(messageArea, client));
        clientThread.start();
        BufferedWriter toServer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = inputField.getText();
                if (input.length() > 0) {
                    sendMessage(toServer, input);
//                    inputField.setText("");
//                    if (input.equals("exit")) {
//                        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
//                    }
//                    try {
//                        toServer.write(input);
//                        toServer.newLine();
//                        toServer.flush();
//                    } catch (IOException ex) {
//                        System.out.println(ex.getMessage());
//                    }
                }
            }
        });
        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = inputField.getText();
                if (input.length() > 0) {
                    sendMessage(toServer, input);
//                    inputField.setText("");
//                    if (input.equals("exit")) {
//                        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
//                    }
//                    try {
//                        toServer.write(input);
//                        toServer.newLine();
//                        toServer.flush();
//                    } catch (IOException ex) {
//                        System.out.println(ex.getMessage());
//                    }
                }
            }
        });
    }

    private void sendMessage(BufferedWriter toServer, String input) {
        inputField.setText("");
        if (input.equals("exit")) {
            dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }
        try {
            toServer.write(input);
            toServer.newLine();
            toServer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
