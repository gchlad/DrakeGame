package suite03;

import org.junit.Test;
import drake.*;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.Assert.*;

public class BoardTroopsTest {

    @Test
    public void classStructure() {
        // All attributes private and final
        for (Field f : BoardTroops.class.getFields()) {
            assertTrue(Modifier.isPrivate(f.getModifiers()));
            assertTrue(Modifier.isFinal(f.getModifiers()));
        }
    }

    @Test
    public void placingTroops() {
        Board board = new Board(3);
        PositionFactory pf = board.positionFactory();
        StandardDrakeSetup setup = new StandardDrakeSetup();

        // Jednotky na desce začínají prázdné, žádný vůdce,
        // žádné stráže
        BoardTroops troops1 = new BoardTroops(PlayingSide.BLUE);

        assertSame(PlayingSide.BLUE, troops1.playingSide());
        assertSame(TilePos.OFF_BOARD, troops1.leaderPosition());
        assertSame(0, troops1.guards());
        assertSame(false, troops1.isLeaderPlaced());
        assertSame(false, troops1.isPlacingGuards());

        checkEmpty(board, troops1);

        // Nejdříve postavíme vůdce
        Troop drake = setup.DRAKE;
        BoardTroops troops2 = troops1.placeTroop(drake, pf.pos("a2"));
        assertNotSame(troops1, troops2);
        checkEmpty(board, troops1);

        assertSame(0, troops2.guards());
        assertSame(true, troops2.isLeaderPlaced());
        assertSame(true, troops2.isPlacingGuards());
        assertEquals(Collections.singleton(pf.pos("a2")), troops2.troopPositions());

        assertEquals(Optional.empty(), troops2.at(pf.pos("a1")));
        assertSame(drake, troops2.at(pf.pos("a2")).get().troop());
        assertEquals(Optional.empty(), troops2.at(pf.pos("a3")));
        assertEquals(Optional.empty(), troops2.at(pf.pos("b1")));
        assertEquals(Optional.empty(), troops2.at(pf.pos("b2")));
        assertEquals(Optional.empty(), troops2.at(pf.pos("b3")));
        assertEquals(Optional.empty(), troops2.at(pf.pos("c1")));
        assertEquals(Optional.empty(), troops2.at(pf.pos("c2")));
        assertEquals(Optional.empty(), troops2.at(pf.pos("c3")));

        assertSame(TroopFace.AVERS, troops2.at(pf.pos("a2")).get().face());
        assertSame(troops2.playingSide(), troops2.at(pf.pos("a2")).get().side());
        assertEquals(pf.pos("a2"), troops2.leaderPosition());

        // První stráž
        Troop clubman1 = setup.CLUBMAN;
        BoardTroops troops3 = troops2.placeTroop(clubman1, pf.pos("a1"));
        assertSame(1, troops3.guards());
        assertSame(true, troops3.isLeaderPlaced());
        assertSame(true, troops3.isPlacingGuards());
        assertEquals(
                new HashSet<BoardPos>(
                        Arrays.asList(
                                pf.pos("a1"),
                                pf.pos("a2")
                        )
                ),
                troops3.troopPositions());

        assertSame(clubman1, troops3.at(pf.pos("a1")).get().troop());
        assertSame(drake, troops3.at(pf.pos("a2")).get().troop());
        assertEquals(Optional.empty(), troops3.at(pf.pos("a3")));
        assertEquals(Optional.empty(), troops3.at(pf.pos("b1")));
        assertEquals(Optional.empty(), troops3.at(pf.pos("b2")));
        assertEquals(Optional.empty(), troops3.at(pf.pos("b3")));
        assertEquals(Optional.empty(), troops3.at(pf.pos("c1")));
        assertEquals(Optional.empty(), troops3.at(pf.pos("c2")));
        assertEquals(Optional.empty(), troops3.at(pf.pos("c3")));

        // Druhá stráž
        Troop clubman2 = setup.CLUBMAN;
        BoardTroops troops4 = troops3.placeTroop(clubman2, pf.pos("b2"));
        assertSame(2, troops4.guards());
        assertSame(true, troops4.isLeaderPlaced());
        assertSame(false, troops4.isPlacingGuards());
        assertEquals(
                new HashSet<BoardPos>(
                        Arrays.asList(
                                pf.pos("a1"),
                                pf.pos("a2"),
                                pf.pos("b2")
                        )
                ),
                troops4.troopPositions());

        assertSame(clubman1, troops4.at(pf.pos("a1")).get().troop());
        assertSame(drake, troops4.at(pf.pos("a2")).get().troop());
        assertEquals(Optional.empty(), troops4.at(pf.pos("a3")));
        assertEquals(Optional.empty(), troops4.at(pf.pos("b1")));
        assertSame(clubman2, troops4.at(pf.pos("b2")).get().troop());
        assertEquals(Optional.empty(), troops4.at(pf.pos("b3")));
        assertEquals(Optional.empty(), troops4.at(pf.pos("c1")));
        assertEquals(Optional.empty(), troops4.at(pf.pos("c2")));
        assertEquals(Optional.empty(), troops4.at(pf.pos("c3")));

        // Nějaká další jednotka
        Troop spearman = setup.SPEARMAN;
        BoardTroops troops5 = troops4.placeTroop(spearman, pf.pos("c1"));
        assertSame(2, troops5.guards());
        assertSame(true, troops5.isLeaderPlaced());
        assertSame(false, troops5.isPlacingGuards());
        assertEquals(
                new HashSet<BoardPos>(
                        Arrays.asList(
                                pf.pos("a1"),
                                pf.pos("a2"),
                                pf.pos("b2"),
                                pf.pos("c1")
                        )
                ),
                troops5.troopPositions());

        assertSame(clubman1, troops5.at(pf.pos("a1")).get().troop());
        assertSame(drake, troops5.at(pf.pos("a2")).get().troop());
        assertEquals(Optional.empty(), troops5.at(pf.pos("a3")));
        assertEquals(Optional.empty(), troops5.at(pf.pos("b1")));
        assertSame(clubman2, troops5.at(pf.pos("b2")).get().troop());
        assertEquals(Optional.empty(), troops5.at(pf.pos("b3")));
        assertSame(spearman, troops5.at(pf.pos("c1")).get().troop());
        assertEquals(Optional.empty(), troops5.at(pf.pos("c2")));
        assertEquals(Optional.empty(), troops5.at(pf.pos("c3")));
    }

    @Test
    public void movingTroops() {
        Board board = new Board(3);
        PositionFactory pf = board.positionFactory();

        BoardTroops troops = new BoardTroops(PlayingSide.ORANGE);
        assertSame(PlayingSide.ORANGE, troops.playingSide());

        StandardDrakeSetup setup = new StandardDrakeSetup();
        Troop drake = setup.DRAKE;
        Troop clubman1 = setup.CLUBMAN;
        Troop clubman2 = setup.CLUBMAN;
        Troop spearman = setup.SPEARMAN;

        try {
            troops.troopStep(pf.pos("a2"), pf.pos("a3"));
            fail();
        } catch (IllegalStateException e) {
            // Dokud nestojí vůdce a stráže, není možné hýbat s jednotkami.
        }

        troops = troops.placeTroop(drake, pf.pos("a2"));

        try {
            troops.troopStep(pf.pos("a2"), pf.pos("a3"));
            fail();
        } catch (IllegalStateException e) {
            // Dokud nestojí vůdce a stráže, není možné hýbat s jednotkami.
        }

        troops = troops.placeTroop(clubman1, pf.pos("a1"));

        try {
            troops.troopStep(pf.pos("a2"), pf.pos("a3"));
            fail();
        } catch (IllegalStateException e) {
            // Dokud nestojí vůdce a stráže, není možné hýbat s jednotkami.
        }


        troops = troops
                .placeTroop(clubman2, pf.pos("b2"))
                .placeTroop(spearman, pf.pos("c1"));

        //Test výchozího umístění jednotek
        assertSame(clubman1, troops.at(pf.pos("a1")).get().troop());
        assertSame(drake, troops.at(pf.pos("a2")).get().troop());
        assertEquals(Optional.empty(), troops.at(pf.pos("a3")));
        assertEquals(Optional.empty(), troops.at(pf.pos("b1")));
        assertSame(clubman2, troops.at(pf.pos("b2")).get().troop());
        assertEquals(Optional.empty(), troops.at(pf.pos("b3")));
        assertSame(spearman, troops.at(pf.pos("c1")).get().troop());
        assertEquals(Optional.empty(), troops.at(pf.pos("c2")));
        assertEquals(Optional.empty(), troops.at(pf.pos("c3")));

        //Test výchozích stran (face) jednotek
        assertEquals(TroopFace.AVERS, troops.at(pf.pos("a1")).get().face());
        assertEquals(TroopFace.AVERS, troops.at(pf.pos("a2")).get().face());
        assertEquals(TroopFace.AVERS, troops.at(pf.pos("b2")).get().face());
        assertEquals(TroopFace.AVERS, troops.at(pf.pos("c1")).get().face());

        //Pohyb drake z a2 na a3
        troops = troops.troopStep(pf.pos("a2"), pf.pos("a3"));

        //Test umístění jednotek po pohybu jednotky z a2 na a3
        assertSame(clubman1, troops.at(pf.pos("a1")).get().troop());
        assertEquals(Optional.empty(), troops.at(pf.pos("a2")));
        assertSame(drake, troops.at(pf.pos("a3")).get().troop());
        assertEquals(Optional.empty(), troops.at(pf.pos("b1")));
        assertSame(clubman2, troops.at(pf.pos("b2")).get().troop());
        assertSame(drake, troops.at(pf.pos("a3")).get().troop());
        assertEquals(Optional.empty(), troops.at(pf.pos("b3")));
        assertSame(spearman, troops.at(pf.pos("c1")).get().troop());
        assertEquals(Optional.empty(), troops.at(pf.pos("c2")));
        assertEquals(Optional.empty(), troops.at(pf.pos("c3")));

        //Test zda jednotka po pohybu změnila stranu (face)
        assertEquals(TroopFace.REVERS, troops.at(pf.pos("a3")).get().face());

        // Pozor na pozici vůdce
        assertEquals(pf.pos("a3"), troops.leaderPosition());

        try {
            troops.troopStep(pf.pos("a2"), pf.pos("c2"));
            fail();
        } catch (IllegalArgumentException e) {
            // Není možné se pohnout z prázného políčka
        }

        try {
            troops.troopStep(pf.pos("c1"), pf.pos("a3"));
            fail();
        } catch (IllegalArgumentException e) {
            // Není možné se pohnout na políčko s jednotkou
        }

        //Pohyb drake z a3 na b3
        troops = troops.troopStep(pf.pos("a3"), pf.pos("b3"));
        //Test zda jednotka po pohybu znovu změnila stranu (face)
        assertEquals(TroopFace.AVERS, troops.at(pf.pos("b3")).get().face());
    }

    @Test
    public void flippingTroops() {
        Board board = new Board(3);
        PositionFactory pf = board.positionFactory();

        BoardTroops troops = new BoardTroops(PlayingSide.ORANGE);
        assertSame(PlayingSide.ORANGE, troops.playingSide());

        StandardDrakeSetup setup = new StandardDrakeSetup();
        Troop drake = setup.DRAKE;
        Troop clubman1 = setup.CLUBMAN;
        Troop clubman2 = setup.CLUBMAN;

        try {
            troops.troopFlip(pf.pos("a2"));
            fail();
        } catch (IllegalStateException e) {
            // Dokud nestojí vůdce a stráže, není možné hýbat s jednotkami.
        }

        troops = troops.placeTroop(drake, pf.pos("a2"));

        try {
            troops.troopFlip(pf.pos("a2"));
            fail();
        } catch (IllegalStateException e) {
            // Dokud nestojí vůdce a stráže, není možné hýbat s jednotkami.
        }

        troops = troops.placeTroop(clubman1, pf.pos("a1"));

        try {
            troops.troopFlip(pf.pos("a2"));
            fail();
        } catch (IllegalStateException e) {
            // Dokud nestojí vůdce a stráže, není možné hýbat s jednotkami.
        }


        troops = troops
                .placeTroop(clubman2, pf.pos("b2"))
                .troopFlip(pf.pos("a2"));

        assertSame(drake, troops.at(pf.pos("a2")).get().troop());
        assertSame(TroopFace.REVERS, troops.at(pf.pos("a2")).get().face());

        try {
            troops.troopFlip(pf.pos("a3"));
            fail();
        } catch (IllegalArgumentException e) {
            // Není možné otočit jednotku na prázdném políčku
        }
    }

    @Test
    public void removingTroops() {
        Board board = new Board(3);
        PositionFactory pf = board.positionFactory();

        BoardTroops troops = new BoardTroops(PlayingSide.ORANGE);
        assertSame(PlayingSide.ORANGE, troops.playingSide());

        StandardDrakeSetup setup = new StandardDrakeSetup();
        Troop drake = setup.DRAKE;
        Troop clubman1 = setup.CLUBMAN;
        Troop clubman2 = setup.CLUBMAN;

        try {
            troops.removeTroop(pf.pos("a2"));
            fail();
        } catch (IllegalStateException e) {
            // Dokud nestojí vůdce a stráže, není možné hýbat s jednotkami.
        }

        troops = troops.placeTroop(drake, pf.pos("a2"));

        try {
            troops.removeTroop(pf.pos("a2"));
            fail();
        } catch (IllegalStateException e) {
            // Dokud nestojí vůdce a stráže, není možné hýbat s jednotkami.
        }

        troops = troops.placeTroop(clubman1, pf.pos("a1"));

        try {
            troops.removeTroop(pf.pos("a2"));
            fail();
        } catch (IllegalStateException e) {
            // Dokud nestojí vůdce a stráže, není možné hýbat s jednotkami.
        }


        troops = troops.placeTroop(clubman2, pf.pos("b2"));

        try {
            troops.removeTroop(pf.pos("a3"));
            fail();
        } catch (IllegalArgumentException e) {
            // Není možné odstranit jednotku na prázdném políčku
        }

        troops = troops.removeTroop(pf.pos("a2"));

        assertSame(Optional.empty(), troops.at(pf.pos("a2")));
        assertSame(TilePos.OFF_BOARD, troops.leaderPosition());
        assertFalse(troops.isLeaderPlaced());
    }

    private void checkEmpty(Board board, BoardTroops boardTroops) {
        PositionFactory pf = board.positionFactory();
        assertEquals(Optional.empty(), boardTroops.at(pf.pos("a1")));
        assertEquals(Optional.empty(), boardTroops.at(pf.pos("a2")));
        assertEquals(Optional.empty(), boardTroops.at(pf.pos("a3")));
        assertEquals(Optional.empty(), boardTroops.at(pf.pos("b1")));
        assertEquals(Optional.empty(), boardTroops.at(pf.pos("b2")));
        assertEquals(Optional.empty(), boardTroops.at(pf.pos("b3")));
        assertEquals(Optional.empty(), boardTroops.at(pf.pos("c1")));
        assertEquals(Optional.empty(), boardTroops.at(pf.pos("c2")));
        assertEquals(Optional.empty(), boardTroops.at(pf.pos("c3")));

        assertEquals(Collections.emptySet(), boardTroops.troopPositions());
    }
}


