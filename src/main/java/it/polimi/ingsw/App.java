package it.polimi.ingsw;

import it.polimi.ingsw.view.cli.CliView;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App {

    private void launchClient(String serverIP, int serverPort) throws IOException {
        CliView view = new CliView(serverIP, serverPort);

    }

    public static void main( String[] args ) {
        System.out.println( "Hello World!" );
    }
}
