import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Hugo Cortes Lopez & Benjamin Brankovic
 * Date:    2020-10-26
 * Time:    17:13
 * Project: FifteenGamePuzzle
 */
public class FifteenPuzzleGame {
    public static void init(){
        GameBoard gb = new GameBoard();
        gb.setTitle("Fifteen Game");
        gb.setVisible(true);
        gb.setSize(600, 600);
        gb.setLocationRelativeTo(null);
        //gb.shuffleTiles();
        gb.solvedPosition();
        gb.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
