package io.pavelkoch.ceara.websockets.models;

import io.pavelkoch.ceara.websockets.Response;

public class MoveResponse implements Response {
    /**
     * The event type.
     */
    private final String type = "move";

    /**
     * The move instance.
     */
    private final Move move;

    /**
     * @param x The x coordinate of the move
     * @param y The y coordinate of the move
     */
    public MoveResponse(int x, int y) {
        this.move = new Move(x, y);
    }

    /**
     * The inner class representing a single move.
     */
    private class Move {
        /**
         * The x coordinate of the move.
         */
        private final int x;

        /**
         * The y coordinate of the move.
         */
        private final int y;

        /**
         * @param x The x coordinate of the move
         * @param y The y coordinate of the move
         */
        private Move(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
