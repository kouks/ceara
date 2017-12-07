package io.pavelkoch.ceara.websockets;

public enum EventType {
    HELLO("hello"),
    YOUR_MOVE("your_move"),
    GAME_OVER("game_over"),
    TOURNAMENT_OVER("tournament_over");

    private final String type;

    EventType(String type) {
        this.type = type;
    }

    public static EventType fromString(String name) {
        for (EventType event : values()) {
            if (event.type.equals(name)) {
                return event;
            }
        }

        return null;
    }
}
