import java.awt.Color;
import javax.swing.*;
import java.awt.event.*;

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
    private static boolean canBlackBeChecked;
    private static boolean canWhiteBeChecked;
    private static boolean canWhiteBeCheckMated;
    private static boolean canBlackBeCheckMated;

    public static void main( String args[] ) {

        frame = new ChessGameFrame("Chess - Assignment X");
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setBackground( Color.WHITE );     // set frame background color
        frame.setSize( 900, 900 );   // set frame size
        frame.setLocationRelativeTo(null);      // center frame on screen
        frame.setVisible( true );               // display frame

        // START FRAME MENU BAR
        JMenuBar menu = new JMenuBar();
        JMenu file_menu = new JMenu("File");
        file_menu.setMnemonic('F');

        JMenuItem new_item = new JMenuItem("New");
        new_item.setMnemonic('N');
        file_menu.add(new_item);

        JMenuItem load_item = new JMenuItem("Load");
        load_item.setMnemonic('L');
        file_menu.add(load_item);

        JMenuItem save_item = new JMenuItem("Save");
        save_item.setMnemonic('S');
        file_menu.add(save_item);

        JMenuItem exit_item = new JMenuItem("Exit");
        exit_item.setMnemonic('x');
        file_menu.add(exit_item);
        exit_item.addActionListener(
            new ActionListener() // anonymous inner class
            {
                public void actionPerformed( ActionEvent event )
                {
                    System.exit( 0 );
                }
            }
        );
        menu.add(file_menu);
        frame.setJMenuBar(menu);
        // END FRAME MENU BAR

        // set current player
        setCurrentPlayer("White");
        frame.setNorthTextField("White's Move");

        // initialize both players to be not in check, checkmate
        blackIsChecked = false;
        whiteIsChecked = false;
        canWhiteBeCheckMated = false;
        canBlackBeCheckMated = false;

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

    // get current player
    public static String getCurrentPlayer(){ return currentPlayer; }
    // get current clicked piece
    public static String getSelectedPiece(){ return selectedPiece; }
    // get the color of the clicked panel
    public static Color getSelectedSquaresColor() { return selectedSquaresColor; }
    // get the ChessGameFrame instance
    public static ChessGameFrame getFrame(){ return frame; }
    // get the array storing the original colors of the valid move squares
    public static Color[] getValidMoveColors(){ return validMoveColors; }
    // get array storing the valid move positions
    public static boolean[] getValidMovePositions(){ return validMovePositions; }
    // returns true or false if a space is empty
    public static boolean isSpaceEmpty(int pos) { return (frame.getBoard().squareAt(pos).getPiece() != null); }
    // get the pos of the white king
    public static int getWhiteKingPos() { return whiteKing; }
    // get the pos of the black king
    public static int getBlackKingPos() { return blackKing; }
    // bool that represents if a piece is currently selected
    public static boolean isMoving() { return currentlyMoving; }
    // stores the pos of the selected piece
    public static int getMovingFrom() { return movingFrom; }
    // bool that determines if white is in check
    public static boolean isWhiteChecked() { return whiteIsChecked; }
    // bool that determines if black is in check
    public static boolean isBlackChecked() { return blackIsChecked; }
    // bool that determines if a move can lead to check
    public static boolean canWhiteBeChecked() { return canWhiteBeChecked; }
    public static boolean canBlackBeChecked() { return canBlackBeChecked; }
    // bools that determine if players are checkmated
    public static boolean canWhiteBeCheckMated() { return canWhiteBeCheckMated; }
    public static boolean canBlackBeCheckMated() { return canBlackBeCheckMated; }


    // mutators

    // set the current player
    public static void setCurrentPlayer(String player){ currentPlayer = player; }
    // set the selected piece
    public static void setSelectedPiece(String piece){ selectedPiece = piece; }
    // set the bool indicating a piece is selected
    public static void setCurrentlyMoving(boolean val){ currentlyMoving = val; }
    // set the color of the selected square
    public static void setSelectedSquaresColor(Color c) { selectedSquaresColor = c; }
    // set the pos the piece is selected on
    public static void setMovingFrom(int pos){ movingFrom = pos; }
    // set the pos for the kings
    public static void setWhiteKingPos(int pos){ whiteKing = pos; }
    public static void setBlackKingPos(int pos){ blackKing = pos; }
    // set the bool indicating if a player is checked or not
    public static void blackIsChecked(boolean b){ blackIsChecked = b; }
    public static void whiteIsChecked(boolean b){ whiteIsChecked = b; }
    // set the bool indicating if a player detects check on a move
    public static void canWhiteBeChecked(boolean b){ canWhiteBeChecked = b; }
    public static void canBlackBeChecked(boolean b){ canBlackBeChecked = b; }
    // checkmate accessors
    public static void whiteIsCheckMated() { canWhiteBeCheckMated = true; }
    public static void blackIsCheckMated() { canBlackBeCheckMated = true; }
    public static void whiteIsNotCheckMated() { canWhiteBeCheckMated = false; }
    public static void blackIsNotCheckMated() { canBlackBeCheckMated = false; }
}