import java.util.ArrayList;

public class MoveLogic {
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
    // coords[0] = x-value
    // coords[1] = y-value
    public static int coord_to_position(int[] coords) {
        return coords[0]*8+coords[1];
    }

    public static void movePawn(int[] new_move, ArrayList<Integer> my_moves, String player, boolean hasMoved) {
        // allows pawn to move one unit forward
        if(player.equals("White"))
            new_move[0]++;
        else
            new_move[0]--;
        if(coords_valid_check(new_move))
            my_moves.add(coord_to_position(new_move));

        //System.out.println(hasMoved);

        // pawn can move two spaces on its first move
        if(!hasMoved) {
            if(player.equals("White"))
                new_move[0]++;
            else
                new_move[0]--;
            //System.out.println("hit");
            if(coords_valid_check(new_move))
                my_moves.add(coord_to_position(new_move));
        }

        // TODO: since pawns cannot attack forward, make sure that the piece ahead is null in order for
        //       the pawn to move forward

        // TODO allows pawn to attack on the left diagonal if enemy present
        // TODO allows pawn to attack on the right diagonal if enemy present
    }

    public static void moveRook(int[] new_move, ArrayList<Integer> my_moves, boolean hasMoved) {
        // allows rook to move down vertically
        while(true){
            new_move[0]++;
            if(!coords_valid_check(new_move))
                break;
            my_moves.add(coord_to_position(new_move));
        }
        // allows rook to move up vertically
        while(true){
            new_move[0]--;
            if(!coords_valid_check(new_move))
                break;
            my_moves.add(coord_to_position(new_move));
        }
        // allows rook to move right horizontally
        while(true){
            new_move[1]++;
            if(!coords_valid_check(new_move))
                break;
            my_moves.add(coord_to_position(new_move));
        }
        // allows rook to move left horizontally
        while(true){
            new_move[1]--;
            if(!coords_valid_check(new_move))
                break;
            my_moves.add(coord_to_position(new_move));
        }
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

    public static void moveBishop(int[] new_move, ArrayList<Integer> my_moves) {
        // allows bishop to move diagonally
        while(true){
            new_move[0]++;
            new_move[1]++;
            if(!coords_valid_check(new_move))
                break;
            my_moves.add(coord_to_position(new_move));
        }
        while(true){
            new_move[0]--;
            new_move[1]--;
            if(!coords_valid_check(new_move))
                break;
            my_moves.add(coord_to_position(new_move));
        }
    }

    public static void moveQueen(int[] new_move, ArrayList<Integer> my_moves, int curr_pos) {
        // allows queen to move diagonally
        while(true){
            new_move[0]++;
            new_move[1]++;
            if(!coords_valid_check(new_move))
                break;
            my_moves.add(coord_to_position(new_move));
        }

        new_move = position_to_coord(curr_pos);
        while(true){
            new_move[0]--;
            new_move[1]--;
            if(!coords_valid_check(new_move))
                break;
            my_moves.add(coord_to_position(new_move));
       }

        new_move = position_to_coord(curr_pos);
        while(true){
            new_move[0]++;
            if(!coords_valid_check(new_move))
                break;
            my_moves.add(coord_to_position(new_move));
        }
        // allows queen to move up vertically
        new_move = position_to_coord(curr_pos);
        while(true){
            new_move[0]--;
            if(!coords_valid_check(new_move))
                break;
            my_moves.add(coord_to_position(new_move));
        }
        // allows queen to move right horizontally
        new_move = position_to_coord(curr_pos);
        while(true){
            new_move[1]++;
            if(!coords_valid_check(new_move))
                break;
            my_moves.add(coord_to_position(new_move));
        }
        // allows queen to move left horizontally
        new_move = position_to_coord(curr_pos);
        while(true){
            new_move[1]--;
            if(!coords_valid_check(new_move))
                break;
            my_moves.add(coord_to_position(new_move));
        }
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
    public static ArrayList<Integer> get_valid_moves(String player, String piece, int curr_pos){
        ArrayList<Integer> my_moves = new ArrayList<>();

        // TODO NOT SURE HOW TO CHECK IF A PIECE/ENEMY OCCUPIES A GIVEN POSITION, need to write helper function
        // TODO NEED TO ADD COLLISION DETECTION REGARDING MOVEMENT WITH FRIENDLY/ENEMY PIECES
        boolean pieceHasMoved = ChessGame.getFrame().getBoard().squareAt(curr_pos).hasPieceMoved();
        //System.out.println(pieceHasMoved);
        int[] new_move = position_to_coord(curr_pos);
        //if(player.equals("White")){
            if(piece.equals("pawn"))
                movePawn(new_move, my_moves, player, pieceHasMoved);
            else if(piece.equals("rook"))
                moveRook(new_move, my_moves, pieceHasMoved);
            else if(piece.equals("knight"))
                moveKnight(new_move, my_moves, curr_pos);
            else if(piece.equals("bishop"))
                moveBishop(new_move, my_moves);
            else if(piece.equals("queen"))
                moveQueen(new_move, my_moves, curr_pos);
            else if(piece.equals("king"))
                moveKing(new_move, my_moves, curr_pos, pieceHasMoved);
        //}
        return my_moves;
    }

}