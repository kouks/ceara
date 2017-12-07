package io.pavelkoch.ceara.websockets.models;

import com.google.gson.annotations.SerializedName;
import io.pavelkoch.ceara.websockets.Event;

public class YourMoveEventBag extends EventBag implements Event {
    /**
     * The current game state.
     */
    public String[][] state;

    /**
     * The array of played moves.
     */
    public Move[] moves;

    /**
     * The inner class representing a single move.
     */
    public class Move {
        /**
         * The x coordinate of the move.
         */
        public int x;

        /**
         * The y coordinate of the move.
         */
        public int y;

        /**
         * The side the move was played for.
         */
        @SerializedName("side") public String sign;
    }
}
