import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

@SuppressWarnings("serial") // this is used to suppress a serializable warning because JPanel implements serializable
public class ChessBoardPanel extends JPanel {
    //public ChessBoardPanel() {
    //    super(new GridLayout(4, 4, 5, 5));
    //    panel.setBackground(Color.BLACK);
    //}

    public void paintComponent( Graphics g ) {
        super.paintComponent( g ); // call superclass's paintComponent
        Graphics2D g2d = ( Graphics2D ) g;


        //chessBoard.setBorder(BorderFactory.createRaisedBevelBorder());
        
    }
}