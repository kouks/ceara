package io.pavelkoch.ceara.mind;

public class EvaluatedPosition {
    public Position pos;
    public int eval;

    public EvaluatedPosition(Position pos, int eval) {
        this.pos = pos;
        this.eval = eval;
    }

    @Override
    public String toString() {
        return this.pos.toString() + " = " + eval;
    }
}
