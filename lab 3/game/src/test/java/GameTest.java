import org.example.Main;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    @BeforeEach
    public void setUp() {
        Main.setupGrid();
    }

    @Test
    public void testSetupGrid_PlayerStartsAtTopLeft() {
        assertEquals(Main.PLAYER_ICON, Main.gameGrid[0][0], "0, 0 should be player pos");
    }

    @Test
    public void testSetupGrid_AllOtherCellsAreEmpty() {
        for (int row = 0; row < Main.GRID_SIZE; row++) {
            for (int col = 0; col < Main.GRID_SIZE; col++) {
                if (row != 0 || col != 0) { // 0, 0 is player position
                    assertEquals(Main.EMPTY_CELL, Main.gameGrid[row][col], "cells other than 0,0 should be empty");
                }
            }
        }
    }

    @Test
    public void testSetGoalPosition_GoalIsAtBottomRight() {
        Main.setGoalPosition();

        assertEquals(Main.GOAL_ICON, Main.gameGrid[Main.GRID_SIZE - 1][Main.GRID_SIZE - 1],
                "goal should be in right bottom corner");
    }

    @Test
    public void testPlaceObstaclesRandomly_ShouldNotBeObstaclesOnPlayerandGoalPosition() {
        Main.setGoalPosition();
        Main.placeObstaclesRandomly();

        assertNotEquals(Main.OBSTACLE_ICON, Main.gameGrid[0][0], "Players starting position should not have obstacle");
        assertNotEquals(Main.OBSTACLE_ICON, Main.gameGrid[Main.GRID_SIZE - 1][Main.GRID_SIZE - 1],
                "Goal position should not have obstacle");
    }

    @Test
    public void testIsMoveAllowed_BoundaryConditions() {
        assertFalse(Main.isMoveAllowed(0, -1));
        assertFalse(Main.isMoveAllowed(-1, 0));
        assertFalse(Main.isMoveAllowed(0, Main.GRID_SIZE));
        assertFalse(Main.isMoveAllowed(Main.GRID_SIZE, 0));
    }

    @Test
    public void testIsMoveAllowed_EmptyPosition() {
        assertTrue(Main.isMoveAllowed(0, 1), "Move should be valid");
    }

    @Test
    public void testIsMoveAllowed_ObstaclePosition() {
        Main.gameGrid[1][1] = Main.OBSTACLE_ICON;
        assertFalse(Main.isMoveAllowed(1, 1), "Move should be invalid, there is obstacle");
    }
    @Test
    public void testIsMoveAllowed_GoalPosition() {
        Main.gameGrid[2][2] = Main.GOAL_ICON;
        assertTrue(Main.isMoveAllowed(2, 2), "Move should be valid");
    }

    @Test
    public void testPlayerMovement_ValidMoveUpdatesPosition() {
        Main.gameGrid[1][0] = Main.EMPTY_CELL;
        int initialRow = Main.playerPosX;

        Main.playerPosX++;
        assertEquals(initialRow + 1, Main.playerPosX, "Player's X position should increment after valid move");
    }

    @Test
    public void testPlayerMovement_InvalidMoveDoesNotUpdatePosition() {
        Main.gameGrid[0][1] = Main.OBSTACLE_ICON;
        int initialRow = Main.playerPosX;
        int initialCol = Main.playerPosY;

        if (Main.isMoveAllowed(0, 1)) {
            Main.playerPosY++;
        }
        assertEquals(initialRow, Main.playerPosX, "Player's X position should not change after invalid move");
        assertEquals(initialCol, Main.playerPosY, "Player's Y position should not change after invalid move");
    }
}
