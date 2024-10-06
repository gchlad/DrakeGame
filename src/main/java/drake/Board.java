package drake;

public class Board {
    private final int dimension;
    private final BoardTile[][] tiles;

    public Board(int dimension) {
        this.dimension = dimension;
        this.tiles = new BoardTile[dimension][dimension];

        for (int i = 0; i< dimension; i++){
            for (int j = 0; j< dimension; j++) {
                tiles[i][j] = BoardTile.EMPTY;
            }
        }
    }

    private Board(int dimension, BoardTile[][] tiles) {
        this.dimension = dimension;
        this.tiles = tiles;
    }

    public int dimension() {
        return dimension;
    }

    public BoardTile at(TilePos pos) {
        return tiles[pos.i()][pos.j()];
    }

    public Board withTiles(TileAt... ats) {
        BoardTile[][] newTiles = new BoardTile[dimension][dimension];

        for (int i = 0; i < dimension; i++) {
            newTiles[i]=tiles[i].clone();
        }

        for (TileAt tileAt : ats) {
            TilePos pos = tileAt.pos;
            if (pos.i() >= 0 && pos.i() < dimension && pos.j() >= 0 && pos.j() < dimension) {
                newTiles[pos.i()][pos.j()] = tileAt.tile;
            }
        }

        return new Board(dimension, newTiles);
    }

    public PositionFactory positionFactory() {
        return new PositionFactory(dimension);
    }

    public static class TileAt {
        public final BoardPos pos;
        public final BoardTile tile;

        public TileAt(BoardPos pos, BoardTile tile) {
            this.pos = pos;
            this.tile = tile;
        }
    }
}

