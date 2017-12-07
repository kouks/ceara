package io.pavelkoch.ceara.websockets;

import com.google.gson.Gson;
import io.pavelkoch.ceara.Ceara;
import io.pavelkoch.ceara.exceptions.ExceptionHandler;
import io.pavelkoch.ceara.mind.State;
import io.pavelkoch.ceara.websockets.models.*;

import javax.websocket.MessageHandler;
import javax.websocket.Session;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Handler implements MessageHandler.Whole<String> {
    /**
     * The web socket session.
     */
    private final Session session;

    /**
     * The json library instance.
     */
    private final Gson gson;

    /**
     * @param session The web socket session
     */
    Handler(Session session, Gson gson) {
        this.session = session;
        this.gson = gson;
    }

    /**
     * The on message hook for the web socket connection.
     *
     * @param message The received message
     */
    @Override
    public void onMessage(String message) {
        System.out.println(message);

        // Receive the event type from the message using the beautiful
        // Google gson library.
        EventType event = EventType.fromString(
                this.gson.fromJson(message, EventBag.class).type
        );

        // If there is no event in the message, which can happen mainly for
        // responses of type {"ok": true}, don't execute anything.
        if (event == null) {
            return;
        }

        // We pass the event to another method to be handled.
        this.handleEvent(event, message);
    }

    /**
     * Handles the provided event in a switch-case, some people don't like switch-cases, well,
     * I don't like some people. It's not an ugly one anyway.
     *
     * @param event The event to be handled
     * @param message The message received through the web socket
     */
    private void handleEvent(EventType event, String message) {
        switch (event) {
            case GAME_OVER:
            case HELLO:
                this.respond(new ReadyResponse());
                break;
            case TOURNAMENT_OVER:
                Ceara.terminate();
                break;
            case YOUR_MOVE:
                YourMoveEventBag eventBag = this.gson.fromJson(message, YourMoveEventBag.class);
                State state = new State(eventBag.state, eventBag.moves);

                // TEST ZONE
                System.out.println(Arrays.deepToString(eventBag.state));
                System.out.println(Arrays.toString(eventBag.moves));
                System.out.println("move: ");
                int x = new Scanner(System.in).nextInt();
                int y = new Scanner(System.in).nextInt();
                this.respond(new MoveResponse(x, y));
                // END TEST ZONE

                break;
        }
    }

    /**
     * Sends a provided response to the server.
     *
     * @param response The response to be sent.
     */
    private void respond(Response response) {
        try {
            this.session.getBasicRemote().sendText(this.gson.toJson(response));
        } catch (IOException e) {
            ExceptionHandler.render(e);
        }
    }
}
