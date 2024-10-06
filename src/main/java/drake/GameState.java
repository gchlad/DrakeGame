package drake;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class GameState {
    private final Board board;
    private final PlayingSide sideOnTurn;
    private final Army blueArmy;
    private final Army orangeArmy;
    private final GameResult result;

    public GameState(
            Board board,
            Army blueArmy,
            Army orangeArmy) {
        this(board, blueArmy, orangeArmy, PlayingSide.BLUE, GameResult.IN_PLAY);
    }

    public GameState(
            Board board,
            Army blueArmy,
            Army orangeArmy,
            PlayingSide sideOnTurn,
            GameResult result) {
        this.board = board;
        this.sideOnTurn = sideOnTurn;
        this.blueArmy = blueArmy;
        this.orangeArmy = orangeArmy;
        this.result = result;
    }

    public Board board() {
        return board;
    }

    public PlayingSide sideOnTurn() {
        return sideOnTurn;
    }

    public GameResult result() {
        return result;
    }

    public Army army(PlayingSide side) {
        if (side == PlayingSide.BLUE) {
            return blueArmy;
        }

        return orangeArmy;
    }

    public Army armyOnTurn() {
        return army(sideOnTurn);
    }

    public Army armyNotOnTurn() {
        if (sideOnTurn == PlayingSide.BLUE)
            return orangeArmy;

        return blueArmy;
    }

    public Tile tileAt(TilePos pos) {
        Optional<TroopTile> blueTroopTile = blueArmy.boardTroops().at(pos);
        Optional<TroopTile> orangeTroopTile = orangeArmy.boardTroops().at(pos);

        if(!blueTroopTile.equals(Optional.empty())) {
            return blueTroopTile.get();
        }

        if(!orangeTroopTile.equals(Optional.empty())) {
            return orangeTroopTile.get();
        }

        if (!board.at(pos).hasTroop()) {
            return board.at(pos);
        }

        return null;
    }

    private boolean canStepFrom(TilePos origin) {
        if(origin == TilePos.OFF_BOARD || !this.armyOnTurn().boardTroops().isLeaderPlaced() || this.armyOnTurn().boardTroops().isPlacingGuards()) {
            return false;
        }
        else {
            return (this.result == GameResult.IN_PLAY && tileAt(origin).hasTroop() && this.sideOnTurn() == sideOnTurn);
        }
    }

    private boolean canStepTo(TilePos target) {
        if(target == TilePos.OFF_BOARD){
            return false;
        }
        try {
            return result == GameResult.IN_PLAY && tileAt(target).canStepOn();
        }
        catch (ArrayIndexOutOfBoundsException e){
            return false;
        }
    }

    private boolean canCaptureOn(TilePos target) {
        return result == GameResult.IN_PLAY && target != TilePos.OFF_BOARD && tileAt(target).hasTroop() && !armyNotOnTurn().boardTroops().at(target).equals(Optional.empty());
    }

    public boolean canStep(TilePos origin, TilePos target) {

        return canStepFrom(origin) && canStepTo(target);
    }

    public boolean canCapture(TilePos origin, TilePos target) {
        return canStepFrom(origin) && canCaptureOn(target);
    }

    public boolean canPlaceFromStack(TilePos target) {
        if(armyOnTurn().stack().isEmpty() || target == TilePos.OFF_BOARD || result != GameResult.IN_PLAY || !canStepTo(target) ) {
            return false;
        }
        else{
            // intro game
            if(!armyOnTurn().boardTroops().isLeaderPlaced()){
                return target.row() == (armyOnTurn().side() == PlayingSide.BLUE ? 1 : board.dimension());
            }
            else if (armyOnTurn().boardTroops().isLeaderPlaced() && armyOnTurn().boardTroops().isPlacingGuards()) {
                List<?extends TilePos> neighbours = armyOnTurn().boardTroops().leaderPosition().neighbours();
                boolean inNeighborhood = false;
                for ( TilePos neighbour : neighbours ) {
                    if ( target.equals(neighbour) ) {
                        inNeighborhood = true;
                        break;
                    }
                }
                return inNeighborhood;
            }

            // medium game
            else {
                Set<BoardPos> placedTroops = armyOnTurn().boardTroops().troopPositions();
                boolean inNeighborhood = false;
                for ( BoardPos placedTroop : placedTroops ) {
                    if ( placedTroop.isNextTo(target) ) {
                        inNeighborhood = true;
                        break;
                    }
                }
                return inNeighborhood;
            }
        }
    }

    public GameState stepOnly(BoardPos origin, BoardPos target) {
        if (canStep(origin, target))
            return createNewGameState(
                    armyNotOnTurn(),
                    armyOnTurn().troopStep(origin, target), GameResult.IN_PLAY);

        throw new IllegalArgumentException();
    }

    public GameState stepAndCapture(BoardPos origin, BoardPos target) {
        if (canCapture(origin, target)) {
            Troop captured = armyNotOnTurn().boardTroops().at(target).get().troop();
            GameResult newResult = GameResult.IN_PLAY;

            if (armyNotOnTurn().boardTroops().leaderPosition().equals(target))
                newResult = GameResult.VICTORY;

            return createNewGameState(
                    armyNotOnTurn().removeTroop(target),
                    armyOnTurn().troopStep(origin, target).capture(captured), newResult);
        }

        throw new IllegalArgumentException();
    }

    public GameState captureOnly(BoardPos origin, BoardPos target) {
        if (canCapture(origin, target)) {
            Troop captured = armyNotOnTurn().boardTroops().at(target).get().troop();
            GameResult newResult = GameResult.IN_PLAY;

            if (armyNotOnTurn().boardTroops().leaderPosition().equals(target))
                newResult = GameResult.VICTORY;

            return createNewGameState(
                    armyNotOnTurn().removeTroop(target),
                    armyOnTurn().troopFlip(origin).capture(captured), newResult);
        }

        throw new IllegalArgumentException();
    }

    public GameState placeFromStack(BoardPos target) {
        if (canPlaceFromStack(target)) {
            return createNewGameState(
                    armyNotOnTurn(),
                    armyOnTurn().placeFromStack(target),
                    GameResult.IN_PLAY);
        }

        throw new IllegalArgumentException();
    }

    public GameState resign() {
        return createNewGameState(
                armyNotOnTurn(),
                armyOnTurn(),
                GameResult.VICTORY);
    }

    public GameState draw() {
        return createNewGameState(
                armyOnTurn(),
                armyNotOnTurn(),
                GameResult.DRAW);
    }

    private GameState createNewGameState(Army armyOnTurn, Army armyNotOnTurn, GameResult result) {
        if (armyOnTurn.side() == PlayingSide.BLUE) {
            return new GameState(board, armyOnTurn, armyNotOnTurn, PlayingSide.BLUE, result);
        }

        return new GameState(board, armyNotOnTurn, armyOnTurn, PlayingSide.ORANGE, result);
    }
}

