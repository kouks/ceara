package io.pavelkoch.ceara.websockets.models;

import io.pavelkoch.ceara.Ceara;
import io.pavelkoch.ceara.websockets.Response;

public class ReadyResponse implements Response {
    /**
     * The event type.
     */
    private String type = "ready";

    /**
     * The bot name.
     */
    private String name = Ceara.NAME;
}
