/*

    Zach Sirotto and Ben Cook
    Assignment X - Chess!!

*/
    
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
            if(ChessGame.getFrame().getBoard().squareAt(coords[0] * 8 + coords[1]).getPlayer() == null ||
            !ChessGame.getFrame().getBoard().squareAt(coords[0] * 8 + coords[1]).getPlayer().equals(ChessGame.getCurrentPlayer()))
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
            if(coords_valid_check(new_move) && ChessGame.getFrame().getBoard().squareAt(coord_to_position(new_move)).getPawnWasHere())
                my_moves.add(coord_to_position(new_move));

            // diagnol right
            new_move[1] += 2;

            // validate move
            if(coords_valid_check(new_move)) {
                String player = ChessGame.getFrame().getBoard().squareAt(coord_to_position(new_move)).getPlayer();
                if(player != null && !player.equals(ChessGame.getCurrentPlayer()))
                    my_moves.add(coord_to_position(new_move));
            }

            // EN PASSANT (IN PASSING)
            if(coords_valid_check(new_move) && ChessGame.getFrame().getBoard().squareAt(coord_to_position(new_move)).getPawnWasHere())
                my_moves.add(coord_to_position(new_move));
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

            // EN PASSANT (IN PASSING)
            if(coords_valid_check(new_move) && ChessGame.getFrame().getBoard().squareAt(coord_to_position(new_move)).getPawnWasHere()) 
                my_moves.add(coord_to_position(new_move));

            // diagnol right
            new_move[1] += 2;

            // validate move
            if(coords_valid_check(new_move)) {
                String player = ChessGame.getFrame().getBoard().squareAt(coord_to_position(new_move)).getPlayer();
                if(player != null && !player.equals(ChessGame.getCurrentPlayer()))
                    my_moves.add(coord_to_position(new_move));
            }

            // EN PASSANT (IN PASSING)
            if(coords_valid_check(new_move) && ChessGame.getFrame().getBoard().squareAt(coord_to_position(new_move)).getPawnWasHere()) 
                my_moves.add(coord_to_position(new_move));
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
        if(coords_valid_check(new_move))// && !doesMoveCauseCheck(curr_pos, coord_to_position(new_move), "king"))
            my_moves.add(coord_to_position(new_move));

        // allows king to move diagonally up/right
        new_move = position_to_coord(curr_pos);
        new_move[0]--;
        new_move[1]++;
        if(coords_valid_check(new_move))// && !doesMoveCauseCheck(curr_pos, coord_to_position(new_move), "king"))
            my_moves.add(coord_to_position(new_move));

        // allows king to move diagonally down/right
        new_move = position_to_coord(curr_pos);
        new_move[0]++;
        new_move[1]++;
        if(coords_valid_check(new_move))// && !doesMoveCauseCheck(curr_pos, coord_to_position(new_move), "king"))
            my_moves.add(coord_to_position(new_move));

        // allows king to move diagonally down/left
        new_move = position_to_coord(curr_pos);
        new_move[0]++;
        new_move[1]--;
        if(coords_valid_check(new_move))// && !doesMoveCauseCheck(curr_pos, coord_to_position(new_move), "king"))
            my_moves.add(coord_to_position(new_move));

        // allows king to move up
        new_move = position_to_coord(curr_pos);
        new_move[0]--;
        if(coords_valid_check(new_move))// && !doesMoveCauseCheck(curr_pos, coord_to_position(new_move), "king"))
            my_moves.add(coord_to_position(new_move));

        // allows king to move down
        new_move = position_to_coord(curr_pos);
        new_move[0]++;
        if(coords_valid_check(new_move))// && !doesMoveCauseCheck(curr_pos, coord_to_position(new_move), "king"))
            my_moves.add(coord_to_position(new_move));

        // allows king to move left
        new_move = position_to_coord(curr_pos);
        new_move[1]++;
        if(coords_valid_check(new_move))// && !doesMoveCauseCheck(curr_pos, coord_to_position(new_move), "king"))
            my_moves.add(coord_to_position(new_move));

        // allows king to move right
        new_move = position_to_coord(curr_pos);
        new_move[1]--;
        if(coords_valid_check(new_move))// && !doesMoveCauseCheck(curr_pos, coord_to_position(new_move), "king"))
            my_moves.add(coord_to_position(new_move));

        if(!ChessGame.isWhiteChecked() && !hasMoved && ChessGame.getCurrentPlayer().equals("White")){
            // white left
            if(!ChessGame.getFrame().getBoard().squareAt(0).hasPieceMoved() &&
                    ChessGame.getFrame().getBoard().squareAt(1).getPiece() == null &&
                    ChessGame.getFrame().getBoard().squareAt(2).getPiece() == null &&
                    ChessGame.getFrame().getBoard().squareAt(3).getPiece() == null) {
                new_move = position_to_coord(2);
                if(coords_valid_check(new_move))// && !doesMoveCauseCheck(curr_pos, 3, "king"))
                    my_moves.add(coord_to_position(new_move));
            }
            // white right
            if(!ChessGame.getFrame().getBoard().squareAt(7).hasPieceMoved() &&
                    ChessGame.getFrame().getBoard().squareAt(6).getPiece() == null &&
                    ChessGame.getFrame().getBoard().squareAt(5).getPiece() == null) {
                new_move = position_to_coord(6);
                if(coords_valid_check(new_move))// && !doesMoveCauseCheck(curr_pos, 5, "king"))
                    my_moves.add(coord_to_position(new_move));
            }
        }
        if(!ChessGame.isBlackChecked() && !hasMoved && ChessGame.getCurrentPlayer().equals("Black")){
            // black left
            if(!ChessGame.getFrame().getBoard().squareAt(56).hasPieceMoved() &&
                    ChessGame.getFrame().getBoard().squareAt(57).getPiece() == null &&
                    ChessGame.getFrame().getBoard().squareAt(58).getPiece() == null &&
                    ChessGame.getFrame().getBoard().squareAt(59).getPiece() == null) {
                new_move = position_to_coord(58);
                if(coords_valid_check(new_move))// && !doesMoveCauseCheck(curr_pos, 59, "king"))
                    my_moves.add(coord_to_position(new_move));
            }
            // black right
            if(!ChessGame.getFrame().getBoard().squareAt(63).hasPieceMoved() &&
                    ChessGame.getFrame().getBoard().squareAt(62).getPiece() == null &&
                    ChessGame.getFrame().getBoard().squareAt(61).getPiece() == null) {
                new_move = position_to_coord(62);
                if(coords_valid_check(new_move))// && !doesMoveCauseCheck(curr_pos, 61, "king"))
                    my_moves.add(coord_to_position(new_move));
            }
        }
    }

    // returns ArrayList of valid positions to choose from
    public static ArrayList<Integer> get_valid_moves(String player, String piece, int curr_pos){
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
        return my_moves;
    }

    // does move cause check?
    public static boolean doesMoveCauseCheck(int currpos, int newpos, String piece) {
        ArrayList<Integer> moves;
        boolean retval;
        // get current player and king position
        String player = ChessGame.getCurrentPlayer();
        int kingPos = ChessGame.getCurrentKingPos();
        // get current square and new square
        ChessSquarePanel currsq = ChessGame.getFrame().getBoard().squareAt(currpos);
        ChessSquarePanel newsq = ChessGame.getFrame().getBoard().squareAt(newpos);
        // get new square's piece, player, and piecelabel
        String enemyPiece = newsq.getPiece();
        String enemyPlayer = newsq.getPlayer();
        JLabel enemyPieceLabel = newsq.getPieceLabel();

        // move the piece to its new position
        newsq.setPiece(piece, player, newpos);

        // remove piece from its current position
        currsq.remove(currsq.getPieceLabel());
        currsq.setPiece(null, null, currpos);

        // update king's position if the king moved
        if(player.equals("White") && piece.equals("king"))
            ChessGame.setWhiteKingPos(newpos);
        else if(player.equals("Black") && piece.equals("king"))
            ChessGame.setBlackKingPos(newpos);

        // if board state is now in check, then move causes check
        if(isBoardStateInCheck(currsq, false))
            retval = true;
        else
            retval = false;

        // put piece back
        currsq.setPiece(piece, player, currpos);

        // reset new square
        newsq.remove(newsq.getPieceLabel());
        newsq.setPieceLabel(enemyPieceLabel);
        newsq.setCurrentPiece(enemyPiece);
        newsq.setCurrentPlayer(enemyPlayer);

        // update king's position if the king moved
        if(player.equals("White") && piece.equals("king"))
            ChessGame.setWhiteKingPos(currpos);
        else if(player.equals("Black") && piece.equals("king"))
            ChessGame.setBlackKingPos(currpos);

        return retval;

    }

    // is the state of the board currently in check?
    public static boolean isBoardStateInCheck(ChessSquarePanel currentSquare, boolean remove) {
        // pass in false for remove unless special circumstances

        // set these to false, if they are true after the next block of code, then the move is invalid
        // since it can put the current player in check
        if(remove) {
            ChessGame.canBlackBeChecked(false);
            ChessGame.canWhiteBeChecked(false);
        }
        
        // get current player
        String currPlayer = ChessGame.getCurrentPlayer();
        String enemyPlayer = ChessGame.getEnemyPlayer();

        // get current king position
        int currKingPos = ChessGame.getCurrentKingPos();

        ArrayList<Integer> moves;

        for(int i = 0; i < 64; i++) {
            ChessSquarePanel square = ChessGame.getFrame().getBoard().squareAt(i);
            if(square.getPlayer() != null && square.getPlayer().equals(enemyPlayer)) {
                ChessGame.changeCurrentPlayer();
                moves = get_valid_moves(enemyPlayer, square.getPiece(), i);
                ChessGame.changeCurrentPlayer();
                if(moves.contains(currKingPos)) {
                    if(remove) {
                        currentSquare.remove(currentSquare.getPieceLabel());
                        ChessGame.canPlayerBeChecked(true);
                    }
                    return true;
                }
            }
        }
        return false;

    }

    public static boolean castleThroughCheck(int curr_pos, int position, String piece) {
        if(piece.equals("king")) {
            if(position == 2)
                 return doesMoveCauseCheck(curr_pos, 3, "king");
            else if(position == 6)
                 return doesMoveCauseCheck(curr_pos, 5, "king");
            else if(position == 58)
                return doesMoveCauseCheck(curr_pos, 59, "king");
            else if(position == 62)
                return doesMoveCauseCheck(curr_pos, 61, "king");
            else
                return false;
        }
        return false;
    }
}