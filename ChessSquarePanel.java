import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
//import javax.imageio.ImageIO;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.OverlayLayout;


@SuppressWarnings("serial") // this is used to suppress a serializable warning because JPanel implements serializable
public class ChessSquarePanel extends JPanel implements MouseListener {
    private String piece;
    private String player;
    private int position;
    private JLabel pieceLabel;
    private String image;
    private JLayeredPane overlay;

    public ChessSquarePanel() {
        super();
        setBorder(BorderFactory.createLineBorder(Color.BLACK)); // add black border
        addMouseListener(this);
        overlay = new JLayeredPane();
        overlay.setLayout(new OverlayLayout(overlay));
        overlay.setBackground(new Color(0, 255, 0, 125));
        add(overlay);
        //overlay.setSize(getWidth(), getHeight());
        //add(overlay);
    }

    public void setPiece(String piece, String player, int pos) {
        this.piece = piece;
        this.player = player;
        this.position = pos;

        if(piece != null && player != null) {
            image = "./images/png/" + player + "_" + piece + ".png";
            pieceLabel = new JLabel(new ImageIcon(new ImageIcon(getClass().getResource(image)).getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT)));
            add(pieceLabel);
        }
    }

    public String getPiece() {
        return piece;
    }

    public void paintComponent( Graphics g ) {
        super.paintComponent( g ); // call superclass's paintComponent
        Graphics2D g2d = ( Graphics2D ) g;
        //pieceLabel = new JLabel(new ImageIcon(new ImageIcon(getClass().getResource(image)).getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_DEFAULT)));
    }

    public void mouseClicked(MouseEvent event) {
        System.out.print("mouse clicked ");
        System.out.println(position);
        overlay.setBackground(new Color(0, 255, 0, 125));
        //repaint();
        //overlay.setSize(getWidth(), getHeight());
        //add(overlay);
        
    }

    public void mouseExited(MouseEvent event) {

    }

    public void mouseEntered(MouseEvent e) {

   }

    public void mousePressed(MouseEvent e) {

    }
     
    public void mouseReleased(MouseEvent e) {

    }
     
}