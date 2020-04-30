package com.VSS.tcpechoserver;

import java.io.*;
import java.net.Socket;

public class AnswerThread extends Thread {
    Socket _client = null;
    int _number;
    BufferedReader _fromClient = null;
    BufferedWriter _toClient = null;
    public AnswerThread(Socket client, int threadNumber) {
        this._client = client;
        this._number = threadNumber;
        System.out.println("Started new thread " + threadNumber);
        try {
            _fromClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
            _toClient = new BufferedWriter(new OutputStreamWriter(this._client.getOutputStream()));
        }
        catch (IOException e) {
            System.out.println("Error in constructor in Thread " + this._number + ": " + e.getMessage());
        }
    }

    public void run() {
        try {
            this._toClient.write("connected to server in thread no." + this._number);
            this._toClient.newLine();
            this._toClient.flush();
            String msg;
            do {
                msg = this._fromClient.readLine();
                System.out.println("Thread " + this._number + " received " + msg);
                this._toClient.write(msg);
                this._toClient.newLine();
                this._toClient.flush();
            } while (!msg.equals("exit"));
            this._client.close();
            System.out.println("Closed Thread " + this._number);
        }
        catch(IOException e) {
            System.out.println("Error while running Thread " + this._number + ": " + e.getMessage());
        }
    }
}
