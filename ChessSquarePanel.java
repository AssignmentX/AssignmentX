import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

import javax.swing.JLabel;


@SuppressWarnings("serial") // this is used to suppress a serializable warning because JPanel implements serializable
public class ChessSquarePanel extends JPanel {
    private String piece;
    private String player;
    private JLabel imgLabel;

    public ChessSquarePanel() {
        super();
        setBorder(BorderFactory.createLineBorder(Color.BLACK)); // add black border
    }

    public void setPiece(String piece, String player) {
        this.piece = piece;
        this.player = player;

        imgLabel = new JLabel(new ImageIcon("images/reactor.png"));
        imgLabel.setLocation(5, 5);
        add(imgLabel);
        repaint();
    }

    public String getPiece() {
        return piece;
    }

    public void paintComponent( Graphics g ) {
        super.paintComponent( g ); // call superclass's paintComponent
        Graphics2D g2d = ( Graphics2D ) g;
    }
}