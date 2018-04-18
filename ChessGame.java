import java.awt.Color;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;

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
        //creates game frame
        init_frame();

        // frame menu bar
        JMenuBar menu = new JMenuBar();
        JMenu file_menu = new JMenu("Game");
        file_menu.setMnemonic('G');
        JMenuItem new_item = new JMenuItem("New");
        new_item.setMnemonic('N');
        file_menu.add(new_item);
        new_item.addActionListener(
                new ActionListener()
                {
                    public void actionPerformed( ActionEvent event )
                    {
                        frame.clearTextArea();
                        frame.resetBoard();
                        init_players();
                    }
                }
        );
        JMenuItem load_item = new JMenuItem("Load");
        load_item.setMnemonic('L');
        file_menu.add(load_item);
        load_item.addActionListener(
                new ActionListener()
                {
                    public void actionPerformed( ActionEvent event )
                    {
                        load_game();
                    }
                }
        );
        JMenuItem save_item = new JMenuItem("Save");
        save_item.setMnemonic('S');
        file_menu.add(save_item);
        save_item.addActionListener(
                new ActionListener()
                {
                    public void actionPerformed( ActionEvent event )
                    {
                        save_game(frame,frame.getBoard(),frame.getBoard().getSquares());

                    }
                }
        );
        JMenuItem exit_item = new JMenuItem("Exit");
        exit_item.setMnemonic('x');
        file_menu.add(exit_item);
        exit_item.addActionListener(
            new ActionListener()
            {
                public void actionPerformed( ActionEvent event )
                {
                    System.exit( 0 );
                }
            }
        );
        menu.add(file_menu);
        frame.setJMenuBar(menu);

        // sets initial player states
        init_players();
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

    public static void setFrame(ChessGameFrame new_frame){ frame = new_frame; }
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

    //helpers
    public static void init_players(){

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

    public static void init_frame(){
        frame = new ChessGameFrame("Chess - Assignment X");
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setBackground( Color.WHITE );     // set frame background color
        frame.setSize( 900, 900 );   // set frame size
        frame.setLocationRelativeTo(null);      // center frame on screen
        frame.setVisible( true );               // display frame
    }

    public static void save_game(ChessGameFrame game_frame, ChessBoardPanel board_panel, ChessSquarePanel[] square_panel){
        Object[] save_file = new Object[square_panel.length+2];

        for(int i = 0; i < square_panel.length; i++){
            save_file[i] = square_panel[i];
        }
        save_file[square_panel.length] = game_frame;
        save_file[square_panel.length+1] = board_panel;

        String filename = "file.ser";
        // Serialization
        try
        {
            //Saving of object in a file
            FileOutputStream file = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(file);

            // Method for serialization of object
            out.writeObject(save_file);
            out.close();
            file.close();

            System.out.println("Object has been serialized");

        }
        catch(IOException ex)
        {
            System.out.println("IOException is caught");
        }
    }

    public static void load_game(){
        String filename = "file.ser";

        // Deserialization
        try
        {
            // Reading the object from a file
            FileInputStream file = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(file);

            // deserialize objects
            Object[] saved = (Object[])in.readObject();
            in.close();
            file.close();

            // organizes and casts loaded data
            ChessSquarePanel[] loaded_squares = new ChessSquarePanel[64];
            for(int i = 0; i != 64; i++){
                loaded_squares[i] = (ChessSquarePanel)saved[i];
            }
            ChessGameFrame loaded_frame = (ChessGameFrame)saved[64];
            ChessBoardPanel loaded_panel = (ChessBoardPanel)saved[65];

            // clear text area and replace with saved game text
            frame.clearTextArea();
            frame.appendTextArea(loaded_frame.getTextArea());
            //setFrame(loaded_frame);

            // replaces existing chessboard with saved chessboard
            frame.setChessBoard(loaded_panel);

            //replaces existing square data array with saved squares array
            frame.getBoard().setSquares(loaded_squares);

            System.out.println("LOAD COMPLETE");
        }
        catch(IOException ex)
        {
            System.out.println("IOException is caught");
        }
        catch(ClassNotFoundException ex)
        {
            System.out.println("ClassNotFoundException is caught");
        }
    }
}