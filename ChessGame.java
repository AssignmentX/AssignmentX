import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;

public class ChessGame {
    private static String currentPlayer;
    private static boolean currentlyMoving;
    private static int movingFrom;
    private static String selectedPiece;
    private static ChessGameFrame frame;
    private static Color selectedSquaresColor;
    private static Color[] validMoveColors;
    private static boolean[] validMovePositions;
    private static int whiteKing;
    private static int blackKing;
    private static boolean blackIsChecked;
    private static boolean whiteIsChecked;

    public static void main( String args[] ) {

        frame = new ChessGameFrame("Chess - Assignment X"); 
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setBackground( Color.WHITE );     // set frame background color
        frame.setSize( 900, 900 );             // set frame size
        frame.setLocationRelativeTo(null);      // center frame on screen
        frame.setVisible( true );               // display frame
    
        // figure out who goes first
        //Object[] options = {"Black", "White", "Random"};
        //int n = JOptionPane.showOptionDialog(frame, "Who should go first?", "Select First Move", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[2]);
        //String firstMove;
        //if(n == 0)
        //    firstMove = "Black";
        //else if(n == 1)
        //    firstMove = "White";
        //else
        //    firstMove = (new Random().nextInt() % 2 == 0) ? "Black" : "White";

        // set current player
        setCurrentPlayer("White");
        frame.setNorthTextField("White's Move");

        // initialize both players to be not in check
        blackIsChecked = false;
        whiteIsChecked = false;

        // tracks which piece is currently making a move
        currentlyMoving = false;
        movingFrom = -1;

        // tracks the colors and positions of valid moves
        validMoveColors = new Color[64];
        validMovePositions = new boolean[64];
        for(boolean x : validMovePositions)
            x = false;
    }

    // accessors
    public static String getCurrentPlayer(){ return currentPlayer; }
    public static String getSelectedPiece(){ return selectedPiece; }
    public static Color getSelectedSquaresColor() { return selectedSquaresColor; }
    public static ChessGameFrame getFrame(){ return frame; }
    public static Color[] getValidMoveColors(){ return validMoveColors; }
    public static boolean[] getValidMovePositions(){ return validMovePositions; }
    public static boolean isSpaceEmpty(int pos) { return (frame.getBoard().squareAt(pos).getPiece() != null); }
    public static int getWhiteKingPos() { return whiteKing; }
    public static int getBlackKingPos() { return blackKing; }
    public static boolean isMoving() { return currentlyMoving; }
    public static int getMovingFrom() { return movingFrom; }
    public static boolean isWhiteChecked() { return whiteIsChecked; }
    public static boolean isBlackChecked() { return blackIsChecked; }

    // mutators
    public static void setCurrentPlayer(String player){ currentPlayer = player; }
    public static void setSelectedPiece(String piece){ selectedPiece = piece; }
    public static void setCurrentlyMoving(boolean val){ currentlyMoving = val; }
    public static void setSelectedSquaresColor(Color c) { selectedSquaresColor = c; }
    public static void setMovingFrom(int pos){ movingFrom = pos; }
    public static void setWhiteKingPos(int pos){ whiteKing = pos; }
    public static void setBlackKingPos(int pos){ blackKing = pos; }
    public static void blackIsChecked(boolean b){ blackIsChecked = b; }
    public static void whiteIsChecked(boolean b){ whiteIsChecked = b; }
}