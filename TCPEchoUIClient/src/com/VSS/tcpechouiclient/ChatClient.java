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
    private JTextArea onlineArea;

    public ChatClient(String server, String usrName, int port) throws IOException {
        add(rootPanel);

        setTitle("User " + usrName + " at " + server + ":" + port);
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Socket client = new Socket();
        client.connect(new InetSocketAddress(server, port), 1000);

        Thread receiveThread = new Thread(new ReceiveThread(messageArea, client, onlineArea));
        receiveThread.start();
        BufferedWriter toServer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));

        toServer.write("#lgn " + usrName);
        toServer.newLine();
        toServer.flush();

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = inputField.getText();
                if (input.length() > 0) {
                    sendMessage(toServer, input, usrName);
                }
            }
        });
        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = inputField.getText();
                if (input.length() > 0) {
                    sendMessage(toServer, input, usrName);
                }
            }
        });
    }

    private void sendMessage(BufferedWriter toServer, String input, String usrName) {
        inputField.setText("");
        try {
            if (input.equals("exit")) {
                toServer.write("#ext " + usrName);
                toServer.newLine();
                toServer.flush();
                dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
            }
            toServer.write(usrName + " " + input);
            toServer.newLine();
            toServer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
