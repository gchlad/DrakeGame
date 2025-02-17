package suite05;

import org.junit.Test;
import drake.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

import static org.junit.Assert.assertEquals;

public class JSONTest {

    private GameState state08() {
        Board board = new Board(4);
        PositionFactory pf = board.positionFactory();
        board = board.withTiles(
                new Board.TileAt(pf.pos(1, 1), BoardTile.MOUNTAIN),
                new Board.TileAt(pf.pos(3, 2), BoardTile.MOUNTAIN));

        GameState state01 = new StandardDrakeSetup().startState(board);
        GameState state02 = new PlaceFromStack(pf.pos("a1")).execute(state01);
        GameState state03 = new PlaceFromStack(pf.pos("a4")).execute(state02);
        GameState state04 = new PlaceFromStack(pf.pos("b1")).execute(state03);
        GameState state05 = new PlaceFromStack(pf.pos("a3")).execute(state04);
        GameState state06 = new PlaceFromStack(pf.pos("a2")).execute(state05);
        GameState state07 = new PlaceFromStack(pf.pos("b4")).execute(state06);
        return new PlaceFromStack(pf.pos("c1")).execute(state07);
    }

    @Test
    public void testGameResult() {
        ByteArrayOutputStream out;
        PrintWriter writer;

        out = new ByteArrayOutputStream();
        writer = new PrintWriter(out);
        state08().result().toJSON(writer);
        writer.close();
        assertEquals(
                "\"IN_PLAY\"",
                out.toString()
        );
    }

    @Test
    public void testBoardPos() {
        ByteArrayOutputStream out;
        PrintWriter writer;

        out = new ByteArrayOutputStream();
        writer = new PrintWriter(out);
        state08().armyOnTurn().boardTroops().leaderPosition().toJSON(writer);
        writer.close();
        assertEquals(
                "\"a4\"",
                out.toString()
        );
    }

    @Test
    public void testPlayingSide() {
        ByteArrayOutputStream out;
        PrintWriter writer;

        out = new ByteArrayOutputStream();
        writer = new PrintWriter(out);
        state08().sideOnTurn().toJSON(writer);
        writer.close();
        assertEquals(
                "\"ORANGE\"",
                out.toString()
        );
    }

    @Test
    public void testTroopFace() {
        ByteArrayOutputStream out;
        PrintWriter writer;

        out = new ByteArrayOutputStream();
        writer = new PrintWriter(out);
        state08().armyOnTurn().boardTroops().at(new BoardPos(4, 0, 3)).get().face().toJSON(writer);
        writer.close();
        assertEquals(
                "\"AVERS\"",
                out.toString()
        );
    }

    @Test
    public void testTroop() {
        ByteArrayOutputStream out;
        PrintWriter writer;

        out = new ByteArrayOutputStream();
        writer = new PrintWriter(out);
        state08().armyOnTurn().boardTroops().at(new BoardPos(4, 0, 3)).get().troop().toJSON(writer);
        writer.close();
        assertEquals(
                "\"Drake\"",
                out.toString()
        );
    }

    @Test
    public void testTroopTile() {
        ByteArrayOutputStream out;
        PrintWriter writer;

        out = new ByteArrayOutputStream();
        writer = new PrintWriter(out);
        state08().armyOnTurn().boardTroops().at(new BoardPos(4, 0, 3)).get().toJSON(writer);
        writer.close();
        assertEquals(
                "{\"troop\":\"Drake\",\"side\":\"ORANGE\",\"face\":\"AVERS\"}",
                out.toString()
        );
    }

    @Test
    public void testBoardTroops() {
        ByteArrayOutputStream out;
        PrintWriter writer;

        out = new ByteArrayOutputStream();
        writer = new PrintWriter(out);
        state08().armyOnTurn().boardTroops().toJSON(writer);
        writer.close();
        assertEquals(
                "{\"side\":\"ORANGE\",\"leaderPosition\":\"a4\",\"guards\":2,\"troopMap\":{\"a3\":{\"troop\":\"Clubman\",\"side\":\"ORANGE\",\"face\":\"AVERS\"},\"a4\":{\"troop\":\"Drake\",\"side\":\"ORANGE\",\"face\":\"AVERS\"},\"b4\":{\"troop\":\"Clubman\",\"side\":\"ORANGE\",\"face\":\"AVERS\"}}}",
                out.toString()
        );
    }

    @Test
    public void testArmy() {
        ByteArrayOutputStream out;
        PrintWriter writer;

        out = new ByteArrayOutputStream();
        writer = new PrintWriter(out);
        state08().armyOnTurn().toJSON(writer);
        writer.close();
        assertEquals(
                "{\"boardTroops\":{\"side\":\"ORANGE\",\"leaderPosition\":\"a4\",\"guards\":2,\"troopMap\":{\"a3\":{\"troop\":\"Clubman\",\"side\":\"ORANGE\",\"face\":\"AVERS\"},\"a4\":{\"troop\":\"Drake\",\"side\":\"ORANGE\",\"face\":\"AVERS\"},\"b4\":{\"troop\":\"Clubman\",\"side\":\"ORANGE\",\"face\":\"AVERS\"}}},\"stack\":[\"Monk\",\"Spearman\",\"Swordsman\",\"Archer\"],\"captured\":[]}",
                out.toString()
        );
    }

    @Test
    public void testBoardTile() {
        ByteArrayOutputStream out;
        PrintWriter writer;

        out = new ByteArrayOutputStream();
        writer = new PrintWriter(out);
        state08().board().at(new BoardPos(4, 0, 3)).toJSON(writer);
        writer.close();
        assertEquals(
                "\"empty\"",
                out.toString()
        );
    }

    @Test
    public void testBoardTile2() {
        ByteArrayOutputStream out;
        PrintWriter writer;

        out = new ByteArrayOutputStream();
        writer = new PrintWriter(out);
        state08().board().at(new BoardPos(4, 3, 2)).toJSON(writer);
        writer.close();
        assertEquals(
                "\"mountain\"",
                out.toString()
        );
    }

    @Test
    public void testBoard() {
        ByteArrayOutputStream out;
        PrintWriter writer;

        out = new ByteArrayOutputStream();
        writer = new PrintWriter(out);
        state08().board().toJSON(writer);
        writer.close();
        assertEquals(
                "{\"dimension\":4,\"tiles\":[\"empty\",\"empty\",\"empty\",\"empty\",\"empty\",\"mountain\",\"empty\",\"empty\",\"empty\",\"empty\",\"empty\",\"mountain\",\"empty\",\"empty\",\"empty\",\"empty\"]}",
                out.toString()
        );
    }

    @Test
    public void testGameState() {
        Board board = new Board(4);
        PositionFactory pf = board.positionFactory();
        board = board.withTiles(
                new Board.TileAt(pf.pos(1, 1), BoardTile.MOUNTAIN),
                new Board.TileAt(pf.pos(3, 2), BoardTile.MOUNTAIN));

        ByteArrayOutputStream out;
        PrintWriter writer;

        out = new ByteArrayOutputStream();
        writer = new PrintWriter(out);
        GameState state01 = new StandardDrakeSetup().startState(board);
        state01.toJSON(writer);
        writer.close();
        assertEquals(
                "{\"result\":\"IN_PLAY\",\"board\":{\"dimension\":4,\"tiles\":[\"empty\",\"empty\",\"empty\",\"empty\",\"empty\",\"mountain\",\"empty\",\"empty\",\"empty\",\"empty\",\"empty\",\"mountain\",\"empty\",\"empty\",\"empty\",\"empty\"]},\"blueArmy\":{\"boardTroops\":{\"side\":\"BLUE\",\"leaderPosition\":\"off-board\",\"guards\":0,\"troopMap\":{}},\"stack\":[\"Drake\",\"Clubman\",\"Clubman\",\"Monk\",\"Spearman\",\"Swordsman\",\"Archer\"],\"captured\":[]},\"orangeArmy\":{\"boardTroops\":{\"side\":\"ORANGE\",\"leaderPosition\":\"off-board\",\"guards\":0,\"troopMap\":{}},\"stack\":[\"Drake\",\"Clubman\",\"Clubman\",\"Monk\",\"Spearman\",\"Swordsman\",\"Archer\"],\"captured\":[]}}",
                out.toString());

        out = new ByteArrayOutputStream();
        writer = new PrintWriter(out);
        GameState state02 = new PlaceFromStack(pf.pos("a1")).execute(state01);
        state02.toJSON(writer);
        writer.close();
        assertEquals(
                "{\"result\":\"IN_PLAY\",\"board\":{\"dimension\":4,\"tiles\":[\"empty\",\"empty\",\"empty\",\"empty\",\"empty\",\"mountain\",\"empty\",\"empty\",\"empty\",\"empty\",\"empty\",\"mountain\",\"empty\",\"empty\",\"empty\",\"empty\"]},\"blueArmy\":{\"boardTroops\":{\"side\":\"BLUE\",\"leaderPosition\":\"a1\",\"guards\":0,\"troopMap\":{\"a1\":{\"troop\":\"Drake\",\"side\":\"BLUE\",\"face\":\"AVERS\"}}},\"stack\":[\"Clubman\",\"Clubman\",\"Monk\",\"Spearman\",\"Swordsman\",\"Archer\"],\"captured\":[]},\"orangeArmy\":{\"boardTroops\":{\"side\":\"ORANGE\",\"leaderPosition\":\"off-board\",\"guards\":0,\"troopMap\":{}},\"stack\":[\"Drake\",\"Clubman\",\"Clubman\",\"Monk\",\"Spearman\",\"Swordsman\",\"Archer\"],\"captured\":[]}}",
                out.toString()
        );

        out = new ByteArrayOutputStream();
        writer = new PrintWriter(out);
        GameState state03 = new PlaceFromStack(pf.pos("a4")).execute(state02);
        state03.toJSON(writer);
        writer.close();
        assertEquals(
                "{\"result\":\"IN_PLAY\",\"board\":{\"dimension\":4,\"tiles\":[\"empty\",\"empty\",\"empty\",\"empty\",\"empty\",\"mountain\",\"empty\",\"empty\",\"empty\",\"empty\",\"empty\",\"mountain\",\"empty\",\"empty\",\"empty\",\"empty\"]},\"blueArmy\":{\"boardTroops\":{\"side\":\"BLUE\",\"leaderPosition\":\"a1\",\"guards\":0,\"troopMap\":{\"a1\":{\"troop\":\"Drake\",\"side\":\"BLUE\",\"face\":\"AVERS\"}}},\"stack\":[\"Clubman\",\"Clubman\",\"Monk\",\"Spearman\",\"Swordsman\",\"Archer\"],\"captured\":[]},\"orangeArmy\":{\"boardTroops\":{\"side\":\"ORANGE\",\"leaderPosition\":\"a4\",\"guards\":0,\"troopMap\":{\"a4\":{\"troop\":\"Drake\",\"side\":\"ORANGE\",\"face\":\"AVERS\"}}},\"stack\":[\"Clubman\",\"Clubman\",\"Monk\",\"Spearman\",\"Swordsman\",\"Archer\"],\"captured\":[]}}",
                out.toString()
        );

        out = new ByteArrayOutputStream();
        writer = new PrintWriter(out);
        GameState state04 = new PlaceFromStack(pf.pos("b1")).execute(state03);
        state04.toJSON(writer);
        writer.close();
        assertEquals(
                "{\"result\":\"IN_PLAY\",\"board\":{\"dimension\":4,\"tiles\":[\"empty\",\"empty\",\"empty\",\"empty\",\"empty\",\"mountain\",\"empty\",\"empty\",\"empty\",\"empty\",\"empty\",\"mountain\",\"empty\",\"empty\",\"empty\",\"empty\"]},\"blueArmy\":{\"boardTroops\":{\"side\":\"BLUE\",\"leaderPosition\":\"a1\",\"guards\":1,\"troopMap\":{\"a1\":{\"troop\":\"Drake\",\"side\":\"BLUE\",\"face\":\"AVERS\"},\"b1\":{\"troop\":\"Clubman\",\"side\":\"BLUE\",\"face\":\"AVERS\"}}},\"stack\":[\"Clubman\",\"Monk\",\"Spearman\",\"Swordsman\",\"Archer\"],\"captured\":[]},\"orangeArmy\":{\"boardTroops\":{\"side\":\"ORANGE\",\"leaderPosition\":\"a4\",\"guards\":0,\"troopMap\":{\"a4\":{\"troop\":\"Drake\",\"side\":\"ORANGE\",\"face\":\"AVERS\"}}},\"stack\":[\"Clubman\",\"Clubman\",\"Monk\",\"Spearman\",\"Swordsman\",\"Archer\"],\"captured\":[]}}",
                out.toString()
        );

        out = new ByteArrayOutputStream();
        writer = new PrintWriter(out);
        GameState state05 = new PlaceFromStack(pf.pos("a3")).execute(state04);
        state05.toJSON(writer);
        writer.close();
        assertEquals(
                "{\"result\":\"IN_PLAY\",\"board\":{\"dimension\":4,\"tiles\":[\"empty\",\"empty\",\"empty\",\"empty\",\"empty\",\"mountain\",\"empty\",\"empty\",\"empty\",\"empty\",\"empty\",\"mountain\",\"empty\",\"empty\",\"empty\",\"empty\"]},\"blueArmy\":{\"boardTroops\":{\"side\":\"BLUE\",\"leaderPosition\":\"a1\",\"guards\":1,\"troopMap\":{\"a1\":{\"troop\":\"Drake\",\"side\":\"BLUE\",\"face\":\"AVERS\"},\"b1\":{\"troop\":\"Clubman\",\"side\":\"BLUE\",\"face\":\"AVERS\"}}},\"stack\":[\"Clubman\",\"Monk\",\"Spearman\",\"Swordsman\",\"Archer\"],\"captured\":[]},\"orangeArmy\":{\"boardTroops\":{\"side\":\"ORANGE\",\"leaderPosition\":\"a4\",\"guards\":1,\"troopMap\":{\"a3\":{\"troop\":\"Clubman\",\"side\":\"ORANGE\",\"face\":\"AVERS\"},\"a4\":{\"troop\":\"Drake\",\"side\":\"ORANGE\",\"face\":\"AVERS\"}}},\"stack\":[\"Clubman\",\"Monk\",\"Spearman\",\"Swordsman\",\"Archer\"],\"captured\":[]}}",
                out.toString()
        );

        out = new ByteArrayOutputStream();
        writer = new PrintWriter(out);
        GameState state06 = new PlaceFromStack(pf.pos("a2")).execute(state05);
        state06.toJSON(writer);
        writer.close();
        assertEquals(
                "{\"result\":\"IN_PLAY\",\"board\":{\"dimension\":4,\"tiles\":[\"empty\",\"empty\",\"empty\",\"empty\",\"empty\",\"mountain\",\"empty\",\"empty\",\"empty\",\"empty\",\"empty\",\"mountain\",\"empty\",\"empty\",\"empty\",\"empty\"]},\"blueArmy\":{\"boardTroops\":{\"side\":\"BLUE\",\"leaderPosition\":\"a1\",\"guards\":2,\"troopMap\":{\"a1\":{\"troop\":\"Drake\",\"side\":\"BLUE\",\"face\":\"AVERS\"},\"a2\":{\"troop\":\"Clubman\",\"side\":\"BLUE\",\"face\":\"AVERS\"},\"b1\":{\"troop\":\"Clubman\",\"side\":\"BLUE\",\"face\":\"AVERS\"}}},\"stack\":[\"Monk\",\"Spearman\",\"Swordsman\",\"Archer\"],\"captured\":[]},\"orangeArmy\":{\"boardTroops\":{\"side\":\"ORANGE\",\"leaderPosition\":\"a4\",\"guards\":1,\"troopMap\":{\"a3\":{\"troop\":\"Clubman\",\"side\":\"ORANGE\",\"face\":\"AVERS\"},\"a4\":{\"troop\":\"Drake\",\"side\":\"ORANGE\",\"face\":\"AVERS\"}}},\"stack\":[\"Clubman\",\"Monk\",\"Spearman\",\"Swordsman\",\"Archer\"],\"captured\":[]}}",
                out.toString()
        );

        out = new ByteArrayOutputStream();
        writer = new PrintWriter(out);
        GameState state07 = new PlaceFromStack(pf.pos("b4")).execute(state06);
        state07.toJSON(writer);
        writer.close();
        assertEquals(
                "{\"result\":\"IN_PLAY\",\"board\":{\"dimension\":4,\"tiles\":[\"empty\",\"empty\",\"empty\",\"empty\",\"empty\",\"mountain\",\"empty\",\"empty\",\"empty\",\"empty\",\"empty\",\"mountain\",\"empty\",\"empty\",\"empty\",\"empty\"]},\"blueArmy\":{\"boardTroops\":{\"side\":\"BLUE\",\"leaderPosition\":\"a1\",\"guards\":2,\"troopMap\":{\"a1\":{\"troop\":\"Drake\",\"side\":\"BLUE\",\"face\":\"AVERS\"},\"a2\":{\"troop\":\"Clubman\",\"side\":\"BLUE\",\"face\":\"AVERS\"},\"b1\":{\"troop\":\"Clubman\",\"side\":\"BLUE\",\"face\":\"AVERS\"}}},\"stack\":[\"Monk\",\"Spearman\",\"Swordsman\",\"Archer\"],\"captured\":[]},\"orangeArmy\":{\"boardTroops\":{\"side\":\"ORANGE\",\"leaderPosition\":\"a4\",\"guards\":2,\"troopMap\":{\"a3\":{\"troop\":\"Clubman\",\"side\":\"ORANGE\",\"face\":\"AVERS\"},\"a4\":{\"troop\":\"Drake\",\"side\":\"ORANGE\",\"face\":\"AVERS\"},\"b4\":{\"troop\":\"Clubman\",\"side\":\"ORANGE\",\"face\":\"AVERS\"}}},\"stack\":[\"Monk\",\"Spearman\",\"Swordsman\",\"Archer\"],\"captured\":[]}}",
                out.toString()
        );

        out = new ByteArrayOutputStream();
        writer = new PrintWriter(out);
        GameState state08 = new PlaceFromStack(pf.pos("c1")).execute(state07);
        state08.toJSON(writer);
        writer.close();
        assertEquals(
                "{\"result\":\"IN_PLAY\",\"board\":{\"dimension\":4,\"tiles\":[\"empty\",\"empty\",\"empty\",\"empty\",\"empty\",\"mountain\",\"empty\",\"empty\",\"empty\",\"empty\",\"empty\",\"mountain\",\"empty\",\"empty\",\"empty\",\"empty\"]},\"blueArmy\":{\"boardTroops\":{\"side\":\"BLUE\",\"leaderPosition\":\"a1\",\"guards\":2,\"troopMap\":{\"a1\":{\"troop\":\"Drake\",\"side\":\"BLUE\",\"face\":\"AVERS\"},\"a2\":{\"troop\":\"Clubman\",\"side\":\"BLUE\",\"face\":\"AVERS\"},\"b1\":{\"troop\":\"Clubman\",\"side\":\"BLUE\",\"face\":\"AVERS\"},\"c1\":{\"troop\":\"Monk\",\"side\":\"BLUE\",\"face\":\"AVERS\"}}},\"stack\":[\"Spearman\",\"Swordsman\",\"Archer\"],\"captured\":[]},\"orangeArmy\":{\"boardTroops\":{\"side\":\"ORANGE\",\"leaderPosition\":\"a4\",\"guards\":2,\"troopMap\":{\"a3\":{\"troop\":\"Clubman\",\"side\":\"ORANGE\",\"face\":\"AVERS\"},\"a4\":{\"troop\":\"Drake\",\"side\":\"ORANGE\",\"face\":\"AVERS\"},\"b4\":{\"troop\":\"Clubman\",\"side\":\"ORANGE\",\"face\":\"AVERS\"}}},\"stack\":[\"Monk\",\"Spearman\",\"Swordsman\",\"Archer\"],\"captured\":[]}}",
                out.toString()
        );

        out = new ByteArrayOutputStream();
        writer = new PrintWriter(out);
        GameState state09 = new PlaceFromStack(pf.pos("b3")).execute(state08);
        state09.toJSON(writer);
        writer.close();
        assertEquals(
                "{\"result\":\"IN_PLAY\",\"board\":{\"dimension\":4,\"tiles\":[\"empty\",\"empty\",\"empty\",\"empty\",\"empty\",\"mountain\",\"empty\",\"empty\",\"empty\",\"empty\",\"empty\",\"mountain\",\"empty\",\"empty\",\"empty\",\"empty\"]},\"blueArmy\":{\"boardTroops\":{\"side\":\"BLUE\",\"leaderPosition\":\"a1\",\"guards\":2,\"troopMap\":{\"a1\":{\"troop\":\"Drake\",\"side\":\"BLUE\",\"face\":\"AVERS\"},\"a2\":{\"troop\":\"Clubman\",\"side\":\"BLUE\",\"face\":\"AVERS\"},\"b1\":{\"troop\":\"Clubman\",\"side\":\"BLUE\",\"face\":\"AVERS\"},\"c1\":{\"troop\":\"Monk\",\"side\":\"BLUE\",\"face\":\"AVERS\"}}},\"stack\":[\"Spearman\",\"Swordsman\",\"Archer\"],\"captured\":[]},\"orangeArmy\":{\"boardTroops\":{\"side\":\"ORANGE\",\"leaderPosition\":\"a4\",\"guards\":2,\"troopMap\":{\"a3\":{\"troop\":\"Clubman\",\"side\":\"ORANGE\",\"face\":\"AVERS\"},\"a4\":{\"troop\":\"Drake\",\"side\":\"ORANGE\",\"face\":\"AVERS\"},\"b3\":{\"troop\":\"Monk\",\"side\":\"ORANGE\",\"face\":\"AVERS\"},\"b4\":{\"troop\":\"Clubman\",\"side\":\"ORANGE\",\"face\":\"AVERS\"}}},\"stack\":[\"Spearman\",\"Swordsman\",\"Archer\"],\"captured\":[]}}",
                out.toString()
        );

        out = new ByteArrayOutputStream();
        writer = new PrintWriter(out);
        GameState state10 = new StepAndCapture(pf.pos("a2"), pf.pos("a3")).execute(state09);
        state10.toJSON(writer);
        writer.close();
        assertEquals(
                "{\"result\":\"IN_PLAY\",\"board\":{\"dimension\":4,\"tiles\":[\"empty\",\"empty\",\"empty\",\"empty\",\"empty\",\"mountain\",\"empty\",\"empty\",\"empty\",\"empty\",\"empty\",\"mountain\",\"empty\",\"empty\",\"empty\",\"empty\"]},\"blueArmy\":{\"boardTroops\":{\"side\":\"BLUE\",\"leaderPosition\":\"a1\",\"guards\":2,\"troopMap\":{\"a1\":{\"troop\":\"Drake\",\"side\":\"BLUE\",\"face\":\"AVERS\"},\"a3\":{\"troop\":\"Clubman\",\"side\":\"BLUE\",\"face\":\"REVERS\"},\"b1\":{\"troop\":\"Clubman\",\"side\":\"BLUE\",\"face\":\"AVERS\"},\"c1\":{\"troop\":\"Monk\",\"side\":\"BLUE\",\"face\":\"AVERS\"}}},\"stack\":[\"Spearman\",\"Swordsman\",\"Archer\"],\"captured\":[\"Clubman\"]},\"orangeArmy\":{\"boardTroops\":{\"side\":\"ORANGE\",\"leaderPosition\":\"a4\",\"guards\":2,\"troopMap\":{\"a4\":{\"troop\":\"Drake\",\"side\":\"ORANGE\",\"face\":\"AVERS\"},\"b3\":{\"troop\":\"Monk\",\"side\":\"ORANGE\",\"face\":\"AVERS\"},\"b4\":{\"troop\":\"Clubman\",\"side\":\"ORANGE\",\"face\":\"AVERS\"}}},\"stack\":[\"Spearman\",\"Swordsman\",\"Archer\"],\"captured\":[]}}",
                out.toString()
        );

        out = new ByteArrayOutputStream();
        writer = new PrintWriter(out);
        GameState state11 = new PlaceFromStack(pf.pos("c4")).execute(state10);
        state11.toJSON(writer);
        writer.close();
        assertEquals(
                "{\"result\":\"IN_PLAY\",\"board\":{\"dimension\":4,\"tiles\":[\"empty\",\"empty\",\"empty\",\"empty\",\"empty\",\"mountain\",\"empty\",\"empty\",\"empty\",\"empty\",\"empty\",\"mountain\",\"empty\",\"empty\",\"empty\",\"empty\"]},\"blueArmy\":{\"boardTroops\":{\"side\":\"BLUE\",\"leaderPosition\":\"a1\",\"guards\":2,\"troopMap\":{\"a1\":{\"troop\":\"Drake\",\"side\":\"BLUE\",\"face\":\"AVERS\"},\"a3\":{\"troop\":\"Clubman\",\"side\":\"BLUE\",\"face\":\"REVERS\"},\"b1\":{\"troop\":\"Clubman\",\"side\":\"BLUE\",\"face\":\"AVERS\"},\"c1\":{\"troop\":\"Monk\",\"side\":\"BLUE\",\"face\":\"AVERS\"}}},\"stack\":[\"Spearman\",\"Swordsman\",\"Archer\"],\"captured\":[\"Clubman\"]},\"orangeArmy\":{\"boardTroops\":{\"side\":\"ORANGE\",\"leaderPosition\":\"a4\",\"guards\":2,\"troopMap\":{\"a4\":{\"troop\":\"Drake\",\"side\":\"ORANGE\",\"face\":\"AVERS\"},\"b3\":{\"troop\":\"Monk\",\"side\":\"ORANGE\",\"face\":\"AVERS\"},\"b4\":{\"troop\":\"Clubman\",\"side\":\"ORANGE\",\"face\":\"AVERS\"},\"c4\":{\"troop\":\"Spearman\",\"side\":\"ORANGE\",\"face\":\"AVERS\"}}},\"stack\":[\"Swordsman\",\"Archer\"],\"captured\":[]}}",
                out.toString()
        );

        out = new ByteArrayOutputStream();
        writer = new PrintWriter(out);
        GameState state12 = new StepOnly(pf.pos("c1"), pf.pos("d2")).execute(state11);
        state12.toJSON(writer);
        writer.close();
        assertEquals(
                "{\"result\":\"IN_PLAY\",\"board\":{\"dimension\":4,\"tiles\":[\"empty\",\"empty\",\"empty\",\"empty\",\"empty\",\"mountain\",\"empty\",\"empty\",\"empty\",\"empty\",\"empty\",\"mountain\",\"empty\",\"empty\",\"empty\",\"empty\"]},\"blueArmy\":{\"boardTroops\":{\"side\":\"BLUE\",\"leaderPosition\":\"a1\",\"guards\":2,\"troopMap\":{\"a1\":{\"troop\":\"Drake\",\"side\":\"BLUE\",\"face\":\"AVERS\"},\"a3\":{\"troop\":\"Clubman\",\"side\":\"BLUE\",\"face\":\"REVERS\"},\"b1\":{\"troop\":\"Clubman\",\"side\":\"BLUE\",\"face\":\"AVERS\"},\"d2\":{\"troop\":\"Monk\",\"side\":\"BLUE\",\"face\":\"REVERS\"}}},\"stack\":[\"Spearman\",\"Swordsman\",\"Archer\"],\"captured\":[\"Clubman\"]},\"orangeArmy\":{\"boardTroops\":{\"side\":\"ORANGE\",\"leaderPosition\":\"a4\",\"guards\":2,\"troopMap\":{\"a4\":{\"troop\":\"Drake\",\"side\":\"ORANGE\",\"face\":\"AVERS\"},\"b3\":{\"troop\":\"Monk\",\"side\":\"ORANGE\",\"face\":\"AVERS\"},\"b4\":{\"troop\":\"Clubman\",\"side\":\"ORANGE\",\"face\":\"AVERS\"},\"c4\":{\"troop\":\"Spearman\",\"side\":\"ORANGE\",\"face\":\"AVERS\"}}},\"stack\":[\"Swordsman\",\"Archer\"],\"captured\":[]}}",
                out.toString()
        );

        out = new ByteArrayOutputStream();
        writer = new PrintWriter(out);
        GameState state13 = new StepOnly(pf.pos("c4"), pf.pos("c3")).execute(state12);
        state13.toJSON(writer);
        writer.close();
        assertEquals(
                "{\"result\":\"IN_PLAY\",\"board\":{\"dimension\":4,\"tiles\":[\"empty\",\"empty\",\"empty\",\"empty\",\"empty\",\"mountain\",\"empty\",\"empty\",\"empty\",\"empty\",\"empty\",\"mountain\",\"empty\",\"empty\",\"empty\",\"empty\"]},\"blueArmy\":{\"boardTroops\":{\"side\":\"BLUE\",\"leaderPosition\":\"a1\",\"guards\":2,\"troopMap\":{\"a1\":{\"troop\":\"Drake\",\"side\":\"BLUE\",\"face\":\"AVERS\"},\"a3\":{\"troop\":\"Clubman\",\"side\":\"BLUE\",\"face\":\"REVERS\"},\"b1\":{\"troop\":\"Clubman\",\"side\":\"BLUE\",\"face\":\"AVERS\"},\"d2\":{\"troop\":\"Monk\",\"side\":\"BLUE\",\"face\":\"REVERS\"}}},\"stack\":[\"Spearman\",\"Swordsman\",\"Archer\"],\"captured\":[\"Clubman\"]},\"orangeArmy\":{\"boardTroops\":{\"side\":\"ORANGE\",\"leaderPosition\":\"a4\",\"guards\":2,\"troopMap\":{\"a4\":{\"troop\":\"Drake\",\"side\":\"ORANGE\",\"face\":\"AVERS\"},\"b3\":{\"troop\":\"Monk\",\"side\":\"ORANGE\",\"face\":\"AVERS\"},\"b4\":{\"troop\":\"Clubman\",\"side\":\"ORANGE\",\"face\":\"AVERS\"},\"c3\":{\"troop\":\"Spearman\",\"side\":\"ORANGE\",\"face\":\"REVERS\"}}},\"stack\":[\"Swordsman\",\"Archer\"],\"captured\":[]}}",
                out.toString()
        );

        out = new ByteArrayOutputStream();
        writer = new PrintWriter(out);
        GameState state14 = new PlaceFromStack(pf.pos("c1")).execute(state13);
        state14.toJSON(writer);
        writer.close();
        assertEquals(
                "{\"result\":\"IN_PLAY\",\"board\":{\"dimension\":4,\"tiles\":[\"empty\",\"empty\",\"empty\",\"empty\",\"empty\",\"mountain\",\"empty\",\"empty\",\"empty\",\"empty\",\"empty\",\"mountain\",\"empty\",\"empty\",\"empty\",\"empty\"]},\"blueArmy\":{\"boardTroops\":{\"side\":\"BLUE\",\"leaderPosition\":\"a1\",\"guards\":2,\"troopMap\":{\"a1\":{\"troop\":\"Drake\",\"side\":\"BLUE\",\"face\":\"AVERS\"},\"a3\":{\"troop\":\"Clubman\",\"side\":\"BLUE\",\"face\":\"REVERS\"},\"b1\":{\"troop\":\"Clubman\",\"side\":\"BLUE\",\"face\":\"AVERS\"},\"c1\":{\"troop\":\"Spearman\",\"side\":\"BLUE\",\"face\":\"AVERS\"},\"d2\":{\"troop\":\"Monk\",\"side\":\"BLUE\",\"face\":\"REVERS\"}}},\"stack\":[\"Swordsman\",\"Archer\"],\"captured\":[\"Clubman\"]},\"orangeArmy\":{\"boardTroops\":{\"side\":\"ORANGE\",\"leaderPosition\":\"a4\",\"guards\":2,\"troopMap\":{\"a4\":{\"troop\":\"Drake\",\"side\":\"ORANGE\",\"face\":\"AVERS\"},\"b3\":{\"troop\":\"Monk\",\"side\":\"ORANGE\",\"face\":\"AVERS\"},\"b4\":{\"troop\":\"Clubman\",\"side\":\"ORANGE\",\"face\":\"AVERS\"},\"c3\":{\"troop\":\"Spearman\",\"side\":\"ORANGE\",\"face\":\"REVERS\"}}},\"stack\":[\"Swordsman\",\"Archer\"],\"captured\":[]}}",
                out.toString()
        );

        out = new ByteArrayOutputStream();
        writer = new PrintWriter(out);
        GameState state15 = new StepOnly(pf.pos("b3"), pf.pos("c2")).execute(state14);
        state15.toJSON(writer);
        writer.close();
        assertEquals(
                "{\"result\":\"IN_PLAY\",\"board\":{\"dimension\":4,\"tiles\":[\"empty\",\"empty\",\"empty\",\"empty\",\"empty\",\"mountain\",\"empty\",\"empty\",\"empty\",\"empty\",\"empty\",\"mountain\",\"empty\",\"empty\",\"empty\",\"empty\"]},\"blueArmy\":{\"boardTroops\":{\"side\":\"BLUE\",\"leaderPosition\":\"a1\",\"guards\":2,\"troopMap\":{\"a1\":{\"troop\":\"Drake\",\"side\":\"BLUE\",\"face\":\"AVERS\"},\"a3\":{\"troop\":\"Clubman\",\"side\":\"BLUE\",\"face\":\"REVERS\"},\"b1\":{\"troop\":\"Clubman\",\"side\":\"BLUE\",\"face\":\"AVERS\"},\"c1\":{\"troop\":\"Spearman\",\"side\":\"BLUE\",\"face\":\"AVERS\"},\"d2\":{\"troop\":\"Monk\",\"side\":\"BLUE\",\"face\":\"REVERS\"}}},\"stack\":[\"Swordsman\",\"Archer\"],\"captured\":[\"Clubman\"]},\"orangeArmy\":{\"boardTroops\":{\"side\":\"ORANGE\",\"leaderPosition\":\"a4\",\"guards\":2,\"troopMap\":{\"a4\":{\"troop\":\"Drake\",\"side\":\"ORANGE\",\"face\":\"AVERS\"},\"b4\":{\"troop\":\"Clubman\",\"side\":\"ORANGE\",\"face\":\"AVERS\"},\"c2\":{\"troop\":\"Monk\",\"side\":\"ORANGE\",\"face\":\"REVERS\"},\"c3\":{\"troop\":\"Spearman\",\"side\":\"ORANGE\",\"face\":\"REVERS\"}}},\"stack\":[\"Swordsman\",\"Archer\"],\"captured\":[]}}",
                out.toString()
        );

        out = new ByteArrayOutputStream();
        writer = new PrintWriter(out);
        GameState state16 = new PlaceFromStack(pf.pos("a2")).execute(state15);
        state16.toJSON(writer);
        writer.close();
        assertEquals(
                "{\"result\":\"IN_PLAY\",\"board\":{\"dimension\":4,\"tiles\":[\"empty\",\"empty\",\"empty\",\"empty\",\"empty\",\"mountain\",\"empty\",\"empty\",\"empty\",\"empty\",\"empty\",\"mountain\",\"empty\",\"empty\",\"empty\",\"empty\"]},\"blueArmy\":{\"boardTroops\":{\"side\":\"BLUE\",\"leaderPosition\":\"a1\",\"guards\":2,\"troopMap\":{\"a1\":{\"troop\":\"Drake\",\"side\":\"BLUE\",\"face\":\"AVERS\"},\"a2\":{\"troop\":\"Swordsman\",\"side\":\"BLUE\",\"face\":\"AVERS\"},\"a3\":{\"troop\":\"Clubman\",\"side\":\"BLUE\",\"face\":\"REVERS\"},\"b1\":{\"troop\":\"Clubman\",\"side\":\"BLUE\",\"face\":\"AVERS\"},\"c1\":{\"troop\":\"Spearman\",\"side\":\"BLUE\",\"face\":\"AVERS\"},\"d2\":{\"troop\":\"Monk\",\"side\":\"BLUE\",\"face\":\"REVERS\"}}},\"stack\":[\"Archer\"],\"captured\":[\"Clubman\"]},\"orangeArmy\":{\"boardTroops\":{\"side\":\"ORANGE\",\"leaderPosition\":\"a4\",\"guards\":2,\"troopMap\":{\"a4\":{\"troop\":\"Drake\",\"side\":\"ORANGE\",\"face\":\"AVERS\"},\"b4\":{\"troop\":\"Clubman\",\"side\":\"ORANGE\",\"face\":\"AVERS\"},\"c2\":{\"troop\":\"Monk\",\"side\":\"ORANGE\",\"face\":\"REVERS\"},\"c3\":{\"troop\":\"Spearman\",\"side\":\"ORANGE\",\"face\":\"REVERS\"}}},\"stack\":[\"Swordsman\",\"Archer\"],\"captured\":[]}}",
                out.toString()
        );

        out = new ByteArrayOutputStream();
        writer = new PrintWriter(out);
        GameState state17 = new PlaceFromStack(pf.pos("c4")).execute(state16);
        state17.toJSON(writer);
        writer.close();
        assertEquals(
                "{\"result\":\"IN_PLAY\",\"board\":{\"dimension\":4,\"tiles\":[\"empty\",\"empty\",\"empty\",\"empty\",\"empty\",\"mountain\",\"empty\",\"empty\",\"empty\",\"empty\",\"empty\",\"mountain\",\"empty\",\"empty\",\"empty\",\"empty\"]},\"blueArmy\":{\"boardTroops\":{\"side\":\"BLUE\",\"leaderPosition\":\"a1\",\"guards\":2,\"troopMap\":{\"a1\":{\"troop\":\"Drake\",\"side\":\"BLUE\",\"face\":\"AVERS\"},\"a2\":{\"troop\":\"Swordsman\",\"side\":\"BLUE\",\"face\":\"AVERS\"},\"a3\":{\"troop\":\"Clubman\",\"side\":\"BLUE\",\"face\":\"REVERS\"},\"b1\":{\"troop\":\"Clubman\",\"side\":\"BLUE\",\"face\":\"AVERS\"},\"c1\":{\"troop\":\"Spearman\",\"side\":\"BLUE\",\"face\":\"AVERS\"},\"d2\":{\"troop\":\"Monk\",\"side\":\"BLUE\",\"face\":\"REVERS\"}}},\"stack\":[\"Archer\"],\"captured\":[\"Clubman\"]},\"orangeArmy\":{\"boardTroops\":{\"side\":\"ORANGE\",\"leaderPosition\":\"a4\",\"guards\":2,\"troopMap\":{\"a4\":{\"troop\":\"Drake\",\"side\":\"ORANGE\",\"face\":\"AVERS\"},\"b4\":{\"troop\":\"Clubman\",\"side\":\"ORANGE\",\"face\":\"AVERS\"},\"c2\":{\"troop\":\"Monk\",\"side\":\"ORANGE\",\"face\":\"REVERS\"},\"c3\":{\"troop\":\"Spearman\",\"side\":\"ORANGE\",\"face\":\"REVERS\"},\"c4\":{\"troop\":\"Swordsman\",\"side\":\"ORANGE\",\"face\":\"AVERS\"}}},\"stack\":[\"Archer\"],\"captured\":[]}}",
                out.toString()
        );

        out = new ByteArrayOutputStream();
        writer = new PrintWriter(out);
        GameState state18 = new StepOnly(pf.pos("d2"), pf.pos("d1")).execute(state17);
        state18.toJSON(writer);
        writer.close();
        assertEquals(
                "{\"result\":\"IN_PLAY\",\"board\":{\"dimension\":4,\"tiles\":[\"empty\",\"empty\",\"empty\",\"empty\",\"empty\",\"mountain\",\"empty\",\"empty\",\"empty\",\"empty\",\"empty\",\"mountain\",\"empty\",\"empty\",\"empty\",\"empty\"]},\"blueArmy\":{\"boardTroops\":{\"side\":\"BLUE\",\"leaderPosition\":\"a1\",\"guards\":2,\"troopMap\":{\"a1\":{\"troop\":\"Drake\",\"side\":\"BLUE\",\"face\":\"AVERS\"},\"a2\":{\"troop\":\"Swordsman\",\"side\":\"BLUE\",\"face\":\"AVERS\"},\"a3\":{\"troop\":\"Clubman\",\"side\":\"BLUE\",\"face\":\"REVERS\"},\"b1\":{\"troop\":\"Clubman\",\"side\":\"BLUE\",\"face\":\"AVERS\"},\"c1\":{\"troop\":\"Spearman\",\"side\":\"BLUE\",\"face\":\"AVERS\"},\"d1\":{\"troop\":\"Monk\",\"side\":\"BLUE\",\"face\":\"AVERS\"}}},\"stack\":[\"Archer\"],\"captured\":[\"Clubman\"]},\"orangeArmy\":{\"boardTroops\":{\"side\":\"ORANGE\",\"leaderPosition\":\"a4\",\"guards\":2,\"troopMap\":{\"a4\":{\"troop\":\"Drake\",\"side\":\"ORANGE\",\"face\":\"AVERS\"},\"b4\":{\"troop\":\"Clubman\",\"side\":\"ORANGE\",\"face\":\"AVERS\"},\"c2\":{\"troop\":\"Monk\",\"side\":\"ORANGE\",\"face\":\"REVERS\"},\"c3\":{\"troop\":\"Spearman\",\"side\":\"ORANGE\",\"face\":\"REVERS\"},\"c4\":{\"troop\":\"Swordsman\",\"side\":\"ORANGE\",\"face\":\"AVERS\"}}},\"stack\":[\"Archer\"],\"captured\":[]}}",
                out.toString()
        );

        out = new ByteArrayOutputStream();
        writer = new PrintWriter(out);
        GameState state19 = new StepOnly(pf.pos("c2"), pf.pos("d2")).execute(state18);
        state19.toJSON(writer);
        writer.close();
        assertEquals(
                "{\"result\":\"IN_PLAY\",\"board\":{\"dimension\":4,\"tiles\":[\"empty\",\"empty\",\"empty\",\"empty\",\"empty\",\"mountain\",\"empty\",\"empty\",\"empty\",\"empty\",\"empty\",\"mountain\",\"empty\",\"empty\",\"empty\",\"empty\"]},\"blueArmy\":{\"boardTroops\":{\"side\":\"BLUE\",\"leaderPosition\":\"a1\",\"guards\":2,\"troopMap\":{\"a1\":{\"troop\":\"Drake\",\"side\":\"BLUE\",\"face\":\"AVERS\"},\"a2\":{\"troop\":\"Swordsman\",\"side\":\"BLUE\",\"face\":\"AVERS\"},\"a3\":{\"troop\":\"Clubman\",\"side\":\"BLUE\",\"face\":\"REVERS\"},\"b1\":{\"troop\":\"Clubman\",\"side\":\"BLUE\",\"face\":\"AVERS\"},\"c1\":{\"troop\":\"Spearman\",\"side\":\"BLUE\",\"face\":\"AVERS\"},\"d1\":{\"troop\":\"Monk\",\"side\":\"BLUE\",\"face\":\"AVERS\"}}},\"stack\":[\"Archer\"],\"captured\":[\"Clubman\"]},\"orangeArmy\":{\"boardTroops\":{\"side\":\"ORANGE\",\"leaderPosition\":\"a4\",\"guards\":2,\"troopMap\":{\"a4\":{\"troop\":\"Drake\",\"side\":\"ORANGE\",\"face\":\"AVERS\"},\"b4\":{\"troop\":\"Clubman\",\"side\":\"ORANGE\",\"face\":\"AVERS\"},\"c3\":{\"troop\":\"Spearman\",\"side\":\"ORANGE\",\"face\":\"REVERS\"},\"c4\":{\"troop\":\"Swordsman\",\"side\":\"ORANGE\",\"face\":\"AVERS\"},\"d2\":{\"troop\":\"Monk\",\"side\":\"ORANGE\",\"face\":\"AVERS\"}}},\"stack\":[\"Archer\"],\"captured\":[]}}",
                out.toString()
        );

        out = new ByteArrayOutputStream();
        writer = new PrintWriter(out);
        GameState state20 = new StepAndCapture(pf.pos("d1"), pf.pos("a4")).execute(state19);
        state20.toJSON(writer);
        writer.close();
        assertEquals(
                "{\"result\":\"VICTORY\",\"board\":{\"dimension\":4,\"tiles\":[\"empty\",\"empty\",\"empty\",\"empty\",\"empty\",\"mountain\",\"empty\",\"empty\",\"empty\",\"empty\",\"empty\",\"mountain\",\"empty\",\"empty\",\"empty\",\"empty\"]},\"blueArmy\":{\"boardTroops\":{\"side\":\"BLUE\",\"leaderPosition\":\"a1\",\"guards\":2,\"troopMap\":{\"a1\":{\"troop\":\"Drake\",\"side\":\"BLUE\",\"face\":\"AVERS\"},\"a2\":{\"troop\":\"Swordsman\",\"side\":\"BLUE\",\"face\":\"AVERS\"},\"a3\":{\"troop\":\"Clubman\",\"side\":\"BLUE\",\"face\":\"REVERS\"},\"a4\":{\"troop\":\"Monk\",\"side\":\"BLUE\",\"face\":\"REVERS\"},\"b1\":{\"troop\":\"Clubman\",\"side\":\"BLUE\",\"face\":\"AVERS\"},\"c1\":{\"troop\":\"Spearman\",\"side\":\"BLUE\",\"face\":\"AVERS\"}}},\"stack\":[\"Archer\"],\"captured\":[\"Clubman\",\"Drake\"]},\"orangeArmy\":{\"boardTroops\":{\"side\":\"ORANGE\",\"leaderPosition\":\"off-board\",\"guards\":2,\"troopMap\":{\"b4\":{\"troop\":\"Clubman\",\"side\":\"ORANGE\",\"face\":\"AVERS\"},\"c3\":{\"troop\":\"Spearman\",\"side\":\"ORANGE\",\"face\":\"REVERS\"},\"c4\":{\"troop\":\"Swordsman\",\"side\":\"ORANGE\",\"face\":\"AVERS\"},\"d2\":{\"troop\":\"Monk\",\"side\":\"ORANGE\",\"face\":\"AVERS\"}}},\"stack\":[\"Archer\"],\"captured\":[]}}",
                out.toString()
        );
    }
}


