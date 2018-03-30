import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.BorderFactory;


@SuppressWarnings("serial") // this is used to suppress a serializable warning because JPanel implements serializable
public class ChessSquarePanel extends JPanel {
    private String piece;
    private String player;
    public ChessSquarePanel() {
        super();
        setBorder(BorderFactory.createLineBorder(Color.BLACK)); // add black border
        //piece = new String("");
        //player = new String("");
    }

    public void setPiece(String piece, String player) {
        this.piece = piece;
        this.player = player;
    }

    public String getPiece() {
        return piece;
    }

    public void paintComponent( Graphics g ) {
        super.paintComponent( g ); // call superclass's paintComponent
        Graphics2D g2d = ( Graphics2D ) g;
    }
}