import java.util.ArrayList;
import java.util.HashMap;

public class MoveLogic {
    // check to see if coordinates are a valid move on the board
    public static boolean coords_valid_check(int[] coords){
        if(coords[0] >= 0 && coords[0] <= 7 && coords[1] >= 0 && coords[1] <= 7) {
            if(ChessGame.getFrame().getBoard().squareAt(coords[0] * 8 + coords[1]).getPlayer() != ChessGame.getCurrentPlayer())
                return true;
        }
        return false;
    }
    // check to see if coordinates are a valid move on the board
    public static boolean alt_coords_valid_check(ArrayList<HashMap<String,String>> board, int[] coords){
        if(coords[0] >= 0 && coords[0] <= 7 && coords[1] >= 0 && coords[1] <= 7) {
            if(board.get(coords[0] * 8 + coords[1]).get("Player") != ChessGame.getCurrentPlayer())
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
        // TODO: EN PASSANT (IN PASSING)


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
        if(!hasMoved) {
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

            // diagnol right
            new_move[1] += 2;

            // validate move
            if(coords_valid_check(new_move)) {
                String player = ChessGame.getFrame().getBoard().squareAt(coord_to_position(new_move)).getPlayer();
                if(player != null && !player.equals(ChessGame.getCurrentPlayer()))
                    my_moves.add(coord_to_position(new_move));
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


    public static void moveAltPawn(ArrayList<HashMap<String,String>> board, int[] new_move, ArrayList<Integer> my_moves, boolean hasMoved, int curr_pos) {
        // TODO: EN PASSANT (IN PASSING)


        // allows pawn to move one unit forward
        if(ChessGame.getCurrentPlayer().equals("White"))
            new_move[0]++;
        else
            new_move[0]--;
        if(alt_coords_valid_check(board, new_move)) {
            // make sure pawn can move forward
            if(board.get(coord_to_position(new_move)).get("Piece") == null)
                my_moves.add(coord_to_position(new_move));
        }

        // allows pawn to move two spaces on its first move
        if(!hasMoved) {
            if(ChessGame.getCurrentPlayer().equals("White"))
                new_move[0]++;
            else
                new_move[0]--;
            if(alt_coords_valid_check(board, new_move)) {
                // make sure pawn can move forward
                if(board.get(coord_to_position(new_move)).get("Piece") == null)
                    my_moves.add(coord_to_position(new_move));
            }
        }

        new_move = position_to_coord(curr_pos);

        if(ChessGame.getCurrentPlayer().equals("White")) {
            // diagnol left
            new_move[0]++;
            new_move[1]--;

            // validate move
            if(alt_coords_valid_check(board, new_move)) {
                String player = board.get(coord_to_position(new_move)).get("Player");
                if(player != null && !player.equals(ChessGame.getCurrentPlayer()))
                    my_moves.add(coord_to_position(new_move));
            }

            // diagnol right
            new_move[1] += 2;

            // validate move
            if(alt_coords_valid_check(board, new_move)) {
                String player = board.get(coord_to_position(new_move)).get("Player");
                if(player != null && !player.equals(ChessGame.getCurrentPlayer()))
                    my_moves.add(coord_to_position(new_move));
            }
        }
        else {
            // diagnol left
            new_move[0]--;
            new_move[1]--;

            // validate move
            if(alt_coords_valid_check(board, new_move)) {
                String player = board.get(coord_to_position(new_move)).get("Player");
                if(player != null && !player.equals(ChessGame.getCurrentPlayer()))
                    my_moves.add(coord_to_position(new_move));
            }

            // diagnol right
            new_move[1] += 2;

            // validate move
            if(alt_coords_valid_check(board, new_move)) {
                String player = board.get(coord_to_position(new_move)).get("Player");
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

    public static void moveAltRook(ArrayList<HashMap<String,String>> board, int[] new_move, ArrayList<Integer> my_moves, boolean hasMoved, int curr_pos) {
        // allows rook to move down vertically
        while(true){
            new_move[0]++;
            if(!alt_coords_valid_check(board, new_move))
                break;
            my_moves.add(coord_to_position(new_move));
            if(board.get(coord_to_position(new_move)).get("Piece") == null)
                break;
        }

        new_move = position_to_coord(curr_pos);

        // allows rook to move up vertically
        while(true){
            new_move[0]--;
            if(!alt_coords_valid_check(board, new_move))
                break;
            my_moves.add(coord_to_position(new_move));
            if(board.get(coord_to_position(new_move)).get("Piece") == null)
                break;
        }

        new_move = position_to_coord(curr_pos);

        // allows rook to move right horizontally
        while(true){
            new_move[1]++;
            if(!alt_coords_valid_check(board, new_move))
                break;
            my_moves.add(coord_to_position(new_move));
            if(board.get(coord_to_position(new_move)).get("Piece") == null)
                break;
        }

        new_move = position_to_coord(curr_pos);

        // allows rook to move left horizontally
        while(true){
            new_move[1]--;
            if(!alt_coords_valid_check(board, new_move))
                break;
            my_moves.add(coord_to_position(new_move));
            if(board.get(coord_to_position(new_move)).get("Piece") == null)
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

    public static void moveAltBishop(ArrayList<HashMap<String,String>> board, int[] new_move, ArrayList<Integer> my_moves, int curr_pos) {
        // allows bishop to move diagonally
        while(true){
            new_move[0]++;
            new_move[1]++;
            if(!alt_coords_valid_check(board, new_move))
                break;
            my_moves.add(coord_to_position(new_move));
            if(board.get(coord_to_position(new_move)).get("Piece") == null)
                break;
        }

        new_move = position_to_coord(curr_pos);

        while(true){
            new_move[0]--;
            new_move[1]--;
            if(!alt_coords_valid_check(board, new_move))
                break;
            my_moves.add(coord_to_position(new_move));
            if(board.get(coord_to_position(new_move)).get("Piece") == null)
                break;
        }

        new_move = position_to_coord(curr_pos);

        while(true){
            new_move[0]--;
            new_move[1]++;
            if(!alt_coords_valid_check(board, new_move))
                break;
            my_moves.add(coord_to_position(new_move));
            if(board.get(coord_to_position(new_move)).get("Piece") == null)
                break;
        }

        new_move = position_to_coord(curr_pos);

        while(true){
            new_move[0]++;
            new_move[1]--;
            if(!alt_coords_valid_check(board, new_move))
                break;
            my_moves.add(coord_to_position(new_move));
            if(board.get(coord_to_position(new_move)).get("Piece") == null)
                break;
        }
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

    public static void moveAltQueen(ArrayList<HashMap<String,String>> board, int[] new_move, ArrayList<Integer> my_moves, int curr_pos) {
        moveAltBishop(board, new_move, my_moves, curr_pos); // allows queen to move diagonally
        new_move = position_to_coord(curr_pos);
        moveAltRook(board, new_move, my_moves, true, curr_pos); // allows queen to move vertically
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
        if(recurse)
            return my_moves;

        // TODO: prune moves that put you in check
        for(int move : my_moves){
            // save piece and player in desired location
            String tmpPiece = ChessGame.getFrame().getBoard().squareAt(move).getPiece();
            String tmpPlayer = ChessGame.getFrame().getBoard().squareAt(move).getPlayer();

            // move piece to desired location
            ChessGame.getFrame().getBoard().squareAt(move).setPiece(piece, player, move);

            // remove piece from current location
            ChessGame.getFrame().getBoard().squareAt(curr_pos).remove(ChessGame.getFrame().getBoard().squareAt(curr_pos).getPieceLabel());
            ChessGame.getFrame().getBoard().squareAt(curr_pos).setPiece(null, null, curr_pos);
            ChessGame.getFrame().getBoard().squareAt(curr_pos).setPieceLabel(null);
            ChessGame.getFrame().getBoard().squareAt(curr_pos).setPlayer(null);
            //ChessGame.getFrame().getBoard().squareAt(curr_pos).repaint();

            // check if move puts player in check
            if(player.equals("White")){
                for(int i = 0; i < 64; i++) {
                    if(ChessGame.getFrame().getBoard().squareAt(i).getPlayer() == "Black") {
                        // for every piece on the board, get its valid moves
                        ArrayList<Integer> moves = get_valid_moves("Black", ChessGame.getFrame().getBoard().squareAt(i).getPiece(), i, true);
                        if(moves.contains(ChessGame.getWhiteKingPos()) || (piece == "King" && moves.contains(move))){
                            my_moves.remove(Integer.valueOf(i));
                            System.out.println("puts u in check");
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
                            System.out.println("puts u in check");
                        }
                    }
                }
            }

            // put piece back to where it was moved from
            
            //ChessGame.getFrame().getBoard().squareAt(curr_pos).remove(ChessGame.getFrame().getBoard().squareAt(curr_pos).getPieceLabel());
            if(ChessGame.getFrame().getBoard().squareAt(curr_pos).getPiece() == null)
            //ChessGame.getFrame().getBoard().squareAt(curr_pos).remove(ChessGame.getFrame().getBoard().squareAt(curr_pos).getPieceLabel());
            ChessGame.getFrame().getBoard().squareAt(curr_pos).setPiece(piece, player, curr_pos);

            ChessGame.getFrame().getBoard().squareAt(move).remove(ChessGame.getFrame().getBoard().squareAt(move).getPieceLabel());
            ChessGame.getFrame().getBoard().squareAt(move).setPiece(tmpPiece, tmpPlayer, move);
            
            ChessGame.getFrame().getBoard().squareAt(move).repaint();

            
        }
        */
        
        return my_moves;
    }

    public static boolean amIChecked() {
        // copy state of board
        ArrayList<HashMap<String,String>> board = new ArrayList<HashMap<String,String>>();
        for(int i = 0; i < 64; i++){
            board.add(new HashMap<String,String>());
            board.get(i).put("Player", ChessGame.getFrame().getBoard().squareAt(i).getPlayer());
            board.get(i).put("Piece", ChessGame.getFrame().getBoard().squareAt(i).getPiece());
            if(ChessGame.getFrame().getBoard().squareAt(i).hasPieceMoved())
                board.get(i).put("hasMoved", "true");
            else
                board.get(i).put("hasMoved", "false");
        }
        for(int i = 0; i < 64; i++){
            ArrayList<Integer> my_moves = new ArrayList<>();
            int[] new_move = position_to_coord(i);
            boolean hasMoved;
            if(board.get(i).get("hasMoved") == "true")
                hasMoved = true;
            else
                hasMoved = false;
            if(board.get(i).get("Piece").equals("pawn"))
                moveAltPawn(board, new_move, my_moves, hasMoved, i);
            else if(board.get(i).get("Piece").equals("rook"))
                moveAltRook(board, new_move, my_moves, hasMoved, i);
            else if(board.get(i).get("Piece").equals("knight"))
                moveKnight(new_move, my_moves, i);
            else if(board.get(i).get("Piece").equals("bishop"))
                moveAltBishop(board, new_move, my_moves, i);
            else if(board.get(i).get("Piece").equals("queen"))
                moveAltQueen(board, new_move, my_moves, i);
            else if(board.get(i).get("Piece").equals("king"))
                moveKing(new_move, my_moves, i, hasMoved);
        }
        
        if(ChessGame.getCurrentPlayer() == "White") {

        }
        else{

        }
        
        return true;
    }

}