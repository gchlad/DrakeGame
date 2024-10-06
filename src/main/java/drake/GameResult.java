package drake;

import java.io.PrintWriter;

public enum GameResult implements JSONSerializable{
    VICTORY, DRAW, IN_PLAY;

    @Override
    public void toJSON(PrintWriter writer) {
        writer.print("\"");
        if ( this.equals(GameResult.IN_PLAY) ) {
            writer.print("IN_PLAY");
        }
        else if ( this.equals(GameResult.DRAW) ) {
            writer.print("DRAW");
        }
        else {
            writer.print("VICTORY");
        }
        writer.print("\"");
    }
}
