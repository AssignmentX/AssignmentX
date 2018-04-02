import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
//import javax.imageio.ImageIO;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.OverlayLayout;


@SuppressWarnings("serial") // this is used to suppress a serializable warning because JPanel implements serializable
public class ChessSquarePanel extends JPanel implements MouseListener {
    private String piece;
    private String player;
    private int position;
    private JLabel pieceLabel;
    private String image;
    private JLayeredPane overlay;
    private ChessBoardPanel parent;

    public ChessSquarePanel(ChessBoardPanel parent) {
        super();
        this.parent = parent; // keep track of chessboardpanel as parent
        setBorder(BorderFactory.createLineBorder(Color.BLACK)); // add black border
        addMouseListener(this); // adds mouse listener to panel 
        // overlay is used to highlight the panel
        overlay = new JLayeredPane();
        overlay.setLayout(new OverlayLayout(overlay));
        overlay.setBackground(new Color(0, 255, 0, 125));
        add(overlay);
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

    // check to see if coordinates are a valid move on the board
    // TODO NEED TO ADD COLLISION DETECTION FOR FRIENDLY UNITS
    public boolean coords_valid_check(int[] coords){
        if(coords[0] >= 0 && coords[0] <= 7 && coords[1] >= 0 && coords[1] <= 7)
            return true;
        else
            return false;
    }

    // converts a position on the game board to its corresponding coordinates
    public int[] position_to_coord(int pos) {
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
    public int coord_to_position(int[] coords) {
        return coords[0]*8+coords[1];
    }

    // returns ArrayList of valid positions to choose from
    public ArrayList<Integer> get_valid_moves(String player, String piece, int curr_pos){
        ArrayList<Integer> my_moves = new ArrayList<>();

        // TODO NOT SURE HOW TO CHECK IF A PIECE/ENEMY OCCUPIES A GIVEN POSITION, need to write helper function
        // TODO NEED TO ADD COLLISION DETECTION REGARDING MOVEMENT WITH FRIENDLY/ENEMY PIECES
        if(player.equals("White")){
            if(piece.equals("pawn")){
                // TODO NEED TO ADD ABILITY TO MOVE TWO SPACES AT START & OTHER WEIRD PAWN RULES
                // TODO SUCH AS THE ABILITY TO MOVE BACKWARDS IF PAWN REACHES END OF GAME BOARD
                int[] new_move = position_to_coord(curr_pos);
                // allows pawn to move one unit forward
                new_move[0]++;
                if(coords_valid_check(new_move))
                    my_moves.add(coord_to_position(new_move));
                // TODO allows pawn to attack on the left diagonal if enemy present
                // TODO allows pawn to attack on the right diagonal if enemy present
            }
            else if(piece.equals("rook")){
                int[] new_move = position_to_coord(curr_pos);
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
            else if(piece.equals("knight")){
                int[] new_move = position_to_coord(curr_pos);
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
            else if(piece.equals("bishop")) {
                int[] new_move = position_to_coord(curr_pos);
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
            else if(piece.equals("queen")) {
                int[] new_move = position_to_coord(curr_pos);
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
            else if(piece.equals("king")) {
                int[] new_move = position_to_coord(curr_pos);
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
        }

        return my_moves;
    }

    public void paintComponent( Graphics g ) {
        super.paintComponent( g ); // call superclass's paintComponent
        Graphics2D g2d = ( Graphics2D ) g;

        // TODO: RESIZE CHESS PIECES WHEN THE BOARD RESIZES
        //pieceLabel = new JLabel(new ImageIcon(new ImageIcon(getClass().getResource(image)).getImage().getScaledInstance(getWidth()*3/4, getHeight()*3/4, Image.SCALE_DEFAULT)));
    }

    public void mouseClicked(MouseEvent event) {
        // validate the correct player is clicking the square
        if(ChessGame.getCurrentPlayer() == player){
            // TODO: MAYBE HIGHLIGHT SQUARE WHEN CLICKED ??? might need JLayeredPane
            //overlay.setPreferredSize(new Dimension(getWidth(), getHeight()));
            //add(overlay);
            //setLayout(null);
            //repaint();

            // change background color back to default for previously clicked square
            if(ChessGame.getMovingFrom() != -1) {
                parent.squareAt(ChessGame.getMovingFrom()).setBackground(ChessGame.getSelectedSquaresColor());
            }


            // lazy solution instead of highlighting, changes background color
            setBackground(Color.GREEN);

            ChessGame.setCurrentlyMoving(true);
            ChessGame.setMovingFrom(position);
            ChessGame.setSelectedPiece(piece);
            ChessGame.setSelectedSquaresColor(getBackground());

        }
        else if(ChessGame.isMoving()){
            ChessSquarePanel currentPosition = parent.squareAt(ChessGame.getMovingFrom());


            // TODO: VALIDATE RULE HERE BEFORE ALLOWING THIS MOVE TO BE DONE
            ArrayList<Integer> valid_moves = get_valid_moves(ChessGame.getCurrentPlayer(), ChessGame.getSelectedPiece(), ChessGame.getMovingFrom());
            System.out.print("\nValid moves: ");
            for (int x : valid_moves)
                System.out.print(" " + x);

            if(valid_moves.contains(position)){
                System.out.printf("\nUser selected: %d", position);

                // display move message
                String msg = ChessGame.getCurrentPlayer() + " " + ChessGame.getSelectedPiece() + ": " + Integer.toString(ChessGame.getMovingFrom()) + " - " + Integer.toString(position);
                parent.getChessGameFrame().appendTextArea(msg);

                // TODO: UN-HIGHLIGHT SQUARES HERE

                // lazy solution, set color back to default
                //currentPosition.setBackground(ChessGame.getSelectedSquaresColor());

                // remove piece from its current position
                currentPosition.remove(currentPosition.getPieceLabel());
                currentPosition.setPiece(null, null, ChessGame.getMovingFrom());
                currentPosition.repaint();

                // if there is an enemy piece in clicked position, remove it
                if(pieceLabel != null) {
                    remove(pieceLabel);
                    pieceLabel = null;
                }

                // move piece to clicked position
                setPiece(ChessGame.getSelectedPiece(), ChessGame.getCurrentPlayer(), position);


                // clear data for previously selected piece
                ChessGame.setCurrentlyMoving(false);
                ChessGame.setMovingFrom(-1);
                ChessGame.setSelectedPiece(null);

                // change turn to other player
                if(ChessGame.getCurrentPlayer() == "White") {
                    ChessGame.setCurrentPlayer("Black");
                    ChessGame.getFrame().setNorthTextField("Black's Move");
                }
                else {
                    ChessGame.setCurrentPlayer("White");
                    ChessGame.getFrame().setNorthTextField("White's Move");
                }
            }
            else
                System.out.printf("\nMove is: %d, Invalid move", position);

        }
    }

    public JLabel getPieceLabel() {
        return pieceLabel;
    }

    public void mouseExited(MouseEvent event) {}
    public void mouseEntered(MouseEvent event) {}
    public void mousePressed(MouseEvent event) {}
    public void mouseReleased(MouseEvent event) {}
     
}