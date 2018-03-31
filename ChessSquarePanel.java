import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Image;
//import javax.imageio.ImageIO;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

import javax.swing.JLabel;


@SuppressWarnings("serial") // this is used to suppress a serializable warning because JPanel implements serializable
public class ChessSquarePanel extends JPanel {
    private String piece;
    private String player;
    private JLabel pieceLabel;
    private String image;

    public ChessSquarePanel() {
        super();
        setBorder(BorderFactory.createLineBorder(Color.BLACK)); // add black border
    }

    public void setPiece(String piece, String player) {
        this.piece = piece;
        this.player = player;

        image = "./images/png/" + player + "_" + piece + ".png";
        pieceLabel = new JLabel(new ImageIcon(new ImageIcon(getClass().getResource(image)).getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT)));
        add(pieceLabel);
    }

    public String getPiece() {
        return piece;
    }

    public void paintComponent( Graphics g ) {
        super.paintComponent( g ); // call superclass's paintComponent
        Graphics2D g2d = ( Graphics2D ) g;
        //pieceLabel = new JLabel(new ImageIcon(new ImageIcon(getClass().getResource(image)).getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_DEFAULT)));
    }
}