package it.polimi.ingsw.network.server;

import java.net.Socket;

public class SocketClientConnection implements ClientConnection, Runnable{
    Server server;
    Socket socket;

    public SocketClientConnection(Server server, Socket socket) {
        this.server = server;
        this.socket = socket;
    }

    public void run(){

    }
}
