import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JFrame;

public class ChessGame {
    public static void main( String args[] ) {
        ChessGameFrame frame = new ChessGameFrame("Chess - Assignment X"); 
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setBackground( Color.WHITE );     // set frame background color
        frame.setSize( 1200, 800 );             // set frame size
        frame.setLocationRelativeTo(null);      // center frame on screen
        frame.setVisible( true );               // display frame
    }
}