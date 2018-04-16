import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.Timer;
import java.awt.event.*;


@SuppressWarnings("serial") // this is used to suppress a serializable warning because JPanel implements serializable
public class ChessSquarePanel extends JPanel implements MouseListener, ActionListener {
    private String piece;
    private String player;
    private int position;
    private JLabel pieceLabel;
    private String image;
    private ChessBoardPanel parent;
    private boolean hasMoved;
    private boolean pawnWasHere = false; // used for en passant (in passing)
    private int delay = 100; // for flashing a square
    private final int numOfFlashes = 4; // max number of flashes
    private int flashCount = 0; // current number of flashes
    protected Timer timer;
    private int currentHeight;
    private int currentWidth;

    public ChessSquarePanel(ChessBoardPanel parent) {
        super();
        this.parent = parent; // keep track of chessboardpanel as parent
        setBorder(BorderFactory.createLineBorder(Color.BLACK)); // add black border
        this.hasMoved = false; // keeps track of if piece has moved from starting position
        addMouseListener(this); // adds mouse listener to panel 
        timer = new Timer(delay, this); // creates timer for flashing a square
        // default size of height and width of chess pieces at default width/height of window frame
        currentWidth = 89;
        currentHeight = 86;
    }

    public void setPiece(String piece, String player, int pos) {
        this.piece = piece;
        this.player = player;
        this.position = pos;

        if(piece != null && player != null) {
            image = "./images/png/" + player.toLowerCase() + "_" + piece + ".png";
            pieceLabel = new JLabel(new ImageIcon(new ImageIcon(getClass().getResource(image)).getImage().getScaledInstance(currentWidth*3/4+currentWidth/8, currentHeight*3/4+currentHeight/10, Image.SCALE_DEFAULT)));
            add(pieceLabel);
        }
        else{
            pieceLabel = null;
            piece = null;
            player = null;
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

        // RESIZE CHESS PIECES WHEN THE BOARD RESIZES
        if(player != null && piece != null && (currentHeight != getHeight() || currentWidth != getWidth())) {
            // save current height & width
            currentHeight = getHeight();
            currentWidth = getWidth();

            image = "./images/png/" + player.toLowerCase() + "_" + piece + ".png";
            pieceLabel.setIcon(new ImageIcon(new ImageIcon(getClass().getResource(image)).getImage().getScaledInstance(currentWidth*3/4+currentWidth/8, currentHeight*3/4+currentHeight/10, Image.SCALE_DEFAULT)));
        }
    }

    public void mouseClicked(MouseEvent event) {

        // determine if player is in check
        boolean checked;
        if(ChessGame.getCurrentPlayer().equals("White"))
            checked = ChessGame.isWhiteChecked();
        else
            checked = ChessGame.isBlackChecked();


        // validate the correct player is clicking the square
        if(ChessGame.getCurrentPlayer().equals(player)) {// && (!checked || piece.equals("king"))) {



            // change background color back to default for previously clicked square
            if(ChessGame.getMovingFrom() != -1) {
                parent.squareAt(ChessGame.getMovingFrom()).setBorder(BorderFactory.createLineBorder(Color.BLACK));
                parent.squareAt(ChessGame.getMovingFrom()).setBackground(ChessGame.getSelectedSquaresColor());
                // also, unhighlight valid move squares
                for(int i = 0; i < 64; i++) {
                    if(ChessGame.getValidMovePositions()[i] && parent.squareAt(i).getPiece() == null)
                        parent.squareAt(i).setBackground(ChessGame.getValidMoveColors()[i]);
                    else if(ChessGame.getValidMovePositions()[i] && (!checked || !parent.squareAt(i).getPiece().equals("king"))) {
                        if(parent.squareAt(i).getBackground() != Color.RED)
                            parent.squareAt(i).setBackground(ChessGame.getValidMoveColors()[i]);
                    }
                    //else if(ChessGame.getValidMovePositions()[i])
                    //    parent.squareAt(i).setBackground(ChessGame.getValidMoveColors()[i]);
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
                if(parent.squareAt(x).getBackground() != Color.RED) {
                    ChessGame.getValidMovePositions()[x] = true;
                    ChessGame.getValidMoveColors()[x] = parent.squareAt(x).getBackground();
                }
                
                // highlight square
                parent.squareAt(x).setBackground(new Color(0, 200, 0));
            }



        }
        else if(ChessGame.isMoving()){

            //System.out.println(ChessGame.getValidMoveColors()[position]);
            ChessSquarePanel currentPosition = parent.squareAt(ChessGame.getMovingFrom());

            // get list of valid moves
            ArrayList<Integer> valid_moves = MoveLogic.get_valid_moves(ChessGame.getCurrentPlayer(), ChessGame.getSelectedPiece(), ChessGame.getMovingFrom(), false);
            //System.out.print("Valid moves: ");
            //for (int x : valid_moves) {
            //    System.out.print(" " + x);
            //}
            //System.out.println();

            // validate move
            if(valid_moves.contains(position)){

                ArrayList<Integer> moves;
                String enemyPiece;// = currentPosition.getPiece();
                String enemyPlayer;// = currentPosition.getPlayer();
                JLabel enemyPieceLabel;

                // init all values of array to false for determining if we should disengage en passant
                boolean disengageEnPassant[] = new boolean[64];
                Arrays.fill(disengageEnPassant, false);

                // should we disengage en passant?
                for(int i = 0; i < 64; i++){
                    if(parent.squareAt(i).getPawnWasHere()) {
                        if( i+8 < 64 && parent.squareAt(i+8).getPlayer() != null && parent.squareAt(i+8).getPlayer().equals("White")){
                            if(parent.squareAt(i+8).getPiece() != null && parent.squareAt(i+8).getPiece().equals("pawn")) {
                                //if(parent.squareAt(i+8).getPawnWasHere())
                                    disengageEnPassant[i] = true;
                            }
                        }
                        else if( i-8 >= 0 && parent.squareAt(i-8).getPlayer() != null && parent.squareAt(i-8).getPlayer().equals("Black")){
                            if(parent.squareAt(i-8).getPiece() != null && parent.squareAt(i-8).getPiece().equals("pawn")) {
                                //if(parent.squareAt(i-8).getPawnWasHere())
                                    disengageEnPassant[i] = true;
                            }
                        }
                    }
                }

                // remove piece from its current position
                currentPosition.remove(currentPosition.getPieceLabel());
                currentPosition.setPiece(null, null, ChessGame.getMovingFrom());

                // save clicked positions piece and player
                if(piece != null && player != null) {
                    enemyPiece = new String(piece);//new String(piece);
                    enemyPlayer = new String(player);//new String(player);
                    enemyPieceLabel = pieceLabel;
                }
                else {
                    enemyPiece = null;
                    enemyPlayer = null;
                    enemyPieceLabel = null;
                }

                // if there is an enemy piece in clicked position, remove it
                //if(pieceLabel != null)//{
                //    remove(pieceLabel);
                //}
                //else {
                    // move piece to clicked position
                    //if(pieceLabel != null) {
                    //    setCurrentPiece(ChessGame.getSelectedPiece());
                    //setCurrentPlayer(ChessGame.getCurrentPlayer());
                    //}
                    //else{
                    //    //remove(pieceLabel);
                        setPiece(ChessGame.getSelectedPiece(), ChessGame.getCurrentPlayer(), position);
                    //}
                    //
                //}

                // update king's position if the king moved
                if(ChessGame.getCurrentPlayer().equals("White") && ChessGame.getSelectedPiece().equals("king"))
                    ChessGame.setWhiteKingPos(position);
                else if(ChessGame.getCurrentPlayer().equals("Black") && ChessGame.getSelectedPiece().equals("king"))
                    ChessGame.setBlackKingPos(position);

                String currplayer = ChessGame.getCurrentPlayer();



                // check for checkmate
                if(checked){
                    // these will be set to false in the next code block if there exists a valid move that gets player out of check
                    if(currplayer.equals("White"))
                        ChessGame.whiteIsCheckMated();
                    else
                        ChessGame.blackIsCheckMated();

                    for(int i = 0; i < 64; i++) {
                        // if piece is current player's piece
                        if(parent.squareAt(i).getPlayer() != null && parent.squareAt(i).getPlayer().equals(currplayer)) {
                            // get valid moves for that piece
                            moves = MoveLogic.get_valid_moves(currplayer, parent.squareAt(i).getPiece(), i, false);
                            // prune moves that put current player in check
                            MoveLogic.doMovesPutPlayerInCheck(moves, parent.squareAt(i).getPiece(), i);
                            // if moves is not empty, then player cannot be checkmated and break
                            if(!moves.isEmpty()){
                                if(currplayer.equals("White"))
                                    ChessGame.whiteIsNotCheckMated();
                                else
                                    ChessGame.blackIsNotCheckMated();
                            }
                        }
                    }
                }
                // check for stalemate
                else{

                }

                if((currplayer.equals("White") && !ChessGame.canWhiteBeCheckMated()) || (currplayer.equals("Black") && !ChessGame.canBlackBeCheckMated())) {

                    System.out.println("Not checkmated");

                    // set these to false, if they are true after the next block of code, then the move is invalid
                    // since it can put the current player in check
                    ChessGame.canBlackBeChecked(false);
                    ChessGame.canWhiteBeChecked(false);

                    // see if move puts current player in check
                    for(int i = 0; i < 64; i++) {
                        // if current player is white
                       if(ChessGame.getCurrentPlayer().equals("White")) {
                           if(parent.squareAt(i).getPlayer() != null && parent.squareAt(i).getPlayer().equals("Black")) {
                               ChessGame.setCurrentPlayer("Black"); // this is a janky fix, don't worry about it ;)
                               moves = MoveLogic.get_valid_moves("Black", parent.squareAt(i).getPiece(), i, false);
                               ChessGame.setCurrentPlayer("White"); // this is a janky fix, don't worry about it ;)
                               if(moves.contains(ChessGame.getWhiteKingPos())){
                                   ChessGame.canWhiteBeChecked(true);
                                   remove(pieceLabel);
                               }
                           }
                       }
                       // if current player is black
                       else{
                           if(parent.squareAt(i).getPlayer() != null && parent.squareAt(i).getPlayer().equals("White")) {
                               ChessGame.setCurrentPlayer("White"); // this is a janky fix, don't worry about it ;)
                               moves = MoveLogic.get_valid_moves("White", parent.squareAt(i).getPiece(), i, false);
                               ChessGame.setCurrentPlayer("Black"); // this is a janky fix, don't worry about it ;)
                               if(moves.contains(ChessGame.getBlackKingPos())){
                                   ChessGame.canBlackBeChecked(true);
                                   remove(pieceLabel);
                               }
                           }
                       }
                    }



                    // put piece back if it can cause check (do not allow player to put his/herself in check)
                    if((ChessGame.getCurrentPlayer().equals("White") && ChessGame.canWhiteBeChecked()) || (ChessGame.getCurrentPlayer().equals("Black") && ChessGame.canBlackBeChecked())) {
                        currentPosition.setPiece(ChessGame.getSelectedPiece(), ChessGame.getCurrentPlayer(), ChessGame.getMovingFrom());
                        pieceLabel = enemyPieceLabel;
                        setCurrentPiece(enemyPiece);
                        setCurrentPlayer(enemyPlayer);

                        // update king's position if the king moved
                        if(ChessGame.getCurrentPlayer().equals("White") && ChessGame.getSelectedPiece().equals("king"))
                            ChessGame.setWhiteKingPos(ChessGame.getMovingFrom());
                        else if(ChessGame.getCurrentPlayer().equals("Black") && ChessGame.getSelectedPiece().equals("king"))
                            ChessGame.setBlackKingPos(ChessGame.getMovingFrom());

                        // flash the square
                        timer.start();
                    }
                    
                    else { // move does not put current player in check


                        //System.out.println("HIT");
                        // move piece to clicked position
                        if(enemyPieceLabel != null) {
                            remove(enemyPieceLabel);
                        }
                        //    setPiece(enemyPiece, enemyPlayer, position);
                        //    pieceLabel = enemyPieceLabel;
                        //    piece = enemyPiece;
                        //    player = enemyPlayer;
                        //}

                        // display move message
                        String msg = ChessGame.getCurrentPlayer() + " " + ChessGame.getSelectedPiece() + ": " + MoveLogic.pos_to_AN[ChessGame.getMovingFrom()] + " - " + MoveLogic.pos_to_AN[position];
                        parent.getChessGameFrame().appendTextArea(msg);

                        // lazy solution, set color back to default
                        currentPosition.setBackground(ChessGame.getSelectedSquaresColor());
                        currentPosition.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                        // also, unhighlight valid move squares
                        for(int i = 0; i < 64; i ++) {
                            if(ChessGame.getValidMovePositions()[i]) {
                                parent.squareAt(i).setBackground(ChessGame.getValidMoveColors()[i]);
                                ChessGame.getValidMovePositions()[i] = false;
                            }
                        }

                        // unhighlight red squares if we were previously in check
                        //for(int i = 0; i < 64; i++) {
                        //    if(ChessGame.getValidMovePositions()[i]){
                        //        parent.squareAt(i).setBackground(ChessGame.getValidMoveColors()[i]);
                        //        ChessGame.getValidMovePositions()[i] = false;
                        //    }
                        //}

                        // get moves to see if other player is in check
                        moves = MoveLogic.get_valid_moves(ChessGame.getCurrentPlayer(), ChessGame.getSelectedPiece(), position, false);

                        ChessGame.blackIsChecked(false);
                        ChessGame.whiteIsChecked(false);

                        // check if other player is in check
                        if(ChessGame.getCurrentPlayer().equals("White")){
                            if(moves.contains(ChessGame.getBlackKingPos())){
                                //System.out.println("CHECK");
                                ChessGame.blackIsChecked(true);
                                // store color of squares that are being highlighted red
                                if(parent.squareAt(position).getBackground() != Color.RED) {
                                    ChessGame.getValidMovePositions()[position] = true;
                                    ChessGame.getValidMoveColors()[position] = parent.squareAt(position).getBackground();
                                    ChessGame.getValidMovePositions()[ChessGame.getBlackKingPos()] = true;
                                    ChessGame.getValidMoveColors()[ChessGame.getBlackKingPos()] = parent.squareAt(ChessGame.getBlackKingPos()).getBackground();
                                }
                                // highlight squares red
                                parent.squareAt(position).setBackground(Color.RED);
                                parent.squareAt(ChessGame.getBlackKingPos()).setBackground(Color.RED);
                            }
                        }
                        else{
                            if(moves.contains(ChessGame.getWhiteKingPos())) {
                                //System.out.println("CHECK");
                                ChessGame.whiteIsChecked(true);
                                // store color of squares that are being highlighted red
                                if(parent.squareAt(position).getBackground() != Color.RED) {
                                    
                                    ChessGame.getValidMovePositions()[position] = true;
                                    ChessGame.getValidMoveColors()[position] = parent.squareAt(position).getBackground();
                                    ChessGame.getValidMovePositions()[ChessGame.getWhiteKingPos()] = true;
                                    ChessGame.getValidMoveColors()[ChessGame.getWhiteKingPos()] = parent.squareAt(ChessGame.getWhiteKingPos()).getBackground();
                                }
                                // highlight squares red
                                parent.squareAt(position).setBackground(Color.RED);
                                parent.squareAt(ChessGame.getWhiteKingPos()).setBackground(Color.RED);
                            }
                        }

                        // update king's position if the king moved (we do this again in case the king re-checked itself)
                        if(ChessGame.getCurrentPlayer().equals("White") && ChessGame.getSelectedPiece().equals("king"))
                            ChessGame.setWhiteKingPos(position);
                        else if(ChessGame.getCurrentPlayer().equals("Black") && ChessGame.getSelectedPiece().equals("king"))
                            ChessGame.setBlackKingPos(position);

                        // pawn's first move, trigger possibility for en passant immediately
                        if(!parent.squareAt(ChessGame.getMovingFrom()).hasPieceMoved()) {
                            // pawn moved down 2 squares (white), engage en passant rule
                            if(position == 16 + ChessGame.getMovingFrom()) {
                                parent.squareAt(ChessGame.getMovingFrom() + 8).setPawnWasHere(true);
                            }
                            // pawn moved up 2 squares (black), engage en passant rule
                            else if(position == ChessGame.getMovingFrom() - 16) {
                                parent.squareAt(ChessGame.getMovingFrom() - 8).setPawnWasHere(true);
                            }
                        }

                        // piece has been moved (used for pawns first move, and kings+rooks for castling)
                        parent.squareAt(ChessGame.getMovingFrom()).pieceHasMoved();
                        hasMoved = true;

                        if(ChessGame.getCurrentPlayer().equals("Black"))
                            checked = ChessGame.isWhiteChecked();
                        else
                            checked = ChessGame.isBlackChecked();

                        for(int i = 0; i < 64; i++){
                            // disengage en passant
                            if(disengageEnPassant[i]) {
                                parent.squareAt(i).setPawnWasHere(false);
                                disengageEnPassant[i] = false;
                            }

                            // if king is not in check, unhighlight red squares
                            if(!checked && parent.squareAt(i).getBackground() == Color.RED){
                                System.out.println(i);
                                System.out.println(parent.squareAt(i).getBackground());
                                System.out.println(ChessGame.getValidMoveColors()[i]);
                                parent.squareAt(i).setBackground(ChessGame.getValidMoveColors()[i]);
                                ChessGame.getValidMovePositions()[i] = false;
                            }
                        }

                        // clear data for previously selected piece
                        ChessGame.setCurrentlyMoving(false);
                        ChessGame.setMovingFrom(-1);
                        ChessGame.setSelectedPiece(null);

                        // change turn to other player
                        if(ChessGame.getCurrentPlayer().equals("White")) {
                            ChessGame.setCurrentPlayer("Black");
                            ChessGame.getFrame().setNorthTextField("Black's Move");
                        }
                        else {
                            ChessGame.setCurrentPlayer("White");
                            ChessGame.getFrame().setNorthTextField("White's Move");
                        }
                    }//end of else for move does not put player in check

                } // end of not checkmated

                // someone got checkmated
                else {
                    System.out.print(ChessGame.getCurrentPlayer());
                    System.out.println(" checkmated");
                }

            } // end of invalid move
            else
                System.out.printf("Invalid move: %d\n", position);

        } // player is not moving
    } /// end of mouseclicked()

    // used to flash a square if a move can put you in check
    public void actionPerformed(ActionEvent e) {
        // will run when the timer fires
        if(flashCount < numOfFlashes) {
            flashCount += 1;
            // flash the current square
            if(getBackground() != Color.RED)
                setBackground(Color.RED);
            else
                setBackground(new Color(0, 200, 0));
            
            //repaint();
        }
        else {
            flashCount = 0;
            timer.stop();
            //setPiece(piece, player, position);
        }
    }

    // this is used to clear chess pieces in other squares
    public JLabel getPieceLabel() { return pieceLabel; }

    // this is used to keep track of each piece's first move
    public void pieceHasMoved() { hasMoved = true; }
    public boolean hasPieceMoved() { return hasMoved; }

    public void setCurrentPiece(String p) { piece = p; }
    public void setCurrentPlayer(String p) { player = p; }
    public void setPieceLabel(JLabel j) { pieceLabel = j; }

    public void mouseExited(MouseEvent event) {}
    public void mouseEntered(MouseEvent event) {}
    public void mousePressed(MouseEvent event) {}
    public void mouseReleased(MouseEvent event) {}
     
    // used for en passant (in passing)
    public boolean getPawnWasHere() { return pawnWasHere; }
    public void setPawnWasHere(boolean b) { pawnWasHere = b; }
}