import java.util.ArrayList;
import javax.swing.JLabel;

public class MoveLogic {

    public static String[] pos_to_AN = {"a8","b8","c8","d8","e8","f8","g8","h8",
            "a7","b7","c7","d7","e7","f7","g7","h7",
            "a6","b6","c6","d6","e6","f6","g6","h6",
            "a5","b5","c5","d5","e5","f5","g5","h5",
            "a4","b4","c4","d4","e4","f4","g4","h4",
            "a3","b3","c3","d3","e3","f3","g3","h3",
            "a2","b2","c2","d2","e2","f2","g2","h2",
            "a1","b1","c1","d1","e1","f1","g1","h1"};

    // check to see if coordinates are a valid move on the board
    public static boolean coords_valid_check(int[] coords){
        if(coords[0] >= 0 && coords[0] <= 7 && coords[1] >= 0 && coords[1] <= 7) {
            if(ChessGame.getFrame().getBoard().squareAt(coords[0] * 8 + coords[1]).getPlayer() != ChessGame.getCurrentPlayer())
                return true;
        }
        return false;
    }

    // converts a position on the game board to its corresponding coordinates
    public static int[] position_to_coord(int pos) {
        int[] the_coords = {0,0};

        for(int i = 0; i < pos; i++){
            the_coords[1]++;
            if (the_coords[1] > 7){
                the_coords[1]=0;
                the_coords[0]++;
            }
        }
        return the_coords;
    }

    // converts a pair (array size 2) of coordinates to a position on a the game board
    // coords[0] = x-value        coords[1] = y-value
    public static int coord_to_position(int[] coords) {
        return coords[0]*8+coords[1];
    }

    public static void movePawn(int[] new_move, ArrayList<Integer> my_moves, boolean hasMoved, int curr_pos) {
        // allows pawn to move one unit forward
        if(ChessGame.getCurrentPlayer().equals("White"))
            new_move[0]++;
        else
            new_move[0]--;
        if(coords_valid_check(new_move)) {
            // make sure pawn can move forward
            if(ChessGame.getFrame().getBoard().squareAt(coord_to_position(new_move)).getPiece() == null)
                my_moves.add(coord_to_position(new_move));
        }

        // allows pawn to move two spaces on its first move
        if(coords_valid_check(new_move) && !hasMoved && ChessGame.getFrame().getBoard().squareAt(coord_to_position(new_move)).getPiece() == null) {
            if(ChessGame.getCurrentPlayer().equals("White"))
                new_move[0]++;
            else
                new_move[0]--;
            if(coords_valid_check(new_move)) {
                // make sure pawn can move forward
                if(ChessGame.getFrame().getBoard().squareAt(coord_to_position(new_move)).getPiece() == null)
                    my_moves.add(coord_to_position(new_move));
            }
        }

        new_move = position_to_coord(curr_pos);

        if(ChessGame.getCurrentPlayer().equals("White")) {
            // diagnol left
            new_move[0]++;
            new_move[1]--;

            // validate move
            if(coords_valid_check(new_move)) {
                String player = ChessGame.getFrame().getBoard().squareAt(coord_to_position(new_move)).getPlayer();
                if(player != null && !player.equals(ChessGame.getCurrentPlayer()))
                    my_moves.add(coord_to_position(new_move));
            }

            // EN PASSANT (IN PASSING)
            if(coords_valid_check(new_move) && ChessGame.getFrame().getBoard().squareAt(coord_to_position(new_move)).getPawnWasHere()) {
                System.out.print("pawn was at: ");
                System.out.println(coord_to_position(new_move));
                ChessSquarePanel sq = ChessGame.getFrame().getBoard().squareAt(coord_to_position(new_move) - 8);
                if(ChessGame.isSpaceEmpty(coord_to_position(new_move)) && sq.getPiece() != null) {
                    if(sq.getPiece().equals("pawn") && sq.getPlayer().equals("Black"))
                        my_moves.add(coord_to_position(new_move));
                }
            }

            // diagnol right
            new_move[1] += 2;

            // validate move
            if(coords_valid_check(new_move)) {
                String player = ChessGame.getFrame().getBoard().squareAt(coord_to_position(new_move)).getPlayer();
                if(player != null && !player.equals(ChessGame.getCurrentPlayer()))
                    my_moves.add(coord_to_position(new_move));
            }

            // EN PASSANT (IN PASSING)
            if(coords_valid_check(new_move) && ChessGame.getFrame().getBoard().squareAt(coord_to_position(new_move)).getPawnWasHere()) {
                ChessSquarePanel sq = ChessGame.getFrame().getBoard().squareAt(coord_to_position(new_move) - 8);
                if(ChessGame.isSpaceEmpty(coord_to_position(new_move)) && sq.getPiece() != null) {
                    if(sq.getPiece().equals("pawn") && sq.getPlayer().equals("Black"))
                        my_moves.add(coord_to_position(new_move));
                }
            }
        }
        else {
            // diagnol left
            new_move[0]--;
            new_move[1]--;

            // validate move
            if(coords_valid_check(new_move)) {
                String player = ChessGame.getFrame().getBoard().squareAt(coord_to_position(new_move)).getPlayer();
                if(player != null && !player.equals(ChessGame.getCurrentPlayer()))
                    my_moves.add(coord_to_position(new_move));
            }

            // diagnol right
            new_move[1] += 2;

            // validate move
            if(coords_valid_check(new_move)) {
                String player = ChessGame.getFrame().getBoard().squareAt(coord_to_position(new_move)).getPlayer();
                if(player != null && !player.equals(ChessGame.getCurrentPlayer()))
                    my_moves.add(coord_to_position(new_move));
            }
        }
    }

    public static void moveRook(int[] new_move, ArrayList<Integer> my_moves, boolean hasMoved, int curr_pos) {
        // allows rook to move down vertically
        while(true){
            new_move[0]++;
            if(!coords_valid_check(new_move))
                break;
            my_moves.add(coord_to_position(new_move));
            if(ChessGame.isSpaceEmpty(coord_to_position(new_move)))
                break;
        }

        new_move = position_to_coord(curr_pos);

        // allows rook to move up vertically
        while(true){
            new_move[0]--;
            if(!coords_valid_check(new_move))
                break;
            my_moves.add(coord_to_position(new_move));
            if(ChessGame.isSpaceEmpty(coord_to_position(new_move)))
                break;
        }

        new_move = position_to_coord(curr_pos);

        // allows rook to move right horizontally
        while(true){
            new_move[1]++;
            if(!coords_valid_check(new_move))
                break;
            my_moves.add(coord_to_position(new_move));
            if(ChessGame.isSpaceEmpty(coord_to_position(new_move)))
                break;
        }

        new_move = position_to_coord(curr_pos);

        // allows rook to move left horizontally
        while(true){
            new_move[1]--;
            if(!coords_valid_check(new_move))
                break;
            my_moves.add(coord_to_position(new_move));
            if(ChessGame.isSpaceEmpty(coord_to_position(new_move)))
                break;
        }

        // TODO: IMPLEMENT CASTLING FOR ROOKS
    }

    public static void moveKnight(int[] new_move, ArrayList<Integer> my_moves, int curr_pos) {
        // allows knight to move right (HOOK UP)
        new_move[0]+=1;
        new_move[1]+=2;
        if(coords_valid_check(new_move))
            my_moves.add(coord_to_position(new_move));
        // allows knight to move right (HOOK DOWN)
        new_move = position_to_coord(curr_pos);
        new_move[0]-=1;
        new_move[1]+=2;
        if(coords_valid_check(new_move))
            my_moves.add(coord_to_position(new_move));


        // allows knight to move left (HOOK UP)
        new_move = position_to_coord(curr_pos);
        new_move[0]-=1;
        new_move[1]-=2;
        if(coords_valid_check(new_move))
            my_moves.add(coord_to_position(new_move));
        // allows knight to move left (HOOK DOWN)
        new_move = position_to_coord(curr_pos);
        new_move[0]+=1;
        new_move[1]-=2;
        if(coords_valid_check(new_move))
            my_moves.add(coord_to_position(new_move));


        // allows knight to move up (HOOK LEFT)
        new_move = position_to_coord(curr_pos);
        new_move[0]-=2;
        new_move[1]-=1;
        if(coords_valid_check(new_move))
            my_moves.add(coord_to_position(new_move));
        // allows knight to move up (HOOK RIGHT)
        new_move = position_to_coord(curr_pos);
        new_move[0]-=2;
        new_move[1]+=1;
        if(coords_valid_check(new_move))
            my_moves.add(coord_to_position(new_move));


        // allows knight to move down (HOOK LEFT)
        new_move = position_to_coord(curr_pos);
        new_move[0]+=2;
        new_move[1]-=1;
        if(coords_valid_check(new_move))
            my_moves.add(coord_to_position(new_move));
        // allows knight to move down (HOOK LEFT)
        new_move = position_to_coord(curr_pos);
        new_move[0]+=2;
        new_move[1]+=1;
        if(coords_valid_check(new_move))
            my_moves.add(coord_to_position(new_move));
    }

    public static void moveBishop(int[] new_move, ArrayList<Integer> my_moves, int curr_pos) {
        // allows bishop to move diagonally
        while(true){
            new_move[0]++;
            new_move[1]++;
            if(!coords_valid_check(new_move))
                break;
            my_moves.add(coord_to_position(new_move));
            if(ChessGame.isSpaceEmpty(coord_to_position(new_move)))
                break;
        }

        new_move = position_to_coord(curr_pos);

        while(true){
            new_move[0]--;
            new_move[1]--;
            if(!coords_valid_check(new_move))
                break;
            my_moves.add(coord_to_position(new_move));
            if(ChessGame.isSpaceEmpty(coord_to_position(new_move)))
                break;
        }

        new_move = position_to_coord(curr_pos);

        while(true){
            new_move[0]--;
            new_move[1]++;
            if(!coords_valid_check(new_move))
                break;
            my_moves.add(coord_to_position(new_move));
            if(ChessGame.isSpaceEmpty(coord_to_position(new_move)))
                break;
        }

        new_move = position_to_coord(curr_pos);

        while(true){
            new_move[0]++;
            new_move[1]--;
            if(!coords_valid_check(new_move))
                break;
            my_moves.add(coord_to_position(new_move));
            if(ChessGame.isSpaceEmpty(coord_to_position(new_move)))
                break;
        }
    }

    public static void moveQueen(int[] new_move, ArrayList<Integer> my_moves, int curr_pos) {
        moveBishop(new_move, my_moves, curr_pos); // allows queen to move diagonally
        new_move = position_to_coord(curr_pos);
        moveRook(new_move, my_moves, true, curr_pos); // allows queen to move vertically
    }

    public static void moveKing(int[] new_move, ArrayList<Integer> my_moves, int curr_pos, boolean hasMoved) {
        // allows king to move diagonally up/left
        new_move[0]--;
        new_move[1]--;
        if(coords_valid_check(new_move))
            my_moves.add(coord_to_position(new_move));

        // allows king to move diagonally up/right
        new_move = position_to_coord(curr_pos);
        new_move[0]--;
        new_move[1]++;
        if(coords_valid_check(new_move))
            my_moves.add(coord_to_position(new_move));

        // allows king to move diagonally down/right
        new_move = position_to_coord(curr_pos);
        new_move[0]++;
        new_move[1]++;
        if(coords_valid_check(new_move))
            my_moves.add(coord_to_position(new_move));

        // allows king to move diagonally down/left
        new_move = position_to_coord(curr_pos);
        new_move[0]++;
        new_move[1]--;
        if(coords_valid_check(new_move))
            my_moves.add(coord_to_position(new_move));

        // allows king to move up
        new_move = position_to_coord(curr_pos);
        new_move[0]--;
        if(coords_valid_check(new_move))
            my_moves.add(coord_to_position(new_move));

        // allows king to move down
        new_move = position_to_coord(curr_pos);
        new_move[0]++;
        if(coords_valid_check(new_move))
            my_moves.add(coord_to_position(new_move));

        // allows king to move left
        new_move = position_to_coord(curr_pos);
        new_move[1]++;
        if(coords_valid_check(new_move))
            my_moves.add(coord_to_position(new_move));

        // allows king to move right
        new_move = position_to_coord(curr_pos);
        new_move[1]--;
        if(coords_valid_check(new_move))
            my_moves.add(coord_to_position(new_move));
    }

    // returns ArrayList of valid positions to choose from
    public static ArrayList<Integer> get_valid_moves(String player, String piece, int curr_pos, boolean recurse){
        ArrayList<Integer> my_moves = new ArrayList<>();
        boolean pieceHasMoved = ChessGame.getFrame().getBoard().squareAt(curr_pos).hasPieceMoved();
        int[] new_move = position_to_coord(curr_pos);
        if(piece.equals("pawn"))
            movePawn(new_move, my_moves, pieceHasMoved, curr_pos);
        else if(piece.equals("rook"))
            moveRook(new_move, my_moves, pieceHasMoved, curr_pos);
        else if(piece.equals("knight"))
            moveKnight(new_move, my_moves, curr_pos);
        else if(piece.equals("bishop"))
            moveBishop(new_move, my_moves, curr_pos);
        else if(piece.equals("queen"))
            moveQueen(new_move, my_moves, curr_pos);
        else if(piece.equals("king"))
            moveKing(new_move, my_moves, curr_pos, pieceHasMoved);
        /*
        if(!recurse)
            return my_moves;

        // TODO: prune moves that put you in check
        for(int move : my_moves){
            // save piece and player in desired location
            String tmpPiece = ChessGame.getFrame().getBoard().squareAt(move).getPiece();
            String tmpPlayer = ChessGame.getFrame().getBoard().squareAt(move).getPlayer();
            // move piece to desired location
            ChessGame.getFrame().getBoard().squareAt(curr_pos).setPiece(null, null, curr_pos);
            ChessGame.getFrame().getBoard().squareAt(move).setPiece(piece, player, move);
            // check if move puts player in check

            if(player.equals("White")){
                for(int i = 0; i < 64; i++) {
                    if(ChessGame.getFrame().getBoard().squareAt(i).getPlayer() == "Black") {
                        // for every piece on the board, get its valid moves
                        ArrayList<Integer> moves = get_valid_moves("Black", ChessGame.getFrame().getBoard().squareAt(i).getPiece(), i, true);
                        if(moves.contains(ChessGame.getWhiteKingPos()) || (piece == "King" && moves.contains(move))){
                            my_moves.remove(Integer.valueOf(i));
                            // white king is in check
                        }
                    }
                }
            }
            else{
                for(int i = 0; i < 64; i++) {
                    if(ChessGame.getFrame().getBoard().squareAt(i).getPlayer() == "White") {
                        // for every piece on the board, get its valid moves
                        ArrayList<Integer> moves = get_valid_moves("White", ChessGame.getFrame().getBoard().squareAt(i).getPiece(), i, true);
                        if(moves.contains(ChessGame.getBlackKingPos()) || (piece == "King" && moves.contains(move))) {
                            // black king is in check
                            my_moves.remove(Integer.valueOf(i));
                        }
                    }
                }
            }

            // put piece back to where it was moved from
            ChessGame.getFrame().getBoard().squareAt(curr_pos).setPiece(piece, player, move);
            ChessGame.getFrame().getBoard().squareAt(move).setPiece(tmpPiece, tmpPlayer, curr_pos);

            
        }*/
        return my_moves;
    }

    public static void doMovesPutPlayerInCheck(ArrayList<Integer> moves, String currpiece, int pos) {
        String enemyPiece;
        String enemyPlayer;
        JLabel enemyPieceLabel;
        String currplayer = ChessGame.getCurrentPlayer();
        String notcurrplayer;
        int kingpos;
        ChessSquarePanel currsquare = ChessGame.getFrame().getBoard().squareAt(pos);
        if(currplayer.equals("White"))
            kingpos = ChessGame.getWhiteKingPos();
        else
            kingpos = ChessGame.getBlackKingPos();

        ArrayList<Integer> movelist = new ArrayList<>();
        if(currplayer.equals("White"))
            notcurrplayer = new String("Black");
        else
            notcurrplayer = new String("White");

        boolean checked = true; // used to determine if player is checkmated

        // check to see if any one of the moves puts the player in check
        for(Integer move : moves) {
            ChessSquarePanel newsquare = ChessGame.getFrame().getBoard().squareAt(move);
            
            // remove piece from its current position
            currsquare.remove(currsquare.getPieceLabel());
            currsquare.setPiece(null, null, pos);

            // save moved square's piece and player
            String piece = newsquare.getPiece();
            String player = newsquare.getPlayer();
            if(piece != null && player != null) {
                enemyPiece = new String(piece);
                enemyPlayer = new String(player);
                enemyPieceLabel = newsquare.getPieceLabel();
            }
            else {
                enemyPiece = null;
                enemyPlayer = null;
                enemyPieceLabel = null;
            }

            // make the move
            newsquare.setPiece(currpiece, currplayer, move);

            // update king's position if the king moved
            if(currplayer.equals("White") && currpiece.equals("king"))
                ChessGame.setWhiteKingPos(move);
            else if(currplayer.equals("Black") && currpiece.equals("king"))
                ChessGame.setBlackKingPos(move);


            // check the board to see if the enemy can put current player in check
            for(int i = 0; i < 64; i++){
                // for each enemy piece
                if(ChessGame.getFrame().getBoard().squareAt(i).getPlayer().equals(notcurrplayer)) {
                    // get their valid moves
                    ChessGame.setCurrentPlayer(notcurrplayer); // this is a janky fix, don't worry about it ;)
                    movelist = MoveLogic.get_valid_moves(notcurrplayer, ChessGame.getFrame().getBoard().squareAt(i).getPiece(), i, false);
                    ChessGame.setCurrentPlayer(currplayer); // this is a janky fix, don't worry about it ;)
                    
                    // if there exists a valid move, user is not checkmated
                    if(!movelist.contains(kingpos))
                        checked = false;

                    currsquare.setPiece(currpiece, currplayer, pos); // put piece back

                    // undo the move
                    newsquare.remove(newsquare.getPieceLabel());
                    newsquare.setPieceLabel(enemyPieceLabel);
                    newsquare.setCurrentPiece(enemyPiece);
                    newsquare.setCurrentPlayer(enemyPlayer);

                    // update king's position if the king moved
                    if(currplayer.equals("White") && currpiece.equals("king"))
                        ChessGame.setWhiteKingPos(pos);
                    else if(currplayer.equals("Black") && currpiece.equals("king"))
                        ChessGame.setBlackKingPos(pos);

                    if(!checked)
                        break; // end the looping early since we KNOW player is not in checkmate
                }
            }
        }

        // if checked is still true, then player has no valid moves, and is checkmated
        if(checked)
            moves.clear();
    }

}