package io.pavelkoch.ceara.mind;

import java.util.*;

public class Core implements Runnable {
    private State currentState;
    private String side;

    public Core(State state) {
        this.currentState = state;
    }

    /**
     * Run the calculation.
     */
    @Override
    public void run() {
        //
    }

    public Position requestNextMove() {
        if (this.currentState.getMoves().length == 0) {
            return new Position(9, 9);
        }

        List<EvaluatedPosition> evaluated = new ArrayList<>();
        int[][] evaluation = new int[][] {
                {0, 1, 10, 100, 100000},
                {0, 1, 10, 1000, 10000},
        };

        for (int x = 0; x < 20; x++) {
            for (int y = 0; y < 20; y++) {
                String[][] state = this.currentState.getState();
                String tile = state[x][y];
                Position pos = new Position(x, y);
                int eval = 0;
                int my = 0;
                int enemy = 0;

                if (!tile.equals("-")) {
                    continue;
                }

                my = 0; enemy = 0;
                for (int i = x - 1; i >= x - 4 && i >= 0; i--) {
                    if (state[i][y].equals(this.side)) { my++; continue; }
                    if (!state[i][y].equals("-")) enemy++;
                }
                if (enemy == 0 && my > 0) eval += evaluation[0][my];
                if (enemy > 0 && my == 0) eval += evaluation[1][enemy];

                my = 0; enemy = 0;
                for (int i = y - 1; i >= y - 4 && i >=0 ; i--) {
                    if (state[x][i].equals(this.side)) { my++; continue; }
                    if (!state[x][i].equals("-")) enemy++;
                }
                if (enemy == 0 && my > 0) eval += evaluation[0][my];
                if (enemy > 0 && my == 0) eval += evaluation[1][enemy];

                my = 0; enemy = 0;
                for (int i = x + 1; i <= x + 4 && i <= 19 ; i++) {
                    if (state[i][y].equals(this.side)) { my++; continue; }
                    if (!state[i][y].equals("-")) enemy++;
                }
                if (enemy == 0 && my > 0) eval += evaluation[0][my];
                if (enemy > 0 && my == 0) eval += evaluation[1][enemy];

                my = 0; enemy = 0;
                for (int i = y + 1; i <= y + 4 && i <= 19 ; i++) {
                    if (state[x][i].equals(this.side)) { my++; continue; }
                    if (!state[x][i].equals("-")) enemy++;
                }
                if (enemy == 0 && my > 0) eval += evaluation[0][my];
                if (enemy > 0 && my == 0) eval += evaluation[1][enemy];

                my = 0; enemy = 0;
                for (int i = 1; i < 5 && i + x < 20 && i + y < 20; i++) {
                    if (state[x + i][y + i].equals(this.side)) { my++; continue; }
                    if (!state[x + i][y + i].equals("-")) enemy++;
                }
                if (enemy == 0 && my > 0) eval += evaluation[0][my];
                if (enemy > 0 && my == 0) eval += evaluation[1][enemy];

                my = 0; enemy = 0;
                for (int i = 1; i < 5 && i + x < 20 && y - i >= 0; i++) {
                    if (state[x + i][y - i].equals(this.side)) { my++; continue; }
                    if (!state[x + i][y - i].equals("-")) enemy++;
                }
                if (enemy == 0 && my > 0) eval += evaluation[0][my];
                if (enemy > 0 && my == 0) eval += evaluation[1][enemy];

                my = 0; enemy = 0;
                for (int i = 1; i < 5 && x - i >= 0 && y - i >= 0; i++) {
                    if (state[x - i][y - i].equals(this.side)) { my++; continue; }
                    if (!state[x - i][y - i].equals("-")) enemy++;
                }
                if (enemy == 0 && my > 0) eval += evaluation[0][my];
                if (enemy > 0 && my == 0) eval += evaluation[1][enemy];

                my = 0; enemy = 0;
                for (int i = 1; i < 5 && x - i >= 0 && y + i < 20; i++) {
                    if (state[x - i][y + i].equals(this.side)) { my++; continue; }
                    if (!state[x - i][y + i].equals("-")) enemy++;
                }
                if (enemy == 0 && my > 0) eval += evaluation[0][my];
                if (enemy > 0 && my == 0) eval += evaluation[1][enemy];

                evaluated.add(new EvaluatedPosition(pos, eval));
            }
        }

        EvaluatedPosition highest = null;
        System.out.println(evaluated);
        for (EvaluatedPosition pos : evaluated) {
            if (highest == null) {
                highest = pos;
                continue;
            }
            highest = highest.eval > pos.eval ? highest : pos;
        }

        return highest.pos;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }
}
