package com.VSS.tcpechouiclient;

import javax.swing.*;
import java.io.IOException;

public class TCPEchoUIClient {
    public static void main(String[] args) {
        final String server;
        final String client;
        final int port;

        server = JOptionPane.showInputDialog("Enter Server-Address", "localhost");
        client = JOptionPane.showInputDialog("Enter Username", "me");
        port = Integer.parseInt(JOptionPane.showInputDialog("Enter Port", "4711"));

        if (server == null || server.equals("")) {
            return;
        }
        if (client == null || client.equals("")) {
            return;
        }
        if (port == 0) {
            return;
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ChatClient myClient = null;
                try {
                    myClient = new ChatClient(server, client, port);
                    myClient.setVisible(true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
