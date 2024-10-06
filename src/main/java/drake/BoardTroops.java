package drake;

import java.io.PrintWriter;
import java.util.*;

public class BoardTroops implements JSONSerializable{
    private final PlayingSide playingSide;
    private final Map<BoardPos, TroopTile> troopMap;
    private final TilePos leaderPosition;
    private final int guards;

    public BoardTroops(PlayingSide playingSide) {
        this.playingSide = playingSide;
        this.troopMap = Collections.emptyMap();
        this.guards = 0;
        this.leaderPosition = TilePos.OFF_BOARD;
    }

    public BoardTroops(
            PlayingSide playingSide,
            Map<BoardPos, TroopTile> troopMap,
            TilePos leaderPosition,
            int guards) {
        this.playingSide = playingSide;
        this.troopMap = troopMap;
        this.guards = guards;
        this.leaderPosition = leaderPosition;
    }

    public Optional<TroopTile> at(TilePos pos) {
        TroopTile tile = troopMap.get(pos);
        return tile != null ? Optional.of(tile) : Optional.empty();
    }

    public PlayingSide playingSide() {
        return playingSide;
    }

    public TilePos leaderPosition() {
        return leaderPosition;
    }

    public int guards() {
        //if(guards == 0 || guards == 1 || guards == 2) {
        return guards;
        //TODO: exception?
    }

    public boolean isLeaderPlaced() {
        return !leaderPosition.equals(TilePos.OFF_BOARD);
    }

    public boolean isPlacingGuards() {
        return isLeaderPlaced() && guards < 2;
    }

    public Set<BoardPos> troopPositions() {
        return troopMap.keySet(); //?
    }

    public BoardTroops placeTroop(Troop troop, BoardPos target) {
        if (troopMap.containsKey(target)) {
            throw new IllegalArgumentException("Target position occupied.");
        }

        Map<BoardPos, TroopTile> newTroopMap = new HashMap<>(troopMap);
        TroopTile newTroopTile = new TroopTile(troop, playingSide, TroopFace.AVERS);
        newTroopMap.put(target, newTroopTile);

        TilePos newLeaderPosition = leaderPosition;
        int newGuards = guards;

        if(!isLeaderPlaced()) {
            newLeaderPosition = target;
        }
        else if(isLeaderPlaced() && isPlacingGuards()){
            newGuards++;
        }

        return new BoardTroops(playingSide, newTroopMap, newLeaderPosition, newGuards);
    }

    public BoardTroops troopStep(BoardPos origin, BoardPos target) {
        if (!isLeaderPlaced() || isPlacingGuards()) {
            throw new IllegalStateException("Leader and guards placing, not allowed to move tiles.");
        }
        if (!at(origin).isPresent() || at(target).isPresent()) {
            throw new IllegalArgumentException("Target position occupied or origin position empty.");
        }

        Map<BoardPos, TroopTile> newTroopMap = new HashMap<>(troopMap);
        TroopTile troopTile = newTroopMap.remove(origin);
        newTroopMap.put(target, troopTile.flipped());

        TilePos newLeaderPosition = leaderPosition.equals(origin) ? target : leaderPosition;

        return new BoardTroops(playingSide, newTroopMap, newLeaderPosition, guards);
    }

    public BoardTroops troopFlip(BoardPos origin) {
        if (!isLeaderPlaced()) {
            throw new IllegalStateException(
                    "Cannot move troops before the leader is placed.");
        }

        if (isPlacingGuards()) {
            throw new IllegalStateException(
                    "Cannot move troops before guards are placed.");
        }

        if (!at(origin).isPresent())
            throw new IllegalArgumentException();

        Map<BoardPos, TroopTile> newTroops = new HashMap<>(troopMap);
        TroopTile tile = newTroops.remove(origin);
        newTroops.put(origin, tile.flipped());

        return new BoardTroops(playingSide(), newTroops, leaderPosition, guards);
    }

    public BoardTroops removeTroop(BoardPos target) {
        if (!isLeaderPlaced() || isPlacingGuards()) {
            throw new IllegalStateException(
                    "Cannot move troops before the leader and guard are placed.");
        }

        if (!at(target).isPresent()) {
            throw new IllegalArgumentException();
        }

        Map<BoardPos, TroopTile> newTroops = new HashMap<>(troopMap);
        newTroops.remove(target);

        TilePos newLeaderPosition = leaderPosition.equals(target)? TilePos.OFF_BOARD : leaderPosition;
        return new BoardTroops(playingSide(), newTroops, newLeaderPosition, guards);
    }

    @Override
    public void toJSON(PrintWriter writer) {
        writer.print("{");

        writer.print("\"side\":\"");
        String color = playingSide.equals(PlayingSide.BLUE)? "BLUE":"ORANGE";
        writer.print(color);

        writer.print("\",\"leaderPosition\":\"");
        writer.print(leaderPosition.toString());

        writer.printf("\",\"guards\":%d,", guards);

        writer.print("\"troopMap\":{");
        int i = 0;
        Map<BoardPos, TroopTile> armyPos = new TreeMap<>(troopMap);
        for (Map.Entry<BoardPos, TroopTile> entry : armyPos.entrySet()) {
            writer.printf("\"%s\":", entry.getKey().toString());
            entry.getValue().toJSON(writer);
            if ( i+1 != troopMap.keySet().size() )
                writer.print(",");
            i++;
        }
        writer.print("}");

        writer.print("}");
    }
}
