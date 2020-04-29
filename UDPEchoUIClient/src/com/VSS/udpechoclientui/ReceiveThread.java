package com.VSS.udpechoclientui;

import javax.swing.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ReceiveThread extends Thread {
    JTextArea _textArea;
    DatagramSocket _client;
    public ReceiveThread(JTextArea textArea, DatagramSocket client) {
        _textArea = textArea;
        _client = client;
    }

    public void run() {
        DatagramPacket clientPacket = new DatagramPacket(new byte[256], 256);

        while(true) {
            try {
                _client.receive(clientPacket);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            byte[] data = clientPacket.getData();
            String msg = new String(data, 0, clientPacket.getLength());
            _textArea.append(msg + "\n");
            if (msg.equals("exit")) {
                break;
            }
        }
    }
}
