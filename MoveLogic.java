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


        if(!hasMoved && ChessGame.getCurrentPlayer().equals("White")){
            // white left
            if(!ChessGame.getFrame().getBoard().squareAt(0).hasPieceMoved() &&
                    ChessGame.getFrame().getBoard().squareAt(1).getPiece() == null &&
                    ChessGame.getFrame().getBoard().squareAt(2).getPiece() == null &&
                    ChessGame.getFrame().getBoard().squareAt(3).getPiece() == null) {
                new_move = position_to_coord(2);
                if(coords_valid_check(new_move))
                    my_moves.add(coord_to_position(new_move));
            }
            // white right
            if(!ChessGame.getFrame().getBoard().squareAt(7).hasPieceMoved() &&
                    ChessGame.getFrame().getBoard().squareAt(6).getPiece() == null &&
                    ChessGame.getFrame().getBoard().squareAt(5).getPiece() == null) {
                new_move = position_to_coord(6);
                if(coords_valid_check(new_move))
                    my_moves.add(coord_to_position(new_move));
            }
        }
        if(!hasMoved && ChessGame.getCurrentPlayer().equals("Black")){
            // black left
            if(!ChessGame.getFrame().getBoard().squareAt(56).hasPieceMoved() &&
                    ChessGame.getFrame().getBoard().squareAt(57).getPiece() == null &&
                    ChessGame.getFrame().getBoard().squareAt(58).getPiece() == null &&
                    ChessGame.getFrame().getBoard().squareAt(59).getPiece() == null) {
                new_move = position_to_coord(58);
                if(coords_valid_check(new_move))
                    my_moves.add(coord_to_position(new_move));
            }
            // black right
            if(!ChessGame.getFrame().getBoard().squareAt(63).hasPieceMoved() &&
                    ChessGame.getFrame().getBoard().squareAt(62).getPiece() == null &&
                    ChessGame.getFrame().getBoard().squareAt(61).getPiece() == null) {
                new_move = position_to_coord(62);
                if(coords_valid_check(new_move))
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
}