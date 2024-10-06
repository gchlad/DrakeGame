package drake;

import java.util.ArrayList;
import java.util.List;

public class SlideAction extends TroopAction {

    public SlideAction(Offset2D offset) {
        super(offset);
    }


    public SlideAction(int offsetX, int offsetY) {
        super(offsetX, offsetY);
    }

    public List<Move> movesFrom(BoardPos origin, PlayingSide side, GameState state) {
        List<Move> result = new ArrayList<>();
        TilePos target = origin.stepByPlayingSide(offset(), side);

        while(!target.equals(TilePos.OFF_BOARD) ) {
            boolean canStep = state.canStep(origin, target);
            boolean canCapture = state.canCapture(origin, target);

            if (canStep) {
                result.add(new StepOnly(origin, (BoardPos) target));
            }
            if (canCapture) {
                result.add(new StepAndCapture(origin, (BoardPos) target));
                break;  //blocking
            }
            // blocking
            if (!canStep /*&& !canCapture*/) {
                break;
            }

            target = target.stepByPlayingSide(offset(), side);
        }

        return result;
    }
}
