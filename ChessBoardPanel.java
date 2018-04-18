import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.io.Serializable;
import javax.swing.BorderFactory;

@SuppressWarnings("serial") // this is used to suppress a serializable warning because JPanel implements serializable
public class ChessBoardPanel extends JPanel implements Serializable {
    private ChessSquarePanel squares[];
    private ChessGameFrame parent;
    public ChessBoardPanel(ChessGameFrame parent) {
        super();
        this.parent = parent; // keep track of chessgameframe as parent
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
                else
                    squares[i*8 + j].setBackground(Color.WHITE);
                add(squares[i*8 + j]);
            }
        }
        // initialize white pieces
        squares[0].setPiece("rook", "White", 0);
        squares[1].setPiece("knight", "White", 1);
        squares[2].setPiece("bishop", "White", 2);
        squares[3].setPiece("queen", "White", 3);
        squares[4].setPiece("king", "White", 4);
        squares[5].setPiece("bishop", "White", 5);
        squares[6].setPiece("knight", "White", 6);
        squares[7].setPiece("rook", "White", 7);
        for(int i = 8; i < 16; i++) {
            squares[i].setPiece("pawn", "White", i);
        }
        // initialize empty pieces
        for(int i = 16; i < 48; i++) {
            squares[i].setPiece(null, null, i);
            squares[i].pieceHasMoved(); // needed on empty squares since the board is stateless
        }
        // initialize black pieces
        squares[56].setPiece("rook", "Black", 56);
        squares[57].setPiece("knight", "Black", 57);
        squares[58].setPiece("bishop", "Black", 58);
        squares[59].setPiece("queen", "Black", 59);
        squares[60].setPiece("king", "Black", 60);
        squares[61].setPiece("bishop", "Black", 61);
        squares[62].setPiece("knight", "Black", 62);
        squares[63].setPiece("rook", "Black", 63);
        for(int i = 48; i < 56; i++) {
            squares[i].setPiece("pawn", "Black", i);
        }
        // set locations for kings
        ChessGame.setWhiteKingPos(4);
        ChessGame.setBlackKingPos(60);
    }

    public void paintComponent( Graphics g ) {
        super.paintComponent( g ); // call superclass's paintComponent
        Graphics2D g2d = ( Graphics2D ) g;
    }

    // this is used by ChessSquarePanels to call the appendTextArea() method in ChessGameFrame
    public ChessGameFrame getChessGameFrame() {
        return parent;
    }
    public ChessSquarePanel squareAt(int pos) { return squares[pos]; }
    public ChessSquarePanel[] getSquares(){ return squares; }
    public void setSquares(ChessSquarePanel[] new_squares){ squares = new_squares; }

}