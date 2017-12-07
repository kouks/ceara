package io.pavelkoch.ceara.websockets;

import com.google.gson.Gson;
import io.pavelkoch.ceara.exceptions.ExceptionHandler;
import org.glassfish.tyrus.client.ClientManager;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.util.concurrent.TimeUnit;

public class Client implements Runnable {
    /**
     * The web socket uri to connect to.
     */
    private final URI uri;

    /**
     * @param uri The web socket uri to connect to
     */
    public Client(URI uri) {
        this.uri = uri;
    }

    /**
     * Runs the web socket thread,
     */
    @Override
    public void run() {
        ClientManager client = ClientManager.createClient();

        // We attempt to establish the connection while
        // assigning the message handler to the Handler class
        try {
            client.connectToServer(new CearaEndpoint(), this.uri);
            client.getExecutorService().awaitTermination(1, TimeUnit.DAYS);
        } catch (DeploymentException | IOException | InterruptedException e) {
            ExceptionHandler.render(e);
        }
    }

    private class CearaEndpoint extends Endpoint {
        /**
         * On open web socket hook. Registers an event listener.
         *
         * @param session The web socket session
         * @param endpointConfig The configuration
         */
        @Override
        public void onOpen(Session session, EndpointConfig endpointConfig) {
            session.addMessageHandler(new Handler(session, new Gson()));
        }
    }
}
