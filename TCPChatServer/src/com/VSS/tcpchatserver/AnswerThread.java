package com.VSS.tcpchatserver;

import java.io.*;
import java.net.Socket;
import java.util.Vector;

public class AnswerThread extends Thread {
    private Socket _client = null;
    private final int _number;
    private BufferedReader _fromClient = null;
    private BufferedWriter _toClient = null;
    private final Vector<Socket> _globalClients;
    private final Vector<BufferedWriter> _globalOuts;
    private final Vector<String> _globalUsers;

    public AnswerThread(Socket client, int threadNumber, Vector<Socket> clients, Vector<BufferedWriter> clientOuts, Vector<String> users) {
        this._client = client;
        this._number = threadNumber;
        _globalClients = clients;
        _globalOuts = clientOuts;
        _globalUsers = users;

        System.out.println("Started new thread " + threadNumber);
        try {
            _fromClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
            _toClient = new BufferedWriter(new OutputStreamWriter(this._client.getOutputStream()));
        } catch (IOException e) {
            System.out.println("Error in constructor in Thread " + this._number + ": " + e.getMessage());
        }
    }

    public void run() {
        try {
            String msg;

            while (true) {
                msg = this._fromClient.readLine();
                System.out.println("Thread " + this._number + " received " + msg);
                if (IsSystemMessage(msg)) {
                    String name = GetName(msg);
                    if (IsLoginMessage(msg)) {
                        synchronized (_globalClients) {
                            for (String usrName : _globalUsers) {
                                if (name.equals(usrName)) {
                                    _toClient.write("#nex"); // name exists
                                    _toClient.newLine();
                                    _toClient.flush();
                                    this._client.close();
                                    this.stop();
                                    break;
                                }
                            }

                            for (String usrName : _globalUsers) {
                                _toClient.write("#add " + usrName);
                                _toClient.newLine();
                            }
                            _toClient.flush();

                            for (BufferedWriter toGlobalClient : _globalOuts) {
                                toGlobalClient.write("#lgn " + name);
                                toGlobalClient.newLine();
                                toGlobalClient.write("#add " + name);
                                toGlobalClient.newLine();
                                toGlobalClient.flush();
                            }
                            _globalClients.add(_client);
                            _globalOuts.add(_toClient);
                            _globalUsers.add(name);
                        }
                    } else if (IsExitMessage(msg)) {
                        synchronized (_globalClients) {
                            for (BufferedWriter toGlobalClient : _globalOuts) {
                                toGlobalClient.write("#ext " + name);
                                toGlobalClient.newLine();
                                toGlobalClient.flush();
                            }
                            _globalClients.remove(_client);
                            _globalOuts.remove(_toClient);
                            _globalUsers.remove(name);
                            this._client.close();
                            this.stop();
                            break;
                        }
                    }
                } else {
                    synchronized (_globalOuts) {
                        for (BufferedWriter toGlobalClient : _globalOuts) {
                            toGlobalClient.write(msg);
                            toGlobalClient.newLine();
                            toGlobalClient.flush();
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error while running Thread " + this._number + ": " + e.getMessage());
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

    private boolean IsExitMessage(String msg) {
        return msg.startsWith("#ext");
    }
}
