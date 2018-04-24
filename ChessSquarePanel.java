import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JRootPane;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.Timer;
import java.awt.event.*;
import java.net.URL;


@SuppressWarnings("serial") // this is used to suppress a serializable warning because JPanel implements serializable
public class ChessSquarePanel extends JPanel implements MouseListener, ActionListener, Serializable {
    private String piece;
    private String player;
    private int position;
    private JLabel pieceLabel;
    private URL url;
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
            url = ChessGame.class.getResource("images/png/" + player.toLowerCase() + "_" + piece + ".png");
            pieceLabel = new JLabel(new ImageIcon(new ImageIcon(url).getImage().getScaledInstance(currentWidth*3/4+currentWidth/8, currentHeight*3/4+currentHeight/10, Image.SCALE_DEFAULT)));
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
        if(player != null && piece != null && (currentHeight != getHeight() || currentWidth != getWidth()) && (getWidth() != 0 || getHeight() != 0)) {
            // save current height & width
            currentHeight = getHeight();
            currentWidth = getWidth();

            url = ChessGame.class.getResource("images/png/" + player.toLowerCase() + "_" + piece + ".png");
            pieceLabel.setIcon(new ImageIcon(new ImageIcon(url).getImage().getScaledInstance(currentWidth*3/4+currentWidth/8, currentHeight*3/4+currentHeight/10, Image.SCALE_DEFAULT)));
        }
    }

    public void mouseClicked(MouseEvent event) {

        // determine if player is in check
        boolean checked;
        if(ChessGame.getCurrentPlayer().equals("White"))
            checked = ChessGame.isWhiteChecked();
        else
            checked = ChessGame.isBlackChecked();

        // leave this block to do nothing while playing is selecting a piece
        if(ChessGame.isPlayerSelectingAPiece()){}

        // player is checkmated
        else if(ChessGame.canBlackBeCheckMated() || ChessGame.canWhiteBeCheckMated()) {
            // player is checkmated
            System.out.print(ChessGame.getCurrentPlayer());
            System.out.println(" CHECKMATED!!!!");
            // get king pos
            int currKingPos;
            if(ChessGame.getCurrentPlayer().equals("Black"))
                currKingPos = ChessGame.getBlackKingPos();
            else
                currKingPos = ChessGame.getWhiteKingPos();
            // save color of king square
            ChessGame.getValidMovePositions()[currKingPos] = true;
            ChessGame.getValidMoveColors()[currKingPos] = ChessGame.getFrame().getBoard().squareAt(currKingPos).getBackground();
            // highlight king square red
            ChessGame.getFrame().getBoard().squareAt(currKingPos).setBackground(Color.RED);
        }

        // player is stalemated
        else if(ChessGame.isWhiteStaleMated() || ChessGame.isBlackStaleMated()) {
            System.out.println("stalemated");
        }

        // validate the correct player is clicking the square
        else if(ChessGame.getCurrentPlayer().equals(player)) {// && (!checked || piece.equals("king"))) {

            // should we disengage en passant?
            for(int i = 0; i < 64; i++){
                if(ChessGame.getFrame().getBoard().squareAt(i).getPawnWasHere()) {
                    if( i+8 < 64 && ChessGame.getFrame().getBoard().squareAt(i+8).getPlayer() != null && ChessGame.getFrame().getBoard().squareAt(i+8).getPlayer().equals("White")){
                        if(ChessGame.getFrame().getBoard().squareAt(i+8).getPiece() != null && ChessGame.getFrame().getBoard().squareAt(i+8).getPiece().equals("pawn"))
                            ChessGame.setEnPassant(i, true);
                    }
                    else if( i-8 >= 0 && ChessGame.getFrame().getBoard().squareAt(i-8).getPlayer() != null && ChessGame.getFrame().getBoard().squareAt(i-8).getPlayer().equals("Black")){
                        if(ChessGame.getFrame().getBoard().squareAt(i-8).getPiece() != null && ChessGame.getFrame().getBoard().squareAt(i-8).getPiece().equals("pawn"))
                            ChessGame.setEnPassant(i, true);
                    }
                }
            }


            // change background color back to default for previously clicked square
            if(ChessGame.getMovingFrom() != -1) {
                ChessGame.getFrame().getBoard().squareAt(ChessGame.getMovingFrom()).setBorder(BorderFactory.createLineBorder(Color.BLACK));
                ChessGame.getFrame().getBoard().squareAt(ChessGame.getMovingFrom()).setBackground(ChessGame.getSelectedSquaresColor());
                // also, unhighlight valid move squares
                for(int i = 0; i < 64; i++) {
                    if(ChessGame.getValidMovePositions()[i] && ChessGame.getFrame().getBoard().squareAt(i).getPiece() == null)
                        ChessGame.getFrame().getBoard().squareAt(i).setBackground(ChessGame.getValidMoveColors()[i]);
                    else if(ChessGame.getValidMovePositions()[i] && (!checked || !ChessGame.getFrame().getBoard().squareAt(i).getPiece().equals("king"))) {
                        if(ChessGame.getFrame().getBoard().squareAt(i).getBackground() != Color.RED)
                            ChessGame.getFrame().getBoard().squareAt(i).setBackground(ChessGame.getValidMoveColors()[i]);
                    }
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
            ArrayList<Integer> valid_moves = MoveLogic.get_valid_moves(ChessGame.getCurrentPlayer(), ChessGame.getSelectedPiece(), ChessGame.getMovingFrom());
            for(int x : valid_moves) {
                // save position of square we are highlighting
                if(ChessGame.getFrame().getBoard().squareAt(x).getBackground() != Color.RED) {
                    ChessGame.getValidMovePositions()[x] = true;
                    ChessGame.getValidMoveColors()[x] = ChessGame.getFrame().getBoard().squareAt(x).getBackground();
                }
                
                // highlight square
                ChessGame.getFrame().getBoard().squareAt(x).setBackground(new Color(0, 200, 0));
            }



        }
        else if(ChessGame.isMoving()){
            ChessSquarePanel currentPosition = ChessGame.getFrame().getBoard().squareAt(ChessGame.getMovingFrom());

            // get list of valid moves
            ArrayList<Integer> valid_moves = MoveLogic.get_valid_moves(ChessGame.getCurrentPlayer(), ChessGame.getSelectedPiece(), ChessGame.getMovingFrom());

            // validate move
            if(valid_moves.contains(position)){

                ArrayList<Integer> moves;
                String enemyPiece;
                String enemyPlayer;
                JLabel enemyPieceLabel;

                // remove piece from its current position
                currentPosition.remove(currentPosition.getPieceLabel());
                currentPosition.setPiece(null, null, ChessGame.getMovingFrom());

                // save clicked positions piece and player
                if(piece != null && player != null) {
                    enemyPiece = new String(piece);
                    enemyPlayer = new String(player);
                    enemyPieceLabel = pieceLabel;
                }
                else {
                    enemyPiece = null;
                    enemyPlayer = null;
                    enemyPieceLabel = null;
                }

                // move the piece to its new position
                setPiece(ChessGame.getSelectedPiece(), ChessGame.getCurrentPlayer(), position);

                // update king's position if the king moved
                if(ChessGame.getCurrentPlayer().equals("White") && ChessGame.getSelectedPiece().equals("king"))
                    ChessGame.setWhiteKingPos(position);
                else if(ChessGame.getCurrentPlayer().equals("Black") && ChessGame.getSelectedPiece().equals("king"))
                    ChessGame.setBlackKingPos(position);

                
                // CHECKMATE DETECTION!!!!!!!!!!!!!
                // /*
                int currking;
                String currplayer;

                //System.out.println(ChessGame.getCurrentPlayer());

                // keep track of the last player who went as the current player
                if(ChessGame.getCurrentPlayer().equals("White")) {
                    currplayer = "Black";
                    currking = ChessGame.getBlackKingPos();
                    //ChessGame.setCurrentPlayer("Black");
                }
                    
                else {
                    currplayer = "White";
                    currking = ChessGame.getWhiteKingPos();
                    //ChessGame.setCurrentPlayer("White");
                }

                ChessGame.changeCurrentPlayer();

                // see if player is checked, this is used to determine checkmate or stalemate
                if(MoveLogic.isBoardStateInCheck(this, false))
                    checked = true;
                else
                    checked = false;


                boolean checkmated = true;
                //boolean foundValidMove = false;
                String enemyPiece2;
                String enemyPlayer2;
                JLabel enemyPieceLabel2;
                for(int i = 0; i < 64; i++) {

                    // if we found out player is not checkmated early, break out (saves time complexity)
                    if(!checkmated)
                        break;

                    // save current square
                    ChessSquarePanel currentsquare = ChessGame.getFrame().getBoard().squareAt(i);

                    // for each of the current player's pieces
                    if(currentsquare.getPlayer() != null && currentsquare.getPlayer().equals(currplayer)){

                        ArrayList<Integer> validmoves = new ArrayList<>(MoveLogic.get_valid_moves(currentsquare.getPlayer(), currentsquare.getPiece(), i));

                        // no valid moves
                        //if(!validmoves.isEmpty())
                        //    foundValidMove = true;

                        // make a move for this piece and then see if it puts king in check
                        for(int newmove : validmoves) {
                            // save square for the newmove
                            ChessSquarePanel newsquare = ChessGame.getFrame().getBoard().squareAt(newmove);

                            // save moved square's piece and player
                            if(newsquare.getPiece() != null && newsquare.getPlayer() != null) {
                                enemyPiece2 = new String(newsquare.getPiece());
                                enemyPlayer2 = new String(newsquare.getPlayer());
                                enemyPieceLabel2 = newsquare.getPieceLabel();
                            }
                            else {
                                enemyPiece2 = null;
                                enemyPlayer2 = null;
                                enemyPieceLabel2 = null;
                            }

                            // make the move
                            newsquare.setPiece(currentsquare.getPiece(), currplayer, newmove);

                            // update king's position if the king moved
                            if(currplayer.equals("White") && currentsquare.getPiece().equals("king")){
                                ChessGame.setWhiteKingPos(newmove);
                                currking = newmove;
                            }
                            else if(currplayer.equals("Black") && currentsquare.getPiece().equals("king")){
                                ChessGame.setBlackKingPos(newmove);
                                currking = newmove;
                            }

                            // remove piece from its current position
                            currentsquare.remove(currentsquare.getPieceLabel());
                            currentsquare.setPiece(null, null, i);

                            boolean checkPossiblyMated = true;
                            boolean validEnemyMoves = false;

                            // find squares that can potentially put the enemy king in checkmate
                            moves = MoveLogic.get_valid_moves(currplayer, "queen", currking);
                            moves.addAll(MoveLogic.get_valid_moves(currplayer, "knight", currking));

                            // if no places to put king in check, then king is obviously not in check
                            if(moves.isEmpty()){
                                //System.out.println("HIT");
                                checkPossiblyMated = false;
                            }

                            // change player
                            ChessGame.changeCurrentPlayer();

                            // save list of squares that king can be checked in to reduce work
                            ArrayList<Integer> savedCheckSquares = new ArrayList<Integer>();
                            ArrayList<Integer> savedCheckPiecesSquares = new ArrayList<Integer>();

                            // go thru squares, and see if any piece can put the player in check
                            for(int move : moves) {
                                //System.out.println(ChessGame.getCurrentPlayer());
                                if(!checkPossiblyMated) { // break early if we know we are not checked
                                    //System.out.println("HIT");
                                    break;
                                }

                                ChessSquarePanel possiblechecksquare = ChessGame.getFrame().getBoard().squareAt(move);

                                // if square has a piece, get its valid moves
                                if(possiblechecksquare.getPlayer() != null && possiblechecksquare.getPlayer().equals(ChessGame.getCurrentPlayer())){
                                    //System.out.println("HIT");
                                    validEnemyMoves = true;

                                    ArrayList<Integer> movelist = MoveLogic.get_valid_moves(possiblechecksquare.getPlayer(), possiblechecksquare.getPiece(), move);

                                    // if piece does not put player in check, there is no checkmate
                                    if(!movelist.contains(currking)){
                                        // make sure its not another piece putting the king in check
                                        if(!savedCheckSquares.contains(currking)) {
                                            //System.out.println(savedCheckSquares);
                                            //System.out.print("POSSIBLY NOT IN CHECK ");
                                            //System.out.println(newsquare.getPiece());
                                            //System.out.print(" at ");
                                            //System.out.print(move);
                                            //System.out.print(possiblechecksquare.getPiece() + " does not contain ");
                                            //System.out.println(currking);
                                            checkPossiblyMated = false;
                                        }
                                        
                                    }
                                    else {
                                        // TO DO: ADD THIS TO A LIST OF SQUARES TO BE HIGHLIGHTED RED
                                        //System.out.print(possiblechecksquare.getPiece() + " can check king at ");
                                        //System.out.println(currking);
                                        savedCheckSquares.add(currking);
                                        savedCheckPiecesSquares.add(move);
                                    }
                                }
                                // no enemy pieces
                                else {

                                }
                            }

                            // highlight all squares player is checkmated by
                            //for(int checkedSquare : savedCheckPiecesSquares) {
                            //    ChessSquarePanel sq = ChessGame.getFrame().getBoard().squareAt(checkedSquare);
                            //    //ChessGame.getValidMoveColors()[checkedSquare] = sq.getBackground();
                            //    //ChessGame.getValidMovePositions()[checkedSquare] = true;
                            //    //sq.setBackground(Color.RED);
                            //}
                            

                            // change player back
                            ChessGame.changeCurrentPlayer();

                            // put piece back
                            currentsquare.setPiece(newsquare.getPiece(), currplayer, i);

                            // undo the move
                            newsquare.remove(newsquare.getPieceLabel());
                            newsquare.setPieceLabel(enemyPieceLabel2);
                            newsquare.setCurrentPiece(enemyPiece2);
                            newsquare.setCurrentPlayer(enemyPlayer2);

                            // update king's position if the king moved
                            if(currplayer.equals("White") && currentsquare.getPiece().equals("king")){
                                currking = i;
                                ChessGame.setWhiteKingPos(i);
                            }
                            else if(currplayer.equals("Black") && currentsquare.getPiece().equals("king")) {
                                currking = i;
                                ChessGame.setBlackKingPos(i);
                            }

                            // if player is not in check, then player is obviously not checkmated
                            if(!checkPossiblyMated || !validEnemyMoves){
                                //System.out.println("not checkmated");
                                checkmated = false;
                                break;
                            }
                        }
                    }
                }

                // no valid moves
                //if(!foundValidMove)
                //    checkmated = false;

                // handle updating the appropriate variables if player is checkmated or stalemated
                if(currplayer.equals("White") && checkmated){
                    if(checked){
                        //System.out.println("we got a checkmate sonny boy");
                        ChessGame.whiteIsCheckMated();
                    }
                    else{
                        //System.out.println("we got a stalemate sonny boy");
                        ChessGame.whiteIsStaleMated();
                    }
                }
                else if(currplayer.equals("Black") && checkmated){
                    if(checked){
                        //System.out.println("we got a checkmate sonny boy");
                        ChessGame.blackIsCheckMated();
                    }
                    else{
                        //System.out.println("we got a stalemate sonny boy");
                        ChessGame.whiteIsStaleMated();
                    }
                }

                // set player back to normal
                ChessGame.changeCurrentPlayer();

                // player is not checkmated
                if((ChessGame.getCurrentPlayer().equals("White") && !ChessGame.canWhiteBeCheckMated()) || (ChessGame.getCurrentPlayer().equals("Black") && !ChessGame.canBlackBeCheckMated())) {

                    //System.out.println("Not checkmated");

                    // CHECK DETECTION!!!!!!!!!!!!!!
                    MoveLogic.isBoardStateInCheck(this, true);

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

                        // move piece to clicked position
                        if(enemyPieceLabel != null) {
                            remove(enemyPieceLabel);
                        }

                        // CASTlING
                        // castling white
                        // left
                        if(ChessGame.getCurrentPlayer().equals("White") && ChessGame.getSelectedPiece().equals("king") && position == 2 )
                            castle(0,3, ChessGame.getCurrentPlayer());
                        
                        // right
                        if(ChessGame.getCurrentPlayer().equals("White") && ChessGame.getSelectedPiece().equals("king") && position == 6 )
                            castle(7,5, ChessGame.getCurrentPlayer());
                        
                        // castling black
                        // left
                        if(ChessGame.getCurrentPlayer().equals("Black") && ChessGame.getSelectedPiece().equals("king") && position == 58 )
                            castle(56,59, ChessGame.getCurrentPlayer());
                        
                        // right
                        if(ChessGame.getCurrentPlayer().equals("Black") && ChessGame.getSelectedPiece().equals("king") && position == 62 )
                            castle(63,61, ChessGame.getCurrentPlayer());
                        

                        // display move message
                        String msg = ChessGame.getCurrentPlayer() + " " + ChessGame.getSelectedPiece() + ": " + MoveLogic.pos_to_AN[ChessGame.getMovingFrom()] + " - " + MoveLogic.pos_to_AN[position] + "\n";
                        ChessGame.getFrame().appendTextArea(msg);

                        // lazy solution, set color back to default
                        currentPosition.setBackground(ChessGame.getSelectedSquaresColor());
                        currentPosition.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                        // also, unhighlight valid move squares
                        for(int i = 0; i < 64; i ++) {
                            if(ChessGame.getValidMovePositions()[i]) {
                                ChessGame.getFrame().getBoard().squareAt(i).setBackground(ChessGame.getValidMoveColors()[i]);
                                ChessGame.getValidMovePositions()[i] = false;
                            }
                        }

                        // get moves to see if other player is in check
                        moves = MoveLogic.get_valid_moves(ChessGame.getCurrentPlayer(), ChessGame.getSelectedPiece(), position);

                        ChessGame.blackIsChecked(false);
                        ChessGame.whiteIsChecked(false);

                        // check if other player is in check
                        if(ChessGame.getCurrentPlayer().equals("White")){
                            if(moves.contains(ChessGame.getBlackKingPos())){
                                ChessGame.blackIsChecked(true);
                                // store color of squares that are being highlighted red
                                if(ChessGame.getFrame().getBoard().squareAt(position).getBackground() != Color.RED) {
                                    ChessGame.getValidMovePositions()[position] = true;
                                    ChessGame.getValidMoveColors()[position] = ChessGame.getFrame().getBoard().squareAt(position).getBackground();
                                    ChessGame.getValidMovePositions()[ChessGame.getBlackKingPos()] = true;
                                    ChessGame.getValidMoveColors()[ChessGame.getBlackKingPos()] = ChessGame.getFrame().getBoard().squareAt(ChessGame.getBlackKingPos()).getBackground();
                                }
                                // highlight squares red
                                ChessGame.getFrame().getBoard().squareAt(position).setBackground(Color.RED);
                                ChessGame.getFrame().getBoard().squareAt(ChessGame.getBlackKingPos()).setBackground(Color.RED);
                            }
                        }
                        else{
                            if(moves.contains(ChessGame.getWhiteKingPos())) {
                                ChessGame.whiteIsChecked(true);
                                // store color of squares that are being highlighted red
                                if(ChessGame.getFrame().getBoard().squareAt(position).getBackground() != Color.RED) {
                                    
                                    ChessGame.getValidMovePositions()[position] = true;
                                    ChessGame.getValidMoveColors()[position] = ChessGame.getFrame().getBoard().squareAt(position).getBackground();
                                    ChessGame.getValidMovePositions()[ChessGame.getWhiteKingPos()] = true;
                                    ChessGame.getValidMoveColors()[ChessGame.getWhiteKingPos()] = ChessGame.getFrame().getBoard().squareAt(ChessGame.getWhiteKingPos()).getBackground();
                                }
                                // highlight squares red
                                ChessGame.getFrame().getBoard().squareAt(position).setBackground(Color.RED);
                                ChessGame.getFrame().getBoard().squareAt(ChessGame.getWhiteKingPos()).setBackground(Color.RED);
                            }
                        }

                        // update king's position if the king moved (we do this again in case the king re-checked itself)
                        if(ChessGame.getCurrentPlayer().equals("White") && ChessGame.getSelectedPiece().equals("king"))
                            ChessGame.setWhiteKingPos(position);
                        else if(ChessGame.getCurrentPlayer().equals("Black") && ChessGame.getSelectedPiece().equals("king"))
                            ChessGame.setBlackKingPos(position);

                        // pawn's first move, trigger possibility for en passant immediately
                        if(!ChessGame.getFrame().getBoard().squareAt(ChessGame.getMovingFrom()).hasPieceMoved()) {
                            // pawn moved down 2 squares (white), engage en passant rule
                            if(position == 16 + ChessGame.getMovingFrom())
                                ChessGame.getFrame().getBoard().squareAt(ChessGame.getMovingFrom() + 8).setPawnWasHere(true);
                            // pawn moved up 2 squares (black), engage en passant rule
                            else if(position == ChessGame.getMovingFrom() - 16)
                                ChessGame.getFrame().getBoard().squareAt(ChessGame.getMovingFrom() - 8).setPawnWasHere(true);
                        }

                        // piece has been moved (used for pawns first move, and kings+rooks for castling)
                        ChessGame.getFrame().getBoard().squareAt(ChessGame.getMovingFrom()).pieceHasMoved();
                        hasMoved = true;

                        if(ChessGame.getCurrentPlayer().equals("Black"))
                            checked = ChessGame.isWhiteChecked();
                        else
                            checked = ChessGame.isBlackChecked();

                        for(int i = 0; i < 64; i++){
                            // disengage en passant
                            if(ChessGame.getEnPassant(i)) {
                                ChessGame.getFrame().getBoard().squareAt(i).setPawnWasHere(false);
                                ChessGame.setEnPassant(i, false);
                                // oh my god en passant?? :> who has ever even heard of this srsly?
                                if(ChessGame.getFrame().getBoard().squareAt(i).getPiece() != null && 
                                    ChessGame.getFrame().getBoard().squareAt(i).getPiece().equals("pawn") && 
                                    ChessGame.getFrame().getBoard().squareAt(i).getPlayer().equals(ChessGame.getCurrentPlayer())) {
                                    // piece was captured via en passant CORRECTLY 
                                    if(ChessGame.getCurrentPlayer().equals("White")) {
                                        ChessGame.getFrame().getBoard().squareAt(i-8).remove(ChessGame.getFrame().getBoard().squareAt(i-8).getPieceLabel());
                                        ChessGame.getFrame().getBoard().squareAt(i-8).setPiece(null,  null, i-8);
                                        ChessGame.getFrame().getBoard().squareAt(i-8).repaint();
                                    }
                                    else {
                                        ChessGame.getFrame().getBoard().squareAt(i+8).remove(ChessGame.getFrame().getBoard().squareAt(i+8).getPieceLabel());
                                        ChessGame.getFrame().getBoard().squareAt(i+8).setPiece(null,  null, i+8);
                                        ChessGame.getFrame().getBoard().squareAt(i+8).repaint();
                                    }
                                    

                                }
                            }

                            // if king is not in check, unhighlight red squares
                            if(!checked && ChessGame.getFrame().getBoard().squareAt(i).getBackground() == Color.RED){
                                ChessGame.getFrame().getBoard().squareAt(i).setBackground(ChessGame.getValidMoveColors()[i]);
                                ChessGame.getValidMovePositions()[i] = false;
                            }
                        }

                        // clear data for previously selected piece
                        ChessGame.setCurrentlyMoving(false);
                        ChessGame.setMovingFrom(-1);
                        ChessGame.setSelectedPiece(null);

                        // play sound for a successful move
                        ChessGame.getClickSound().play();

                        // change turn to other player
                        if(ChessGame.getCurrentPlayer().equals("White")) {
                            ChessGame.setCurrentPlayer("Black");
                            ChessGame.getFrame().setNorthTextField("Black's Move");
                        }
                        else {
                            ChessGame.setCurrentPlayer("White");
                            ChessGame.getFrame().setNorthTextField("White's Move");
                        }

                        // if pawn made it to end of board, allow current player to exchange it
                        if(ChessGame.getFrame().getBoard().squareAt(position).getPiece().equals("pawn") &&
                           ChessGame.getEndPos().contains(position)) {
                           PieceSelectionScreen pieceSelectionScreen = new PieceSelectionScreen();
                           ChessGame.setSuperPawn(position);
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
        }
        else {
            flashCount = 0;
            timer.stop();
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

    //castling
    public void castle(int rook_starting_pos, int rook_ending_pos, String currplayer){
        ChessSquarePanel rook_starting = ChessGame.getFrame().getBoard().squareAt(rook_starting_pos);
        rook_starting.remove(rook_starting.getPieceLabel());
        rook_starting.setPiece(null, null, rook_starting_pos);
        rook_starting.repaint();
        ChessSquarePanel newpos = ChessGame.getFrame().getBoard().squareAt(rook_ending_pos);
        newpos.setPiece("rook", currplayer, rook_ending_pos);
        newpos.repaint();
    }

}