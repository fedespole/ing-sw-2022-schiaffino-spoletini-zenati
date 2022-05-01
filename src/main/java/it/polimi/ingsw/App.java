package it.polimi.ingsw;

import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.server.Server;

import java.io.IOException;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App {

    public static void main( String[] args ) throws IOException {

        System.out.println( "Eryantis" );
        System.out.println( "> Type server or client: " );
        System.out.println( "> ");

        Scanner scanner = new Scanner(System.in);
        while(true) {
            String cmd = scanner.nextLine();
            cmd.toLowerCase();
            switch(cmd){
                case "server" : {
                    Server.main(null);
                    break;
                }
                case "client" : {
                    Client.main(null);
                    break;
                }
                default : {
                    System.err.println("> Please enter a correct input.");
                }
            }

        }
    }
}
