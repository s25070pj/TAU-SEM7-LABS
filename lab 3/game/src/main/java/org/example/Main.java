package org.example;

import java.util.Random;
import java.util.Scanner;

public class Main {
    static final int GRID_SIZE = 5;
    static final char EMPTY_CELL = '.';
    static final char PLAYER_ICON = 'A';
    static final char GOAL_ICON = 'B';
    static final char OBSTACLE_ICON = 'X';

    static char[][] gameGrid = new char[GRID_SIZE][GRID_SIZE];
    static int playerPosX = 0;
    static int playerPosY = 0;

    public static void main(String[] args) {
        setupGrid();
        placeObstaclesRandomly();
        setGoalPosition();
        startGameLoop();
    }

    public static void setupGrid() {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                gameGrid[row][col] = EMPTY_CELL;
            }
        }
        gameGrid[playerPosX][playerPosY] = PLAYER_ICON;
    }

    public static void placeObstaclesRandomly() {
        Random rng = new Random();
        int obstacleCount = GRID_SIZE + 2;

        while (obstacleCount > 0) {
            int randRow = rng.nextInt(GRID_SIZE);
            int randCol = rng.nextInt(GRID_SIZE);

            if (gameGrid[randRow][randCol] == EMPTY_CELL && !(randRow == 0 && randCol == 0)) {
                gameGrid[randRow][randCol] = OBSTACLE_ICON;
                obstacleCount--;
            }
        }
    }

    public static void setGoalPosition() {
        gameGrid[GRID_SIZE - 1][GRID_SIZE - 1] = GOAL_ICON;
    }

    public static void displayGrid() {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                System.out.print(gameGrid[row][col] + " ");
            }
            System.out.println();
        }
    }

    public static void startGameLoop() {
        Scanner inputScanner = new Scanner(System.in);

        while (true) {
            displayGrid();
            System.out.println("Enter your move (WASD): ");
            char userMove = inputScanner.next().toUpperCase().charAt(0);

            int targetRow = playerPosX;
            int targetCol = playerPosY;

            switch (userMove) {
                case 'W':
                    targetRow--;
                    break;
                case 'A':
                    targetCol--;
                    break;
                case 'S':
                    targetRow++;
                    break;
                case 'D':
                    targetCol++;
                    break;
                default:
                    System.out.println("Provide valid input!");
                    continue;
            }

            if (isMoveAllowed(targetRow, targetCol)) {
                gameGrid[playerPosX][playerPosY] = EMPTY_CELL;
                playerPosX = targetRow;
                playerPosY = targetCol;
                gameGrid[playerPosX][playerPosY] = PLAYER_ICON;

                if (playerPosX == GRID_SIZE - 1 && playerPosY == GRID_SIZE - 1) {
                    displayGrid();
                    System.out.println("You won!");
                    break;
                }
            } else {
                System.out.println("You cant go there");
            }
        }

        inputScanner.close();
    }

    public static boolean isMoveAllowed(int row, int col) {
        return row >= 0 && row < GRID_SIZE && col >= 0 && col < GRID_SIZE && gameGrid[row][col] != OBSTACLE_ICON;
    }
}