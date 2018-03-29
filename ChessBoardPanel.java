import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.BorderFactory;

@SuppressWarnings("serial") // this is used to suppress a serializable warning because JPanel implements serializable
public class ChessBoardPanel extends JPanel {
    private ChessSquarePanel squares[];

    public ChessBoardPanel() {
        super();
        
        setBorder(BorderFactory.createLineBorder(Color.BLACK)); // add black border
        setLayout(new GridLayout(8, 8, 3, 3));
        setBackground(Color.WHITE);
        
        squares = new ChessSquarePanel[64];
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                squares[i] = new ChessSquarePanel();
                if((i % 2 == 0 && j % 2 == 0) || (i % 2 != 0 && j % 2 != 0))
                    squares[i].setBackground(Color.GRAY);
                add(squares[i]);
            }
        }
    }

    public void paintComponent( Graphics g ) {
        super.paintComponent( g ); // call superclass's paintComponent
        Graphics2D g2d = ( Graphics2D ) g;
    }
}