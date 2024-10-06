package drake;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class TroopTile implements Tile, JSONSerializable {
    private final Troop troop;
    private final PlayingSide side;
    private final TroopFace face;

    public TroopTile(Troop troop, PlayingSide side, TroopFace face){
        this.troop = troop;
        this.side = side;
        this.face = face;
    }

    public PlayingSide side(){
        return this.side;
    }

    public TroopFace face(){
        return this.face;
    }

    public Troop troop(){
        return this.troop;
    }

    @Override
    public boolean canStepOn(){
        return false;
    }

    @Override
    public boolean hasTroop(){
        return true;
    }

    public TroopTile flipped(){
        return new TroopTile(this.troop, this.side, face == TroopFace.AVERS ? TroopFace.REVERS : TroopFace.AVERS);
    }

    @Override
    public List<Move> movesFrom(BoardPos pos, GameState state) {
        //TODO: possible moves
        return null;
    }

    @Override
    public void toJSON(PrintWriter writer) {
        writer.printf("{\"troop\":");
        troop.toJSON(writer);

        String color = side.equals(PlayingSide.BLUE) ? "BLUE":"ORANGE";

        writer.printf(",\"side\":\"%s\",", color);

        writer.print("\"face\":");
        face.toJSON(writer);

        writer.print("}");
    }
}
