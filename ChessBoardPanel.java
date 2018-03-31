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
                squares[i*8 + j] = new ChessSquarePanel(this);
                if((i % 2 == 0 && j % 2 == 0) || (i % 2 != 0 && j % 2 != 0))
                    squares[i*8 + j].setBackground(Color.GRAY);
                add(squares[i*8 + j]);
            }
        }
        // initialize white pieces
        squares[0].setPiece("rook", "white", 0);
        squares[1].setPiece("knight", "white", 1);
        squares[2].setPiece("bishop", "white", 2);
        squares[3].setPiece("queen", "white", 3);
        squares[4].setPiece("king", "white", 4);
        squares[5].setPiece("bishop", "white", 5);
        squares[6].setPiece("knight", "white", 6);
        squares[7].setPiece("rook", "white", 7);
        for(int i = 8; i < 16; i++) {
            squares[i].setPiece("pawn", "white", i);
        }
        // initialize empty pieces
        for(int i = 17; i < 56; i++) {
            squares[i].setPiece(null, null, i);
        }
        // initialize black pieces
        squares[56].setPiece("rook", "black", 56);
        squares[57].setPiece("knight", "black", 57);
        squares[58].setPiece("bishop", "black", 58);
        squares[59].setPiece("queen", "black", 59);
        squares[60].setPiece("king", "black", 60);
        squares[61].setPiece("bishop", "black", 61);
        squares[62].setPiece("knight", "black", 62);
        squares[63].setPiece("rook", "black", 63);
        for(int i = 48; i < 56; i++) {
            squares[i].setPiece("pawn", "black", i);
        }
    }

    public void paintComponent( Graphics g ) {
        super.paintComponent( g ); // call superclass's paintComponent
        Graphics2D g2d = ( Graphics2D ) g;
    }
}