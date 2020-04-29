package com.VSS.udpechoclientui;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.*;

public class ChatClient extends JFrame {
    private JPanel rootPanel;
    private JPanel mainPanel;
    private JButton sendButton;
    private JTextField chatInput;
    private JLabel onlineHeader;
    private JPanel chatContentPanel;
    private JPanel onlinePanel;
    private JTextArea messageArea;

    public ChatClient(String server, String usrName, int port) throws SocketException, UnknownHostException {
        add(rootPanel);

        setTitle("User " + usrName + " at " + server + ":" + port);
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        InetAddress servAddr = InetAddress.getByName(server);
        DatagramSocket client = new DatagramSocket();
        Thread receiveThread = new Thread(new ReceiveThread(messageArea, client));

        receiveThread.start();

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String msg = chatInput.getText();
                if (msg.length() > 0) {
                    sendMessage(client, servAddr, port, msg);
                }
            }
        });

        chatInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String msg = chatInput.getText();
                if (msg.length() > 0) {
                    sendMessage(client, servAddr, port, msg);
                }
            }
        });

    }

    private void sendMessage(DatagramSocket client, InetAddress servAddr, int port, String msg) {
        if (msg.equals("exit")) {
            dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }
        chatInput.setText("");
        byte[] data = msg.getBytes();
        DatagramPacket clientPacket = new DatagramPacket(data, data.length, servAddr, port);
        try {
            client.send(clientPacket);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
