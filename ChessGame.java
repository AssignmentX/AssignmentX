import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.util.Random;

public class ChessGame {
    private static String currentPlayer;
    private static boolean currentlyMoving;
    private static int movingFrom;
    private static String selectedPiece;
    private static ChessGameFrame frame;

    public static void main( String args[] ) {

        frame = new ChessGameFrame("Chess - Assignment X"); 
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setBackground( Color.WHITE );     // set frame background color
        frame.setSize( 900, 900 );             // set frame size
        frame.setLocationRelativeTo(null);      // center frame on screen
        frame.setVisible( true );               // display frame
    
        // figure out who goes first
        Object[] options = {"Black", "White", "Random"};
        int n = JOptionPane.showOptionDialog(frame, "Who should go first?", "Select First Move", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[2]);
        String firstMove;
        if(n == 0)
            firstMove = "Black";
        else if(n == 1)
            firstMove = "White";
        else
            firstMove = (new Random().nextInt() % 2 == 0) ? "Black" : "White";
        // set current player
        setCurrentPlayer(firstMove);
        frame.setNorthTextField(firstMove + "'s Move");
        currentlyMoving = false;
    }

    public static String getCurrentPlayer(){ return currentPlayer; }
    public static String getSelectedPiece(){ return selectedPiece; }
    public static ChessGameFrame getFrame(){ return frame; }

    public static boolean isMoving() { return currentlyMoving; }
    public static int getMovingFrom() { return movingFrom; }
    public static void setCurrentPlayer(String player){ currentPlayer = player; }
    public static void setSelectedPiece(String piece){ selectedPiece = piece; }
    public static void setCurrentlyMoving(boolean val){ currentlyMoving = val; }
    public static void setMovingFrom(int pos){ movingFrom = pos; }
}