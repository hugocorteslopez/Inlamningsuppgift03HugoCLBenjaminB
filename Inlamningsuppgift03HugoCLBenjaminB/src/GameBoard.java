import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.awt.BorderLayout.SOUTH;

/**
 * Created by Hugo Cortes Lopez & Benjamin Brankovic
 * Date:    2020-10-23
 * Time:    15:23
 * Project: FifteenGamePuzzle
 */

public class GameBoard extends JFrame {
    JButton[][] tiles = new JButton[4][4];
    int grayRowPosition;
    int grayColPosition;

    public GameBoard() {
        addTiles();
        addNewGameButton();
    }

    //Repainting all tiles to white and setting the text to "blank"
    //Used when New Game is initialized
    public void reset() {
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                tiles[row][col].setBackground(Color.white);
                tiles[row][col].setText("");
            }
        }
    }

    //Sets all tiles in numeric order(solved state) to test isSolved method
    public void solvedPosition() {
        int val = 1;
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                if (val != 16) {
                    tiles[row][col].setText(String.valueOf(val));
                    val++;
                } else {
                    tiles[row][col].setBackground(Color.gray);
                    grayRowPosition = row;
                    grayColPosition = col;
                }
            }
        }
    }

    //Sets numbers on tiles and checks if tile has an assigned number
    //Last tile gets assigned a gray color and its position
    public void shuffleTiles() {
        boolean[] used = new boolean[16];
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                int val = (int) (16 * Math.random());
                while (used[val]) {
                    val = (int) (16 * Math.random());
                }
                used[val] = true;
                if (val != 0)
                    tiles[row][col].setText("" + val);
                else {
                    tiles[row][col].setBackground(Color.gray);
                    grayRowPosition = row;
                    grayColPosition = col;
                }
            }
        }
    }

    //Gets the source from where we clicked
    //Moves tile
    //Checks if current state is solved
    class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Object square = e.getSource();
            outer:
            for (int row = 0; row < 4; row++) {
                for (int col = 0; col < 4; col++) {
                    if (tiles[row][col] == square) {
                        moveTile(row, col);
                        if (isSolved())
                            JOptionPane.showMessageDialog(null, "Grattis! Du klarade av spelet.");
                        break outer;
                    }
                }
            }
        }
    }

    //"Swapping" position
    public void moveTile(int row, int col) {
        if (canMove(row, col)) {
            tiles[grayRowPosition][grayColPosition].setText(tiles[row][col].getText());
            tiles[grayRowPosition][grayColPosition].setBackground(Color.white);
            tiles[row][col].setText("");
            tiles[row][col].setBackground(Color.gray);
            grayRowPosition = row;
            grayColPosition = col;
        }
    }

    //Move condition to check around itself
    public boolean canMove(int row, int col) {
        return (row + 1 == grayRowPosition && col == grayColPosition)
                || (row - 1 == grayRowPosition && col == grayColPosition)
                || (row == grayRowPosition && col + 1 == grayColPosition)
                || (row == grayRowPosition && col - 1 == grayColPosition);
    }

    private boolean isSolved() {
        if (!tiles[3][3].getText().equals(""))
            return false;

        int count = 15;
        for (int row = 3; row >= 0; row--) {
            for (int col = 3; col >= 0; col--) {
                var buttonTextValue = tiles[row][col].getText();
                if (buttonTextValue.equals(""))
                    continue;

                int currentButtonValue = Integer.parseInt(buttonTextValue);
                if (currentButtonValue != count)
                    return false;
                count--;
            }
        }
        return true;
    }

    //Adding a new game button
    private void addNewGameButton() {
        JButton newGameBtn = new JButton("NEW GAME");
        add(newGameBtn, SOUTH);
        newGameBtn.addMouseListener(new NewGameListener());
    }

    //Adding tiles to the GameBoard
    private void addTiles() {
        Container con1 = new Container();
        con1.setSize(600, 600);
        con1.setLayout(new GridLayout(4, 4));
        GameBoard.ButtonListener clicked = new GameBoard.ButtonListener();
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                JButton n = new JButton();
                tiles[row][col] = n;
                tiles[row][col].addActionListener(clicked);
                tiles[row][col].setBackground(Color.white);
                con1.add(tiles[row][col]);
            }
        }
        add(con1);
    }

    private class NewGameListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            reset();
            shuffleTiles();

        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
}
