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

        // initialize checkered squares
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                squares[i*8 + j] = new ChessSquarePanel();
                if((i % 2 == 0 && j % 2 == 0) || (i % 2 != 0 && j % 2 != 0))
                    squares[i*8 + j].setBackground(Color.GRAY);
                add(squares[i*8 + j]);
            }
        }
        // initialize white pieces
        squares[0].setPiece("rook", "white");
        squares[1].setPiece("knight", "white");
        squares[2].setPiece("bishop", "white");
        squares[3].setPiece("queen", "white");
        squares[4].setPiece("king", "white");
        squares[5].setPiece("bishop", "white");
        squares[6].setPiece("knight", "white");
        squares[7].setPiece("rook", "white");
        for(int i = 8; i < 15; i++) {
            squares[i].setPiece("pawn", "white");
        }

        // initialize black pieces
        squares[56].setPiece("rook", "black");
        squares[57].setPiece("knight", "black");
        squares[58].setPiece("bishop", "black");
        squares[59].setPiece("queen", "black");
        squares[60].setPiece("king", "black");
        squares[61].setPiece("bishop", "black");
        squares[62].setPiece("knight", "black");
        squares[63].setPiece("rook", "black");
        for(int i = 48; i < 56; i++) {
            squares[i].setPiece("pawn", "black");
        }

    }

    public void paintComponent( Graphics g ) {
        super.paintComponent( g ); // call superclass's paintComponent
        Graphics2D g2d = ( Graphics2D ) g;
    }
}