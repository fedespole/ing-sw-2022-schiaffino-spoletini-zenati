package it.polimi.ingsw;

import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.server.Server;

import java.io.IOException;
import java.util.Scanner;
import it.polimi.ingsw.common.ANSIcolors.ANSI;
/**
 *This class is the main application, it asks the user whether he wants to join as a client or to run a server,
 * then it runs the correspondent main class
 */
public class App {

    public static void main( String[] args ) throws IOException {
        System.out.println(ANSI.CYAN_BOLD+  "Welcome to Eryantis!" + ANSI.RESET);
        System.out.println( "> Type Server or Client: ");
        System.out.print(ANSI.GREEN+  "> " + ANSI.RESET);
        Scanner scanner = new Scanner(System.in);
        while(true) {
            String cmd = scanner.nextLine();
            cmd = cmd.toLowerCase();
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
                    System.out.println(ANSI.RED+ "> Please enter a correct input." + ANSI.RESET);
                    System.out.print(ANSI.GREEN+  "> " + ANSI.RESET);
                }
            }

        }
    }
}
