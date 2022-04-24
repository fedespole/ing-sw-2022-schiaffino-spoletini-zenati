package it.polimi.ingsw.network;

import it.polimi.ingsw.common.events.PlayerAccessEvent;
import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.server.Server;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class ServerTest extends TestCase {

    @Test
    public void ServerTest1() throws IOException {
        Server server = new Server();
        server.run();
        Client client = new Client("localhost",server.getPort());
        assertEquals(0,server.getPlayingConnection().size());
        client.getServerEvs().add(new PlayerAccessEvent(client,"Ciao"));
    }
}
