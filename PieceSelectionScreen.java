import java.io.Serializable;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.ImageIcon;
import java.awt.Image;
import javax.swing.border.EmptyBorder;
import java.awt.BasicStroke;
import java.awt.event.*;
import java.util.ArrayList;


public class PieceSelectionScreen extends JFrame implements Serializable, ActionListener {
    private BorderLayout layout; // borderlayout object

    public PieceSelectionScreen() {
        super("Select a Piece"); // pass title for window to parent constructor
        
        setSize(320, 320);   // set frame size
        setLocationRelativeTo(null);      // center frame on screen
        setUndecorated(true);
        layout = new BorderLayout( 5, 5 ); // 5 pixel gaps

        setLayout( layout ); // set frame layout

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2, 4, 4));
        panel.setBackground( Color.WHITE );     // set frame background color
        getRootPane().setBorder(BorderFactory.createStrokeBorder(new BasicStroke(2.5f)));
        panel.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(2.0f)));
        add(panel, BorderLayout.CENTER);
        JTextField textFieldNorth = new JTextField();
        textFieldNorth.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        textFieldNorth.setHorizontalAlignment(JTextField.CENTER);
        Font font = new Font(textFieldNorth.getFont().getName(), Font.BOLD, 22);
        textFieldNorth.setFont(font);
        textFieldNorth.setEditable(false);
        textFieldNorth.setText("Choose a Piece");
        textFieldNorth.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(2.5f)));
        add(textFieldNorth, BorderLayout.NORTH);

        setVisible( true );               // display frame

        String image;

        image = "./images/png/" + ChessGame.getCurrentPlayer().toLowerCase() + "_" + "rook" + ".png";
        JLabel rook = new JLabel(new ImageIcon(new ImageIcon(ChessGame.class.getResource(image)).getImage().getScaledInstance(120, 120, Image.SCALE_DEFAULT)));
        JButton rookButton = new JButton();
        rookButton.addActionListener(this);
        rookButton.setActionCommand("rook");
        rook.setVerticalAlignment(JLabel.CENTER);
        rook.setHorizontalAlignment(JLabel.CENTER);
        rookButton.add(rook);
        panel.add(rookButton);

        image = "./images/png/" + ChessGame.getCurrentPlayer().toLowerCase() + "_" + "knight" + ".png";
        JLabel knight = new JLabel(new ImageIcon(new ImageIcon(ChessGame.class.getResource(image)).getImage().getScaledInstance(120, 120, Image.SCALE_DEFAULT)));
        JButton knightButton = new JButton();
        knightButton.addActionListener(this);
        knightButton.setActionCommand("knight");
        knight.setVerticalAlignment(JLabel.CENTER);
        knight.setHorizontalAlignment(JLabel.CENTER);
        knightButton.add(knight);
        panel.add(knightButton);

        image = "./images/png/" + ChessGame.getCurrentPlayer().toLowerCase() + "_" + "bishop" + ".png";
        JLabel bishop = new JLabel(new ImageIcon(new ImageIcon(ChessGame.class.getResource(image)).getImage().getScaledInstance(120, 120, Image.SCALE_DEFAULT)));
        JButton bishopButton = new JButton();
        bishopButton.addActionListener(this);
        bishopButton.setActionCommand("bishop");
        bishop.setVerticalAlignment(JLabel.CENTER);
        bishop.setHorizontalAlignment(JLabel.CENTER);
        bishopButton.add(bishop);
        panel.add(bishopButton);

        image = "./images/png/" + ChessGame.getCurrentPlayer().toLowerCase() + "_" + "queen" + ".png";
        JLabel queen = new JLabel(new ImageIcon(new ImageIcon(ChessGame.class.getResource(image)).getImage().getScaledInstance(120, 120, Image.SCALE_DEFAULT)));
        JButton queenButton = new JButton();
        queenButton.addActionListener(this);
        queenButton.setActionCommand("queen");
        queen.setVerticalAlignment(JLabel.CENTER);
        queen.setHorizontalAlignment(JLabel.CENTER);
        queenButton.add(queen);
        panel.add(queenButton);

        // player is selecting a piece
        ChessGame.playerIsSelectingAPiece(true);
    }

    public void actionPerformed(ActionEvent e){
        String selectedPiece = e.getActionCommand();
        String currentPlayer;
        ChessSquarePanel currSquare;
        int currKingPos;
        if(ChessGame.getCurrentPlayer().equals("White")) {
            currentPlayer = "Black";
            currKingPos = ChessGame.getWhiteKingPos();
        }
        else {
            currentPlayer = "White";
            currKingPos = ChessGame.getBlackKingPos();
        }

        // save current square for referencing
        currSquare = ChessGame.getFrame().getBoard().squareAt(ChessGame.getSuperPawn());

        // replace super pawn with selected piece
        currSquare.remove(currSquare.getPieceLabel());
        currSquare.setPiece(selectedPiece, currentPlayer, ChessGame.getSuperPawn());
        currSquare.validate();

        // play sound
        ChessGame.getClickSound().play();

        // get valid moves for piece
        ArrayList<Integer> moves = new ArrayList<>();
        ChessGame.changeCurrentPlayer();
        moves = MoveLogic.get_valid_moves(currentPlayer, selectedPiece, ChessGame.getSuperPawn());
        ChessGame.changeCurrentPlayer();

        // check for check
        if(moves.contains(currKingPos)){
            System.out.println("check");
            if(currentPlayer.equals("Black"))
                ChessGame.whiteIsChecked(true);
            else
                ChessGame.blackIsChecked(true);

            // store color of squares that are being highlighted red
            if(ChessGame.getFrame().getBoard().squareAt(ChessGame.getSuperPawn()).getBackground() != Color.RED) {
                ChessGame.getValidMovePositions()[ChessGame.getSuperPawn()] = true;
                ChessGame.getValidMoveColors()[ChessGame.getSuperPawn()] = ChessGame.getFrame().getBoard().squareAt(ChessGame.getSuperPawn()).getBackground();
                ChessGame.getValidMovePositions()[currKingPos] = true;
                ChessGame.getValidMoveColors()[currKingPos] = ChessGame.getFrame().getBoard().squareAt(currKingPos).getBackground();
            }
            // highlight squares red
            ChessGame.getFrame().getBoard().squareAt(ChessGame.getSuperPawn()).setBackground(Color.RED);
            ChessGame.getFrame().getBoard().squareAt(currKingPos).setBackground(Color.RED);
        }

        // clean up
        ChessGame.playerIsSelectingAPiece(false);
        ChessGame.setSuperPawn(-1);

        // close window
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }
    
}
