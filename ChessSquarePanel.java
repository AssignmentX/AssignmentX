import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.Dimension;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.OverlayLayout;

import java.util.ArrayList;


@SuppressWarnings("serial") // this is used to suppress a serializable warning because JPanel implements serializable
public class ChessSquarePanel extends JPanel implements MouseListener {
    private String piece;
    private String player;
    private int position;
    private JLabel pieceLabel;
    private String image;
    private ChessBoardPanel parent;
    private boolean hasMoved;

    public ChessSquarePanel(ChessBoardPanel parent) {
        super();
        this.parent = parent; // keep track of chessboardpanel as parent
        setBorder(BorderFactory.createLineBorder(Color.BLACK)); // add black border
        this.hasMoved = false; // keeps track of if piece has moved from starting position
        addMouseListener(this); // adds mouse listener to panel 
    }

    public void setPiece(String piece, String player, int pos) {
        this.piece = piece;
        this.player = player;
        this.position = pos;

        if(piece != null && player != null) {
            image = "./images/png/" + player.toLowerCase() + "_" + piece + ".png";
            // TODO: this must be tweaked to center the chess pieces on the panel
            // TODO: this must be changed whenever we change the default size of the window
            pieceLabel = new JLabel(new ImageIcon(new ImageIcon(getClass().getResource(image)).getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT)));
            add(pieceLabel);
        }
        else{
            pieceLabel = null;
        }
    }

    public String getPiece() {
        return piece;
    }

    public String getPlayer() {
        return player;
    }

    public void paintComponent( Graphics g ) {
        super.paintComponent( g ); // call superclass's paintComponent
        Graphics2D g2d = ( Graphics2D ) g;

        // TODO: RESIZE CHESS PIECES WHEN THE BOARD RESIZES
        //pieceLabel = new JLabel(new ImageIcon(new ImageIcon(getClass().getResource(image)).getImage().getScaledInstance(getWidth()*3/4, getHeight()*3/4, Image.SCALE_DEFAULT)));
    }

    public void mouseClicked(MouseEvent event) {

        // determine if player is in check
        boolean checked;
        if(ChessGame.getCurrentPlayer().equals("White")){
            checked = ChessGame.isWhiteChecked();
        }
        else{
            checked = ChessGame.isBlackChecked();
        }

        // validate the correct player is clicking the square
        if(ChessGame.getCurrentPlayer().equals(player)) {// && (!checked || piece.equals("king"))) {

            // change background color back to default for previously clicked square
            if(ChessGame.getMovingFrom() != -1) {
                parent.squareAt(ChessGame.getMovingFrom()).setBorder(BorderFactory.createLineBorder(Color.BLACK));
                parent.squareAt(ChessGame.getMovingFrom()).setBackground(ChessGame.getSelectedSquaresColor());
                // also, unhighlight valid move squares
                for(int i = 0; i < 64; i++) {
                    if(ChessGame.getValidMovePositions()[i])
                        parent.squareAt(i).setBackground(ChessGame.getValidMoveColors()[i]);
                }
            }

            setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()));


            ChessGame.setCurrentlyMoving(true);
            ChessGame.setMovingFrom(position);
            ChessGame.setSelectedPiece(piece);
            ChessGame.setSelectedSquaresColor(getBackground());

            // lazy solution instead of highlighting, changes background color
            if(!checked || !piece.equals("king"))
                setBackground(Color.GREEN);

            // get list of valid moves
            ArrayList<Integer> valid_moves = MoveLogic.get_valid_moves(ChessGame.getCurrentPlayer(), ChessGame.getSelectedPiece(), ChessGame.getMovingFrom(), false);
            for(int x : valid_moves) {
                // save position of square we are highlighting
                ChessGame.getValidMovePositions()[x] = true;
                ChessGame.getValidMoveColors()[x] = parent.squareAt(x).getBackground();

                // highlight square
                parent.squareAt(x).setBackground(new Color(0, 200, 0));
            }

        }
        else if(ChessGame.isMoving()){
            ChessSquarePanel currentPosition = parent.squareAt(ChessGame.getMovingFrom());

            // get list of valid moves
            ArrayList<Integer> valid_moves = MoveLogic.get_valid_moves(ChessGame.getCurrentPlayer(), ChessGame.getSelectedPiece(), ChessGame.getMovingFrom(), false);
            System.out.print("Valid moves: ");
            for (int x : valid_moves) {
                System.out.print(" " + x);
            }
            System.out.println();

            // validate move
            if(valid_moves.contains(position)){

                // piece has been moved (used for pawns first move, and kings+rooks for castling)
                parent.squareAt(ChessGame.getMovingFrom()).pieceHasMoved();
                hasMoved = true;

                // display move message
                String msg = ChessGame.getCurrentPlayer() + " " + ChessGame.getSelectedPiece() + ": " + Integer.toString(ChessGame.getMovingFrom()) + " - " + Integer.toString(position);
                parent.getChessGameFrame().appendTextArea(msg);

                // lazy solution, set color back to default
                currentPosition.setBackground(ChessGame.getSelectedSquaresColor());
                currentPosition.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                // also, unhighlight valid move squares
                for(int x : valid_moves) {
                    parent.squareAt(x).setBackground(ChessGame.getValidMoveColors()[x]);
                    ChessGame.getValidMovePositions()[x] = false;
                }

                // remove piece from its current position
                currentPosition.remove(currentPosition.getPieceLabel());
                currentPosition.setPiece(null, null, ChessGame.getMovingFrom());
                currentPosition.repaint();

                // if there is an enemy piece in clicked position, remove it
                if(pieceLabel != null) {
                    remove(pieceLabel);
                    pieceLabel = null;
                    player = null;
                }

                // move piece to clicked position
                setPiece(ChessGame.getSelectedPiece(), ChessGame.getCurrentPlayer(), position);

                // update king's position
                if(ChessGame.getCurrentPlayer().equals("White") && ChessGame.getSelectedPiece().equals("king"))
                    ChessGame.setWhiteKingPos(position);
                else if(ChessGame.getCurrentPlayer().equals("Black") && ChessGame.getSelectedPiece().equals("king"))
                    ChessGame.setBlackKingPos(position);

                // set if move puts current player in check
                //System.out.print("MOVES: ");
                for(int i = 0; i < 64; i++) {
                   if(ChessGame.getCurrentPlayer().equals("White")) {
                       if(parent.squareAt(i).getPlayer() != null && parent.squareAt(i).getPlayer().equals("Black")) {
                           ArrayList<Integer> moves = MoveLogic.get_valid_moves("Black", parent.squareAt(i).getPiece(), i, true);
                           //System.out.print(moves.toString());
                           if(moves.contains(ChessGame.getWhiteKingPos()))
                               System.out.println("puts u in check");
                       }
                   }
                   else{
                        System.out.println(parent.squareAt(i).getPlayer());
                       if(parent.squareAt(i).getPlayer() != null && parent.squareAt(i).getPlayer().equals("White")) {
                           ArrayList<Integer> moves = MoveLogic.get_valid_moves("White", parent.squareAt(i).getPiece(), i, true);
                           //System.out.print(moves.toString());
                           if(moves.contains(ChessGame.getBlackKingPos()))
                               System.out.println("puts u in check");
                       }
                   }
                }
                //System.out.println();

                // get moves to see if other player is in check
                ArrayList<Integer> moves = MoveLogic.get_valid_moves(ChessGame.getCurrentPlayer(), ChessGame.getSelectedPiece(), position, false);

                // check if other player is in check
                if(ChessGame.getCurrentPlayer().equals("White")){
                    if(moves.contains(ChessGame.getBlackKingPos())){
                        System.out.println("CHECK");
                        ChessGame.blackIsChecked(true);
                        //ChessGame.getValidMovePositions()[position] = true;
                        //ChessGame.getValidMoveColors()[position] = parent.squareAt(position).getBackground();
                        parent.squareAt(position).setBackground(Color.RED);
                        parent.squareAt(ChessGame.getBlackKingPos()).setBackground(Color.RED);
                    }
                }
                else{
                    if(moves.contains(ChessGame.getWhiteKingPos())) {
                        System.out.println("CHECK");
                        ChessGame.whiteIsChecked(true);
                        //ChessGame.getValidMovePositions()[position] = true;
                        //ChessGame.getValidMoveColors()[position] = parent.squareAt(position).getBackground();
                        parent.squareAt(position).setBackground(Color.RED);
                        parent.squareAt(ChessGame.getWhiteKingPos()).setBackground(Color.RED);
                    }
                }

                // change turn to other player
                if(ChessGame.getCurrentPlayer().equals("White")) {
                    ChessGame.setCurrentPlayer("Black");
                    ChessGame.getFrame().setNorthTextField("Black's Move");
                }
                else {
                    ChessGame.setCurrentPlayer("White");
                    ChessGame.getFrame().setNorthTextField("White's Move");
                }

                // clear data for previously selected piece
                ChessGame.setCurrentlyMoving(false);
                ChessGame.setMovingFrom(-1);
                ChessGame.setSelectedPiece(null);
            }
            else
                System.out.printf("Invalid move: %d\n", position);

        }
    }

    // this is used to clear chess pieces in other squares
    public JLabel getPieceLabel() { return pieceLabel; }

    // this is used to keep track of each piece's first move
    public void pieceHasMoved() { hasMoved = true; }
    public boolean hasPieceMoved() { return hasMoved; }

    public void mouseExited(MouseEvent event) {}
    public void mouseEntered(MouseEvent event) {}
    public void mousePressed(MouseEvent event) {}
    public void mouseReleased(MouseEvent event) {}
     
}