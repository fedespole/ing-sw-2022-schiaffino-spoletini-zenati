package it.polimi.ingsw.network;

import it.polimi.ingsw.common.events.GameHandler;
import it.polimi.ingsw.common.events.PlayerAccessEvent;
import it.polimi.ingsw.common.events.VictoryEvent;
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
        assertEquals(0,server.getController().getGame().getPlayers().size());
        client.getClientEvs().add(new PlayerAccessEvent(client,"Ciao"));
        assertEquals(1,server.getController().getGame().getPlayers().size());
        assertEquals("Ciao",server.getController().getGame().getPlayers().get(0));
        assertNotNull(server.getPlayingConnection().get(0).getData().getOwner());
        GameHandler.calls(new VictoryEvent(server.getController(),server.getController().getGame().getPlayers().get(0)));
    }
}
