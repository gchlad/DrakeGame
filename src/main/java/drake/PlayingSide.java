package drake;

import java.io.PrintWriter;

public enum PlayingSide implements JSONSerializable {
    ORANGE, BLUE;

    @Override
    public void toJSON(PrintWriter writer) {
        writer.print(this.equals(PlayingSide.BLUE)? "\"BLUE\"":"\"ORANGE\"");
    }
}
