package io.pavelkoch.ceara.websockets.models;

import io.pavelkoch.ceara.websockets.Event;

public class NewGameEventBag extends EventBag implements Event {
    /**
     * The side this bot plays for.
     */
    public String side;
}
