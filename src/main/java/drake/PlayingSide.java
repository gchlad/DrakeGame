package drake;

import java.io.PrintWriter;

public enum PlayingSide {
    ORANGE, BLUE;

    @Override
    public void toJSON(PrintWriter writer) {
        writer.print(this.equals(PlayingSide.BLUE)? "\"BLUE\"":"\"ORANGE\"");
    }
}
