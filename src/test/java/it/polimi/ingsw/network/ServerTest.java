package it.polimi.ingsw.network;

import it.polimi.ingsw.common.events.fromClientEvents.PlayerAccessEvent;
import it.polimi.ingsw.common.events.fromClientEvents.SelectedGameSetUpEvent;
import it.polimi.ingsw.model.basicgame.STATUS;
import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.server.RemoteView;
import it.polimi.ingsw.network.server.Server;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;

public class ServerTest extends TestCase {

    private Thread serverThread;

    @Test
    public void ServerTest1() throws IOException, InterruptedException {
       /* Server server = new Server();
        serverThread = new Thread(server);
        serverThread.start();
        Client client = new Client("localhost",server.getPort());
        assertEquals(0,server.getController().getGame().getPlayers().size());
        client.getClientEvs().add(new PlayerAccessEvent(client,"Ciao"));
        Thread.sleep(1000);
        assertEquals(0,client.getClientEvs().size());
        assertEquals(1,server.getController().getGame().getPlayers().size());
        assertEquals("Ciao",server.getController().getGame().getPlayers().get(0).getUsername());
        assertNotNull(server.getPlayingConnection().get(0).getData().getOwner());
        server.kills();*/
    }

    @Test
    public void ResilienceTest() throws InterruptedException, IOException {
        Server server = new Server();
        serverThread = new Thread(server);
        serverThread.start();
        Client client1 = new Client("localhost",server.getPort());
        client1.getClientEvs().add(new PlayerAccessEvent(client1,"Host"));
        Socket client1Socket = client1.getSocket();
        Thread.sleep(100);
        assertEquals(1,server.getController().getGame().getPlayers().size());
        client1.getClientEvs().add(new SelectedGameSetUpEvent(client1,3,false));
        Thread.sleep(100);
        Client client2 = new Client("localhost",server.getPort());
        client2.getClientEvs().add(new PlayerAccessEvent(client1,"Player1"));
        Thread.sleep(100);
        assertEquals(2,server.getController().getGame().getPlayers().size());
        Client client3 = new Client("localhost",server.getPort());
        client3.getClientEvs().add(new PlayerAccessEvent(client1,"Player2"));
        Thread.sleep(100);
        assertEquals(3,server.getController().getGame().getPlayers().size());
        assertNotSame(STATUS.SETUP, server.getController().getGame().getStatusGame().getStatus());
        client1.getSocket().close();
        Client client4 = new Client("localhost",server.getPort());
        client4.getClientEvs().add(new PlayerAccessEvent(client1,"Host"));
        Thread.sleep(100);
        assertEquals(3,server.getPlayingConnection().size());
        for(RemoteView remoteView:server.getPlayingConnection()){
            assertNotSame(remoteView.getClientSocket().getLocalPort(),client1Socket.getPort());
            if(remoteView.getData().getOwner().getUsername().equals("Host")){
                assertEquals(remoteView.getClientSocket().getLocalPort(),client4.getSocket().getPort());
            }
        }

    }
}
