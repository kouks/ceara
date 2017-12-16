package io.pavelkoch.ceara;

import io.pavelkoch.ceara.exceptions.ExceptionHandler;
import io.pavelkoch.ceara.mind.Core;
import io.pavelkoch.ceara.mind.State;
import io.pavelkoch.ceara.websockets.Client;
import io.pavelkoch.ceara.websockets.models.YourMoveEventBag;

import java.net.URI;
import java.util.Scanner;

public class Ceara {
    /**
     * The glorious bot name.
     */
    public static final String NAME = "Ceara 0.1";

    /**
     * The web socket thread.
     */
    private static Thread socketThread;

    /**
     * The core instance.
     */
    private static Core mind;

    /**
     * The "mind" thread.
     */
    private static Thread mindThread;

    /**
     * The program entry point
     *
     * @param args The command line arguments
     */
    public static void main(String[] args) {
//        State state = new State();
//        state.setState(9, 9, "x");
//        YourMoveEventBag.Move[] moves = new YourMoveEventBag.Move[1];
//        state.setMoves(moves);
//        Core test = new Core(state);
//        test.setSide("o");
//        System.out.println(test.requestNextMove());
//        System.exit(0);

        // We request the web socket uri to connect to.
        System.out.print("Enter the web socket uri: ");
	    String uri = new Scanner(System.in).next();

	    try {
            new Ceara().bootstrap(new URI(uri));
        } catch (Exception e) {
            ExceptionHandler.render(e);
        }
    }

    /**
     * Bootstrapping the bot, creating main threads.
     *
     * @param uri The web socket uri to connect to
     */
    private void bootstrap(URI uri) {
        // We create a new thread that handles the messages from the web socket.
        socketThread = new Thread(new Client(uri));
        socketThread.start();

        // And a new thread that handles the bot "mind".
        mind = new Core(new State());
        mindThread = new Thread(mind);
        mindThread.start();
    }

    /**
     * Returns the core instance.
     */
    public static Core getMind() {
        return mind;
    }
}
