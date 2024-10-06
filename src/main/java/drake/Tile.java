package drake;

import java.util.List;

public interface Tile {
    boolean canStepOn();

    boolean hasTroop();

    List<Move> movesFrom(BoardPos pos, GameState state);
}