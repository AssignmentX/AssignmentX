import java.awt.Color;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
    private static boolean whiteIsStaleMated;
    private static boolean blackIsStaleMated;
    private static int[] threeFoldRepitition;
    private static boolean[] disengageEnPassant;
    // used for upgrading a pawn to a selected piece
    private static boolean playerIsSelectingAPiece;
    private static int superPawn;
    // used to store squares involved in checkmate for highlighting
    private static boolean[] checkMateHighlighting;
    // sound enable / disable
    private static boolean voiceAssist;
    private static boolean soundEffects;

    // sounds
    private static Sound click_sound;
    private static Sound error_sound;
    private static Sound check_sound;
    private static Sound checkMate_sound;
    private static Sound staleMate_sound;
    private static Sound draw_sound;
    private static Sound resign_sound;
    private static HashMap<String, Sound> soundMap;

    public static void main( String args[] ) {
        // default sound to false before sound selection
        voiceAssist = false;
        soundEffects = false;

        //creates game frame
        init_frame();

        // frame menu bar
        JMenuBar menu = new JMenuBar();
        JMenu file_menu = new JMenu("File");
        file_menu.setMnemonic('F');
        JMenuItem new_item = new JMenuItem("New");
        new_item.setMnemonic('N');
        file_menu.add(new_item);
        new_item.addActionListener(
            new ActionListener()
            {
                public void actionPerformed( ActionEvent event )
                {
                    if(voiceAssist)
                        resign_sound.play();
                    new_game();
                    String msg = "Game has been reset.";
                    JOptionPane.showMessageDialog(frame, msg, "Success", JOptionPane.PLAIN_MESSAGE);
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
        JMenu game_menu = new JMenu("Game");
        game_menu.setMnemonic('G');
        new_item = new JMenuItem("Offer Draw");
        new_item.setMnemonic('D');
        game_menu.add(new_item);
        new_item.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent event){
                    if(voiceAssist)
                        draw_sound.play();
                    JOptionPane.showMessageDialog(frame, "The game was a draw.", "Draw", JOptionPane.PLAIN_MESSAGE);
                    new_game();
                }
            }
        );
        
        new_item = new JMenuItem("Sound");
        new_item.setMnemonic('S');
        game_menu.add(new_item);
        new_item.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent event){
                    soundPrompt();
                }
            }
        );
        menu.add(game_menu);
        frame.setJMenuBar(menu);
        frame.validate();

        // sets initial player states
        init_players();
    }

    // accessors
    // get current player
    public static String getCurrentPlayer(){ return currentPlayer; }
    // get enemy player
    public static String getEnemyPlayer(){
        if(currentPlayer.equals("White"))
            return "Black";
        else
            return "White";
    }
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
    public static boolean isSpaceEmpty(int pos) { return (ChessGame.getFrame().getBoard().squareAt(pos).getPiece() != null); }
    // get the pos of the white king
    public static int getWhiteKingPos() { return whiteKing; }
    // get the pos of the black king
    public static int getBlackKingPos() { return blackKing; }
    // get the pos of the current player's king
    public static int getCurrentKingPos() {
        if(ChessGame.getCurrentPlayer().equals("White"))
            return whiteKing;
        else
            return blackKing;
    }
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
    // is player stalemated?
    public static boolean isWhiteStaleMated() { return whiteIsStaleMated; }
    public static boolean isBlackStaleMated() { return blackIsStaleMated; }
    // returns threefoldrepitition array
    public static int[] getThreeFoldRepitition() { return threeFoldRepitition; }
    // accessor for en passant
    public static boolean getEnPassant(int i) { return disengageEnPassant[i]; }
    // get array highlighting for checkmate
    public static boolean[] getCheckMateHighlighting() { return checkMateHighlighting; }
    // get sound objects
    public static Sound getClickSound(){ return click_sound; }
    public static Sound getErrorSound(){ return error_sound; }
    public static Sound getCheckSound(){ return check_sound; }
    public static Sound getCheckMateSound(){ return checkMate_sound; }
    public static Sound getStaleMateSound(){ return staleMate_sound; }
    public static Sound getDrawSound(){ return draw_sound; }
    public static Sound getResignSound() { return resign_sound; }
    public static boolean voiceAssist() { return voiceAssist; }
    public static boolean soundEffects() { return soundEffects; }
    // get sound map
    public static HashMap<String,Sound> soundMap() { return soundMap; }
    // is player selecting a piece?
    public static boolean isPlayerSelectingAPiece() { return playerIsSelectingAPiece; }
    // get pawns pos
    public static int getSuperPawn() { return superPawn; }
    // get positions on the end of the board
    public static ArrayList<Integer> getEndPos() {
        ArrayList<Integer> list = new ArrayList<Integer>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 56, 57, 58, 59, 60, 61, 62, 63));
        return list;
    }
   
    // mutators

    public static void setFrame(ChessGameFrame new_frame){ frame = new_frame; }
    // set the current player
    public static void setCurrentPlayer(String player){ currentPlayer = player; }
    // change the player
    public static void changeCurrentPlayer(){
        if(currentPlayer.equals("White"))
            currentPlayer = "Black";
        else
            currentPlayer = "White";
    }
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
    public static void canPlayerBeChecked(boolean b) {
        if(ChessGame.getCurrentPlayer().equals("White"))
            canWhiteBeChecked = b;
        else
            canBlackBeChecked = b;
    }
    // checkmate mutators
    public static void whiteIsCheckMated() { canWhiteBeCheckMated = true; }
    public static void blackIsCheckMated() { canBlackBeCheckMated = true; }
    public static void whiteIsNotCheckMated() { canWhiteBeCheckMated = false; }
    public static void blackIsNotCheckMated() { canBlackBeCheckMated = false; }
    // stalemate mutators
    public static void whiteIsStaleMated() { whiteIsStaleMated = true; }
    public static void blackIsStaleMated() { blackIsStaleMated = true; }
    public static void whiteIsNotStaleMated() { whiteIsStaleMated = false; }
    public static void blackIsNotStaleMated() { blackIsStaleMated = false; }
    // en passant mutator
    public static void setEnPassant(int i, boolean b) { disengageEnPassant[i] = b; }
    // checkmate highlighting array
    public static void setCheckMateHighlighting(int i) { checkMateHighlighting[i] = true; }
    public static void resetCheckMateHighlighting() { for(int i = 0; i < 64; i++) checkMateHighlighting[i] = false; }
    // piece selecting when pawn makes it to other side of the board
    public static void playerIsSelectingAPiece(boolean b) { playerIsSelectingAPiece = b; }
    //set pawns pos
    public static void setSuperPawn(int pos) { superPawn = pos; }
    // sound mutators
    public static void enableSoundEffects() { soundEffects = true; }
    public static void disableSoundEffects() { soundEffects = false; }
    public static void enableVoiceAssist() { voiceAssist = true; }
    public static void disableVoiceAssist() { voiceAssist = false; }
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

        // init threefold repitition array
        threeFoldRepitition = new int[6];
        for(int x : threeFoldRepitition)
            x = -1;

        // init all values of array to false for determining if we should disengage en passant
        disengageEnPassant = new boolean[64];
        Arrays.fill(disengageEnPassant, false);

        // init array that stores squares involved in a checkmate
        checkMateHighlighting = new boolean[64];
        Arrays.fill(checkMateHighlighting, false);

    }

    public static void init_frame(){
        frame = new ChessGameFrame("Chess - Assignment X");
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setBackground( Color.WHITE );     // set frame background color
        frame.setSize( 900, 900 );   // set frame size
        frame.setLocationRelativeTo(null);      // center frame on screen
        frame.setVisible( true );               // display frame

        soundPrompt();
    }


    public static void soundPrompt(){
        // init sounds
        click_sound = new Sound("assets/click_sound.wav", false);
        error_sound = new Sound("assets/error_sound.wav", false);
        check_sound = new Sound("assets/check_sound.wav", false);
        checkMate_sound = new Sound("assets/checkMate_sound.wav", false);
        staleMate_sound = new Sound("assets/Stalemate.wav", false);
        draw_sound = new Sound("assets/Drawoffer.wav", false);
        resign_sound = new Sound("assets/Resign.wav", false);

        // init sound map
        soundMap = new HashMap<>();
        final String[] soundFiles = {"1", "2", "3", "4", "5", "6", "7", "8",
                                     "a", "b", "c", "d", "e", "f", "g", "h",
                                     "bishop", "knight", "queen", "rook", "king",
                                     "O-O", "O-O-O", "takes"};
        for(int i = 0; i < soundFiles.length; i++) {
            String fileName = "assets/moves/" + soundFiles[i] + ".wav";
            soundMap.put(soundFiles[i], new Sound(fileName, true));
        }

        SoundSelection soundSelectionScreen = new SoundSelection();
    }

    public static void new_game(){
        frame.clearTextArea();
        frame.resetBoard();
        init_players();
    }

    public static void save_game(ChessGameFrame game_frame, ChessBoardPanel board_panel, ChessSquarePanel[] square_panel){
        Object[] save_file = new Object[89];

        for(int i = 0; i < square_panel.length; i++){
            save_file[i] = square_panel[i];
        }
        save_file[64] = game_frame;
        save_file[65] = board_panel;
        save_file[66] = currentPlayer;
        save_file[67] = currentlyMoving;
        save_file[68] = movingFrom;
        save_file[69] = selectedPiece;
        save_file[70] = frame;
        save_file[71] = selectedSquaresColor;
        save_file[72] = validMoveColors;
        save_file[73] = validMovePositions;
        save_file[74] = whiteKing;
        save_file[75] = blackKing;
        save_file[76] = blackIsChecked;
        save_file[77] = whiteIsChecked;
        save_file[78] = canBlackBeChecked;
        save_file[79] = canWhiteBeChecked;
        save_file[80] = canWhiteBeCheckMated;
        save_file[81] = canBlackBeCheckMated;
        save_file[82] = threeFoldRepitition;
        save_file[83] = disengageEnPassant;
        save_file[84] = playerIsSelectingAPiece;
        save_file[85] = superPawn;
        save_file[86] = whiteIsStaleMated;
        save_file[87] = blackIsStaleMated;
        save_file[88] = checkMateHighlighting;

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


            String msg = "Game saved.";
            JOptionPane.showMessageDialog(frame, msg, "Success", JOptionPane.PLAIN_MESSAGE);

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

            //frame = loaded_frame;

            // clear text area and replace with saved game text
            frame.clearTextArea();
            frame.appendTextArea(loaded_frame.getTextAreaText());

            // replaces existing chessboard with saved chessboard
            frame.setChessBoard(loaded_panel);

            //replaces existing square data array with saved squares array
            frame.getBoard().setSquares(loaded_squares);
            
            //ChessGame data
            currentPlayer = (String)saved[66];
            currentlyMoving = (boolean)saved[67];
            movingFrom = (int)saved[68];
            selectedPiece = (String)saved[69];
            selectedSquaresColor = (Color)saved[71];
            validMoveColors = (Color[])saved[72];
            validMovePositions = (boolean[])saved[73];
            whiteKing = (int)saved[74];
            blackKing = (int)saved[75];
            blackIsChecked = (boolean)saved[76];
            whiteIsChecked = (boolean)saved[77];
            canBlackBeChecked = (boolean)saved[78];
            canWhiteBeChecked = (boolean)saved[79];
            canWhiteBeCheckMated = (boolean)saved[80];
            canBlackBeCheckMated = (boolean)saved[81];
            threeFoldRepitition = (int[])saved[82];
            disengageEnPassant = (boolean[])saved[83];
            playerIsSelectingAPiece = (boolean)saved[84];
            superPawn = (int)saved[85];
            whiteIsStaleMated = (boolean)saved[86];
            blackIsStaleMated = (boolean)saved[87];
            checkMateHighlighting = (boolean[])saved[88];

            frame.setNorthTextField(currentPlayer + "'s Move");
            String msg = "Game loaded.";
            JOptionPane.showMessageDialog(frame, msg, "Success", JOptionPane.PLAIN_MESSAGE);
        }
        catch(IOException ex)
        {
            String msg = "Unable to load game.";
            JOptionPane.showMessageDialog(frame, msg, "Error", JOptionPane.WARNING_MESSAGE);
        }
        catch(ClassNotFoundException ex)
        {
            System.out.println("ClassNotFoundException is caught");
        }
    }

}